package src.org.mcserver.minecraftserver;

public class Server {
	private int port;
	public static Thread ph = null;

	public Server(int port){
		this.port = port;
		ph = new Thread(new PacketHandler(), "PacketHandler");
	}
	
	public void stop(){
		
	}
}
