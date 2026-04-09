// ── Màn hình Khởi tạo Tướng quân (Modernized 2026) ──────────────────────────
// File này xử lý ghép lớp (Layering) cho nhân vật: Thân -> Kiếm -> Tay -> Mặt -> Tóc.

import React, { useState, useEffect, useRef } from 'react';
import { 
  View, 
  Text, 
  Image, 
  TouchableOpacity, 
  Animated, 
  Alert,
  ScrollView
} from 'react-native';
import { styles }       from './CreateCharacterScreen.styles';
import { SocketClient } from '../../network/SocketClient';
import { BODIES, HAIRS, FACES, SWORDS, FRONT_ARMS } from '../../assets/AssetIndex';
import { SoftkeyBar }   from '../../components/SoftkeyBar';
import { PopupMenu, MenuItem } from '../../components/PopupMenu';
import { MENU_START, MENU_LOGOUT } from '../../constants/MenuConstants';
import { Dimensions }   from 'react-native';

const { width: SCREEN_WIDTH } = Dimensions.get('window');

// ─── Assets ───────────────────────────────────────────────────────────────
const ASSET_ICON_OK     = require('../../../assets/ui/icons/icon_ok.png');
const ASSET_ICON_CANCEL = require('../../../assets/ui/icons/icon_cancel.png');
const ASSET_RED_SUN     = require('../../../assets/ui/icons/icon_sharpest_1.png');
const ASSET_ARROW       = require('../../../assets/ui/icons/arrowfocus1.png');

interface CreateCharacterScreenProps {
  onSuccess: () => void;
  onCancel: () => void;
}

const ELEMENTS = ['LÔI', 'HỎA', 'THỦY'];

const MENU_ITEMS: MenuItem[] = [
  MENU_START,
  MENU_LOGOUT,
];

// ─── Selector Component ──────────────────────────────────────────────────
const SelectorRow = ({ label, value, options, onPrev, onNext, suffix = "", hideCounter = false }: any) => {
  const displayValue = value === -1 || (Array.isArray(options) && options.length === 0)
    ? (label === "TÓC" ? "TRỌC" : "TRỐNG") 
    : hideCounter ? suffix : `${suffix} ${value + 1} / ${options.length || 0}`;

  return (
    <View style={styles.selectionRow}>
      <Text style={styles.label}>{label}</Text>
      <View style={styles.selector}>
        <TouchableOpacity style={styles.arrow} onPress={onPrev}>
          <Image 
            source={ASSET_ARROW} 
            style={[styles.arrowIcon, { transform: [{ rotate: '-90deg' }] }]} 
          />
        </TouchableOpacity>
        <Text style={styles.valueText}>{displayValue}</Text>
        <TouchableOpacity style={styles.arrow} onPress={onNext}>
          <Image 
            source={ASSET_ARROW} 
            style={[styles.arrowIcon, { transform: [{ rotate: '90deg' }] }]} 
          />
        </TouchableOpacity>
      </View>
    </View>
  );
};

export const CreateCharacterScreen: React.FC<CreateCharacterScreenProps> = ({ onSuccess, onCancel }) => {
  const client = SocketClient.getInstance();
  
  // ── States for Selections ──────────────────────────────────────────
  const [element,     setElement]     = useState(0);
  const [genderIdx,   setGenderIdx]   = useState(0); // 0: NAM (Mặc định), 1: NỮ
  const [hairIdx,     setHairIdx]     = useState(-1); // -1 = Không có tóc (trọc)
  const [faceIdx,     setFaceIdx]     = useState(-1); // -1 = Không có mặt (ẩn mắt)
  const [swordIdx,    setSwordIdx]    = useState(0);

  // ── States for Menu ────────────────────────────────────────────────
  const [menuVisible, setMenuVisible]     = useState(false);
  const [selectedIndex, setSelectedIndex] = useState(0);

  // ── Floating Animation ──────────────────────────────────────────────
  const floatingAnim = useRef(new Animated.Value(0)).current;

  useEffect(() => {
    Animated.loop(
      Animated.sequence([
        Animated.timing(floatingAnim, { toValue: 15, duration: 2500, useNativeDriver: true }),
        Animated.timing(floatingAnim, { toValue: 0,  duration: 2500, useNativeDriver: true }),
      ])
    ).start();

    const onCharSuccess = (msg: string) => Alert.alert('Thành Công', msg, [{ text: 'BẮT ĐẦU', onPress: onSuccess }]);
    const onCharFailed  = (msg: string) => Alert.alert('Thất Bại', msg);

    client.on('createCharSuccess', onCharSuccess);
    client.on('createCharFailed',  onCharFailed);

    return () => {
      client.off('createCharSuccess', onCharSuccess);
      client.off('createCharFailed',  onCharFailed);
    };
  }, []);

  const handleCreate = () => {
    client.createCharacter(element, faceIdx, hairIdx, 0, genderIdx);
  };

  const handleMenuSelect = (id: number) => {
    setMenuVisible(false);
    switch (id) {
      case 1: handleCreate(); break;
      case 2: handleCreate(); break; // Reuse create for now
      case 0: onCancel();     break;
    }
  };

  const handleLeftSoftkey  = () => menuVisible ? handleMenuSelect(MENU_ITEMS[selectedIndex].id as number) : setMenuVisible(true);
  const handleRightSoftkey = () => menuVisible ? setMenuVisible(false) : onCancel();

  return (
    <View style={styles.container}>
      <Image source={require('../../../assets/createcs/bk.png')} style={styles.background} />

      <View style={styles.previewContainer}>
        <Animated.View style={{ transform: [{ translateY: floatingAnim }], alignItems: 'center' }}>
          <Image source={require('../../../assets/createcs/stone.png')} style={styles.stonePlatform} />
          
          {/* ── Layered Character ── */}
          <View style={styles.characterStack}>
             {/* Lớp 1: Thân (Body) */}
             <Image source={BODIES[genderIdx]} style={styles.bodyLayer} />

             {/* Lớp 2: Kiếm */}
             <Image 
                source={SWORDS[genderIdx]} 
                style={genderIdx === 1 ? styles.swordNu : styles.swordLayer} 
             />

             {/* Lớp 3: Bàn tay */}
             <Image 
                source={FRONT_ARMS[genderIdx]} 
                style={genderIdx === 1 ? styles.frontArmNu : styles.frontArmLayer} 
             />

             {/* Lớp 4: Mắt */}
             {faceIdx !== -1 && <Image source={FACES[faceIdx]} style={styles.faceLayer} />}

             {/* Lớp 5: Tóc */}
             {hairIdx !== -1 && <Image source={HAIRS[hairIdx]} style={styles.hairLayer} />}
          </View>
        </Animated.View>
      </View>

      <View style={styles.selectionPanel}>
        <ScrollView showsVerticalScrollIndicator={false}>
          <SelectorRow 
            label="GIỚI TÍNH" 
            value={genderIdx} 
            options={BODIES} 
            suffix={genderIdx === 0 ? "NAM" : "NỮ"}
            hideCounter={true}
            onPrev={() => setGenderIdx(v => (v > 0 ? 0 : 1))}
            onNext={() => setGenderIdx(v => (v < 1 ? 1 : 0))} 
          />

          <SelectorRow 
            label="HỆ" 
            value={element} 
            options={ELEMENTS} 
            suffix={ELEMENTS[element]}
            hideCounter={true} // Xóa 1/3
            onPrev={() => setElement(v => (v > 0 ? v - 1 : ELEMENTS.length - 1))}
            onNext={() => setElement(v => (v < ELEMENTS.length - 1 ? v + 1 : 0))} 
          />

          <SelectorRow 
            label="MẮT" 
            value={faceIdx} 
            options={FACES} 
            hideCounter={true}
            onPrev={() => setFaceIdx(v => v > -1 ? v - 1 : FACES.length - 1)}
            onNext={() => setFaceIdx(v => v < FACES.length - 1 ? v + 1 : -1)} 
          />

          <SelectorRow 
            label="TÓC" 
            value={hairIdx} 
            options={HAIRS} 
            hideCounter={true}
            onPrev={() => setHairIdx(v => HAIRS.length > 0 ? (v > -1 ? v - 1 : HAIRS.length - 1) : -1)}
            onNext={() => setHairIdx(v => HAIRS.length > 0 ? (v < HAIRS.length - 1 ? v + 1 : -1) : -1)} 
          />
        </ScrollView>
      </View>

      {/* ── Popup menu ── */}
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
          leftIcon={menuVisible ? ASSET_ICON_OK : ASSET_RED_SUN}
          rightIcon={menuVisible ? ASSET_ICON_CANCEL : undefined}
          onLeftPress={handleLeftSoftkey}
          onRightPress={handleRightSoftkey}
          onCenterPress={menuVisible ? () => setMenuVisible(false) : undefined}
        />
      </View>
    </View>
  );
};
