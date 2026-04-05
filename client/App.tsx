import React, { useState, useEffect } from 'react';
import { SafeAreaView, StatusBar, StyleSheet, View, Text } from 'react-native';
import { LoginScreen } from './src/screens/LoginScreen';
import { MainScreen } from './src/screens/MainScreen';
import { SocketClient } from './src/network/SocketClient';

export default function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [isConnected, setIsConnected] = useState(false);
  const client = SocketClient.getInstance();

  useEffect(() => {
    // Connect to server (Replace with your actual IP if testing on physical device)
    client.connect('ws://localhost:5102/game');

    client.on('connected', () => setIsConnected(true));
    client.on('disconnected', () => setIsConnected(false));

    return () => {
      client.removeAllListeners('connected');
      client.removeAllListeners('disconnected');
    };
  }, []);

  return (
    <SafeAreaView style={styles.container}>
      <StatusBar barStyle="light-content" backgroundColor="#000" />
      {!isConnected ? (
        <View style={styles.loadingContainer}>
          <Text style={styles.loadingText}>DANG KET NOI CHIEN TRUONG...</Text>
        </View>
      ) : isLoggedIn ? (
        <MainScreen />
      ) : (
        <LoginScreen onLoginSuccess={() => setIsLoggedIn(true)} />
      )}
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#000',
  },
  loadingContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#000',
  },
  loadingText: {
    color: '#FFD700', // GOLD
    fontSize: 18,
    fontWeight: 'bold',
    letterSpacing: 2,
  },
});
