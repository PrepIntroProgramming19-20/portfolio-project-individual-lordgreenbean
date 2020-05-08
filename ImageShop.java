import java.awt.*;
import javax.swing.*;

public class ImageShop extends JPanel
{
    String fileName = "shop.png";
    ImageIcon nobosh = new ImageIcon(fileName);
    @Override
    public void paintComponent(Graphics g) {
        Image image = nobosh.getImage();
        g.drawImage(image, 3, 4, this);
    }
}
