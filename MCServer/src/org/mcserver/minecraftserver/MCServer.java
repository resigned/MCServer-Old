package org.mcserver.minecraftserver;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MCServer {
	static ServerSocket Server;
	static DataOutputStream out;
	static DataInputStream in;
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
			in = new DataInputStream(inputStream);

			int packetId = in.read();
			PacketHandler.handle(in, out, packetId);
			out.close();
			in.close();
			socket.close();
		}
	}
}
