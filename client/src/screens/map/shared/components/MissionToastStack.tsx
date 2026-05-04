import React from 'react';
import { Text, View } from 'react-native';
import type { MapMissionToast } from '../../core/MapMission.types';
import { styles } from './MissionToastStack.styles';

interface MissionToastStackProps {
  toasts: MapMissionToast[];
}

export const MissionToastStack: React.FC<MissionToastStackProps> = ({ toasts }) => {
  if (toasts.length === 0) {
    return null;
  }

  return (
    <View style={styles.stack} pointerEvents="none">
      {toasts.map((toast) => (
        <View key={toast.id} style={styles.toast}>
          <Text style={styles.title}>{toast.title}</Text>
          <Text style={styles.text}>{toast.message}</Text>
          {toast.lines.slice(0, 2).map((line) => (
            <Text key={`${toast.id}-${line}`} style={styles.line}>{line}</Text>
          ))}
        </View>
      ))}
    </View>
  );
};
