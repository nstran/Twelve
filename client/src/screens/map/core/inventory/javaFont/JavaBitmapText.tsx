import React from 'react';
import { Image, View } from 'react-native';

const BLACK_FONT_ASSET = require('../../../../../../assets/login/04_font_candidate/_blackfont.png');
const CHAR_MAP = " 0123456789.,:!?()+-*/#$%abcdefghijklmnopqrstuvwxyzáàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵđABCDĐEFGHIJKLMNOPQRSTUVWXYZÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴ\\\"@<=>;_&'`^~{}[]";
const GLYPH_X = [0,2,8,13,19,25,31,37,43,49,55,61,62,64,65,66,71,74,77,84,87,92,96,103,108,119,124,130,84,77,61,0,6,90,119,130,91,12,21,27,33,39,45,48,52,65,56,108,96,122,71,101,75,80,85,90,12,17,22,27,45,56,65,95,106,111,127,0,32,38,50,5,118,70,76,82,88,11,61,132,124,101,95,134,103,109,17,23,29,35,41,47,53,115,5,121,128,67,74,81,88,95,101,29,107,121,11,19,35,43,51,59,0,127,113,5,0,67,74,81,87,93,100,118,10,27,15,61,107,34,22,40,48,55,124,0,111,63,69,76,83,90,101,117,8,27,14,34,41,48,55,124,107,62,69,76,0,83,90,97,114,7,21,27,14,20,33,39,45,51,121,128,104,132,131,57,60,110,65,83,91,113,73,0,26,8,16,34,42,50,121,59,99,68,77,86,108,0,24,50,7,31,40,115,124,57,16,77,93,100,66,130,70,0,93,0,115,99,60,84,0,84,107,49,10,23,73,109,73,0,0];
const GLYPH_Y = [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,7,7,9,9,5,7,7,5,9,9,9,9,9,9,9,9,9,9,9,9,10,12,12,14,15,15,15,16,16,16,16,16,16,16,16,16,16,17,18,18,18,18,19,19,24,25,25,25,26,26,17,19,22,26,17,26,27,28,28,28,28,28,28,28,28,29,29,29,34,35,35,36,36,36,37,37,37,38,38,38,38,39,39,30,39,40,41,42,44,45,45,46,46,46,46,47,47,48,17,47,48,48,48,49,50,51,53,52,54,54,54,55,55,55,55,56,56,57,57,57,58,59,60,61,63,63,63,64,64,64,64,64,65,57,68,69,69,69,69,69,71,72,72,73,39,51,71,75,73,75,76,76,76,77,79,80,81,81,81,81,83,84,87,87,88,88,88,88,91,92,92,93,93,95,96,96,99,93,99,99,99,100,84,19,104,88,76,88,26,38,100,1,1,100,103,104,104,104,104,104,0,0];
const GLYPH_W = [2,6,5,6,6,6,6,6,6,6,6,1,2,1,1,5,3,3,7,3,5,4,7,5,11,5,6,5,6,6,4,6,6,1,3,5,1,9,6,6,6,6,3,4,4,6,5,9,5,5,4,5,5,5,5,5,5,5,5,5,5,5,5,6,5,7,5,5,6,6,6,6,6,6,6,6,7,6,6,2,2,2,4,1,6,6,6,6,6,6,6,6,7,6,6,7,7,7,7,7,7,6,6,6,6,6,8,8,8,8,8,8,5,5,5,5,5,7,7,6,6,7,7,6,5,7,7,3,4,6,5,8,7,8,6,8,6,6,7,7,7,11,6,7,6,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,6,6,6,6,6,6,6,6,7,6,6,3,3,3,5,3,8,8,8,8,8,8,8,8,8,8,8,9,9,9,9,9,9,7,7,7,7,7,9,9,9,9,9,9,7,7,7,7,7,4,4,10,6,6,6,2,7,7,1,2,7,7,5,5,3,3,1,0,0];
const GLYPH_H = [1,9,9,9,9,9,9,9,9,9,9,2,4,7,9,9,12,12,7,1,5,12,9,12,9,7,10,7,10,7,10,9,10,9,11,10,10,7,7,7,9,9,7,7,9,7,7,7,7,9,7,10,10,10,10,9,10,12,12,12,12,12,10,10,10,11,12,12,10,10,10,10,9,10,10,10,11,12,12,10,10,10,10,11,10,10,10,10,9,10,10,10,11,12,12,8,10,10,10,10,10,10,10,10,10,9,9,10,10,10,10,11,12,12,12,12,9,10,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,11,9,9,9,9,9,9,9,9,9,12,12,12,12,11,12,12,12,12,12,14,12,12,12,12,12,14,12,12,12,12,11,12,12,12,12,12,14,12,12,12,12,11,12,12,12,12,11,12,12,12,12,12,14,9,12,12,12,12,11,12,12,12,12,11,11,12,12,12,12,13,12,12,12,12,11,12,4,10,7,3,7,9,1,9,3,2,4,3,12,12,12,12,12,0,0];
const GLYPH_OFFSET_Y = [11,3,3,3,3,3,3,3,3,3,3,10,10,5,3,3,2,2,5,8,2,2,3,2,3,5,2,5,2,5,2,5,2,3,3,2,2,5,5,5,5,5,5,5,3,5,5,5,5,5,5,2,2,2,2,5,2,0,0,0,0,2,2,2,2,1,0,2,2,2,2,2,5,2,2,2,1,0,2,2,2,2,2,3,2,2,2,2,5,2,2,2,1,0,2,4,2,2,2,2,4,2,2,2,2,5,3,2,2,2,2,3,2,2,2,2,5,2,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,3,0,0,0,0,0,0,3,0,0,0,0,3,0,0,0,0,3,1,0,0,0,0,1,0,0,0,0,3,2,2,3,5,6,5,5,13,3,2,2,3,7,2,2,2,2,2,0,0];
const FONT_HEIGHT = 14;
const FONT_BASELINE = 11;
const CHAR_SPACING = 1;
const FONT_SHEET_WIDTH = 138;
const FONT_SHEET_HEIGHT = 117;
const CHAR_INDEX = new Map(Array.from(CHAR_MAP).map((char, index) => [char, index]));

export type JavaBitmapTextAnchor = 0 | 1 | 2;

interface JavaBitmapTextProps {
  text: string;
  x: number;
  y: number;
  scale: number;
  anchor?: JavaBitmapTextAnchor;
  bold?: boolean;
}

const getGlyphIndex = (char: string) => CHAR_INDEX.get(char) ?? 0;

export const measureJavaBitmapText = (text: string) => Array.from(text).reduce((width, char) => width + GLYPH_W[getGlyphIndex(char)] + CHAR_SPACING, 0);

export const JavaBitmapText: React.FC<JavaBitmapTextProps> = ({ text, x, y, scale, anchor = 0, bold = false }) => {
  const logicalWidth = measureJavaBitmapText(text);
  let cursor = 0;
  let left = x;
  if (anchor === 1) {
    left -= (logicalWidth * scale) / 2;
  }
  if (anchor === 2) {
    left -= logicalWidth * scale;
  }

  return (
    <View pointerEvents="none" style={{ position: 'absolute', left, top: y, width: logicalWidth * scale + (bold ? scale : 0), height: FONT_HEIGHT * scale, overflow: 'hidden' }}>
      {Array.from(text).map((char, index) => {
        const glyph = getGlyphIndex(char);
        const glyphW = GLYPH_W[glyph];
        const glyphH = GLYPH_H[glyph];
        const glyphX = GLYPH_X[glyph];
        const glyphY = GLYPH_Y[glyph];
        const glyphTop = (GLYPH_OFFSET_Y[glyph] - 1) * scale;
        const glyphLeft = cursor * scale;
        cursor += glyphW + CHAR_SPACING;

        return (
          <View key={String(index)} style={{ position: 'absolute', left: glyphLeft, top: glyphTop, width: glyphW * scale + (bold ? scale : 0), height: glyphH * scale, overflow: 'hidden' }}>
            <Image source={BLACK_FONT_ASSET} style={{ position: 'absolute', left: -glyphX * scale, top: -glyphY * scale, width: FONT_SHEET_WIDTH * scale, height: FONT_SHEET_HEIGHT * scale }} resizeMode="stretch" />
            {bold ? <Image source={BLACK_FONT_ASSET} style={{ position: 'absolute', left: (1 - glyphX) * scale, top: -glyphY * scale, width: FONT_SHEET_WIDTH * scale, height: FONT_SHEET_HEIGHT * scale }} resizeMode="stretch" /> : null}
          </View>
        );
      })}
    </View>
  );
};

export const JAVA_BITMAP_FONT_HEIGHT = FONT_HEIGHT;
export const JAVA_BITMAP_FONT_BASELINE = FONT_BASELINE;
