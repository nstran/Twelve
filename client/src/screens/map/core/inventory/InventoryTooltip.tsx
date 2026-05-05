import React from 'react';
import { Text, View } from 'react-native';
import type { CharacterEquipmentItem, CharacterInventoryItem } from '../../../character/shared';
import { RANK_COLORS } from './InventoryLayout';
import { styles } from './InventoryScreen.styles';
import type { InventoryCellData } from './InventoryCellView';

interface InventoryTooltipProps {
  cell: InventoryCellData | null;
  playerLevel: number;
  playerGender: number;
  scale: number;
  canvasHeight: number;
}

const getEquipmentBonusLines = (entry: CharacterEquipmentItem) => {
  const lines: string[] = [];

  if (entry.bonusCuongLuc) lines.push(`Cường Lực +${entry.bonusCuongLuc}`);
  if (entry.bonusThanPhap) lines.push(`Thân Pháp +${entry.bonusThanPhap}`);
  if (entry.bonusNoiLuc) lines.push(`Nội Lực +${entry.bonusNoiLuc}`);
  if (entry.bonusTheLuc) lines.push(`Thể Lực +${entry.bonusTheLuc}`);
  if (entry.bonusAttack) lines.push(`Tấn công +${entry.bonusAttack}`);
  if (entry.bonusAttackPercent) lines.push(`Tấn công +${entry.bonusAttackPercent}%`);
  if (entry.bonusDefense) lines.push(`Phòng thủ +${entry.bonusDefense}`);
  if (entry.bonusDodge) lines.push(`Né tránh +${entry.bonusDodge}`);
  if (entry.bonusCrit) lines.push(`Bạo kích +${entry.bonusCrit}`);
  if (entry.bonusMaxHp) lines.push(`Sinh lực +${entry.bonusMaxHp}`);

  return lines;
};

const getDurabilityText = (entry: CharacterEquipmentItem) => {
  if (entry.maxDurability <= 0) {
    return null;
  }

  let suffix = '';
  if (entry.durability <= 0) {
    suffix = ' (Đã hư hoàn toàn)';
  } else if ((entry.durability * 100) / entry.maxDurability < 30) {
    suffix = ' (Đã hư hỏng nặng)';
  }

  return `Độ bền: ${entry.durability}/${entry.maxDurability}${suffix}`;
};

const EquipmentTooltip = ({ entry, playerLevel, playerGender }: {
  entry: CharacterEquipmentItem;
  playerLevel: number;
  playerGender: number;
}) => {
  const rankColor = RANK_COLORS[entry.rank] ?? RANK_COLORS[0];
  const durabilityText = getDurabilityText(entry);
  const bonusLines = getEquipmentBonusLines(entry);
  const levelOk = playerLevel >= entry.requiredLevel;
  const genderOk = entry.gender === 2 || entry.gender === playerGender;

  return (
    <>
      <Text style={[styles.tooltipName, { color: rankColor }]} numberOfLines={1}>
        {entry.displayName}{entry.level > 0 ? ` +${entry.level}` : ''}
      </Text>
      <Text style={levelOk ? styles.tooltipLine : styles.tooltipLineRed}>Yêu cầu cấp: {entry.requiredLevel}</Text>
      {durabilityText ? (
        <Text style={entry.durability > 0 && (entry.durability * 100) / entry.maxDurability >= 30 ? styles.tooltipLine : styles.tooltipLineRed}>
          {durabilityText}
        </Text>
      ) : null}
      {bonusLines.map((line) => (
        <Text key={line} style={styles.tooltipLineGreen}>{line}</Text>
      ))}
      {entry.summary ? <Text style={styles.tooltipLine}>{entry.summary}</Text> : null}
      {!genderOk ? <Text style={styles.tooltipLineRed}>Không phù hợp giới tính</Text> : null}
      {entry.canRepair ? <Text style={styles.tooltipLineYellow}>Có thể sửa chữa</Text> : <Text style={styles.tooltipLineRed}>Không thể sửa chữa</Text>}
      {!entry.tradeable ? <Text style={styles.tooltipLineRed}>Không thể giao dịch</Text> : null}
    </>
  );
};

const ItemTooltip = ({ item }: { item: CharacterInventoryItem }) => (
  <>
    <Text style={[styles.tooltipName, { color: '#FFFFFF' }]} numberOfLines={1}>{item.displayName}</Text>
    {item.description ? <Text style={styles.tooltipLine}>{item.description}</Text> : null}
    {item.quantity > 1 ? <Text style={styles.tooltipLineYellow}>Số lượng: {item.quantity}</Text> : null}
    {item.isUsable ? <Text style={styles.tooltipLineGreen}>Có thể sử dụng</Text> : null}
  </>
);

export const InventoryTooltip: React.FC<InventoryTooltipProps> = ({
  cell,
  playerLevel,
  playerGender,
  scale,
  canvasHeight,
}) => {
  if (!cell || cell.kind === 'empty') {
    return null;
  }

  const width = 220 * scale;
  const left = 9 * scale;
  const minHeight = Math.max(64 * scale, canvasHeight / 4);
  const bottom = 7 * scale;

  return (
    <View
      pointerEvents="none"
      style={[
        styles.tooltipContainer,
        {
          left,
          width,
          minHeight,
          bottom,
          paddingHorizontal: 6 * scale,
          paddingVertical: 4 * scale,
        },
      ]}
    >
      {cell.kind === 'equipment' ? (
        <EquipmentTooltip entry={cell.entry} playerLevel={playerLevel} playerGender={playerGender} />
      ) : (
        <ItemTooltip item={cell.item} />
      )}
    </View>
  );
};
