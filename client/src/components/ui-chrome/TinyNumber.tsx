import React from 'react';
import { Image, StyleSheet, View } from 'react-native';

const TINY_NUMBER_ASSET = require('../../../assets/ui/04_tabs_and_numbers/tinynumber.png');

const DIGIT_WIDTHS = [4, 3, 4, 4, 5, 4, 4, 4, 4, 4, 5] as const;
const DIGIT_X = [0, 4, 7, 11, 15, 20, 24, 28, 32, 36, 40] as const;
const SHEET_WIDTH = 45;
const SHEET_HEIGHT = 7;
const GLYPH_HEIGHT = 7;

export interface TinyNumberProps {
  value: number | string;
  x: number;
  y: number;
  scale?: number;
}

export const measureTinyNumber = (value: number | string): number => {
  const text = String(value);
  if (text.length === 0) {
    return 0;
  }

  let width = 0;
  for (const char of text) {
    const glyphIndex = char === '+' ? 10 : Number(char);
    if (!Number.isInteger(glyphIndex) || glyphIndex < 0 || glyphIndex > 10) {
      continue;
    }
    width += DIGIT_WIDTHS[glyphIndex] + 1;
  }

  return Math.max(0, width - 1);
};

export const TinyNumber: React.FC<TinyNumberProps> = ({ value, x, y, scale = 1 }) => {
  const text = String(value);
  let cursor = 0;

  return (
    <View style={[styles.root, { left: x * scale, top: y * scale, width: measureTinyNumber(text) * scale, height: GLYPH_HEIGHT * scale }]} pointerEvents="none">
      {Array.from(text).map((char, index) => {
        const glyphIndex = char === '+' ? 10 : Number(char);
        if (!Number.isInteger(glyphIndex) || glyphIndex < 0 || glyphIndex > 10) {
          return null;
        }

        const glyphW = DIGIT_WIDTHS[glyphIndex];
        const glyphX = DIGIT_X[glyphIndex];
        const left = cursor;
        cursor += glyphW + 1;

        return (
          <View key={`${char}-${index}`} style={[styles.clip, { left: left * scale, width: glyphW * scale, height: GLYPH_HEIGHT * scale }]}>
            <Image
              source={TINY_NUMBER_ASSET}
              style={[styles.sheet, { left: -glyphX * scale, width: SHEET_WIDTH * scale, height: SHEET_HEIGHT * scale }]}
              resizeMode="stretch"
            />
          </View>
        );
      })}
    </View>
  );
};

const styles = StyleSheet.create({
  root: {
    position: 'absolute',
  },
  clip: {
    position: 'absolute',
    top: 0,
    overflow: 'hidden',
  },
  sheet: {
    position: 'absolute',
    top: 0,
  },
});
