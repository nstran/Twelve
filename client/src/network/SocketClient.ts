import EventEmitter from 'eventemitter3';

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
  width: number;
  height: number;
  tileSize: number;
  tiles: number[];
}

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
    const cmd = data[6];
    const payload = data.slice(7, 7 + payloadLength);

    console.log(`[SocketClient] ← Received CMD ${cmd}, payloadLength=${payloadLength}`);

    switch (cmd) {
      case 4: // Login Success
        this.emit('authSuccess');
        break;

      case 0: // Server Error — phát cả authFailed lẫn registerFailed để màn hình nào cũng nhận được
        const errorMsg = this.parseStringTag(payload, 1);
        console.log('[SocketClient] ← CMD 0 server error:', JSON.stringify(errorMsg));
        this.emit('authFailed',     errorMsg);
        this.emit('registerFailed', errorMsg); // RegisterScreen cũng lắng nghe
        break;

      case 131: // Register Response
        const regMsg = this.parseStringTag(payload, 1);
        console.log('[SocketClient] ← CMD 131 Register response msg:', JSON.stringify(regMsg));
        if (regMsg.includes('thanh cong')) {
          console.log('[SocketClient] → emit registerSuccess');
          this.emit('registerSuccess', regMsg);
        } else {
          console.log('[SocketClient] → emit registerFailed');
          this.emit('registerFailed', regMsg);
        }
        break;

      case 11: // Map Info — parse tags and emit structured MapInfo
        const mapInfo = this.parseMapInfo(payload);
        this.emit('mapInfo', mapInfo);
        break;

      case 43: // Scene Actors — parse sequentially (multiple actors share tag IDs)
        const actors = this.parseActors(payload);
        this.emit('actorsUpdate', actors);
        break;

      case 44: // Move Ack
        this.emit('moveAck', payload);
        break;

      default:
        console.log(`[SocketClient] Unhandled CMD ${cmd}`);
    }
  }

  // ─── Auth ─────────────────────────────────────────────────────────────────

  login(username: string, password: string) {
    const packet = this.buildAuthPacket(2, username, password); // CMD 2 = Login
    this.socket?.send(packet);
  }

  // ─── CMD 1: Đăng ký tài khoản ───────────────────────────────────────────
  // Tags: 9=username, 10=password, 11=fullName, 12=dob, 13=phone, 14=gender(byte)
  register(
    username: string,
    password: string,
    fullName: string,
    dob: string,        // DD-MM-YYYY
    phone: string,
    gender: 0 | 1,     // 0=Nam, 1=Nữ
  ) {
    console.log('[SocketClient] register() called, socket state:', this.socket ? this.socket.readyState : 'NULL');

    if (!this.socket) {
      console.error('[SocketClient] register() FAILED: socket is NULL');
      return;
    }
    if (this.socket.readyState !== WebSocket.OPEN) {
      console.error('[SocketClient] register() FAILED: socket.readyState =', this.socket.readyState, '(expected', WebSocket.OPEN, '= OPEN)');
      return;
    }

    const tags: number[] = [
      ...this.makeStringTag(9,  username),
      ...this.makeStringTag(10, password),
      ...this.makeStringTag(11, fullName),
      ...this.makeStringTag(12, dob),
      ...this.makeStringTag(13, phone),
      ...this.makeByteTag(14, gender),
    ];
    const payload = new Uint8Array(tags);

    // 7-byte header: SubCount(2) + PayloadLength(4) + CMD(1)
    const packet = new Uint8Array(7 + payload.length);
    packet[0] = 0; packet[1] = 6;                       // SubCount = 6 tags
    packet[2] = (payload.length >> 24) & 0xFF;
    packet[3] = (payload.length >> 16) & 0xFF;
    packet[4] = (payload.length >> 8)  & 0xFF;
    packet[5] =  payload.length        & 0xFF;
    packet[6] = 1;                                       // CMD 1 = Register
    packet.set(payload, 7);

    console.log('[SocketClient] → Sending CMD 1 Register, packet size:', packet.length, 'bytes');
    this.socket.send(packet);
    console.log('[SocketClient] → send() completed');
  }

  joinMap() {
    // CMD 11: Request map info. 7-byte header with empty payload.
    const packet = new Uint8Array(7);
    packet[0] = 0; packet[1] = 0; // SubCount = 0
    packet[2] = 0; packet[3] = 0; packet[4] = 0; packet[5] = 0; // PayloadLength = 0
    packet[6] = 11; // CMD
    this.socket?.send(packet);
  }

  move(x: number, y: number) {
    // Build payload: xTag + yTag
    const xTag = this.makeIntTag(102, x);
    const yTag = this.makeIntTag(103, y);
    const payload = new Uint8Array([...xTag, ...yTag]);

    // 7-byte header: SubCount(2) + PayloadLength(4) + Command(1)
    const packet = new Uint8Array(7 + payload.length);
    packet[0] = 0; packet[1] = 2; // SubCount = 2 tags
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

  /**
   * Parse MapInfo from CMD 11 payload.
   * Tags: 20=name, 56=width, 57=height, 58=tileW, 59=tileH, 55=ground layer
   */
  private parseMapInfo(payload: Uint8Array): MapInfo {
    let pos = 0;
    let name = 'Unknown';
    let width = 10;
    let height = 8;
    let tileSize = 32;
    let tiles: number[] = [];

    while (pos <= payload.length - 5) {
      const id = payload[pos];
      const len = this.readInt(payload, pos + 1);
      const val = payload.slice(pos + 5, pos + 5 + len);

      switch (id) {
        case 20: name = new TextDecoder().decode(val); break;
        case 56: width = this.readInt(val, 0); break;
        case 57: height = this.readInt(val, 0); break;
        case 58: tileSize = this.readInt(val, 0); break;
        case 55: tiles = Array.from(val); break; // Ground layer
      }
      pos += 5 + len;
    }

    return { name, width, height, tileSize, tiles };
  }

  /**
   * Parse actor list from CMD 43 payload.
   * Tags per actor: 9=id, 26=label, 27=kind, 102=x, 103=y
   * Actors share the same tag IDs, so we must parse SEQUENTIALLY (not by dictionary).
   * A new actor starts whenever we encounter tag 9 again.
   */
  private parseActors(payload: Uint8Array): Actor[] {
    const actors: Actor[] = [];
    let current: Partial<Actor> | null = null;
    let pos = 0;

    while (pos <= payload.length - 5) {
      const id = payload[pos];
      const len = this.readInt(payload, pos + 1);
      const val = payload.slice(pos + 5, pos + 5 + len);

      switch (id) {
        case 9: // Actor ID — start of a new actor block
          if (current?.id) actors.push(current as Actor);
          current = { id: new TextDecoder().decode(val) };
          break;
        case 26: // Label / display name
          if (current) current.label = new TextDecoder().decode(val);
          break;
        case 27: // Kind (class/monster type)
          if (current) current.kind = this.readInt(val, 0);
          break;
        case 102: // X position
          if (current) current.x = this.readInt(val, 0);
          break;
        case 103: // Y position
          if (current) current.y = this.readInt(val, 0);
          break;
      }
      pos += 5 + len;
    }
    if (current?.id) actors.push(current as Actor);
    return actors;
  }
}
