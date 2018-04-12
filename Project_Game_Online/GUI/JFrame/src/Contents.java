import javax.swing.*;
import java.awt.*;

/**
 * Created by dosontung on 4/11/18.
 */
public class Contents extends JComponent
{
    Player[] playerList;
    Grid grid;

    public Contents(){};
    public Contents(Grid g, Player[] p)
    {
        this.playerList = p;
        this.grid = g;
    }

    public void setPlayerList(Player[] playerList)
    {
        this.playerList = playerList;
    }

    public void setGrid(Grid grid)
    {
        this.grid = grid;
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
        for (int k = 0; k < playerList.length; k++)
        {
            g.setColor(Color.BLACK);
            g.fillOval(playerList[k].getPosX(), playerList[k].getPosY(), 10, 10);
        }

    }
}
