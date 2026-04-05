import React, { useState, useEffect } from 'react';
import { 
  View, 
  Text, 
  TextInput, 
  TouchableOpacity, 
  ImageBackground, 
  StyleSheet, 
  Alert,
  ActivityIndicator
} from 'react-native';
import { SocketClient } from '../network/SocketClient';

const GOLD = '#FFD700';
const DARK_BG = 'rgba(0, 0, 0, 0.7)';

export const LoginScreen = ({ onLoginSuccess }: { onLoginSuccess: () => void }) => {
  const [isRegister, setIsRegister] = useState(false);
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);

  const client = SocketClient.getInstance();

  useEffect(() => {
    client.on('authSuccess', () => {
      setLoading(false);
      onLoginSuccess();
    });

    client.on('authFailed', (msg) => {
      setLoading(false);
      Alert.alert('That bai', msg || 'Sai tai khoan hoac mat khau');
    });

    client.on('registerSuccess', (msg) => {
      setLoading(false);
      Alert.alert('Thanh cong', msg);
      setIsRegister(false);
    });

    client.on('registerFailed', (msg) => {
      setLoading(false);
      Alert.alert('That bai', msg);
    });

    return () => {
      client.removeAllListeners('authSuccess');
      client.removeAllListeners('authFailed');
      client.removeAllListeners('registerSuccess');
      client.removeAllListeners('registerFailed');
    };
  }, []);

  const handleAction = () => {
    if (!username || !password) {
      Alert.alert('Chu y', 'Vui long nhap day du thong tin');
      return;
    }
    setLoading(true);
    if (isRegister) {
      client.register(username, password);
    } else {
      client.login(username, password);
    }
  };

  return (
    <ImageBackground 
      source={require('../../assets/original/bklogin.png')} 
      style={styles.container}
      resizeMode="cover"
    >
      <View style={styles.overlay}>
        <Text style={styles.title}>{isRegister ? 'DANG KY' : 'DANG NHAP'}</Text>
        
        <View style={styles.inputGroup}>
          <Text style={styles.label}>Ten tai khoan</Text>
          <TextInput 
            style={styles.input} 
            value={username}
            onChangeText={setUsername}
            placeholderTextColor="#666"
            autoCapitalize="none"
          />
        </View>

        <View style={styles.inputGroup}>
          <Text style={styles.label}>Mat khau</Text>
          <TextInput 
            style={styles.input} 
            value={password}
            onChangeText={setPassword}
            secureTextEntry
            placeholderTextColor="#666"
          />
        </View>

        {loading ? (
          <ActivityIndicator color={GOLD} style={{ marginVertical: 20 }} />
        ) : (
          <TouchableOpacity style={styles.button} onPress={handleAction}>
            <Text style={styles.buttonText}>{isRegister ? 'GHI DANH' : 'THAM CHIEN'}</Text>
          </TouchableOpacity>
        )}

        <TouchableOpacity onPress={() => setIsRegister(!isRegister)}>
          <Text style={styles.switchText}>
            {isRegister ? 'Da co tai khoan? Dang nhap' : 'Chua co tai khoan? Dang ky'}
          </Text>
        </TouchableOpacity>
      </View>
    </ImageBackground>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  overlay: {
    backgroundColor: DARK_BG,
    padding: 20,
    borderRadius: 10,
    width: '85%',
    borderWidth: 1,
    borderColor: GOLD,
    alignItems: 'center',
  },
  title: {
    color: GOLD,
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 20,
    letterSpacing: 2,
  },
  inputGroup: {
    width: '100%',
    marginBottom: 15,
  },
  label: {
    color: GOLD,
    fontSize: 14,
    marginBottom: 5,
  },
  input: {
    backgroundColor: '#111',
    borderWidth: 1,
    borderColor: GOLD,
    color: '#FFF',
    padding: 10,
    borderRadius: 5,
    fontSize: 16,
  },
  button: {
    backgroundColor: GOLD,
    paddingVertical: 12,
    paddingHorizontal: 40,
    borderRadius: 5,
    marginVertical: 15,
  },
  buttonText: {
    color: '#000',
    fontWeight: 'bold',
    fontSize: 18,
  },
  switchText: {
    color: '#AAA',
    marginTop: 10,
    textDecorationLine: 'underline',
  }
});
