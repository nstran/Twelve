/**
 * RegisterScreen — Tái hiện giao diện LCDUI Form của J2ME
 *
 * Layout:
 *  ┌─────────────────────┐
 *  │   Đăng ký  (teal)   │  ← title bar
 *  ├─────────────────────┤
 *  │ Tên đăng nhập       │  ← label (cyan highlight khi focus)
 *  │ [____________]      │  ← input (beige / white khi focus)
 *  │ Mật Khẩu            │
 *  │ [____________]      │
 *  │  ...                │
 *  ├─────────────────────┤
 *  │ Đăng ký  │   Hủy   │  ← bottom softkey bar (teal)
 *  └─────────────────────┘
 */

import React, { useState, useEffect } from 'react';
import {
  View,
  Text,
  TextInput,
  TouchableOpacity,
  ScrollView,
  Alert,
  BackHandler,
  StyleSheet,
  Platform,
  ActivityIndicator,
} from 'react-native';
import { SocketClient } from '../network/SocketClient';

// ── Màu sắc J2ME LCDUI Form ───────────────────────────────────────────────
const C = {
  bg:           '#5a5a5a',   // nền xám của Form
  titleBar:     '#00b4b4',   // thanh tiêu đề teal/cyan
  titleText:    '#000000',
  labelBg:      '#5a5a5a',   // label bình thường
  labelBgFocus: '#00b4b4',   // label đang focus (highlight teal)
  labelText:    '#ffffff',
  labelTextFocus: '#000000',
  inputBg:      '#c8b898',   // input beige (J2ME mặc định)
  inputBgFocus: '#ffffff',   // input trắng khi focus
  inputText:    '#000000',
  inputBorder:  '#666666',
  inputBorderFocus: '#0055cc',
  bottomBar:    '#00b4b4',   // thanh softkey teal
  softkeyText:  '#000000',
  softkeyDivider: '#007a7a',
  captchaBg:    '#ffffff',
  captchaText:  '#000000',
  radioCheck:   '#000000',
};

const CAPTCHA_CODE = '1234'; // Captcha tĩnh (demo)

type Gender = 'Nam' | 'Nữ';

interface Props {
  onBack: () => void;
  onRegisterSuccess: () => void;
}

export const RegisterScreen = ({ onBack, onRegisterSuccess }: Props) => {
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

  // ── Auth events ─────────────────────────────────────────────────────────
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

  // ── Android back ─────────────────────────────────────────────────────────
  useEffect(() => {
    const h = BackHandler.addEventListener('hardwareBackPress', () => {
      onBack();
      return true;
    });
    return () => h.remove();
  }, []);

  // ── Validate & Submit ────────────────────────────────────────────────────
  const handleRegister = () => {
    if (!username.trim()) return Alert.alert('Chú ý', 'Vui lòng nhập tên đăng nhập');
    if (!password)        return Alert.alert('Chú ý', 'Vui lòng nhập mật khẩu');
    if (password !== confirm) return Alert.alert('Chú ý', 'Mật khẩu nhập lại không khớp');
    if (captcha !== CAPTCHA_CODE) return Alert.alert('Chú ý', 'Mã xác nhận không đúng');
    setLoading(true);
    client.register(username.trim(), password);
  };

  // ── Helpers ──────────────────────────────────────────────────────────────
  const isFocus = (name: string) => focusedField === name;
  const labelStyle = (name: string) => [
    s.label,
    isFocus(name) && s.labelFocus,
  ];
  const labelTextStyle = (name: string) => [
    s.labelText,
    isFocus(name) && s.labelTextFocus,
  ];
  const inputStyle = (name: string) => [
    s.input,
    isFocus(name) && s.inputFocus,
  ];

  return (
    <View style={s.root}>

      {/* ── Title bar ───────────────────────────────────────────── */}
      <View style={s.titleBar}>
        <Text style={s.titleText}>Đăng ký</Text>
      </View>

      {/* ── Scrollable form body ────────────────────────────────── */}
      <ScrollView
        style={s.scroll}
        contentContainerStyle={s.scrollContent}
        keyboardShouldPersistTaps="handled"
      >

        {/* Tên đăng nhập */}
        <View style={labelStyle('username')}>
          <Text style={labelTextStyle('username')}>Tên đăng nhập</Text>
        </View>
        <TextInput
          style={inputStyle('username')}
          value={username}
          onChangeText={setUsername}
          onFocus={() => setFocusedField('username')}
          autoCapitalize="none"
          autoCorrect={false}
          returnKeyType="next"
        />

        {/* Mật Khẩu */}
        <View style={labelStyle('password')}>
          <Text style={labelTextStyle('password')}>Mật Khẩu</Text>
        </View>
        <TextInput
          style={inputStyle('password')}
          value={password}
          onChangeText={setPassword}
          onFocus={() => setFocusedField('password')}
          secureTextEntry
          returnKeyType="next"
        />

        {/* Nhập lại mật khẩu */}
        <View style={labelStyle('confirm')}>
          <Text style={labelTextStyle('confirm')}>Nhập lại mật khẩu</Text>
        </View>
        <TextInput
          style={inputStyle('confirm')}
          value={confirm}
          onChangeText={setConfirm}
          onFocus={() => setFocusedField('confirm')}
          secureTextEntry
          returnKeyType="next"
        />

        {/* Họ tên */}
        <View style={labelStyle('fullName')}>
          <Text style={labelTextStyle('fullName')}>Họ tên:</Text>
        </View>
        <TextInput
          style={inputStyle('fullName')}
          value={fullName}
          onChangeText={setFullName}
          onFocus={() => setFocusedField('fullName')}
          returnKeyType="next"
        />

        {/* Ngày sinh */}
        <View style={labelStyle('dob')}>
          <Text style={labelTextStyle('dob')}>Ngày sinh</Text>
        </View>
        <View style={[s.input, s.dobRow, isFocus('dob') && s.inputFocus]}>
          <TextInput
            style={s.dobInput}
            value={dob}
            onChangeText={setDob}
            onFocus={() => setFocusedField('dob')}
            placeholder="DD - MM - YYYY"
            placeholderTextColor="#888"
            keyboardType="numbers-and-punctuation"
            textAlign="center"
          />
        </View>

        {/* Số điện thoại */}
        <View style={labelStyle('phone')}>
          <Text style={labelTextStyle('phone')}>Số điện thoại:</Text>
        </View>
        <TextInput
          style={inputStyle('phone')}
          value={phone}
          onChangeText={setPhone}
          onFocus={() => setFocusedField('phone')}
          keyboardType="phone-pad"
          returnKeyType="next"
        />

        {/* Giới tính */}
        <View style={s.labelPlain}>
          <Text style={s.labelText}>Giới tính</Text>
        </View>
        <View style={s.genderRow}>
          <TouchableOpacity style={s.radioItem} onPress={() => setGender('Nam')}>
            <View style={s.radioBox}>
              {gender === 'Nam' && <Text style={s.radioCheck}>✓</Text>}
            </View>
            <Text style={s.radioLabel}>Nam</Text>
          </TouchableOpacity>
          <TouchableOpacity style={s.radioItem} onPress={() => setGender('Nữ')}>
            <View style={s.radioBox}>
              {gender === 'Nữ' && <Text style={s.radioCheck}>✓</Text>}
            </View>
            <Text style={s.radioLabel}>Nữ</Text>
          </TouchableOpacity>
        </View>

        {/* CAPTCHA */}
        <View style={s.labelPlain}>
          <Text style={s.labelText}>Nhập lại những ký tự sau:</Text>
        </View>
        <View style={s.captchaDisplay}>
          <Text style={s.captchaCode}>{CAPTCHA_CODE}</Text>
        </View>
        <TextInput
          style={inputStyle('captcha')}
          value={captcha}
          onChangeText={setCaptcha}
          onFocus={() => setFocusedField('captcha')}
          keyboardType="numeric"
          maxLength={4}
          returnKeyType="done"
          onSubmitEditing={handleRegister}
        />

        {/* Padding cuối */}
        <View style={{ height: 16 }} />
      </ScrollView>

      {/* ── Bottom softkey bar (teal, full-width split) ──────────── */}
      <View style={s.bottomBar}>
        <TouchableOpacity
          style={s.softkey}
          onPress={handleRegister}
          activeOpacity={0.7}
          disabled={loading}
        >
          {loading
            ? <ActivityIndicator color="#000" size="small" />
            : <Text style={s.softkeyText}>Đăng ký</Text>
          }
        </TouchableOpacity>

        <View style={s.softkeyDivider} />

        <TouchableOpacity
          style={s.softkey}
          onPress={onBack}
          activeOpacity={0.7}
        >
          <Text style={s.softkeyText}>Hủy</Text>
        </TouchableOpacity>
      </View>

    </View>
  );
};

// ── Helpers ─────────────────────────────────────────────────────────────────
function getTodayString(): string {
  const d = new Date();
  const dd = String(d.getDate()).padStart(2, '0');
  const mm = String(d.getMonth() + 1).padStart(2, '0');
  const yyyy = d.getFullYear();
  return `${dd} - ${mm} - ${yyyy}`;
}

// ── Styles ───────────────────────────────────────────────────────────────────
const SOFTKEY_H = 24;

const s = StyleSheet.create({
  root: {
    flex: 1,
    backgroundColor: C.bg,
  },

  // Title bar
  titleBar: {
    backgroundColor: C.titleBar,
    paddingVertical: 5,
    paddingHorizontal: 10,
    alignItems: 'center',
  },
  titleText: {
    color: C.titleText,
    fontSize: 15,
    fontWeight: 'bold',
    fontFamily: Platform.OS === 'ios' ? 'System' : 'sans-serif-medium',
  },

  // Scroll
  scroll: { flex: 1 },
  scrollContent: { paddingBottom: 8 },

  // Label (bình thường)
  label: {
    backgroundColor: C.labelBg,
    paddingHorizontal: 10,
    paddingTop: 6,
    paddingBottom: 2,
  },
  // Label khi field đang focus — teal highlight
  labelFocus: {
    backgroundColor: C.labelBgFocus,
  },
  labelPlain: {
    backgroundColor: C.labelBg,
    paddingHorizontal: 10,
    paddingTop: 6,
    paddingBottom: 2,
  },
  labelText: {
    color: C.labelText,
    fontSize: 13,
    fontFamily: Platform.OS === 'ios' ? 'System' : 'sans-serif',
  },
  labelTextFocus: {
    color: C.labelTextFocus,
  },

  // Input (beige)
  input: {
    backgroundColor: C.inputBg,
    borderWidth: 1,
    borderColor: C.inputBorder,
    marginHorizontal: 10,
    marginBottom: 2,
    paddingHorizontal: 6,
    paddingVertical: Platform.OS === 'ios' ? 5 : 2,
    color: C.inputText,
    fontSize: 13,
    fontFamily: Platform.OS === 'ios' ? 'System' : 'sans-serif',
    minHeight: 26,
  },
  // Input khi focus — trắng, border xanh
  inputFocus: {
    backgroundColor: C.inputBgFocus,
    borderColor: C.inputBorderFocus,
    borderWidth: 2,
  },

  // Ngày sinh — center
  dobRow: {
    justifyContent: 'center',
    alignItems: 'center',
    paddingVertical: 0,
    paddingHorizontal: 0,
  },
  dobInput: {
    width: '100%',
    color: C.inputText,
    fontSize: 13,
    textAlign: 'center',
    paddingVertical: Platform.OS === 'ios' ? 5 : 2,
    fontFamily: Platform.OS === 'ios' ? 'System' : 'sans-serif',
  },

  // Giới tính
  genderRow: {
    flexDirection: 'row',
    marginHorizontal: 10,
    marginBottom: 2,
    backgroundColor: C.bg,
    paddingVertical: 4,
    paddingHorizontal: 4,
    gap: 16,
  },
  radioItem: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 6,
  },
  radioBox: {
    width: 16,
    height: 16,
    borderWidth: 1,
    borderColor: '#cccccc',
    backgroundColor: '#c8b898',
    justifyContent: 'center',
    alignItems: 'center',
  },
  radioCheck: {
    color: C.radioCheck,
    fontSize: 11,
    lineHeight: 14,
    fontWeight: 'bold',
  },
  radioLabel: {
    color: '#ffffff',
    fontSize: 13,
  },

  // CAPTCHA display box
  captchaDisplay: {
    marginHorizontal: 10,
    marginBottom: 4,
    backgroundColor: C.captchaBg,
    borderWidth: 1,
    borderColor: '#999',
    alignItems: 'center',
    paddingVertical: 6,
  },
  captchaCode: {
    color: C.captchaText,
    fontSize: 28,
    fontWeight: 'bold',
    letterSpacing: 8,
    fontFamily: Platform.OS === 'ios' ? 'Courier New' : 'monospace',
  },

  // Bottom softkey bar — teal, split left/right
  bottomBar: {
    height: SOFTKEY_H,
    flexDirection: 'row',
    backgroundColor: C.bottomBar,
  },
  softkey: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  softkeyText: {
    color: C.softkeyText,
    fontSize: 12,
    fontWeight: 'bold',
    fontFamily: Platform.OS === 'ios' ? 'System' : 'sans-serif-medium',
  },
  softkeyDivider: {
    width: 1,
    backgroundColor: C.softkeyDivider,
  },
});
