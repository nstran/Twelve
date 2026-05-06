import React, { useEffect, useState } from 'react';
import { ActivityIndicator, Image, Pressable, ScrollView, Text, TextInput, TouchableOpacity, View } from 'react-native';
import { PvpCornerFrame } from './PvpCornerFrame';
import { PvpArenaRow } from './PvpArenaRow';
import { PvpFontStyles } from '../JavaFontMetrics';
import { CharacterRenderer } from '../../../character';
import type { PvpChallengeTicket, PvpOpponentEntry } from '../../../battle';
import { styles } from './PvpDialog.styles';

export type PvpDialogMode = 'arena' | 'challenge';
export type PvpDialogStatus = 'idle' | 'loading' | 'ready' | 'error' | 'starting';

export interface PvpStartOptions {
  stake: number;
  allowSpectators: boolean;
  oneWay: boolean;
  disableSpecialSkills: boolean;
}

interface PvpDialogProps {
  mode: PvpDialogMode;
  opponents: PvpOpponentEntry[];
  status: PvpDialogStatus;
  error: string | null;
  selectedTarget: string;
  stakeThousands: string;
  allowSpectators: boolean;
  oneWay: boolean;
  disableSpecialSkills: boolean;
  pendingTicket: PvpChallengeTicket | null;
  onClose: () => void;
  onRefresh: () => void;
  onSelectTarget: (target: string) => void;
  onStakeThousandsChange: (value: string) => void;
  onAllowSpectatorsChange: (value: boolean) => void;
  onOneWayChange: (value: boolean) => void;
  onDisableSpecialSkillsChange: (value: boolean) => void;
  onStart: (target: string, options: PvpStartOptions) => void;
}

const parseStakeThousands = (value: string): number => {
  const normalized = value.replace(/[^\d]/g, '');
  if (!normalized) {
    return 0;
  }

  return Math.max(0, Number(normalized)) * 1000;
};

const PvpCheckbox: React.FC<{
  label: string;
  checked: boolean;
  onChange: (value: boolean) => void;
}> = ({ label, checked, onChange }) => (
  <TouchableOpacity
    activeOpacity={0.85}
    style={styles.checkRow}
    onPress={() => onChange(!checked)}
  >
    <View style={[styles.checkBox, checked && styles.checkBoxActive]}>
      {checked ? <Text style={styles.checkMark}>✓</Text> : null}
    </View>
    <Text style={styles.checkLabel}>{label}</Text>
  </TouchableOpacity>
);

const HIDDEN_DRAGON_ASSET = require('../../../../../assets/battle/09_hidden_pieces/hiddendragon.png');
const HIDDEN_DRAGON_SIZE = { width: 107, height: 78 };

/**
 * Formats remaining time in hh:mm:ss format.
 * Java evidence: os.java:119-120, ew.java:80-82 use `i.b(l2, "hh:mm:ss")`
 */
const formatCountdown = (remainingMs: number): string => {
  const totalSeconds = Math.max(0, Math.floor(remainingMs / 1000));
  const hours = Math.floor(totalSeconds / 3600);
  const minutes = Math.floor((totalSeconds % 3600) / 60);
  const seconds = totalSeconds % 60;
  return `${String(hours).padStart(2, '0')}:${String(minutes).padStart(2, '0')}:${String(seconds).padStart(2, '0')}`;
};

export const PvpDialog: React.FC<PvpDialogProps> = ({
  mode,
  opponents,
  status,
  error,
  selectedTarget,
  stakeThousands,
  allowSpectators,
  oneWay,
  disableSpecialSkills,
  pendingTicket,
  onClose,
  onRefresh,
  onSelectTarget,
  onStakeThousandsChange,
  onAllowSpectatorsChange,
  onOneWayChange,
  onDisableSpecialSkillsChange,
  onStart,
}) => {
  // Timer countdown for challenge mode (os.java:117-120, ew.java:79-82)
  const [countdown, setCountdown] = useState('');
  useEffect(() => {
    if (mode !== 'challenge' || !pendingTicket) {
      setCountdown('');
      return;
    }
    const update = () => {
      const remaining = pendingTicket.expiresAtUnixMs - Date.now();
      setCountdown(remaining > 0 ? formatCountdown(remaining) : '00:00:00');
    };
    update();
    const timer = setInterval(update, 1000);
    return () => clearInterval(timer);
  }, [mode, pendingTicket]);

  const isBusy = status === 'loading' || status === 'starting';
  const trimmedTarget = selectedTarget.trim();
  const exactOpponent = opponents.find(
    (opponent) => opponent.username.toLowerCase() === trimmedTarget.toLowerCase(),
  );
  const selectedOpponent = mode === 'arena'
    ? exactOpponent ?? opponents[0] ?? null
    : exactOpponent ?? null;
  const arenaFocusCard = mode === 'arena' ? selectedOpponent : null;

  return (
    <View style={styles.overlay}>
      <Pressable style={styles.backdrop} onPress={isBusy ? undefined : onClose} />
      <PvpCornerFrame
        style={styles.frame}
        showCorners={true}
        cornerAsset={require('../../../../../assets/ui/00_corner_frames/_corner.png')}
      >
        {/* Horizontal separator line - os.java:116 */}
        <View style={[styles.separator, mode === 'arena' ? styles.separatorArena : styles.separatorChallenge]} />

        {/* Bottom decoration - os.java:110 */}
        <Image
          source={HIDDEN_DRAGON_ASSET}
          style={[styles.hiddenDragon, { width: HIDDEN_DRAGON_SIZE.width, height: HIDDEN_DRAGON_SIZE.height }]}
          resizeMode="stretch"
        />

        {/* Timer countdown - os.java:117-120, ew.java:79-82 */}
        {mode === 'challenge' && countdown && pendingTicket ? (
          <Text style={styles.timerText}>{`${pendingTicket.targetUsername} ${countdown}`}</Text>
        ) : null}

        <View style={styles.header}>
          <Text style={styles.title}>{mode === 'arena' ? 'Lôi Đài' : 'Khiêu Chiến'}</Text>
          <TouchableOpacity activeOpacity={0.85} onPress={onRefresh} disabled={isBusy}>
            <Text style={[styles.headerAction, isBusy && styles.actionDisabled]}>Cập nhật</Text>
          </TouchableOpacity>
        </View>

        {mode === 'challenge' ? (
          <View style={styles.challengeForm}>
            <Text style={styles.label}>Nhập nick</Text>
            <TextInput
              value={selectedTarget}
              onChangeText={onSelectTarget}
              editable={!isBusy}
              autoCapitalize="none"
              autoCorrect={false}
              style={styles.input}
              placeholderTextColor="#9b7b51"
              placeholder="Tên nhân vật"
            />
            <Text style={styles.label}>Đặt Cược</Text>
            <View style={styles.stakeRow}>
              <TextInput
                value={stakeThousands}
                onChangeText={onStakeThousandsChange}
                editable={!isBusy}
                keyboardType="number-pad"
                style={[styles.input, styles.stakeInput]}
                placeholderTextColor="#9b7b51"
                placeholder="0"
              />
              <Text style={styles.stakeUnit}>.000 KEN</Text>
            </View>
            <View style={styles.checkGrid}>
              <PvpCheckbox label="Cho xem" checked={allowSpectators} onChange={onAllowSpectatorsChange} />
              <PvpCheckbox label="1 chiều" checked={oneWay} onChange={onOneWayChange} />
              <PvpCheckbox
                label="Không chơi Tuyệt Chiêu"
                checked={disableSpecialSkills}
                onChange={onDisableSpecialSkillsChange}
              />
            </View>
          </View>
        ) : null}

        {mode === 'arena' ? (
          <View style={styles.arenaBoard}>
            <ScrollView style={styles.list} contentContainerStyle={styles.listContent}>
              {opponents.map((opponent) => {
                const selected = opponent.username.toLowerCase() === trimmedTarget.toLowerCase();
                return (
                  <PvpArenaRow
                    key={opponent.username}
                    opponent={opponent}
                    selected={selected}
                    disabled={isBusy}
                    onPress={() => onSelectTarget(opponent.username)}
                  />
                );
              })}
            </ScrollView>

            {opponents.length === 0 ? (
              <View style={styles.arenaEmptyPanel}>
                <Text style={styles.arenaEmptyTitle}>Bảng Lôi Đài đang trống</Text>
                <Text style={styles.arenaEmptyMeta}>Bấm Cập nhật để lấy danh sách đối thủ</Text>
              </View>
            ) : null}

            {arenaFocusCard ? (
              <View style={styles.legacyPreviewCard}>
                <View style={styles.legacyPreviewAvatar}>
                  <CharacterRenderer appearance={arenaFocusCard.appearance} scale={0.88} anchorToBody facing="right" />
                </View>
                <View style={styles.legacyPreviewInfo}>
                  <Text style={styles.legacyPreviewName} numberOfLines={1}>{arenaFocusCard.username}</Text>
                  <Text style={styles.legacyPreviewMeta} numberOfLines={1}>Cấp: {arenaFocusCard.level}</Text>
                  <Text style={styles.legacyPreviewMeta} numberOfLines={1}>{arenaFocusCard.statusMessage || 'Hào Kiệt'}</Text>
                </View>
              </View>
            ) : null}
          </View>
        ) : selectedOpponent ? (
          <View style={styles.previewRow}>
            <View style={styles.previewAvatar}>
              <CharacterRenderer appearance={selectedOpponent.appearance} scale={0.9} anchorToBody facing="right" />
            </View>
            <View style={styles.opponentInfo}>
              <Text style={styles.opponentName} numberOfLines={1}>{selectedOpponent.username}</Text>
              <Text style={styles.opponentMeta} numberOfLines={1}>
                Cấp {selectedOpponent.level}  |  Danh vọng {selectedOpponent.honor}
              </Text>
              <Text style={styles.opponentMeta} numberOfLines={1}>
                Sinh lực {selectedOpponent.currentHp}/{selectedOpponent.maxHp}
              </Text>
            </View>
          </View>
        ) : null}

        {status === 'loading' ? (
          <View style={styles.statusRow}>
            <ActivityIndicator color="#f2d383" />
            <Text style={styles.statusText}>Đang tải...</Text>
          </View>
        ) : null}
        {error ? <Text style={styles.errorText}>{error}</Text> : null}
        {status !== 'loading' && opponents.length === 0 ? (
          <Text style={styles.emptyText}>Chưa có đối thủ</Text>
        ) : null}

        <View style={styles.footer}>
          <TouchableOpacity activeOpacity={0.85} style={styles.button} onPress={onClose} disabled={isBusy}>
            <Text style={styles.buttonText}>Hủy</Text>
          </TouchableOpacity>
          <TouchableOpacity
            activeOpacity={0.85}
            style={[styles.button, styles.buttonPrimary, isBusy && styles.buttonDisabled]}
            disabled={isBusy}
            onPress={() => onStart(trimmedTarget, {
              stake: parseStakeThousands(stakeThousands),
              allowSpectators,
              oneWay,
              disableSpecialSkills,
            })}
          >
            <Text style={styles.buttonPrimaryText}>{mode === 'arena' ? 'Đánh!' : 'Gửi'}</Text>
          </TouchableOpacity>
        </View>
      </PvpCornerFrame>
    </View>
  );
};
