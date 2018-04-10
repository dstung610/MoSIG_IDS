// package Game;

public class GameSettings {
	static public final String host = "localhost";
	static public final int s_ZoneSize = 16; //256; // realsize = zone_size x zone_size

	static public String GenerateChanelName(String from, String to) {
		return "cn_" + from + "_" + to + "_";
	}
}