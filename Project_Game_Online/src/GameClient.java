public class GameClient
{
    static ConnectorClientServer m_cToServer;
    
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage:\nGameClient <Player Name>");
            System.exit(1);
        }
        String sPlayerName = args[0];
        // vec2 playerPos = new vec2(Integer.parseInt(args[1]), Integer.parseInt(args[2]));

        m_cToServer = new ConnectorClientServer(sPlayerName);

        m_cToServer.send(GameUtils.GenerateLoginMsg(sPlayerName));
        
        System.exit(0);
    }
}