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
        return "lr_" + sPlayerName + "+";
	}
	
	static String GeneratePlayerSaveFile(String sPlayerName)
    {
		//"login request from player ABC"
        return "DB_" + sPlayerName + ".sav";
	}
}