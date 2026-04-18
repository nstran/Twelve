import React from 'react';
import {
  Text,
  StyleSheet,
  View,
  TouchableOpacity,
  Image,
} from 'react-native';
import { BaseDialog } from './BaseDialog';
import { styles } from './ConfirmDialog.styles';
import { CREATE_CHARACTER_ASSETS } from '../screens/character/create/assets';

interface ConfirmDialogProps {
  visible: boolean;
  title?: string;
  message: string;
  confirmLabel?: string;
  cancelLabel?: string;
  onConfirm: () => void;
  onCancel: () => void;
}

export const ConfirmDialog: React.FC<ConfirmDialogProps> = ({
  visible,
  title = 'Chú ý',
  message,
  confirmLabel = 'Có',
  cancelLabel = 'Không',
  onConfirm,
  onCancel,
}) => {
  return (
    <BaseDialog 
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
    </BaseDialog>
  );
};
