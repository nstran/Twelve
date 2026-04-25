import React from 'react';
import {
  GestureResponderEvent,
  StyleSheet,
  Text,
  TouchableWithoutFeedback,
  View,
} from 'react-native';
import { Actor } from '../network/SocketClient';

// Actor is defined in SocketClient.ts and re-exported here for convenience
export type { Actor };

interface MapRendererProps {
  width: number; // In tiles
  height: number; // In tiles
  tileSize: number;
  data: number[]; // Tile IDs
  actors: Actor[];
  onMapPress?: (x: number, y: number) => void;
}

const tileColors = [
  '#2e7d32',
  '#3f8f3f',
  '#256b25',
  '#6d8f3f',
  '#2f5f7f',
];

/**
 * Legacy tile-map renderer used by the old fallback `MainScreen`.
 *
 * Source/reasoning:
 * - Java world-map flow in `og.java` sends `ks.a().b("M99", go.x)` after
 *   selecting a town; this renderer is only a temporary non-side-scroll
 *   fallback for map packets that are not yet reconstructed as Hoa Lư runtime.
 * - The previous implementation used `@shopify/react-native-skia` Canvas and
 *   crashed on web when CanvasKit/WebGLRenderer was unavailable. Keep this
 *   fallback pure React Native so selecting/returning to legacy map screens
 *   never blanks the app because of CanvasKit bootstrap.
 */
export const MapRenderer: React.FC<MapRendererProps> = ({
  width,
  height,
  tileSize,
  data,
  actors,
  onMapPress,
}) => {
  const canvasWidth = width * tileSize;
  const canvasHeight = height * tileSize;

  const handlePress = (event: GestureResponderEvent) => {
    const { locationX, locationY } = event.nativeEvent;
    if (onMapPress) {
      onMapPress(Math.floor(locationX), Math.floor(locationY));
    }
  };

  return (
    <TouchableWithoutFeedback onPress={handlePress}>
      <View
        style={[
          styles.container,
          {
            width: canvasWidth,
            height: canvasHeight,
          },
        ]}
      >
        <View style={styles.mapLayer}>
          {data.map((tileId, index) => {
            const x = (index % width) * tileSize;
            const y = Math.floor(index / width) * tileSize;
            const color = tileColors[Math.abs(tileId) % tileColors.length];

            return (
              <View
                key={`tile-${index}`}
                style={[
                  styles.tile,
                  {
                    left: x,
                    top: y,
                    width: Math.max(1, tileSize - 0.5),
                    height: Math.max(1, tileSize - 0.5),
                    backgroundColor: color,
                  },
                ]}
              />
            );
          })}
        </View>

        <View pointerEvents="none" style={styles.actorLayer}>
          {actors.map((actor) => (
            <View
              key={`actor-${actor.id}`}
              style={[
                styles.actorHalo,
                {
                  left: actor.x - tileSize / 2,
                  top: actor.y - tileSize / 2,
                  width: tileSize,
                  height: tileSize,
                  borderRadius: tileSize / 2,
                },
              ]}
            >
              <View
                style={[
                  styles.actorDot,
                  {
                    width: tileSize / 1.5,
                    height: tileSize / 1.5,
                    borderRadius: tileSize / 3,
                  },
                ]}
              />
              <Text style={styles.actorLabel} numberOfLines={1}>
                {actor.label}
              </Text>
            </View>
          ))}
        </View>
      </View>
    </TouchableWithoutFeedback>
  );
};

const styles = StyleSheet.create({
  container: {
    backgroundColor: '#000',
    borderWidth: 2,
    borderColor: '#ffd700',
    borderRadius: 8,
    overflow: 'hidden',
    elevation: 10,
    position: 'relative',
  },
  mapLayer: {
    ...StyleSheet.absoluteFillObject,
  },
  tile: {
    position: 'absolute',
  },
  actorLayer: {
    ...StyleSheet.absoluteFillObject,
  },
  actorHalo: {
    position: 'absolute',
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: 'rgba(255, 215, 0, 0.2)',
    borderWidth: 1,
    borderColor: 'rgba(255, 215, 0, 0.45)',
  },
  actorDot: {
    backgroundColor: '#ffd700',
    borderWidth: 1,
    borderColor: '#fff2a8',
  },
  actorLabel: {
    position: 'absolute',
    top: -15,
    minWidth: 48,
    color: '#fff2a8',
    fontSize: 10,
    textAlign: 'center',
    textShadowColor: '#000',
    textShadowRadius: 2,
  },
});