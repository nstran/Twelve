import EventEmitter from 'eventemitter3';

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
    this.socket = new WebSocket(url);
    this.socket.binaryType = 'arraybuffer';

    this.socket.onopen = () => {
      console.log('Connected to battlefield');
      this.emit('connected');
    };

    this.socket.onmessage = (event) => {
      const data = new Uint8Array(event.data);
      this.handlePacket(data);
    };

    this.socket.onclose = () => {
      console.log('Disconnected from battlefield');
      this.emit('disconnected');
    };
  }

  private handlePacket(data: Uint8Array) {
    if (data.length < 7) return;

    // Decode 7-byte Header: SubCount(2) + PayloadLength(4) + Command(1)
    const subCount = (data[0] << 8) | data[1];
    const payloadLength = (data[2] << 24) | (data[3] << 16) | (data[4] << 8) | data[5];
    const cmd = data[6];
    const payload = data.slice(7);

    switch (cmd) {
      case 4: // Login Success
        this.emit('authSuccess');
        break;
      case 0: // Error/Auth Failed
        const errorMsg = this.parseStringTag(payload, 1);
        this.emit('authFailed', errorMsg);
        break;
      case 131: // Register Response
        const regMsg = this.parseStringTag(payload, 1);
        if (regMsg.includes('thanh cong')) {
          this.emit('registerSuccess', regMsg);
        } else {
          this.emit('registerFailed', regMsg);
        }
        break;
      case 11: // Map Info
        this.emit('mapInfo', payload);
        break;
      case 43: // Scene Actors
        this.emit('actorsUpdate', payload);
        break;
      case 44: // Move Ack
        this.emit('moveAck', payload);
        break;
    }
  }

  login(username: string, passwordHash: string) {
    const packet = this.buildAuthPacket(4, username, passwordHash);
    this.socket?.send(packet);
  }

  register(username: string, passwordHash: string) {
    const packet = this.buildAuthPacket(131, username, passwordHash);
    this.socket?.send(packet);
  }

  private buildAuthPacket(cmd: number, user: string, pass: string): Uint8Array {
    const userTag = this.makeStringTag(9, user);
    const passTag = this.makeStringTag(10, pass);
    const payload = new Uint8Array([...userTag, ...passTag]);
    
    // 7-byte Header: SubCount(2) + PayloadLength(4) + Command(1)
    const packet = new Uint8Array(7 + payload.length);
    packet[0] = 0; // SubCount High
    packet[1] = 1; // SubCount Low (1 tag)
    packet[2] = (payload.length >> 24) & 0xFF; // Length 4 bytes
    packet[3] = (payload.length >> 16) & 0xFF;
    packet[4] = (payload.length >> 8) & 0xFF;
    packet[5] = payload.length & 0xFF;
    packet[6] = cmd;
    packet.set(payload, 7);
    return packet;
  }

  private makeStringTag(id: number, value: string): number[] {
    const bytes = Array.from(new TextEncoder().encode(value));
    // 5-byte Tag Header: ID(1) + Length(4)
    return [
      id, 
      (bytes.length >> 24) & 0xFF, 
      (bytes.length >> 16) & 0xFF, 
      (bytes.length >> 8) & 0xFF, 
      bytes.length & 0xFF, 
      ...bytes
    ];
  }

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

  joinMap() {
    const packet = new Uint8Array([29, 0, 0]); // Empty join
    this.socket?.send(packet);
  }

  move(x: number, y: number) {
    const xTag = [102, 0, 4, (x >> 24) & 0xFF, (x >> 16) & 0xFF, (x >> 8) & 0xFF, x & 0xFF];
    const yTag = [103, 0, 4, (y >> 24) & 0xFF, (y >> 16) & 0xFF, (y >> 8) & 0xFF, y & 0xFF];
    const payload = new Uint8Array([...xTag, ...yTag]);
    
    const packet = new Uint8Array(3 + payload.length);
    packet[0] = 44;
    packet[1] = (payload.length >> 8) & 0xFF;
    packet[2] = payload.length & 0xFF;
    packet.set(payload, 3);
    this.socket?.send(packet);
  }
}
