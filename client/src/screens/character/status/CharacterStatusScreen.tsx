import React, { useState } from 'react';
import { Dimensions, View, Text, Image } from 'react-native';
import { styles } from './CharacterStatusScreen.styles';
import { SoftkeyBar } from '../../../components/controls/SoftkeyBar/SoftkeyBar';
import { PopupMenu } from '../../../components/controls/PopupMenu/PopupMenu';
import { COMMON_MENU_ITEMS } from '../../../constants/MenuConstants';
import { CHARACTER_STATUS_ASSETS } from './assets';
import { CreateCharacterPreview } from '../create/CreateCharacterPreview';
import { CornerFrame } from '../../../components/ui/CornerFrame/CornerFrame';
import type { CharacterAppearance as PlayerAppearance } from '../shared';

const { width: SCREEN_WIDTH } = Dimensions.get('window');

// ── elementsicon: 60×15, 4 frames of 15×15 (pc.b() dùng getWidth()/4) ───────
const EI_TOTAL_W = 60;
const EI_H       = 15;
const EI_FRAME_W = EI_TOTAL_W / 4; // 15px
const EI_SCALE   = 1.3;
const EI_DISP_W  = Math.round(EI_FRAME_W * EI_SCALE); // 19.5px
const EI_DISP_H  = Math.round(EI_H       * EI_SCALE); // 19.5px

// ── Primary stat highlight — aI[] trong da.java ───────────────────────────────
// Hỏa(0)→row0=CườngLực, Lôi(1)→row2=ThânPháp, Thủy(2)→row1=NộiLực
const PRIMARY_STAT: Record<number, number> = { 0: 0, 1: 2, 2: 1 };

const toBarPct = (cur: number, max: number) => {
  if (max <= 0) return 0;
  return Math.max(0, Math.min(100, (cur * 100) / max));
};

const formatQuan = (value: number) =>
  `${String(Math.max(0, Math.floor(value))).replace(/\B(?=(\d{3})+(?!\d))/g, '.')} Quan`;

interface StatusScreenProps {
  onStart:    () => void;
  onLogout:   () => void;
  appearance: PlayerAppearance;
  username?:  string;
}

// ── ElementIcon ───────────────────────────────────────────────────────────────
const ElementIcon: React.FC<{ elementIndex: number }> = ({ elementIndex }) => (
  <View style={{ width: EI_DISP_W, height: EI_DISP_H, overflow: 'hidden' }}>
    <Image
      source={CHARACTER_STATUS_ASSETS.elementsicon}
      resizeMode="stretch"
      style={{
        position: 'absolute',
        left:   -(elementIndex * EI_FRAME_W * EI_SCALE),
        top:    0,
        width:  EI_TOTAL_W * EI_SCALE,
        height: EI_DISP_H,
      }}
    />
  </View>
);

// ── Decorative Divider ───────────────────────────────────────────────────────
const DecorativeDivider: React.FC = () => (
  <View style={styles.dividerContainer}>
    <View style={styles.dividerLine} />
    <Text style={styles.dividerSymbol}>Ꮚ</Text>
    <View style={styles.dividerLine} />
  </View>
);

// ── InfoRow: hàng label + value box (+ text phụ bên phải nếu có) ─────────────
interface InfoRowProps {
  label: string;
  value: string;
  valueRed?: boolean;
}
const InfoRow: React.FC<InfoRowProps> = ({ label, value, valueRed }) => (
  <View style={styles.infoItem}>
    <Text style={styles.infoLabel}>{label}</Text>
    <View style={styles.infoBox}>
      <Text style={[styles.infoValue, valueRed && styles.infoValueRed]} numberOfLines={1}>
        {value}
      </Text>
    </View>
  </View>
);

// ── BarRow: thanh tiến trình với icon nhỏ bên trái ───────────────────────────
interface BarRowProps {
  icon:  any;
  iconW: number;
  iconH: number;
  color: string;
  text:  string;
  pct:   number;
}
const BarRow: React.FC<BarRowProps> = ({ icon, iconW, iconH, color, text, pct }) => (
  <View style={styles.barRow}>
    <View style={{ width: 22, alignItems: 'center', justifyContent: 'center' }}>
      <Image source={icon} style={{ width: iconW * 1.2, height: iconH * 1.2 }} resizeMode="contain" />
    </View>
    <View style={styles.barWrapper}>
      <View style={[styles.barFill, { backgroundColor: color, width: `${Math.min(pct, 100)}%` as any }]} />
      <Text style={styles.barText}>{text}</Text>
    </View>
  </View>
);

// ── AttrRow: chỉ số cơ bản với highlight stat chính ─────────────────────────
interface AttrRowProps {
  label:   string;
  value:   number;
  primary?: boolean;
}
const AttrRow: React.FC<AttrRowProps> = ({ label, value, primary }) => (
  <View style={styles.attrItem}>
    <Text style={[styles.attrLabel, primary && styles.attrLabelPrimary]}>{label}</Text>
    <View style={[styles.attrBox, primary && styles.attrBoxPrimary]}>
      <Text style={[styles.attrValue, primary && styles.attrValuePrimary]}>{value}</Text>
    </View>
  </View>
);

// ── CombatStat: ô chỉ số chiến đấu ──────────────────────────────────────────
const CombatStat: React.FC<{ label: string; value: string }> = ({ label, value }) => (
  <View style={styles.combatItem}>
    <Text style={styles.combatLabel}>{label}</Text>
    <View style={styles.combatBox}>
      <Text style={styles.combatValue}>{value}</Text>
    </View>
  </View>
);

// ── Main Screen ──────────────────────────────────────────────────────────────
export const CharacterStatusScreen: React.FC<StatusScreenProps> = ({
  onStart, onLogout, appearance, username,
}) => {
  const [menuVisible,   setMenuVisible]   = useState(false);
  const [selectedIndex, setSelectedIndex] = useState(0);

  const elementIndex = appearance.elementIndex ?? 0;
  const primaryRow   = PRIMARY_STAT[elementIndex] ?? 0;

  // Placeholder — thay bằng dữ liệu thực tế từ props/db
  const player = {
    level:     appearance.level ?? 1,
    quanHam:   appearance.quanHam ?? 'Tân Binh',
    xepHang:   appearance.xepHang ?? 'Chưa có',
    danhVong:  appearance.danhVong ?? 0,
    thangThua: appearance.thangThua ?? '0/0',
    quan:      appearance.quan ?? '0 Quan',
    walletQuan: appearance.walletQuan ?? 0,
    quanProgress: appearance.quanProgress ?? { cur: 0, max: 10000 },
    hp:    appearance.hp ?? { cur: 100,  max: 100 },
    exp:   appearance.exp ?? { cur: 0, max: 100 },
    power: appearance.power ?? { cur: 0,  max: 100 },
    stats: appearance.stats ?? { cuongLuc: 10, noiLuc: 10, thanPhap: 10, theLuc: 10 },
    points: appearance.points ?? 0,
    combat: appearance.combat ?? { attack: 0, def: 0, acc: 0, dodge: 0, hp: 0, crit: '0%' },
  };

  const STAT_LABELS = ['Cường Lực', 'Nội Lực', 'Thân Pháp', 'Thể Lực'];
  const STAT_VALUES = [
    player.stats.cuongLuc,
    player.stats.noiLuc,
    player.stats.thanPhap,
    player.stats.theLuc,
  ];

  return (
    <View style={styles.container}>

      {/* Background */}
      <View style={styles.bgWrapper}>
        <Image source={CHARACTER_STATUS_ASSETS.background} style={styles.background} />
      </View>

      <CornerFrame style={styles.outerBox} contentStyle={styles.innerBox}>
        {/* ── HEADER ──────────────────────────────────────────────── */}
        <View style={styles.header}>
          {/* Dòng tên và Cấp — Nằm trên cùng */}
          <View style={styles.nameRow}>
            <ElementIcon elementIndex={elementIndex} />
            <Text style={styles.username} numberOfLines={1}>{username ?? appearance.username ?? 'Nhân vật'}</Text>
            <Text style={styles.levelText}>Cấp:{player.level}</Text>
          </View>

          <View style={styles.headerContent}>
              <View style={styles.avatarPanel}>
                <View style={styles.avatarBox}>
                  <View style={{ 
                    width: '100%',
                    height: '100%',
                    alignItems: 'center',
                    justifyContent: 'flex-end',
                  }}>
                    <CreateCharacterPreview
                      genderIndex={appearance.genderIndex}
                      faceIndex={appearance.faceIndex}
                      hairIndex={appearance.hairIndex}
                      hairColorIndex={appearance.hairColorIndex}
                      skinColorIndex={appearance.skinColorIndex}
                      scale={1.5}
                      style={{ position: 'relative', bottom: 4 }}
                    />
                  </View>
                </View>
                <View style={styles.walletRow}>
                  <Text style={styles.walletText} numberOfLines={1}>{formatQuan(player.walletQuan)}</Text>
                </View>
              </View>

            {/* Info panel phải */}
            <View style={styles.infoPanel}>
              <InfoRow label="Quân Hàm"    value={player.quanHam}          valueRed />
              <InfoRow label="Xếp Hạng"    value={player.xepHang} />
              <InfoRow label="Danh vọng"   value={String(player.danhVong)} />
              <InfoRow label="Thắng/Thua"  value={player.thangThua} />
            </View>
          </View>
        </View>

        <DecorativeDivider />

        {/* ── 3 THANH TIẾN TRÌNH ──────────────────────────────────── */}
        <View style={styles.barsSection}>
          <BarRow
            icon={CHARACTER_STATUS_ASSETS.heart}   iconW={13} iconH={13}
            color="#dd1111"
            text={`${player.hp.cur}/${player.hp.max}`}
            pct={toBarPct(player.hp.cur, player.hp.max)}
          />
          <BarRow
            icon={CHARACTER_STATUS_ASSETS.expicon}  iconW={12} iconH={12}
            color="#22aa22"
            text={`${player.exp.cur}%`}
            pct={player.exp.cur}
          />
          <BarRow
            icon={CHARACTER_STATUS_ASSETS.gold}    iconW={13} iconH={9}
            color="#e6cc9d"
            text={`${player.quanProgress.cur}/${player.quanProgress.max}`}
            pct={toBarPct(player.quanProgress.cur, player.quanProgress.max)}
          />
        </View>

        {/* ── 4 CHỈ SỐ CƠ BẢN ────────────────────────────────────── */}
        <View style={styles.attrSection}>
          {STAT_LABELS.map((label, i) => (
            <AttrRow
              key={label}
              label={label}
              value={STAT_VALUES[i]}
              primary={i === primaryRow}
            />
          ))}
        </View>

        {/* Điểm */}
        <View style={styles.pointsRow}>
          <Text style={styles.pointsLabel}>Điểm</Text>
          <View style={styles.pointsBox}>
            <Text style={styles.pointsValue}>{player.points}</Text>
          </View>
        </View>

        <DecorativeDivider />

        {/* ── COMBAT STATS: 2 cột 3 hàng ─────────────────────────── */}
        <View style={styles.combatGrid}>
          <View style={styles.combatCol}>
            <CombatStat label="Tấn Công"  value={String(player.combat.attack)} />
            <CombatStat label="Chính xác" value={String(player.combat.acc)} />
            <CombatStat label="Sinh lực"  value={String(player.combat.hp)} />
          </View>
          <View style={styles.combatCol}>
            <CombatStat label="P.Thủ"    value={String(player.combat.def)} />
            <CombatStat label="Né Tránh" value={String(player.combat.dodge)} />
            <CombatStat label="Chí Mạng" value={player.combat.crit} />
          </View>
        </View>
      </CornerFrame>

      {/* ── Softkeys ─────────────────────────────────────────────────── */}
      <View style={styles.softKeyBar}>
        <PopupMenu
          visible={menuVisible}
          items={COMMON_MENU_ITEMS}
          selectedIndex={selectedIndex}
          onSelect={(item) => { setMenuVisible(false); if (item.id === 0) onLogout(); }}
          onIndexChange={setSelectedIndex}
          onClose={() => setMenuVisible(false)}
          bottomOffset={28}
        />
        <SoftkeyBar
          width={SCREEN_WIDTH}
          centerLabel="Bắt đầu"
          onCenterPress={onStart}
          onLeftPress={() => setMenuVisible(true)}
          onRightPress={menuVisible ? () => setMenuVisible(false) : undefined}
          isMenuOpen={menuVisible}
        />
      </View>
    </View>
  );
};
