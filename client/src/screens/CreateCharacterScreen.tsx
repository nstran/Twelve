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
import { SocketClient } from '../network/SocketClient';
import { BODIES, HAIRS, FACES, SWORDS, FRONT_ARMS } from '../assets/AssetIndex';

interface CreateCharacterScreenProps {
  onSuccess: () => void;
  onCancel: () => void;
}

const ELEMENTS = ['KIM', 'MỘC', 'THỦY', 'HỎA', 'THỔ'];

export const CreateCharacterScreen: React.FC<CreateCharacterScreenProps> = ({ onSuccess, onCancel }) => {
  const client = SocketClient.getInstance();
  
  // ── States for Selections ──────────────────────────────────────────
  const [element,     setElement]     = useState(0);
  const [genderIdx,   setGenderIdx]   = useState(0); // 0: NAM (Mặc định), 1: NỮ
  const [hairIdx,     setHairIdx]     = useState(-1); // -1 = Không có tóc (trọc)
  const [faceIdx,     setFaceIdx]     = useState(-1); // -1 = Không có mặt (ẩn mắt)
  const [swordIdx,    setSwordIdx]    = useState(0);

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

  const SelectorRow = ({ label, value, options, onPrev, onNext, suffix = "" }: any) => (
    <View style={styles.selectionRow}>
      <Text style={styles.label}>{label}</Text>
      <View style={styles.selector}>
        <TouchableOpacity style={styles.arrow} onPress={onPrev}>
          <Text style={styles.arrowText}>{'<'}</Text>
        </TouchableOpacity>
        <Text style={styles.valueText}>
          {value === -1 || options.length === 0 ? (label === "MÁI TÓC" ? "TRỌC" : "TRỐNG") : `${suffix} ${value + 1} / ${options.length}`}
        </Text>
        <TouchableOpacity style={styles.arrow} onPress={onNext}>
          <Text style={styles.arrowText}>{'>'}</Text>
        </TouchableOpacity>
      </View>
    </View>
  );

  return (
    <View style={styles.container}>
      <Image source={require('../../assets/createcs/bk.png')} style={styles.background} />

      <View style={styles.headerContainer}>
        <View style={styles.headerFrame}><Text style={styles.headerText}>KHỞI TẠO TƯỚNG QUÂN</Text></View>
      </View>

      <View style={styles.previewContainer}>
        <Animated.View style={{ transform: [{ translateY: floatingAnim }], alignItems: 'center' }}>
          <Image source={require('../../assets/createcs/stone.png')} style={styles.stonePlatform} />
          
          {/* ── Layered Character ── */}
          <View style={styles.characterStack}>
             {/* Lớp 1: Thân (Body) */}
             <Image source={BODIES[genderIdx]} style={styles.bodyLayer} />

             {/* Lớp 2: Kiếm - Dùng style riêng swordNu viết thêm ở cuối file cho Nữ */}
             <Image 
                source={SWORDS[genderIdx]} 
                style={genderIdx === 1 ? styles.swordNu : styles.swordLayer} 
             />

             {/* Lớp 3: Bàn tay - Dùng style riêng frontArmNu viết thêm ở cuối file cho Nữ */}
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
          {/* Menu chọn Giới tính (NAM/NỮ) */}
          <SelectorRow label="GIỚI TÍNH" value={genderIdx} options={BODIES} suffix={genderIdx === 0 ? "NAM" : "NỮ"}
            onPrev={() => setGenderIdx(v => (v > 0 ? 0 : 1))}
            onNext={() => setGenderIdx(v => (v < 1 ? 1 : 0))} />

          <SelectorRow label="HỆ PHÁI" value={element} options={ELEMENTS} suffix=""
            onPrev={() => setElement(v => (v > 0 ? v - 1 : ELEMENTS.length - 1))}
            onNext={() => setElement(v => (v < ELEMENTS.length - 1 ? v + 1 : 0))} />

          <SelectorRow label="THẦN THÁI" value={faceIdx} options={FACES} suffix="MẮT"
            onPrev={() => setFaceIdx(v => v > -1 ? v - 1 : FACES.length - 1)}
            onNext={() => setFaceIdx(v => v < FACES.length - 1 ? v + 1 : -1)} />

          <SelectorRow label="MÁI TÓC" value={hairIdx} options={HAIRS} suffix="TÓC"
            onPrev={() => setHairIdx(v => HAIRS.length > 0 ? (v > -1 ? v - 1 : HAIRS.length - 1) : -1)}
            onNext={() => setHairIdx(v => HAIRS.length > 0 ? (v < HAIRS.length - 1 ? v + 1 : -1) : -1)} />

          <SelectorRow label="VŨ KHÍ" value={swordIdx} options={SWORDS} suffix="KIẾM"
            onPrev={() => setSwordIdx(v => (v > 0 ? v - 1 : SWORDS.length - 1))}
            onNext={() => setSwordIdx(v => (v < SWORDS.length - 1 ? v + 1 : 0))} />
        </ScrollView>
      </View>

      <View style={styles.softKeyBar}>
        <TouchableOpacity style={styles.softKey} onPress={handleCreate}><Text style={styles.softKeyText}>CHỌN XONG</Text></TouchableOpacity>
        <View style={styles.softKeyDivider} /><TouchableOpacity style={styles.softKey} onPress={onCancel}><Text style={styles.softKeyText}>QUAY LẠI</Text></TouchableOpacity>
      </View>
    </View>
  );
};
