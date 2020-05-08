import java.awt.*;
import javax.swing.*;

public class ImagePanel2 extends JPanel
{
    String fileName = "noBat.png";
    ImageIcon nobosh = new ImageIcon(fileName);
    @Override
    public void paintComponent(Graphics g) {
        Image image = nobosh.getImage();
        g.drawImage(image, 3, 4, this);
    }
}
