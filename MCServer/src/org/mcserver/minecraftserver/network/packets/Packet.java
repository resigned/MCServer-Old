package org.mcserver.minecraftserver.network.packets;

import io.netty.buffer.ByteBuf;

public abstract class Packet {
    public abstract void BufOut(ByteBuf out);
    public abstract void BufIn(ByteBuf in);
    public abstract void handle();
}
