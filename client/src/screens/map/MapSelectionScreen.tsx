import React, { useState, useRef, useEffect } from 'react';
import { 
  View, 
  Text, 
  Image, 
  ScrollView, 
  TouchableOpacity, 
  Dimensions,
  Animated
} from 'react-native';
import { styles, MAP_WIDTH, MAP_HEIGHT } from './MapSelectionScreen.styles';
import { SoftkeyBar }     from '../../components/SoftkeyBar';
import { MAPS, MapInfo }  from '../../data/MapData';

const { width: SCREEN_WIDTH, height: SCREEN_HEIGHT } = Dimensions.get('window');

const ASSET_MAP_BG  = require('../../../assets/m/m.png');
const ASSET_LOCK    = require('../../../assets/m/lock.png');
const ASSET_ARENA   = require('../../../assets/m/arena.png');
const ASSET_ROOM    = require('../../../assets/m/room.png');
const ASSET_HAND    = require('../../../assets/m/hand.png');

interface MapSelectionScreenProps {
  onSelect: (map: MapInfo) => void;
  onBack: () => void;
}

export const MapSelectionScreen: React.FC<MapSelectionScreenProps> = ({ onSelect, onBack }) => {
  const [focusedId, setFocusedId] = useState(MAPS[0].id);
  const scrollRefX = useRef<ScrollView>(null);
  const scrollRefY = useRef<ScrollView>(null);
  
  // Animation for the hand pointer
  const handAnim = useRef(new Animated.Value(0)).current;

  useEffect(() => {
    Animated.loop(
      Animated.sequence([
        Animated.timing(handAnim, { toValue: -10, duration: 500, useNativeDriver: true }),
        Animated.timing(handAnim, { toValue: 0, duration: 500, useNativeDriver: true }),
      ])
    ).start();
  }, []);

  const focusedMap = MAPS.find(m => m.id === focusedId) || MAPS[0];

  const renderMapPoint = (map: MapInfo) => {
    const isFocused = map.id === focusedId;
    const isLocked  = !map.unlocked;

    return (
      <View 
        key={map.id} 
        style={[
          styles.mapPoint, 
          { left: `${map.x}%`, top: `${map.y}%` }
        ]}
      >
        <TouchableOpacity 
          activeOpacity={0.7}
          onPress={() => setFocusedId(map.id)}
          style={styles.iconContainer}
        >
          {isFocused && (
            <Animated.Image 
              source={ASSET_HAND} 
              style={[styles.focusIndicator, { transform: [{ translateY: handAnim }] }]} 
            />
          )}

          {isLocked && (
            <Image source={ASSET_LOCK} style={styles.mapIcon} />
          )}
        </TouchableOpacity>

        <Text 
          style={[
            styles.mapLabel, 
            isFocused && styles.mapLabelFocused,
            map.id === 'hoalu' && { color: '#FF4444' } // Hoa Lư màu đỏ như ảnh mẫu
          ]}
        >
          {map.name}
        </Text>
      </View>
    );
  };

  return (
    <View style={styles.container}>
      {/* ── Pannable World Map ───────────────────────────────────── */}
      <ScrollView 
        ref={scrollRefY}
        showsVerticalScrollIndicator={false}
        style={{ flex: 1 }}
        contentContainerStyle={{ flexGrow: 1 }}
      >
        <ScrollView 
          horizontal
          ref={scrollRefX}
          showsHorizontalScrollIndicator={false}
          style={{ flex: 1 }}
          contentContainerStyle={{ flexGrow: 1 }}
        >
          <View style={styles.panningContainer}>
            <Image source={ASSET_MAP_BG} style={styles.background} />
            
            {MAPS.map(renderMapPoint)}
          </View>
        </ScrollView>
      </ScrollView>

      {/* ── Softkey Bar ── */}
      <SoftkeyBar 
        width={SCREEN_WIDTH}
        leftLabel="Chọn"
        onLeftPress={() => {
          if (focusedMap.unlocked) onSelect(focusedMap);
          else console.log('[Map] Location is locked');
        }}
        centerLabel={focusedMap.unlocked ? "Vào" : "Nâng cấp"} 
        onCenterPress={() => { if(focusedMap.unlocked) onSelect(focusedMap); }}
        rightLabel="Quay lại"
        onRightPress={onBack}
      />
    </View>
  );
};
