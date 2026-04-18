import React from 'react';
import {
  Text,
  StyleSheet,
  View,
  TouchableOpacity,
  Image,
} from 'react-native';
import { LegacyBaseDialog } from './LegacyBaseDialog';
import { CREATE_CHARACTER_ASSETS } from '../screens/character/create/assets';

interface LegacyConfirmDialogProps {
  visible: boolean;
  title?: string;
  message: string;
  confirmLabel?: string;
  cancelLabel?: string;
  onConfirm: () => void;
  onCancel: () => void;
}

export const LegacyConfirmDialog: React.FC<LegacyConfirmDialogProps> = ({
  visible,
  title = 'Chú ý',
  message,
  confirmLabel = 'Có',
  cancelLabel = 'Không',
  onConfirm,
  onCancel,
}) => {
  return (
    <LegacyBaseDialog 
      visible={visible} 
      onClose={onCancel} 
      backgroundPattern={CREATE_CHARACTER_ASSETS.dragonPattern}
      backgroundColor="#FFFFFF"
    >
      <View style={styles.container}>
        {title ? <Text style={styles.title}>{title}</Text> : null}
        <Text style={styles.message}>{message}</Text>
        
        <View style={styles.buttonRow}>
          <TouchableOpacity onPress={onConfirm} activeOpacity={0.7} style={styles.btnWrapper}>
            <View style={styles.btnBackground}>
              <Image
                source={CREATE_CHARACTER_ASSETS.ornateCorner}
                style={styles.btnPart}
                resizeMode="stretch"
              />
              <Image
                source={CREATE_CHARACTER_ASSETS.ornateCorner}
                style={[styles.btnPart, styles.btnPartRotated]}
                resizeMode="stretch"
              />
            </View>
            <View style={styles.btnTextOverlay}>
              <Text style={styles.btnText}>{confirmLabel}</Text>
            </View>
          </TouchableOpacity>

          <TouchableOpacity onPress={onCancel} activeOpacity={0.7} style={styles.btnWrapper}>
            <View style={styles.btnBackground}>
              <Image
                source={CREATE_CHARACTER_ASSETS.ornateCorner}
                style={styles.btnPart}
                resizeMode="stretch"
              />
              <Image
                source={CREATE_CHARACTER_ASSETS.ornateCorner}
                style={[styles.btnPart, styles.btnPartRotated]}
                resizeMode="stretch"
              />
            </View>
            <View style={styles.btnTextOverlay}>
              <Text style={styles.btnText}>{cancelLabel}</Text>
            </View>
          </TouchableOpacity>
        </View>
      </View>
    </LegacyBaseDialog>
  );
};

const styles = StyleSheet.create({
  container: {
    paddingVertical: 12,
    paddingHorizontal: 25, // Generous horizontal padding
    minWidth: 280, // Minimum base width
    maxWidth: 400, // Maximum width before wrapping
    alignItems: 'center',
  },
  title: {
    fontSize: 20,
    fontWeight: 'bold',
    color: '#000',
    textAlign: 'center',
    letterSpacing: 0.5,
    marginBottom: 8, // Specific gap after title
  },
  message: {
    fontSize: 16,
    color: '#000',
    textAlign: 'center',
    lineHeight: 22,
    width: '100%',
    marginBottom: 15, // Specific gap after message
  },
  buttonRow: {
    flexDirection: 'row',
    justifyContent: 'center',
    gap: 30,
    width: '100%',
  },
  btnWrapper: {
    width: 90,
    height: 32,
    justifyContent: 'center',
    alignItems: 'center',
  },
  btnBackground: {
    flexDirection: 'row',
    ...StyleSheet.absoluteFillObject,
  },
  btnPart: {
    width: 45,
    height: 32,
  },
  btnPartRotated: {
    transform: [{ scaleX: -1 }],
  },
  btnTextOverlay: {
    ...StyleSheet.absoluteFillObject,
    justifyContent: 'center',
    alignItems: 'center',
    zIndex: 10,
    paddingBottom: 2, // Slight adjustment for legacy font alignment
  },
  btnText: {
    color: '#fff',
    fontSize: 15,
    fontWeight: 'bold',
    textShadowColor: 'rgba(0, 0, 0, 0.75)',
    textShadowOffset: { width: 0, height: 1 },
    textShadowRadius: 1,
  },
});
