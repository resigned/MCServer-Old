package org.mcserver.minecraftserver.network.session;

import java.util.HashMap;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SessionHandler extends ChannelInboundHandlerAdapter {
	static HashMap<ChannelHandlerContext, UserProfile> UserProfileRegistry = new HashMap<ChannelHandlerContext, UserProfile>();
	public void channelActive(ChannelHandlerContext ctx){
		UserProfile user = new UserProfile(ctx);
		UserProfileRegistry.put(ctx, user);
    }
	
	public static UserProfile getUserProfile(ChannelHandlerContext ctx){
		UserProfile user = UserProfileRegistry.get(ctx);
		return user;
	}

}
