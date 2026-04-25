import React, { useState, useEffect, useRef } from 'react';
import { SafeAreaView, StatusBar, StyleSheet, View, Text } from 'react-native';
import { LoadingDialog } from './src/components/dialogs/LoadingDialog/LoadingDialog';
import {
  BattleScreen,
  createBattlePvpActionResolver,
  createBattleResultResolver,
  createBattleSessionSnapshotResolver,
  createBattleSessionSyncResolver,
  createBattleSkillPacketResolver,
  createEnemyBattleTurnPlanResolver,
  createMapMonsterRosterResolver,
  createMonsterBattleBootstrapResolver,
  createPvpChallengeApi,
  createPvpBattleBootstrapResolver,
  createPvpOpponentListResolver,
  HoaLuMapScreen,
  LoginScreen,
  MainScreen,
  MapSelectionScreen,
  RegisterScreen,
  type MonsterBattleBootstrapResponse,
  type BattleResultRewardResponse,
} from './src/screens';
import { CreateCharacterScreen } from './src/screens/character/create';
import {
  CharacterStatusScreen,
  createPlayerRuntimeApi,
  mergePlayerRuntimeAppearance,
  type PlayerAppearance,
} from './src/screens/character/status';
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
const API_BASE_URL       = 'http://localhost:5102';
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
  const [apiLoadingCount, setApiLoadingCount] = useState(0);
  const addLog = (msg: string) => console.log(msg);
  const resolveSkillPacket = React.useMemo(
    () => createBattleSkillPacketResolver(SERVER_URL),
    [],
  );
  const resolveBattleSessionSync = React.useMemo(
    () => createBattleSessionSyncResolver(SERVER_URL),
    [],
  );
  const resolveBattleSessionSnapshot = React.useMemo(
    () => createBattleSessionSnapshotResolver(SERVER_URL),
    [],
  );
  const resolveBattlePvpAction = React.useMemo(
    () => createBattlePvpActionResolver(SERVER_URL),
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
  const resolvePvpOpponents = React.useMemo(
    () => createPvpOpponentListResolver(SERVER_URL),
    [],
  );
  const resolvePvpBootstrap = React.useMemo(
    () => createPvpBattleBootstrapResolver(SERVER_URL),
    [],
  );
  const resolvePvpChallengeApi = React.useMemo(
    () => createPvpChallengeApi(SERVER_URL),
    [],
  );
  const resolveMapMonsterRoster = React.useMemo(
    () => createMapMonsterRosterResolver(SERVER_URL),
    [],
  );
  const playerRuntimeApi = React.useMemo(
    () => createPlayerRuntimeApi(SERVER_URL),
    [],
  );

  const client         = SocketClient.getInstance();
  const reconnectTimer  = useRef<ReturnType<typeof setTimeout> | null>(null);
  const pendingBattleResultRef = useRef<BattleResultRewardResponse | null>(null);
  const delayedBattleResultTimerRef = useRef<ReturnType<typeof setTimeout> | null>(null);
  const attemptRef      = useRef(0);
  const playerAppearanceRef = useRef<PlayerAppearance>(playerAppearance);
  // Username lấy từ session đã lưu, dùng để save lại rolling token sau auto-login
  const pendingUsername = useRef<string | null>(null);
  const lastScreen      = useRef<Screen | null>(null);
  const [defeatBlinkToken, setDefeatBlinkToken] = useState(0);

  useEffect(() => {
    playerAppearanceRef.current = playerAppearance;
  }, [playerAppearance]);

  useEffect(() => {
    const originalFetch = globalThis.fetch.bind(globalThis);

    const resolveRequestUrl = (input: RequestInfo | URL): string => {
      if (typeof input === 'string') {
        return input;
      }

      if (input instanceof URL) {
        return input.toString();
      }

      return input.url;
    };

    const shouldTrackRequest = (input: RequestInfo | URL, init?: RequestInit): boolean => {
      const url = resolveRequestUrl(input);
      if (!url.startsWith(API_BASE_URL)) {
        return false;
      }

      const headers = new Headers(init?.headers ?? (typeof input === 'string' || input instanceof URL ? undefined : input.headers));
      if (headers.get('X-Twelve-Silent-Loading') === 'true') {
        return false;
      }

      const path = (() => {
        try {
          return new URL(url).pathname;
        } catch {
          return url;
        }
      })();

      return ![
        '/pvp/challenges/inbox',
        '/battle/session-snapshot',
        '/battle/session-sync',
        '/pvp/opponents',
      ].some((silentPath) => path.endsWith(silentPath));
    };

    globalThis.fetch = (async (...args: Parameters<typeof fetch>): Promise<Response> => {
      const track = shouldTrackRequest(args[0], args[1]);
      if (track) {
        setApiLoadingCount((current) => current + 1);
      }

      try {
        return await originalFetch(...args);
      } finally {
        if (track) {
          setApiLoadingCount((current) => Math.max(0, current - 1));
        }
      }
    }) as typeof fetch;

    return () => {
      globalThis.fetch = originalFetch;
    };
  }, []);

  const applyRuntimeResponse = React.useCallback((response: { snapshot: any } | null) => {
    if (!response?.snapshot) {
      return;
    }

    setPlayerAppearance((current) => mergePlayerRuntimeAppearance(current, response.snapshot));
  }, []);

  const refreshPlayerRuntime = React.useCallback(async (username?: string) => {
    if (!username) {
      return null;
    }

    const response = await playerRuntimeApi.load(username);
    applyRuntimeResponse(response);
    return response;
  }, [applyRuntimeResponse, playerRuntimeApi]);

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
      const nextAppearance = {
        ...appearance,
        genderIndex:    appearance.genderIndex,
        faceIndex:      appearance.faceIndex,
        hairIndex:      appearance.hairIndex,
        hairColorIndex: appearance.hairColorIndex,
        skinColorIndex: appearance.skinColorIndex,
        elementIndex:   appearance.elementIndex,
      };
      setPlayerAppearance(nextAppearance);
      void refreshPlayerRuntime(nextAppearance.username);
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
      if (delayedBattleResultTimerRef.current) clearTimeout(delayedBattleResultTimerRef.current);
    };
  }, []);

  const leaveBattle = () => {
    const pendingResult = pendingBattleResultRef.current;
    pendingBattleResultRef.current = null;
    if (delayedBattleResultTimerRef.current) {
      clearTimeout(delayedBattleResultTimerRef.current);
      delayedBattleResultTimerRef.current = null;
    }
    setBattleBootstrap(null);
    setScreen('hoaLuMap');
    if (pendingResult?.result === 'defeat') {
      setDefeatBlinkToken((current) => current + 1);
    }
    if (pendingResult) {
      delayedBattleResultTimerRef.current = setTimeout(() => {
        delayedBattleResultTimerRef.current = null;
        applyBattleResult(pendingResult);
      }, 500);
    }
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
        expRange: { value: result.expAfter, floor: result.expFloor, ceiling: result.expCeiling },
      };
    });
    void refreshPlayerRuntime(playerAppearanceRef.current.username);
  };

  const queueBattleResult = (result: BattleResultRewardResponse) => {
    pendingBattleResultRef.current = result;
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
            apiBaseUrl={API_BASE_URL}
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
            roomLabel={selectedMap?.roomLabel ?? selectedMap?.displayName ?? 'Hoa Lư'}
            appearance={playerAppearance}
            onBack={() => setScreen('mapSelection')}
            onLogout={async () => {
              await clearSession();
              setScreen('login');
            }}
            resolveMonsterRoster={resolveMapMonsterRoster}
            resolveMonsterBootstrap={resolveMonsterBootstrap}
            resolvePvpOpponents={resolvePvpOpponents}
            resolvePvpBootstrap={resolvePvpBootstrap}
              resolvePvpChallengeApi={resolvePvpChallengeApi}
            defeatBlinkToken={defeatBlinkToken}
            onAllocateStat={async (stat) => {
              const username = playerAppearance.username;
              if (!username) return null;
              const response = await playerRuntimeApi.allocateStat(username, stat);
              applyRuntimeResponse(response);
              return response?.message ?? null;
            }}
            onAllocateSkill={async (familyCode) => {
              const username = playerAppearance.username;
              if (!username) return null;
              const response = await playerRuntimeApi.allocateSkill(username, familyCode);
              applyRuntimeResponse(response);
              return response?.message ?? null;
            }}
            onToggleEquipment={async (equipKey, equip) => {
              const username = playerAppearance.username;
              if (!username) return null;
              const response = await playerRuntimeApi.toggleEquipment(username, equipKey, equip);
              applyRuntimeResponse(response);
              return response?.message ?? null;
            }}
            onPreviewEquipmentLoadout={async (equipKeys) => {
              const username = playerAppearance.username;
              if (!username) return null;
              const response = await playerRuntimeApi.previewEquipmentLoadout(username, equipKeys);
              return response?.snapshot
                ? mergePlayerRuntimeAppearance(playerAppearanceRef.current, response.snapshot)
                : null;
            }}
            onCommitEquipmentLoadout={async (equipKeys) => {
              const username = playerAppearance.username;
              if (!username) return null;
              const response = await playerRuntimeApi.commitEquipmentLoadout(username, equipKeys);
              applyRuntimeResponse(response);
              return response?.message ?? null;
            }}
            onUseItem={async (itemId) => {
              const username = playerAppearance.username;
              if (!username) return null;
              const response = await playerRuntimeApi.useItem(username, itemId);
              applyRuntimeResponse(response);
              return response?.message ?? null;
            }}
            onDiscardEquipment={async (equipKey) => {
              const username = playerAppearance.username;
              if (!username) return null;
              const response = await playerRuntimeApi.discardEquipment(username, [equipKey]);
              applyRuntimeResponse(response);
              return response?.message ?? null;
            }}
            onDiscardItem={async (itemId, quantity) => {
              const username = playerAppearance.username;
              if (!username) return null;
              const response = await playerRuntimeApi.discardItem(username, itemId, quantity);
              applyRuntimeResponse(response);
              return response?.message ?? null;
            }}
            onRepairEquipment={async (equipKey) => {
              const username = playerAppearance.username;
              if (!username) return null;
              const response = await playerRuntimeApi.repairEquipment(username, equipKey, 5010);
              applyRuntimeResponse(response);
              return response?.message ?? null;
            }}
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
            resolveBattleSessionSnapshot={resolveBattleSessionSnapshot}
            resolveBattlePvpAction={resolveBattlePvpAction}
            resolveBattleResult={resolveBattleResult}
            onBattleResult={queueBattleResult}
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
            onAllocateStat={async (stat) => {
              const username = playerAppearance.username;
              if (!username) return null;
              const response = await playerRuntimeApi.allocateStat(username, stat);
              applyRuntimeResponse(response);
              return response?.message ?? null;
            }}
            onAllocateSkill={async (familyCode) => {
              const username = playerAppearance.username;
              if (!username) return null;
              const response = await playerRuntimeApi.allocateSkill(username, familyCode);
              applyRuntimeResponse(response);
              return response?.message ?? null;
            }}
            onToggleEquipment={async (equipKey, equip) => {
              const username = playerAppearance.username;
              if (!username) return null;
              const response = await playerRuntimeApi.toggleEquipment(username, equipKey, equip);
              applyRuntimeResponse(response);
              return response?.message ?? null;
            }}
            onPreviewEquipmentLoadout={async (equipKeys) => {
              const username = playerAppearance.username;
              if (!username) return null;
              const response = await playerRuntimeApi.previewEquipmentLoadout(username, equipKeys);
              return response?.snapshot
                ? mergePlayerRuntimeAppearance(playerAppearanceRef.current, response.snapshot)
                : null;
            }}
            onCommitEquipmentLoadout={async (equipKeys) => {
              const username = playerAppearance.username;
              if (!username) return null;
              const response = await playerRuntimeApi.commitEquipmentLoadout(username, equipKeys);
              applyRuntimeResponse(response);
              return response?.message ?? null;
            }}
            onUseItem={async (itemId) => {
              const username = playerAppearance.username;
              if (!username) return null;
              const response = await playerRuntimeApi.useItem(username, itemId);
              applyRuntimeResponse(response);
              return response?.message ?? null;
            }}
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
      <LoadingDialog visible={apiLoadingCount > 0} message="Vui lòng chờ..." />
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
