import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
class Shopkeep {
    public static boolean present = false;

    static int bookStock = 0;
    static int bookPrice = 0;
    static int jewelPrice = 0;
    static int keyPrice = 1000000;
    static int gold = 0;
    static int cartVolume = 0;
    static JLabel cartLabel = new JLabel("-"+cartVolume+"-");

    static JFrame shopFrame;
    static JFrame sellingFrame;
    static JLabel name=new JLabel("Astor Sibellius Shoppithan III");
    static JPanel options;

    static class bookChoose implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            if(gold>=bookPrice) {
                gold=gold-bookPrice;
                Basement.books++;
                System.out.println("'Thank you kindly.'");
                System.out.println("+1 Book.");
            } else {System.out.println("'You can't affort that.'");}
            shopFrame.dispose();
        }
    }
    static class keyChoose implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            if(gold>=keyPrice&&Basement.totRat>=13) {
                gold = gold-keyPrice;
                for(int i=Basement.rats.size()-14;i<Basement.rats.size();i++) {
                    Basement.rats.remove(i);
                }
                Basement.hasKey=true;
                System.out.println("'...'");
                System.out.println("'I will enjoy these rats.'");
                System.out.println("'I will enjoy them very much.'");
                System.out.println("+1 Key.");
            } else {System.out.println("'It wouldn't be fun if I gave this to you now.'");}
            shopFrame.dispose();
        }
    }
    static class cartIncrease implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            cartVolume++;
            if(cartVolume>Basement.rocks){cartVolume=0;}
            cartLabel.setText("-"+cartVolume+"-");
        }
    }
    static class cartDecrease implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            cartVolume--;
            if(cartVolume<0){cartVolume=Basement.rocks;}
            cartLabel.setText("-"+cartVolume+"-");
        }
    }
    static class confirm implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            Basement.rocks = Basement.rocks-cartVolume;
            gold=gold+(cartVolume*jewelPrice);
            System.out.println("+"+(cartVolume*jewelPrice)+" Coins.");
            System.out.println("'Ugnhhhh... What beautiful jewels...'");
            sellingFrame.dispose();
            shopFrame.dispose();
        }
    }
    static class jewelChoose implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            sellingFrame = new JFrame();
            JLabel prompt = new JLabel("'...How many can I have?'");
            sellingFrame.add(BorderLayout.NORTH,prompt);

            JButton more = new JButton(">");
            more.addActionListener(new cartIncrease());
            JButton less = new JButton("<");
            less.addActionListener(new cartDecrease());

            JButton confirmPurchase = new JButton("This Many.");
            confirmPurchase.addActionListener(new confirm());

            sellingFrame.add(BorderLayout.CENTER, cartLabel);
            sellingFrame.add(BorderLayout.WEST, less);
            sellingFrame.add(BorderLayout.EAST, more);
            sellingFrame.setSize(300,100);
            sellingFrame.setVisible(true);
        }
    }

    static void shop() {
        shopFrame = new JFrame();
        if(present) {
            ImageShop image = new ImageShop();
            options = new JPanel();
            JButton buyBook = new JButton("Purchase book("+bookStock+"): "+bookPrice+" gold.");
            buyBook.addActionListener(new bookChoose());
            JButton buyKey = new JButton("Purchase key: 13 rats + "+keyPrice+" gold.");
            buyKey.addActionListener(new keyChoose());
            JButton sellRocks = new JButton("Sell  jewels: "+jewelPrice+" gold.");
            sellRocks.addActionListener(new jewelChoose());
            options.setLayout(new BorderLayout());
            options.add(BorderLayout.NORTH,buyBook);
            options.add(BorderLayout.CENTER,buyKey);
            options.add(BorderLayout.SOUTH,sellRocks);
            shopFrame.add(BorderLayout.NORTH, new JLabel("'What are you buying?' (Gold: "+gold+")"));
            shopFrame.add(BorderLayout.CENTER, image);
            shopFrame.add(BorderLayout.SOUTH, name);
            shopFrame.add(BorderLayout.EAST, options);
            shopFrame.setSize(image.nobosh.getIconWidth()+375,image.nobosh.getIconHeight()+50);
        } else {
            shopFrame.add(BorderLayout.NORTH,new JLabel("It seems like there's nobody here."));
            shopFrame.setSize(150,100);
        }
        shopFrame.setVisible(true);
    }

    static void restock() {
        if((20*Math.random())>13) {
            present = true;
            bookStock = (int)(3*Math.random());
            bookPrice = 80+(int)(100*Math.random());
            jewelPrice = 1+(int)(15*Math.random());
            System.out.println("There's a scratching sound coming from the old hut.");
        }
    }
}