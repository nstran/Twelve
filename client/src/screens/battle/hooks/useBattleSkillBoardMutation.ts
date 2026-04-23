import { useCallback, type Dispatch, type MutableRefObject, type SetStateAction } from 'react';
import {
  clearMatchedCells,
  collapseLogic,
  collectSkillPacketBoardKeys,
  findMatchesFromAffected,
  type BattleCell,
  type GemType,
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
  setFireSwordMarkBaseGems: Dispatch<SetStateAction<Record<string, GemType>>>;
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
  setFireSwordMarkBaseGems,
  setFireSwordMarkTriggers,
}: UseBattleSkillBoardMutationArgs) => {
  const applyServerPacketMarkCell = useCallback((cell: BattleCell, stateId: number) => {
    const [row, col] = cell;
    const currentBoard = boardRef.current;
    if (row < 0 || row >= currentBoard.length || col < 0 || col >= currentBoard[row].length) {
      return;
    }

    const key = `${row},${col}`;
    const currentGem = currentBoard[row][col];
    if (currentGem === stateId && stateId === 10) {
      // Consecutive 1001 casts can legitimately point back to an existing
      // fire-sword mark. Re-arm the strip animation instead of dropping the
      // packet on the floor; keep the board node as state `10`.
      //
      // Reset the visual base to the current red-sword state so the strip does
      // not resurrect the original pre-conversion gem (for example a white
      // sword) underneath the animation.
      setFireSwordMarkBaseGems(current => ({
        ...current,
        [key]: currentGem,
      }));
      setFireSwordMarkTriggers(current => ({
        ...current,
        [key]: (current[key] ?? 0) + 1,
      }));
      return;
    }

    if (currentGem === stateId) {
      return;
    }

    if (stateId === 10 && currentGem !== null) {
      // Java keeps family 1001 as board state `10`; the renderer maps that
      // state to `chess8` art after the strip animation. Do not rewrite the
      // board node to plain `8`, or the special type-2 explosion semantics break.
      setFireSwordMarkBaseGems(current => ({
        ...current,
        [key]: currentGem,
      }));
      setFireSwordMarkTriggers(current => ({
        ...current,
        [key]: (current[key] ?? 0) + 1,
      }));
    }

    const nextBoard = currentBoard.map(boardRow => [...boardRow]);
    nextBoard[row][col] = stateId as Board[number][number];
    boardRef.current = nextBoard;
    setBoard(nextBoard);
  }, [boardRef, setBoard, setFireSwordMarkBaseGems, setFireSwordMarkTriggers]);

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
