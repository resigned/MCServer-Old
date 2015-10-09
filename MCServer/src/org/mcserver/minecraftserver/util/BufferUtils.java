package org.mcserver.minecraftserver.util;

import io.netty.buffer.ByteBuf;

public class BufferUtils {
	public static int readVarInt(ByteBuf buf) {
        int varint = 0;
        int bytes = 0;
        byte in;
        while (true) {
            in = buf.readByte();
            varint |= (in & 0x7F) << (bytes * 7);
            if (bytes > 5) {
            	 throw new RuntimeException("VarInt is too big");
            }
            if ((in & 0x80) != 0x80) {
                break;
            }
        }
        return varint;
    }

}
