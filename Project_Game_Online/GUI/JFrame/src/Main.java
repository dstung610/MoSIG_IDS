import javax.swing.*;

/**
 * Created by dosontung on 4/11/18.
 */

public class Main
{
    public static void main(String[] args)
    {
        Player p1 = new Player(10, 10);
        Player p2 = new Player(150, 10);
        Player p3 = new Player(100, 30);
        Player p4 = new Player(40, 20);
        Player[] playerList = {p1, p4};
        Player[] playerList2 = {p2, p3, p4, p1};
        Grid g1 = new Grid(32);
        GFrame frame1 = new GFrame(g1, playerList);

        while (true)
        {
            try
            {
                Thread.sleep(2000);
                frame1.updatePlayerList(playerList2);
                p1.randomRun();
                p2.randomRun();
                p3.randomRun();
                p4.randomRun();
                frame1.update();
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }


        }

    }
}
