/**
 * Created by dosontung on 4/10/18.
 */

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class GFrame extends JPanel
{
    ArrayList<PlayerIcon> playerArrayList = new ArrayList<>();
    PlayerIcon controlPlayer;
    PlayerIcon[] playerList;
    Contents contents = new Contents();
    JFrame frame = new JFrame("Playground");
    int frameWidth = 500;
    int frameHeight = 500;
    int controlAnchorX = frameWidth / 2;
    int controlAnchorY = frameHeight - 200;

    GFrame(Grid grid, PlayerIcon[] playerList)
    {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

        this.playerList = playerList;
        this.contents.setGrid(grid);
        this.contents.setPlayerList(playerList);
        controlPlayer = playerList[0];
        JButton moveButtonLeft = new JButton("Left");
        JButton moveButtonRight = new JButton("Right");
        JButton moveButtonUp = new JButton("Up");
        JButton moveButtonDown = new JButton("Down");

        moveButtonLeft.setBounds(controlAnchorX - 50, controlAnchorY, 50, 15);
        moveButtonRight.setBounds(controlAnchorX + 50, controlAnchorY, 50, 15);
        moveButtonUp.setBounds(controlAnchorX, controlAnchorY - 50, 50, 15);
        moveButtonDown.setBounds(controlAnchorX, controlAnchorY + 50, 50, 15);

        moveButtonLeft.addActionListener(moveLeft);
        moveButtonRight.addActionListener(moveRight);
        moveButtonUp.addActionListener(moveUp);
        moveButtonDown.addActionListener(moveDown);


//        frame.getContentPane().add(moveButtonLeft);
//        frame.getContentPane().add(moveButtonRight);
//        frame.getContentPane().add(moveButtonUp);
//        frame.getContentPane().add(moveButtonDown);
//
        /* Adding Panel to Frame */
        frame.getContentPane().add(this.contents);

        /* Setting Frame */
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(frameWidth, frameHeight);
        frame.setVisible(true);
    }

    ActionListener moveLeft = new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            controlPlayer.moveLeft();
            frame.repaint();
            System.out.println("Button Pressed: " + controlPlayer.getPosX());
        }
    };

    ActionListener moveRight = new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            controlPlayer.moveRight();
            frame.repaint();
            System.out.println("Button Pressed: " + controlPlayer.getPosX());
        }
    };
    ActionListener moveUp = new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            controlPlayer.moveUp();
            frame.repaint();
            System.out.println("Button Pressed: " + controlPlayer.getPosX());
        }
    };
    ActionListener moveDown = new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            controlPlayer.moveDown();
            frame.repaint();
            System.out.println("Button Pressed: " + controlPlayer.getPosX());
        }
    };

    public void updatePlayerList(PlayerIcon[] pl)
    {
        this.playerList = pl;
        frame.getContentPane().add(new Contents(this.contents.grid, pl));
        frame.revalidate();
        frame.repaint();
        for (int i = 0; i < pl.length; i++) System.out.println(pl[i].toString());

    }

    public void update()
    {
        contents.update();
        frame.revalidate();
        frame.repaint();
    }

}