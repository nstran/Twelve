import React from 'react';
import {
  View,
  StyleSheet,
  Modal,
  TouchableWithoutFeedback,
  type StyleProp,
  type ViewStyle,
} from 'react-native';
import { CornerFrame } from '../CornerFrame/CornerFrame';

export interface BaseDialogProps {
  visible: boolean;
  onClose?: () => void;
  children?: React.ReactNode;
  width?: number | string;
  style?: StyleProp<ViewStyle>;
  contentStyle?: StyleProp<ViewStyle>;
  cornerAsset?: any;
  backgroundPattern?: any;
  backgroundColor?: string;
}

/**
 * Base component for all legacy-styled dialogs.
 * Provides the dark overlay and the CornerFrame shell.
 */
export const BaseDialog: React.FC<BaseDialogProps> = ({
  visible,
  onClose,
  children,
  width = '80%',
  style,
  contentStyle,
  cornerAsset,
  backgroundPattern,
  backgroundColor,
}) => {
  return (
    <Modal
      transparent
      visible={visible}
      animationType="fade"
      onRequestClose={onClose}
    >
      <TouchableWithoutFeedback onPress={onClose}>
        <View style={styles.overlay}>
          <TouchableWithoutFeedback>
            <View style={[styles.dialogWrapper, style]}>
              <CornerFrame 
                style={styles.frame}
                contentStyle={[styles.content, contentStyle]}
                cornerAsset={cornerAsset}
                backgroundPattern={backgroundPattern}
                backgroundColor={backgroundColor}
              >
                {children}
              </CornerFrame>
            </View>
          </TouchableWithoutFeedback>
        </View>
      </TouchableWithoutFeedback>
    </Modal>
  );
};

const styles = StyleSheet.create({
  overlay: {
    flex: 1,
    backgroundColor: 'rgba(0, 0, 0, 0.5)',
    justifyContent: 'center',
    alignItems: 'center',
  },
  dialogWrapper: {
    maxWidth: 400,
    minHeight: 100,
  },
  content: {
    padding: 0,
    alignItems: 'center',
    justifyContent: 'center',
  },
  frame: {
    flex: 1,
  },
});
