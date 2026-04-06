import { Dimensions, View, Text, Image, ScrollView } from 'react-native';
import React from 'react';
import { styles } from './CharacterStatusScreen.styles';
import { SoftkeyBar } from '../components/SoftkeyBar';
import { BODIES, FACES, HAIRS } from '../assets/AssetIndex';

const { width: SCREEN_WIDTH } = Dimensions.get('window');
const ASSET_CORNERS = require('../../assets/ui/frames/cornerskb.png');

interface StatusScreenProps {
  onStart: () => void;
}

export const CharacterStatusScreen: React.FC<StatusScreenProps> = ({ onStart }) => {
  // ── Fake Data for Mockup (Matching i_win_95 image) ─────────────────────
  const player = {
    username: 'i_win_95',
    level: 101,
    title: 'Đại Hiệp',
    rank: '6,291',
    fame: '242',
    winLoss: '922/389',
    ken: '1.100 KEN',
    hp: { current: 1850, max: 1850 },
    exp: '41,7%',
    stamina: { current: 2627, max: 10000 },
    stats: {
      cuongLuc: 115,
      noiLuc: 148,
      thanPhap: 532,
      theLuc: 110,
    },
    points: 0,
    combat: {
      attack: 615,
      def: 266,
      acc: 1596,
      dodge: 798,
      hp: 1850,
      crit: '38%',
    }
  };

  const StatField = ({ label, value, isTitle = false }: { label: string; value: string; isTitle?: boolean }) => (
    <View style={styles.statValueRow}>
      <Text style={styles.statLabel}>{label}</Text>
      <View style={styles.statValueBox}>
        <Text style={[styles.statValueText, isTitle && styles.titleText]}>{value}</Text>
      </View>
    </View>
  );

  const ProgressBar = ({ color, text, percentage }: { color: string; text: string; percentage: number }) => (
    <View style={styles.barWrapper}>
      <View style={[styles.barFill, { backgroundColor: color, width: `${percentage}%` }]} />
      <Text style={styles.barText}>{text}</Text>
    </View>
  );

  const AttrRow = ({ label, value, isSpecial = false }: { label: string; value: number; isSpecial?: boolean }) => (
    <View style={styles.attrRow}>
      <Text style={[styles.attrLabel, isSpecial && styles.hpLabel]}>{label}</Text>
      <View style={styles.attrValueBox}>
        <Text style={styles.attrValueText}>{value}</Text>
      </View>
    </View>
  );

  return (
    <View style={styles.container}>
      <View style={styles.outerBox}>
        {/* Ornate Corners */}
        <Image source={ASSET_CORNERS} style={{ position: 'absolute', top: -2, left: -2, width: 20, height: 20, zIndex: 10, resizeMode: 'contain' }} />
        <Image source={ASSET_CORNERS} style={{ position: 'absolute', top: -2, right: -2, width: 20, height: 20, zIndex: 10, resizeMode: 'contain', transform: [{ scaleX: -1 }] }} />
        <Image source={ASSET_CORNERS} style={{ position: 'absolute', bottom: -2, left: -2, width: 20, height: 20, zIndex: 10, resizeMode: 'contain', transform: [{ scaleY: -1 }] }} />
        <Image source={ASSET_CORNERS} style={{ position: 'absolute', bottom: -2, right: -2, width: 20, height: 20, zIndex: 10, resizeMode: 'contain', transform: [{ scale: -1 }] }} />

        <View style={styles.innerBox}>
          
          {/* ── Header ──────────────────────────────────────────────── */}
          <View style={styles.headerRow}>
            <View style={styles.avatarContainer}>
              <View style={{ width: 60, height: 80, position: 'relative' }}>
                {/* Lớp Layered Nhân vật */}
                <Image source={BODIES[0]} style={[styles.avatarLayer, { position: 'absolute' }]} />
                <Image source={FACES[1]} style={[styles.avatarLayer, { position: 'absolute', top: 5 }]} />
                {/* <Image source={HAIRS[0]} style={[styles.avatarLayer, { position: 'absolute', top: -2 }]} /> */}
              </View>
            </View>
            
            <View style={styles.topStats}>
              <View style={styles.nameRow}>
                <Text style={styles.usernameText}>⚡ {player.username}</Text>
                <Text style={styles.levelText}>Cấp: {player.level}</Text>
              </View>
              
              <StatField label="Danh Hiệu" value={player.title} isTitle />
              <StatField label="Xếp Hạng" value={player.rank} />
              <StatField label="Danh vọng" value={player.fame} />
              <StatField label="Thắng/Thua" value={player.winLoss} />
              
              <Text style={styles.kenLabel}>{player.ken}</Text>
            </View>
          </View>

          <View style={styles.divider} />

          {/* ── Progress Bars ────────────────────────────────────────── */}
          <View style={styles.barsContainer}>
            <ProgressBar color="#dd2222" text={`${player.hp.current}/${player.hp.max}`} percentage={100} />
            <ProgressBar color="#22bb22" text={player.exp} percentage={41.7} />
            <ProgressBar color="#e6cc00" text={`${player.stamina.current}/${player.stamina.max}`} percentage={26} />
          </View>

          {/* ── Attributes ───────────────────────────────────────────── */}
          <View style={styles.attrContainer}>
            <AttrRow label="Cường Lực" value={player.stats.cuongLuc} />
            <AttrRow label="Nội Lực" value={player.stats.noiLuc} />
            <AttrRow label="Thân Pháp" value={player.stats.thanPhap} isSpecial />
            <AttrRow label="Thể Lực" value={player.stats.theLuc} />
          </View>

          <View style={styles.pointsRow}>
            <Text style={styles.pointsLabel}>Điểm</Text>
            <View style={styles.pointsValueBox}>
              <Text style={styles.attrValueText}>{player.points}</Text>
            </View>
          </View>

          <View style={styles.divider} />

          {/* ── Combat Stats Grid ────────────────────────────────────── */}
          <View style={styles.combatStatsGrid}>
            <View style={styles.combatStatItem}>
              <Text style={styles.combatStatLabel}>Tấn Công</Text>
              <View style={styles.combatStatBox}><Text style={styles.combatStatValue}>{player.combat.attack}</Text></View>
            </View>
            <View style={styles.combatStatItem}>
              <Text style={styles.combatStatLabel}>P.Thủ</Text>
              <View style={styles.combatStatBox}><Text style={styles.combatStatValue}>{player.combat.def}</Text></View>
            </View>
            <View style={styles.combatStatItem}>
              <Text style={styles.combatStatLabel}>Chính Xác</Text>
              <View style={styles.combatStatBox}><Text style={styles.combatStatValue}>{player.combat.acc}</Text></View>
            </View>
            <View style={styles.combatStatItem}>
              <Text style={styles.combatStatLabel}>Né Tránh</Text>
              <View style={styles.combatStatBox}><Text style={styles.combatStatValue}>{player.combat.dodge}</Text></View>
            </View>
            <View style={styles.combatStatItem}>
              <Text style={styles.combatStatLabel}>Sinh Lực</Text>
              <View style={styles.combatStatBox}><Text style={styles.combatStatValue}>{player.combat.hp}</Text></View>
            </View>
            <View style={styles.combatStatItem}>
              <Text style={styles.combatStatLabel}>Chí Mạng</Text>
              <View style={styles.combatStatBox}><Text style={styles.combatStatValue}>{player.combat.crit}</Text></View>
            </View>
          </View>

          {/* ── Bottom Softkeys (Overlay) ────────────────────────────── */}
          <View style={styles.softKeyBarContainer}>
            <SoftkeyBar 
              width={SCREEN_WIDTH - 24} // Adjust for padding
              leftLabel="Menu"
              rightLabel="Bắt đầu"
              onRightPress={onStart}
              onLeftPress={() => {}} // Future menu
            />
          </View>
        </View>
      </View>
    </View>
  );
};
