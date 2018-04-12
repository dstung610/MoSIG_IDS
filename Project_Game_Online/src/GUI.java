import javax.swing.*;

/**
 * Created by dosontung on 4/11/18.
 */

public class GUI
{
    static ConnectorNodes cToNode;

    public static void main(String[] args)
    {
        PlayerIcon p1 = new PlayerIcon(300, 100);
        PlayerIcon[] playerList = {p1};
        Grid g1 = new Grid(GameUtils.s_SettingZoneSize);
        GFrame frame1 = new GFrame(g1, playerList);

        cToNode = new ConnectorNodes("GUI", "node_" + args[0]);

        while (true)
        {
            // GameUtils.LOG(".");
            String msg = cToNode.getMessage();
//            String msg = "1000000000000000000000000000000000000000001000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
            if (msg != null)
            {

                g1.map = msg.split("");
//                GameUtils.LOG("RR " + msg);
//                int id = msg.indexOf("1");
//                int x = id / GameUtils.s_SettingZoneSize;
//                int y = id % GameUtils.s_SettingZoneSize;
//                playerList[0].setPosX(x * 10);
//                playerList[0].setPosY(y * 10);
//                if (id == -1)
//                {
//                    playerList[0].setPosX(-10);
//                    playerList[0].setPosY(-10);
//
//                }
            }

            frame1.update();
            try
            {
                Thread.sleep(30);

                // i++;
                // if (i == 3)
                // {
                //     p4.speak = true;

                // }
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }

        }

    }
}
