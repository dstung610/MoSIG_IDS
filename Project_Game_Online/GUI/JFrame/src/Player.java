import java.util.Random;

/**
 * Created by dosontung on 4/11/18.
 */

public class Player
{
    private int posX = 0;
    private int posY = 0;
    private int step = 10;

    public int getPosX()
    {
        return posX;
    }

    public int getPosY()
    {
        return posY;
    }

    public void setPosX(int posX)
    {
        this.posX = posX;
    }

    public void setPosY(int posY)
    {
        this.posY = posY;
    }

    Player(int posX, int posY)
    {
        this.posX = posX;
        this.posY = posY;
    }


    public void moveLeft()
    {
        this.setPosX(getPosX() - step);
    }

    public void moveRight()
    {
        this.setPosX(getPosX() + step);
    }

    public void moveUp()
    {
        this.setPosY(getPosY() - step);
    }

    public void moveDown()
    {
        this.setPosY(getPosY() + step);

    }

    public String toString()
    {
        return "(X: " + getPosX() + " - Y:" + getPosY() + ")";
    }

    public void randomRun()
    {
        int max = 4, min = 1;
        int randomDirection = (int) (Math.random() * ((max - min) + 1)) + min;

        switch (randomDirection)
        {
            case 1:
                moveLeft();
                break;
            case 2:
                moveRight();
                break;
            case 3:
                moveUp();
                break;
            case 4:
                moveDown();
                break;
        }

    }

}
