import React from 'react';
import { Image, StyleSheet, View } from 'react-native';

const EMOTE_ASSETS = {
  smileys: require('../../../assets/ui/06_emotes/smileys.png'),
  onions: require('../../../assets/ui/06_emotes/onions.png'),
} as const;

export type EmotePack = keyof typeof EMOTE_ASSETS;

export interface EmoteGlyphProps {
  pack: EmotePack;
  index: number;
  x: number;
  y: number;
  frameWidth: number;
  frameHeight: number;
  columns: number;
  scale?: number;
}

export const EmoteGlyph: React.FC<EmoteGlyphProps> = ({ pack, index, x, y, frameWidth, frameHeight, columns, scale = 1 }) => {
  const safeColumns = Math.max(1, columns);
  const safeIndex = Math.max(0, index);
  const col = safeIndex % safeColumns;
  const row = Math.floor(safeIndex / safeColumns);
  const sheetWidth = safeColumns * frameWidth;
  const sheetHeight = (row + 1) * frameHeight;

  return (
    <View
      style={[
        styles.root,
        {
          left: x * scale,
          top: y * scale,
          width: frameWidth * scale,
          height: frameHeight * scale,
        },
      ]}
      pointerEvents="none"
    >
      <Image
        source={EMOTE_ASSETS[pack]}
        style={[
          styles.sheet,
          {
            left: -(col * frameWidth) * scale,
            top: -(row * frameHeight) * scale,
            width: sheetWidth * scale,
            height: sheetHeight * scale,
          },
        ]}
        resizeMode="stretch"
      />
    </View>
  );
};

const styles = StyleSheet.create({
  root: {
    position: 'absolute',
    overflow: 'hidden',
  },
  sheet: {
    position: 'absolute',
  },
});
