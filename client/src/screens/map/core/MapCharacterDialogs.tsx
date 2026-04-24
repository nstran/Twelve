import React, { useMemo, useState } from 'react';
import {
  Dimensions,
  Image,
  Pressable,
  ScrollView,
  Text,
  TouchableOpacity,
  View,
} from 'react-native';
import { CornerFrame } from '../../../components/ui/CornerFrame/CornerFrame';
import { CreateCharacterPreview } from '../../character/create/CreateCharacterPreview';
import { CHARACTER_STATUS_ASSETS } from '../../character/status/assets';
import type {
  CharacterAppearance,
  CharacterEquipmentItem,
  CharacterInventoryItem,
  CharacterSkillNode,
} from '../../character/shared';
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
  skilltree: require('../../../../assets/ui/12_info/skilltree.png'),
  hidenobj: require('../../../../assets/ui/12_info/hidenobj.png'),
  itemchest: require('../../../../assets/ui/12_info/itemchest.png'),
};
const EQUIPMENT_ICON_ASSETS: Record<number, any> = {
  4101: require('../../../../assets/equipment/03_weapon_e1/weapon_800xx/80098.png'),
  4102: require('../../../../assets/equipment/03_weapon_e1/weapon_802xx/80298.png'),
  4103: require('../../../../assets/equipment/07_accessory_e5_e7_e8/accessory_candidate_120xxx/120198.png'),
  4201: require('../../../../assets/equipment/02_armor_e0/armor_701xx/70198.png'),
  4202: require('../../../../assets/equipment/02_armor_e0/armor_703xx/70398.png'),
  4203: require('../../../../assets/equipment/07_accessory_e5_e7_e8/accessory_candidate_120xxx/120298.png'),
  80000: require('../../../../assets/equipment/03_weapon_e1/weapon_800xx/80098.png'),
  80200: require('../../../../assets/equipment/03_weapon_e1/weapon_802xx/80298.png'),
  120100: require('../../../../assets/equipment/07_accessory_e5_e7_e8/accessory_candidate_120xxx/120198.png'),
  70100: require('../../../../assets/equipment/02_armor_e0/armor_701xx/70198.png'),
  70300: require('../../../../assets/equipment/02_armor_e0/armor_703xx/70398.png'),
  120200: require('../../../../assets/equipment/07_accessory_e5_e7_e8/accessory_candidate_120xxx/120298.png'),
};
const SKILL_UI_ASSETS = {
  increase: require('../../../../assets/skill/00_skill_tree_ui_confirmed/skill_tree_board/increase.png'),
  decrease: require('../../../../assets/skill/00_skill_tree_ui_confirmed/skill_tree_board/decrease.png'),
};
const HUD_ASSETS = {
  btinscrease: require('../../../../assets/hud/01_button_markers/btinscrease.png'),
};
const HIDDEN_SLOT_SIZE = 32;
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
  onUseItem?: (itemId: number) => Promise<string | null>;
}

const PRIMARY_STAT: Record<number, number> = { 0: 0, 1: 2, 2: 1 };
const STAT_ROWS: Array<{ label: string; key: CharacterStatKey; valueKey: keyof NonNullable<CharacterAppearance['stats']> }> = [
  { label: 'Cường Lực', key: 'CuongLuc', valueKey: 'cuongLuc' },
  { label: 'Nội Lực', key: 'NoiLuc', valueKey: 'noiLuc' },
  { label: 'Thân Pháp', key: 'ThanPhap', valueKey: 'thanPhap' },
  { label: 'Thể Lực', key: 'TheLuc', valueKey: 'theLuc' },
];
const EQUIPMENT_SLOT_NAMES: Record<number, string> = {
  0: 'Nón',
  1: 'Giày',
  2: 'Áo',
  3: 'Phụ kiện',
  4: 'Vũ khí',
  5: 'Bùa',
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

const getEquipmentBonusRows = (entry: CharacterEquipmentItem) => [
  entry.bonusCuongLuc ? `Cường Lực ${formatSigned(entry.bonusCuongLuc)}` : null,
  entry.bonusNoiLuc ? `Nội Lực ${formatSigned(entry.bonusNoiLuc)}` : null,
  entry.bonusThanPhap ? `Thân Pháp ${formatSigned(entry.bonusThanPhap)}` : null,
  entry.bonusTheLuc ? `Thể Lực ${formatSigned(entry.bonusTheLuc)}` : null,
  entry.bonusAttack ? `Tấn Công ${formatSigned(entry.bonusAttack)}` : null,
  entry.bonusDefense ? `P.Thủ ${formatSigned(entry.bonusDefense)}` : null,
  entry.bonusDodge ? `Né Tránh ${formatSigned(entry.bonusDodge)}` : null,
  entry.bonusCrit ? `Chí Mạng ${formatSigned(entry.bonusCrit, '%')}` : null,
  entry.bonusMaxHp ? `Sinh lực ${formatSigned(entry.bonusMaxHp)}` : null,
].filter((row): row is string => Boolean(row));

const resolveEquipmentIcon = (entry: CharacterEquipmentItem) =>
  EQUIPMENT_ICON_ASSETS[entry.resourceId] ?? EQUIPMENT_ICON_ASSETS[entry.resourceId - (entry.resourceId % 100)];

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
  onPress: () => void;
  onUpgrade: () => void;
  style: object;
}> = ({ familyCode, node, canUpgrade, disabled, onPress, onUpgrade, style }) => {
  const skill = BATTLE_SKILLS[familyCode];
  const level = node?.level ?? 0;

  return (
    <Pressable onPress={onPress} style={[styles.skillNode, style]}>
      <Image source={skill.icon} style={styles.skillIcon} resizeMode="stretch" />
      <Text style={styles.skillLevel}>{level}</Text>
      <TouchableOpacity
        activeOpacity={0.85}
        disabled={!canUpgrade || disabled}
        onPress={onUpgrade}
        style={[styles.skillUpgradeButton, (!canUpgrade || disabled) && styles.skillUpgradeButtonDisabled]}
      >
        <Image source={SKILL_UI_ASSETS.increase} style={styles.skillUpgradeIcon} resizeMode="contain" />
      </TouchableOpacity>
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
  [94, 11],
  [94, 101],
  [94, 152],
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
              onPress={() => setSelectedFamilyCode(familyCode)}
              onUpgrade={() => onRunAction(`skill-${familyCode}`, () => onAllocateSkill?.(familyCode))}
              style={SKILL_POSITIONS[index]}
            />
          );
        })}
      </View>
      <Divider />
      <View style={styles.skillInfoCompact}>
        <Text style={styles.skillInfoTitle}>{selectedSkill.title}</Text>
        <Text style={styles.skillInfoText}>
          Cấp {selectedNode?.level ?? 0}/{selectedNode?.maxLevel ?? 12}  Cần cấp {selectedNode?.requiredLevel ?? 1}
        </Text>
      </View>
    </View>
  );
};

const EquipmentDialog: React.FC<{
  appearance: CharacterAppearance;
  pending: string | null;
  onRunAction: (key: string, runner?: DialogActionRunner) => void;
  onToggleEquipment?: (equipKey: string, equip: boolean) => Promise<string | null>;
  onUseItem?: (itemId: number) => Promise<string | null>;
}> = ({ appearance, pending, onRunAction, onToggleEquipment, onUseItem }) => {
  return (
    <InventoryShell
      appearance={appearance}
      pending={pending}
      onRunAction={onRunAction}
      onToggleEquipment={onToggleEquipment}
      onUseItem={onUseItem}
    />
  );
};

type InventoryCell =
  | { kind: 'equipment'; key: string; entry: CharacterEquipmentItem }
  | { kind: 'item'; key: string; item: CharacterInventoryItem }
  | { kind: 'empty'; key: string };
type EquipmentCell = Extract<InventoryCell, { kind: 'equipment' }>;

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
}> = ({
  slot,
  entry,
  selected,
  onPress,
  style,
}) => (
  <Pressable onPress={onPress} style={[styles.equipSlot, style, selected && styles.inventorySelected]}>
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
  <Pressable onPress={onPress} style={[styles.inventoryCell, style, selected && styles.inventorySelected]}>
    {cell.kind === 'empty' ? null : (
      <>
        <Image
          source={cell.kind === 'equipment' ? resolveEquipmentIcon(cell.entry) ?? INFO_ASSETS.itemchest : INFO_ASSETS.itemchest}
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

const InventoryDetailPanel: React.FC<{
  playerLevel: number;
  selected?: InventoryCell;
  selectedIsEquipped: boolean;
  pending: string | null;
  onRunAction: (key: string, runner?: DialogActionRunner) => void;
  onToggleEquipment?: (equipKey: string, equip: boolean) => Promise<string | null>;
  onUseItem?: (itemId: number) => Promise<string | null>;
}> = ({
  playerLevel,
  selected,
  selectedIsEquipped,
  pending,
  onRunAction,
  onToggleEquipment,
  onUseItem,
}) => {
  if (selected?.kind === 'equipment') {
    const entry = selected.entry;
    const canEquip = selectedIsEquipped || playerLevel >= entry.requiredLevel;
    const bonusRows = getEquipmentBonusRows(entry);

    return (
      <View style={styles.inventoryDetailPanel}>
        <View style={styles.inventoryDetailHeader}>
          <Text style={styles.inventoryDetailTitle} numberOfLines={1}>{entry.displayName}</Text>
          <Text style={styles.inventoryDetailMeta}>
            {EQUIPMENT_SLOT_NAMES[entry.slot] ?? 'Trang bị'}  Cấp {entry.level}
          </Text>
        </View>
        <Text style={styles.inventoryDetailText} numberOfLines={1}>
          Yêu cầu cấp {entry.requiredLevel}  {entry.isEquipped ? 'Đang mặc' : 'Trong túi'}
        </Text>
        <View style={styles.inventoryBonusGrid}>
          {(bonusRows.length > 0 ? bonusRows : [entry.summary]).slice(0, 6).map(row => (
            <Text key={row} style={styles.inventoryBonusText} numberOfLines={1}>{row}</Text>
          ))}
        </View>
        <View style={styles.inventoryDetailActions}>
          <ActionButton
            label={selectedIsEquipped ? 'Tháo' : 'Trang bị'}
            disabled={pending !== null || !canEquip || !onToggleEquipment}
            onPress={() => onRunAction(
              `${selectedIsEquipped ? 'unequip' : 'equip'}-${entry.equipKey}`,
              () => onToggleEquipment?.(entry.equipKey, !selectedIsEquipped),
            )}
          />
        </View>
      </View>
    );
  }

  if (selected?.kind === 'item') {
    const item = selected.item;

    return (
      <View style={styles.inventoryDetailPanel}>
        <View style={styles.inventoryDetailHeader}>
          <Text style={styles.inventoryDetailTitle} numberOfLines={1}>{item.displayName}</Text>
          <Text style={styles.inventoryDetailMeta}>{item.quantity}/{item.stackCap}</Text>
        </View>
        <Text style={styles.inventoryDetailText} numberOfLines={2}>{item.description}</Text>
        {item.isUsable ? (
          <Text style={styles.inventoryBonusText} numberOfLines={1}>Hồi {item.healAmount} sinh lực</Text>
        ) : null}
        <View style={styles.inventoryDetailActions}>
          <ActionButton
            label={item.isUsable ? 'Dùng' : 'Giữ'}
            disabled={pending !== null || !item.isUsable || !onUseItem}
            onPress={() => onRunAction(`item-${item.itemId}`, () => onUseItem?.(item.itemId))}
          />
        </View>
      </View>
    );
  }

  return <View style={styles.inventoryDetailPanel} />;
};

const InventoryShell: React.FC<{
  appearance: CharacterAppearance;
  pending: string | null;
  onRunAction: (key: string, runner?: DialogActionRunner) => void;
  onToggleEquipment?: (equipKey: string, equip: boolean) => Promise<string | null>;
  onUseItem?: (itemId: number) => Promise<string | null>;
}> = ({ appearance, pending, onRunAction, onToggleEquipment, onUseItem }) => {
  const player = createPlayerModel(appearance);
  const equipped = player.equipment.filter(entry => entry.isEquipped);
  const bagEquipment = player.equipment.filter(entry => !entry.isEquipped);
  const rawCells: InventoryCell[] = [
    ...bagEquipment.map((entry): InventoryCell => ({ kind: 'equipment', key: `equip-${entry.equipKey}`, entry })),
    ...player.inventory.map((item): InventoryCell => ({ kind: 'item', key: `item-${item.itemId}`, item })),
  ];
  const cells = Array.from({ length: Math.max(36, rawCells.length) }, (_, index) => rawCells[index] ?? { kind: 'empty' as const, key: `empty-${index}` });
  const [selectedKey, setSelectedKey] = useState<string>(rawCells[0]?.key ?? '');
  const equippedCells = equipped.map((entry): EquipmentCell => ({ kind: 'equipment', key: `equipped-${entry.equipKey}`, entry }));
  const selected = rawCells.find(cell => cell.key === selectedKey)
    ?? equippedCells.find(cell => cell.key === selectedKey)
    ?? rawCells.find((cell): cell is EquipmentCell => cell.kind === 'equipment' && selectedKey.endsWith(cell.entry.equipKey))
    ?? equippedCells.find(cell => selectedKey.endsWith(cell.entry.equipKey));
  const selectedIsEquipped = selected?.kind === 'equipment' && selected.entry.isEquipped;
  const getEquipped = (slot: number) => equipped.find(entry => entry.slot === slot);
  const selectEquipped = (slot: number) => {
    const entry = getEquipped(slot);
    if (entry) {
      setSelectedKey(`equipped-${entry.equipKey}`);
    }
  };
  const renderEquipSlot = (slot: number, style: object) => (
    <EquipmentSlot
      slot={slot}
      entry={getEquipped(slot)}
      selected={selectedIsEquipped && selected?.kind === 'equipment' && selected.entry.slot === slot}
      onPress={() => selectEquipped(slot)}
      style={style}
    />
  );

  return (
    <View style={styles.inventoryBody}>
      <View style={styles.inventoryNameRow}>
        <Text style={styles.inventoryName} numberOfLines={1}>{player.username}</Text>
        <Text style={styles.inventoryLevel}>Cấp:{player.level}</Text>
      </View>
      {renderEquipSlot(0, styles.equipSlotHat)}
      {renderEquipSlot(2, styles.equipSlotArmor)}
      {renderEquipSlot(4, styles.equipSlotWeapon)}
      {renderEquipSlot(1, styles.equipSlotBoot)}
      {renderEquipSlot(5, styles.equipSlotCharm)}
      {renderEquipSlot(3, styles.equipSlotAccessory)}
      <View style={styles.inventoryAvatarBox}>
        <CreateCharacterPreview
          genderIndex={appearance.genderIndex}
          faceIndex={appearance.faceIndex}
          hairIndex={appearance.hairIndex}
          hairColorIndex={appearance.hairColorIndex}
          skinColorIndex={appearance.skinColorIndex}
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
            onPress={() => setSelectedKey(cell.key)}
            style={{
              left: (index % 6) * 51,
              top: Math.floor(index / 6) * 51,
            }}
          />
        ))}
      </View>

      <InventoryDetailPanel
        playerLevel={player.level}
        selected={selected}
        selectedIsEquipped={selectedIsEquipped}
        pending={pending}
        onRunAction={onRunAction}
        onToggleEquipment={onToggleEquipment}
        onUseItem={onUseItem}
      />
    </View>
  );
};

const InventoryDialog: React.FC<{
  appearance: CharacterAppearance;
  pending: string | null;
  onRunAction: (key: string, runner?: DialogActionRunner) => void;
  onToggleEquipment?: (equipKey: string, equip: boolean) => Promise<string | null>;
  onUseItem?: (itemId: number) => Promise<string | null>;
}> = ({ appearance, pending, onRunAction, onToggleEquipment, onUseItem }) => {
  return (
    <InventoryShell
      appearance={appearance}
      pending={pending}
      onRunAction={onRunAction}
      onToggleEquipment={onToggleEquipment}
      onUseItem={onUseItem}
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
  onUseItem,
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
              onUseItem={onUseItem}
            />
          )}
          {activeDialog === 'inventory' && (
            <InventoryDialog
              appearance={appearance}
              pending={pending}
              onRunAction={runAction}
              onToggleEquipment={onToggleEquipment}
              onUseItem={onUseItem}
            />
          )}
        </ScrollView>
      </CornerFrame>
    </View>
  );
};
