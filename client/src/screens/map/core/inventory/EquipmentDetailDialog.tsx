import React from 'react';
import { Image, Pressable, Text, TouchableOpacity, View } from 'react-native';
import type { CharacterEquipmentItem } from '../../../character/shared';
import { resolveEquipmentIconAsset } from '../../../character/shared';
import { RANK_COLORS } from './InventoryLayout';
import { styles } from './InventoryScreen.styles';

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

  let durabilityStyle = styles.detailLine;
  let durabilityText = '';
  if (entry.maxDurability > 0) {
    const ratio = (entry.durability * 100) / entry.maxDurability;
    durabilityText = `Độ bền: ${entry.durability}/${entry.maxDurability}`;
    if (entry.durability <= 0) {
      durabilityText += ' (Đã hư hoàn toàn)';
      durabilityStyle = styles.detailLineRed;
    } else if (ratio < 30) {
      durabilityText += ' (Đã hư hỏng nặng)';
      durabilityStyle = styles.detailLineRed;
    }
  }

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

        {/* Name + enhancement — hg.java name line */}
        <View style={styles.detailNameRow}>
          <Text style={[styles.detailName, { color: rankColor }]} numberOfLines={1}>
            {entry.displayName}
          </Text>
          {entry.level > 0 ? (
            <Text style={styles.detailEnhancement}>+{entry.level}</Text>
          ) : null}
        </View>

        <View style={styles.detailSeparator} />

        {/* Required level — hg.java level check */}
        <Text style={levelOk ? styles.detailLine : styles.detailLineRed}>
          Yêu cầu cấp: {entry.requiredLevel}
        </Text>

        {/* Durability — hg.java durability display */}
        {durabilityText ? (
          <Text style={durabilityStyle}>{durabilityText}</Text>
        ) : null}

        {/* Stat lines — hg.java stat rendering in green */}
        {statLines.map((line) => (
          <Text key={line} style={styles.detailLineGreen}>{line}</Text>
        ))}

        {/* Description */}
        {entry.summary ? (
          <Text style={styles.detailDescription}>{entry.summary}</Text>
        ) : null}

        {/* Gender restriction — hg.java gender warning */}
        {!genderOk ? (
          <Text style={styles.detailLineRed}>Không phù hợp giới tính</Text>
        ) : null}

        {/* Trade warning */}
        {!entry.tradeable ? (
          <Text style={styles.detailLineRed}>Không thể giao dịch</Text>
        ) : null}

        {/* Close button — hg.java softkey "Đóng" */}
        <View style={styles.detailCloseRow}>
          <TouchableOpacity style={styles.detailCloseButton} onPress={onClose}>
            <Text style={styles.detailCloseText}>Đóng</Text>
          </TouchableOpacity>
        </View>
      </View>
    </View>
  );
};
