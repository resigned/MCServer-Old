package src.org.mcserver.minecraftserver;

public class MCServer {
	static int port = 25565;
	static Server server;

	public static void main(String[] args) throws Exception {
		Server server = new Server(port);
	}
}
