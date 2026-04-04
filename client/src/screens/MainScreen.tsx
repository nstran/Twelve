import React, { useEffect, useState } from 'react';
import { View, Text, StyleSheet } from 'react-native';
import { SocketClient, Actor } from '../network/SocketClient';
import { MapRenderer } from '../engine/MapRenderer';

export const MainScreen: React.FC = () => {
  const [mapData, setMapData] = useState<any>(null);
  const [actors, setActors] = useState<Actor[]>([]);
  const [client, setClient] = useState<SocketClient | null>(null);

  useEffect(() => {
    const wsClient = new SocketClient(
      (data) => setMapData(data),
      (newActors) => setActors(prev => {
        const merged = [...prev];
        newActors.forEach(actor => {
          const idx = merged.findIndex(a => a.id === actor.id);
          if (idx >= 0) merged[idx] = actor;
          else merged.push(actor);
        });
        return merged;
      })
    );
    
    wsClient.connect('ws://localhost:2026');
    setClient(wsClient);
  }, []);

  const handleMapPress = (x: number, y: number) => {
    if (client) {
      client.move(x, y);
    }
  };

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Twelve - Lộ Diện Sứ Quân</Text>
      {mapData ? (
        <MapRenderer 
          width={mapData.width || 10} 
          height={mapData.height || 10} 
          tileSize={mapData.tileSize || 32} 
          data={new Array(100).fill(32)} // Dummy grass tiles
          actors={actors}
          onMapPress={handleMapPress}
        />
      ) : (
        <Text style={styles.loading}>Hào khí vạn năm - Đang kết nối...</Text>
      )}
      
      <View style={styles.stats}>
        <Text style={styles.statText}>Nhân vật: {actors.length}</Text>
        {actors.map(a => (
          <Text key={a.id} style={styles.statText}> - {a.label} ({a.x}, {a.y})</Text>
        ))}
        <Text style={[styles.statText, { marginTop: 10, color: '#ffd700' }]}>
          * Chạm lên bản đồ để di binh!
        </Text>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#0a0a0a',
    alignItems: 'center',
    justifyContent: 'center',
  },
  title: {
    color: '#ffd700',
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 20,
    textTransform: 'uppercase',
  },
  loading: {
    color: '#888',
    fontStyle: 'italic',
  },
  stats: {
    marginTop: 20,
    padding: 15,
    backgroundColor: '#1a1a1a',
    borderRadius: 8,
    width: '90%',
    borderWidth: 1,
    borderColor: '#333',
  },
  statText: {
    color: '#ccc',
    fontSize: 14,
  },
});
