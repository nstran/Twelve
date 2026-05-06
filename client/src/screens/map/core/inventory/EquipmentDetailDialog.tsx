import React from 'react';
import { Image, Pressable, TouchableOpacity, View } from 'react-native';
import type { CharacterEquipmentItem } from '../../../character/shared';
import { resolveEquipmentIconAsset } from '../../../character/shared';
import { RANK_COLORS } from './InventoryLayout';
import { styles } from './InventoryScreen.styles';
import { JAVA_BITMAP_FONT_HEIGHT, JavaBitmapText, measureJavaBitmapText } from './javaFont/JavaBitmapText';

/**
 * Element icon asset for equipment element display.
 * Source: hg.java — ll.f > 0 renders element icon before equipment name.
 * Asset: /battle/04_element_icons/elementsicon.png — 60x15, 4 frames of 15x15.
 * Frame indices: 0=Hỏa, 1=Lôi, 2=Thủy, 3=N/A.
 */
const ELEMENT_ICON_ASSET = require('../../../../../assets/battle/04_element_icons/elementsicon.png');
const ELEMENT_FRAME_W = 15;
const ELEMENT_FRAME_H = 15;

/**
 * Full equipment detail dialog matching hg.java.
 *
 * Source: reference/redecoded/decompiled/hg.java
 *
 * Layout:
 * - Icon centered at top.
 * - Name with rank color + enhancement.
 * - Separator.
 * - Required level (red if not met).
 * - Durability (red if below 30%).
 * - Stat lines in green.
 * - Description.
 * - Gender restriction warning.
 * - Close button.
 */

interface EquipmentDetailDialogProps {
  entry: CharacterEquipmentItem;
  playerLevel: number;
  playerGender: number;
  onClose: () => void;
}

const getStatLines = (entry: CharacterEquipmentItem): string[] => {
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

export const EquipmentDetailDialog: React.FC<EquipmentDetailDialogProps> = ({
  entry,
  playerLevel,
  playerGender,
  onClose,
}) => {
  const icon = resolveEquipmentIconAsset(entry);
  const rankColor = RANK_COLORS[entry.rank] ?? RANK_COLORS[0];
  const levelOk = playerLevel >= entry.requiredLevel;
  const genderOk = entry.gender === 2 || entry.gender === playerGender;
  const statLines = getStatLines(entry);

  let durabilityColor = '#FFFF00';
  let durabilityText = '';
  if (entry.maxDurability > 0) {
    durabilityText = `Độ bền: ${entry.durability}/${entry.maxDurability}`;
    if (entry.durability <= 0) {
      durabilityText += ' (Đã hư hoàn toàn)';
      durabilityColor = '#FF0000';
    } else if ((entry.durability * 100) / entry.maxDurability < 30) {
      durabilityText += ' (Đã hư hỏng nặng)';
      durabilityColor = '#FF0000';
    }
  }

  // Build all lines for JavaBitmapText rendering
  const lines: { text: string; color: string }[] = [];

  // Required level line
  const levelText = `Yêu cầu cấp: ${entry.requiredLevel}`;
  lines.push({ text: levelText, color: levelOk ? '#FFFF00' : '#FF0000' });

  // Durability line
  if (durabilityText) {
    lines.push({ text: durabilityText, color: durabilityColor });
  }

  // Stat lines in green
  for (const line of statLines) {
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

  // Trade warning
  if (!entry.tradeable) {
    lines.push({ text: 'Không thể giao dịch', color: '#FF0000' });
  }

  const lineHeight = (JAVA_BITMAP_FONT_HEIGHT + 2);
  let currentY = 0;

  // Calculate name + enhancement text width for centering
  const nameText = entry.displayName;
  const nameWidth = measureJavaBitmapText(nameText);
  const enhWidth = entry.level > 0 ? measureJavaBitmapText(` +${entry.level}`) : 0;
  const totalNameWidth = nameWidth + enhWidth;

  // Element icon - hg.java: ll.f > 0 renders element icon before name
  const elementIndex = Math.max(0, Math.min(3, entry.elementIcon ?? 0));
  const hasElementIcon = entry.elementIcon > 0 && entry.elementIcon < 4;
  const iconWidth = hasElementIcon ? ELEMENT_FRAME_W + 2 : 0;

  return (
    <View style={styles.detailOverlay}>
      <Pressable style={styles.detailBackdrop} onPress={onClose} />
      <View style={styles.detailPanel}>
        {/* Icon centered — hg.java o = cu(f/2, 10) */}
        <View style={styles.detailIconRow}>
          {icon ? (
            <Image source={icon} style={styles.detailIcon} resizeMode="contain" />
          ) : (
            <View style={[styles.detailIcon, { backgroundColor: '#333' }]} />
          )}
        </View>

        {/* Name + enhancement — hg.java name line, centered with JavaBitmapText */}
        <View style={styles.detailNameRow}>
          {/* Element icon before name - hg.java: ll.f > 0 */}
          {hasElementIcon ? (
            <View style={{ width: ELEMENT_FRAME_W, height: ELEMENT_FRAME_H, overflow: 'hidden' }}>
              <Image
                source={ELEMENT_ICON_ASSET}
                style={{ position: 'absolute', left: -(elementIndex * ELEMENT_FRAME_W), top: 0, width: ELEMENT_FRAME_W * 4, height: ELEMENT_FRAME_H }}
                resizeMode="stretch"
              />
            </View>
          ) : null}
          <JavaBitmapText text={nameText} x={hasElementIcon ? ELEMENT_FRAME_W + 2 : 0} y={0} scale={1} anchor={0} />
          {entry.level > 0 ? (
            <JavaBitmapText text={`+${entry.level}`} x={(hasElementIcon ? ELEMENT_FRAME_W + 2 : 0) + nameWidth} y={0} scale={1} anchor={0} />
          ) : null}
        </View>

        <View style={styles.detailSeparator} />

        {/* Text lines rendered with JavaBitmapText */}
        {lines.map((line, index) => {
          const y = currentY;
          currentY += lineHeight;
          return (
            <View key={String(index)} style={{ position: 'absolute', left: 0, top: y }}>
              <JavaBitmapText text={line.text} x={0} y={0} scale={1} anchor={0} />
            </View>
          );
        })}

        {/* Close button — hg.java softkey "Đóng" */}
        <View style={styles.detailCloseRow}>
          <TouchableOpacity style={styles.detailCloseButton} onPress={onClose}>
            <JavaBitmapText text="Đóng" x={0} y={0} scale={1} anchor={1} />
          </TouchableOpacity>
        </View>
      </View>
    </View>
  );
};
