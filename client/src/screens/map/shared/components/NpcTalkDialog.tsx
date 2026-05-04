import React from 'react';
import { Text, View } from 'react-native';
import { CornerFrame } from '../../../../components/ui/CornerFrame/CornerFrame';
import { styles } from './NpcTalkDialog.styles';

export interface NpcTalkDialogState {
  npcId: string;
  message: string;
}

interface NpcTalkDialogProps {
  dialog: NpcTalkDialogState;
}

export const NpcTalkDialog: React.FC<NpcTalkDialogProps> = ({ dialog }) => (
  <View style={styles.overlay} pointerEvents="box-none">
    <CornerFrame style={styles.frame} contentStyle={styles.content}>
      <Text style={styles.title}>NPC</Text>
      <Text style={styles.message}>{dialog.message}</Text>
    </CornerFrame>
  </View>
);
