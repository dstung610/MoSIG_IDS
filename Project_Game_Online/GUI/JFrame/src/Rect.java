import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Rect extends JPanel
{
    public void paint(Graphics g)
    {
        g.drawRect(10, 10, 100, 100);
    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("JavaTutorial.net");
        frame.getContentPane().add(new Rect());
        frame.setSize(300, 300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }
}	