import React, { useState, useEffect, useMemo } from 'react';
import {
  View,
  Text,
  TextInput,
  TouchableOpacity,
  ScrollView,
  BackHandler,
  ActivityIndicator,
  useWindowDimensions,
} from 'react-native';

import { SocketClient } from '../../network/SocketClient';
import { getStyles } from './RegisterScreen.styles';
import { SoftkeyBar } from '../../components/SoftkeyBar';
import { CalendarPicker } from '../../components/CalendarPicker';

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
  const [focusedField, setFocusedField] = useState<string | null>('username');
  const [loading,  setLoading]    = useState(false);
  const [showCalendar, setShowCalendar] = useState(false);

  // ── Thay Alert bằng banner hiển thị trực tiếp ────────────────────────────
  const [errorMsg,   setErrorMsg]   = useState('');
  const [successMsg, setSuccessMsg] = useState('');

  const showError   = (msg: string) => { setErrorMsg(msg); setSuccessMsg(''); };
  const showSuccess = (msg: string) => { setSuccessMsg(msg); setErrorMsg(''); };
  const clearMsg    = () => { setErrorMsg(''); setSuccessMsg(''); };

  const client = SocketClient.getInstance();

  useEffect(() => {
    console.log('[Register] Mounting: subscribing to registerSuccess/registerFailed events');

    const onSuccess = (msg: string) => {
      console.log('[Register] ← SERVER: registerSuccess', msg);
      setLoading(false);
      showSuccess('Đăng ký thành công! Đang chuyển sang đăng nhập...');
      // Tự động chuyển sang màn đăng nhập sau 1.5 giây
      setTimeout(() => onRegisterSuccess(), 1500);
    };

    const onFailed = (msg: string) => {
      console.log('[Register] ← SERVER: registerFailed', msg);
      setLoading(false);
      showError(msg || 'Đăng ký không thành công. Vui lòng thử lại.');
    };

    client.on('registerSuccess', onSuccess);
    client.on('registerFailed',  onFailed);

    return () => {
      console.log('[Register] Unmounting: unsubscribing events');
      client.off('registerSuccess', onSuccess);
      client.off('registerFailed',  onFailed);
    };
  }, []);

  useEffect(() => {
    const h = BackHandler.addEventListener('hardwareBackPress', () => {
      if (showCalendar) { setShowCalendar(false); return true; }
      onBack();
      return true;
    });
    return () => h.remove();
  }, [showCalendar]);

  const handleRegister = () => {
    console.log('[Register] ── handleRegister fired ──');
    clearMsg();

    const trimUser  = username.trim();
    const trimName  = fullName.trim();
    const trimPhone = phone.trim();

    console.log('[Register] username:', JSON.stringify(trimUser));
    console.log('[Register] password length:', password.length);
    console.log('[Register] confirm match:', password === confirm);

    // ── Validate — hiển thị lỗi trực tiếp trên màn hình ─────────────────
    if (!trimUser) {
      console.log('[Register] FAIL: username empty');
      showError('Vui lòng nhập tên đăng nhập');
      return;
    }
    if (trimUser.length < 4) {
      console.log('[Register] FAIL: username too short', trimUser.length);
      showError('Tên đăng nhập phải từ 4 ký tự trở lên');
      return;
    }
    if (!password) {
      console.log('[Register] FAIL: password empty');
      showError('Vui lòng nhập mật khẩu');
      return;
    }
    if (password.length < 6) {
      console.log('[Register] FAIL: password too short', password.length);
      showError('Mật khẩu phải có ít nhất 6 ký tự');
      return;
    }
    if (password !== confirm) {
      console.log('[Register] FAIL: passwords do not match');
      showError('Mật khẩu nhập lại không khớp');
      return;
    }

    console.log('[Register] Validation OK → setLoading(true) → client.register()');
    setLoading(true);

    client.register(
      trimUser,
      password,
      trimName,
      dob,
      trimPhone,
      gender === 'Nam' ? 0 : 1,
    );
  };

  // ── Render input ──────────────────────────────────────────────────────────
  const renderInput = (
    name: string,
    val: string,
    setVal: (v: string) => void,
    extraProps: any = {},
    isCentered = false
  ) => {
    const isFocused = focusedField === name;

    if (name === 'dob') {
      return (
        <TouchableOpacity
          activeOpacity={1}
          style={[styles.inputBox, isFocused ? styles.inputActive : styles.inputInactive]}
          onPress={() => { setFocusedField('dob'); setShowCalendar(true); }}
        >
          <Text style={[styles.textInput, styles.dobText, { lineHeight: 30 }]}>{val}</Text>
        </TouchableOpacity>
      );
    }

    return (
      <View style={[styles.inputBox, isFocused ? styles.inputActive : styles.inputInactive]}>
        <TextInput
          style={[styles.textInput, isCentered && styles.dobText]}
          value={val}
          onChangeText={(v) => { setVal(v); clearMsg(); }}
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
        {/* ── Banner lỗi / thành công ─────────────────────────────────────── */}
        {!!errorMsg && (
          <View style={styles.bannerError}>
            <Text style={styles.bannerText}>⚠ {errorMsg}</Text>
          </View>
        )}
        {!!successMsg && (
          <View style={styles.bannerSuccess}>
            <Text style={styles.bannerText}>✓ {successMsg}</Text>
          </View>
        )}

        <Text style={styles.label}>Tên đăng nhập</Text>
        {renderInput('username', username, setUsername, { autoCapitalize: 'none', autoCorrect: false })}

        <Text style={styles.label}>Mật Khẩu</Text>
        {renderInput('password', password, setPassword, { secureTextEntry: true })}

        <Text style={styles.label}>Nhập lại mật khẩu</Text>
        {renderInput('confirm', confirm, setConfirm, { secureTextEntry: true })}

        <Text style={styles.label}>Họ tên:</Text>
        {renderInput('fullName', fullName, setFullName)}

        <Text style={styles.label}>Ngày sinh</Text>
        {renderInput('dob', dob, setDob)}

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

      </ScrollView>

      {loading && (
        <View style={styles.loadingOverlay}>
          <ActivityIndicator color="#0055cc" size="large" />
        </View>
      )}

      <CalendarPicker
        visible={showCalendar}
        initialDate={dob}
        onSelect={(newDate) => { setDob(newDate); setShowCalendar(false); }}
        onClose={() => setShowCalendar(false)}
      />

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
  const dd   = String(d.getDate()).padStart(2, '0');
  const mm   = String(d.getMonth() + 1).padStart(2, '0');
  const yyyy = d.getFullYear();
  return `${dd} - ${mm} - ${yyyy}`;
}
