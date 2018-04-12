
public class GameUtils {
	static public final String host = "localhost";
	static public final String s_sExchangeNameServerClient = "ServerBroadcast";
	static public final String s_sExchangeNameClientServer = "CustomerService";
	static public final int s_SettingZoneSize = 16; //256; // realsize = zone_size x zone_size
	static public final int s_SettingNbZones = 4;
	static public final int s_SettingMapSize = s_SettingZoneSize * 2;

	static public final float[] s_faZoneTopX = {0, s_SettingZoneSize + 1, s_SettingZoneSize + 1, 0};
	static public final float [] s_faZoneTopY = {0, 0, s_SettingZoneSize + 1, s_SettingZoneSize + 1};
	static public final float[] s_faZoneBottomX = {s_SettingZoneSize, s_SettingZoneSize * 2, s_SettingZoneSize * 2, s_SettingZoneSize};
	static public final float [] s_faZoneBottomY = {s_SettingZoneSize, s_SettingZoneSize, s_SettingZoneSize * 2, s_SettingZoneSize * 2};

	static public String GenerateChanelName(String from, String to) {
		//"connect ABC_XYZ"
		return "cn_" + from + "_" + to + "+";
	}

	static String GenerateLoginMsg(String sPlayerName) {
		//"login request from player ABC"
		return "lOGINreqESt:" + sPlayerName;
	}

	static String GeneratePlayerSaveFile(String sPlayerName) {
		//"login request from player ABC"
		return "DB_" + sPlayerName + ".sav";
	}

	static void LOG(String msg) {
		System.out.println(msg);
	}

	static String packPlayerForwardMsg(String sNodeName, String sPlayerName, float x, float y) {
		return "ALLNODES@" + sNodeName + "#FoRwArD-" + sPlayerName + ":" + x + " " + y;
	}

	static String packPlayerRemoveMsg(String sNodeName, String sPlayerName) {
		return "ALLNODES@" + sNodeName + "#REMOVE-" + sPlayerName;
	}

	static String packPlayerInfo(String sPlayerName, float x, float y) {
		return "PLAYERINFO@" + sPlayerName + ":" + x + " " + y;
	}

	static Player unpackPlayerInfo(String msg) {
		String[] msgs;
		msgs = msg.split("@");
		msg = msgs[1];//remove header

		msgs = msg.split(":");
		String sPlayerName = msgs[0];
		msg = msgs[1];
		msgs = msg.split(" ");
		float x = Float.parseFloat(msgs[0]);
		float y = Float.parseFloat(msgs[1]);

		return new Player(sPlayerName, new vec2(x, y));
	}
	static vec2 unpackPlayerInfoToVec2(String msg) {
		String[] msgs;
		msgs = msg.split("*");
		msg = msgs[1];//remove header

		msgs = msg.split(":");
		String sPlayerName = msgs[0];
		msg = msgs[1];
		msgs = msg.split(" ");
		float x = Float.parseFloat(msgs[0]);
		float y = Float.parseFloat(msgs[1]);

		return new vec2(x, y);
	}
}