import React, { useEffect, useState } from 'react';
import { View, Text, StyleSheet, SafeAreaView, Platform } from 'react-native';
import { MapRenderer } from '../engine/MapRenderer';
import { socketClient } from '../network/SocketClient';
import { PacketRequest } from '../network/BinaryProtocol';
import { Buffer } from 'buffer';

export const MainScreen: React.FC = () => {
  const [mapData, setMapData] = useState<number[]>([]);
  const [status, setStatus] = useState('Connecting...');

  useEffect(() => {
    // Determine server URL (localhost for sim, IP for real device)
    const serverUrl = Platform.OS === 'android' ? 'ws://10.0.2.2:5000/game' : 'ws://localhost:5000/game';
    
    socketClient.connect(serverUrl);

    // Listen for Map Info (CMD 11)
    socketClient.on(11, (request: PacketRequest) => {
      setStatus('Map Received');
      const logicLayer = request.tags.get(61); // Tag 61 is Logic Layer
      if (logicLayer) {
        setMapData(Array.from(new Uint8Array(logicLayer)));
      }
    });

    // Listen for Auth Ack (CMD 1)
    socketClient.on(1, () => {
      setStatus('Authenticated');
      // After login, request Hoa Lu map
      socketClient.requestMap('Hoa Lu');
    });

    return () => {
       // Should cleanup socket if needed
    };
  }, []);

  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.title}>TWELVE / SỨ QUÂN</Text>
        <View style={styles.statusBadge}>
          <Text style={styles.statusText}>STATUS: {status.toUpperCase()}</Text>
        </View>
      </View>

      <View style={styles.renderArea}>
        {mapData.length > 0 ? (
          <MapRenderer 
            width={10} 
            height={8} 
            tileSize={32} 
            data={mapData} 
          />
        ) : (
          <Text style={styles.logText}>Waiting for Map Data...</Text>
        )}
      </View>

      <View style={styles.footer}>
        <Text style={styles.logText}>Server: {status}</Text>
      </View>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#050505',
    padding: 16,
  },
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: 20,
    borderBottomWidth: 1,
    borderColor: '#333',
    paddingBottom: 10,
  },
  title: {
    color: '#fff',
    fontSize: 18,
    fontFamily: Platform.OS === 'ios' ? 'Courier New' : 'monospace',
    fontWeight: 'bold',
    letterSpacing: 2,
  },
  statusBadge: {
    backgroundColor: '#1a1a1a',
    paddingHorizontal: 8,
    paddingVertical: 4,
    borderWidth: 1,
    borderColor: '#2e7d32',
  },
  statusText: {
    color: '#2e7d32',
    fontSize: 10,
    fontWeight: 'bold',
  },
  renderArea: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  footer: {
    marginTop: 20,
    borderTopWidth: 1,
    borderColor: '#333',
    paddingTop: 10,
  },
  logText: {
    color: '#666',
    fontSize: 10,
    fontFamily: Platform.OS === 'ios' ? 'Courier New' : 'monospace',
  },
});
