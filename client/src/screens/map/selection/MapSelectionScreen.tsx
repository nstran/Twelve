import React, { useState, useRef, useEffect } from 'react';
import {
  View,
  Text,
  Image,
  Dimensions,
  Animated,
  PanResponder,
  GestureResponderEvent,
  PanResponderGestureState,
} from 'react-native';
import { styles, MAP_WIDTH, MAP_HEIGHT } from './MapSelectionScreen.styles';
import { SoftkeyBar }     from '../../../components/controls/SoftkeyBar/SoftkeyBar';
import { fetchWorldMapCatalog, MAPS, MapInfo }  from '../../../data/MapData';
import { MAP_SELECTION_ASSETS } from './assets';

const { width: SCREEN_WIDTH, height: SCREEN_HEIGHT } = Dimensions.get('window');

const ASSET_MAP_BG  = MAP_SELECTION_ASSETS.background;
const ASSET_LOCK    = MAP_SELECTION_ASSETS.lock;
const ASSET_HAND    = MAP_SELECTION_ASSETS.hand;
const ASSET_ARROW   = MAP_SELECTION_ASSETS.arrow;

// Java old world map hit/label/lock coordinates come from oh.java.
// The extracted RN asset is 480x480, while old drawing code had a 16px UI/menu
// gutter around a logical 512 space. Use the actual extracted asset size to
// avoid drifting lock overlays after moving catalog to server.
const JAVA_WORLD_MAP_WIDTH = 480;
const JAVA_WORLD_MAP_HEIGHT = 480;
const JAVA_LOCK_WIDTH = 17;
const JAVA_LOCK_HEIGHT = 20;
const JAVA_CURSOR_WIDTH = 16;
const JAVA_CURSOR_HEIGHT = 16;
const WORLD_MAP_SCALE_X = MAP_WIDTH / JAVA_WORLD_MAP_WIDTH;
const WORLD_MAP_SCALE_Y = MAP_HEIGHT / JAVA_WORLD_MAP_HEIGHT;
const WORLD_MAP_SCALE = WORLD_MAP_SCALE_X;
const SOFTKEY_HEIGHT = 28;
const VIEWPORT_WIDTH = SCREEN_WIDTH;
const VIEWPORT_HEIGHT = Math.max(1, SCREEN_HEIGHT - SOFTKEY_HEIGHT);

interface MapSelectionScreenProps {
  apiBaseUrl: string;
  onSelect: (map: MapInfo) => void;
  onBack: () => void;
}

interface CursorPosition {
  x: number;
  y: number;
}

const clamp = (value: number, min: number, max: number): number =>
  Math.max(min, Math.min(max, value));

const getEventPoint = (event: GestureResponderEvent): CursorPosition => ({
  x: event.nativeEvent.locationX,
  y: event.nativeEvent.locationY,
});

const getScaledHitBox = (map: MapInfo) => ({
  x: map.hitX * WORLD_MAP_SCALE_X,
  y: map.hitY * WORLD_MAP_SCALE_Y,
  width: map.hitWidth * WORLD_MAP_SCALE_X,
  height: map.hitHeight * WORLD_MAP_SCALE_Y,
});

const pointInsideMap = (point: CursorPosition, map: MapInfo): boolean => {
  const hitBox = getScaledHitBox(map);
  return (
    point.x >= hitBox.x &&
    point.x <= hitBox.x + hitBox.width &&
    point.y >= hitBox.y &&
    point.y <= hitBox.y + hitBox.height
  );
};

export const MapSelectionScreen: React.FC<MapSelectionScreenProps> = ({ apiBaseUrl, onSelect, onBack }) => {
  const [maps, setMaps] = useState<MapInfo[]>(MAPS);
  const [focusedId, setFocusedId] = useState<string | null>(null);
  const [mapOffset, setMapOffset] = useState({ x: 0, y: 0 });
  const [cursorPosition, setCursorPosition] = useState<CursorPosition>({ x: 460, y: 780 });
  const dragStartOffsetRef = useRef({ x: 0, y: 0 });
  const didMoveRef = useRef(false);
  const mapOffsetRef = useRef(mapOffset);

  // Animation for Java hand pointer.
  const handAnim = useRef(new Animated.Value(0)).current;

  useEffect(() => {
    mapOffsetRef.current = mapOffset;
  }, [mapOffset]);

  useEffect(() => {
    Animated.loop(
      Animated.sequence([
        Animated.timing(handAnim, { toValue: -10, duration: 500, useNativeDriver: true }),
        Animated.timing(handAnim, { toValue: 0, duration: 500, useNativeDriver: true }),
      ])
    ).start();
  }, [handAnim]);

  useEffect(() => {
    let isMounted = true;

    void fetchWorldMapCatalog(apiBaseUrl)
      .then((serverMaps) => {
        if (!isMounted || serverMaps.length === 0) {
          return;
        }

        setMaps(serverMaps);
      })
      .catch(() => {
        // Fallback catalog in MapData mirrors server Java reconstruction.
      });

    return () => {
      isMounted = false;
    };
  }, [apiBaseUrl]);

  const maxOffsetX = Math.max(0, MAP_WIDTH - VIEWPORT_WIDTH);
  const maxOffsetY = Math.max(0, MAP_HEIGHT - VIEWPORT_HEIGHT);
  const focusedMap = focusedId ? maps.find(m => m.id === focusedId) ?? null : null;

  const updateFocusFromViewportPoint = (viewportPoint: CursorPosition): void => {
    const mapPoint = {
      x: viewportPoint.x + mapOffsetRef.current.x,
      y: viewportPoint.y + mapOffsetRef.current.y,
    };
    const hitMap = maps.find((map) => pointInsideMap(mapPoint, map)) ?? null;
    setFocusedId(hitMap?.id ?? null);
    setCursorPosition(mapPoint);
  };

  const panResponder = useRef(
    PanResponder.create({
      onStartShouldSetPanResponder: () => true,
      onMoveShouldSetPanResponder: () => true,
      onPanResponderGrant: (event: GestureResponderEvent) => {
        dragStartOffsetRef.current = mapOffsetRef.current;
        didMoveRef.current = false;
        updateFocusFromViewportPoint(getEventPoint(event));
      },
      onPanResponderMove: (event: GestureResponderEvent, gestureState: PanResponderGestureState) => {
        didMoveRef.current = didMoveRef.current || Math.abs(gestureState.dx) > 2 || Math.abs(gestureState.dy) > 2;

        const nextOffset = {
          x: clamp(dragStartOffsetRef.current.x - gestureState.dx, 0, maxOffsetX),
          y: clamp(dragStartOffsetRef.current.y - gestureState.dy, 0, maxOffsetY),
        };

        mapOffsetRef.current = nextOffset;
        setMapOffset(nextOffset);
        updateFocusFromViewportPoint(getEventPoint(event));
      },
      onPanResponderRelease: (event: GestureResponderEvent) => {
        updateFocusFromViewportPoint(getEventPoint(event));
      },
      onPanResponderTerminate: (event: GestureResponderEvent) => {
        updateFocusFromViewportPoint(getEventPoint(event));
      },
    })
  ).current;

  const renderMapPoint = (map: MapInfo) => {
    const isLocked  = !map.unlocked;
    const hitBox = getScaledHitBox(map);

    return (
      <View
        key={map.id}
        pointerEvents="none"
        style={[
          styles.mapPoint,
          {
            left: hitBox.x,
            top: hitBox.y,
            width: hitBox.width,
            height: hitBox.height,
          }
        ]}
      >
        {isLocked && (
          <Image
            source={ASSET_LOCK}
            style={[
              styles.mapIcon,
              {
                left: (map.lockX - map.hitX) * WORLD_MAP_SCALE_X,
                top: (map.lockY - map.hitY) * WORLD_MAP_SCALE_Y,
                width: JAVA_LOCK_WIDTH * WORLD_MAP_SCALE,
                height: JAVA_LOCK_HEIGHT * WORLD_MAP_SCALE,
              },
            ]}
          />
        )}

        <Text
          style={[
            styles.mapLabel,
            {
                left: (map.labelX - map.hitX) * WORLD_MAP_SCALE_X,
                top: (map.labelY - map.hitY) * WORLD_MAP_SCALE_Y,
            },
            focusedId === map.id && styles.mapLabelFocused,
            map.id === 'hoalu' && { color: '#FF4444' }
          ]}
        >
          {map.name}
        </Text>
      </View>
    );
  };

  const cursorSource = focusedMap ? ASSET_HAND : ASSET_ARROW;
  const cursorLeft = cursorPosition.x - mapOffset.x;
  const cursorTop = cursorPosition.y - mapOffset.y;

  return (
    <View style={styles.container}>
      <View style={styles.mapViewport} {...panResponder.panHandlers}>
        <View
          style={[
            styles.panningContainer,
            {
              transform: [
                { translateX: -mapOffset.x },
                { translateY: -mapOffset.y },
              ],
            },
          ]}
        >
          <Image source={ASSET_MAP_BG} style={styles.background} />

          {maps.map(renderMapPoint)}
        </View>

        <Animated.Image
          source={cursorSource}
          style={[
            styles.cursorIndicator,
            {
              left: cursorLeft,
              top: cursorTop,
              width: JAVA_CURSOR_WIDTH * WORLD_MAP_SCALE,
              height: JAVA_CURSOR_HEIGHT * WORLD_MAP_SCALE,
              transform: focusedMap ? [{ translateY: handAnim }] : [],
            },
          ]}
        />
      </View>

      <SoftkeyBar
        width={SCREEN_WIDTH}
        leftLabel={focusedMap?.unlocked ? 'Vào Thành' : ' '}
        onLeftPress={() => {
          if (focusedMap?.unlocked) onSelect(focusedMap);
        }}
        centerLabel=""
        rightLabel="Đăng Xuất"
        onRightPress={onBack}
      />
    </View>
  );
};