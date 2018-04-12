import javax.swing.*;
import java.awt.*;

/**
 * Created by dosontung on 4/11/18.
 */
public class Contents extends JComponent
{
    PlayerIcon[] playerList;
    Grid grid;

    public Contents()
    {
    }

    ;

    public Contents(Grid g, PlayerIcon[] p)
    {
        this.playerList = p;
        this.grid = g;
    }

    public void setPlayerList(PlayerIcon[] playerList)
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
        g.setColor(Color.lightGray);
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                g.drawRect(i * cellSize, j * cellSize, cellSize, cellSize);
            }
        }

        for (int k = 0; k < playerList.length; k++)
        {
            if (playerList[k].speak)
            {
                g.setColor(Color.RED);
                g.fillOval(playerList[k].getPosX(), playerList[k].getPosY(), 10, 10);
                playerList[k].speak = false;
            } else
            {
                g.setColor(Color.BLACK);
                g.fillOval(playerList[k].getPosX(), playerList[k].getPosY(), 10, 10);
            }
        }
        // System.out.println("done paint");

    }
}
