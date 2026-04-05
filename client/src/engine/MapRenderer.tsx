import React from 'react';
import { Canvas, Rect, Group, Circle } from '@shopify/react-native-skia';
import { View, StyleSheet, TouchableWithoutFeedback, GestureResponderEvent } from 'react-native';
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

export const MapRenderer: React.FC<MapRendererProps> = ({ 
  width, height, tileSize, data, actors, onMapPress 
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
      <View style={styles.container}>
        <Canvas style={{ width: canvasWidth, height: canvasHeight }}>
          {/* Render Map Layer */}
          {data.map((tileId, index) => {
            const x = (index % width) * tileSize;
            const y = Math.floor(index / width) * tileSize;
            const color = '#2e7d32'; // Grass Green
            
            return (
              <Rect
                key={`tile-${index}`}
                x={x}
                y={y}
                width={tileSize - 0.5}
                height={tileSize - 0.5}
                color={color}
              />
            );
          })}

          {/* Render Actors Layer */}
          {actors.map((actor) => (
            <Group key={`actor-${actor.id}`}>
              <Circle 
                cx={actor.x} 
                cy={actor.y} 
                r={tileSize / 3} 
                color="#ffd700" 
              />
              <Circle 
                cx={actor.x} 
                cy={actor.y} 
                r={tileSize / 2.5} 
                color="rgba(255, 215, 0, 0.2)" 
              />
            </Group>
          ))}
        </Canvas>
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
  },
});
