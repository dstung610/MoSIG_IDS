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

}
