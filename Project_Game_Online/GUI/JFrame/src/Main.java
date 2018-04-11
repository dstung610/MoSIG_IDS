import javax.swing.*;

/**
 * Created by dosontung on 4/11/18.
 */

public class Main
{
    public static void main(String[] args)
    {
        Player p1 = new Player(10, 10);
        Grid g1 = new Grid(32);
        new GFrame(g1, p1);
    }
}
