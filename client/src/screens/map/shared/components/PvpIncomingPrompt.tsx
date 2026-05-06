import React from 'react';
import { Text, TouchableOpacity, View } from 'react-native';
import { PvpCornerFrame } from './PvpCornerFrame';
import type { PvpChallengeTicket } from '../../../battle';
import { styles } from './PvpIncomingPrompt.styles';

export interface PvpIncomingPromptState {
  ticket: PvpChallengeTicket;
  status: 'pending' | 'starting';
}

interface PvpIncomingPromptProps {
  prompt: PvpIncomingPromptState;
  onAccept: (ticket: PvpChallengeTicket) => void;
  onDecline: (ticket: PvpChallengeTicket) => void;
}

const formatPvpStake = (stake: number): string =>
  `${Math.max(0, Math.floor(stake / 1000)).toLocaleString('vi-VN')}.000 KEN`;

export const PvpIncomingPrompt: React.FC<PvpIncomingPromptProps> = ({ prompt, onAccept, onDecline }) => {
  const busy = prompt.status === 'starting';
  return (
    <View style={styles.overlay} pointerEvents="box-none">
      <View style={styles.backdrop} />
      <PvpCornerFrame
        style={styles.frame}
        showCorners={true}
        cornerAsset={require('../../../../../assets/ui/00_corner_frames/_corner.png')}
      >
        <Text style={styles.title}>Khiêu Chiến</Text>
        <Text style={styles.message}>
          {prompt.ticket.challengerUsername} muốn thách đấu với bạn.
        </Text>
        <Text style={styles.meta}>Cược: {formatPvpStake(prompt.ticket.stake)}</Text>
        <View style={styles.footer}>
          <TouchableOpacity activeOpacity={0.85} style={styles.button} onPress={() => onDecline(prompt.ticket)} disabled={busy}>
            <Text style={styles.buttonText}>Từ chối</Text>
          </TouchableOpacity>
          <TouchableOpacity activeOpacity={0.85} style={[styles.button, styles.buttonPrimary]} onPress={() => onAccept(prompt.ticket)} disabled={busy}>
            <Text style={styles.buttonPrimaryText}>{busy ? 'Đang vào...' : 'Đồng ý'}</Text>
          </TouchableOpacity>
        </View>
      </PvpCornerFrame>
    </View>
  );
};
