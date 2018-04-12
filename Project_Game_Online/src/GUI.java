import javax.swing.*;

/**
 * Created by dosontung on 4/11/18.
 */

public class GUI {
    static ConnectorNodes cToNode;

    public static void main(String[] args) {
        PlayerIcon p1 = new PlayerIcon(300, 100);
        // PlayerIcon p2 = new PlayerIcon(150, 10);
        // PlayerIcon p3 = new PlayerIcon(100, 30);
        // PlayerIcon p4 = new PlayerIcon(40, 20);
        PlayerIcon[] playerList = { p1 };//, p2, p3, p4};
        Grid g1 = new Grid(GameUtils.s_SettingZoneSize);
        GFrame frame1 = new GFrame(g1, playerList);

        cToNode = new ConnectorNodes("GUI", "node_" + args[0]);

        int i = 0;
        while (true) {
            // GameUtils.LOG(".");
            String msg = cToNode.getMessage();
            // playerList[0].setPosX(-100);
            // playerList[0].setPosY(-100);
            if (msg != null) {
                GameUtils.LOG("RR " + msg);
                int id = msg.indexOf("1");
                int x = id / GameUtils.s_SettingZoneSize;
                int y = id % GameUtils.s_SettingZoneSize;
                playerList[0].setPosX(x * 10);
                playerList[0].setPosY(y * 10);
                if (id == -1)
                {
                    playerList[0].setPosX(- 10);
                    playerList[0].setPosY(- 10);
    
                }
            }

            frame1.update();
            try {
                Thread.sleep(30);

                // i++;
                // if (i == 3)
                // {
                //     p4.speak = true;

                // }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}
