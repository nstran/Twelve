import React, { useState, useEffect, useCallback, useRef } from 'react';
import {
  View, Image, Text, TouchableOpacity,
  Animated, Easing,
} from 'react-native';
import {
  MonsterSprite, MonsterType, WALK_FRAMES, ATTACK_FRAMES, monsterDisplaySize,
} from '../../engine/MonsterSprite';
import { GemCell, TBar } from './BattleScreen.components';
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
  AI_ORDER,
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
  RED_SWORD_GEM,
  SWORD_CAT,
} from './BattleScreen.shared';
import type {
  AILevel,
  Board,
  FallEntry,
  FXKind,
  GemType,
} from './BattleScreen.shared';
import {
  s,
  SCREEN_W, SCREEN_H,
  BG_W, BG_H, BOARD_SCALE,
  BOARD_COLS, BOARD_ROWS, GEM_SIZE,
  BOARD_LEFT_OFFSET, BOARD_TOP_OFFSET,
} from './BattleScreen.styles';
interface Props {
  monsterType: MonsterType;
  onVictory:  () => void;
  onDefeat:   () => void;
  onFlee:     () => void;
}

const TURN_TIME_LIMIT_SEC = 30;
const RESULT_ART_INDEX = 1;
const RESULT_ART_META = {
  victory: {
    asset: require('../../../assets/strwin.png'),
    frameWidth: 172,
    frameHeight: 65,
    sheetWidth: 516,
    sheetHeight: 65,
  },
  defeat: {
    asset: require('../../../assets/strlose.png'),
    frameWidth: 146,
    frameHeight: 56,
    sheetWidth: 438,
    sheetHeight: 56,
  },
} as const;

export const BattleScreen: React.FC<Props> = ({
  monsterType, onVictory, onDefeat, onFlee,
}) => {
  const maxHP  = 100;
  const maxEHP = MONSTER_HP[monsterType] ?? 150;
  const maxMP  = 100;
  const maxPow = 100;

  const [board,         setBoard]         = useState<Board>(makeBoard);
  const [selected,      setSelected]      = useState<[number, number] | null>(null);
  const [explodeFrames, setExplodeFrames] = useState<Record<string, number>>({});
  const [playerHP,  setPlayerHP]  = useState(maxHP);
  const [enemyHP,   setEnemyHP]   = useState(maxEHP);
  const [mana,      setMana]      = useState(30);
  const [power,     setPower]     = useState(40);
  const [log,       setLog]       = useState('Trận đấu bắt đầu!');
  const [combo,     setCombo]     = useState(0);
  const [phase,     setPhase]     = useState<'idle' | 'busy' | 'over'>('idle');
  const [result,    setResult]    = useState<'victory' | 'defeat' | null>(null);
  const [monFrame,  setMonFrame]  = useState<number>(WALK_FRAMES[0]);
  const [monAtk,    setMonAtk]   = useState(false);
  const [aiLevel,   setAiLevel]  = useState<AILevel | null>('linh_canh');

  // ── Turn-based system ──────────────────────────────────────────────────────
  const [turn, setTurn] = useState<'player' | 'monster'>('player');
  const turnRef = useRef<'player' | 'monster'>('player');

  // ── Extra turns: match 4+ → bonus lượt ────────────────────────────────────
  const [extraTurns, setExtraTurns] = useState(0);
  const extraTurnsRef = useRef(0);
  const [bonusBanner, setBonusBanner] = useState<string | null>(null);
  const bonusBannerAnim = useRef(new Animated.Value(0)).current;
  const [turnTimeLeft, setTurnTimeLeft] = useState(TURN_TIME_LIMIT_SEC);
  const [turnCycle, setTurnCycle] = useState(0);

  // Match particle effects
  interface MatchFXItem { key: string; r: number; c: number; kind: FXKind; anim: Animated.Value }
  const [matchFX, setMatchFX] = useState<MatchFXItem[]>([]);
  const fxKeyRef = useRef(0);

  const shakeAnim  = useRef(new Animated.Value(0)).current;
  const resultArtAnim = useRef(new Animated.Value(0)).current;
  const powerBlinkAnim = useRef(new Animated.Value(1)).current;
  const powerBlinkLoopRef = useRef<Animated.CompositeAnimation | null>(null);
  const phaseRef   = useRef<'idle' | 'busy' | 'over'>('idle');
  const mountedRef = useRef(true);
  const boardRef   = useRef<Board>(board);
  const enemyHPRef = useRef(enemyHP);
  const playerHPRef= useRef(playerHP);
  const turnTimerRef = useRef<ReturnType<typeof setInterval> | null>(null);
  const turnDeadlineRef = useRef(0);

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
  useEffect(() => { turnRef.current = turn; }, [turn]);
  useEffect(() => { extraTurnsRef.current = extraTurns; }, [extraTurns]);
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

    matched.forEach(k => {
      const [r, c] = k.split(',').map(Number);
      const g = b[r][c];
      if (g === null) return;
      const kind = GEM_FX_KIND[g];
      // For non-sword gems, spawn individual particles
      if (kind !== 'sword') {
        const anim = new Animated.Value(0);
        items.push({ key: `fx-${++fxKeyRef.current}`, r, c, kind, anim });
      }
    });

    // Sword explosion: spawn aura2/aura3 at center of sword matches
    if (hasSwordExpansion) {
      // Find sword cells in original match
      const swordCells: Array<[number, number]> = [];
      matched.forEach(k => {
        const [r, c] = k.split(',').map(Number);
        const g = b[r][c];
        if (g !== null && GEM_CATEGORY[g] === SWORD_CAT) swordCells.push([r, c]);
      });
      swordCells.forEach(([r, c]) => {
        const anim = new Animated.Value(0);
        items.push({ key: `fx-${++fxKeyRef.current}`, r, c, kind: 'sword', anim });
      });
    }

    if (items.length === 0) return;
    setMatchFX(prev => [...prev, ...items]);

    // Animate: 0→1 over 500ms (scale up + float up + fade out)
    Animated.parallel(
      items.map(fx => Animated.timing(fx.anim, {
        toValue: 1, duration: 550, easing: Easing.out(Easing.quad), useNativeDriver: true,
      }))
    ).start(() => {
      if (!mountedRef.current) return;
      // Remove finished particles
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
      Animated.timing(bonusBannerAnim, { toValue: 1, duration: 250, useNativeDriver: true }),
      Animated.delay(800),
      Animated.timing(bonusBannerAnim, { toValue: 0, duration: 300, useNativeDriver: true }),
    ]).start(() => { if (mountedRef.current) setBonusBanner(null); });
  }, [bonusBannerAnim]);

  // ── Chain processor ────────────────────────────────────────────────────────
  const processMatches = useCallback((b: Board, chain: number) => {
    if (!mountedRef.current) return;
    const raw = findMatches(b);
    if (raw.size === 0) {
      setBoard(b); setCombo(0);

      // ── Check deadlock: không còn nước đi → reset board ──
      if (getAllValidMoves(b).length === 0) {
        setLog('🔀 Hết nước đi! Xáo trộn bàn cờ...');
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
        setLog(`🔄 Thêm lượt! ${remaining > 0 ? `(còn ${remaining} lượt nữa)` : '(lượt cuối)'}`);
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
    if (matched.size > 4 || chain > 0) {
      Animated.sequence([
        Animated.timing(shakeAnim, { toValue:  7, duration: 35, useNativeDriver: true }),
        Animated.timing(shakeAnim, { toValue: -7, duration: 35, useNativeDriver: true }),
        Animated.timing(shakeAnim, { toValue:  0, duration: 35, useNativeDriver: true }),
      ]).start();
    }

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
    const hasRedBlast = [...matched].some(k => {
      const [r, c] = k.split(',').map(Number);
      return b[r][c] === RED_SWORD_GEM;
    }) && matched.size > raw.size;

    playExplosion(raw, matched, b, () => {
      if (!mountedRef.current) return;
      const { newBoard, fallMap } = collapseLogic(b, matched);

      // ── Turn-based effects: player turn → hurt monster, monster turn → hurt player ──
      if (turnRef.current === 'player') {
        // Player's turn: damage to enemy, heal/mp/pow to player
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

      const isMonTurn = turnRef.current === 'monster';
      const parts: string[] = [];
      if (hasRedBlast) parts.push('💥 Kiếm đỏ nổ dây chuyền!');
      if (dmg > 0)  parts.push(isMonTurn ? `🐉 Quái đánh -${dmg} HP` : `⚔ -${dmg}`);
      if (heal > 0) parts.push(isMonTurn ? `🐉 Quái hồi +${heal} HP` : `❤ +${heal}`);
      setLog((chain > 0 ? `COMBO ×${chain + 1}!  ` : '') + parts.join('  '));
      setCombo(chain + 1);

      animateFall(newBoard, fallMap, () => {
        setTimeout(() => processMatches(newBoard, chain + 1), 80);
      });
    });
  }, [playExplosion, animateFall, resetBoardAnim, shakeAnim, showBonusBanner, maxHP, maxEHP, maxMP, maxPow]);

  // ── Direct swap (dùng cho cả AI và human) ─────────────────────────────────
  const doDirectSwap = useCallback((r1: number, c1: number, r2: number, c2: number) => {
    const b = boardRef.current;
    const nb: Board = b.map(r => [...r]);
    [nb[r1][c1], nb[r2][c2]] = [nb[r2][c2], nb[r1][c1]];
    if (findMatches(nb).size === 0) {
      setPhase('busy'); phaseRef.current = 'busy';
      animateInvalidSwapBounce(r1, c1, r2, c2, () => {
        if (!mountedRef.current) return;
        setCombo(0);
        if (turnRef.current === 'monster') {
          turnRef.current = 'player'; setTurn('player');
          setLog('🐉 Quái đổi chỗ nhưng không ghép được, quân cờ bật lại.');
        } else {
          setLog('↩ Không tạo được match, quân cờ trở về vị trí cũ.');
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

  const handleTurnTimeout = useCallback(() => {
    if (!mountedRef.current || phaseRef.current !== 'idle' || result !== null) return;

    clearTurnTimer();
    aiTimers.current.forEach(clearTimeout);
    aiTimers.current = [];
    setAiStep(null);
    setSelected(null);

    const who = turnRef.current === 'player' ? 'Bạn' : 'Quái';
    if (extraTurnsRef.current > 0) {
      const remaining = extraTurnsRef.current - 1;
      extraTurnsRef.current = remaining;
      setExtraTurns(remaining);
      setLog(`⏳ ${who} hết 30 giây, mất 1 lượt thưởng.`);
      showBonusBanner(`⏳ ${who} hết giờ! -1 lượt thưởng`);
      setTurnTimeLeft(TURN_TIME_LIMIT_SEC);
      setTurnCycle(v => v + 1);
      return;
    }

    const nextTurn = turnRef.current === 'player' ? 'monster' : 'player';
    turnRef.current = nextTurn;
    setTurn(nextTurn);
    setTurnTimeLeft(TURN_TIME_LIMIT_SEC);
    setLog(`⏳ ${who} hết 30 giây, mất lượt.`);
    showBonusBanner(`⏳ ${who} hết giờ, đổi lượt!`);
  }, [clearTurnTimer, result, showBonusBanner]);

  useEffect(() => {
    clearTurnTimer();

    if (phase !== 'idle' || result !== null) {
      if (result !== null) setTurnTimeLeft(TURN_TIME_LIMIT_SEC);
      return;
    }

    turnDeadlineRef.current = Date.now() + TURN_TIME_LIMIT_SEC * 1000;
    setTurnTimeLeft(TURN_TIME_LIMIT_SEC);
    turnTimerRef.current = setInterval(() => {
      const remainingMs = turnDeadlineRef.current - Date.now();
      const next = Math.max(0, Math.ceil(remainingMs / 1000));
      if (mountedRef.current) setTurnTimeLeft(prev => (prev === next ? prev : next));
      if (remainingMs <= 0) handleTurnTimeout();
    }, 250);

    return clearTurnTimer;
  }, [phase, turn, result, turnCycle, clearTurnTimer, handleTurnTimeout]);

  useEffect(() => {
    // Monster AI triggers only when it's monster's turn, phase is idle, and AI level is set
    if (phase !== 'idle' || turn !== 'monster' || aiLevel === null || result !== null) return;
    const cfg = AI_CONFIGS[aiLevel];

    // Clear old timers
    aiTimers.current.forEach(clearTimeout);
    aiTimers.current = [];

    // Step 0: Monster "thinking" — chờ thinkMs
    setAiStep('think');
    setSelected(null);
    setLog(`🐉 Lượt quái vật — ${cfg.emoji} ${cfg.name} đang suy nghĩ...`);

    const t1 = setTimeout(() => {
      if (!mountedRef.current || phaseRef.current !== 'idle' || turnRef.current !== 'monster') return;
      const b  = boardRef.current;
      // Monster AI: eHP & pHP swapped — monster wants to maximize its own benefit
      const mv = pickAIMove(b, aiLevel, playerHPRef.current, enemyHPRef.current);
      if (!mv) {
        // No valid moves — skip monster turn
        setAiStep(null);
        turnRef.current = 'player'; setTurn('player');
        setLog('🐉 Quái không tìm được nước đi!');
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
    setMana(m => m - 30);
    const dmg = 40 + Math.floor(Math.random() * 20);
    setEnemyHP(hp => {
      const next = Math.max(0, hp - dmg);
      if (next === 0 && phaseRef.current !== 'over') {
        phaseRef.current = 'over'; setPhase('over'); setResult('victory');
      }
      return next;
    });
    setLog(`💫 Kỹ năng! -${dmg} HP quái!`);
    // Skill also ends player turn → switch to monster
    turnRef.current = 'monster'; setTurn('monster');
  }, [mana, phase, turn]);

  // ── Layout ─────────────────────────────────────────────────────────────────
  // Khung battle panel bám theo bkboardv. Các phần bên trong dùng toạ độ local
  // để chỉ cần căn trong một container relative.
  const BATTLE_PANEL_LEFT_SHIFT = 0;
  const BATTLE_PANEL_LEFT = Math.round((SCREEN_W - BG_W) / 2)
    + Math.round(BATTLE_PANEL_LEFT_SHIFT * BOARD_SCALE);
  const BATTLE_PANEL_TOP = Math.round(20 * BOARD_SCALE) + 50;

  // Hai cột thanh chỉ số trái/phải nằm trong 3 rãnh dưới board.
  // Tách riêng offset để về sau có thể căn từng bên độc lập.
  const PLAYER_HUD_LEFT = 8 * BOARD_SCALE;
  const PLAYER_HUD_SHIFT_Y = -1;
  const ENEMY_HUD_RIGHT = 8 * BOARD_SCALE;
  const ENEMY_HUD_SHIFT_Y = -1;

  // HUD_BASE_Y: vị trí bắt đầu stack HP/MP/Nộ.
  // Mỗi thanh có x/y/w/h riêng để chỉnh pixel-perfect từng thanh.
  const HUD_BASE_Y = 231 * BOARD_SCALE;
  const PLAYER_HP_X = 2.6 * BOARD_SCALE;
  const PLAYER_HP_Y = 3 * BOARD_SCALE;
  const PLAYER_HP_W = 74.7 * BOARD_SCALE;
  const PLAYER_HP_H = 5 * BOARD_SCALE;
  const PLAYER_MP_X = 2.6 * BOARD_SCALE;
  const PLAYER_MP_Y = 10.3 * BOARD_SCALE;
  const PLAYER_MP_W = 75 * BOARD_SCALE;
  const PLAYER_MP_H = 5 * BOARD_SCALE;
  const PLAYER_POWER_X = 2 * BOARD_SCALE;
  const PLAYER_POWER_Y = 17.4 * BOARD_SCALE;
  const PLAYER_POWER_W = 74.7 * BOARD_SCALE;
  const PLAYER_POWER_H = 5 * BOARD_SCALE;

  const ENEMY_HP_X = 2.6 * BOARD_SCALE;
  const ENEMY_HP_Y = 3 * BOARD_SCALE;
  const ENEMY_HP_W = 74.7 * BOARD_SCALE;
  const ENEMY_HP_H = 5 * BOARD_SCALE;
  const ENEMY_MP_X = 2.6 * BOARD_SCALE;
  const ENEMY_MP_Y = 10.3 * BOARD_SCALE;
  const ENEMY_MP_W = 74.7 * BOARD_SCALE;
  const ENEMY_MP_H = 5 * BOARD_SCALE;
  const ENEMY_POWER_X = 0 * BOARD_SCALE;
  const ENEMY_POWER_Y = 17.4 * BOARD_SCALE;
  const ENEMY_POWER_W = 74.7 * BOARD_SCALE;
  const ENEMY_POWER_H = 5 * BOARD_SCALE;

  const PLAYER_HUD_BOX_W = Math.max(
    PLAYER_HP_X + PLAYER_HP_W,
    PLAYER_MP_X + PLAYER_MP_W,
    PLAYER_POWER_X + PLAYER_POWER_W,
  );
  const PLAYER_HUD_BOX_H = Math.max(
    PLAYER_HP_Y + PLAYER_HP_H,
    PLAYER_MP_Y + PLAYER_MP_H,
    PLAYER_POWER_Y + PLAYER_POWER_H,
  );
  const ENEMY_HUD_BOX_W = Math.max(
    ENEMY_HP_X + ENEMY_HP_W,
    ENEMY_MP_X + ENEMY_MP_W,
    ENEMY_POWER_X + ENEMY_POWER_W,
  );
  const ENEMY_HUD_BOX_H = Math.max(
    ENEMY_HP_Y + ENEMY_HP_H,
    ENEMY_MP_Y + ENEMY_MP_H,
    ENEMY_POWER_Y + ENEMY_POWER_H,
  );
  const HUD_STACK_H = Math.max(PLAYER_HUD_BOX_H, ENEMY_HUD_BOX_H);

  // TURN_TIMER_TOP: dòng số giây còn lại ở giữa, ngay dưới 2 cụm thanh chỉ số.
  const TURN_TIMER_SHIFT_X = 0;
  const TURN_TIMER_SHIFT_Y = 0;
  const TURN_TIMER_TOP = HUD_BASE_Y + HUD_STACK_H - 20 * BOARD_SCALE + TURN_TIMER_SHIFT_Y * BOARD_SCALE;

  // BOARD_TOP: vị trí thật của lưới gem, dùng chung cho badge thêm lượt và bonus banner.
  const BOARD_SHIFT_X = 0;
  const BOARD_SHIFT_Y = -17;
  const BOARD_LEFT = BOARD_LEFT_OFFSET + BOARD_SHIFT_X * BOARD_SCALE + 7;
  const BOARD_TOP  = BOARD_TOP_OFFSET + BOARD_SHIFT_Y * BOARD_SCALE;

  // LOG_TOP: thanh text log nằm ngay dưới bàn cờ.
  const LOG_SHIFT_X = 0;
  const LOG_SHIFT_Y = 0;
  const LOG_TOP    = BOARD_TOP + GEM_SIZE * BOARD_ROWS + 4 + LOG_SHIFT_Y * BOARD_SCALE;

  // Các cụm phía dưới panel battle.
  // CHARS_TOP: hàng nhân vật/quái, GND_TOP: mặt đất, BTN_TOP: nút hành động,
  // AI_ROW_TOP + AI_LBL_TOP: cụm chọn AI và text báo lượt.
  const CHARS_ROW_SHIFT_X = 0;
  const CHARS_ROW_SHIFT_Y = 0;
  const PLAYER_SPRITE_SHIFT_X = 0;
  const PLAYER_SPRITE_SHIFT_Y = 0;
  const ENEMY_SPRITE_SHIFT_X = 0;
  const ENEMY_SPRITE_SHIFT_Y = 0;
  const GROUND_SHIFT_X = 0;
  const GROUND_SHIFT_Y = 0;
  const BTN_ROW_SHIFT_X = 0;
  const BTN_ROW_SHIFT_Y = 0;
  const AI_ROW_SHIFT_X = 0;
  const AI_ROW_SHIFT_Y = 0;
  const AI_LABEL_SHIFT_X = 0;
  const AI_LABEL_SHIFT_Y = 0;
  const EXTRA_TURNS_SHIFT_X = 0;
  const EXTRA_TURNS_SHIFT_Y = 0;
  const BONUS_BANNER_SHIFT_X = 0;
  const BONUS_BANNER_SHIFT_Y = 0;
  const CHARS_TOP  = SCREEN_H - 178 + CHARS_ROW_SHIFT_Y * BOARD_SCALE;
  const GND_TOP    = CHARS_TOP + 80 + GROUND_SHIFT_Y * BOARD_SCALE;
  const BTN_TOP    = SCREEN_H - 70 + BTN_ROW_SHIFT_Y * BOARD_SCALE;
  const AI_ROW_TOP = BTN_TOP + 46 + AI_ROW_SHIFT_Y * BOARD_SCALE;
  const AI_LBL_TOP = AI_ROW_TOP + 38 + AI_LABEL_SHIFT_Y * BOARD_SCALE;
  const { w: mW, h: mH } = monsterDisplaySize(monsterType);
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

  return (
    <View style={s.root}>

      {/* ── Battle Panel: relative frame for bkboardv + everything inside it ── */}
      <View style={{
        position: 'absolute',
        top: BATTLE_PANEL_TOP,
        left: BATTLE_PANEL_LEFT,
        width: BG_W,
        height: BG_H,
      }}>
        <Image
          source={require('../../../assets/play/bkboardv.png')}
          style={{ position: 'absolute', top: 0, left: 0, width: '100%', height: '100%' }}
          resizeMode="stretch"
        />

      {/* ── Player bars ──────────────────────────────────────── */}
        <View style={{
          position: 'absolute', top: HUD_BASE_Y + PLAYER_HUD_SHIFT_Y * BOARD_SCALE,
          left: PLAYER_HUD_LEFT, width: PLAYER_HUD_BOX_W, height: PLAYER_HUD_BOX_H, zIndex: 20,
        }}>
          <View style={{ position: 'absolute', top: PLAYER_HP_Y, left: PLAYER_HP_X }}>
            <TBar asset={require('../../../assets/play/hpbar.png')} fill={playerHP / maxHP} w={PLAYER_HP_W} h={PLAYER_HP_H} direction="ltr" />
          </View>
          <View style={{ position: 'absolute', top: PLAYER_MP_Y, left: PLAYER_MP_X }}>
            <TBar asset={require('../../../assets/play/manabar.png')} fill={mana / maxMP} w={PLAYER_MP_W} h={PLAYER_MP_H} direction="ltr" />
          </View>
          <Animated.View style={{ position: 'absolute', top: PLAYER_POWER_Y, left: PLAYER_POWER_X, opacity: powerBlinkAnim }}>
            <TBar asset={require('../../../assets/play/powerbar.png')} fill={power / maxPow} w={PLAYER_POWER_W} h={PLAYER_POWER_H} direction="ltr" />
          </Animated.View>
        </View>

        {/* ── Enemy bars ───────────────────────────────────────── */}
        <View style={{
          position: 'absolute', top: HUD_BASE_Y + ENEMY_HUD_SHIFT_Y * BOARD_SCALE,
          right: ENEMY_HUD_RIGHT, width: ENEMY_HUD_BOX_W, height: ENEMY_HUD_BOX_H, zIndex: 20,
        }}>
          <View style={{ position: 'absolute', top: ENEMY_HP_Y, right: ENEMY_HP_X }}>
            <TBar asset={require('../../../assets/play/hpbar.png')} fill={enemyHP / maxEHP} w={ENEMY_HP_W} h={ENEMY_HP_H} direction="rtl" />
          </View>
          <View style={{ position: 'absolute', top: ENEMY_MP_Y, right: ENEMY_MP_X }}>
            <TBar asset={require('../../../assets/play/manabar.png')} fill={0} w={ENEMY_MP_W} h={ENEMY_MP_H} direction="rtl" />
          </View>
          <View style={{ position: 'absolute', top: ENEMY_POWER_Y, right: ENEMY_POWER_X }}>
            <TBar asset={require('../../../assets/play/powerbar.png')} fill={0} w={ENEMY_POWER_W} h={ENEMY_POWER_H} direction="rtl" />
          </View>
        </View>

        {/* ── Gem board ────────────────────────────────────────── */}
        <Animated.View style={[s.gemBoard, {
          top:    BOARD_TOP,
          left:   BOARD_LEFT,
          width:  GEM_SIZE * BOARD_COLS,
          height: GEM_SIZE * BOARD_ROWS,
          transform: [{ translateX: shakeAnim }],
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
                    zIndex: selected?.[0] === r && selected?.[1] === c ? 120 : 1,
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
                    selected={selected?.[0] === r && selected?.[1] === c}
                    focusVariant={turn === 'monster' ? 'enemy' : 'player'}
                    onPress={() => handleGemPress(r, c)}
                  />
                </Animated.View>
              ) : null
            )
          )}

          {/* ── Match particle effects overlay ──────────────────── */}
          {matchFX.map(fx => {
            const fxSize = fx.kind === 'sword' ? GEM_SIZE * 2.5 : GEM_SIZE * 1.4;
            const opacity = fx.anim.interpolate({
              inputRange: [0, 0.3, 1], outputRange: [0, 1, 0],
            });
            const scale = fx.anim.interpolate({
              inputRange: [0, 0.4, 1],
              outputRange: [0.3, 1.1, fx.kind === 'sword' ? 1.6 : 0.7],
            });
            const translateY = fx.anim.interpolate({
              inputRange: [0, 1],
              outputRange: [0, fx.kind === 'sword' ? -GEM_SIZE * 0.4 : -GEM_SIZE * 0.8],
            });
            const cx = fx.c * GEM_SIZE + GEM_SIZE / 2 - fxSize / 2;
            const cy = fx.r * GEM_SIZE + GEM_SIZE / 2 - fxSize / 2;

            let source: any;
            switch (fx.kind) {
              case 'gold':        source = AURA1_IMG;  break;
              case 'mp':          source = CRYS_BLUE;  break;
              case 'crystal_red': source = CRYS_RED;   break;
              case 'sword':       source = Math.random() > 0.5 ? AURA2_IMG : AURA3_IMG; break;
            }

            return (
              <Animated.Image
                key={fx.key}
                source={source}
                resizeMode="contain"
                style={{
                  position: 'absolute',
                  left: cx, top: cy,
                  width: fxSize, height: fxSize,
                  opacity,
                  transform: [{ scale }, { translateY }],
                }}
              />
            );
          })}
        </Animated.View>

        {/* ── Extra turns badge (persistent, on board center) ──── */}
        {extraTurns > 0 && (
          <View style={{
            position: 'absolute',
            top: BOARD_TOP + GEM_SIZE * BOARD_ROWS / 2 - 14 + EXTRA_TURNS_SHIFT_Y * BOARD_SCALE,
            left: BOARD_LEFT + GEM_SIZE * BOARD_COLS / 2 - 55 + EXTRA_TURNS_SHIFT_X * BOARD_SCALE,
            width: 110, height: 20,
            justifyContent: 'center', alignItems: 'center',
            zIndex: 50,
          }}>
            <Text style={{ color: '#111', fontSize: 12, fontWeight: 'bold' }}>
              Còn {extraTurns} lượt
            </Text>
          </View>
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

      {/* ── Ground ───────────────────────────────────────────── */}
      <Image
        source={require('../../../assets/play/ground.png')}
        style={[s.ground, {
          top: GND_TOP,
          transform: [{ translateX: GROUND_SHIFT_X * BOARD_SCALE }],
        }]}
        resizeMode="repeat"
      />

      {/* ── Characters ───────────────────────────────────────── */}
      <View style={[s.charsRow, {
        top: CHARS_TOP,
        transform: [{ translateX: CHARS_ROW_SHIFT_X * BOARD_SCALE }],
      }]}>
        <Image source={require('../../../assets/character/Full.png')}
          style={[s.playerSprite, {
            transform: [
              { translateX: PLAYER_SPRITE_SHIFT_X * BOARD_SCALE },
              { translateY: PLAYER_SPRITE_SHIFT_Y * BOARD_SCALE },
            ],
          }]} resizeMode="contain" />
        <View style={{ flex: 1 }} />
        <View style={{
          width: mW,
          height: mH,
          alignSelf: 'flex-end',
          transform: [
            { translateX: ENEMY_SPRITE_SHIFT_X * BOARD_SCALE },
            { translateY: ENEMY_SPRITE_SHIFT_Y * BOARD_SCALE },
          ],
        }}>
          <MonsterSprite type={monsterType} frameIndex={monFrame} facingRight={false} />
        </View>
      </View>

      {/* ── Action buttons ───────────────────────────────────── */}
      <View style={[s.btnRow, {
        top: BTN_TOP,
        transform: [{ translateX: BTN_ROW_SHIFT_X * BOARD_SCALE }],
      }]}>
        <TouchableOpacity style={s.btnFlee} onPress={onFlee}>
          <Text style={s.btnTxt}>🏃 Tháo Chạy</Text>
        </TouchableOpacity>
        <TouchableOpacity
          style={[s.btnSkill, mana < 30 && s.btnOff]}
          onPress={handleSkill}
          disabled={mana < 30 || phase !== 'idle' || turn !== 'player'}
        >
          <Text style={s.btnTxt}>💫 Kỹ Năng (30 MP)</Text>
        </TouchableOpacity>
      </View>

      {/* ── AI level selector ────────────────────────────────── */}
      <View style={[s.aiRow, {
        top: AI_ROW_TOP,
        transform: [{ translateX: AI_ROW_SHIFT_X * BOARD_SCALE }],
      }]}>
        {/* Monster difficulty label */}
        <Text style={{ color: '#aaa', fontSize: 10, marginRight: 4 }}>Quái:</Text>

        {/* Monster AI level chips */}
        {AI_ORDER.map(lv => (
          <TouchableOpacity
            key={lv}
            style={[s.aiChip, aiLevel === lv && s.aiChipActive]}
            onPress={() => setAiLevel(lv)}
          >
            <Text style={s.aiChipTxt}>{AI_CONFIGS[lv].emoji}</Text>
          </TouchableOpacity>
        ))}
      </View>

      {/* Turn indicator + AI info label */}
      <View style={[s.aiLabelRow, {
        top: AI_LBL_TOP,
        transform: [{ translateX: AI_LABEL_SHIFT_X * BOARD_SCALE }],
      }]}>
        <Text style={[s.aiLabelTxt, {
          color: turn === 'player' ? '#4fc3f7' : '#ff8a65',
          fontWeight: 'bold',
        }]}>
          {turn === 'player' ? '⚔ LƯỢT CỦA BẠN' : '🐉 LƯỢT QUÁI VẬT'}
          {turn === 'monster' && aiLevel !== null && (
            aiStep === 'think' ? ` — ${AI_CONFIGS[aiLevel].emoji} đang suy nghĩ...` :
            aiStep === 'pick1' ? ` — ${AI_CONFIGS[aiLevel].emoji} chọn viên 1...` :
            aiStep === 'pick2' ? ` — ${AI_CONFIGS[aiLevel].emoji} di chuyển...` :
            ` — ${AI_CONFIGS[aiLevel].emoji} ${AI_CONFIGS[aiLevel].name}`
          )}
          {aiLevel === null && turn === 'monster' ? ' — chọn độ khó quái!' : ''}
        </Text>
      </View>

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
                top: BATTLE_PANEL_TOP + BOARD_TOP + GEM_SIZE * BOARD_ROWS / 2 - 34 * BOARD_SCALE,
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
