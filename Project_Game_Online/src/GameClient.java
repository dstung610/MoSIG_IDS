public class GameClient
{
    static Connector m_cToServer;
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage:\nGameServer <Server Name>");
            System.exit(1);
        }
        String  = args[0];
        vec2 playerPos = new vec2(Integer.parseInt(args[1]), Integer.parseInt(args[2]));

        // Player player = new Player(playerName, playerPos);

        
        System.exit(0);
    }
}