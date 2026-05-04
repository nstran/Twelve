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
  assetCatalogId?: string;
  framePaths?: string[];
  /** From server TLV when roster is DB-driven (per map/room). */
  spawnTemplateKey?: string;
  surfaceId?: string;
  patrolStartRatio?: number;
  patrolEndRatio?: number;
  spawnRatio?: number;
  moveSpeed?: number;
}

export interface MapMonsterRosterPacket {
  mapId: string;
  roomId: number;
  mode: number;
  monsters: MapMonsterSpawnRecord[];
}

export interface MapNpcRosterRecord {
  npcId: string;
  displayName: string;
  visualTypeByte: number;
  displayLevel: number;
  tileX: number;
  tileY: number;
  nameColorMode: number;
}

export interface MapNpcRosterPacket {
  mapId: string;
  mode: number;
  npcs: MapNpcRosterRecord[];
}

export interface NpcTalkSocketResponse {
  ok: boolean;
  npcId?: string;
  message?: string;
  error?: string;
}

export interface MissionTaskRecord {
  rawValue: number;
  questId: string;
  text: string;
}

export interface MissionRecord {
  questId: string;
  title: string;
  description: string;
  price: number;
  statusFlag: boolean;
  tasks: MissionTaskRecord[];
  rewardLines: string[];
}

export interface MissionListPacket {
  missions: MissionRecord[];
}

export interface MissionDetailPacket {
  mission: MissionRecord;
  openAsUpdate: boolean;
}

export interface MissionTaskNotificationPacket {
  task: MissionTaskRecord;
  message: string;
}

export interface MissionNotificationPacket {
  mission: MissionRecord;
  message: string;
}

export interface MissionUpdatePacket {
  mission: MissionRecord;
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

    this.socket = new WebSocket(url);
    this.socket.binaryType = 'arraybuffer';

    this.socket.onopen = () => {
      this.emit('connected');
    };

    this.socket.onmessage = (event) => {
      const data = new Uint8Array(event.data);
      this.handlePacket(data);
    };

    this.socket.onclose = () => {
      this.socket = null;
      this.emit('disconnected');
    };

    this.socket.onerror = (err) => {
      this.emit('error', err);
    };
  }

  private handlePacket(data: Uint8Array) {
    // Minimum packet = 7-byte header: SubCount(2) + PayloadLength(4) + Command(1)
    if (data.length < 7) {
      return;
    }

    const payloadLength = (data[2] << 24) | (data[3] << 16) | (data[4] << 8) | data[5];
    const cmd = data[6] as Command;
    const payload = data.slice(7, 7 + payloadLength);

    switch (cmd) {
      case Command.LOGIN_SUCCESS: {
        // Payload gồm Token(tag 2) + ExpiresAt(tag 3)
        const token     = this.parseStringTag(payload, Tag.TOKEN);
        const expiresAt = this.parseLongTag(payload,   Tag.EXPIRES_AT);
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
        const rawGold        = this.parseLongTag(payload, Tag.WALLET_QUAN);
        const expDenominator = Math.max(1, expCeiling - expFloor);
        const expPct = Math.max(0, Math.min(100, Math.floor(((expValue - expFloor) * 100) / expDenominator)));
        // Java/remake wallet lock: Tag.WALLET_QUAN currently carries raw KEN/gold.
        // Only a complete 10_000-gold chunk is displayed as Quan; the remainder
        // remains on the gold progress bar.
        // Source: gameplay memory + BATTLE_SYSTEM_RECONSTRUCTION.md §EXP/Gold/Quan.
        const quanProgressCap = Math.max(1, quanCap || 10000);
        const walletQuan = Math.floor(Math.max(0, rawGold) / quanProgressCap);
        const goldProgress = Math.max(0, rawGold) % quanProgressCap;

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
          quan: `${walletQuan} Quan`,
          quanProgress: { cur: goldProgress, max: quanProgressCap },
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
        this.emit('authFailed',     errorMsg);
        this.emit('registerFailed', errorMsg); 
        break;

      case Command.REGISTER_RESPONSE:
        const regMsg = this.parseStringTag(payload, Tag.MESSAGE);
        if (regMsg.includes('thanh cong')) {
          this.emit('registerSuccess', regMsg);
        } else {
          this.emit('registerFailed', regMsg);
        }
        break;

      case Command.CREATE_CHAR_RESPONSE:
        const charMsg = this.parseStringTag(payload, Tag.MESSAGE);
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

      case Command.MAP_NPC_ROSTER_REMAKE: {
        const roster = this.parseMapNpcRoster(payload);
        this.emit('mapNpcRoster', roster);
        break;
      }
      case Command.NPC_TALK_RESPONSE_REMAKE: {
        const response = this.parseJsonPayload<NpcTalkSocketResponse>(payload, { ok: false, error: 'invalid_payload' });
        this.emit('npcTalkResponse', response);
        break;
      }

      case Command.MISSION_LIST: {
        this.emit('missionList', this.parseMissionList(payload));
        break;
      }
      case Command.MISSION_DETAIL: {
        this.emit('missionDetail', this.parseMissionDetail(payload));
        break;
      }
      case Command.MISSION_TASK_NOTIFY: {
        this.emit('missionTaskNotification', this.parseMissionTaskNotification(payload));
        break;
      }
      case Command.MISSION_NOTIFY: {
        this.emit('missionNotification', this.parseMissionNotification(payload));
        break;
      }
      case Command.MISSION_UPDATE: {
        this.emit('missionUpdate', this.parseMissionUpdate(payload));
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
        break;
    }
}

  // ─── Auth ─────────────────────────────────────────────────────────────────

  login(username: string, password: string) {
    const packet = this.buildAuthPacket(Command.LOGIN_REQUEST, username, password);
    this.socket?.send(packet);
  }

  // ─── CMD 3: Auto-login bằng session token ────────────────────────────────
  tokenLogin(token: string) {
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

  requestMissionList() {
    // Java evidence: ks.o() sends kw(31) without mission id.
    const packet = this.wrapPacket(Command.MISSION_LIST, new Uint8Array(), 0);
    this.socket?.send(packet);
  }

  requestMissionDetail(questId: string) {
    // Java evidence: ks.o(String) sends kw(33), serialized as tag 77.
    const payload = new Uint8Array(this.makeStringTag(77, questId));
    const packet = this.wrapPacket(Command.MISSION_DETAIL, payload, 1);
    this.socket?.send(packet);
  }

  requestMissionAccept(questId: string) {
    // Java evidence: ks.m(String) sends kw(32), serialized as tag 77.
    const payload = new Uint8Array(this.makeStringTag(77, questId));
    const packet = this.wrapPacket(Command.MISSION_ACCEPT_ACK, payload, 1);
    this.socket?.send(packet);
  }

  requestMissionCancel(questId: string) {
    // Java evidence: ks.n(String) sends kw(41), serialized as tag 77.
    const payload = new Uint8Array(this.makeStringTag(77, questId));
    const packet = this.wrapPacket(Command.MISSION_CANCEL_ACK, payload, 1);
    this.socket?.send(packet);
  }

  requestNpcTalk(npcId: string, isContinue: boolean) {
    // Java evidence: ks.a().a(ki2.f.a, bl2) sends kw(16) with NPC id string and boolean continue flag.
    const payload = new Uint8Array([
      ...this.makeStringTag(9, npcId),
      ...this.makeByteTag(40, isContinue ? 1 : 0),
    ]);
    const packet = this.wrapPacket(Command.NPC_TALK_REQUEST, payload, 2);
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

  private parseMapNpcRoster(payload: Uint8Array): MapNpcRosterPacket {
    let pos = 0;
    let mapId = '';
    let mode = 0;
    const npcs: MapNpcRosterRecord[] = [];

    while (pos <= payload.length - 5) {
      const id = payload[pos];
      const len = this.readInt(payload, pos + 1);
      const val = payload.slice(pos + 5, pos + 5 + len);

      switch (id) {
        case 20:
          mapId = new TextDecoder().decode(val);
          break;
        case 40:
          mode = val[0] ?? 0;
          break;
        case 9:
          npcs.push(this.parseMapNpcRecord(val));
          break;
      }

      pos += 5 + len;
    }

    return { mapId, mode, npcs };
  }

  private parseMapNpcRecord(payload: Uint8Array): MapNpcRosterRecord {
    let pos = 0;
    let npcId = '';
    let displayName = '';
    let visualTypeByte = 0;
    let displayLevel = 0;
    let tileX = 0;
    let tileY = 0;
    let nameColorMode = 0;

    while (pos <= payload.length - 5) {
      const id = payload[pos];
      const len = this.readInt(payload, pos + 1);
      const val = payload.slice(pos + 5, pos + 5 + len);

      switch (id) {
        case 9:
          npcId = new TextDecoder().decode(val);
          break;
        case 26:
          displayName = new TextDecoder().decode(val);
          break;
        case 27:
          displayLevel = this.readInt(val, 0);
          break;
        case 15:
          visualTypeByte = val[0] ?? 0;
          break;
        case 129:
          tileX = this.readInt(val, 0);
          break;
        case 106:
          tileY = this.readInt(val, 0);
          break;
        case 107:
          nameColorMode = val[0] ?? 0;
          break;
      }

      pos += 5 + len;
    }

    return {
      npcId,
      displayName,
      visualTypeByte,
      displayLevel,
      tileX,
      tileY,
      nameColorMode,
    };
  }

  private parseMissionList(payload: Uint8Array): MissionListPacket {
    let pos = 0;
    const missions: MissionRecord[] = [];

    while (pos <= payload.length - 5) {
      const id = payload[pos];
      const len = this.readInt(payload, pos + 1);
      const end = pos + 5 + len;

      if (id === 77) {
        missions.push({
          questId: this.readStringTagValue(payload, pos),
          title: this.readStringTagInRange(payload, 26, pos + 5, end),
          description: '',
          price: 0,
          statusFlag: this.readByteTagInRange(payload, 100, pos + 5, end, 0) === 1,
          tasks: [],
          rewardLines: [],
        });
      }

      pos += 5 + len;
    }

    return { missions };
  }

  private parseMissionDetail(payload: Uint8Array): MissionDetailPacket {
    const questId = this.parseStringTag(payload, 77);
    const mission: MissionRecord = {
      questId,
      title: this.parseStringTag(payload, 26),
      description: this.parseStringTag(payload, 79),
      price: this.parseLongTag(payload, 132),
      statusFlag: this.parseIntTag(payload, 100) === 0,
      tasks: this.parseMissionTasks(payload, questId),
      rewardLines: [],
    };

    return { mission, openAsUpdate: mission.statusFlag };
  }

  private parseMissionTaskNotification(payload: Uint8Array): MissionTaskNotificationPacket {
    const questId = this.parseStringTag(payload, 77);
    return {
      task: {
        rawValue: this.parseIntTag(payload, 80),
        questId,
        text: this.parseStringTag(payload, 81),
      },
      message: this.parseStringTag(payload, 149),
    };
  }

  private parseMissionNotification(payload: Uint8Array): MissionNotificationPacket {
    const mission: MissionRecord = {
      questId: this.parseStringTag(payload, 77),
      title: this.parseStringTag(payload, 26),
      description: '',
      price: 0,
      statusFlag: false,
      tasks: [],
      rewardLines: this.parseRepeatedStringTags(payload, 1),
    };

    return { mission, message: this.parseStringTag(payload, 149) };
  }

  private parseMissionUpdate(payload: Uint8Array): MissionUpdatePacket {
    const questId = this.parseStringTag(payload, 77);
    return {
      mission: {
        questId,
        title: '',
        description: '',
        price: 0,
        statusFlag: false,
        tasks: this.parseMissionTasks(payload, questId),
        rewardLines: [],
      },
    };
  }

  private parseMissionTasks(payload: Uint8Array, questId: string): MissionTaskRecord[] {
    let pos = 0;
    const tasks: MissionTaskRecord[] = [];

    while (pos <= payload.length - 5) {
      const id = payload[pos];
      const len = this.readInt(payload, pos + 1);
      const end = pos + 5 + len;

      if (id === 80) {
        tasks.push({
          rawValue: this.readIntTagValue(payload, pos, -1),
          questId,
          text: this.readStringTagInRange(payload, 81, pos + 5, end),
        });
      }

      pos += 5 + len;
    }

    return tasks;
  }

  private parseRepeatedStringTags(payload: Uint8Array, tagId: number): string[] {
    let pos = 0;
    const values: string[] = [];

    while (pos <= payload.length - 5) {
      const id = payload[pos];
      const len = this.readInt(payload, pos + 1);
      const val = payload.slice(pos + 5, pos + 5 + len);

      if (id === tagId) {
        values.push(new TextDecoder().decode(val));
      }

      pos += 5 + len;
    }

    return values;
  }

  private readStringTagValue(payload: Uint8Array, tagStart: number): string {
    const len = this.readInt(payload, tagStart + 1);
    const valueStart = tagStart + 5;
    const valueEnd = valueStart + len;
    const nested = this.readStringTagInRange(payload, 77, valueStart, valueEnd);
    if (nested) {
      return nested;
    }

    return new TextDecoder().decode(payload.slice(valueStart, valueEnd));
  }

  private readIntTagValue(payload: Uint8Array, tagStart: number, fallback: number): number {
    const len = this.readInt(payload, tagStart + 1);
    const valueStart = tagStart + 5;
    const valueEnd = valueStart + len;
    const directValueLength = valueEnd - valueStart;
    if (directValueLength === 1 || directValueLength === 2 || directValueLength === 4) {
      return this.readInt(payload.slice(valueStart, valueEnd), 0);
    }

    return fallback;
  }

  private readStringTagInRange(payload: Uint8Array, tagId: number, start: number, end: number): string {
    let pos = start;

    while (pos <= end - 5) {
      const id = payload[pos];
      const len = this.readInt(payload, pos + 1);
      const valStart = pos + 5;
      const valEnd = valStart + len;

      if (valEnd > end) {
        return '';
      }
      if (id === tagId) {
        return new TextDecoder().decode(payload.slice(valStart, valEnd));
      }

      pos = valEnd;
    }

    return '';
  }

  private readByteTagInRange(payload: Uint8Array, tagId: number, start: number, end: number, fallback: number): number {
    let pos = start;

    while (pos <= end - 5) {
      const id = payload[pos];
      const len = this.readInt(payload, pos + 1);
      const valStart = pos + 5;
      const valEnd = valStart + len;

      if (valEnd > end) {
        return fallback;
      }
      if (id === tagId) {
        return payload[valStart] ?? fallback;
      }

      pos = valEnd;
    }

    return fallback;
  }

  private parseJsonPayload<T>(payload: Uint8Array, fallback: T): T {
    try {
      const text = new TextDecoder().decode(payload);
      return JSON.parse(text) as T;
    } catch {
      return fallback;
    }
  }

  private readFloat32BE(val: Uint8Array): number | undefined {
    if (val.byteLength < 4) return undefined;
    return new DataView(val.buffer, val.byteOffset, 4).getFloat32(0, false);
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
    let assetCatalogId: string | undefined;
    let spawnTemplateKey: string | undefined;
    let surfaceId: string | undefined;
    let patrolStartRatio: number | undefined;
    let patrolEndRatio: number | undefined;
    let spawnRatio: number | undefined;
    let moveSpeed: number | undefined;

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
        case 110:
          assetCatalogId = new TextDecoder().decode(val);
          break;
        case 116:
          spawnTemplateKey = new TextDecoder().decode(val);
          break;
        case 111:
          surfaceId = new TextDecoder().decode(val);
          break;
        case 112:
          patrolStartRatio = this.readFloat32BE(val);
          break;
        case 113:
          patrolEndRatio = this.readFloat32BE(val);
          break;
        case 114:
          spawnRatio = this.readFloat32BE(val);
          break;
        case 115:
          moveSpeed = this.readFloat32BE(val);
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
      assetCatalogId,
      spawnTemplateKey,
      surfaceId,
      patrolStartRatio,
      patrolEndRatio,
      spawnRatio,
      moveSpeed,
    };
  }

}
