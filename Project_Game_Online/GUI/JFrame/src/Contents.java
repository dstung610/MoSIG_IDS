import javax.swing.*;
import java.awt.*;

/**
 * Created by dosontung on 4/11/18.
 */
public class Contents extends JComponent
{
    Player player;
    Grid grid;

    public Contents(Grid g, Player p)
    {
        this.player = p;
        this.grid = g;
    }

    public void paint(Graphics g)
    {
        int size = this.grid.size;
        int cellSize = this.grid.cellSize;
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                g.drawRect(i * cellSize, j * cellSize, cellSize, cellSize);
            }
        }
        g.setColor(Color.BLACK);
        g.fillOval(player.getPosX(), player.getPosY(), 10, 10);
//        g.setColor(Color.blue);
//        g.fillOval(player.getPosX(), player.getPosY() + 10, 10, 10);
    }
}
