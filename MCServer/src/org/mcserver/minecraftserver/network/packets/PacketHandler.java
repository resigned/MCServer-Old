package org.mcserver.minecraftserver.network.packets;

import java.util.HashMap;

import org.mcserver.minecraftserver.network.packets.Packet;
import org.mcserver.minecraftserver.network.packets.handshake.PacketRecieveHandshake;
import org.mcserver.minecraftserver.util.ByteBufUtils;

import io.netty.buffer.ByteBuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class PacketHandler extends ChannelInboundHandlerAdapter {
	static HashMap<Byte,Packet> Outgoing = new HashMap<Byte,Packet>();
	static HashMap<Byte,Packet> Incoming = new HashMap<Byte,Packet>();

	
	public static void setupRegistry(){
		Incoming.put(0x00, new PacketRecieveHandshake());
	}
	
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buff = (ByteBuf) msg;
        int PacketLength = ByteBufUtils.readVarInt(buff);
        int PacketId = ByteBufUtils.readVarInt(buff);
        if(Incoming.containsKey(PacketId)){
        	Packet packet = null;
			try {
				packet = Incoming.get(PacketId).newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
	        packet.BufIn(buff);
		}
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
