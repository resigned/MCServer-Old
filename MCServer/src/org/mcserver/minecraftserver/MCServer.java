package org.mcserver.minecraftserver;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

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
			switch (packetId) {
			case 15:
				final int version = in.read();
				@SuppressWarnings("unused")
				final int ip = in.read();
				@SuppressWarnings("unused")
				final int port = in.read();
				final int state = in.read();
				switch (state) {
				case 108:
					System.out.println("User attempted to handshake.");
			        ByteArrayOutputStream b = new ByteArrayOutputStream();
			        DataOutputStream handshake = new DataOutputStream(b);
			        handshake.writeByte(0x00);
			        JSONObject h = new JSONObject()
	                .put("version", new JSONObject()
	                        .put("name", "1.8.7")
	                        .put("protocol", 47))
	                .put("players", new JSONObject()
	                        .put("max", 100)
	                        .put("online", 5)
	                        .put("sample", new JSONArray()
	                                .put(new JSONObject()
	                                        .put("name", "Bluesocks")
	                                        .put("id", "8652d6de-69bd-4319-8991-065231982198"))))
	                .put("description", new JSONObject()
	                        .put("text", "Hello world"));
			        handshake.writeUTF(h.toString());

			        writeVarInt(out, b.size());
			        out.write(b.toByteArray());
			        writeVarInt(handshake, 1);

					out.writeByte(0x01);
					
					break;
				}
				break;
			case 65533:
				System.out.println("User attemped to ping, sending pong.");
			default:
				break;
			}
			System.out.println(packetId);
		}
	}
	public static void writeVarInt(DataOutputStream out, int paramInt) throws IOException {
        while (true) {
            if ((paramInt & 0xFFFFFF80) == 0) {
              out.writeByte(paramInt);
              return;
            }

            out.writeByte(paramInt & 0x7F | 0x80);
            paramInt >>>= 7;
        }
    }
}
