import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
class Shopkeep {
    public boolean present = false;
    
    static int bookStock = 0;
    static int bookPrice = 0;
    static int jewelPrice = 0;
    static int woodPrice = 0;
    static int keyPrice = 1000000;
    static int gold = 0;
    
    static JFrame shopFrame;
    static JLabel name=new JLabel("Astor Sibellius Shoppithan III");
    static JPanel options;
    
    class bookChoose implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            if(gold>=bookPrice) {
                gold=gold-bookPrice;
                Basement.books++;
            } else {System.out.println("You can't affort that.");}
        }
    }
    class keyChoose implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            if(gold>=keyPrice&&Basement.totRat>=13) {
                gold = gold-keyPrice;
                Basement.hasKey=true;
            } else {System.out.println("Come back when you can pay for it.");}
        }
    }
    class jewelChoose implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            
        }
    }
    
    static void shop() {
    shopFrame = new JFrame();
    ImageShop image = new ImageShop();
    options = new JPanel();
    JButton buyBook = new JButton("Purchase a book ("+bookPrice+" gold per book)");
    JButton buyKey = new JButton("Buy the Key (13 rats and "+keyPrice+" gold)");
    JButton sellRocks = new JButton("Sell  jewels ("+jewelPrice+" gold per jewel)");
    options.setLayout(new BorderLayout());
    options.add(BorderLayout.NORTH,buyBook);
    options.add(BorderLayout.CENTER,buyKey);
    options.add(BorderLayout.SOUTH,sellRocks);
    shopFrame.add(BorderLayout.NORTH, name);
    shopFrame.add(BorderLayout.CENTER, image);
    shopFrame.add(BorderLayout.EAST, options);
    shopFrame.setSize(image.nobosh.getIconWidth()+375,image.nobosh.getIconHeight()+50);
    shopFrame.setVisible(true);
    }
    static void restock() {
        
    }
}