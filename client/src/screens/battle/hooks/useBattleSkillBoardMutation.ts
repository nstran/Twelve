import { useCallback, type Dispatch, type MutableRefObject, type SetStateAction } from 'react';
import {
  clearMatchedCells,
  collapseLogic,
  collectSkillPacketBoardKeys,
  findMatchesFromAffected,
  type BattleCell,
  type BattleSkillRuntimePacket,
  type Board,
  type FallEntry,
  type JavaBoardEngine,
} from '../core';
import type { useBattleMatchFlow } from './useBattleMatchFlow';

interface UseBattleSkillBoardMutationArgs {
  animateFall: (
    nextBoard: Board,
    fallMap: FallEntry[],
    onDone: () => void,
  ) => void;
  boardEngineRef: MutableRefObject<JavaBoardEngine>;
  boardRef: MutableRefObject<Board>;
  mountedRef: MutableRefObject<boolean>;
  playExplosion: (
    matched: Set<string>,
    expanded: Set<string>,
    board: Board,
    onDone: () => void,
  ) => void;
  processMatchesRef: MutableRefObject<ReturnType<typeof useBattleMatchFlow>['processMatches'] | null>;
  setBoard: Dispatch<SetStateAction<Board>>;
  setFireSwordMarkTriggers: Dispatch<SetStateAction<Record<string, number>>>;
}

export const useBattleSkillBoardMutation = ({
  animateFall,
  boardEngineRef,
  boardRef,
  mountedRef,
  playExplosion,
  processMatchesRef,
  setBoard,
  setFireSwordMarkTriggers,
}: UseBattleSkillBoardMutationArgs) => {
  const applyServerPacketMarkCell = useCallback((cell: BattleCell, stateId: number) => {
    const [row, col] = cell;
    if (stateId === 10) {
      const key = `${row},${col}`;
      setFireSwordMarkTriggers(current => ({
        ...current,
        [key]: (current[key] ?? 0) + 1,
      }));
    }

    setBoard(currentBoard => {
      if (row < 0 || row >= currentBoard.length || col < 0 || col >= currentBoard[row].length) {
        return currentBoard;
      }

      if (currentBoard[row][col] === stateId) {
        return currentBoard;
      }

      const nextBoard = currentBoard.map(boardRow => [...boardRow]);
      nextBoard[row][col] = stateId as Board[number][number];
      boardRef.current = nextBoard;
      return nextBoard;
    });
  }, [boardRef, setBoard, setFireSwordMarkTriggers]);

  const applyServerPacketBoardMutation = useCallback((packet: BattleSkillRuntimePacket) => {
    switch (packet.boardMutation.kind) {
      case 'clear': {
        const matched = collectSkillPacketBoardKeys(packet);
        if (matched.size === 0) return;

        const currentBoard = boardRef.current;
        const clearedBoard = clearMatchedCells(currentBoard, matched);

        playExplosion(matched, matched, currentBoard, () => {
          if (!mountedRef.current) return;
          const { newBoard, fallMap, affectedKeys } = collapseLogic(clearedBoard, matched, boardEngineRef.current);
          boardRef.current = newBoard;
          animateFall(newBoard, fallMap, () => {
            if (!mountedRef.current || !processMatchesRef.current) return;
            if (findMatchesFromAffected(newBoard, affectedKeys).size === 0) return;
            processMatchesRef.current(newBoard, 0, affectedKeys);
          });
        });
        return;
      }
      case 'mark': {
        const stateId = packet.boardMutation.stateId ?? 10;
        if (packet.boardMutation.cells.length === 0) return;
        for (const cell of packet.boardMutation.cells) {
          applyServerPacketMarkCell(cell, stateId);
        }
        return;
      }
      case 'helper':
      case 'none':
      default:
        return;
    }
  }, [animateFall, applyServerPacketMarkCell, boardEngineRef, boardRef, mountedRef, playExplosion, processMatchesRef]);

  return {
    applyServerPacketBoardMutation,
    applyServerPacketMarkCell,
  };
};
