import React, { useState, useEffect, useMemo } from 'react';
import {
  View,
  Text,
  TextInput,
  TouchableOpacity,
  Image,
  ScrollView,
  Alert,
  BackHandler,
  ActivityIndicator,
  useWindowDimensions,
} from 'react-native';

import { SocketClient } from '../network/SocketClient';
import { getStyles } from './RegisterScreen.styles';
import { SoftkeyBar } from '../components/SoftkeyBar';

// ── Assets ────────────────────────────────────────────────────────────────
const ASSET_ICON_OK     = require('../../assets/ui/icons/icon_ok.png');
const ASSET_ICON_CANCEL = require('../../assets/ui/icons/icon_cancel.png');

const CAPTCHA_CODE = '1234'; 

type Gender = 'Nam' | 'Nữ';

interface Props {
  onBack: () => void;
  onRegisterSuccess: () => void;
}

export const RegisterScreen = ({ onBack, onRegisterSuccess }: Props) => {
  const { width, height } = useWindowDimensions();
  const styles = useMemo(() => getStyles(width, height), [width, height]);

  const [username, setUsername]   = useState('');
  const [password, setPassword]   = useState('');
  const [confirm,  setConfirm]    = useState('');
  const [fullName, setFullName]   = useState('');
  const [dob,      setDob]        = useState(getTodayString());
  const [phone,    setPhone]      = useState('');
  const [gender,   setGender]     = useState<Gender>('Nam');
  const [captcha,  setCaptcha]    = useState('');
  const [focusedField, setFocusedField] = useState<string | null>('username');
  const [loading,  setLoading]    = useState(false);

  const client = SocketClient.getInstance();

  useEffect(() => {
    const onSuccess = (msg: string) => {
      setLoading(false);
      Alert.alert('Thành công', msg || 'Đăng ký thành công!', [
        { text: 'Đăng nhập ngay', onPress: onRegisterSuccess },
      ]);
    };
    const onFailed = (msg: string) => {
      setLoading(false);
      Alert.alert('Thất bại', msg || 'Đăng ký không thành công.');
    };
    client.on('registerSuccess', onSuccess);
    client.on('registerFailed', onFailed);
    return () => {
      client.off('registerSuccess', onSuccess);
      client.off('registerFailed', onFailed);
    };
  }, []);

  useEffect(() => {
    const h = BackHandler.addEventListener('hardwareBackPress', () => {
      onBack();
      return true;
    });
    return () => h.remove();
  }, []);

  const handleRegister = () => {
    if (!username.trim()) return Alert.alert('Chú ý', 'Vui lòng nhập tên đăng nhập');
    if (!password)        return Alert.alert('Chú ý', 'Vui lòng nhập mật khẩu');
    if (password !== confirm) return Alert.alert('Chú ý', 'Mật khẩu nhập lại không khớp');
    if (captcha !== CAPTCHA_CODE) return Alert.alert('Chú ý', 'Mã xác nhận không đúng');
    setLoading(true);
    client.register(username.trim(), password);
  };

  /**
   * Helper to render Clean Input Box (No ornaments, no border when active)
   */
  const renderInput = (
    name: string, 
    val: string, 
    setVal: (v: string) => void, 
    extraProps: any = {}, 
    isCentered = false
  ) => {
    const isFocused = focusedField === name;
    return (
      <View style={[
        styles.inputBox, 
        isFocused ? styles.inputActive : styles.inputInactive
      ]}>
        <TextInput
          style={[
            styles.textInput, 
            isCentered && styles.dobText,
          ]}
          value={val}
          onChangeText={setVal}
          onFocus={() => setFocusedField(name)}
          selectionColor="red"
          {...extraProps}
        />
      </View>
    );
  };

  return (
    <View style={styles.container}>
      
      <View style={styles.header}>
        <Text style={styles.headerText}>Đăng ký</Text>
      </View>

      <ScrollView
        style={styles.scroll}
        contentContainerStyle={styles.scrollContent}
        keyboardShouldPersistTaps="handled"
      >
        <Text style={styles.label}>Tên đăng nhập</Text>
        {renderInput('username', username, setUsername, { autoCapitalize: 'none', autoCorrect: false })}

        <Text style={styles.label}>Mật Khẩu</Text>
        {renderInput('password', password, setPassword, { secureTextEntry: true })}

        <Text style={styles.label}>Nhập lại mật khẩu</Text>
        {renderInput('confirm', confirm, setConfirm, { secureTextEntry: true })}

        <Text style={styles.label}>Họ tên:</Text>
        {renderInput('fullName', fullName, setFullName)}

        <Text style={styles.label}>Ngày sinh</Text>
        {renderInput('dob', dob, setDob, { placeholder: 'DD - MM - YYYY' }, true)}

        <Text style={styles.label}>Số điện thoại:</Text>
        {renderInput('phone', phone, setPhone, { keyboardType: 'phone-pad' })}

        <Text style={styles.label}>Giới tính</Text>
        <View style={styles.genderRow}>
          <TouchableOpacity style={styles.radioItem} onPress={() => setGender('Nam')}>
            <View style={styles.radioBox}>
              {gender === 'Nam' && <Text style={styles.radioCheck}>✓</Text>}
            </View>
            <Text style={styles.radioLabel}>Nam</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.radioItem} onPress={() => setGender('Nữ')}>
            <View style={styles.radioBox}>
              {gender === 'Nữ' && <Text style={styles.radioCheck}>✓</Text>}
            </View>
            <Text style={styles.radioLabel}>Nữ</Text>
          </TouchableOpacity>
        </View>

        <Text style={styles.label}>Nhập lại những ký tự sau:</Text>
        <View style={styles.captchaContainer}>
          <Text style={styles.captchaText}>{CAPTCHA_CODE}</Text>
        </View>
        {renderInput('captcha', captcha, setCaptcha, { keyboardType: 'numeric', maxLength: 4, onSubmitEditing: handleRegister })}
      </ScrollView>

      {loading && (
        <View style={styles.loadingOverlay}>
          <ActivityIndicator color="#0055cc" size="large" />
        </View>
      )}

      {/* ── SoftkeyBar Flexible (Labels for Register, Time always on) ── */}
      <SoftkeyBar
        width={width}
        onLeftPress={handleRegister}
        onRightPress={onBack}
        leftLabel="Đăng ký"
        rightLabel="Hủy"
      />

    </View>
  );
};

function getTodayString(): string {
  const d = new Date();
  const dd = String(d.getDate()).padStart(2, '0');
  const mm = String(d.getMonth() + 1).padStart(2, '0');
  const yyyy = d.getFullYear();
  return `${dd} - ${mm} - ${yyyy}`;
}
