/**
 * Created by dosontung on 4/10/18.
 */

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class GFrame extends JPanel
{
    ArrayList<Player> playerArrayList = new ArrayList<>();
    Player controlPlayer;
    JFrame frame = new JFrame("Playground");
    int frameWidth = 1000;
    int frameHeight = 800;
    int controlAnchorX = frameWidth / 2;
    int controlAnchorY = frameHeight - 200;

    GFrame(Grid grid, Player player)
    {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

        controlPlayer = player;
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

        frame.getContentPane().add(moveButtonLeft);
        frame.getContentPane().add(moveButtonRight);
        frame.getContentPane().add(moveButtonUp);
        frame.getContentPane().add(moveButtonDown);

//        JPanel contentArea = new JPanel();
//        JPanel controlArea = new JPanel();
//        contentArea.add(new Contents(grid,player));
//        container.add(contentArea);
//        container.add(controlArea);

//        String[] playerlist = {"Bird", "Cat", "Dog", "Rabbit", "Pig"};
//        JComboBox petList = new JComboBox(playerlist);
//        petList.setSelectedIndex(4);
//        petList.addActionListener(petList);
//        frame.getContentPane().add(petList);

        /* Adding Panel to Frame */
        frame.getContentPane().add(new Contents(grid, player));
//        frame.getContentPane().add(container);


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


}