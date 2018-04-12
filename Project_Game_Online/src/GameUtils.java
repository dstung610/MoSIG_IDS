// package Game;

public class GameUtils {
	static public final String host = "localhost";
	static public final String s_sExchangeNameServerClient = "ServerBroadcast";
	static public final String s_sExchangeNameClientServer = "CustomerService";
	static public final int s_SettingZoneSize = 32; //256; // realsize = zone_size x zone_size
	static public final int s_SettingNbZones = 4;
	static public final int s_SettingMapSize = s_SettingZoneSize * 2;

	static public String GenerateChanelName(String from, String to) {
		//"connect ABC_XYZ"
		return "cn_" + from + "_" + to + "+";
	}

	static String GenerateLoginMsg(String sPlayerName)
    {
		//"login request from player ABC"
        return "lOGINreqESt:" + sPlayerName;
	}
	
	static String GeneratePlayerSaveFile(String sPlayerName)
    {
		//"login request from player ABC"
        return "DB_" + sPlayerName + ".sav";
	}
	

	static void LOG(String msg)
	{
		System.out.println(msg);
	}

	static String packPlayerForwardMsg(String sNodeName, String sPlayerName, float x, float y)
	{
		return "ALLNODES*" + sNodeName + "|FoRwArD-" + sPlayerName + ":" + x + " " + y;
	}
	static String packPlayerInfo(String sPlayerName, float x, float y)
	{
		return "PLAYERINFO*"+ sPlayerName + ":" + x + " " + y;
	}

	static Player unpackPlayerInfo(String msg)
	{
        msgs = msg.split("-");
        String task = msgs[0];
        msg = msgs[1];
        if (task.compareTo("CruRentPOs") == 0) {
            msgs = msg.split(":");
            String sPlayerName = msgs[0];
            msg = msgs[1];
            msgs = msg.split(" ");
            float x = Float.parseFloat(sPos[0]);
            float y = Float.parseFloat(sPos[1]);

	}
}