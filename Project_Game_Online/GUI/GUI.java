import javax.swing.*;

/**
 * Created by dosontung on 4/11/18.
 */

public class GUI
{
    public static void main(String[] args)
    {
        Player p1 = new Player(300, 100);
        Player p2 = new Player(150, 10);
        Player p3 = new Player(100, 30);
        Player p4 = new Player(40, 20);
        Player[] playerList = {p1, p2, p3, p4};
        Grid g1 = new Grid(32);
        GFrame frame1 = new GFrame(g1, playerList);
        
        int i = 0;
        while (true)
        {
            try
            {
                Thread.sleep(4000);
                p1.randomRun();
                p2.randomRun();
                p3.randomRun();
                p4.randomRun();
                frame1.update();
                i++;
                if (i == 3)
                {
                    p4.speak = true;

                }
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }


        }

    }
}
