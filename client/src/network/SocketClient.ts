import EventEmitter from 'eventemitter3';
import { Command, Tag } from './Protocol';

// Actor type exported here so both MapRenderer and MainScreen import from one place
export interface Actor {
  id: string;
  label: string;
  kind: number;
  x: number;
  y: number;
}

export interface MapInfo {
  name: string;
  roomId: number;
  width: number;
  height: number;
  tileSize: number;
  tiles: number[];
  playerWorldState?: {
    x: number;
    y: number;
    direction: number;
    actionState: number;
  };
}

export interface PlayerMapState {
  mapId: string;
  roomId: number;
  x: number;
  y: number;
  direction: number;
  actionState: number;
}

export interface MapMonsterSpawnRecord {
  monsterKey: string;
  spawnGroupKey: string;
  spawnInstanceIndex: number;
  displayName: string;
  visualTypeByte: number;
  displayLevel: number;
  iqValue: number;
  spawnCount: number;
  nameColorMode: number;
}

export interface MapMonsterRosterPacket {
  mapId: string;
  roomId: number;
  mode: number;
  monsters: MapMonsterSpawnRecord[];
}

export interface MonsterBattleBootstrapSocketResponse<T = unknown> {
  ok: boolean;
  data: T | null;
  error?: string | null;
}

type MonsterBattleBootstrapSocketResponseWire<T = unknown> =
  | MonsterBattleBootstrapSocketResponse<T>
  | {
    Ok?: boolean;
    Data?: T | null;
    Error?: string | null;
  };

export class SocketClient extends EventEmitter {
  private socket: WebSocket | null = null;
  private static instance: SocketClient;

  private constructor() {
    super();
  }

  public static getInstance(): SocketClient {
    if (!SocketClient.instance) {
      SocketClient.instance = new SocketClient();
    }
    return SocketClient.instance;
  }

  connect(url: string) {
    // Đóng socket cũ nếu còn mở, tránh leak
    if (this.socket) {
      console.log('[SocketClient] Closing old socket before reconnecting...');
      // Gỡ handler cũ để tránh emit 'disconnected' khi chủ động đóng
      this.socket.onclose = null;
      this.socket.onerror = null;
      this.socket.onmessage = null;
      this.socket.onopen = null;
      if (this.socket.readyState === WebSocket.OPEN || this.socket.readyState === WebSocket.CONNECTING) {
        this.socket.close();
      }
      this.socket = null;
    }

    console.log('[SocketClient] Opening WebSocket →', url);
    this.socket = new WebSocket(url);
    this.socket.binaryType = 'arraybuffer';

    this.socket.onopen = () => {
      console.log('[SocketClient] Connected ✓');
      this.emit('connected');
    };

    this.socket.onmessage = (event) => {
      const data = new Uint8Array(event.data);
      this.handlePacket(data);
    };

    this.socket.onclose = (ev) => {
      console.log('[SocketClient] Disconnected (code=%d reason=%s)', ev.code, ev.reason || '—');
      this.socket = null;
      this.emit('disconnected');
    };

    this.socket.onerror = (err) => {
      console.error('[SocketClient] WebSocket error', err);
      this.emit('error', err);
    };
  }

  private handlePacket(data: Uint8Array) {
    // Minimum packet = 7-byte header: SubCount(2) + PayloadLength(4) + Command(1)
    if (data.length < 7) {
      console.warn('[SocketClient] ← Received too-short packet:', data.length, 'bytes');
      return;
    }

    const payloadLength = (data[2] << 24) | (data[3] << 16) | (data[4] << 8) | data[5];
    const cmd = data[6] as Command;
    const payload = data.slice(7, 7 + payloadLength);

    console.log(`[SocketClient] ← Received CMD ${cmd} (${Command[cmd]}), payloadLength=${payloadLength}`);

    switch (cmd) {
      case Command.LOGIN_SUCCESS: {
        // Payload gồm Token(tag 2) + ExpiresAt(tag 3)
        const token     = this.parseStringTag(payload, Tag.TOKEN);
        const expiresAt = this.parseLongTag(payload,   Tag.EXPIRES_AT);
        console.log('[SocketClient] ← LOGIN_SUCCESS token=', token ? token.slice(0,8)+'…' : 'none',
                    'expiresAt=', expiresAt);
        this.emit('authSuccess', { token, expiresAt });
        break;
      }

      case Command.CHARACTER_REQUIRED:
        this.emit('characterRequired');
        break;

      case Command.CHARACTER_INFO: {
        // CMD 7: Server gửi diện mạo nhân vật sau login thành công
        const genderIndex    = this.parseIntTag(payload, Tag.GENDER_STYLE);
        const elementIndex   = this.parseIntTag(payload, Tag.ELEMENT);
        const faceIndex      = this.parseIntTag(payload, Tag.FACE);
        const hairIndex      = this.parseIntTag(payload, Tag.HAIR_STYLE);
        const hairColorIndex = this.parseIntTag(payload, Tag.HAIR_COLOR);
        const skinColorIndex = this.parseIntTag(payload, Tag.SKIN_COLOR);
        const username       = this.parseStringTag(payload, Tag.USERNAME);
        const level          = this.parseIntTag(payload, Tag.LEVEL);
        const hpCur          = this.parseIntTag(payload, Tag.CURRENT_HP);
        const hpMax          = this.parseIntTag(payload, Tag.MAX_HP);
        const powerCur       = this.parseIntTag(payload, Tag.CURRENT_POWER);
        const powerMax       = this.parseIntTag(payload, Tag.MAX_POWER);
        const expValue       = this.parseLongTag(payload, Tag.EXP_VALUE);
        const expFloor       = this.parseLongTag(payload, Tag.EXP_FLOOR);
        const expCeiling     = this.parseLongTag(payload, Tag.EXP_CEILING);
        const quanProgress   = this.parseLongTag(payload, Tag.QUAN_PROGRESS);
        const quanCap        = this.parseLongTag(payload, Tag.QUAN_CAP);
        const cuongLuc       = this.parseIntTag(payload, Tag.CUONG_LUC);
        const thanPhap       = this.parseIntTag(payload, Tag.THAN_PHAP);
        const noiLuc         = this.parseIntTag(payload, Tag.NOI_LUC);
        const theLuc         = this.parseIntTag(payload, Tag.THE_LUC);
        const points         = this.parseIntTag(payload, Tag.FREE_POINTS);
        const skillPoints    = this.parseIntTag(payload, Tag.SKILL_POINTS);
        const attack         = this.parseIntTag(payload, Tag.ATTACK);
        const accuracy       = this.parseIntTag(payload, Tag.ACCURACY);
        const defense        = this.parseIntTag(payload, Tag.DEFENSE);
        const dodge          = this.parseIntTag(payload, Tag.DODGE);
        const crit           = this.parseIntTag(payload, Tag.CRIT);
        const danhVong       = this.parseIntTag(payload, Tag.HONOR);
        const titlePrimary   = this.parseStringTag(payload, Tag.TITLE_PRIMARY);
        const titleSecondary = this.parseStringTag(payload, Tag.TITLE_SECONDARY);
        const walletQuan     = this.parseLongTag(payload, Tag.WALLET_QUAN);
        const expDenominator = Math.max(1, expCeiling - expFloor);
        const expPct = Math.max(0, Math.min(100, Math.floor(((expValue - expFloor) * 100) / expDenominator)));

        console.log('[SocketClient] ← CHARACTER_INFO g=%d e=%d level=%d hp=%d/%d atk=%d',
          genderIndex, elementIndex, level, hpCur, hpMax, attack);
        this.emit('characterInfo', {
          genderIndex,
          elementIndex,
          faceIndex,
          hairIndex,
          hairColorIndex,
          skinColorIndex,
          username,
          level,
          quanHam: titlePrimary || titleSecondary || undefined,
          xepHang: titleSecondary || undefined,
          danhVong,
          walletQuan,
          quan: `${quanProgress}/${quanCap || 10000} Quan`,
          quanProgress: { cur: quanProgress, max: quanCap || 10000 },
          hp: { cur: hpCur, max: hpMax },
          exp: { cur: expPct, max: 100 },
          expRange: { value: expValue, floor: expFloor, ceiling: expCeiling },
          power: { cur: powerCur, max: powerMax },
          stats: { cuongLuc, noiLuc, thanPhap, theLuc },
          points,
          freePoints: points,
          skillPoints,
          combat: {
            attack,
            def: defense,
            acc: accuracy,
            dodge,
            hp: hpMax,
            crit: `${crit}%`,
          },
        });
        break;
      }

      case Command.LOGIN_FAILED:
        const errorMsg = this.parseStringTag(payload, Tag.MESSAGE);
        console.log('[SocketClient] ← Login Failed:', JSON.stringify(errorMsg));
        this.emit('authFailed',     errorMsg);
        this.emit('registerFailed', errorMsg); 
        break;

      case Command.REGISTER_RESPONSE:
        const regMsg = this.parseStringTag(payload, Tag.MESSAGE);
        console.log('[SocketClient] ← Register response:', JSON.stringify(regMsg));
        if (regMsg.includes('thanh cong')) {
          this.emit('registerSuccess', regMsg);
        } else {
          this.emit('registerFailed', regMsg);
        }
        break;

      case Command.CREATE_CHAR_RESPONSE:
        const charMsg = this.parseStringTag(payload, Tag.MESSAGE);
        console.log('[SocketClient] ← Create Character response:', JSON.stringify(charMsg));
        if (charMsg.includes('thanh cong')) {
          this.emit('createCharSuccess', charMsg);
        } else {
          this.emit('createCharFailed', charMsg);
        }
        break;

      case Command.PLAYER_INFO:
        const mapInfo = this.parseMapInfo(payload);
        this.emit('mapInfo', mapInfo);
        if (mapInfo.playerWorldState) {
          this.emit('playerMapState', {
            mapId: mapInfo.name,
            roomId: mapInfo.roomId,
            ...mapInfo.playerWorldState,
          } satisfies PlayerMapState);
        }
        break;

      case Command.MAP_MONSTER_ROSTER: {
        const roster = this.parseMapMonsterRoster(payload);
        this.emit('mapMonsterRoster', roster);
        break;
      }

      case Command.MONSTER_BOOTSTRAP_RESPONSE: {
        const rawJson = new TextDecoder().decode(payload);
        if (!rawJson.trim()) {
          this.emit('monsterBootstrapResponse', { ok: false, data: null, error: 'empty_payload' });
          break;
        }

        try {
          const parsed = JSON.parse(rawJson) as MonsterBattleBootstrapSocketResponseWire;
          const record = parsed as Record<string, unknown>;
          const response: MonsterBattleBootstrapSocketResponse = {
            ok: parsed && typeof parsed === 'object' && 'ok' in record
              ? Boolean(record.ok)
              : Boolean(record.Ok),
            data: parsed && typeof parsed === 'object' && 'data' in record
              ? (record.data as unknown) ?? null
              : (record.Data as unknown) ?? null,
            error: parsed && typeof parsed === 'object' && 'error' in record
              ? (record.error as string | null | undefined) ?? null
              : (record.Error as string | null | undefined) ?? null,
          };
          this.emit('monsterBootstrapResponse', response);
        } catch {
          this.emit('monsterBootstrapResponse', { ok: false, data: null, error: 'invalid_json' });
        }
        break;
      }

      case Command.MAP_LOAD:
        // Future map load logic
        break;

      case 44: // Move Ack (To be refactored)
        this.emit('moveAck', payload);
        this.emit('playerMapState', this.parsePlayerMapState(payload));
        break;

      default:
        console.log(`[SocketClient] Unhandled CMD ${cmd}`);
    }
}

  // ─── Auth ─────────────────────────────────────────────────────────────────

  login(username: string, password: string) {
    const packet = this.buildAuthPacket(Command.LOGIN_REQUEST, username, password);
    this.socket?.send(packet);
  }

  // ─── CMD 3: Auto-login bằng session token ────────────────────────────────
  tokenLogin(token: string) {
    console.log('[SocketClient] tokenLogin() token=', token.slice(0, 8) + '…');
    const tokenTag = this.makeStringTag(Tag.TOKEN, token);
    const payload  = new Uint8Array(tokenTag);
    const packet   = this.wrapPacket(Command.TOKEN_LOGIN_REQUEST, payload, 1);
    this.socket?.send(packet);
  }

  // ─── CMD 1: Đăng ký tài khoản ───────────────────────────────────────────
  // Tags: 9=username, 10=password, 11=fullName, 12=dob, 13=phone, 14=gender(byte)
  register(
    username: string,
    password: string,
    fullName: string,
    dob: string,
    phone: string,
    gender: 0 | 1,
  ) {
    const tags: number[] = [
      ...this.makeStringTag(Tag.USERNAME,      username),
      ...this.makeStringTag(Tag.PASSWORD,      password),
      ...this.makeStringTag(Tag.FULL_NAME,     fullName),
      ...this.makeStringTag(Tag.DATE_OF_BIRTH, dob),
      ...this.makeStringTag(Tag.PHONE,         phone),
      ...this.makeByteTag(Tag.GENDER,          gender),
    ];
    const payload = new Uint8Array(tags);
    const packet = this.wrapPacket(Command.REGISTER_REQUEST, payload, 6);
    this.socket?.send(packet);
  }

  createCharacter(gender: number, element: number, face: number, hair: number, color: number, skin: number) {
    const tags: number[] = [
      ...this.makeIntTag(Tag.GENDER_STYLE, gender),
      ...this.makeIntTag(Tag.ELEMENT,      element),
      ...this.makeIntTag(Tag.FACE,         face),
      ...this.makeIntTag(Tag.HAIR_STYLE,   hair),
      ...this.makeIntTag(Tag.HAIR_COLOR,   color),
      ...this.makeIntTag(Tag.SKIN_COLOR,   skin),
    ];
    const payload = new Uint8Array(tags);
    const packet = this.wrapPacket(Command.CREATE_CHAR_REQUEST, payload, 6);
    this.socket?.send(packet);
  }

  private wrapPacket(cmd: Command, payload: Uint8Array, subCount: number): Uint8Array {
    const packet = new Uint8Array(7 + payload.length);
    packet[0] = (subCount >> 8) & 0xFF;
    packet[1] = subCount & 0xFF;
    packet[2] = (payload.length >> 24) & 0xFF;
    packet[3] = (payload.length >> 16) & 0xFF;
    packet[4] = (payload.length >> 8)  & 0xFF;
    packet[5] =  payload.length        & 0xFF;
    packet[6] = cmd;
    packet.set(payload, 7);
    return packet;
  }

  joinMap(mapId?: string, roomId?: number) {
    const tags: number[] = [];
    if (mapId) {
      tags.push(...this.makeStringTag(20, mapId));
    }
    if (typeof roomId === 'number') {
      tags.push(...this.makeIntTag(30, roomId));
    }

    const payload = new Uint8Array(tags);
    const packet = this.wrapPacket(Command.PLAYER_INFO, payload, (mapId ? 1 : 0) + (typeof roomId === 'number' ? 1 : 0));
    this.socket?.send(packet);
  }

  requestMonsterBootstrap(
    mapId: string,
    roomId: number,
    monsterKey: string,
    initialTurnSide: 'player' | 'enemy',
  ) {
    const payload = new Uint8Array([
      ...this.makeStringTag(20, mapId),
      ...this.makeIntTag(30, roomId),
      ...this.makeStringTag(9, monsterKey),
      ...this.makeByteTag(40, initialTurnSide === 'enemy' ? 1 : 0),
    ]);
    const packet = this.wrapPacket(Command.MONSTER_BOOTSTRAP_REQUEST, payload, 4);
    this.socket?.send(packet);
  }

  move(x: number, y: number, mapId?: string, roomId?: number, facing?: 'left' | 'right', actionState = 0) {
    // Build payload: xTag + yTag
    const tags: number[] = [
      ...this.makeIntTag(102, x),
      ...this.makeIntTag(103, y),
    ];
    let tagCount = 2;

    if (mapId) {
      tags.push(...this.makeStringTag(20, mapId));
      tagCount += 1;
    }
    if (typeof roomId === 'number') {
      tags.push(...this.makeIntTag(30, roomId));
      tagCount += 1;
    }
    if (facing) {
      tags.push(...this.makeIntTag(104, facing === 'right' ? 1 : 0));
      tagCount += 1;
    }
    tags.push(...this.makeIntTag(105, actionState));
    tagCount += 1;

    const payload = new Uint8Array(tags);

    // 7-byte header: SubCount(2) + PayloadLength(4) + Command(1)
    const packet = new Uint8Array(7 + payload.length);
    packet[0] = (tagCount >> 8) & 0xFF;
    packet[1] = tagCount & 0xFF;
    packet[2] = (payload.length >> 24) & 0xFF;
    packet[3] = (payload.length >> 16) & 0xFF;
    packet[4] = (payload.length >> 8) & 0xFF;
    packet[5] = payload.length & 0xFF;
    packet[6] = 44; // CMD Move
    packet.set(payload, 7);
    this.socket?.send(packet);
  }

  // ─── Packet Builders ──────────────────────────────────────────────────────

  private buildAuthPacket(cmd: number, user: string, pass: string): Uint8Array {
    const userTag = this.makeStringTag(9, user);
    const passTag = this.makeStringTag(10, pass);
    const payload = new Uint8Array([...userTag, ...passTag]);

    // 7-byte header: SubCount(2) + PayloadLength(4) + Command(1)
    const packet = new Uint8Array(7 + payload.length);
    packet[0] = 0; packet[1] = 2; // SubCount = 2 tags
    packet[2] = (payload.length >> 24) & 0xFF;
    packet[3] = (payload.length >> 16) & 0xFF;
    packet[4] = (payload.length >> 8) & 0xFF;
    packet[5] = payload.length & 0xFF;
    packet[6] = cmd;
    packet.set(payload, 7);
    return packet;
  }

  // ─── Tag Builders ─────────────────────────────────────────────────────────

  private makeStringTag(id: number, value: string): number[] {
    const bytes = Array.from(new TextEncoder().encode(value));
    return [
      id,
      (bytes.length >> 24) & 0xFF,
      (bytes.length >> 16) & 0xFF,
      (bytes.length >> 8) & 0xFF,
      bytes.length & 0xFF,
      ...bytes,
    ];
  }

  private makeIntTag(id: number, value: number): number[] {
    return [
      id,
      0, 0, 0, 4, // length = 4 bytes
      (value >> 24) & 0xFF,
      (value >> 16) & 0xFF,
      (value >> 8) & 0xFF,
      value & 0xFF,
    ];
  }

  private makeByteTag(id: number, value: number): number[] {
    return [id, 0, 0, 0, 1, value & 0xFF]; // length = 1 byte
  }

  // ─── Tag Parsers ──────────────────────────────────────────────────────────

  private parseStringTag(data: Uint8Array, targetId: number): string {
    let pos = 0;
    while (pos <= data.length - 5) {
      const id = data[pos];
      const len = (data[pos + 1] << 24) | (data[pos + 2] << 16) | (data[pos + 3] << 8) | data[pos + 4];
      if (id === targetId) {
        return new TextDecoder().decode(data.slice(pos + 5, pos + 5 + len));
      }
      pos += 5 + len;
    }
    return '';
  }

  private readInt(data: Uint8Array, offset: number): number {
    return ((data[offset] << 24) | (data[offset + 1] << 16) | (data[offset + 2] << 8) | data[offset + 3]) >>> 0;
  }

  private parseIntTag(data: Uint8Array, targetId: number): number {
    let pos = 0;
    while (pos <= data.length - 5) {
      const id  = data[pos];
      const len = (data[pos + 1] << 24) | (data[pos + 2] << 16) | (data[pos + 3] << 8) | data[pos + 4];
      if (id === targetId && len === 4) {
        return this.readInt(data, pos + 5);
      }
      pos += 5 + len;
    }
    return 0;
  }

  // Đọc long 8-byte big-endian từ payload theo tagId (trả về Unix seconds)
  private parseLongTag(data: Uint8Array, targetId: number): number {
    let pos = 0;
    while (pos <= data.length - 5) {
      const id  = data[pos];
      const len = (data[pos+1] << 24) | (data[pos+2] << 16) | (data[pos+3] << 8) | data[pos+4];
      if (id === targetId && len === 8) {
        // JS không có int64 — dùng DataView để đọc high/low 32-bit và ghép
        const view = new DataView(data.buffer, data.byteOffset + pos + 5, 8);
        const high = view.getUint32(0, false); // big-endian
        const low  = view.getUint32(4, false);
        return high * 0x100000000 + low;       // safe cho timestamp Unix (< 2^53)
      }
      pos += 5 + len;
    }
    return 0;
  }

  /**
   * Parse MapInfo from CMD 11 payload.
   * Tags: 20=name, 56=width, 57=height, 58=tileW, 59=tileH, 55=ground layer
   */
  private parseMapInfo(payload: Uint8Array): MapInfo {
    let pos = 0;
    let name = 'Unknown';
    let roomId = 0;
    let width = 10;
    let height = 8;
    let tileSize = 32;
    let tiles: number[] = [];
    let playerState: PlayerMapState | null = null;

    while (pos <= payload.length - 5) {
      const id = payload[pos];
      const len = this.readInt(payload, pos + 1);
      const val = payload.slice(pos + 5, pos + 5 + len);

      switch (id) {
        case 20: name = new TextDecoder().decode(val); break;
        case 30: roomId = this.readInt(val, 0); break;
        case 56: width = this.readInt(val, 0); break;
        case 57: height = this.readInt(val, 0); break;
        case 58: tileSize = this.readInt(val, 0); break;
        case 55: tiles = Array.from(val); break; // Ground layer
        case 102:
        case 103:
        case 104:
        case 105:
          playerState = this.parsePlayerMapState(payload);
          pos = payload.length;
          continue;
      }
      pos += 5 + len;
    }

    return {
      name,
      roomId,
      width,
      height,
      tileSize,
      tiles,
      playerWorldState: playerState
        ? {
            x: playerState.x,
            y: playerState.y,
            direction: playerState.direction,
            actionState: playerState.actionState,
          }
        : undefined,
    };
  }

  private parsePlayerMapState(payload: Uint8Array): PlayerMapState {
    let pos = 0;
    let mapId = '';
    let roomId = 0;
    let x = 0;
    let y = 0;
    let direction = 0;
    let actionState = 0;

    while (pos <= payload.length - 5) {
      const id = payload[pos];
      const len = this.readInt(payload, pos + 1);
      const val = payload.slice(pos + 5, pos + 5 + len);

      switch (id) {
        case 20: mapId = new TextDecoder().decode(val); break;
        case 30: roomId = this.readInt(val, 0); break;
        case 102: x = this.readInt(val, 0); break;
        case 103: y = this.readInt(val, 0); break;
        case 104: direction = this.readInt(val, 0); break;
        case 105: actionState = this.readInt(val, 0); break;
      }

      pos += 5 + len;
    }

    return { mapId, roomId, x, y, direction, actionState };
  }

  private parseMapMonsterRoster(payload: Uint8Array): MapMonsterRosterPacket {
    let pos = 0;
    let mapId = '';
    let roomId = 0;
    let mode = 0;
    const monsters: MapMonsterSpawnRecord[] = [];

    while (pos <= payload.length - 5) {
      const id = payload[pos];
      const len = this.readInt(payload, pos + 1);
      const val = payload.slice(pos + 5, pos + 5 + len);

      switch (id) {
        case 20:
          mapId = new TextDecoder().decode(val);
          break;
        case 30:
          roomId = this.readInt(val, 0);
          break;
        case 40:
          mode = val[0] ?? 0;
          break;
        case 9:
          monsters.push(this.parseMapMonsterSpawnRecord(val));
          break;
      }

      pos += 5 + len;
    }

    return { mapId, roomId, mode, monsters };
  }

  private parseMapMonsterSpawnRecord(payload: Uint8Array): MapMonsterSpawnRecord {
    let pos = 0;
    let monsterKey = '';
    let spawnGroupKey = '';
    let spawnInstanceIndex = 0;
    let displayName = '';
    let displayLevel = 0;
    let visualTypeByte = 0;
    let iqValue = 0;
    let spawnCount = 0;
    let nameColorMode = 0;

    while (pos <= payload.length - 5) {
      const id = payload[pos];
      const len = this.readInt(payload, pos + 1);
      const val = payload.slice(pos + 5, pos + 5 + len);

      switch (id) {
        case 9:
          monsterKey = new TextDecoder().decode(val);
          break;
        case 26:
          displayName = new TextDecoder().decode(val);
          break;
        case 108:
          spawnGroupKey = new TextDecoder().decode(val);
          break;
        case 109:
          spawnInstanceIndex = this.readInt(val, 0);
          break;
        case 27:
          displayLevel = this.readInt(val, 0);
          break;
        case 15:
          visualTypeByte = val[0] ?? 0;
          break;
        case 129:
          iqValue = this.readInt(val, 0);
          break;
        case 106:
          spawnCount = this.readInt(val, 0);
          break;
        case 107:
          nameColorMode = val[0] ?? 0;
          break;
      }

      pos += 5 + len;
    }

    return {
      monsterKey,
      spawnGroupKey,
      spawnInstanceIndex,
      displayName,
      visualTypeByte,
      displayLevel,
      iqValue,
      spawnCount,
      nameColorMode,
    };
  }

}
