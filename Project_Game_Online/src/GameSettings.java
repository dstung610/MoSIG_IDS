package Game;

public class GameSettings
{
	static public final String host = "localhost";
	
	static public String GenerateChanelName(String from, String to)
	{
		return "cn_" + from + "_" + to + "__";
	}
}