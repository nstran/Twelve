import { useCallback, useEffect, useRef, type Dispatch, type MutableRefObject, type SetStateAction } from 'react';
import { Animated, Easing } from 'react-native';
import { EXPLODE_END, EXPLODE_START, makeBoard, type Board, type FallEntry, type JavaBoardEngine, BOARD_COLS, BOARD_ROWS, GEM_SIZE } from '../core';

interface UseBattleBoardAnimationsArgs {
  mountedRef: MutableRefObject<boolean>;
  boardRef: MutableRefObject<Board>;
  boardEngineRef: MutableRefObject<JavaBoardEngine>;
  setBoard: Dispatch<SetStateAction<Board>>;
  setExplodeFrames: Dispatch<SetStateAction<Record<string, number>>>;
  onSpawnFX: (matched: Set<string>, board: Board, expanded: Set<string>) => void;
}

export const useBattleBoardAnimations = ({
  mountedRef,
  boardRef,
  boardEngineRef,
  setBoard,
  setExplodeFrames,
  onSpawnFX,
}: UseBattleBoardAnimationsArgs) => {
  const offsets = useRef<Animated.Value[][]>(
    Array.from({ length: BOARD_ROWS }, () =>
      Array.from({ length: BOARD_COLS }, () => new Animated.Value(0)),
    ),
  ).current;
  const swapOffsetsX = useRef<Animated.Value[][]>(
    Array.from({ length: BOARD_ROWS }, () =>
      Array.from({ length: BOARD_COLS }, () => new Animated.Value(0)),
    ),
  ).current;
  const swapOffsetsY = useRef<Animated.Value[][]>(
    Array.from({ length: BOARD_ROWS }, () =>
      Array.from({ length: BOARD_COLS }, () => new Animated.Value(0)),
    ),
  ).current;

  useEffect(() => {
    const topOffset = -(BOARD_ROWS + 2) * GEM_SIZE;
    for (let r = 0; r < BOARD_ROWS; r++) {
      for (let c = 0; c < BOARD_COLS; c++) {
        offsets[r][c].setValue(topOffset);
      }
    }

    const anims: Animated.CompositeAnimation[] = [];
    for (let c = 0; c < BOARD_COLS; c++) {
      for (let r = 0; r < BOARD_ROWS; r++) {
        anims.push(
          Animated.timing(offsets[r][c], {
            toValue: 0,
            duration: 480,
            delay: c * 55,
            easing: Easing.in(Easing.quad),
            useNativeDriver: true,
          }),
        );
      }
    }

    Animated.parallel(anims).start();
  }, [offsets]);

  const playExplosion = useCallback((
    matched: Set<string>,
    expanded: Set<string>,
    board: Board,
    onDone: () => void,
  ) => {
    onSpawnFX(matched, board, expanded);

    let frame = EXPLODE_START;
    const applyFrame = (nextFrame: number) => {
      const next: Record<string, number> = {};
      expanded.forEach(key => { next[key] = nextFrame; });
      setExplodeFrames(next);
    };

    applyFrame(frame);
    const tick = setInterval(() => {
      frame++;
      if (frame > EXPLODE_END || !mountedRef.current) {
        clearInterval(tick);
        setExplodeFrames({});
        onDone();
        return;
      }
      applyFrame(frame);
    }, 65);
  }, [mountedRef, onSpawnFX, setExplodeFrames]);

  const animateFall = useCallback((
    newBoard: Board,
    fallMap: FallEntry[],
    onDone: () => void,
  ) => {
    if (fallMap.length === 0) {
      setBoard(newBoard);
      onDone();
      return;
    }

    fallMap.forEach(({ r, c, srcRow }) => {
      offsets[r][c].setValue((srcRow - r) * GEM_SIZE);
    });
    setBoard(newBoard);

    Animated.parallel(
      fallMap.map(({ r, c, srcRow }) =>
        Animated.timing(offsets[r][c], {
          toValue: 0,
          duration: 120 + Math.abs(srcRow - r) * 55,
          easing: Easing.in(Easing.quad),
          useNativeDriver: true,
        }),
      ),
    ).start(() => {
      if (mountedRef.current) onDone();
    });
  }, [mountedRef, offsets, setBoard]);

  const animateInvalidSwapBounce = useCallback((
    r1: number,
    c1: number,
    r2: number,
    c2: number,
    onDone: () => void,
  ) => {
    const x1 = swapOffsetsX[r1][c1];
    const x2 = swapOffsetsX[r2][c2];
    const y1 = swapOffsetsY[r1][c1];
    const y2 = swapOffsetsY[r2][c2];
    const travelX = (c2 - c1) * GEM_SIZE;
    const travelY = (r2 - r1) * GEM_SIZE;

    x1.stopAnimation();
    x2.stopAnimation();
    y1.stopAnimation();
    y2.stopAnimation();
    x1.setValue(0);
    x2.setValue(0);
    y1.setValue(0);
    y2.setValue(0);

    Animated.sequence([
      Animated.parallel([
        Animated.timing(x1, {
          toValue: travelX,
          duration: 120,
          easing: Easing.out(Easing.quad),
          useNativeDriver: true,
        }),
        Animated.timing(x2, {
          toValue: -travelX,
          duration: 120,
          easing: Easing.out(Easing.quad),
          useNativeDriver: true,
        }),
        Animated.timing(y1, {
          toValue: travelY,
          duration: 120,
          easing: Easing.out(Easing.quad),
          useNativeDriver: true,
        }),
        Animated.timing(y2, {
          toValue: -travelY,
          duration: 120,
          easing: Easing.out(Easing.quad),
          useNativeDriver: true,
        }),
      ]),
      Animated.parallel([
        Animated.timing(x1, {
          toValue: 0,
          duration: 340,
          easing: Easing.out(Easing.back(1.4)),
          useNativeDriver: true,
        }),
        Animated.timing(x2, {
          toValue: 0,
          duration: 340,
          easing: Easing.out(Easing.back(1.4)),
          useNativeDriver: true,
        }),
        Animated.timing(y1, {
          toValue: 0,
          duration: 340,
          easing: Easing.out(Easing.back(1.4)),
          useNativeDriver: true,
        }),
        Animated.timing(y2, {
          toValue: 0,
          duration: 340,
          easing: Easing.out(Easing.back(1.4)),
          useNativeDriver: true,
        }),
      ]),
    ]).start(() => {
      x1.setValue(0);
      x2.setValue(0);
      y1.setValue(0);
      y2.setValue(0);
      if (mountedRef.current) onDone();
    });
  }, [mountedRef, swapOffsetsX, swapOffsetsY]);

  const resetBoardAnim = useCallback((nextBoard: Board | undefined, onDone: () => void) => {
    const boardToRender = nextBoard ?? makeBoard(boardEngineRef.current);
    boardRef.current = boardToRender;
    setBoard(boardToRender);

    const topOffset = -(BOARD_ROWS + 2) * GEM_SIZE;
    for (let r = 0; r < BOARD_ROWS; r++) {
      for (let c = 0; c < BOARD_COLS; c++) {
        offsets[r][c].setValue(topOffset);
      }
    }

    const anims: Animated.CompositeAnimation[] = [];
    for (let c = 0; c < BOARD_COLS; c++) {
      for (let r = 0; r < BOARD_ROWS; r++) {
        anims.push(
          Animated.timing(offsets[r][c], {
            toValue: 0,
            duration: 480,
            delay: c * 55,
            easing: Easing.in(Easing.quad),
            useNativeDriver: true,
          }),
        );
      }
    }

    Animated.parallel(anims).start(() => {
      if (mountedRef.current) onDone();
    });
  }, [boardEngineRef, boardRef, mountedRef, offsets, setBoard]);

  return {
    offsets,
    swapOffsetsX,
    swapOffsetsY,
    playExplosion,
    animateFall,
    animateInvalidSwapBounce,
    resetBoardAnim,
  };
};
