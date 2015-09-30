package org.mcserver.minecraftserver;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MCServer {
	static ServerSocket Server;
	static DataOutputStream out;
	static InputStreamReader in;
	static BufferedReader in1;
	static OutputStream outputStream;
    static InputStream inputStream;

	public static void startServer() throws Exception {
		Server = new ServerSocket(25565);
		System.out.println("Successfully started server at the port " + 25565);
	}

	public static void stopServer() throws IOException {
		Server.close();
		System.out.println("Successfully stoped server.");
	}

	public static void main(String[] args) throws Exception {
		startServer();
		while (true) {
			Socket socket = Server.accept();
			System.out.println("A User Connected.");
			outputStream = socket.getOutputStream();
			out = new DataOutputStream(outputStream);

			inputStream = socket.getInputStream();
			in = new InputStreamReader(inputStream);
		    in1 = new BufferedReader(in);
		    
		    int packetId = in.read();
			switch(packetId){
			case 15:
                final int version = in.read();
                @SuppressWarnings("unused")
                final int ip   = in.read();
                @SuppressWarnings("unused")
                final int port    = in.read();
                final int state   = in.read();
				System.out.println("User attempted to handshake.");
				out.write(0x00);
				out.writeUTF("{\"version\": {\"name\":\"1.8.7\",\"protocol\": 47},\"players\": {\"max\": 100,\"online\": 5,\"sample\": [{\"name\": \"thinkofdeath\",\"id\":\"4566e69f-c907-48ee-8d71-d7ba5aa00d20\"}]},\"description\": {\"text\": \"Hello world\"},\"favicon\": \"data:image/png;base64,<data>\"}}");
				System.out.println(state+"");
				break;
			case 65533:
				System.out.println("User attemped to ping, sending pong.");
				out.writeInt(0x01);
				out.close();
				in.close();
				in1.close();
			default:
				break;
			}
			System.out.println(packetId);
		}
	}
}
