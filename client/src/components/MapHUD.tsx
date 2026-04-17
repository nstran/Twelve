import React from 'react';
import {
  View,
  Text,
  StyleSheet,
  Platform,
} from 'react-native';

// ── Types ────────────────────────────────────────────────────────────────────
interface MapHUDProps {
  /** Current HP value */
  hp: number;
  /** Max HP */
  maxHp: number;
  /** Current EXP percentage (0-100) */
  expPercent: number;
  /** Zone / Map name displayed on right corner */
  zoneName: string;
  /** Screen width for responsive layout */
  width: number;
}

// ── Component ────────────────────────────────────────────────────────────────
export const MapHUD: React.FC<MapHUDProps> = ({
  hp,
  maxHp,
  expPercent,
  zoneName,
  width,
}) => {
  const hpPercent = maxHp > 0 ? Math.min(100, (hp / maxHp) * 100) : 0;
  const expClamped = Math.min(100, Math.max(0, expPercent));

  return (
    <View style={[styles.container, { width }]} pointerEvents="none">
      {/* ─── Content ─── */}
      <View style={styles.content}>
        {/* LEFT SIDE: HP + EXP bars stacked */}
        <View style={styles.barsBlock}>
          {/* HP Bar - thick red with dark border */}
          <View style={styles.hpBarOuter}>
            <View style={styles.hpBarTrack}>
              <View style={[styles.hpBarFill, { width: `${hpPercent}%` as any }]}>
                {/* Top shine for 3D glass effect */}
                <View style={styles.hpBarShineTop} />
              </View>
            </View>
          </View>

          {/* EXP Bar - green, thicker like the reference */}
          <View style={styles.expBarOuter}>
            <View style={styles.expBarTrack}>
              <View style={[styles.expBarFill, { width: `${expClamped}%` as any }]}>
                <View style={styles.expBarShineTop} />
              </View>
            </View>
          </View>
        </View>

        {/* RIGHT SIDE: Zone name badge */}
        <View style={styles.zoneBadge}>
          <View style={styles.zoneBadgeBorder}>
            <Text style={styles.zoneText} numberOfLines={1}>
              {zoneName}
            </Text>
          </View>
        </View>
      </View>
    </View>
  );
};

// ── Styles ───────────────────────────────────────────────────────────────────
const BAR_H = 12; // Thicker bars to match Java look
const HUD_HEIGHT = 40;

const styles = StyleSheet.create({
  container: {
    height: HUD_HEIGHT,
    position: 'absolute',
    top: 0,
    left: 0,
    zIndex: 100,
  },

  // Content layout
  content: {
    flex: 1,
    flexDirection: 'row',
    alignItems: 'flex-start',
    paddingLeft: 4,
    paddingRight: 6,
    paddingTop: 4,
  },

  // ── Bars block (left side) ──
  barsBlock: {
    justifyContent: 'flex-start',
    gap: 3,
    width: 140,
    backgroundColor: 'rgba(255, 230, 180, 0.7)', // Slightly parchment-like background for the bar area
    padding: 2,
    borderWidth: 1,
    borderColor: '#8B4513',
    borderRadius: 1,
  },

  // ── HP Bar (red, thick, black bordered) ──
  hpBarOuter: {
    height: BAR_H + 2,
    borderWidth: 1,
    borderColor: '#000000',
    backgroundColor: '#333333',
    overflow: 'hidden',
  },
  hpBarTrack: {
    flex: 1,
    backgroundColor: '#404040',
  },
  hpBarFill: {
    height: '100%',
    backgroundColor: '#FF0000', // Bright red
    position: 'relative',
    overflow: 'hidden',
  },
  hpBarShineTop: {
    position: 'absolute',
    top: 0,
    left: 0,
    right: 0,
    height: '40%',
    backgroundColor: 'rgba(255, 255, 255, 0.4)',
  },

  // ── EXP Bar (green, thick, black bordered) ──
  expBarOuter: {
    height: BAR_H + 2,
    borderWidth: 1,
    borderColor: '#000000',
    backgroundColor: '#333333',
    overflow: 'hidden',
  },
  expBarTrack: {
    flex: 1,
    backgroundColor: '#404040',
  },
  expBarFill: {
    height: '100%',
    backgroundColor: '#00FF00', // Bright green
    position: 'relative',
    overflow: 'hidden',
  },
  expBarShineTop: {
    position: 'absolute',
    top: 0,
    left: 0,
    right: 0,
    height: '40%',
    backgroundColor: 'rgba(255, 255, 255, 0.3)',
  },

  // ── Zone badge (right side, blue box) ──
  zoneBadge: {
    marginLeft: 'auto',
  },
  zoneBadgeBorder: {
    paddingHorizontal: 12,
    paddingVertical: 4,
    borderWidth: 1.5,
    borderColor: '#2255bb',
    backgroundColor: 'rgba(220, 230, 250, 0.95)',
    borderRadius: 2,
  },
  zoneText: {
    color: '#000000',
    fontSize: 16,
    fontWeight: 'normal',
    fontFamily: Platform.OS === 'ios' ? 'System' : 'monospace',
  },
});
