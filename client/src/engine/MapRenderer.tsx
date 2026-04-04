import React from 'react';
import { Canvas, Rect, Group, Text, useFont } from '@shopify/react-native-skia';
import { View, StyleSheet, Dimensions } from 'react-native';

interface MapRendererProps {
  width: number; // In tiles
  height: number; // In tiles
  tileSize: number;
  data: number[]; // Tile IDs
}

export const MapRenderer: React.FC<MapRendererProps> = ({ width, height, tileSize, data }) => {
  const canvasWidth = width * tileSize;
  const canvasHeight = height * tileSize;

  return (
    <View style={styles.container}>
      <Canvas style={{ width: canvasWidth, height: canvasHeight }}>
        {data.map((tileId, index) => {
          const x = (index % width) * tileSize;
          const y = Math.floor(index / width) * tileSize;
          
          // Debug Colors for Tiles
          let color = '#1a1a1a'; // Default wall
          if (tileId === 32) color = '#2e7d32'; // Walkable (Green)
          if (tileId === 2) color = '#c62828';  // Spawn (Red)
          
          return (
            <Group key={index}>
              <Rect
                x={x}
                y={y}
                width={tileSize - 1}
                height={tileSize - 1}
                color={color}
              />
            </Group>
          );
        })}
      </Canvas>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    backgroundColor: '#000',
    borderWidth: 1,
    borderColor: '#333',
    alignSelf: 'center',
  },
});
