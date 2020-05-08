import java.awt.*;
import javax.swing.*;

public class ImagePanel extends JPanel
{
    String fileName = "rat.png";
    ImageIcon yosh = new ImageIcon(fileName);
    @Override
    public void paintComponent(Graphics g) {
        Image image = yosh.getImage();
        g.drawImage(image, 3, 4, this);
    }
}