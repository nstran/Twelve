import { Buffer } from 'buffer';
import { BinaryProtocol, PacketRequest } from './BinaryProtocol';

export type PacketHandler = (request: PacketRequest) => void;

class SocketClient {
  private ws: WebSocket | null = null;
  private handlers: Map<number, PacketHandler[]> = new Map();

  connect(url: string) {
    this.ws = new WebSocket(url);
    this.ws.binaryType = 'arraybuffer';

    this.ws.onopen = () => {
      console.log('[Socket] Connected to server:', url);
      // Automatically send login for now
      this.sendLogin('Player1');
    };

    this.ws.onmessage = (event) => {
      const buffer = Buffer.from(event.data);
      const request = BinaryProtocol.parsePacket(buffer);
      console.log(`[Socket] Received CMD ${request.command}`);
      this.triggerHandlers(request);
    };

    this.ws.onclose = () => {
      console.log('[Socket] Disconnected');
    };

    this.ws.onerror = (error) => {
      console.error('[Socket] Error:', error);
    };
  }

  on(command: number, handler: PacketHandler) {
    if (!this.handlers.has(command)) {
      this.handlers.set(command, []);
    }
    this.handlers.get(command)?.push(handler);
  }

  private triggerHandlers(request: PacketRequest) {
    const list = this.handlers.get(request.command);
    if (list) {
      list.forEach(h => h(request));
    }
  }

  send(command: number, payload: Buffer, subCount: number = 0) {
    if (this.ws && this.ws.readyState === WebSocket.OPEN) {
      const packet = BinaryProtocol.buildPacket(command, payload, subCount);
      this.ws.send(packet.buffer);
    }
  }

  // Helper: Initial Login (CMD 4)
  private sendLogin(username: string) {
    const payload = BinaryProtocol.makeTag(9, username);
    this.send(4, payload, 1);
    console.log('[Socket] Sent Login Request for', username);
  }

  // Request Map (CMD 11)
  requestMap(mapName: string) {
    const payload = BinaryProtocol.makeTag(20, mapName);
    this.send(11, payload, 1);
  }
}

export const socketClient = new SocketClient();
