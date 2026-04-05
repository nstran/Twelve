import React, { useState, useEffect } from 'react';
import { SafeAreaView, StatusBar, StyleSheet, View, Text } from 'react-native';
import { LoginScreen }    from './src/screens/LoginScreen';
import { RegisterScreen } from './src/screens/RegisterScreen';
import { MainScreen }     from './src/screens/MainScreen';
import { SocketClient }   from './src/network/SocketClient';

// ── Screen states (mirrors J2ME screen stack) ────────────────────────────────
type Screen = 'login' | 'register' | 'main';

export default function App() {
  const [screen, setScreen]       = useState<Screen>('login');
  const [isConnected, setIsConnected] = useState(false);

  const client = SocketClient.getInstance();

  useEffect(() => {
    // Replace 'localhost' with your machine's LAN IP when testing on a real device
    client.connect('ws://localhost:5102/game');

    const onConnected    = () => setIsConnected(true);
    const onDisconnected = () => setIsConnected(false);
    client.on('connected',    onConnected);
    client.on('disconnected', onDisconnected);

    return () => {
      client.off('connected',    onConnected);
      client.off('disconnected', onDisconnected);
    };
  }, []);

  const renderScreen = () => {
    if (!isConnected) {
      return (
        <View style={styles.loadingContainer}>
          <Text style={styles.loadingText}>ĐANG KẾT NỐI CHIẾN TRƯỜNG...</Text>
        </View>
      );
    }

    switch (screen) {
      case 'main':
        return <MainScreen />;

      case 'register':
        return (
          <RegisterScreen
            onBack={() => setScreen('login')}
            onRegisterSuccess={() => setScreen('login')}
          />
        );

      case 'login':
      default:
        return (
          <LoginScreen
            onLoginSuccess={() => setScreen('main')}
            onRegister={() => setScreen('register')}
          />
        );
    }
  };

  return (
    <SafeAreaView style={styles.container}>
      <StatusBar barStyle="light-content" backgroundColor="#000" />
      {renderScreen()}
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
    color: '#FFD700',
    fontSize: 18,
    fontWeight: 'bold',
    letterSpacing: 2,
  },
});
