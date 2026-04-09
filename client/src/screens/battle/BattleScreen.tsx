import React, { useState, useEffect, useCallback, useRef } from 'react';
import {
  View, Image, Text, TouchableOpacity,
  Animated, Easing,
} from 'react-native';
import {
  MonsterSprite, WALK_FRAMES, ATTACK_FRAMES,
} from '../../engine/MonsterSprite';
import { AnimatedTBar, GemCell, TBar } from './BattleScreen.components';
import {
  BONUS_BANNER_FADE_IN_MS,
  BONUS_BANNER_FADE_OUT_MS,
  BONUS_BANNER_HOLD_MS,
  BATTLE_ASSETS,
  COLLECT_FX_DURATION_MS,
  COLLECT_PULSE_IN_MS,
  COLLECT_PULSE_OUT_MS,
  EXTRA_TURNS_BADGE_TOTAL_MS,
  GAIN_POPUP_DURATION_MS,
  MATCH_HOLD_BEFORE_EXPLODE_MS,
  MATCH_SPARKLE_MAX_MS,
  MATCH_SPARKLE_MIN_MS,
  MATCH_SPARKLE_STAGGER_MS,
  RESULT_ART_INDEX,
  RESULT_ART_META,
  TURN_TIME_LIMIT_SEC,
} from './BattleScreen.constants';
import {
  BOARD_LEFT,
  BOARD_TOP,
  CHARS_ROW_SHIFT_X,
  ENEMY_HUD_LAYOUT,
  ENEMY_SPRITE_SHIFT_X,
  ENEMY_SPRITE_SHIFT_Y,
  EXTRA_TURNS_SHIFT_X,
  EXTRA_TURNS_SHIFT_Y,
  getBattleStageLayout,
  HUD_BASE_Y,
  HUD_STACK_H,
  PLAYER_HUD_LAYOUT,
  PLAYER_SPRITE_SHIFT_X,
  PLAYER_SPRITE_SHIFT_Y,
  TURN_TIMER_SHIFT_X,
  TURN_TIMER_TOP,
} from './BattleScreen.layout';
import {
  calcSwordDamage,
  collapseLogic,
  expandSword,
  findMatches,
  getAllValidMoves,
  hasBonusTurn,
  makeBoard,
  pickAIMove,
} from './BattleScreen.logic';
import {
  AI_CONFIGS,
  AURA1_IMG,
  AURA2_IMG,
  AURA3_IMG,
  CRYS_BLUE,
  CRYS_RED,
  EXPLODE_END,
  EXPLODE_START,
  GEM_CATEGORY,
  GEM_FX,
  GEM_FX_KIND,
  MONSTER_HP,
  SWORD_CAT,
} from './BattleScreen.shared';
import type {
  AILevel,
  Board,
  FallEntry,
  FXKind,
  GemType,
  MoveSpec,
} from './BattleScreen.shared';
import type {
  BattleCell,
  BattlePhase,
  BattleResult,
  BattleScreenProps,
  BattleSide,
  BattleTurn,
  CollectFXItem,
  DamagePopupItem,
  GainPopupItem,
  MatchFXItem,
} from './BattleScreen.types';
import {
  s,
  BG_W, BG_H, BOARD_SCALE,
  BOARD_COLS, BOARD_ROWS, GEM_SIZE,
} from './BattleScreen.styles';
export const BattleScreen: React.FC<BattleScreenProps> = ({
  monsterType, onVictory, onDefeat,
}) => {
  const maxHP  = 100;
  const maxEHP = MONSTER_HP[monsterType] ?? 150;
  const maxMP  = 100;
  const maxPow = 100;

  const [board,         setBoard]         = useState<Board>(makeBoard);
  const [selected,      setSelected]      = useState<BattleCell | null>(null);
  const [hintCell,      setHintCell]      = useState<BattleCell | null>(null);
  const [hintMove,      setHintMove]      = useState<MoveSpec | null>(null);
  const [explodeFrames, setExplodeFrames] = useState<Record<string, number>>({});
  const [playerHP,  setPlayerHP]  = useState(maxHP);
  const [enemyHP,   setEnemyHP]   = useState(maxEHP);
  const [mana,      setMana]      = useState(30);
  const [power,     setPower]     = useState(40);
  const [phase,     setPhase]     = useState<BattlePhase>('idle');
  const [result,    setResult]    = useState<BattleResult | null>(null);
  const [monFrame,  setMonFrame]  = useState<number>(WALK_FRAMES[0]);
  const [monAtk,    setMonAtk]   = useState(false);
  const [aiLevel]  = useState<AILevel | null>('linh_canh');

  // ── Turn-based system ──────────────────────────────────────────────────────
  const [turn, setTurn] = useState<BattleTurn>('player');
  const turnRef = useRef<BattleTurn>('player');

  // ── Extra turns: match 4+ → bonus lượt ────────────────────────────────────
  const [extraTurns, setExtraTurns] = useState(0);
  const extraTurnsRef = useRef(0);
  const [showExtraTurnsBadge, setShowExtraTurnsBadge] = useState(false);
  const [bonusBanner, setBonusBanner] = useState<string | null>(null);
  const bonusBannerAnim = useRef(new Animated.Value(0)).current;
  const extraTurnsBadgeAnim = useRef(new Animated.Value(1)).current;
  const extraTurnsBadgeLoopRef = useRef<Animated.CompositeAnimation | null>(null);
  const extraTurnsBadgeTimeoutRef = useRef<ReturnType<typeof setTimeout> | null>(null);
  const [turnTimeLeft, setTurnTimeLeft] = useState(TURN_TIME_LIMIT_SEC);
  const [turnCycle, setTurnCycle] = useState(0);

  const [matchFX, setMatchFX] = useState<MatchFXItem[]>([]);
  const [damagePopups, setDamagePopups] = useState<DamagePopupItem[]>([]);
  const [collectFX, setCollectFX] = useState<CollectFXItem[]>([]);
  const [gainPopups, setGainPopups] = useState<GainPopupItem[]>([]);
  const fxKeyRef = useRef(0);
  const damagePopupKeyRef = useRef(0);
  const collectFXKeyRef = useRef(0);
  const gainPopupKeyRef = useRef(0);

  const resultArtAnim = useRef(new Animated.Value(0)).current;
  const powerBlinkAnim = useRef(new Animated.Value(1)).current;
  const playerHPBarAnim = useRef(new Animated.Value(maxHP)).current;
  const enemyHPBarAnim = useRef(new Animated.Value(maxEHP)).current;
  const playerCollectAnim = useRef(new Animated.Value(0)).current;
  const enemyCollectAnim = useRef(new Animated.Value(0)).current;
  const powerBlinkLoopRef = useRef<Animated.CompositeAnimation | null>(null);
  const phaseRef   = useRef<BattlePhase>('idle');
  const mountedRef = useRef(true);
  const boardRef   = useRef<Board>(board);
  const enemyHPRef = useRef(enemyHP);
  const playerHPRef= useRef(playerHP);
  const selectedRef = useRef<BattleCell | null>(selected);
  const hintMoveRef = useRef<MoveSpec | null>(hintMove);
  const playerHintShownRef = useRef(false);
  const turnTimerRef = useRef<ReturnType<typeof setInterval> | null>(null);
  const turnDeadlineRef = useRef(0);
  const turnDeadlineKeyRef = useRef('');

  const offsets = useRef<Animated.Value[][]>(
    Array.from({ length: BOARD_ROWS }, () =>
      Array.from({ length: BOARD_COLS }, () => new Animated.Value(0))
    )
  ).current;
  const swapOffsetsX = useRef<Animated.Value[][]>(
    Array.from({ length: BOARD_ROWS }, () =>
      Array.from({ length: BOARD_COLS }, () => new Animated.Value(0))
    )
  ).current;
  const swapOffsetsY = useRef<Animated.Value[][]>(
    Array.from({ length: BOARD_ROWS }, () =>
      Array.from({ length: BOARD_COLS }, () => new Animated.Value(0))
    )
  ).current;

  useEffect(() => { phaseRef.current = phase; }, [phase]);
  useEffect(() => () => { mountedRef.current = false; }, []);
  useEffect(() => { boardRef.current = board; }, [board]);
  useEffect(() => { enemyHPRef.current = enemyHP; }, [enemyHP]);
  useEffect(() => { playerHPRef.current = playerHP; }, [playerHP]);
  useEffect(() => { selectedRef.current = selected; }, [selected]);
  useEffect(() => { hintMoveRef.current = hintMove; }, [hintMove]);
  useEffect(() => { turnRef.current = turn; }, [turn]);
  useEffect(() => { extraTurnsRef.current = extraTurns; }, [extraTurns]);
  useEffect(() => () => {
    extraTurnsBadgeLoopRef.current?.stop();
    if (extraTurnsBadgeTimeoutRef.current !== null) {
      clearTimeout(extraTurnsBadgeTimeoutRef.current);
      extraTurnsBadgeTimeoutRef.current = null;
    }
  }, []);
  useEffect(() => {
    Animated.timing(playerHPBarAnim, {
      toValue: playerHP,
      duration: 420,
      easing: Easing.out(Easing.cubic),
      useNativeDriver: false,
    }).start();
  }, [playerHP, playerHPBarAnim]);
  useEffect(() => {
    Animated.timing(enemyHPBarAnim, {
      toValue: enemyHP,
      duration: 420,
      easing: Easing.out(Easing.cubic),
      useNativeDriver: false,
    }).start();
  }, [enemyHP, enemyHPBarAnim]);
  useEffect(() => {
    extraTurnsBadgeLoopRef.current?.stop();
    extraTurnsBadgeLoopRef.current = null;
    if (extraTurnsBadgeTimeoutRef.current !== null) {
      clearTimeout(extraTurnsBadgeTimeoutRef.current);
      extraTurnsBadgeTimeoutRef.current = null;
    }

    if (extraTurns <= 0) {
      setShowExtraTurnsBadge(false);
      extraTurnsBadgeAnim.setValue(1);
      return;
    }

    setShowExtraTurnsBadge(true);
    extraTurnsBadgeAnim.setValue(1);
    const loop = Animated.loop(
      Animated.sequence([
        Animated.timing(extraTurnsBadgeAnim, {
          toValue: 0.25,
          duration: 220,
          easing: Easing.inOut(Easing.quad),
          useNativeDriver: true,
        }),
        Animated.timing(extraTurnsBadgeAnim, {
          toValue: 1,
          duration: 220,
          easing: Easing.inOut(Easing.quad),
          useNativeDriver: true,
        }),
      ]),
    );
    extraTurnsBadgeLoopRef.current = loop;
    loop.start();

    extraTurnsBadgeTimeoutRef.current = setTimeout(() => {
      if (!mountedRef.current) return;
      extraTurnsBadgeLoopRef.current?.stop();
      extraTurnsBadgeLoopRef.current = null;
      extraTurnsBadgeAnim.setValue(1);
      setShowExtraTurnsBadge(false);
      extraTurnsBadgeTimeoutRef.current = null;
    }, EXTRA_TURNS_BADGE_TOTAL_MS);
  }, [extraTurns, extraTurnsBadgeAnim]);
  useEffect(() => {
    powerBlinkLoopRef.current?.stop();
    powerBlinkLoopRef.current = null;

    if (power < maxPow || result !== null) {
      powerBlinkAnim.setValue(1);
      return;
    }

    powerBlinkAnim.setValue(1);
    const loop = Animated.loop(
      Animated.sequence([
        Animated.timing(powerBlinkAnim, {
          toValue: 0.15,
          duration: 200,
          easing: Easing.inOut(Easing.quad),
          useNativeDriver: true,
        }),
        Animated.timing(powerBlinkAnim, {
          toValue: 1,
          duration: 200,
          easing: Easing.inOut(Easing.quad),
          useNativeDriver: true,
        }),
      ]),
    );
    powerBlinkLoopRef.current = loop;
    loop.start();

    return () => {
      loop.stop();
      powerBlinkLoopRef.current = null;
      powerBlinkAnim.setValue(1);
    };
  }, [power, maxPow, result, powerBlinkAnim]);
  useEffect(() => {
    if (result === null) {
      resultArtAnim.setValue(0);
      return;
    }

    resultArtAnim.setValue(0);
    Animated.spring(resultArtAnim, {
      toValue: 1,
      friction: 6,
      tension: 80,
      useNativeDriver: true,
    }).start();
  }, [result, resultArtAnim]);

  // ── Entry animation: gem rơi theo cột ─────────────────────────────────────
  useEffect(() => {
    const TOP = -(BOARD_ROWS + 2) * GEM_SIZE;
    for (let r = 0; r < BOARD_ROWS; r++)
      for (let c = 0; c < BOARD_COLS; c++)
        offsets[r][c].setValue(TOP);

    const anims: Animated.CompositeAnimation[] = [];
    for (let c = 0; c < BOARD_COLS; c++)
      for (let r = 0; r < BOARD_ROWS; r++)
        anims.push(Animated.timing(offsets[r][c], {
          toValue: 0, duration: 480,
          delay: c * 55,
          easing: Easing.in(Easing.quad),
          useNativeDriver: true,
        }));
    Animated.parallel(anims).start();
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  // ── Monster animation ──────────────────────────────────────────────────────
  const monTick = useRef(0);
  useEffect(() => {
    const t = setInterval(() => {
      if (!mountedRef.current) return;
      monTick.current++;
      const frames = monAtk ? ATTACK_FRAMES : WALK_FRAMES;
      setMonFrame(frames[monTick.current % frames.length]);
    }, 200);
    return () => clearInterval(t);
  }, [monAtk]);

  // ── Enemy auto-attack REMOVED — monster now plays turn-based on the board ──

  // ── Spawn particle effects at matched positions ────────────────────────────
  const spawnFX = useCallback((matched: Set<string>, b: Board, expanded: Set<string>) => {
    const items: MatchFXItem[] = [];
    const hasSwordExpansion = expanded.size > matched.size;

    const rand = (min: number, max: number) => min + Math.random() * (max - min);
    const pickMatchFXSource = (kind: FXKind) => {
      switch (kind) {
        case 'gold':
          return Math.random() > 0.35 ? AURA1_IMG : AURA2_IMG;
        case 'mp':
          return Math.random() > 0.5 ? CRYS_BLUE : AURA3_IMG;
        case 'crystal_red':
          return Math.random() > 0.55 ? CRYS_RED : AURA1_IMG;
        case 'sword':
          return Math.random() > 0.5 ? AURA2_IMG : AURA3_IMG;
      }
    };
    const pushBurst = (
      r: number,
      c: number,
      kind: FXKind,
      count: number,
      spreadMul: number,
      sizeMul: [number, number],
    ) => {
      for (let i = 0; i < count; i++) {
        const angle = rand(-Math.PI, Math.PI);
        const startRadius = rand(GEM_SIZE * 0.04, GEM_SIZE * 0.24);
        const spreadRadius = rand(GEM_SIZE * 0.42, GEM_SIZE * spreadMul);
        const lift = rand(GEM_SIZE * 0.18, GEM_SIZE * 0.6);
        items.push({
          key: `fx-${++fxKeyRef.current}`,
          r,
          c,
          kind,
          source: pickMatchFXSource(kind),
          size: rand(GEM_SIZE * sizeMul[0], GEM_SIZE * sizeMul[1]),
          startOffsetX: Math.cos(angle) * startRadius,
          startOffsetY: Math.sin(angle) * startRadius * 0.7,
          driftX: Math.cos(angle) * spreadRadius,
          driftY: Math.sin(angle) * spreadRadius * 0.65 - lift,
          rotate: `${rand(-28, 28)}deg`,
          anim: new Animated.Value(0),
          delayMs: rand(0, MATCH_SPARKLE_STAGGER_MS),
          durationMs: rand(MATCH_SPARKLE_MIN_MS, MATCH_SPARKLE_MAX_MS),
        });
      }
    };

    matched.forEach(k => {
      const [r, c] = k.split(',').map(Number);
      const g = b[r][c];
      if (g === null) return;
      const kind = GEM_FX_KIND[g];
      if (kind !== 'sword') {
        pushBurst(r, c, kind, 5, 1.1, [0.24, 0.62]);
      }
    });

    if (hasSwordExpansion) {
      const swordCells: Array<[number, number]> = [];
      matched.forEach(k => {
        const [r, c] = k.split(',').map(Number);
        const g = b[r][c];
        if (g !== null && GEM_CATEGORY[g] === SWORD_CAT) swordCells.push([r, c]);
      });
      swordCells.forEach(([r, c]) => {
        pushBurst(r, c, 'sword', 9, 1.35, [0.32, 0.78]);
      });
    }

    if (items.length === 0) return;
    setMatchFX(prev => [...prev, ...items]);

    Animated.parallel(
      items.map(fx => Animated.sequence([
        Animated.delay(fx.delayMs),
        Animated.timing(fx.anim, {
          toValue: 1,
          duration: fx.durationMs,
          easing: Easing.out(Easing.cubic),
          useNativeDriver: true,
        }),
      ]))
    ).start(() => {
      if (!mountedRef.current) return;
      const keys = new Set(items.map(f => f.key));
      setMatchFX(prev => prev.filter(f => !keys.has(f.key)));
    });
  }, []);

  // ── Explosion animation ────────────────────────────────────────────────────
  const playExplosion = useCallback((
    matched: Set<string>, expanded: Set<string>, b: Board, onDone: () => void,
  ) => {
    // Spawn particle effects
    spawnFX(matched, b, expanded);

    let frame = EXPLODE_START;
    const apply = (f: number) => {
      const next: Record<string, number> = {};
      expanded.forEach(k => { next[k] = f; });
      setExplodeFrames(next);
    };
    apply(frame);
    const tick = setInterval(() => {
      frame++;
      if (frame > EXPLODE_END || !mountedRef.current) {
        clearInterval(tick); setExplodeFrames({}); onDone(); return;
      }
      apply(frame);
    }, 65);
  }, [spawnFX]);

  // ── Fall animation ─────────────────────────────────────────────────────────
  const animateFall = useCallback((
    newBoard: Board, fallMap: FallEntry[], onDone: () => void,
  ) => {
    if (fallMap.length === 0) { setBoard(newBoard); onDone(); return; }

    fallMap.forEach(({ r, c, srcRow }) => {
      offsets[r][c].setValue((srcRow - r) * GEM_SIZE);
    });
    setBoard(newBoard);

    const maxDist = Math.max(...fallMap.map(({ r, srcRow }) => Math.abs(srcRow - r)));
    Animated.parallel(
      fallMap.map(({ r, c, srcRow }) => Animated.timing(offsets[r][c], {
        toValue: 0,
        duration: 120 + Math.abs(srcRow - r) * 55,
        easing: Easing.in(Easing.quad),
        useNativeDriver: true,
      }))
    ).start(() => { if (mountedRef.current) onDone(); });
  }, [offsets]);

  const animateInvalidSwapBounce = useCallback((
    r1: number, c1: number, r2: number, c2: number, onDone: () => void,
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
  }, [swapOffsetsX, swapOffsetsY]);

  // ── Board reset: khi không còn nước đi, tạo board mới rơi từ trên xuống ──
  const resetBoardAnim = useCallback((onDone: () => void) => {
    const nb = makeBoard();
    boardRef.current = nb;
    setBoard(nb);

    // Tất cả gem bắt đầu ở trên cao rồi rơi xuống (giống entry animation)
    const TOP = -(BOARD_ROWS + 2) * GEM_SIZE;
    for (let r = 0; r < BOARD_ROWS; r++)
      for (let c = 0; c < BOARD_COLS; c++)
        offsets[r][c].setValue(TOP);

    const anims: Animated.CompositeAnimation[] = [];
    for (let c = 0; c < BOARD_COLS; c++)
      for (let r = 0; r < BOARD_ROWS; r++)
        anims.push(Animated.timing(offsets[r][c], {
          toValue: 0, duration: 480,
          delay: c * 55,
          easing: Easing.in(Easing.quad),
          useNativeDriver: true,
        }));

    Animated.parallel(anims).start(() => {
      if (mountedRef.current) onDone();
    });
  }, [offsets]);

  // ── Bonus turn banner flash ─────────────────────────────────────────────────
  const showBonusBanner = useCallback((msg: string) => {
    setBonusBanner(msg);
    bonusBannerAnim.setValue(0);
    Animated.sequence([
      Animated.timing(bonusBannerAnim, { toValue: 1, duration: BONUS_BANNER_FADE_IN_MS, useNativeDriver: true }),
      Animated.delay(BONUS_BANNER_HOLD_MS),
      Animated.timing(bonusBannerAnim, { toValue: 0, duration: BONUS_BANNER_FADE_OUT_MS, useNativeDriver: true }),
    ]).start(() => { if (mountedRef.current) setBonusBanner(null); });
  }, [bonusBannerAnim]);

  const showDamagePopup = useCallback((side: BattleSide, amount: number) => {
    if (amount <= 0) return;
    const anim = new Animated.Value(0);
    const key = `dmg-${++damagePopupKeyRef.current}`;
    setDamagePopups(prev => [...prev, { key, side, amount, anim }]);
    Animated.timing(anim, {
      toValue: 1,
      duration: 700,
      easing: Easing.out(Easing.cubic),
      useNativeDriver: true,
    }).start(() => {
      if (!mountedRef.current) return;
      setDamagePopups(prev => prev.filter(item => item.key !== key));
    });
  }, []);

  const showGainPopup = useCallback((side: BattleSide, text: string) => {
    const anim = new Animated.Value(0);
    const key = `gain-${++gainPopupKeyRef.current}`;
    setGainPopups(prev => [...prev, { key, side, text, anim }]);
    Animated.timing(anim, {
      toValue: 1,
      duration: GAIN_POPUP_DURATION_MS,
      easing: Easing.out(Easing.cubic),
      useNativeDriver: true,
    }).start(() => {
      if (!mountedRef.current) return;
      setGainPopups(prev => prev.filter(item => item.key !== key));
    });
  }, []);

  const pulseCollector = useCallback((side: BattleSide) => {
    const anim = side === 'player' ? playerCollectAnim : enemyCollectAnim;
    anim.stopAnimation();
    anim.setValue(0);
    Animated.sequence([
      Animated.timing(anim, {
        toValue: 1,
        duration: COLLECT_PULSE_IN_MS,
        easing: Easing.out(Easing.quad),
        useNativeDriver: true,
      }),
      Animated.timing(anim, {
        toValue: 0,
        duration: COLLECT_PULSE_OUT_MS,
        easing: Easing.in(Easing.quad),
        useNativeDriver: true,
      }),
    ]).start();
  }, [enemyCollectAnim, playerCollectAnim]);

  const spawnCollectFX = useCallback((
    matched: Set<string>,
    b: Board,
    collectorSide: BattleSide,
    healAmount: number,
  ) => {
    const rand = (min: number, max: number) => min + Math.random() * (max - min);
    const hpRect = collectorSide === 'player'
      ? {
        x: panelLeft + playerHud.offsetX + playerHud.hp.x,
        y: panelTop + HUD_BASE_Y + playerHud.shiftY + playerHud.hp.y,
        w: playerHud.hp.w,
        h: playerHud.hp.h,
      }
      : {
        x: panelLeft + BG_W - enemyHud.offsetX - enemyHud.hp.x - enemyHud.hp.w,
        y: panelTop + HUD_BASE_Y + enemyHud.shiftY + enemyHud.hp.y,
        w: enemyHud.hp.w,
        h: enemyHud.hp.h,
      };
    const mpRect = collectorSide === 'player'
      ? {
        x: panelLeft + playerHud.offsetX + playerHud.mp.x,
        y: panelTop + HUD_BASE_Y + playerHud.shiftY + playerHud.mp.y,
        w: playerHud.mp.w,
        h: playerHud.mp.h,
      }
      : {
        x: panelLeft + BG_W - enemyHud.offsetX - enemyHud.mp.x - enemyHud.mp.w,
        y: panelTop + HUD_BASE_Y + enemyHud.shiftY + enemyHud.mp.y,
        w: enemyHud.mp.w,
        h: enemyHud.mp.h,
      };
    const powRect = collectorSide === 'player'
      ? {
        x: panelLeft + playerHud.offsetX + playerHud.power.x,
        y: panelTop + HUD_BASE_Y + playerHud.shiftY + playerHud.power.y,
        w: playerHud.power.w,
        h: playerHud.power.h,
      }
      : {
        x: panelLeft + BG_W - enemyHud.offsetX - enemyHud.power.x - enemyHud.power.w,
        y: panelTop + HUD_BASE_Y + enemyHud.shiftY + enemyHud.power.y,
        w: enemyHud.power.w,
        h: enemyHud.power.h,
      };
    const charRect = collectorSide === 'player'
      ? {
        x: panelLeft + 24,
        y: charsTop + 10,
        w: 42,
        h: 30,
      }
      : {
        x: panelLeft + BG_W - 92,
        y: charsTop + 6,
        w: 46,
        h: 34,
      };

    const usedTargets = {
      hp: [] as Array<{ x: number; y: number }>,
      mp: [] as Array<{ x: number; y: number }>,
      pow: [] as Array<{ x: number; y: number }>,
      char: [] as Array<{ x: number; y: number }>,
    };
    const pickSpacedTarget = (
      bucket: Array<{ x: number; y: number }>,
      rect: { x: number; y: number; w: number; h: number },
      minDistance: number,
      yOvershoot: number,
    ) => {
      let best = {
        x: rect.x + rect.w / 2,
        y: rect.y + rect.h / 2,
        score: -Infinity,
      };

      for (let i = 0; i < 16; i++) {
        const x = rect.x + rand(-rect.w * 0.08, rect.w * 1.08);
        const y = rect.y + rand(-yOvershoot, rect.h + yOvershoot);
        const minScore = bucket.length === 0
          ? Infinity
          : Math.min(...bucket.map(p => Math.hypot(x - p.x, y - p.y)));
        if (minScore > best.score) best = { x, y, score: minScore };
        if (minScore >= minDistance) {
          bucket.push({ x, y });
          return { x, y };
        }
      }

      bucket.push({ x: best.x, y: best.y });
      return { x: best.x, y: best.y };
    };

    const pushCollectBurst = (
      source: any,
      startX: number,
      startY: number,
      rect: { x: number; y: number; w: number; h: number },
      targetBucket: Array<{ x: number; y: number }>,
      count: number,
      sizeRange: [number, number],
      fadeOutAt: number,
      endScale: number,
      arcLiftRange: [number, number],
      glow: { scale: number; opacity: number },
      minDistance: number,
      crop?: { left: number; width: number },
      sizeProfile?: number[],
      delayRangeMs: [number, number] = [0, 320],
      durationRangeMs: [number, number] = [COLLECT_FX_DURATION_MS - 380, COLLECT_FX_DURATION_MS + 260],
    ) => {
      for (let i = 0; i < count; i++) {
        const particleStartX = startX + rand(-GEM_SIZE * 0.34, GEM_SIZE * 0.34);
        const particleStartY = startY + rand(-GEM_SIZE * 0.26, GEM_SIZE * 0.26);
        const target = pickSpacedTarget(targetBucket, rect, minDistance, rect.h * 0.9);
        const targetX = target.x;
        const targetY = target.y;
        const curve1X = particleStartX
          + (targetX - particleStartX) * rand(0.14, 0.3)
          + rand(-GEM_SIZE * 1.9, GEM_SIZE * 1.9);
        const curve1Y = particleStartY
          + (targetY - particleStartY) * rand(0.1, 0.22)
          - rand(arcLiftRange[0] * 1.08, arcLiftRange[1] * 1.22);
        const curve2X = particleStartX
          + (targetX - particleStartX) * rand(0.5, 0.78)
          + rand(-GEM_SIZE * 1.45, GEM_SIZE * 1.45);
        const curve2Y = particleStartY
          + (targetY - particleStartY) * rand(0.44, 0.68)
          - rand(arcLiftRange[0] * 0.22, arcLiftRange[1] * 0.5);
        const baseSize = sizeProfile !== undefined
          ? sizeProfile[Math.min(i, sizeProfile.length - 1)]
          : rand(sizeRange[0], sizeRange[1]);
        const size = baseSize + rand(-1.2, 1.2);
        const cropLeft = crop?.left ?? 0;
        const cropWidth = crop?.width ?? 45;
        const renderW = size;
        const renderH = crop !== undefined ? size * (15 / cropWidth) : size;
        const delayMs = rand(delayRangeMs[0], delayRangeMs[1]);
        const durationMs = rand(durationRangeMs[0], durationRangeMs[1]);

        items.push({
          key: `cfx-${++collectFXKeyRef.current}`,
          source,
          startX: particleStartX,
          startY: particleStartY,
          curve1X,
          curve1Y,
          curve2X,
          curve2Y,
          endX: targetX,
          endY: targetY,
          size,
          isCrystal: crop !== undefined,
          renderW,
          renderH,
          cropLeft,
          cropWidth,
          fadeOutAt,
          endScale,
          glowScale: glow.scale,
          glowOpacity: glow.opacity,
          delayMs,
          durationMs,
          anim: new Animated.Value(0),
        });
      }
    };

    const items: CollectFXItem[] = [];
    let hitHP = false;
    let hitMP = false;
    let hitPow = false;
    let hitCharacter = false;

    matched.forEach(k => {
      const [r, c] = k.split(',').map(Number);
      const g = b[r][c];
      if (g === null) return;
      const fx = GEM_FX[g];
      const startX = panelLeft + BOARD_LEFT + c * GEM_SIZE + GEM_SIZE / 2;
      const startY = panelTop + BOARD_TOP + r * GEM_SIZE + GEM_SIZE / 2;

      if (fx.heal > 0) {
        hitHP = true;
        pushCollectBurst(
          CRYS_RED, startX, startY, hpRect, usedTargets.hp, 2, [44, 56], 0.95, 0.9,
          [GEM_SIZE * 0.48, GEM_SIZE * 1.14], { scale: 1.86, opacity: 0.62 }, 36,
          { left: 7, width: 38 }, [56, 46], [0, 280], [COLLECT_FX_DURATION_MS - 220, COLLECT_FX_DURATION_MS + 320],
        );
      }
      if (fx.mana > 0) {
        hitMP = true;
        pushCollectBurst(
          CRYS_BLUE, startX, startY, mpRect, usedTargets.mp, 2, [42, 54], 0.95, 0.88,
          [GEM_SIZE * 0.44, GEM_SIZE * 1.08], { scale: 1.82, opacity: 0.58 }, 34,
          { left: 7, width: 38 }, [54, 44], [0, 280], [COLLECT_FX_DURATION_MS - 220, COLLECT_FX_DURATION_MS + 320],
        );
      }
      if (fx.pow > 0) {
        hitPow = true;
        pushCollectBurst(
          AURA1_IMG, startX, startY, powRect, usedTargets.pow, 3, [24, 34], 0.93, 0.54,
          [GEM_SIZE * 0.36, GEM_SIZE * 1.04], { scale: 1.62, opacity: 0.34 }, 22,
          undefined, [34, 29, 24], [20, 260], [COLLECT_FX_DURATION_MS - 260, COLLECT_FX_DURATION_MS + 280],
        );
      }
      if (g === 2) {
        hitCharacter = true;
        pushCollectBurst(
          AURA3_IMG, startX, startY, charRect, usedTargets.char, 4, [24, 34], 0.84, 0.38,
          [GEM_SIZE * 0.3, GEM_SIZE * 0.94], { scale: 1.58, opacity: 0.3 }, 20,
          undefined, [34, 30, 27, 24], [10, 260], [COLLECT_FX_DURATION_MS - 300, COLLECT_FX_DURATION_MS + 260],
        );
      }
      if (g === 5) {
        hitCharacter = true;
        pushCollectBurst(
          AURA2_IMG, startX, startY, charRect, usedTargets.char, 4, [24, 34], 0.84, 0.38,
          [GEM_SIZE * 0.3, GEM_SIZE * 0.94], { scale: 1.58, opacity: 0.3 }, 20,
          undefined, [34, 30, 27, 24], [10, 260], [COLLECT_FX_DURATION_MS - 300, COLLECT_FX_DURATION_MS + 260],
        );
      }
    });

    if (items.length === 0) return;
    setCollectFX(prev => [...prev, ...items]);

    Animated.parallel(
      items.map(item => Animated.sequence([
        Animated.delay(item.delayMs),
        Animated.timing(item.anim, {
          toValue: 1,
          duration: item.durationMs,
          easing: Easing.inOut(Easing.quad),
          useNativeDriver: true,
        }),
      ])),
    ).start(() => {
      if (!mountedRef.current) return;
      const keys = new Set(items.map(item => item.key));
      setCollectFX(prev => prev.filter(item => !keys.has(item.key)));
      if (hitHP && healAmount > 0) showGainPopup(collectorSide, `+${healAmount} HP`);
      if (hitHP || hitMP || hitPow || hitCharacter) pulseCollector(collectorSide);
    });
  }, [pulseCollector, showGainPopup]);

  // ── Chain processor ────────────────────────────────────────────────────────
  const processMatches = useCallback((b: Board, chain: number) => {
    if (!mountedRef.current) return;
    const raw = findMatches(b);
    if (raw.size === 0) {
      setBoard(b);

      // ── Check deadlock: không còn nước đi → reset board ──
      if (getAllValidMoves(b).length === 0) {
        showBonusBanner('🔀 Hết nước! Bàn cờ mới!');
        resetBoardAnim(() => {
          if (!mountedRef.current) return;
          // Sau khi reset, giữ nguyên turn + extraTurns, quay lại idle
          setPhase('idle'); phaseRef.current = 'idle';
        });
        return;
      }

      // ── Turn switch: check extraTurns before switching ──
      if (extraTurnsRef.current > 0) {
        // Còn lượt bonus → giữ nguyên turn, trừ 1 lượt
        const remaining = extraTurnsRef.current - 1;
        extraTurnsRef.current = remaining;
        setExtraTurns(remaining);
        const who = turnRef.current === 'player' ? 'Bạn' : 'Quái';
        showBonusBanner(`🔄 ${who} được thêm lượt! ${remaining > 0 ? `Còn ${remaining} lượt` : ''}`);
        setPhase('idle'); phaseRef.current = 'idle';
      } else {
        // Hết lượt bonus → chuyển turn
        const nextTurn = turnRef.current === 'player' ? 'monster' : 'player';
        turnRef.current = nextTurn;
        setTurn(nextTurn);
        setPhase('idle'); phaseRef.current = 'idle';
      }
      return;
    }

    // ── Detect 4+ match cùng màu → +1 bonus turn (flat, mỗi bước chain) ──
    if (hasBonusTurn(raw, b)) {
      const newExtra = extraTurnsRef.current + 1;
      extraTurnsRef.current = newExtra;
      setExtraTurns(newExtra);
      const who = turnRef.current === 'player' ? 'Bạn' : 'Quái';
      showBonusBanner(`✨ ${who} +1 lượt!${newExtra > 1 ? ` (tổng ${newExtra})` : ''}`);
    }

    const matched = expandSword(raw, b);

    let dmg = 0, heal = 0, mp = 0, pow = 0;
    const counts: Partial<Record<GemType, number>> = {};
    raw.forEach(k => {
      const [r, c] = k.split(',').map(Number);
      const g = b[r][c];
      if (g !== null) counts[g] = (counts[g] ?? 0) + 1;
    });
    dmg = calcSwordDamage(b, matched);
    Object.entries(counts).forEach(([gs, cnt]) => {
      const g = Number(gs) as GemType;
      const fx = GEM_FX[g]; const mul = 1 + chain * 0.4;
      heal += Math.round(fx.heal * (cnt! / 3) * mul);
      mp   += Math.round(fx.mana * cnt!);
      pow  += Math.round(fx.pow  * cnt!);
    });
    dmg = Math.round(dmg * (1 + chain * 0.4));
    setTimeout(() => {
      if (!mountedRef.current || phaseRef.current === 'over') return;
      const collectorSide = turnRef.current === 'player' ? 'player' : 'enemy';
      spawnCollectFX(raw, b, collectorSide, heal);
      playExplosion(raw, matched, b, () => {
        if (!mountedRef.current) return;
        const { newBoard, fallMap } = collapseLogic(b, matched);

        // ── Turn-based effects: player turn → hurt monster, monster turn → hurt player ──
        if (turnRef.current === 'player') {
          // Player's turn: damage to enemy, heal/mp/pow to player
          if (dmg > 0) showDamagePopup('enemy', dmg);
          setEnemyHP(hp => {
            const next = Math.max(0, hp - dmg);
            if (next === 0 && phaseRef.current !== 'over') {
              phaseRef.current = 'over'; setPhase('over'); setResult('victory');
            }
            return next;
          });
          if (heal > 0) setPlayerHP(hp => Math.min(maxHP, hp + heal));
          if (mp   > 0) setMana(m       => Math.min(maxMP, m + mp));
          if (pow  > 0) setPower(p      => Math.min(maxPow, p + pow));
        } else {
          // Monster's turn: damage to player, heal to monster
          if (dmg > 0) {
            showDamagePopup('player', dmg);
            setMonAtk(true);
            setTimeout(() => mountedRef.current && setMonAtk(false), 600);
            setPlayerHP(hp => {
              const next = Math.max(0, hp - dmg);
              if (next === 0 && phaseRef.current !== 'over') {
                phaseRef.current = 'over'; setPhase('over'); setResult('defeat');
              }
              return next;
            });
          }
          if (heal > 0) setEnemyHP(hp => Math.min(maxEHP, hp + heal));
          // Monster doesn't gain player's mana/power
        }

        animateFall(newBoard, fallMap, () => {
          setTimeout(() => processMatches(newBoard, chain + 1), 80);
        });
      });
    }, MATCH_HOLD_BEFORE_EXPLODE_MS);
  }, [playExplosion, animateFall, resetBoardAnim, showBonusBanner, showDamagePopup, spawnCollectFX, maxHP, maxEHP, maxMP, maxPow]);

  // ── Direct swap (dùng cho cả AI và human) ─────────────────────────────────
  const doDirectSwap = useCallback((r1: number, c1: number, r2: number, c2: number) => {
    const b = boardRef.current;
    const nb: Board = b.map(r => [...r]);
    [nb[r1][c1], nb[r2][c2]] = [nb[r2][c2], nb[r1][c1]];
    if (findMatches(nb).size === 0) {
      setPhase('busy'); phaseRef.current = 'busy';
      animateInvalidSwapBounce(r1, c1, r2, c2, () => {
        if (!mountedRef.current) return;
        if (turnRef.current === 'monster') {
          turnRef.current = 'player'; setTurn('player');
        }
        setPhase('idle'); phaseRef.current = 'idle';
      });
      return;
    }
    setPhase('busy'); phaseRef.current = 'busy';
    setBoard(nb); processMatches(nb, 0);
  }, [animateInvalidSwapBounce, processMatches]);

  const doDirectSwapRef = useRef(doDirectSwap);
  useEffect(() => { doDirectSwapRef.current = doDirectSwap; }, [doDirectSwap]);

  // ── Monster AI loop: monster plays on the board during its turn ────────────
  // Dùng state aiStep để hiển thị monster đang "click" trên bàn cờ
  // step 0: thinking... → step 1: click gem1 (focus+arrows) → step 2: click gem2 → swap
  const [aiStep, setAiStep] = useState<'think' | 'pick1' | 'pick2' | null>(null);
  const aiTimers = useRef<ReturnType<typeof setTimeout>[]>([]);

  // Cleanup AI timers on unmount
  useEffect(() => () => { aiTimers.current.forEach(clearTimeout); }, []);

  const clearTurnTimer = useCallback(() => {
    if (turnTimerRef.current !== null) {
      clearInterval(turnTimerRef.current);
      turnTimerRef.current = null;
    }
  }, []);

  const pickRandomValidMove = useCallback((b: Board): MoveSpec | null => {
    const valid = getAllValidMoves(b);
    if (valid.length === 0) return null;
    return valid[Math.floor(Math.random() * valid.length)] ?? null;
  }, []);

  const runAutoPlayerMove = useCallback((move: MoveSpec) => {
    aiTimers.current.forEach(clearTimeout);
    aiTimers.current = [];
    playerHintShownRef.current = true;
    setHintCell(null);
    setHintMove(null);
    setAiStep(null);
    setSelected([move.r1, move.c1]);

    const t1 = setTimeout(() => {
      if (!mountedRef.current || phaseRef.current !== 'idle' || turnRef.current !== 'player') return;
      setSelected([move.r2, move.c2]);

      const t2 = setTimeout(() => {
        if (!mountedRef.current || phaseRef.current !== 'idle' || turnRef.current !== 'player') return;
        setSelected(null);
        doDirectSwapRef.current(move.r1, move.c1, move.r2, move.c2);
      }, 350);
      aiTimers.current.push(t2);
    }, 350);
    aiTimers.current.push(t1);
  }, []);

  const handleTurnTimeout = useCallback(() => {
    if (!mountedRef.current || phaseRef.current !== 'idle' || result !== null) return;

    clearTurnTimer();
    aiTimers.current.forEach(clearTimeout);
    aiTimers.current = [];
    setAiStep(null);
    setSelected(null);
    setHintCell(null);

    if (turnRef.current === 'player') {
      const move = hintMoveRef.current ?? pickRandomValidMove(boardRef.current);
      if (move) {
        setTurnTimeLeft(TURN_TIME_LIMIT_SEC);
        runAutoPlayerMove(move);
        return;
      }
    }

    const who = turnRef.current === 'player' ? 'Bạn' : 'Quái';
    if (extraTurnsRef.current > 0) {
      const remaining = extraTurnsRef.current - 1;
      extraTurnsRef.current = remaining;
      setExtraTurns(remaining);
      showBonusBanner(`⏳ ${who} hết giờ! -1 lượt thưởng`);
      setTurnTimeLeft(TURN_TIME_LIMIT_SEC);
      setTurnCycle(v => v + 1);
      return;
    }

    const nextTurn = turnRef.current === 'player' ? 'monster' : 'player';
    turnRef.current = nextTurn;
    setTurn(nextTurn);
    setTurnTimeLeft(TURN_TIME_LIMIT_SEC);
    showBonusBanner(`⏳ ${who} hết giờ, đổi lượt!`);
  }, [clearTurnTimer, pickRandomValidMove, result, runAutoPlayerMove, showBonusBanner]);

  useEffect(() => {
    if (turn === 'player' && phase === 'idle' && result === null) {
      playerHintShownRef.current = false;
      setHintCell(null);
      setHintMove(null);
      return;
    }

    setHintCell(null);
    setHintMove(null);
  }, [turn, phase, result, turnCycle]);

  useEffect(() => {
    clearTurnTimer();

    if (phase !== 'idle' || result !== null) {
      if (result !== null) setTurnTimeLeft(TURN_TIME_LIMIT_SEC);
      return;
    }

    const turnKey = `${turn}-${turnCycle}`;
    const now = Date.now();
    if (turnDeadlineKeyRef.current !== turnKey) {
      turnDeadlineKeyRef.current = turnKey;
      turnDeadlineRef.current = now + TURN_TIME_LIMIT_SEC * 1000;
      setTurnTimeLeft(TURN_TIME_LIMIT_SEC);
    } else {
      const remainingMs = turnDeadlineRef.current - now;
      const next = Math.max(0, Math.ceil(remainingMs / 1000));
      setTurnTimeLeft(next);
      if (remainingMs <= 0) {
        handleTurnTimeout();
        return;
      }
    }

    turnTimerRef.current = setInterval(() => {
      const remainingMs = turnDeadlineRef.current - Date.now();
      const next = Math.max(0, Math.ceil(remainingMs / 1000));
      if (mountedRef.current) setTurnTimeLeft(prev => (prev === next ? prev : next));
      if (
        turnRef.current === 'player' &&
        !playerHintShownRef.current &&
        selectedRef.current === null &&
        remainingMs <= 20000 &&
        remainingMs > 0
      ) {
        const move = pickRandomValidMove(boardRef.current);
        if (move && mountedRef.current) {
          const pickFirst = Math.random() > 0.5;
          setHintCell(pickFirst ? [move.r1, move.c1] : [move.r2, move.c2]);
          setHintMove(move);
          playerHintShownRef.current = true;
        }
      }
      if (remainingMs <= 0) handleTurnTimeout();
    }, 250);

    return clearTurnTimer;
  }, [phase, turn, result, turnCycle, clearTurnTimer, handleTurnTimeout, pickRandomValidMove]);

  useEffect(() => {
    // Monster AI triggers only when it's monster's turn, phase is idle, and AI level is set
    if (phase !== 'idle' || turn !== 'monster' || aiLevel === null || result !== null) return;
    const cfg = AI_CONFIGS[aiLevel];

    // Clear old timers
    aiTimers.current.forEach(clearTimeout);
    aiTimers.current = [];

    // Step 0: Monster "thinking" — chờ thinkMs
    setHintCell(null);
    setHintMove(null);
    setAiStep('think');
    setSelected(null);

    const t1 = setTimeout(() => {
      if (!mountedRef.current || phaseRef.current !== 'idle' || turnRef.current !== 'monster') return;
      const b  = boardRef.current;
      // Monster AI: eHP & pHP swapped — monster wants to maximize its own benefit
      const mv = pickAIMove(b, aiLevel, playerHPRef.current, enemyHPRef.current);
      if (!mv) {
        // No valid moves — skip monster turn
        setAiStep(null);
        turnRef.current = 'player'; setTurn('player');
        return;
      }

      // Step 1: Monster "click" gem1 — hiện focus + mũi tên trên gem1
      setAiStep('pick1');
      setSelected([mv.r1, mv.c1]);

      const t2 = setTimeout(() => {
        if (!mountedRef.current) return;
        // Step 2: Monster "click" gem2 — focus chuyển sang gem2
        setAiStep('pick2');
        setSelected([mv.r2, mv.c2]);

        const t3 = setTimeout(() => {
          if (!mountedRef.current) return;
          // Execute swap
          setAiStep(null);
          setSelected(null);
          doDirectSwapRef.current(mv.r1, mv.c1, mv.r2, mv.c2);
        }, 350);
        aiTimers.current.push(t3);
      }, 400);
      aiTimers.current.push(t2);
    }, cfg.thinkMs);
    aiTimers.current.push(t1);

    return () => { aiTimers.current.forEach(clearTimeout); aiTimers.current = []; };
  }, [phase, turn, aiLevel, result, turnCycle]);

  // ── Human tap ─────────────────────────────────────────────────────────────
  const handleGemPress = useCallback((row: number, col: number) => {
    if (phase !== 'idle' || turn !== 'player') return; // Chỉ cho tap khi lượt player
    setHintCell(null);
    setHintMove(null);
    if (!selected) { setSelected([row, col]); return; }
    const [sr, sc] = selected;
    if (sr === row && sc === col) { setSelected(null); return; }
    const adj = (Math.abs(sr - row) === 1 && sc === col) ||
                (sr === row && Math.abs(sc - col) === 1);
    if (!adj) { setSelected([row, col]); return; }
    setSelected(null);
    doDirectSwapRef.current(sr, sc, row, col);
  }, [selected, phase, turn]);

  // ── Skill ──────────────────────────────────────────────────────────────────
  const handleSkill = useCallback(() => {
    if (mana < 30 || phase !== 'idle' || turn !== 'player') return;
    setHintCell(null);
    setHintMove(null);
    setMana(m => m - 30);
    const dmg = 40 + Math.floor(Math.random() * 20);
    showDamagePopup('enemy', dmg);
    setEnemyHP(hp => {
      const next = Math.max(0, hp - dmg);
      if (next === 0 && phaseRef.current !== 'over') {
        phaseRef.current = 'over'; setPhase('over'); setResult('victory');
      }
      return next;
    });
    // Skill also ends player turn → switch to monster
    turnRef.current = 'monster'; setTurn('monster');
  }, [mana, phase, showDamagePopup, turn]);

  // ── Layout ─────────────────────────────────────────────────────────────────
  const { panelLeft, panelTop, charsTop, damagePopupTop, monsterSize } = getBattleStageLayout(monsterType);
  const playerHud = PLAYER_HUD_LAYOUT;
  const enemyHud = ENEMY_HUD_LAYOUT;
  const { w: mW, h: mH } = monsterSize;
  const resultMeta = result ? RESULT_ART_META[result] : null;
  const resultArtScale = resultArtAnim.interpolate({
    inputRange: [0, 1],
    outputRange: [0.72, 1],
  });
  const resultArtTilt = resultArtAnim.interpolate({
    inputRange: [0, 1],
    outputRange: ['-8deg', '-5deg'],
  });
  const resultArtLift = resultArtAnim.interpolate({
    inputRange: [0, 1],
    outputRange: [14, 0],
  });
  const hasBoardFocus = selected !== null || hintCell !== null;
  const playerDamageLeft = panelLeft + 4;
  const enemyDamageLeft = panelLeft + BG_W - 84;

  return (
    <View style={s.root}>

      {/* ── Battle Panel: relative frame for bkboardv + everything inside it ── */}
      <View style={{
        position: 'absolute',
        top: panelTop,
        left: panelLeft,
        width: BG_W,
        height: BG_H,
      }}>
        <Image
          source={BATTLE_ASSETS.boardFrame}
          style={{ position: 'absolute', top: 0, left: 0, width: '100%', height: '100%' }}
          resizeMode="stretch"
        />

      {/* ── Player bars ──────────────────────────────────────── */}
        <View style={{
          position: 'absolute', top: HUD_BASE_Y + playerHud.shiftY,
          left: playerHud.offsetX, width: playerHud.boxW, height: playerHud.boxH, zIndex: 20,
        }}>
          <View style={{ position: 'absolute', top: playerHud.hp.y, left: playerHud.hp.x }}>
            <AnimatedTBar asset={BATTLE_ASSETS.hpBar} fillAnim={playerHPBarAnim} max={maxHP} w={playerHud.hp.w} h={playerHud.hp.h} direction="ltr" />
          </View>
          <View style={{ position: 'absolute', top: playerHud.mp.y, left: playerHud.mp.x }}>
            <TBar asset={BATTLE_ASSETS.manaBar} fill={mana / maxMP} w={playerHud.mp.w} h={playerHud.mp.h} direction="ltr" />
          </View>
          <Animated.View style={{ position: 'absolute', top: playerHud.power.y, left: playerHud.power.x, opacity: powerBlinkAnim }}>
            <TBar asset={BATTLE_ASSETS.powerBar} fill={power / maxPow} w={playerHud.power.w} h={playerHud.power.h} direction="ltr" />
          </Animated.View>
        </View>

        {/* ── Enemy bars ───────────────────────────────────────── */}
        <View style={{
          position: 'absolute', top: HUD_BASE_Y + enemyHud.shiftY,
          right: enemyHud.offsetX, width: enemyHud.boxW, height: enemyHud.boxH, zIndex: 20,
        }}>
          <View style={{ position: 'absolute', top: enemyHud.hp.y, right: enemyHud.hp.x }}>
            <AnimatedTBar asset={BATTLE_ASSETS.hpBar} fillAnim={enemyHPBarAnim} max={maxEHP} w={enemyHud.hp.w} h={enemyHud.hp.h} direction="rtl" />
          </View>
          <View style={{ position: 'absolute', top: enemyHud.mp.y, right: enemyHud.mp.x }}>
            <TBar asset={BATTLE_ASSETS.manaBar} fill={0} w={enemyHud.mp.w} h={enemyHud.mp.h} direction="rtl" />
          </View>
          <View style={{ position: 'absolute', top: enemyHud.power.y, right: enemyHud.power.x }}>
            <TBar asset={BATTLE_ASSETS.powerBar} fill={0} w={enemyHud.power.w} h={enemyHud.power.h} direction="rtl" />
          </View>
        </View>

        {/* ── Gem board ────────────────────────────────────────── */}
        <Animated.View style={[s.gemBoard, {
          top:    BOARD_TOP,
          left:   BOARD_LEFT,
          width:  GEM_SIZE * BOARD_COLS,
          height: GEM_SIZE * BOARD_ROWS,
          zIndex: hasBoardFocus ? 30 : 10,
        }]}>
          {board.map((row, r) =>
            row.map((gemType, c) =>
              gemType !== null ? (
                <Animated.View
                  key={`${r}-${c}`}
                  style={{
                    position: 'absolute',
                    left: c * GEM_SIZE, top: r * GEM_SIZE,
                    width: GEM_SIZE,    height: GEM_SIZE,
                    zIndex:
                      (selected?.[0] === r && selected?.[1] === c) ||
                      (hintCell?.[0] === r && hintCell?.[1] === c)
                        ? 120
                        : 1,
                    transform: [
                      { translateX: swapOffsetsX[r][c] },
                      { translateY: swapOffsetsY[r][c] },
                      { translateY: offsets[r][c] },
                    ],
                  }}
                >
                  <GemCell
                    gemType={gemType}
                    frameIndex={explodeFrames[`${r},${c}`] ?? 0}
                    size={GEM_SIZE}
                    selected={
                      (selected?.[0] === r && selected?.[1] === c) ||
                      (hintCell?.[0] === r && hintCell?.[1] === c)
                    }
                    focusVariant={turn === 'monster' ? 'enemy' : 'player'}
                    onPress={() => handleGemPress(r, c)}
                  />
                </Animated.View>
              ) : null
            )
          )}

          {/* ── Match particle effects overlay ──────────────────── */}
          {matchFX.map(fx => {
            const opacity = fx.anim.interpolate({
              inputRange: [0, 0.12, 0.75, 1],
              outputRange: [0, 1, 0.9, 0],
            });
            const scale = fx.anim.interpolate({
              inputRange: [0, 0.2, 0.55, 1],
              outputRange: [0.15, 0.95, fx.kind === 'sword' ? 1.42 : 1.18, 0.52],
            });
            const translateY = fx.anim.interpolate({
              inputRange: [0, 1],
              outputRange: [0, fx.driftY],
            });
            const translateX = fx.anim.interpolate({
              inputRange: [0, 1],
              outputRange: [0, fx.driftX],
            });
            const cx = fx.c * GEM_SIZE + GEM_SIZE / 2 - fx.size / 2 + fx.startOffsetX;
            const cy = fx.r * GEM_SIZE + GEM_SIZE / 2 - fx.size / 2 + fx.startOffsetY;

            return (
              <Animated.Image
                key={fx.key}
                source={fx.source}
                resizeMode="contain"
                style={{
                  position: 'absolute',
                  left: cx, top: cy,
                  width: fx.size, height: fx.size,
                  opacity,
                  transform: [{ translateX }, { translateY }, { scale }, { rotate: fx.rotate }],
                }}
              />
            );
          })}
        </Animated.View>

        {/* ── Extra turns badge (persistent, on board center) ──── */}
        {extraTurns > 0 && showExtraTurnsBadge && (
          <Animated.View style={{
            position: 'absolute',
            top: BOARD_TOP + GEM_SIZE * BOARD_ROWS / 2 - 14 + EXTRA_TURNS_SHIFT_Y * BOARD_SCALE,
            left: BOARD_LEFT + GEM_SIZE * BOARD_COLS / 2 - 55 + EXTRA_TURNS_SHIFT_X * BOARD_SCALE,
            width: 110, height: 20,
            justifyContent: 'center', alignItems: 'center',
            zIndex: 50,
            opacity: extraTurnsBadgeAnim,
          }}>
            <Text style={{ color: extraTurns === 1 ? '#fff' : '#111', fontSize: 12, fontWeight: 'bold' }}>
              Còn {extraTurns} lượt
            </Text>
          </Animated.View>
        )}

        <View style={{
          position: 'absolute',
          top: TURN_TIMER_TOP,
          left: 0,
          right: 0,
          zIndex: 20,
          alignItems: 'center',
          transform: [{ translateX: TURN_TIMER_SHIFT_X * BOARD_SCALE }],
        }}>
          <Text style={{
            color: '#dff6ff',
            fontSize: 10 * BOARD_SCALE,
            fontWeight: 'bold',
            textShadowColor: '#000',
            textShadowOffset: { width: 1, height: 1 },
            textShadowRadius: 2,
          }}>
            {turnTimeLeft}s
          </Text>
        </View>
      </View>

      {/* ── Characters ───────────────────────────────────────── */}
      <View style={[s.charsRow, {
        top: charsTop,
        left: panelLeft + 20 + CHARS_ROW_SHIFT_X * BOARD_SCALE,
        width: BG_W - 40,
      }]}>
        <Animated.View style={{
          transform: [
            {
              scale: playerCollectAnim.interpolate({
                inputRange: [0, 1],
                outputRange: [1, 1.08],
              }),
            },
          ],
        }}>
          <Image source={BATTLE_ASSETS.playerSprite}
            style={[s.playerSprite, {
              transform: [
                { translateX: PLAYER_SPRITE_SHIFT_X * BOARD_SCALE },
                { translateY: PLAYER_SPRITE_SHIFT_Y * BOARD_SCALE },
              ],
            }]} resizeMode="contain" />
        </Animated.View>
        <View style={{ flex: 1 }} />
        <Animated.View style={{
          width: mW,
          height: mH,
          alignSelf: 'flex-end',
          transform: [
            {
              scale: enemyCollectAnim.interpolate({
                inputRange: [0, 1],
                outputRange: [1, 1.08],
              }),
            },
            { translateX: ENEMY_SPRITE_SHIFT_X * BOARD_SCALE },
            { translateY: ENEMY_SPRITE_SHIFT_Y * BOARD_SCALE },
          ],
        }}>
          <MonsterSprite type={monsterType} frameIndex={monFrame} facingRight={false} />
        </Animated.View>
      </View>

      {damagePopups.map(item => {
        const opacity = item.anim.interpolate({
          inputRange: [0, 0.12, 0.85, 1],
          outputRange: [0, 1, 1, 0],
        });
        const translateY = item.anim.interpolate({
          inputRange: [0, 1],
          outputRange: [10, -18],
        });
        const scale = item.anim.interpolate({
          inputRange: [0, 0.2, 1],
          outputRange: [0.8, 1.05, 1],
        });

        return (
          <Animated.View
            key={item.key}
            pointerEvents="none"
            style={{
              position: 'absolute',
              top: damagePopupTop,
              left: item.side === 'player' ? playerDamageLeft : enemyDamageLeft,
              width: 80,
              alignItems: 'center',
              opacity,
              transform: [{ translateY }, { scale }],
              zIndex: 40,
            }}
          >
            <Text style={{
              color: '#ff4db8',
              fontSize: 14,
              textShadowColor: '#580026',
              textShadowOffset: { width: 1, height: 1 },
              textShadowRadius: 2,
            }}>
              -{item.amount}
            </Text>
          </Animated.View>
        );
      })}

      {gainPopups.map(item => {
        const opacity = item.anim.interpolate({
          inputRange: [0, 0.12, 0.85, 1],
          outputRange: [0, 1, 1, 0],
        });
        const translateY = item.anim.interpolate({
          inputRange: [0, 1],
          outputRange: [10, -14],
        });

        return (
          <Animated.View
            key={item.key}
            pointerEvents="none"
            style={{
              position: 'absolute',
              top: panelTop + HUD_BASE_Y - 18 * BOARD_SCALE,
              left: item.side === 'player'
                ? panelLeft + playerHud.offsetX + playerHud.hp.x + 8
                : panelLeft + BG_W - enemyHud.offsetX - enemyHud.hp.x - enemyHud.hp.w + 8,
              opacity,
              transform: [{ translateY }],
              zIndex: 42,
            }}
          >
            <Text style={{
              color: '#ffeef7',
              fontSize: 12,
              textShadowColor: '#8a295e',
              textShadowOffset: { width: 1, height: 1 },
              textShadowRadius: 2,
            }}>
              {item.text}
            </Text>
          </Animated.View>
        );
      })}

      {collectFX.map(item => {
        const translateX = item.anim.interpolate({
          inputRange: [0, 0.24, 0.68, 1],
          outputRange: [0, item.curve1X - item.startX, item.curve2X - item.startX, item.endX - item.startX],
        });
        const translateY = item.anim.interpolate({
          inputRange: [0, 0.24, 0.68, 1],
          outputRange: [0, item.curve1Y - item.startY, item.curve2Y - item.startY, item.endY - item.startY],
        });
        const scale = item.anim.interpolate({
          inputRange: [0, 0.1, 0.34, 0.76, 1],
          outputRange: [0.18, 1.18, 1.02, 0.92, item.endScale],
        });
        const opacity = item.anim.interpolate({
          inputRange: [0, 0.06, 0.32, item.fadeOutAt, 1],
          outputRange: [0, 1, 1, 0.96, 0],
        });
        const glowOpacity = item.anim.interpolate({
          inputRange: [0, 0.05, 0.28, item.fadeOutAt, 1],
          outputRange: [0, item.glowOpacity, item.glowOpacity, item.glowOpacity * 0.92, 0],
        });
        const cropScale = item.renderW / item.cropWidth;
        const spriteW = 45 * cropScale;
        const spriteH = 15 * cropScale;
        const spriteOffsetX = -item.cropLeft * cropScale;

        return (
          <View
            key={item.key}
            pointerEvents="none"
            style={{
              position: 'absolute',
              left: item.startX,
              top: item.startY,
              width: item.renderW,
              height: item.renderH,
              overflow: 'visible',
              zIndex: 41,
            }}
          >
            {item.isCrystal ? (
              <>
                <Animated.View
                  style={{
                    position: 'absolute',
                    left: 0,
                    top: 0,
                    width: item.renderW,
                    height: item.renderH,
                    overflow: 'hidden',
                    opacity: glowOpacity,
                    transform: [{ translateX }, { translateY }, { scale: Animated.multiply(scale, item.glowScale) }],
                  }}
                >
                  <Image
                    source={item.source}
                    resizeMode="stretch"
                    style={{
                      position: 'absolute',
                      left: spriteOffsetX,
                      top: 0,
                      width: spriteW,
                      height: spriteH,
                    }}
                  />
                </Animated.View>
                <Animated.View
                  style={{
                    position: 'absolute',
                    left: 0,
                    top: 0,
                    width: item.renderW,
                    height: item.renderH,
                    overflow: 'hidden',
                    opacity,
                    transform: [{ translateX }, { translateY }, { scale }],
                  }}
                >
                  <Image
                    source={item.source}
                    resizeMode="stretch"
                    style={{
                      position: 'absolute',
                      left: spriteOffsetX,
                      top: 0,
                      width: spriteW,
                      height: spriteH,
                    }}
                  />
                </Animated.View>
              </>
            ) : (
              <>
                <Animated.Image
                  source={item.source}
                  resizeMode="contain"
                  style={{
                    position: 'absolute',
                    left: 0,
                    top: 0,
                    width: item.size,
                    height: item.size,
                    opacity: glowOpacity,
                    transform: [{ translateX }, { translateY }, { scale: Animated.multiply(scale, item.glowScale) }],
                  }}
                />
                <Animated.Image
                  source={item.source}
                  resizeMode="contain"
                  style={{
                    position: 'absolute',
                    left: 0,
                    top: 0,
                    width: item.size,
                    height: item.size,
                    opacity,
                    transform: [{ translateX }, { translateY }, { scale }],
                  }}
                />
              </>
            )}
          </View>
        );
      })}

      {/* ── Overlay ──────────────────────────────────────────── */}
      {result !== null && resultMeta !== null && (
        <TouchableOpacity
          activeOpacity={1}
          style={s.overlay}
          onPress={result === 'victory' ? onVictory : onDefeat}
        >
          <Animated.View
            style={[
              s.resultBannerStage,
              {
                top: panelTop + BOARD_TOP + GEM_SIZE * BOARD_ROWS / 2 - 34 * BOARD_SCALE,
                opacity: resultArtAnim,
                transform: [
                  { translateY: resultArtLift },
                  { scale: resultArtScale },
                  { rotate: resultArtTilt },
                ],
              },
            ]}
          >
            <View
              style={{
                width: resultMeta.frameWidth * BOARD_SCALE,
                height: resultMeta.frameHeight * BOARD_SCALE,
                overflow: 'hidden',
              }}
            >
              <Image
                source={resultMeta.asset}
                resizeMode="stretch"
                style={{
                  width: resultMeta.sheetWidth * BOARD_SCALE,
                  height: resultMeta.sheetHeight * BOARD_SCALE,
                  transform: [{ translateX: -RESULT_ART_INDEX * resultMeta.frameWidth * BOARD_SCALE }],
                }}
              />
            </View>
          </Animated.View>
        </TouchableOpacity>
      )}
    </View>
  );
};
