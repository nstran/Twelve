import React, { useState, useEffect, useRef } from 'react';
import { SafeAreaView, StatusBar, StyleSheet, View, Text } from 'react-native';
import {
  BattleScreen,
  createBattleResultResolver,
  createBattleSessionSyncResolver,
  createBattleSkillPacketResolver,
  createEnemyBattleTurnPlanResolver,
  createMapMonsterRosterResolver,
  createMonsterBattleBootstrapResolver,
  HoaLuMapScreen,
  LoginScreen,
  MainScreen,
  MapSelectionScreen,
  RegisterScreen,
  type MonsterBattleBootstrapResponse,
  type BattleResultRewardResponse,
} from './src/screens';
import { CreateCharacterScreen } from './src/screens/character/create';
import { CharacterStatusScreen, type PlayerAppearance } from './src/screens/character/status';
import type { MapInfo } from './src/data/MapData';
import { SocketClient }          from './src/network/SocketClient';
import {
  loadSession,
  saveSession,
  saveLastScreen,
  clearSession,
  setupMobileClearOnClose,
} from './src/storage/SessionStorage';

// ── Screen states ────────────────────────────────────────────────────────────
type Screen = 'login' | 'register' | 'main' | 'createCharacter' | 'characterStatus' | 'mapSelection' | 'hoaLuMap' | 'battle';
type MonsterTypeNav = 'fire' | 'ice' | 'zap';
type BattleInitialTurn = 'player' | 'monster';

const SERVER_URL         = 'ws://localhost:5102/game';
const RECONNECT_DELAY_MS = 2000;

function normalizeScreen(screen?: string | null): Screen {
  if (
    screen === 'hoaLuMap'        ||
    screen === 'battle'          ||
    screen === 'main'            ||
    screen === 'register'        ||
    screen === 'createCharacter' ||
    screen === 'characterStatus' ||
    screen === 'mapSelection'
  ) {
    return screen;
  }

  return 'characterStatus';
}

export default function App() {
  const [screen, setScreen]           = useState<Screen>('login');
  const [battleMonster, setBattleMonster] = useState<MonsterTypeNav>('fire');
  const [battleInitialTurn, setBattleInitialTurn] = useState<BattleInitialTurn>('player');
  const [battleBootstrap, setBattleBootstrap] = useState<MonsterBattleBootstrapResponse | null>(null);
  const [selectedMap, setSelectedMap] = useState<MapInfo | null>(null);
  const [playerAppearance, setPlayerAppearance] = useState<PlayerAppearance>({
    genderIndex: 0, faceIndex: 0, hairIndex: 0, hairColorIndex: 0, skinColorIndex: 0, elementIndex: 0,
  });
  const [isConnected, setIsConnected] = useState(false);
  const [connectMsg, setConnectMsg]   = useState('ĐANG KẾT NỐI CHIẾN TRƯỜNG...');
  const addLog = (msg: string) => console.log(msg);
  const resolveSkillPacket = React.useMemo(
    () => createBattleSkillPacketResolver(SERVER_URL),
    [],
  );
  const resolveBattleSessionSync = React.useMemo(
    () => createBattleSessionSyncResolver(SERVER_URL),
    [],
  );
  const resolveBattleResult = React.useMemo(
    () => createBattleResultResolver(SERVER_URL),
    [],
  );
  const resolveEnemyTurnPlan = React.useMemo(
    () => createEnemyBattleTurnPlanResolver(SERVER_URL),
    [],
  );
  const resolveMonsterBootstrap = React.useMemo(
    () => createMonsterBattleBootstrapResolver(SERVER_URL),
    [],
  );
  const resolveMapMonsterRoster = React.useMemo(
    () => createMapMonsterRosterResolver(SERVER_URL),
    [],
  );

  const client         = SocketClient.getInstance();
  const reconnectTimer  = useRef<ReturnType<typeof setTimeout> | null>(null);
  const attemptRef      = useRef(0);
  // Username lấy từ session đã lưu, dùng để save lại rolling token sau auto-login
  const pendingUsername = useRef<string | null>(null);
  const lastScreen      = useRef<Screen | null>(null);

  // ── Persist screen state ───────────────────────────────────────────────
  useEffect(() => {
    if (screen !== 'login' && screen !== 'register') {
      saveLastScreen(screen);
    }
  }, [screen]);

  const doConnect = () => {
    const attempt = ++attemptRef.current;
    console.log(`[App] Connecting (#${attempt}) → ${SERVER_URL}`);
    setConnectMsg(`ĐANG KẾT NỐI CHIẾN TRƯỜNG... (#${attempt})`);
    client.connect(SERVER_URL);
  };

  useEffect(() => {
    // Setup xoá token khi app tắt (mobile only, web giữ qua F5)
    const unsubAppState = setupMobileClearOnClose();

    const onConnected = async () => {
      addLog('[App] WS connected ✓');
      attemptRef.current = 0;
      setIsConnected(true);
      setConnectMsg('ĐANG KẾT NỐI CHIẾN TRƯỜNG...');

      // ── Thử auto-login bằng token đã lưu ─────────────────────────────────
      const session = await loadSession();
      if (session) {
        addLog(`[App] Session: ${session.username} lastScreen: ${session.lastScreen}`);
        pendingUsername.current = session.username;
        lastScreen.current      = normalizeScreen(session.lastScreen);
        client.tokenLogin(session.token);
      } else {
        addLog('[App] No session → login screen');
      }
    };

    const onDisconnected = () => {
      console.log('[App] Disconnected — reconnecting in', RECONNECT_DELAY_MS, 'ms');
      setIsConnected(false);
      reconnectTimer.current = setTimeout(doConnect, RECONNECT_DELAY_MS);
    };

    const onAuthSuccess = (payload: { token?: string; expiresAt?: number } = {}) => {
      addLog(`[App] CMD4 token=${payload.token?.slice(0,6)}`);
      const username = pendingUsername.current;
      if (username && payload.token && payload.expiresAt) {
        addLog(`[App] Save rolling → ${username}`);
        saveSession({ token: payload.token, expiresAt: payload.expiresAt, username });
        pendingUsername.current = null;
      }
      // Đi đến lastScreen nếu có; không thì chờ CMD 5 (characterRequired) hoặc CMD 7 (characterInfo)
      // CMD 7 sẽ navigate đến characterStatus, CMD 5 sẽ navigate đến createCharacter
      const target = lastScreen.current;
      lastScreen.current = null;
      if (target) setScreen(target);
      // Nếu không có lastScreen → KHÔNG navigate ngay, chờ server quyết định
    };

    const onAuthSuccessWithUser = ({ token, expiresAt, username }: {
      token: string; expiresAt: number; username: string;
    }) => {
      addLog(`[App] authSuccessWithUser → ${username}`);
      saveSession({ token, expiresAt, username });
      const target = lastScreen.current;
      lastScreen.current = null;
      if (target) setScreen(target);
    };

    const onCharacterRequired = () => {
      addLog('[App] CharacterRequired → createCharacter');
      setScreen('createCharacter');
    };

    const onCharacterInfo = (appearance: PlayerAppearance) => {
      addLog(`[App] CharacterInfo → characterStatus (element=${appearance.elementIndex})`);
      setPlayerAppearance({
        ...appearance,
        genderIndex:    appearance.genderIndex,
        faceIndex:      appearance.faceIndex,
        hairIndex:      appearance.hairIndex,
        hairColorIndex: appearance.hairColorIndex,
        skinColorIndex: appearance.skinColorIndex,
        elementIndex:   appearance.elementIndex,
      });
      setScreen('characterStatus');
    };

    const onAuthFailed = (msg?: string) => {
      addLog(`[App] authFailed: ${msg ?? '?'} → clearSession`);
      clearSession();
    };

    client.on('connected',           onConnected);
    client.on('disconnected',        onDisconnected);
    client.on('authSuccess',         onAuthSuccess);
    client.on('authSuccessWithUser', onAuthSuccessWithUser);
    client.on('characterRequired',   onCharacterRequired);
    client.on('characterInfo',       onCharacterInfo);
    client.on('authFailed',          onAuthFailed);

    doConnect();

    return () => {
      client.off('connected',           onConnected);
      client.off('disconnected',        onDisconnected);
      client.off('authSuccess',         onAuthSuccess);
      client.off('authSuccessWithUser', onAuthSuccessWithUser);
      client.off('characterRequired',   onCharacterRequired);
      client.off('characterInfo',       onCharacterInfo);
      client.off('authFailed',          onAuthFailed);
      unsubAppState();
      if (reconnectTimer.current) clearTimeout(reconnectTimer.current);
    };
  }, []);

  const leaveBattle = () => {
    setBattleBootstrap(null);
    setScreen('hoaLuMap');
  };

  const applyBattleResult = (result: BattleResultRewardResponse) => {
    setPlayerAppearance((current) => {
      const expDenominator = Math.max(1, result.expCeiling - result.expFloor);
      const expPct = Math.max(
        0,
        Math.min(100, Math.floor(((result.expAfter - result.expFloor) * 100) / expDenominator)),
      );

      return {
        ...current,
        level: result.levelAfter,
        walletQuan: result.quanAfter,
        quan: `${result.quanAfter} Quan`,
        hp: { cur: result.currentHp, max: result.maxHp },
        exp: { cur: expPct, max: 100 },
      };
    });
  };

  const renderScreen = () => {
    if (!isConnected) {
      return (
        <View style={styles.loadingContainer}>
          <Text style={styles.loadingText}>{connectMsg}</Text>
          <Text style={styles.loadingSubText}>
            Đảm bảo server đang chạy tại{'\n'}{SERVER_URL}
          </Text>
        </View>
      );
    }

    switch (screen) {
      case 'main':
        return <MainScreen onLogout={() => setScreen('login')} />;

      case 'mapSelection':
        return (
          <MapSelectionScreen
            onSelect={(map) => {
              console.log('[App] Selected Map:', map.name, map.id);
              setSelectedMap(map);
              setBattleBootstrap(null);
              setScreen(map.sceneKind === 'sideScroll' ? 'hoaLuMap' : 'main');
            }}
            onBack={async () => {
              await clearSession();
              setScreen('login');
            }}
          />
        );

      case 'hoaLuMap':
        return (
          <HoaLuMapScreen
            mapId={selectedMap?.runtimeMapId ?? 'Hoa Lu'}
            roomId={selectedMap?.defaultRoomId ?? 1}
            roomLabel={selectedMap?.roomLabel ?? 'Khu 1'}
            appearance={playerAppearance}
            onBack={() => setScreen('mapSelection')}
            onLogout={async () => {
              await clearSession();
              setScreen('login');
            }}
            resolveMonsterRoster={resolveMapMonsterRoster}
            resolveMonsterBootstrap={resolveMonsterBootstrap}
            onBattle={(type, initialTurn, monsterBootstrap) => {
              setBattleMonster(type as MonsterTypeNav);
              setBattleInitialTurn(
                monsterBootstrap.initialTurnSide === 'enemy' ? 'monster' : initialTurn,
              );
              setBattleBootstrap(monsterBootstrap);
              setScreen('battle');
            }}
          />
        );

      case 'battle':
        if (!battleBootstrap) {
          return (
            <View style={styles.loadingContainer}>
              <Text style={styles.loadingText}>THIẾU BATTLE BOOTSTRAP</Text>
              <Text style={styles.loadingSubText}>Quay lại Hoa Lư để mở encounter lại.</Text>
            </View>
          );
        }

        return (
          <BattleScreen
            monsterType={battleMonster}
            monsterBootstrap={battleBootstrap}
            appearance={playerAppearance}
            initialTurn={battleInitialTurn}
            resolveSkillPacket={resolveSkillPacket}
            resolveEnemyTurnPlan={resolveEnemyTurnPlan}
            resolveBattleSessionSync={resolveBattleSessionSync}
            resolveBattleResult={resolveBattleResult}
            onBattleResult={applyBattleResult}
            onVictory={leaveBattle}
            onDefeat={leaveBattle}
            onFlee={leaveBattle}
          />
        );

      case 'register':
        return (
          <RegisterScreen
            onBack={() => setScreen('login')}
            onRegisterSuccess={() => setScreen('login')}
          />
        );

      case 'createCharacter':
        return (
          <CreateCharacterScreen
            onSuccess={(appearance) => {
              setPlayerAppearance(appearance);
              setScreen('characterStatus');
            }}
            onCancel={async () => {
              await clearSession();
              setScreen('login');
            }}
          />
        );

      case 'characterStatus':
        return (
          <CharacterStatusScreen
            appearance={playerAppearance}
            onStart={() => setScreen('mapSelection')}
            onLogout={async () => {
              await clearSession();
              setScreen('login');
            }}
          />
        );

      case 'login':
      default:
        return (
          <LoginScreen
            onLoginSuccess={() => {}}
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
  container:        { flex: 1, backgroundColor: '#000' },
  loadingContainer: {
    flex: 1, justifyContent: 'center', alignItems: 'center',
    backgroundColor: '#000', gap: 16,
  },
  loadingText: {
    color: '#FFD700', fontSize: 18, fontWeight: 'bold',
    letterSpacing: 2, textAlign: 'center' as const,
  },
  loadingSubText: {
    color: '#999', fontSize: 13, textAlign: 'center' as const,
  },
});
