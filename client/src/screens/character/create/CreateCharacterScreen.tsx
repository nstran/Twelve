import React, { useMemo, useState } from 'react';
import {
  View,
  Text,
  Image,
  ImageBackground,
  TouchableOpacity,
  Alert,
} from 'react-native';
import { styles } from './CreateCharacterScreen.styles';
import { SocketClient } from '../../../network/SocketClient';
import { SoftkeyBar } from '../../../components/SoftkeyBar';
import { PopupMenu, MenuItem } from '../../../components/PopupMenu';
import { MENU_START, MENU_LOGOUT } from '../../../constants/MenuConstants';
import { CREATE_CHARACTER_ASSETS } from './assets';
import { Dimensions } from 'react-native';
import {
  ELEMENT_OPTIONS,
  GENDER_OPTIONS,
} from './legacyCatalog';
import { LegacyCreateCharacterPreview } from './LegacyCreateCharacterPreview';

const { width: SCREEN_WIDTH } = Dimensions.get('window');

const ASSET_ARROW_LEFT = CREATE_CHARACTER_ASSETS.arrowLeft;
const ASSET_ARROW_RIGHT = CREATE_CHARACTER_ASSETS.arrowRight;

interface CreateCharacterScreenProps {
  onSuccess: () => void;
  onCancel: () => void;
}

const MENU_ITEMS: MenuItem[] = [
  MENU_START,
  MENU_LOGOUT,
];

type SelectorKey = 'gender' | 'element';

interface SelectorRowProps {
  label: string;
  value: string;
  active: boolean;
  onPrev: () => void;
  onNext: () => void;
  onPress: () => void;
}

const SelectorRow: React.FC<SelectorRowProps> = ({
  label,
  value,
  active,
  onPrev,
  onNext,
  onPress,
}) => {
  return (
    <TouchableOpacity style={[styles.selectionRow, active && styles.selectionRowActive]} onPress={onPress} activeOpacity={0.8}>
      <Text style={styles.label}>{label}</Text>
      <View style={[styles.valueBox, active && styles.valueBoxActive]}>
        {active ? (
          <TouchableOpacity style={styles.arrowButton} onPress={onPrev} hitSlop={6}>
            <Image source={ASSET_ARROW_LEFT} style={styles.arrowIcon} resizeMode="contain" />
          </TouchableOpacity>
        ) : <View style={styles.arrowSpacer} />}
        <Text style={[styles.valueText, active && styles.valueTextActive]} numberOfLines={1}>{value}</Text>
        {active ? (
          <TouchableOpacity style={styles.arrowButton} onPress={onNext} hitSlop={6}>
            <Image source={ASSET_ARROW_RIGHT} style={styles.arrowIcon} resizeMode="contain" />
          </TouchableOpacity>
        ) : <View style={styles.arrowSpacer} />}
      </View>
    </TouchableOpacity>
  );
};

export const CreateCharacterScreen: React.FC<CreateCharacterScreenProps> = ({ onSuccess, onCancel }) => {
  const client = SocketClient.getInstance();

  const [element, setElement] = useState(0);
  const [genderIdx, setGenderIdx] = useState(0);
  const [activeSelector, setActiveSelector] = useState<SelectorKey>('gender');
  const [menuVisible, setMenuVisible] = useState(false);
  const [selectedIndex, setSelectedIndex] = useState(0);

  React.useEffect(() => {
    const onCharSuccess = (msg: string) => Alert.alert('Thành Công', msg, [{ text: 'Bắt đầu', onPress: onSuccess }]);
    const onCharFailed = (msg: string) => Alert.alert('Thất Bại', msg);

    client.on('createCharSuccess', onCharSuccess);
    client.on('createCharFailed', onCharFailed);

    return () => {
      client.off('createCharSuccess', onCharSuccess);
      client.off('createCharFailed', onCharFailed);
    };
  }, [client, onSuccess]);

  const handleCreate = () => {
    client.createCharacter(
      ELEMENT_OPTIONS[element]?.value ?? 0,
      0, // Face
      0, // Hair
      0, // Hair Color
      0, // Skin
    );
  };

  const handleMenuSelect = (id: number) => {
    setMenuVisible(false);
    switch (id) {
      case 1: handleCreate(); break;
      case 0: onCancel();     break;
    }
  };

  const changeIndex = (length: number, setter: React.Dispatch<React.SetStateAction<number>>) => (delta: number) => {
    setter((current) => {
      const next = current + delta;
      if (next < 0) return length - 1;
      if (next >= length) return 0;
      return next;
    });
  };

  const nextGender = changeIndex(GENDER_OPTIONS.length, setGenderIdx);
  const nextElement = changeIndex(ELEMENT_OPTIONS.length, setElement);

  const selectorRows = useMemo(() => ([
    {
      key: 'gender' as const,
      label: 'Giới Tính',
      value: GENDER_OPTIONS[genderIdx]?.label ?? GENDER_OPTIONS[0].label,
      onPrev: () => nextGender(-1),
      onNext: () => nextGender(1),
    },
    {
      key: 'element' as const,
      label: 'Hệ',
      value: ELEMENT_OPTIONS[element]?.label ?? ELEMENT_OPTIONS[0].label,
      onPrev: () => nextElement(-1),
      onNext: () => nextElement(1),
    },
  ]), [element, genderIdx, nextElement, nextGender]);

  const handleLeftSoftkey  = () => menuVisible ? handleMenuSelect(MENU_ITEMS[selectedIndex].id as number) : setMenuVisible(true);
  const handleRightSoftkey = () => menuVisible ? setMenuVisible(false) : onCancel();

  return (
    <View style={styles.container}>
      <Image source={CREATE_CHARACTER_ASSETS.background} style={styles.background} resizeMode="stretch" />

      <View style={styles.legacyScene}>
        <View style={styles.previewColumn}>
          <View style={styles.previewStage}>
            <LegacyCreateCharacterPreview
              genderIndex={genderIdx}
              faceIndex={0}
              hairIndex={0}
              hairColorIndex={0}
            />
            <Image source={CREATE_CHARACTER_ASSETS.stone} style={styles.stonePlatform} resizeMode="contain" />
          </View>
        </View>

        <ImageBackground source={CREATE_CHARACTER_ASSETS.panelBorder} style={styles.selectionPanel} imageStyle={{ resizeMode: 'stretch' }}>
          {selectorRows.map((row) => (
            <SelectorRow
              key={row.key}
              label={row.label}
              value={row.value}
              active={activeSelector === row.key}
              onPrev={row.onPrev}
              onNext={row.onNext}
              onPress={() => setActiveSelector(row.key)}
            />
          ))}
          <View style={styles.panelWatermark} />
        </ImageBackground>
      </View>

      <PopupMenu
        visible={menuVisible}
        items={MENU_ITEMS}
        selectedIndex={selectedIndex}
        onSelect={(item) => handleMenuSelect(item.id as number)}
        onIndexChange={setSelectedIndex}
        onClose={() => setMenuVisible(false)}
      />

      <View style={styles.softKeyBarContainer}>
        <SoftkeyBar
          width={SCREEN_WIDTH}
          leftLabel={menuVisible ? 'Chọn' : 'Menu'}
          rightLabel={menuVisible ? 'Đóng' : 'Thoát'}
          onLeftPress={handleLeftSoftkey}
          onRightPress={handleRightSoftkey}
          onCenterPress={menuVisible ? () => setMenuVisible(false) : undefined}
        />
      </View>
    </View>
  );
};
