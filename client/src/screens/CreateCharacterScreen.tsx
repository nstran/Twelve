import React, { useState, useEffect, useRef } from 'react';
import { 
  View, 
  Text, 
  Image, 
  TouchableOpacity, 
  Animated, 
  Alert 
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
  const [bodyIdx,     setBodyIdx]     = useState(0);
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
    // Chúng ta tạm map face/hair index về server. 
    // Trong thực tế, server cần biết ID của asset.
    client.createCharacter(element, faceIdx, hairIdx, 0, bodyIdx);
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
             {/* Lớp 1: Thân */}
             <Image source={BODIES[bodyIdx]} style={styles.bodyLayer} />
             {/* Lớp 2: Kiếm (Weapon) */}
             <Image source={SWORDS[swordIdx]} style={styles.swordLayer} />
             {/* Lớp 3: Bàn tay đè lên (Lấy từ ảnh mới v4) */}
             <Image source={FRONT_ARMS[0]} style={styles.frontArmLayer} />
             {/* Lớp 4: Mắt */}
             {faceIdx !== -1 && <Image source={FACES[faceIdx]} style={styles.faceLayer} />}
             {/* Lớp 5: Tóc */}
             {hairIdx !== -1 && <Image source={HAIRS[hairIdx]} style={styles.hairLayer} />}
          </View>
        </Animated.View>
      </View>

      <View style={styles.selectionPanel}>
        <View style={styles.selectionRow}>
          <Text style={styles.label}>HỆ PHÁI</Text>
          <View style={styles.selector}>
            <TouchableOpacity style={styles.arrow} onPress={() => setElement(v => v>0?v-1:ELEMENTS.length-1)}>
               <Text style={styles.arrowText}>{'<'}</Text>
            </TouchableOpacity>
            <Text style={styles.valueText}>{ELEMENTS[element]}</Text>
            <TouchableOpacity style={styles.arrow} onPress={() => setElement(v => v<ELEMENTS.length-1?v+1:0)}>
               <Text style={styles.arrowText}>{'>'}</Text>
            </TouchableOpacity>
          </View>
        </View>

        <SelectorRow label="VÓC DÁNG" value={bodyIdx} options={BODIES} suffix="KIỂU"
          onPrev={() => setBodyIdx(v => v>0?v-1:BODIES.length-1)}
          onNext={() => setBodyIdx(v => v<BODIES.length-1?v+1:0)} />

        <SelectorRow label="THẦN THÁI" value={faceIdx} options={FACES} suffix="MẮT"
          onPrev={() => setFaceIdx(v => v>-1?v-1:FACES.length-1)}
          onNext={() => setFaceIdx(v => v<FACES.length-1?v+1:-1)} />

        <SelectorRow label="MÁI TÓC" value={hairIdx} options={HAIRS} suffix="TÓC"
          onPrev={() => setHairIdx(v => HAIRS.length > 0 ? (v > -1 ? v - 1 : HAIRS.length - 1) : -1)}
          onNext={() => setHairIdx(v => HAIRS.length > 0 ? (v < HAIRS.length - 1 ? v + 1 : -1) : -1)} />

        <SelectorRow label="VŨ KHÍ" value={swordIdx} options={SWORDS} suffix="KIẾM"
          onPrev={() => setSwordIdx(v => SWORDS.length > 0 ? (v > 0 ? v - 1 : SWORDS.length - 1) : 0)}
          onNext={() => setSwordIdx(v => SWORDS.length > 0 ? (v < SWORDS.length - 1 ? v + 1 : 0) : 0)} />
      </View>

      <View style={styles.softKeyBar}>
        <TouchableOpacity style={styles.softKey} onPress={handleCreate}><Text style={styles.softKeyText}>CHỌN XONG</Text></TouchableOpacity>
        <View style={styles.softKeyDivider} /><TouchableOpacity style={styles.softKey} onPress={onCancel}><Text style={styles.softKeyText}>QUAY LẠI</Text></TouchableOpacity>
      </View>
    </View>
  );
};
