package org.mcserver.minecraftserver;

public class MCServer {
	static int port = 25565;
	static Server server;

	public static void main(String[] args) throws Exception {
		System.out.println("Starting Server...");
		server = new Server(port);
		System.out.println("Started Server...");
		server.run();
	}
}