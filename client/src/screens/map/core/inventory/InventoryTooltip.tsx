import React from 'react';
import { Image, View } from 'react-native';
import type { CharacterEquipmentItem, CharacterInventoryItem } from '../../../character/shared';
import { RANK_COLORS } from './InventoryLayout';
import { styles } from './InventoryScreen.styles';
import { JAVA_BITMAP_FONT_HEIGHT, JavaBitmapText, measureJavaBitmapText } from './javaFont/JavaBitmapText';
import type { InventoryCellData } from './InventoryCellView';

/**
 * Element icon asset for equipment element display.
 * Source: fw.java — ll.f > 0 renders element icon before equipment name.
 * Asset: /battle/04_element_icons/elementsicon.png — 60x15, 4 frames of 15x15.
 * Frame indices: 0=Hỏa, 1=Lôi, 2=Thủy, 3=N/A.
 */
const ELEMENT_ICON_ASSET = require('../../../../../assets/battle/04_element_icons/elementsicon.png');
const ELEMENT_FRAME_W = 15;
const ELEMENT_FRAME_H = 15;

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

  // Element icon index from equipment data (fw.java: ll.f > 0)
  const elementIndex = Math.max(0, Math.min(3, entry.elementIcon ?? 0));
  const hasElementIcon = entry.elementIcon > 0 && entry.elementIcon < 4;

  // Build all lines with their colors for JavaBitmapText rendering
  // Element icon is rendered separately, before the name text
  const lines: { text: string; color: string; bold?: boolean }[] = [];

  // Name line
  const nameText = entry.level > 0 ? `${entry.displayName} +${entry.level}` : entry.displayName;
  lines.push({ text: nameText, color: rankColor, bold: true });

  // Required level line
  const levelText = `Yêu cầu cấp: ${entry.requiredLevel}`;
  lines.push({ text: levelText, color: levelOk ? '#FFFF00' : '#FF0000' });

  // Durability line
  if (durabilityText) {
    const durOk = entry.durability > 0 && (entry.durability * 100) / entry.maxDurability >= 30;
    lines.push({ text: durabilityText, color: durOk ? '#FFFF00' : '#FF0000' });
  }

  // Bonus lines in green
  for (const line of bonusLines) {
    lines.push({ text: line, color: '#00FF00' });
  }

  // Description
  if (entry.summary) {
    lines.push({ text: entry.summary, color: '#FFFFFF' });
  }

  // Gender warning
  if (!genderOk) {
    lines.push({ text: 'Không phù hợp giới tính', color: '#FF0000' });
  }

  // Repair hint
  if (entry.canRepair) {
    lines.push({ text: 'Có thể sửa chữa', color: '#FFFF00' });
  } else {
    lines.push({ text: 'Không thể sửa chữa', color: '#FF0000' });
  }

  // Trade warning
  if (!entry.tradeable) {
    lines.push({ text: 'Không thể giao dịch', color: '#FF0000' });
  }

  const lineHeight = (JAVA_BITMAP_FONT_HEIGHT + 2) * 1;
  const iconWidth = ELEMENT_FRAME_W + 2; // icon width + spacing
  let currentY = 0;

  return (
    <>
      {/* Element icon before name - fw.java: ll.f > 0 renders element icon */}
      {hasElementIcon ? (
        <View style={{ position: 'absolute', left: 0, top: currentY, width: ELEMENT_FRAME_W, height: ELEMENT_FRAME_H, overflow: 'hidden' }}>
          <Image
            source={ELEMENT_ICON_ASSET}
            style={{ position: 'absolute', left: -(elementIndex * ELEMENT_FRAME_W), top: 0, width: ELEMENT_FRAME_W * 4, height: ELEMENT_FRAME_H }}
            resizeMode="stretch"
          />
        </View>
      ) : null}

      {lines.map((line, index) => {
        // First line (name) needs to account for element icon
        const xOffset = index === 0 && hasElementIcon ? iconWidth : 0;
        const y = currentY;
        currentY += lineHeight;
        return (
          <JavaBitmapText
            key={String(index)}
            text={line.text}
            x={xOffset}
            y={y}
            scale={1}
            anchor={0}
            bold={line.bold}
          />
        );
      })}
    </>
  );
};

const ItemTooltip = ({ item }: { item: CharacterInventoryItem }) => {
  const lines: { text: string; color: string }[] = [];

  // Name
  lines.push({ text: item.displayName, color: '#FFFFFF' });

  // Description
  if (item.description) {
    lines.push({ text: item.description, color: '#FFFFFF' });
  }

  // Quantity
  if (item.quantity > 1) {
    lines.push({ text: `Số lượng: ${item.quantity}`, color: '#FFFF00' });
  }

  // Usable
  if (item.isUsable) {
    lines.push({ text: 'Có thể sử dụng', color: '#00FF00' });
  }

  const lineHeight = (JAVA_BITMAP_FONT_HEIGHT + 2) * 1;
  let currentY = 0;

  return (
    <>
      {lines.map((line, index) => {
        const y = currentY;
        currentY += lineHeight;
        return (
          <JavaBitmapText
            key={String(index)}
            text={line.text}
            x={0}
            y={y}
            scale={1}
            anchor={0}
          />
        );
      })}
    </>
  );
};

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
