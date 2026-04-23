import { useCallback, type Dispatch, type MutableRefObject, type SetStateAction } from 'react';
import type { BattleCell, BattlePhase, BattleTurn, MoveSpec } from '../core';

interface UseBattlePlayerInputArgs {
  doDirectSwapRef: MutableRefObject<(r1: number, c1: number, r2: number, c2: number) => void>;
  phase: BattlePhase;
  selected: BattleCell | null;
  setCursorCell: Dispatch<SetStateAction<BattleCell>>;
  setHintCell: Dispatch<SetStateAction<BattleCell | null>>;
  setHintMove: Dispatch<SetStateAction<MoveSpec | null>>;
  setSelected: Dispatch<SetStateAction<BattleCell | null>>;
  turn: BattleTurn;
}

export const useBattlePlayerInput = ({
  doDirectSwapRef,
  phase,
  selected,
  setCursorCell,
  setHintCell,
  setHintMove,
  setSelected,
  turn,
}: UseBattlePlayerInputArgs) => {
  const handleGemPress = useCallback((row: number, col: number) => {
    if (phase !== 'idle' || turn !== 'player') return;
    setCursorCell([row, col]);
    setHintCell(null);
    setHintMove(null);
    if (!selected) {
      setSelected([row, col]);
      return;
    }

    const [sr, sc] = selected;
    if (sr === row && sc === col) {
      setSelected(null);
      return;
    }

    const adjacent = (Math.abs(sr - row) === 1 && sc === col)
      || (sr === row && Math.abs(sc - col) === 1);
    if (!adjacent) {
      setSelected([row, col]);
      return;
    }

    setSelected(null);
    doDirectSwapRef.current(sr, sc, row, col);
  }, [doDirectSwapRef, phase, selected, setCursorCell, setHintCell, setHintMove, setSelected, turn]);

  return { handleGemPress };
};
