import { BinaryProtocol } from './BinaryProtocol';

export interface Actor {
  id: string;
  label: string;
  kind: number;
  x: number;
  y: number;
}

export class SocketClient {
  private socket: WebSocket | null = null;
  private onMapData: (data: any) => void;
  private onActorsData: (actors: Actor[]) => void;

  constructor(
    onMapData: (data: any) => void,
    onActorsData: (actors: Actor[]) => void
  ) {
    this.onMapData = onMapData;
    this.onActorsData = onActorsData;
  }

  connect(url: string) {
    this.socket = new WebSocket(url);
    this.socket.binaryType = 'arraybuffer';

    this.socket.onopen = () => {
      console.log('[Socket] Connected');
      this.login('ElitePlayer');
    };

    this.socket.onmessage = (event) => {
      const protocol = new BinaryProtocol(event.data);
      const command = protocol.readByte();
      const length = protocol.readShort();
      const payload = protocol.readBytes(length);

      this.handlePacket(command, payload);
    };
  }

  private login(username: string) {
    const protocol = new BinaryProtocol();
    protocol.writeByte(4);
    protocol.writeShort(12);
    protocol.writeByte(9);
    protocol.writeString(username);
    this.socket?.send(protocol.getBuffer());
  }

  /**
   * Sending Move Request (CMD 44)
   */
  public move(x: number, y: number) {
    if (!this.socket || this.socket.readyState !== WebSocket.OPEN) return;

    const protocol = new BinaryProtocol();
    protocol.writeByte(44); // Move Intent
    protocol.writeShort(10); // Length placeholder
    protocol.writeByte(102); // Tag X
    protocol.writeInt(x);
    protocol.writeByte(103); // Tag Y
    protocol.writeInt(y);
    
    this.socket.send(protocol.getBuffer());
    console.log(`[Socket] Sent Move to (${x}, ${y})`);
  }

  private handlePacket(command: number, payload: Uint8Array) {
    const protocol = new BinaryProtocol(payload.buffer);
    
    if (command === 11) {
      const mapData: any = {};
      while (protocol.hasMore()) {
        const tag = protocol.readByte();
        if (tag === 20) mapData.name = protocol.readString();
        else if (tag === 56) mapData.width = protocol.readInt();
        else if (tag === 57) mapData.height = protocol.readInt();
        else if (tag === 58) mapData.tileSize = protocol.readInt();
      }
      this.onMapData(mapData);
    } else if (command === 43 || command === 44) { // Update Actors (Echo or Scene)
      const actors: Actor[] = [];
      const actor: Partial<Actor> = {};
      while (protocol.hasMore()) {
        const tag = protocol.readByte();
        if (tag === 9) actor.id = protocol.readString();
        else if (tag === 26) actor.label = protocol.readString();
        else if (tag === 27) actor.kind = protocol.readInt();
        else if (tag === 102) actor.x = protocol.readInt();
        else if (tag === 103) actor.y = protocol.readInt();
      }
      
      if (actor.id && actor.x !== undefined) {
        actors.push(actor as Actor);
      }
      this.onActorsData(actors);
    }
  }
}
