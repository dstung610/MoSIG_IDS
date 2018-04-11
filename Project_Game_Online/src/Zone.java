import java.util.LinkedList;

public class Zone
{    
    private LinkedList<Player> m_lPlayers;
    private char[][] m_acMiniMap;
    public Zone()
    {
        m_lPlayers = new LinkedList<Player>();
        m_acMiniMap = new char[GameUtils.s_ZoneSize][GameUtils.s_ZoneSize];
    }

    public char[][] getMiniMap()
    {
        return m_acMiniMap;
    }

    public String getMiniMapAsString()
    {
        StringBuilder sB = new StringBuilder();
        for (int i = 0; i < GameUtils.s_ZoneSize; i++)
            sB.append(m_acMiniMap[i]);
        return sB.toString();
    }
}