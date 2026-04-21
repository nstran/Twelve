import React, { useMemo, useState } from 'react';
import {
  View,
  Text,
  Image,
  TouchableOpacity,
  Alert,
} from 'react-native';
import { styles } from './CreateCharacterScreen.styles';
import { SocketClient } from '../../../network/SocketClient';
import { SoftkeyBar } from '../../../components/controls/SoftkeyBar/SoftkeyBar';
import { CornerFrame } from '../../../components/ui/CornerFrame/CornerFrame';
import { PopupMenu, MenuItem } from '../../../components/controls/PopupMenu/PopupMenu';
import { MENU_START, MENU_LOGOUT } from '../../../constants/MenuConstants';
import { CREATE_CHARACTER_ASSETS } from './assets';
import { Dimensions } from 'react-native';
import {
  ELEMENT_OPTIONS,
  GENDER_OPTIONS,
  HAIR_STYLE_OPTIONS,
  EYE_STYLE_OPTIONS,
  HAIR_COLOR_OPTIONS,
  SKIN_COLOR_OPTIONS,
} from './createCatalog';
import { CreateCharacterPreview } from './CreateCharacterPreview';
import { LoadingDialog } from '../../../components/dialogs/LoadingDialog/LoadingDialog';
import { ConfirmDialog } from '../../../components/dialogs/ConfirmDialog/ConfirmDialog';
import type { CharacterAppearance } from '../shared';


const { width: SCREEN_WIDTH } = Dimensions.get('window');

const ASSET_ARROW = CREATE_CHARACTER_ASSETS.arrowBlue;
const ASSET_MENU_ICON = CREATE_CHARACTER_ASSETS.iconMenu;
const ASSET_OK_ICON = CREATE_CHARACTER_ASSETS.iconOk;
const ASSET_CANCEL_ICON = CREATE_CHARACTER_ASSETS.iconCancel;

interface CreateCharacterScreenProps {
  onSuccess: (appearance: CharacterAppearance) => void;
  onCancel: () => void;
}

const MENU_ITEMS: MenuItem[] = [
  MENU_START,
  MENU_LOGOUT,
];

type SelectorKey = 'gender' | 'element' | 'face' | 'hair' | 'hairColor' | 'skin';

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
            <Image source={ASSET_ARROW} style={[styles.arrowIcon, styles.arrowIconLeft]} resizeMode="contain" />
          </TouchableOpacity>
        ) : <View style={styles.arrowSpacer} />}

        {/* Color swatch + label */}
        <View style={{ flexDirection: 'row', alignItems: 'center', flex: 1, justifyContent: 'center', gap: 5 }}>

          <Text style={[styles.valueText, active && styles.valueTextActive]} numberOfLines={1}>{value}</Text>
        </View>

        {active ? (
          <TouchableOpacity style={styles.arrowButton} onPress={onNext} hitSlop={6}>
            <Image source={ASSET_ARROW} style={styles.arrowIcon} resizeMode="contain" />
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
  const [faceIdx, setFaceIdx] = useState(0);
  const [hairIdx, setHairIdx] = useState(0);
  const [hairColorIdx, setHairColorIdx] = useState(0);
  const [skinColorIdx, setSkinColorIdx] = useState(0);

  const [activeSelector, setActiveSelector] = useState<SelectorKey>('face');
  const [menuVisible, setMenuVisible] = useState(false);
  const [selectedIndex, setSelectedIndex] = useState(0);

  const [loading, setLoading] = useState(false);
  const [logoutConfirmVisible, setLogoutConfirmVisible] = useState(false);


  React.useEffect(() => {
    const onCharSuccess = (_msg: string) => {
      setLoading(false);
      onSuccess({
        genderIndex:    genderIdx,
        faceIndex:      faceIdx,
        hairIndex:      hairIdx,
        hairColorIndex: hairColorIdx,
        skinColorIndex: skinColorIdx,
        elementIndex:   element,
      });
    };
    const onCharFailed = (msg: string) => {
      setLoading(false);
      Alert.alert('Thất Bại', msg);
    };


    client.on('createCharSuccess', onCharSuccess);
    client.on('createCharFailed', onCharFailed);

    return () => {
      client.off('createCharSuccess', onCharSuccess);
      client.off('createCharFailed', onCharFailed);
    };
  }, [client, onSuccess, genderIdx, faceIdx, hairIdx, hairColorIdx, skinColorIdx]);

  const genderKey = genderIdx === 0 ? 'male' : 'female';

  // Tạm thời khóa hệ tóc về 1 màu mặc định.
  const hairStyleOption = HAIR_STYLE_OPTIONS[genderKey][hairIdx] ?? HAIR_STYLE_OPTIONS[genderKey][0];
  const availableHairColors = HAIR_COLOR_OPTIONS.slice(0, hairStyleOption.numColors ?? HAIR_COLOR_OPTIONS.length);

  React.useEffect(() => {
    if (hairColorIdx >= availableHairColors.length) {
      setHairColorIdx(0);
    }
  }, [hairColorIdx, availableHairColors.length]);

  const handleCreate = () => {
    client.createCharacter(
      genderIdx,
      ELEMENT_OPTIONS[element]?.value ?? 0,
      faceIdx,
      hairIdx,
      hairColorIdx,
      skinColorIdx,
    );
  };

  const handleMenuSelect = (id: number) => {
    setMenuVisible(false);
    switch (id) {
      case 1: 
        setLoading(true);
        handleCreate(); 
        break;
      case 0: 
        setLogoutConfirmVisible(true);
        break;
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

  const nextGender = (delta: number) => {
    setGenderIdx((current) => {
      const next = (current + delta + 2) % 2;
      setFaceIdx(0);
      setHairIdx(0);
      setHairColorIdx(0);
      setSkinColorIdx(0);
      return next;
    });
  };

  const nextElement = changeIndex(ELEMENT_OPTIONS.length, setElement);
  const nextFace = changeIndex(EYE_STYLE_OPTIONS[genderKey].length, setFaceIdx);
  const nextHair = (delta: number) => {
    setHairIdx((current) => {
      const length = HAIR_STYLE_OPTIONS[genderKey].length;
      const next = (current + delta + length) % length;
      setHairColorIdx(0);
      return next;
    });
  };
  const nextHairColor = changeIndex(availableHairColors.length, setHairColorIdx);
  const nextSkinColor = changeIndex(SKIN_COLOR_OPTIONS.length, setSkinColorIdx);

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
    {
      key: 'face' as const,
      label: 'Khuôn Mặt',
      value: EYE_STYLE_OPTIONS[genderKey][faceIdx]?.label ?? 'N/A',
      onPrev: () => nextFace(-1),
      onNext: () => nextFace(1),
    },
    {
      key: 'hair' as const,
      label: 'Kiểu Tóc',
      value: HAIR_STYLE_OPTIONS[genderKey][hairIdx]?.label ?? 'N/A',
      onPrev: () => nextHair(-1),
      onNext: () => nextHair(1),
    },
    {
      key: 'hairColor' as const,
      label: 'Màu Tóc',
      value: availableHairColors[hairColorIdx]?.label ?? 'N/A',

      onPrev: () => nextHairColor(-1),
      onNext: () => nextHairColor(1),
    },
    {
      key: 'skin' as const,
      label: 'Màu Da',
      value: SKIN_COLOR_OPTIONS[skinColorIdx]?.label ?? 'N/A',

      onPrev: () => nextSkinColor(-1),
      onNext: () => nextSkinColor(1),
    },
  ]), [element, genderKey, genderIdx, faceIdx, hairColorIdx, availableHairColors, hairIdx, skinColorIdx]);

  const handleLeftSoftkey  = () => menuVisible ? handleMenuSelect(MENU_ITEMS[selectedIndex].id as number) : setMenuVisible(true);
  const handleRightSoftkey = () => {
    if (menuVisible) {
      setMenuVisible(false);
    }
  };

  return (
    <View style={styles.container}>
      <Image source={CREATE_CHARACTER_ASSETS.background} style={styles.background} resizeMode="stretch" />

      <View style={styles.legacyScene}>
        <View style={styles.stageLayout}>
          <View style={styles.previewColumn}>
            <View style={styles.previewStage}>
              <CreateCharacterPreview
                genderIndex={genderIdx}
                faceIndex={faceIdx}
                hairIndex={hairIdx}
                hairColorIndex={hairColorIdx}
                skinColorIndex={skinColorIdx}
                style={styles.previewSpriteCanvas}
              />
              <Image source={CREATE_CHARACTER_ASSETS.stone} style={styles.stonePlatform} resizeMode="contain" />
            </View>
          </View>

          <CornerFrame style={styles.selectionPanel} contentStyle={styles.selectionPanelInner}>
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
          </CornerFrame>
        </View>
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
          onLeftPress={handleLeftSoftkey}
          onRightPress={menuVisible ? handleRightSoftkey : undefined}
          onCenterPress={menuVisible ? () => setMenuVisible(false) : undefined}
          leftIcon={menuVisible ? ASSET_OK_ICON : ASSET_MENU_ICON}
          rightIcon={menuVisible ? ASSET_CANCEL_ICON : undefined}
        />
      </View>

      <LoadingDialog visible={loading} />

      <ConfirmDialog
        visible={logoutConfirmVisible}
        title="Chú ý"
        message="Bạn muốn đăng xuất không?"
        onConfirm={() => {
          setLogoutConfirmVisible(false);
          onCancel();
        }}
        onCancel={() => setLogoutConfirmVisible(false)}
      />
    </View>
  );
};
