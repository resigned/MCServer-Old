package org.mcserver.minecraftserver.network.packets.handshake;

import org.mcserver.minecraftserver.network.packets.Packet;

import io.netty.buffer.ByteBuf;

public class PacketRecieveHandshake extends Packet {

	@Override
	public void BufOut(ByteBuf out) {
		
	}

	@Override
	public void BufIn(ByteBuf in) {
		System.out.println("hi");
	}

	@Override
	public void handle() {
		
	}

}
