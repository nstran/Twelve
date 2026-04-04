import { Buffer } from 'buffer';

export interface PacketRequest {
  command: number;
  payload: Buffer;
  tags: Map<number, Buffer>;
}

export class BinaryProtocol {
  /**
   * Parse a game packet from the server.
   * Format: [SubCount (Short)] [PayloadLength (Int)] [Command (Byte)] [Payload (N)]
   */
  static parsePacket(data: Buffer): PacketRequest {
    let offset = 0;
    
    // SubCount (2 bytes, Big-Endian)
    const subCount = data.readUInt16BE(offset);
    offset += 2;
    
    // PayloadLength (4 bytes, Big-Endian)
    const payloadLength = data.readUInt32BE(offset);
    offset += 4;
    
    // Command (1 byte)
    const command = data.readUInt8(offset);
    offset += 1;
    
    // Payload
    const payload = data.subarray(offset, offset + payloadLength);
    
    return {
      command,
      payload,
      tags: this.parseTags(payload)
    };
  }

  static parseTags(payload: Buffer): Map<number, Buffer> {
    const tags = new Map<number, Buffer>();
    let offset = 0;
    
    while (offset <= payload.length - 5) {
      const tagId = payload.readUInt8(offset);
      offset += 1;
      
      const length = payload.readUInt32BE(offset);
      offset += 4;
      
      if (length < 0 || offset + length > payload.length) {
        break;
      }
      
      const value = payload.subarray(offset, offset + length);
      tags.set(tagId, value);
      offset += length;
    }
    
    return tags;
  }

  static buildPacket(command: number, payload: Buffer, subCount: number = 0): Buffer {
    const header = Buffer.alloc(7); // 2 (sub) + 4 (len) + 1 (cmd)
    header.writeUInt16BE(subCount, 0);
    header.writeUInt32BE(payload.length, 2);
    header.writeUInt8(command, 6);
    
    return Buffer.concat([header, payload]);
  }

  static makeTag(tagId: number, data: Buffer | string): Buffer {
    const valueBuffer = typeof data === 'string' ? Buffer.from(data, 'utf8') : data;
    const tagHeader = Buffer.alloc(5);
    tagHeader.writeUInt8(tagId, 0);
    tagHeader.writeUInt32BE(valueBuffer.length, 1);
    
    return Buffer.concat([tagHeader, valueBuffer]);
  }

  static makeIntTag(tagId: number, value: number): Buffer {
    const data = Buffer.alloc(4);
    data.writeInt32BE(value, 0);
    return this.makeTag(tagId, data);
  }
}
