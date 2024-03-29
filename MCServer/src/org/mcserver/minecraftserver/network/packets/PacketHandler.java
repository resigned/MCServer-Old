package org.mcserver.minecraftserver.network.packets;

import java.util.HashMap;

import org.mcserver.minecraftserver.network.packets.Packet;
import org.mcserver.minecraftserver.network.packets.handshake.PacketRecieveHandshake;
import org.mcserver.minecraftserver.network.session.SessionHandler;
import org.mcserver.minecraftserver.network.session.UserProfile;
import org.mcserver.minecraftserver.network.session.UserState;
import org.mcserver.minecraftserver.util.BufferUtils;

import io.netty.buffer.ByteBuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class PacketHandler extends ChannelInboundHandlerAdapter {
	static HashMap<Integer,Class<? extends Packet>> Outgoing = new HashMap<Integer,Class<? extends Packet>>();
	static HashMap<Integer,Class<? extends Packet>> Incoming = new HashMap<Integer,Class<? extends Packet>>();
	
	public static void setupRegistry(){
		Incoming.put(0x00, PacketRecieveHandshake.class);
	}
	
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
    	UserProfile user = SessionHandler.getUserProfile(ctx);
    	user.setState(UserState.HANDSHAKE);
        ByteBuf buff = (ByteBuf) msg;
        int PacketLength = BufferUtils.readVarInt(buff);
        int PacketId = BufferUtils.readVarInt(buff);
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