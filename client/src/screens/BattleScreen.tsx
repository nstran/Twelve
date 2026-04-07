import React, { useState, useEffect, useCallback, useRef } from 'react';
import {
  View, Image, Text, TouchableOpacity, ViewStyle, Animated, StyleSheet,
} from 'react-native';
import { MonsterSprite, MonsterType, WALK_FRAMES, ATTACK_FRAMES, monsterDisplaySize } from '../engine/MonsterSprite';
import {
  s, gc, SCREEN_W, SCREEN_H,
  BG_W, BG_H, BOARD_SCALE,
  BOARD_COLS, BOARD_ROWS, GEM_SIZE,
  BOARD_POS_LEFT, BOARD_POS_TOP,
} from './BattleScreen.styles';

// ── Sprite sheet constants (each chess*.png = 196×28, 7 frames of 28×28) ────
const TOTAL_FRAMES   = 9;   // 252 / 28 = 9 frames
const EXPLODE_START  = 2;
const EXPLODE_END    = 8;

// ── Gem types (chess7 excluded from gameplay, reserved for later) ─────────────
const GEM_TYPES = [0, 1, 2, 3, 4, 5, 6, 8] as const;
type GemType = typeof GEM_TYPES[number];

const GEM_SHEETS: Record<GemType, any> = {
  0: require('../../assets/chess0.png'),
  1: require('../../assets/chess1.png'),
  2: require('../../assets/chess2.png'),
  3: require('../../assets/chess3.png'),
  4: require('../../assets/chess4.png'),
  5: require('../../assets/chess5.png'),
  6: require('../../assets/chess6.png'),
  8: require('../../assets/chess8.png'),
};

const GEM_FX: Record<GemType, { dmg: number; heal: number; mana: number; pow: number }> = {
  0: { dmg: 18, heal:  0, mana:  0, pow: 4 },
  1: { dmg:  0, heal: 25, mana:  0, pow: 2 },
  2: { dmg:  8, heal:  5, mana: 15, pow: 3 },
  3: { dmg: 22, heal:  0, mana:  0, pow: 5 },
  4: { dmg: 10, heal: 10, mana:  5, pow: 3 },
  5: { dmg: 12, heal:  0, mana:  8, pow: 4 },
  6: { dmg:  5, heal:  0, mana:  0, pow: 2 },
  8: { dmg: 20, heal:  0, mana:  0, pow: 5 },
};

const MONSTER_HP: Record<MonsterType, number> = { fire: 120, ice: 150, zap: 180 };

// ── Board logic helpers ───────────────────────────────────────────────────────
type Board = (GemType | null)[][];
function randomGem(): GemType { return GEM_TYPES[Math.floor(Math.random() * GEM_TYPES.length)]; }
function makeBoard(): Board {
  return Array.from({ length: BOARD_ROWS }, () =>
    Array.from({ length: BOARD_COLS }, randomGem));
}

function findMatches(b: Board): Set<string> {
  const hit = new Set<string>();
  for (let r = 0; r < BOARD_ROWS; r++) {
    let c = 0;
    while (c < BOARD_COLS - 2) {
      const g = b[r][c];
      if (g !== null && g === b[r][c + 1] && g === b[r][c + 2]) {
        let e = c + 2;
        while (e + 1 < BOARD_COLS && b[r][e + 1] === g) e++;
        for (let i = c; i <= e; i++) hit.add(`${r},${i}`);
        c = e + 1;
      } else c++;
    }
  }
  for (let c = 0; c < BOARD_COLS; c++) {
    let r = 0;
    while (r < BOARD_ROWS - 2) {
      const g = b[r][c];
      if (g !== null && g === b[r + 1][c] && g === b[r + 2][c]) {
        let e = r + 2;
        while (e + 1 < BOARD_ROWS && b[e + 1][c] === g) e++;
        for (let i = r; i <= e; i++) hit.add(`${i},${c}`);
        r = e + 1;
      } else r++;
    }
  }
  return hit;
}

function collapseBoard(b: Board, matched: Set<string>): Board {
  const nb: Board = b.map(row => [...row]);
  for (let c = 0; c < BOARD_COLS; c++) {
    const keep: GemType[] = [];
    for (let r = 0; r < BOARD_ROWS; r++)
      if (!matched.has(`${r},${c}`) && nb[r][c] !== null)
        keep.push(nb[r][c] as GemType);
    const fill = BOARD_ROWS - keep.length;
    const col  = [...Array.from({ length: fill }, randomGem), ...keep];
    for (let r = 0; r < BOARD_ROWS; r++) nb[r][c] = col[r];
  }
  return nb;
}

// ── GemCell component ─────────────────────────────────────────────────────────
interface GemCellProps {
  gemType: GemType;
  frameIndex: number;
  size: number;
  selected: boolean;
  onPress: () => void;
  style?: ViewStyle;
}

const GemCell: React.FC<GemCellProps> = React.memo(({ gemType, frameIndex, size, selected, onPress, style }) => {
  const sheetDisplayW = size * TOTAL_FRAMES;
  const translateX = -frameIndex * size;

  return (
    <TouchableOpacity
      activeOpacity={0.8}
      onPress={onPress}
      style={[gc.wrap, { width: size, height: size }, selected && gc.sel, style]}
    >
      <View style={{ width: size, height: size, alignItems: 'center', justifyContent: 'center' }}>
        {/* Lớp Wrapper Scale: giúp thu nhỏ quân cờ mà KHÔNG làm mất chi tiết ở rìa (clipping) */}
        <View style={{ transform: [{ scale: 0.82 }] }}>
          <View style={{ width: size, height: size, overflow: 'hidden' }}>
            <Image
              source={GEM_SHEETS[gemType]}
              style={{
                width: sheetDisplayW,
                height: size,
                transform: [{ translateX: translateX }],
              }}
              resizeMode="stretch"
            />
          </View>
        </View>
      </View>
    </TouchableOpacity>
  );
});

// ── Props ─────────────────────────────────────────────────────────────────────
interface Props {
  monsterType: MonsterType;
  onVictory: () => void;
  onDefeat: () => void;
  onFlee: () => void;
}

// ── BattleScreen component ────────────────────────────────────────────────────
export const BattleScreen: React.FC<Props> = ({ monsterType, onVictory, onDefeat, onFlee }) => {
  const maxHP = 100;
  const maxEHP = MONSTER_HP[monsterType] ?? 150;
  const maxMP = 100;
  const maxPow = 100;

  const [board, setBoard] = useState<Board>(makeBoard);
  const [selected, setSelected] = useState<[number, number] | null>(null);
  const [explodeFrames, setExplodeFrames] = useState<Record<string, number>>({});
  const [playerHP, setPlayerHP] = useState(maxHP);
  const [enemyHP, setEnemyHP] = useState(maxEHP);
  const [mana, setMana] = useState(30);
  const [power, setPower] = useState(40);
  const [log, setLog] = useState('Trận đấu bắt đầu!');
  const [combo, setCombo] = useState(0);
  const [phase, setPhase] = useState<'idle' | 'busy' | 'over'>('idle');
  const [result, setResult] = useState<'victory' | 'defeat' | null>(null);
  const [monFrame, setMonFrame] = useState<number>(WALK_FRAMES[0]);
  const [monAtk, setMonAtk] = useState(false);
  const shakeAnim = useRef(new Animated.Value(0)).current;

  const phaseRef = useRef<'idle' | 'busy' | 'over'>('idle');
  const mountedRef = useRef(true);

  useEffect(() => { phaseRef.current = phase; }, [phase]);
  useEffect(() => () => { mountedRef.current = false; }, []);

  // Monster animation
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

  // Monster attack
  useEffect(() => {
    const t = setInterval(() => {
      if (!mountedRef.current || phaseRef.current === 'over') return;
      const dmg = 8 + Math.floor(Math.random() * 14);
      setMonAtk(true);
      setTimeout(() => mountedRef.current && setMonAtk(false), 600);
      setPlayerHP(hp => {
        const next = Math.max(0, hp - dmg);
        if (next === 0 && phaseRef.current !== 'over') {
          phaseRef.current = 'over'; setPhase('over'); setResult('defeat');
        }
        return next;
      });
      setLog(`Quái tấn công! -${dmg} HP`);
    }, 3500);
    return () => clearInterval(t);
  }, []);

  const playExplosion = useCallback((matched: Set<string>, onDone: () => void) => {
    let frame = EXPLODE_START;
    const update = (f: number) => {
      const next: Record<string, number> = {};
      matched.forEach(k => { next[k] = f; });
      setExplodeFrames(next);
    };
    update(frame);
    const tick = setInterval(() => {
      frame++;
      if (frame > EXPLODE_END || !mountedRef.current) {
        clearInterval(tick);
        setExplodeFrames({});
        onDone();
        return;
      }
      update(frame);
    }, 65);
  }, []);

  const processMatches = useCallback((b: Board, chain: number) => {
    if (!mountedRef.current) return;
    const matches = findMatches(b);
    if (matches.size === 0) {
      setBoard(b); setPhase('idle'); phaseRef.current = 'idle'; setCombo(0);
      return;
    }

    if (matches.size > 3 || chain > 0) {
      Animated.sequence([
        Animated.timing(shakeAnim, { toValue: 5, duration: 40, useNativeDriver: true }),
        Animated.timing(shakeAnim, { toValue: -5, duration: 40, useNativeDriver: true }),
        Animated.timing(shakeAnim, { toValue: 5, duration: 40, useNativeDriver: true }),
        Animated.timing(shakeAnim, { toValue: 0, duration: 40, useNativeDriver: true }),
      ]).start();
    }

    let dmg = 0, heal = 0, mp = 0, pow = 0;
    const counts: Partial<Record<GemType, number>> = {};
    matches.forEach(key => {
      const [r, c] = key.split(',').map(Number);
      const g = b[r][c];
      if (g !== null) counts[g] = (counts[g] ?? 0) + 1;
    });
    Object.entries(counts).forEach(([gs, cnt]) => {
      const g = Number(gs) as GemType;
      const fx = GEM_FX[g];
      const mul = 1 + chain * 0.4;
      dmg += Math.round(fx.dmg * (cnt! / 3) * mul);
      heal += Math.round(fx.heal * (cnt! / 3) * mul);
      mp += Math.round(fx.mana * cnt!);
      pow += Math.round(fx.pow * cnt!);
    });

    playExplosion(matches, () => {
      if (!mountedRef.current) return;
      const nb = collapseBoard(b, matches);
      setEnemyHP(hp => {
        const next = Math.max(0, hp - dmg);
        if (next === 0 && phaseRef.current !== 'over') {
          phaseRef.current = 'over'; setPhase('over'); setResult('victory');
        }
        return next;
      });
      if (heal > 0) setPlayerHP(hp => Math.min(maxHP, hp + heal));
      if (mp > 0) setMana(m => Math.min(maxMP, m + mp));
      if (pow > 0) setPower(p => Math.min(maxPow, p + pow));
      setLog((chain > 0 ? `COMBO ×${chain + 1}! ` : '') + (dmg > 0 ? `⚔ -${dmg} ` : '') + (heal > 0 ? `❤ +${heal}` : ''));
      setCombo(chain + 1);
      setTimeout(() => processMatches(nb, chain + 1), 150);
    });
  }, [playExplosion, maxHP, maxMP, maxPow]);

  const handleGemPress = useCallback((row: number, col: number) => {
    if (phase !== 'idle') return;
    if (!selected) { setSelected([row, col]); return; }
    const [sr, sc] = selected;
    if (sr === row && sc === col) { setSelected(null); return; }
    const adj = (Math.abs(sr - row) === 1 && sc === col) || (sr === row && Math.abs(sc - col) === 1);
    if (!adj) { setSelected([row, col]); return; }
    const nb: Board = board.map(r => [...r]);
    const tmp = nb[sr][sc]; nb[sr][sc] = nb[row][col]; nb[row][col] = tmp as GemType;
    setSelected(null);
    if (findMatches(nb).size === 0) { setLog('Không có combo!'); return; }
    setPhase('busy'); phaseRef.current = 'busy'; setBoard(nb); processMatches(nb, 0);
  }, [selected, board, phase, processMatches]);

  const handleSkill = useCallback(() => {
    if (mana < 30 || phase !== 'idle') return;
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
  }, [mana, phase]);

  const HUD_LEFT_X = (SCREEN_W - BG_W) / 2 + (12 * BOARD_SCALE);
  const HUD_RIGHT_X = (SCREEN_W - BG_W) / 2 + (138 * BOARD_SCALE);
  const HUD_BASE_Y = 10 + (245 * BOARD_SCALE);
  const HUD_GAP_Y = 12 * BOARD_SCALE;
  const HUD_W = 90 * BOARD_SCALE;
  const HUD_H = 7 * BOARD_SCALE;
  const LOG_TOP = BOARD_POS_TOP + (GEM_SIZE * BOARD_ROWS) + (5 * BOARD_SCALE);
  const CHARS_TOP = SCREEN_H - 180;
  const GND_TOP = CHARS_TOP + 80;
  const BTN_TOP = SCREEN_H - 70;
  const { w: mW, h: mH } = monsterDisplaySize(monsterType);

  return (
    <View style={s.root}>
      <View style={{ position: 'absolute', top: 10, left: (SCREEN_W - BG_W) / 2, width: BG_W, height: BG_H }}>
        <Image source={require('../../assets/play/bkboardv.png')} style={{ width: '100%', height: '100%' }} resizeMode="stretch" />
      </View>

      <View style={{ position: 'absolute', top: HUD_BASE_Y, left: HUD_LEFT_X, width: HUD_W, gap: HUD_GAP_Y - HUD_H, zIndex: 20 }}>
        <TextureBar asset={require('../../assets/play/hpbar.png')} fill={playerHP / maxHP} width={HUD_W} height={HUD_H} />
        <TextureBar asset={require('../../assets/play/manabar.png')} fill={mana / maxMP} width={HUD_W} height={HUD_H} />
        <TextureBar asset={require('../../assets/play/powerbar.png')} fill={power / maxPow} width={HUD_W} height={HUD_H} />
      </View>

      <View style={{ position: 'absolute', top: HUD_BASE_Y, left: HUD_RIGHT_X, width: HUD_W, gap: HUD_GAP_Y - HUD_H, zIndex: 20 }}>
        <TextureBar asset={require('../../assets/play/hpbar.png')} fill={enemyHP / maxEHP} width={HUD_W} height={HUD_H} />
        <TextureBar asset={require('../../assets/play/manabar.png')} fill={0} width={HUD_W} height={HUD_H} />
        <TextureBar asset={require('../../assets/play/powerbar.png')} fill={0} width={HUD_W} height={HUD_H} />
      </View>

      <Animated.View style={[s.gemBoard, { top: BOARD_POS_TOP, left: BOARD_POS_LEFT, width: GEM_SIZE * BOARD_COLS, height: GEM_SIZE * BOARD_ROWS, transform: [{ translateX: shakeAnim }] }]}>
        {board.map((row, r) => row.map((gem, c) => (
          gem !== null && <GemCell key={`${r}-${c}`} gemType={gem} frameIndex={explodeFrames[`${r},${c}`] ?? 0} size={GEM_SIZE} selected={selected?.[0] === r && selected?.[1] === c} onPress={() => handleGemPress(r, c)} style={{ top: r * GEM_SIZE, left: c * GEM_SIZE }} />
        )))}
      </Animated.View>

      <View style={[s.logBar, { top: LOG_TOP, width: BG_W - 30 }]}>
        <Text style={s.logTxt} numberOfLines={2}>{log}</Text>
        {combo > 1 && <Text style={s.comboTxt}>×{combo}</Text>}
      </View>

      <Image source={require('../../assets/play/ground.png')} style={[s.ground, { top: GND_TOP }]} resizeMode="repeat" />

      <View style={[s.charsRow, { top: CHARS_TOP }]}>
        <Image source={require('../../assets/character/Full.png')} style={s.playerSprite} resizeMode="contain" />
        <View style={{ flex: 1 }} />
        <View style={{ width: mW, height: mH, alignSelf: 'flex-end' }}>
          <MonsterSprite type={monsterType} frameIndex={monFrame} facingRight={false} />
        </View>
      </View>

      <View style={[s.btnRow, { top: BTN_TOP }]}>
        <TouchableOpacity style={s.btnFlee} onPress={onFlee}><Text style={s.btnTxt}>🏃 Tháo Chạy</Text></TouchableOpacity>
        <TouchableOpacity style={[s.btnSkill, mana < 30 && s.btnOff]} onPress={handleSkill} disabled={mana < 30 || phase !== 'idle'}><Text style={s.btnTxt}>💫 Kỹ Năng (30 MP)</Text></TouchableOpacity>
      </View>

      {result !== null && (
        <View style={s.overlay}>
          <Text style={s.overlayTitle}>{result === 'victory' ? '🏆 CHIẾN THẮNG!' : '💀 THẤT BẠI!'}</Text>
          <Text style={s.overlaySub}>{result === 'victory' ? 'Quái vật đã bị tiêu diệt!' : 'Nhân vật đã ngã xuống...'}</Text>
          <TouchableOpacity style={s.overlayBtn} onPress={result === 'victory' ? onVictory : onDefeat}><Text style={s.overlayBtnTxt}>{result === 'victory' ? '▶ Tiếp tục' : '↺ Thử lại'}</Text></TouchableOpacity>
        </View>
      )}
    </View>
  );
};

// ── TextureBar component ──────────────────────────────────────────────────────
const TextureBar: React.FC<{ asset: any; fill: number; width: number; height: number; }> = ({ asset, fill, width, height }) => (
  <View style={{ width, height, backgroundColor: 'rgba(0,0,0,0.5)', overflow: 'hidden', borderRadius: 1 }}>
    <Image source={asset} style={{ width, height, transform: [{ translateX: -width * (1 - fill) }] }} resizeMode="stretch" />
  </View>
);
