import React, { useEffect, useMemo, useState } from 'react';
import {
  Dimensions,
  Image,
  Pressable,
  ScrollView,
  Text,
  TouchableOpacity,
  View,
} from 'react-native';
import { PopupMenu } from '../../../components/controls/PopupMenu/PopupMenu';
import { CornerFrame } from '../../../components/ui/CornerFrame/CornerFrame';
import { CharacterRenderer } from '../../character/CharacterRenderer';
import { CHARACTER_STATUS_ASSETS } from '../../character/status/assets';
import type {
  CharacterAppearance,
  CharacterEquipmentItem,
  CharacterInventoryItem,
  CharacterSkillNode,
} from '../../character/shared';
import { resolveEquipmentIconAsset } from '../../character/shared';
import {
  BATTLE_SKILLS,
  getSkillFamiliesForElement,
  type SkillFamilyCode,
} from '../../battle/core/BattleScreen.skills';
import { styles } from './MapCharacterDialogs.styles';

const { width: SCREEN_W, height: SCREEN_H } = Dimensions.get('window');
const EI_TOTAL_W = 60;
const EI_H = 15;
const EI_FRAME_W = EI_TOTAL_W / 4;
const EI_SCALE = 1.3;
const EI_DISP_W = Math.round(EI_FRAME_W * EI_SCALE);
const EI_DISP_H = Math.round(EI_H * EI_SCALE);
const INFO_ASSETS = {
  skilltree: require('../../../../assets/skill/00_skill_tree_ui_confirmed/skill_tree_board/increase.png'),
  hidenobj: require('../../../../assets/ui/12_info/hidenobj.png'),
  itemchest: require('../../../../assets/ui/12_info/itemchest.png'),
};

const INVENTORY_ITEM_ASSETS: Record<string, ReturnType<typeof require>> = {
  potion_red: require('../../../../assets/items/hp.png'),
  potion_blue: require('../../../../assets/items/mp.png'),
  peach: require('../../../../assets/items/ostrich_egg.png'),
  chicken_egg: require('../../../../assets/items/chicken_egg.png'),
  ostrich_egg: require('../../../../assets/items/ostrich_egg.png'),
  dinosaur_egg: require('../../../../assets/items/dinosaur_egg.png'),
  phoenix_egg: require('../../../../assets/items/phoenix_egg.png'),
  dragon_egg: require('../../../../assets/items/dragon_egg.png'),
  hammer: require('../../../../assets/items/repair_hammer.png'),
  repair_hammer: require('../../../../assets/items/repair_hammer.png'),
  kim_thach: require('../../../../assets/items/kim_thach.png'),
  huyet_thach: require('../../../../assets/items/huyet_thach.png'),
  charm_1: require('../../../../assets/items/charm_1.png'),
  charm_2: require('../../../../assets/items/charm_2.png'),
  charm_3: require('../../../../assets/items/charm_3.png'),
};

const SKILL_UI_ASSETS = {
  increase: require('../../../../assets/skill/00_skill_tree_ui_confirmed/skill_tree_board/increase.png'),
  decrease: require('../../../../assets/skill/00_skill_tree_ui_confirmed/skill_tree_board/decrease.png'),
};
const HUD_ASSETS = {
  btinscrease: require('../../../../assets/hud/01_button_markers/btinscrease.png'),
};
const HIDDEN_SLOT_SIZE = 47;
const STAT_ARROW_FRAME_SIZE = 12;

export type MapCharacterDialogKind =
  | 'info'
  | 'potential'
  | 'skills'
  | 'equipment'
  | 'inventory';

export type CharacterStatKey = 'CuongLuc' | 'ThanPhap' | 'NoiLuc' | 'TheLuc';
type DialogActionRunner = () => Promise<string | null> | undefined;

interface MapCharacterDialogsProps {
  activeDialog: MapCharacterDialogKind | null;
  appearance: CharacterAppearance;
  onClose: () => void;
  onAllocateStat?: (stat: CharacterStatKey) => Promise<string | null>;
  onAllocateSkill?: (familyCode: number) => Promise<string | null>;
  onToggleEquipment?: (equipKey: string, equip: boolean) => Promise<string | null>;
  onPreviewEquipmentLoadout?: (equipKeys: string[]) => Promise<CharacterAppearance | null>;
  onCommitEquipmentLoadout?: (equipKeys: string[]) => Promise<string | null>;
  onUseItem?: (itemId: number) => Promise<string | null>;
  onDiscardEquipment?: (equipKey: string) => Promise<string | null>;
  onDiscardItem?: (itemId: number, quantity: number) => Promise<string | null>;
  onRepairEquipment?: (equipKey: string) => Promise<string | null>;
}

const PRIMARY_STAT: Record<number, number> = { 0: 0, 1: 2, 2: 1 };
const STAT_ROWS: Array<{ label: string; key: CharacterStatKey; valueKey: keyof NonNullable<CharacterAppearance['stats']> }> = [
  { label: 'Cường Lực', key: 'CuongLuc', valueKey: 'cuongLuc' },
  { label: 'Nội Lực', key: 'NoiLuc', valueKey: 'noiLuc' },
  { label: 'Thân Pháp', key: 'ThanPhap', valueKey: 'thanPhap' },
  { label: 'Thể Lực', key: 'TheLuc', valueKey: 'theLuc' },
];
const EQUIPMENT_SLOT_NAMES: Record<number, string> = {
  0: 'Áo',
  1: 'Vũ khí',
  2: 'Nón',
  3: 'Giày',
  4: 'Ngựa/Khiên',
  5: 'Nhẫn',
  7: 'Bùa',
  8: 'Bùa',
};

const formatQuan = (value: number) =>
  `${String(Math.max(0, Math.floor(value))).replace(/\B(?=(\d{3})+(?!\d))/g, '.')} Quan`;

const toBarPct = (cur: number, max: number) => {
  if (max <= 0) {
    return 0;
  }

  return Math.max(0, Math.min(100, (cur * 100) / max));
};

const formatSigned = (value: number, suffix = '') => `${value > 0 ? '+' : ''}${value}${suffix}`;

const resolveInventoryItemIcon = (item: CharacterInventoryItem) => {
  if (item.iconKind in INVENTORY_ITEM_ASSETS) {
    return INVENTORY_ITEM_ASSETS[item.iconKind];
  }

  return INFO_ASSETS.itemchest;
};

const getEquipmentBonusRows = (entry: CharacterEquipmentItem) => [
  entry.bonusCuongLuc ? `Cường Lực ${formatSigned(entry.bonusCuongLuc)}` : null,
  entry.bonusNoiLuc ? `Nội Lực ${formatSigned(entry.bonusNoiLuc)}` : null,
  entry.bonusThanPhap ? `Thân Pháp ${formatSigned(entry.bonusThanPhap)}` : null,
  entry.bonusTheLuc ? `Thể Lực ${formatSigned(entry.bonusTheLuc)}` : null,
  entry.bonusAttack ? `Tấn Công ${formatSigned(entry.bonusAttack)}` : null,
  entry.bonusAttackPercent ? `Tấn Công ${formatSigned(entry.bonusAttackPercent, '%')}` : null,
  entry.bonusDefense ? `P.Thủ ${formatSigned(entry.bonusDefense)}` : null,
  entry.bonusDodge ? `Né Tránh ${formatSigned(entry.bonusDodge)}` : null,
  entry.bonusCrit ? `Chí Mạng ${formatSigned(entry.bonusCrit, '%')}` : null,
  entry.bonusMaxHp ? `Sinh lực ${formatSigned(entry.bonusMaxHp)}` : null,
].filter((row): row is string => Boolean(row));

const parseCombatNumber = (value: number | string | undefined) => {
  if (typeof value === 'number') {
    return value;
  }

  const parsed = Number(String(value ?? '0').replace(/[^\d-]/g, ''));
  return Number.isFinite(parsed) ? parsed : 0;
};

const getCombatPreviewRows = (
  current?: CharacterAppearance['combat'],
  preview?: CharacterAppearance['combat'],
) => {
  if (!current || !preview) {
    return [];
  }

  const rows = [
    ['Tấn Công', parseCombatNumber(preview.attack) - parseCombatNumber(current.attack)],
    ['P.Thủ', parseCombatNumber(preview.def) - parseCombatNumber(current.def)],
    ['Sinh lực', parseCombatNumber(preview.hp) - parseCombatNumber(current.hp)],
    ['Né Tránh', parseCombatNumber(preview.dodge) - parseCombatNumber(current.dodge)],
    ['Chính xác', parseCombatNumber(preview.acc) - parseCombatNumber(current.acc)],
    ['Chí Mạng', parseCombatNumber(preview.crit) - parseCombatNumber(current.crit)],
  ] as const;

  return rows.filter(([, delta]) => delta !== 0);
};

const resolveEquipmentIcon = (entry: CharacterEquipmentItem) => resolveEquipmentIconAsset(entry);

const valueBox = (value: React.ReactNode, wide = false) => (
  <View style={[styles.valueBox, wide && styles.valueBoxWide]}>
    <Text style={styles.valueText} numberOfLines={1}>{value}</Text>
  </View>
);

const Divider = () => (
  <View style={styles.divider}>
    <View style={styles.dividerLine} />
    <Text style={styles.dividerMark}>Ꮚ</Text>
    <View style={styles.dividerLine} />
  </View>
);

const ElementIcon: React.FC<{ elementIndex: number }> = ({ elementIndex }) => (
  <View style={{ width: EI_DISP_W, height: EI_DISP_H, overflow: 'hidden' }}>
    <Image
      source={CHARACTER_STATUS_ASSETS.elementsicon}
      resizeMode="stretch"
      style={{
        position: 'absolute',
        left: -(elementIndex * EI_FRAME_W * EI_SCALE),
        top: 0,
        width: EI_TOTAL_W * EI_SCALE,
        height: EI_DISP_H,
      }}
    />
  </View>
);

const StatusBarRow: React.FC<{
  icon: any;
  iconW: number;
  iconH: number;
  color: string;
  text: string;
  pct: number;
}> = ({ icon, iconW, iconH, color, text, pct }) => (
  <View style={styles.statusBarRow}>
    <View style={styles.statusBarIconCell}>
      <Image source={icon} style={{ width: iconW * 1.2, height: iconH * 1.2 }} resizeMode="contain" />
    </View>
    <View style={styles.statusBarWrapper}>
      <View style={[styles.statusBarFill, { backgroundColor: color, width: `${Math.min(pct, 100)}%` as `${number}%` }]} />
      <Text style={styles.statusBarText}>{text}</Text>
    </View>
  </View>
);

const ActionButton: React.FC<{
  label: string;
  onPress?: () => void;
  disabled?: boolean;
}> = ({ label, onPress, disabled }) => (
  <TouchableOpacity
    activeOpacity={0.85}
    onPress={onPress}
    disabled={disabled}
    style={[styles.actionButton, disabled && styles.actionButtonDisabled]}
  >
    <Text style={[styles.actionButtonText, disabled && styles.actionButtonTextDisabled]}>{label}</Text>
  </TouchableOpacity>
);

const StatArrowButton: React.FC<{
  frameIndex: number;
  onPress?: () => void;
  disabled?: boolean;
}> = ({ frameIndex, onPress, disabled }) => (
  <TouchableOpacity
    activeOpacity={0.85}
    onPress={onPress}
    disabled={disabled}
    style={[styles.statArrowButton, disabled && styles.statArrowButtonDisabled]}
  >
    <View style={styles.statArrowFrame}>
      <Image
        source={HUD_ASSETS.btinscrease}
        resizeMode="stretch"
        style={[
          styles.statArrowSheet,
          { left: -(Math.max(0, Math.min(3, frameIndex)) * STAT_ARROW_FRAME_SIZE) },
        ]}
      />
    </View>
  </TouchableOpacity>
);

const createPlayerModel = (appearance: CharacterAppearance) => ({
  username: appearance.username ?? 'Nhân vật',
  level: appearance.level ?? 1,
  quanHam: appearance.quanHam ?? 'Tân Binh',
  xepHang: appearance.xepHang ?? 'Chưa có',
  danhVong: appearance.danhVong ?? 0,
  thangThua: appearance.thangThua ?? '0/0',
  walletQuan: appearance.walletQuan ?? 0,
  hp: appearance.hp ?? { cur: 100, max: 100 },
  exp: appearance.exp ?? { cur: 0, max: 100 },
  quanProgress: appearance.quanProgress ?? { cur: 0, max: 10000 },
  stats: appearance.stats ?? { cuongLuc: 10, noiLuc: 10, thanPhap: 10, theLuc: 10 },
  freePoints: appearance.freePoints ?? appearance.points ?? 0,
  skillPoints: appearance.skillPoints ?? 0,
  combat: appearance.combat ?? { attack: 0, def: 0, acc: 0, dodge: 0, hp: 0, crit: '0%' },
  skills: appearance.skills ?? [],
  equipment: appearance.equipment ?? [],
  inventory: appearance.inventory ?? [],
});

const InfoDialog: React.FC<{ appearance: CharacterAppearance }> = ({ appearance }) => {
  const player = createPlayerModel(appearance);

  return (
    <View style={styles.panelBody}>
      <View style={styles.titleRow}>
        <Text style={styles.titleText}>{player.username}</Text>
        <Text style={styles.titleSubText}>Cấp:{player.level}</Text>
      </View>
      <View style={styles.infoRows}>
        <View style={styles.infoRow}><Text style={styles.infoLabel}>Quân Hàm</Text>{valueBox(player.quanHam, true)}</View>
        <View style={styles.infoRow}><Text style={styles.infoLabel}>Xếp Hạng</Text>{valueBox(player.xepHang, true)}</View>
        <View style={styles.infoRow}><Text style={styles.infoLabel}>Danh vọng</Text>{valueBox(player.danhVong, true)}</View>
        <View style={styles.infoRow}><Text style={styles.infoLabel}>Thắng/Thua</Text>{valueBox(player.thangThua, true)}</View>
        <View style={styles.infoRow}><Text style={styles.infoLabel}>Quan</Text>{valueBox(formatQuan(player.walletQuan), true)}</View>
      </View>
      <Divider />
      <View style={styles.combatGrid}>
        <View style={styles.combatCol}>
          <View style={styles.combatRow}><Text style={styles.combatLabel}>Tấn Công</Text>{valueBox(player.combat.attack)}</View>
          <View style={styles.combatRow}><Text style={styles.combatLabel}>Chính xác</Text>{valueBox(player.combat.acc)}</View>
          <View style={styles.combatRow}><Text style={styles.combatLabel}>Sinh lực</Text>{valueBox(player.combat.hp)}</View>
        </View>
        <View style={styles.combatCol}>
          <View style={styles.combatRow}><Text style={styles.combatLabel}>P.Thủ</Text>{valueBox(player.combat.def)}</View>
          <View style={styles.combatRow}><Text style={styles.combatLabel}>Né Tránh</Text>{valueBox(player.combat.dodge)}</View>
          <View style={styles.combatRow}><Text style={styles.combatLabel}>Chí Mạng</Text>{valueBox(player.combat.crit)}</View>
        </View>
      </View>
    </View>
  );
};

const PotentialDialog: React.FC<{
  appearance: CharacterAppearance;
  pending: string | null;
  onRunAction: (key: string, runner?: DialogActionRunner) => void;
  onAllocateStat?: (stat: CharacterStatKey) => Promise<string | null>;
}> = ({ appearance, pending, onRunAction, onAllocateStat }) => {
  const player = createPlayerModel(appearance);
  const elementIndex = appearance.elementIndex ?? 0;
  const primaryRow = PRIMARY_STAT[appearance.elementIndex ?? 0] ?? 0;

  return (
    <View style={[styles.panelBody, styles.potentialBody]}>
      <View style={styles.statusHeader}>
        <View style={styles.statusNameRow}>
          <ElementIcon elementIndex={elementIndex} />
          <Text style={styles.statusUsername} numberOfLines={1}>{player.username}</Text>
          <Text style={styles.statusLevel}>Cấp:{player.level}</Text>
        </View>

        <View style={styles.statusHeaderContent}>
          <View style={styles.statusAvatarPanel}>
            <View style={styles.statusAvatarBox}>
              <View style={styles.statusAvatarInner}>
                <CharacterRenderer
                  appearance={appearance}
                  scale={1.5}
                  style={{ position: 'relative', bottom: 4 }}
                />
              </View>
            </View>
            <Text style={styles.statusWalletText} numberOfLines={1}>{formatQuan(player.walletQuan)}</Text>
          </View>

          <View style={styles.statusInfoPanel}>
            <View style={styles.statusInfoRow}><Text style={styles.statusInfoLabel}>Quân Hàm</Text>{valueBox(player.quanHam, true)}</View>
            <View style={styles.statusInfoRow}><Text style={styles.statusInfoLabel}>Xếp Hạng</Text>{valueBox(player.xepHang, true)}</View>
            <View style={styles.statusInfoRow}><Text style={styles.statusInfoLabel}>Danh vọng</Text>{valueBox(player.danhVong, true)}</View>
            <View style={styles.statusInfoRow}><Text style={styles.statusInfoLabel}>Thắng/Thua</Text>{valueBox(player.thangThua, true)}</View>
          </View>
        </View>
      </View>

      <Divider />
      <View style={styles.statusBars}>
        <StatusBarRow
          icon={CHARACTER_STATUS_ASSETS.heart}
          iconW={13}
          iconH={13}
          color="#dd1111"
          text={`${player.hp.cur}/${player.hp.max}`}
          pct={toBarPct(player.hp.cur, player.hp.max)}
        />
        <StatusBarRow
          icon={CHARACTER_STATUS_ASSETS.expicon}
          iconW={12}
          iconH={12}
          color="#22aa22"
          text={`${player.exp.cur}%`}
          pct={player.exp.cur}
        />
        <StatusBarRow
          icon={CHARACTER_STATUS_ASSETS.gold}
          iconW={13}
          iconH={9}
          color="#e6cc9d"
          text={`${player.quanProgress.cur}/${player.quanProgress.max}`}
          pct={toBarPct(player.quanProgress.cur, player.quanProgress.max)}
        />
      </View>

      <View style={styles.statRows}>
        {STAT_ROWS.map((row, index) => (
          <View key={row.key} style={styles.statRow}>
            <Text style={[styles.statLabel, index === primaryRow && styles.primaryText]}>{row.label}</Text>
            {valueBox(player.stats[row.valueKey], true)}
            <StatArrowButton
              frameIndex={0}
              disabled={player.freePoints <= 0 || pending !== null}
              onPress={() => onRunAction(`stat-${row.key}`, () => onAllocateStat?.(row.key))}
            />
            <StatArrowButton frameIndex={2} disabled />
          </View>
        ))}
      </View>
      <View style={styles.statusPointsRow}>
        <Text style={styles.statusPointsLabel}>Điểm</Text>
        <View style={styles.statusPointsBox}>
          <Text style={styles.statusPointsValue}>{player.freePoints}</Text>
        </View>
      </View>

      <Divider />
      <View style={styles.combatGrid}>
        <View style={styles.combatCol}>
          <View style={styles.combatRow}><Text style={styles.combatLabel}>Tấn Công</Text>{valueBox(player.combat.attack)}</View>
          <View style={styles.combatRow}><Text style={styles.combatLabel}>Chính xác</Text>{valueBox(player.combat.acc)}</View>
          <View style={styles.combatRow}><Text style={styles.combatLabel}>Sinh lực</Text>{valueBox(player.combat.hp)}</View>
        </View>
        <View style={styles.combatCol}>
          <View style={styles.combatRow}><Text style={styles.combatLabel}>P.Thủ</Text>{valueBox(player.combat.def)}</View>
          <View style={styles.combatRow}><Text style={styles.combatLabel}>Né Tránh</Text>{valueBox(player.combat.dodge)}</View>
          <View style={styles.combatRow}><Text style={styles.combatLabel}>Chí Mạng</Text>{valueBox(player.combat.crit)}</View>
        </View>
      </View>
    </View>
  );
};

const SkillNode: React.FC<{
  familyCode: SkillFamilyCode;
  node?: CharacterSkillNode;
  canUpgrade: boolean;
  disabled: boolean;
  selected?: boolean;
  onPress: () => void;
  onUpgrade: () => void;
  style: object;
}> = ({ familyCode, node, canUpgrade, disabled, selected, onPress, onUpgrade, style }) => {
  const skill = BATTLE_SKILLS[familyCode];
  const level = node?.level ?? 0;

  return (
    <Pressable onPress={onPress} style={[styles.skillNode, style]}>
      {selected && (
        <View style={styles.inventorySelectedFrame} pointerEvents="none">
          <View style={[styles.skillNodeCorner, styles.skillNodeCornerTopLeft]} />
          <View style={[styles.skillNodeCorner, styles.skillNodeCornerTopRight]} />
          <View style={[styles.skillNodeCorner, styles.skillNodeCornerBottomLeft]} />
          <View style={[styles.skillNodeCorner, styles.skillNodeCornerBottomRight]} />
        </View>
      )}
      <Image source={skill.icon} style={styles.skillIcon} resizeMode="stretch" />
      <Text style={styles.skillLevel}>{level}</Text>
      {canUpgrade && !disabled && (
        <TouchableOpacity
          activeOpacity={0.85}
          onPress={onUpgrade}
          style={styles.skillUpgradeButton}
        >
          <Image source={SKILL_UI_ASSETS.increase} style={styles.skillUpgradeIcon} resizeMode="contain" />
        </TouchableOpacity>
      )}
    </Pressable>
  );
};

const SkillTreeBackground = () => (
  <View style={styles.skillTreeBackground} pointerEvents="none">
    <Image source={INFO_ASSETS.skilltree} style={styles.skillTreeHalf} resizeMode="stretch" />
    <Image
      source={INFO_ASSETS.skilltree}
      style={[styles.skillTreeHalf, styles.skillTreeHalfMirror]}
      resizeMode="stretch"
    />
  </View>
);

const SKILL_TREE_SCALE = 1.45;
const SKILL_POSITIONS = [
  [95.2, 11],
  [95.2, 101],
  [95.2, 152],
  [29, 76],
  [29, 127],
  [29, 178],
  [160, 76],
  [160, 127],
  [160, 178],
].map(([left, top]) => ({
  left: Math.round(left * SKILL_TREE_SCALE),
  top: Math.round(top * SKILL_TREE_SCALE),
}));

const SkillsDialog: React.FC<{
  appearance: CharacterAppearance;
  pending: string | null;
  onRunAction: (key: string, runner?: DialogActionRunner) => void;
  onAllocateSkill?: (familyCode: number) => Promise<string | null>;
}> = ({ appearance, pending, onRunAction, onAllocateSkill }) => {
  const player = createPlayerModel(appearance);
  const families = getSkillFamiliesForElement(appearance.elementIndex).map(skill => skill.familyCode);
  const skillsByCode = useMemo(
    () => new Map(player.skills.map(skill => [skill.familyCode, skill])),
    [player.skills],
  );
  const [selectedFamilyCode, setSelectedFamilyCode] = useState<SkillFamilyCode>(families[0]);
  const selectedSkill = BATTLE_SKILLS[selectedFamilyCode];
  const selectedNode = skillsByCode.get(selectedFamilyCode);

  return (
    <View style={[styles.panelBody, styles.skillsBody]}>
      <View style={styles.titleRowCentered}>
        <Text style={styles.titleText}>Điểm Kỹ Năng</Text>
        {valueBox(player.skillPoints)}
      </View>
      <Divider />
      <View style={styles.skillTree}>
        <SkillTreeBackground />
        {families.map((familyCode, index) => {
          const node = skillsByCode.get(familyCode);
          return (
            <SkillNode
              key={familyCode}
              familyCode={familyCode}
              node={node}
              canUpgrade={node?.canUpgrade ?? player.skillPoints > 0}
              disabled={pending !== null}
              selected={selectedFamilyCode === familyCode}
              onPress={() => setSelectedFamilyCode(familyCode)}
              onUpgrade={() => onRunAction(`skill-${familyCode}`, () => onAllocateSkill?.(familyCode))}
              style={SKILL_POSITIONS[index]}
            />
          );
        })}
      </View>
      <Divider />
      <View style={styles.skillInfoCompact}>
        <Text style={styles.skillInfoTitle}>Kỹ năng chưa đặt tên</Text>
      </View>
    </View>
  );
};

const EquipmentDialog: React.FC<{
  appearance: CharacterAppearance;
  pending: string | null;
  onRunAction: (key: string, runner?: DialogActionRunner) => void;
  onToggleEquipment?: (equipKey: string, equip: boolean) => Promise<string | null>;
  onPreviewEquipmentLoadout?: (equipKeys: string[]) => Promise<CharacterAppearance | null>;
  onCommitEquipmentLoadout?: (equipKeys: string[]) => Promise<string | null>;
  onUseItem?: (itemId: number) => Promise<string | null>;
  onDiscardEquipment?: (equipKey: string) => Promise<string | null>;
  onDiscardItem?: (itemId: number, quantity: number) => Promise<string | null>;
  onRepairEquipment?: (equipKey: string) => Promise<string | null>;
}> = ({ appearance, pending, onRunAction, onToggleEquipment, onPreviewEquipmentLoadout, onCommitEquipmentLoadout, onUseItem, onDiscardEquipment, onDiscardItem, onRepairEquipment }) => {
  return (
    <InventoryShell
      appearance={appearance}
      pending={pending}
      onRunAction={onRunAction}
      onToggleEquipment={onToggleEquipment}
      onPreviewEquipmentLoadout={onPreviewEquipmentLoadout}
      onCommitEquipmentLoadout={onCommitEquipmentLoadout}
      onUseItem={onUseItem}
      onDiscardEquipment={onDiscardEquipment}
      onDiscardItem={onDiscardItem}
      onRepairEquipment={onRepairEquipment}
    />
  );
};

type InventoryCell =
  | { kind: 'equipment'; key: string; entry: CharacterEquipmentItem }
  | { kind: 'item'; key: string; item: CharacterInventoryItem }
  | { kind: 'empty'; key: string };
type EquipmentCell = Extract<InventoryCell, { kind: 'equipment' }>;
const EMPTY_EQUIPMENT: CharacterEquipmentItem[] = [];
type InventoryActionMenuState = {
  cellKey: string;
  left: number;
  top: number;
};
type InventoryActionMenuItem = {
  id: string;
  label: string;
  disabled?: boolean;
  onPress?: () => void;
};

const HiddenEquipmentIcon: React.FC<{ slot: number }> = ({ slot }) => (
  <View style={styles.hiddenEquipmentIcon}>
    <Image
      source={INFO_ASSETS.hidenobj}
      resizeMode="stretch"
      style={[
        styles.hiddenEquipmentSheet,
        { left: -(Math.max(0, Math.min(5, slot)) * HIDDEN_SLOT_SIZE) },
      ]}
    />
  </View>
);

const EquipmentSlot: React.FC<{
  slot: number;
  entry?: CharacterEquipmentItem;
  selected?: boolean;
  onPress?: () => void;
  style?: object;
}> = ({ slot, entry, selected, onPress, style }) => (
  <Pressable onPress={onPress} style={[styles.inventoryCell, entry ? styles.inventoryCellFilled : styles.inventoryCellEmpty, style]}>
    {selected && (
      <View style={styles.inventorySelectedFrame} pointerEvents="none">
        <View style={[styles.skillNodeCorner, styles.skillNodeCornerTopLeft]} />
        <View style={[styles.skillNodeCorner, styles.skillNodeCornerTopRight]} />
        <View style={[styles.skillNodeCorner, styles.skillNodeCornerBottomLeft]} />
        <View style={[styles.skillNodeCorner, styles.skillNodeCornerBottomRight]} />
      </View>
    )}
    {entry ? (
      <Image source={resolveEquipmentIcon(entry) ?? INFO_ASSETS.itemchest} style={styles.equipmentItemIcon} resizeMode="contain" />
    ) : (
      <HiddenEquipmentIcon slot={slot} />
    )}
  </Pressable>
);

const InventoryGridCell: React.FC<{
  cell: InventoryCell;
  selected: boolean;
  onPress: () => void;
  style: object;
}> = ({ cell, selected, onPress, style }) => (
  <Pressable onPress={onPress} style={[styles.inventoryCell, cell.kind !== 'empty' ? styles.inventoryCellFilled : styles.inventoryCellEmpty, style]}>
    {selected && (
      <View style={styles.inventorySelectedFrame} pointerEvents="none">
        <View style={[styles.skillNodeCorner, styles.skillNodeCornerTopLeft]} />
        <View style={[styles.skillNodeCorner, styles.skillNodeCornerTopRight]} />
        <View style={[styles.skillNodeCorner, styles.skillNodeCornerBottomLeft]} />
        <View style={[styles.skillNodeCorner, styles.skillNodeCornerBottomRight]} />
      </View>
    )}
    {cell.kind === 'empty' ? null : (
      <>
        <Image
          source={cell.kind === 'equipment' ? resolveEquipmentIcon(cell.entry) ?? INFO_ASSETS.itemchest : resolveInventoryItemIcon(cell.item)}
          style={styles.inventoryCellIconImage}
          resizeMode="contain"
        />
        {cell.kind === 'item' ? (
          <Text style={styles.inventoryCellCount} numberOfLines={1}>{cell.item.quantity}</Text>
        ) : null}
      </>
    )}
  </Pressable>
);

const InventoryActionMenu: React.FC<{
  left: number;
  top: number;
  items: InventoryActionMenuItem[];
}> = ({ left, top, items }) => {
  const selectedIndex = Math.max(0, items.findIndex(item => !item.disabled));

  return (
    <View style={[styles.inventoryActionMenu, { left, top }]}>
      {items.map((item, index) => {
        const selected = index === selectedIndex && !item.disabled;
        return (
          <TouchableOpacity
            key={item.id}
            activeOpacity={1}
            disabled={item.disabled}
            onPress={item.onPress}
            style={[styles.inventoryActionMenuItem, selected && styles.inventoryActionMenuItemSelected]}
          >
            <Text
              style={[
                styles.inventoryActionMenuText,
                selected && styles.inventoryActionMenuTextSelected,
                item.disabled && styles.inventoryActionMenuTextDisabled,
              ]}
              numberOfLines={1}
            >
              {item.label}
            </Text>
          </TouchableOpacity>
        );
      })}
    </View>
  );
};

const InventoryDetailPanel: React.FC<{
  visible: boolean;
  onClose: () => void;
  playerLevel: number;
  playerGender: number;
  currentCombat?: CharacterAppearance['combat'];
  previewCombat?: CharacterAppearance['combat'];
  selected?: InventoryCell;
  pending: string | null;
}> = ({
  visible,
  onClose,
  playerLevel,
  playerGender,
  currentCombat,
  previewCombat,
  selected,
  pending,
}) => {
  if (!visible || !selected) {
    return null;
  }

  if (selected.kind === 'equipment') {
    const entry = selected.entry;
    const canEquip = entry.isEquipped || (
      playerLevel >= entry.requiredLevel
      && (entry.gender === 2 || entry.gender === undefined || entry.gender === playerGender)
    );
    const bonusRows = getEquipmentBonusRows(entry);
    const previewRows = getCombatPreviewRows(currentCombat, previewCombat);
    const isBroken = entry.isBroken;

    return (
      <View style={[styles.inventoryDetailPanel, { backgroundColor: '#ffffff', zIndex: 10000 }]}>
        <View style={styles.inventoryDetailHeader}>
          <Text style={styles.inventoryDetailTitle} numberOfLines={1}>{entry.displayName}</Text>
          <TouchableOpacity onPress={onClose}>
            <Image source={require('../../../../assets/ui/11_softkey_icons_confirmed/icon_cancel.png')} style={styles.inventoryDetailCloseIcon} />
          </TouchableOpacity>
        </View>
        <View style={styles.inventoryDetailContent}>
          <View style={{ flexDirection: 'row', justifyContent: 'space-between', marginBottom: 4 }}>
            <Text style={styles.inventoryDetailMeta}>
              {EQUIPMENT_SLOT_NAMES[entry.slot] ?? 'Trang bị'}
            </Text>
            <Text style={styles.inventoryDetailMeta}>
              Cấp {entry.level}
            </Text>
          </View>
          <Text style={styles.inventoryDetailText}>
            Yêu cầu cấp: {entry.requiredLevel}
          </Text>
          <Text style={[styles.inventoryDetailText, isBroken && { color: '#ef4444', fontWeight: 'bold' }]}>
            Độ bền: {entry.durability}/{entry.maxDurability} {isBroken ? '(Đã hỏng - không cộng chỉ số)' : ''}
          </Text>
          {entry.isEquipped ? (
            <Text style={[styles.inventoryDetailText, !entry.contributesStats && { color: '#ef4444' }]}>
              Hiệu lực: {entry.contributesStats ? 'Đang cộng chỉ số' : 'Không cộng chỉ số/effect'}
            </Text>
          ) : null}
          <View style={styles.inventoryBonusGrid}>
            {(previewRows.length > 0
              ? previewRows.map(([label, delta]) => `${label} ${formatSigned(delta)}`)
              : bonusRows.length > 0
                ? bonusRows
                : [entry.summary]
            ).map(row => (
              <Text key={row} style={styles.inventoryBonusText} numberOfLines={1}>+ {row}</Text>
            ))}
          </View>
          {!canEquip && pending === null ? (
            <Text style={styles.inventoryDetailWarn} numberOfLines={1}>
              Cần 1 búa để sửa chữa
            </Text>
          ) : null}
        </View>
      </View>
    );
  }

  if (selected.kind === 'item') {
    const item = selected.item;

    return (
      <View style={[styles.inventoryDetailPanel, { backgroundColor: '#ffffff', zIndex: 10000 }]}>
        <TouchableOpacity style={styles.inventoryDetailClose} onPress={onClose}>
          <Image source={require('../../../../assets/ui/11_softkey_icons_confirmed/icon_cancel.png')} style={styles.inventoryDetailCloseIcon} />
        </TouchableOpacity>
        <View style={styles.inventoryDetailHeader}>
          <Text style={styles.inventoryDetailTitle} numberOfLines={1}>{item.displayName}</Text>
        </View>
        <Text style={styles.inventoryDetailMeta}>Số lượng: {item.quantity}/{item.stackCap}</Text>
        <Text style={styles.inventoryDetailText} numberOfLines={3}>{item.description}</Text>
        {item.isUsable ? (
          <Text style={styles.inventoryBonusText} numberOfLines={1}>Hồi {item.healAmount} sinh lực</Text>
        ) : null}
      </View>
    );
  }

  return null;
};

const buildEquippedKeySet = (equipment: CharacterEquipmentItem[]) =>
  new Set(equipment.filter(entry => entry.isEquipped).map(entry => entry.equipKey));

const sameKeySet = (left: Set<string>, right: Set<string>) => {
  if (left.size !== right.size) {
    return false;
  }

  for (const key of left) {
    if (!right.has(key)) {
      return false;
    }
  }

  return true;
};

const clampActionMenuLeft = (left: number) => Math.max(6, Math.min(258, left));
const clampActionMenuTop = (top: number) => Math.max(130, Math.min(430, top));

const REPAIR_HAMMER_ITEM_ID = 30099;

const InventoryShell: React.FC<{
  appearance: CharacterAppearance;
  pending: string | null;
  onRunAction: (key: string, runner?: DialogActionRunner) => void;
  onToggleEquipment?: (equipKey: string, equip: boolean) => Promise<string | null>;
  onPreviewEquipmentLoadout?: (equipKeys: string[]) => Promise<CharacterAppearance | null>;
  onCommitEquipmentLoadout?: (equipKeys: string[]) => Promise<string | null>;
  onUseItem?: (itemId: number) => Promise<string | null>;
  onDiscardEquipment?: (equipKey: string) => Promise<string | null>;
  onDiscardItem?: (itemId: number, quantity: number) => Promise<string | null>;
  onRepairEquipment?: (equipKey: string) => Promise<string | null>;
}> = ({ appearance, pending, onRunAction, onToggleEquipment, onPreviewEquipmentLoadout, onCommitEquipmentLoadout, onUseItem, onDiscardEquipment, onDiscardItem, onRepairEquipment }) => {
  const player = createPlayerModel(appearance);
  const equipmentSource = appearance.equipment ?? EMPTY_EQUIPMENT;
  const serverEquippedKeys = useMemo(
    () => buildEquippedKeySet(equipmentSource),
    [equipmentSource],
  );
  const equipmentSignature = equipmentSource.map(entry => `${entry.equipKey}:${entry.isEquipped ? 1 : 0}`).join('|');
  const [draftEquippedKeys, setDraftEquippedKeys] = useState(() => buildEquippedKeySet(equipmentSource));
  const [previewRuntime, setPreviewRuntime] = useState<CharacterAppearance | null>(null);

  useEffect(() => {
    setDraftEquippedKeys(buildEquippedKeySet(equipmentSource));
    setPreviewRuntime(null);
  }, [equipmentSignature, equipmentSource]);

  const previewEquipment = equipmentSource.map(entry => ({
    ...entry,
    isEquipped: draftEquippedKeys.has(entry.equipKey),
  }));
  const statPreview = previewRuntime ?? appearance;
  const previewAppearance = {
    ...appearance,
    combat: statPreview.combat ?? appearance.combat,
    equipmentStats: statPreview.equipmentStats ?? appearance.equipmentStats,
    hp: statPreview.hp ?? appearance.hp,
    equipment: previewEquipment,
  };
  const equipped = previewEquipment.filter(entry => entry.isEquipped);
  const bagEquipment = previewEquipment.filter(entry => !entry.isEquipped);
  const rawCells: InventoryCell[] = [
    ...bagEquipment.map((entry): InventoryCell => ({ kind: 'equipment', key: `equip-${entry.equipKey}`, entry })),
    ...player.inventory.map((item): InventoryCell => ({ kind: 'item', key: `item-${item.itemId}`, item })),
  ];
  const cells = Array.from({ length: Math.max(36, rawCells.length) }, (_, index) => rawCells[index] ?? { kind: 'empty' as const, key: `empty-${index}` });
  const [selectedKey, setSelectedKey] = useState<string>(rawCells[0]?.key ?? '');
  const [actionMenu, setActionMenu] = useState<InventoryActionMenuState | null>(null);
  const [actionMenuSelectedIndex, setActionMenuSelectedIndex] = useState<number>(0);
  const [showDetail, setShowDetail] = useState(false);
  const equippedCells = equipped.map((entry): EquipmentCell => ({ kind: 'equipment', key: `equipped-${entry.equipKey}`, entry }));
  const selected = rawCells.find(cell => cell.key === selectedKey)
    ?? equippedCells.find(cell => cell.key === selectedKey)
    ?? rawCells.find((cell): cell is EquipmentCell => cell.kind === 'equipment' && selectedKey.endsWith(cell.entry.equipKey))
    ?? equippedCells.find(cell => selectedKey.endsWith(cell.entry.equipKey));
  const selectedIsEquipped = selected?.kind === 'equipment' && selected.entry.isEquipped;
  const getEquipped = (slot: number) => equipped.find(entry => entry.slot === slot);
  const hasLoadoutChanges = !sameKeySet(serverEquippedKeys, draftEquippedKeys);
  const previewEquipmentChange = (entry: CharacterEquipmentItem, equip: boolean) => {
    setDraftEquippedKeys((current) => {
      const next = new Set(current);
      if (equip) {
        for (const other of previewEquipment) {
          if (other.slot === entry.slot) {
            next.delete(other.equipKey);
          }
        }

        next.add(entry.equipKey);
      } else {
        next.delete(entry.equipKey);
      }

      return next;
    });
  };
  useEffect(() => {
    if (!onPreviewEquipmentLoadout || !hasLoadoutChanges) {
      setPreviewRuntime(null);
      return;
    }

    let active = true;
    const timer = setTimeout(() => {
      void onPreviewEquipmentLoadout(Array.from(draftEquippedKeys)).then((preview) => {
        if (active) {
          setPreviewRuntime(preview);
        }
      });
    }, 120);

    return () => {
      active = false;
      clearTimeout(timer);
    };
  }, [draftEquippedKeys, hasLoadoutChanges, onPreviewEquipmentLoadout]);
  const commitLoadout = onCommitEquipmentLoadout
    ? () => onCommitEquipmentLoadout(Array.from(draftEquippedKeys))
    : undefined;
  const openActionMenu = (cell: InventoryCell, left: number, top: number) => {
    if (cell.kind === 'empty') {
      setActionMenu(null);
      setSelectedKey('');
      setShowDetail(false);
      return;
    }

    setSelectedKey(cell.key);
    setActionMenu({
      cellKey: cell.key,
      left: clampActionMenuLeft(left),
      top: clampActionMenuTop(top),
    });
    setShowDetail(true);
  };
  const selectEquipped = (slot: number, left: number, top: number) => {
    const entry = getEquipped(slot);
    if (entry) {
      openActionMenu({ kind: 'equipment', key: `equipped-${entry.equipKey}`, entry }, left, top);
    } else {
      setActionMenu(null);
    }
  };
  const renderEquipSlot = (slot: number, style: object, left: number, top: number) => (
    <EquipmentSlot
      slot={slot}
      entry={getEquipped(slot)}
      selected={selectedIsEquipped && selected?.kind === 'equipment' && selected.entry.slot === slot}
      onPress={() => selectEquipped(slot, left + 52, top)}
      style={style}
    />
  );
  const renderActionMenu = () => {
    if (!actionMenu || !selected || selected.kind === 'empty') {
      return null;
    }

    let items: any[] = [];

    if (selected.kind === 'equipment') {
      const entry = selected.entry;
      const canEquip = selectedIsEquipped || (
        player.level >= entry.requiredLevel
        && (entry.gender === 2 || entry.gender === undefined || entry.gender === appearance.genderIndex)
      );

      const isBroken = entry.maxDurability > 0 && entry.durability <= 0;
      const hasHammer = (appearance.inventory ?? []).some(
        (i) => i.itemId === REPAIR_HAMMER_ITEM_ID && i.quantity > 0,
      );
      const canRepair = isBroken && hasHammer && !!onRepairEquipment;

      items = [
        {
          id: 'repair',
          label: 'Sửa chữa',
          disabled: pending !== null || !canRepair,
          onPress: () => {
            setActionMenu(null);
            onRunAction(`repair-${entry.equipKey}`, () => onRepairEquipment?.(entry.equipKey));
          },
        },
        {
          id: 'equip',
          label: selectedIsEquipped ? 'Tháo' : 'Trang bị',
          disabled: pending !== null || !canEquip,
          onPress: () => {
            previewEquipmentChange(entry, !selectedIsEquipped);
            setActionMenu(null);
          },
        },
        { id: 'detail', label: 'Chi Tiết', onPress: () => { setActionMenu(null); setShowDetail(true); } },
        {
          id: 'upgrade',
          label: 'Nâng cấp',
          disabled: pending !== null || selectedIsEquipped,
          onPress: () => setActionMenu(null),
        },
        {
          id: 'sell',
          label: 'Rao bán',
          disabled: selectedIsEquipped,
          onPress: () => setActionMenu(null),
        },
        {
          id: 'drop',
          label: 'Vứt bỏ',
          disabled: pending !== null || selectedIsEquipped || !onDiscardEquipment,
          onPress: () => {
            setActionMenu(null);
            onRunAction(`discard-equip-${entry.equipKey}`, () => onDiscardEquipment?.(entry.equipKey));
          },
        },
        ...(hasLoadoutChanges ? [{
          id: 'commit',
          label: 'Cập nhật',
          disabled: pending !== null || !hasLoadoutChanges || !commitLoadout,
          onPress: () => {
            setActionMenu(null);
            onRunAction('equipment-loadout', commitLoadout);
          },
        }] : []),
      ];
    } else {
      const item = selected.item;
      items = [
        {
          id: 'use',
          label: item.isUsable ? 'Dùng' : 'Giữ',
          disabled: pending !== null || !item.isUsable || !onUseItem,
          onPress: () => {
            setActionMenu(null);
            onRunAction(`item-${item.itemId}`, () => onUseItem?.(item.itemId));
          },
        },
        { id: 'detail', label: 'Chi Tiết', onPress: () => setActionMenu(null) },
        { id: 'sell', label: 'Rao bán', onPress: () => setActionMenu(null) },
        {
          id: 'drop',
          label: 'Vứt bỏ',
          disabled: pending !== null || !onDiscardItem,
          onPress: () => {
            setActionMenu(null);
            onRunAction(`discard-item-${item.itemId}`, () => onDiscardItem?.(item.itemId, item.quantity));
          },
        },
      ];
    }

    const menuItems = items.filter(i => !i.disabled);

    return (
      <PopupMenu
        visible={true}
        left={actionMenu.left}
        top={actionMenu.top}
        items={menuItems}
        selectedIndex={actionMenuSelectedIndex}
        onIndexChange={setActionMenuSelectedIndex}
        onSelect={(item) => {}}
        onClose={() => { setActionMenu(null); setActionMenuSelectedIndex(0); }}
      />
    );
  };

  return (
    <View style={styles.inventoryBody}>
      <View style={styles.inventoryNameRow}>
        <Text style={styles.inventoryName} numberOfLines={1}>{player.username}</Text>
        <Text style={styles.inventoryLevel}>Cấp:{player.level}</Text>
      </View>
      {renderEquipSlot(0, styles.equipSlotArmor, 56, 46)}
      {renderEquipSlot(1, styles.equipSlotWeapon, 56, 102)}
      {renderEquipSlot(2, styles.equipSlotHat, 194, 46)}
      {renderEquipSlot(3, styles.equipSlotBoot, 194, 102)}
      {renderEquipSlot(4, styles.equipSlotMount, 250, 46)}
      {renderEquipSlot(5, styles.equipSlotRing, 250, 102)}
      <View style={styles.inventoryAvatarBox}>
        <CharacterRenderer
          appearance={previewAppearance}
          scale={1.45}
          style={{ position: 'relative', bottom: 2 }}
        />
      </View>
      <Text style={styles.capacityText}>{rawCells.length}/50</Text>

      <View style={styles.inventoryGrid}>
        {cells.map((cell, index) => (
          <InventoryGridCell
            key={cell.key}
            cell={cell}
            selected={selectedKey === cell.key}
            onPress={() => openActionMenu(
              cell,
              22 + (index % 6) * 51 + 34,
              178 + Math.floor(index / 6) * 51,
            )}
            style={{
              left: (index % 6) * 51,
              top: Math.floor(index / 6) * 51,
            }}
          />
        ))}
      </View>

      <InventoryDetailPanel
        visible={showDetail}
        onClose={() => setShowDetail(false)}
        playerLevel={player.level}
        playerGender={appearance.genderIndex}
        currentCombat={appearance.combat}
        previewCombat={previewRuntime?.combat}
        selected={selected}
        pending={pending}
      />
      {renderActionMenu()}
    </View>
  );
};

const InventoryDialog: React.FC<{
  appearance: CharacterAppearance;
  pending: string | null;
  onRunAction: (key: string, runner?: DialogActionRunner) => void;
  onToggleEquipment?: (equipKey: string, equip: boolean) => Promise<string | null>;
  onPreviewEquipmentLoadout?: (equipKeys: string[]) => Promise<CharacterAppearance | null>;
  onCommitEquipmentLoadout?: (equipKeys: string[]) => Promise<string | null>;
  onUseItem?: (itemId: number) => Promise<string | null>;
  onDiscardEquipment?: (equipKey: string) => Promise<string | null>;
  onDiscardItem?: (itemId: number, quantity: number) => Promise<string | null>;
  onRepairEquipment?: (equipKey: string) => Promise<string | null>;
}> = ({ appearance, pending, onRunAction, onToggleEquipment, onPreviewEquipmentLoadout, onCommitEquipmentLoadout, onUseItem, onDiscardEquipment, onDiscardItem, onRepairEquipment }) => {
  return (
    <InventoryShell
      appearance={appearance}
      pending={pending}
      onRunAction={onRunAction}
      onToggleEquipment={onToggleEquipment}
      onPreviewEquipmentLoadout={onPreviewEquipmentLoadout}
      onCommitEquipmentLoadout={onCommitEquipmentLoadout}
      onUseItem={onUseItem}
      onDiscardEquipment={onDiscardEquipment}
      onDiscardItem={onDiscardItem}
      onRepairEquipment={onRepairEquipment}
    />
  );
};

export const MapCharacterDialogs: React.FC<MapCharacterDialogsProps> = ({
  activeDialog,
  appearance,
  onClose,
  onAllocateStat,
  onAllocateSkill,
  onToggleEquipment,
  onPreviewEquipmentLoadout,
  onCommitEquipmentLoadout,
  onUseItem,
  onDiscardEquipment,
  onDiscardItem,
  onRepairEquipment,
}) => {
  const [pending, setPending] = useState<string | null>(null);

  if (!activeDialog) {
    return null;
  }

  const runAction = (key: string, runner?: DialogActionRunner) => {
    if (!runner || pending) {
      return;
    }

    setPending(key);
    const action = runner();
    if (!action) {
      setPending(null);
      return;
    }

    void action
      .then(() => undefined)
      .finally(() => setPending(null));
  };

  const dialogWidth = Math.min(SCREEN_W * 0.94, activeDialog === 'skills' ? 480 : 430);
  const dialogMaxHeight = Math.min(
    SCREEN_H * 0.92,
    activeDialog === 'potential'
      ? 720
      : activeDialog === 'skills'
        ? 620
        : activeDialog === 'equipment' || activeDialog === 'inventory'
          ? 700
          : 580,
  );

  return (
    <View style={styles.overlay}>
      <Pressable style={styles.backdrop} onPress={onClose} />
      <CornerFrame
        style={[styles.dialogFrame, { width: dialogWidth, maxHeight: dialogMaxHeight }]}
        contentStyle={styles.dialogContent}
      >
        <ScrollView
          style={{ maxHeight: dialogMaxHeight - 30, width: '100%' }}
          contentContainerStyle={styles.dialogScrollContent}
          showsVerticalScrollIndicator={false}
        >
          {activeDialog === 'info' && <InfoDialog appearance={appearance} />}
          {activeDialog === 'potential' && (
            <PotentialDialog
              appearance={appearance}
              pending={pending}
              onRunAction={runAction}
              onAllocateStat={onAllocateStat}
            />
          )}
          {activeDialog === 'skills' && (
            <SkillsDialog
              appearance={appearance}
              pending={pending}
              onRunAction={runAction}
              onAllocateSkill={onAllocateSkill}
            />
          )}
          {activeDialog === 'equipment' && (
            <EquipmentDialog
              appearance={appearance}
              pending={pending}
              onRunAction={runAction}
              onToggleEquipment={onToggleEquipment}
              onPreviewEquipmentLoadout={onPreviewEquipmentLoadout}
              onCommitEquipmentLoadout={onCommitEquipmentLoadout}
              onUseItem={onUseItem}
              onDiscardEquipment={onDiscardEquipment}
              onDiscardItem={onDiscardItem}
              onRepairEquipment={onRepairEquipment}
            />
          )}
          {activeDialog === 'inventory' && (
            <InventoryDialog
              appearance={appearance}
              pending={pending}
              onRunAction={runAction}
              onToggleEquipment={onToggleEquipment}
              onPreviewEquipmentLoadout={onPreviewEquipmentLoadout}
              onCommitEquipmentLoadout={onCommitEquipmentLoadout}
              onUseItem={onUseItem}
              onDiscardEquipment={onDiscardEquipment}
              onDiscardItem={onDiscardItem}
              onRepairEquipment={onRepairEquipment}
            />
          )}
        </ScrollView>
      </CornerFrame>
    </View>
  );
};
