import React, { useEffect, useState, useRef } from 'react';
import { View, Text, StyleSheet } from 'react-native';
import { SocketClient, Actor, MapInfo } from '../network/SocketClient';
import { MapRenderer } from '../engine/MapRenderer';

export const MainScreen: React.FC = () => {
  const [mapData, setMapData] = useState<MapInfo | null>(null);
  const [actors, setActors] = useState<Actor[]>([]);

  // Use a ref so event handlers always have access to the stable singleton
  const clientRef = useRef<SocketClient>(SocketClient.getInstance());

  useEffect(() => {
    const client = clientRef.current;

    // ── Event listeners ────────────────────────────────────────────────────
    const onMapInfo = (info: MapInfo) => {
      setMapData(info);
    };

    const onActorsUpdate = (newActors: Actor[]) => {
      setActors(prev => {
        const merged = [...prev];
        newActors.forEach(actor => {
          const idx = merged.findIndex(a => a.id === actor.id);
          if (idx >= 0) merged[idx] = actor;
          else merged.push(actor);
        });
        return merged;
      });
    };

    const onMoveAck = (payload: Uint8Array) => {
      // Server echoes back the move — update own actor position
      // Tags 102=x, 103=y are in the payload; actor update will come via actorsUpdate
      console.log('[MainScreen] Move acknowledged by server');
    };

    const onConnected = () => {
      // Request map data immediately after connecting
      client.joinMap();
    };

    client.on('connected', onConnected);
    client.on('mapInfo', onMapInfo);
    client.on('actorsUpdate', onActorsUpdate);
    client.on('moveAck', onMoveAck);

    // If already connected (singleton reused), request map right away
    client.joinMap();

    // ── Cleanup ────────────────────────────────────────────────────────────
    return () => {
      client.off('connected', onConnected);
      client.off('mapInfo', onMapInfo);
      client.off('actorsUpdate', onActorsUpdate);
      client.off('moveAck', onMoveAck);
    };
  }, []);

  const handleMapPress = (x: number, y: number) => {
    clientRef.current.move(x, y);
  };

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Twelve - Lộ Diện Sứ Quân</Text>

      {mapData ? (
        <MapRenderer
          width={mapData.width}
          height={mapData.height}
          tileSize={mapData.tileSize}
          data={mapData.tiles}
          actors={actors}
          onMapPress={handleMapPress}
        />
      ) : (
        <Text style={styles.loading}>Hào khí vạn năm - Đang kết nối...</Text>
      )}

      <View style={styles.stats}>
        <Text style={styles.statText}>
          {mapData ? `Bản đồ: ${mapData.name} (${mapData.width}x${mapData.height})` : 'Chưa kết nối'}
        </Text>
        <Text style={styles.statText}>Nhân vật trên sân: {actors.length}</Text>
        {actors.map(a => (
          <Text key={a.id} style={styles.statText}>
            {'  '}— {a.label} ({a.x}, {a.y})
          </Text>
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
