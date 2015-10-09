package org.mcserver.minecraftserver.network.session;

import io.netty.channel.ChannelHandlerContext;

public class UserProfile {
	private UserState state;
	private ChannelHandlerContext ctx;
	public UserProfile(ChannelHandlerContext ctx){
		this.ctx = ctx;
	}
	/**
	 * Sets User's State
	 * @param state
	 */
	public void setState(UserState state){
		this.state = state;
	}
	/**
	 * Returns User's State
	 * @return
	 */
	public UserState getState(){
		return this.state;
	}
}
