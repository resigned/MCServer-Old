package org.mcserver.minecraftserver.packets;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PacketHandler extends Thread implements Runnable {
	ServerSocket ssocket;
	static DataOutputStream out;
	static DataInputStream in;
	static OutputStream outputStream;
	static InputStream inputStream;
	@Override
	public void start(){
			Socket socket = ssocket.accept();
			outputStream = socket.getOutputStream();
			out = new DataOutputStream(outputStream);

			inputStream = socket.getInputStream();
			in = new DataInputStream(inputStream);
			PacketHandler.handle(in, out);
			out.close();
			in.close();
			socket.close();
	}
	public static void handle(DataInputStream in, DataOutputStream out) throws IOException, JSONException {
		int LengthId = ByteBufUtils.readVarInt(in);
		int PacketId = ByteBufUtils.readVarInt(in);
		switch (PacketId) {
		case 0x00:
			int ProtocolVersion = ByteBufUtils.readVarInt(in);
			String ServerAddress = ByteBufUtils.readUTF8(in);
			int ServerPort = in.readUnsignedShort();
			int NextState = ByteBufUtils.readVarInt(in);
			if(NextState == 1) {
				System.out.println("User sent status request.");
			} else if (NextState == 2) {
				System.out.println("User tried joining.");
			} else {
				System.out.println("User requested response.");
				ByteArrayOutputStream b = new ByteArrayOutputStream();
				DataOutputStream handshake = new DataOutputStream(b);
				handshake.writeByte(0x00);
				JSONObject h = new JSONObject()
						.put("version", new JSONObject().put("name", "1.8.7").put("protocol", 47))
						.put("players",
								new JSONObject().put("max", 100).put("online", 5).put("sample",
										new JSONArray().put(new JSONObject().put("name", "Bluesocks").put("id",
												"8652d6de-69bd-4319-8991-065231982198"))))
						.put("description", new JSONObject().put("text", "Hello world"));
				handshake.writeUTF(h.toString());

				ByteBufUtils.writeVarInt(out, b.size());
				out.write(b.toByteArray());
				ByteBufUtils.writeVarInt(handshake, 1);
			}
			break;
		case 0x01:
			out.writeByte(0x01);
			out.writeLong(in.readLong());
			System.out.println("User attemped to ping, sending pong.");
		default:
			break;
		}
	}
}
