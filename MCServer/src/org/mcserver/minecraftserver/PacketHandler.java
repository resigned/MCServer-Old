package org.mcserver.minecraftserver;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PacketHandler {
	public static void HandlePacket(DataInputStream in, DataOutputStream out, int packet) throws IOException, JSONException {
		switch (packet) {
		case 15:
			in.skip(3);
			final int state = ByteBufUtils.readVarInt(in);
			System.out.println(state+"");
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

				out.writeByte(0x01);
			break;
		case 65533:
			System.out.println("User attemped to ping, sending pong.");
		default:
			break;
		}
	}
}
