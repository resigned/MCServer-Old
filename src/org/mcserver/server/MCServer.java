package org.mcserver.minecraftserver;

public class MCServer {
	static int port = 25565;
	static Server server;

	public static void main(String[] args) throws Exception {
		server = new Server(port);
	}
}
