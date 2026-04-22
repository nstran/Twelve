import React from 'react';
import { Image, ScrollView, Text, TouchableOpacity, View } from 'react-native';
import { BaseDialog } from '../../../components/ui/BaseDialog/BaseDialog';
import {
  type BattleSkillDefinition,
  type SkillFamilyCode,
  getSkillFamiliesForElement,
  isBattleSkillServerPacketReady,
} from '../core';

const PANEL_CORNER = require('../../../../assets/ui/00_corner_frames/4.png');

interface BattleSkillPanelProps {
  visible: boolean;
  elementIndex?: number;
  selectedFamily: SkillFamilyCode | null;
  onHighlight: (familyCode: SkillFamilyCode) => void;
  onCast: (familyCode: SkillFamilyCode) => void;
  onClose: () => void;
}

const SkillTile: React.FC<{
  skill: BattleSkillDefinition;
  selected: boolean;
  onPress: () => void;
}> = ({ skill, selected, onPress }) => {
  const packetReady = isBattleSkillServerPacketReady(skill.familyCode);

  return (
  <TouchableOpacity
    activeOpacity={0.9}
    onPress={onPress}
    style={{
      width: 72,
      height: 72,
      marginBottom: 10,
      borderWidth: selected ? 2 : 1,
      borderColor: selected ? '#3aa8ff' : '#b3b3b3',
      backgroundColor: selected ? '#f4fbff' : '#f1f1f1',
      alignItems: 'center',
      justifyContent: 'center',
      opacity: packetReady ? 1 : 0.55,
    }}
  >
    <Image
      source={skill.icon}
      resizeMode="contain"
      style={{ width: 54, height: 54, opacity: selected ? 1 : 0.75 }}
    />
    <Text
      style={{
        position: 'absolute',
        bottom: 3,
        left: 0,
        right: 0,
        textAlign: 'center',
        color: selected ? '#004a86' : '#555',
        fontSize: 8,
      }}
      >
        {skill.familyCode}
      </Text>
      {!packetReady && (
        <Text
          style={{
            position: 'absolute',
            top: 3,
            right: 4,
            color: '#7a2a2a',
            fontSize: 7,
            fontWeight: '700',
          }}
        >
          LOCK
        </Text>
      )}
  </TouchableOpacity>
  );
};

export const BattleSkillPanel: React.FC<BattleSkillPanelProps> = ({
  visible,
  elementIndex,
  selectedFamily,
  onHighlight,
  onCast,
  onClose,
}) => {
  const skills = React.useMemo(() => getSkillFamiliesForElement(elementIndex), [elementIndex]);
  const selectedSkill = React.useMemo(
    () => skills.find(skill => skill.familyCode === selectedFamily) ?? skills[0] ?? null,
    [selectedFamily, skills],
  );
  const selectedSkillPacketReady = selectedSkill
    ? isBattleSkillServerPacketReady(selectedSkill.familyCode)
    : false;

  return (
    <BaseDialog
      visible={visible}
      onClose={onClose}
      style={{ width: 340 }}
      cornerAsset={PANEL_CORNER}
      backgroundColor="#fdfdfd"
      contentStyle={{ paddingHorizontal: 12, paddingVertical: 10, alignItems: 'stretch' }}
    >
      <View style={{ width: 316 }}>
        <Text
          style={{
            color: '#111',
            fontSize: 20,
            fontWeight: '700',
            marginBottom: 4,
          }}
        >
          Tuyệt Chiêu
        </Text>
        <Text style={{ color: '#666', fontSize: 11, marginBottom: 10 }}>
          Panel nay dung family code + runtime Java. Name/damage cuoi cung van phai den tu server.
        </Text>

        <View
          style={{
            flexDirection: 'row',
            flexWrap: 'wrap',
            justifyContent: 'space-between',
            marginBottom: 8,
          }}
        >
          {skills.map(skill => (
            <SkillTile
              key={skill.familyCode}
              skill={skill}
              selected={skill.familyCode === selectedSkill?.familyCode}
              onPress={() => {
                if (skill.familyCode === selectedSkill?.familyCode) {
                  onCast(skill.familyCode);
                  return;
                }
                onHighlight(skill.familyCode);
              }}
            />
          ))}
        </View>

        {selectedSkill && (
          <ScrollView
            style={{
              maxHeight: 180,
              borderTopWidth: 1,
              borderTopColor: '#d8d8d8',
              paddingTop: 10,
            }}
          >
            <Text style={{ color: '#101010', fontSize: 19, fontWeight: '700', marginBottom: 6 }}>
              {selectedSkill.title}
            </Text>
            <Text style={{ color: '#2b2b2b', fontSize: 13, lineHeight: 18, marginBottom: 8 }}>
              Hệ {selectedSkill.elementLabel}. {selectedSkill.summary}
            </Text>
            <Text style={{ color: '#6d6d6d', fontSize: 12, lineHeight: 17 }}>
              {selectedSkill.serverNote}
            </Text>
            {!selectedSkillPacketReady && (
              <Text style={{ color: '#8b2d2d', fontSize: 12, lineHeight: 17, marginTop: 8 }}>
                Skill này đang khóa ở runtime hiện tại vì server chưa có target arrays battle thật cho family này.
              </Text>
            )}
          </ScrollView>
        )}
      </View>
    </BaseDialog>
  );
};
