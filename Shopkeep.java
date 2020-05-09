import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
class Shopkeep {
    public static boolean present = false;

    static int bookStock = 0;
    static int bookPrice = 0;
    static int jewelPrice = 0;
    static int keyPrice = 850;
    static int gold = 0;
    static int cartVolume = 0;
    static JLabel cartLabel = new JLabel("        -"+cartVolume+"-");

    static JFrame shopFrame;
    static JFrame sellingFrame;
    static JLabel name=new JLabel("      Astor Sibellius Shoppithan III");
    static JPanel options;
    //The listener for when you choose to buy a book.
    static class bookChoose implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            //if there are no book available
            if(bookStock<1){
                System.out.println("'I have no books for you today.'");
                System.out.println("'What's this one I'm holding?'");
                System.out.println("'You cannot look at this book, small child.'");
            }
            //if you DO have money.
            else if(gold>=bookPrice) {
                gold=gold-bookPrice;
                Basement.books++;
                System.out.println("'Thank you kindly.'");
                System.out.println("+1 Book.");
                bookStock--;
            }
            //when you DON'T.
            else {System.out.println("'You can't affort that.'");}
            shopFrame.dispose();
        }
    }
    //When you choose to buy the key.
    static class keyChoose implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            if(gold>=keyPrice&&Basement.totRat>=13) {
                gold = gold-keyPrice;
                //removes rats from earliest to more recent.
                for(int i=0;i<13;i++) {
                    System.out.println("The shopkeeper takes "+Basement.rats.get(0).name+".");
                    Basement.rats.remove(0);
                    Basement.totRat--;
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
    //Increasing how much you want to sell.
    static class cartIncrease implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            cartVolume++;
            if(cartVolume>Basement.rocks){cartVolume=0;}
            cartLabel.setText("        -"+cartVolume+"-");
        }
    }
    //decreasing how much you want to sell.
    static class cartDecrease implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            cartVolume--;
            if(cartVolume<0){cartVolume=Basement.rocks;}
            cartLabel.setText("        -"+cartVolume+"-");
        }
    }
    //confirming how much you want to sell.
    static class confirm implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            Basement.rocks = Basement.rocks-cartVolume;
            gold=gold+(cartVolume*jewelPrice);
            System.out.println("+"+(cartVolume*jewelPrice)+" Coins.");
            System.out.println("'Ugnhhhh... What beautiful jewels...'");
            sellingFrame.dispose();
            shopFrame.dispose();
            cartVolume = 0;
        }
    }
    //GUI that comes up when you want to sell jewels.
    static class jewelChoose implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            sellingFrame = new JFrame();
            JLabel prompt = new JLabel("        '...How many can I have?'");
            sellingFrame.add(BorderLayout.NORTH,prompt);
            //buttons for selling more or less.
            JButton more = new JButton(">");
            more.addActionListener(new cartIncrease());
            JButton less = new JButton("<");
            less.addActionListener(new cartDecrease());

            JButton confirmPurchase = new JButton("This Many.");
            confirmPurchase.addActionListener(new confirm());

            sellingFrame.add(BorderLayout.CENTER, cartLabel);
            sellingFrame.add(BorderLayout.WEST, less);
            sellingFrame.add(BorderLayout.EAST, more);
            sellingFrame.add(BorderLayout.SOUTH, confirmPurchase);
            sellingFrame.setSize(200,100);
            sellingFrame.setVisible(true);
        }
    }
    //The GUI that comes up when you select the shopkeeper.
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
            shopFrame.add(BorderLayout.NORTH, new JLabel("     'What are you buying?' (Gold: "+gold+")"));
            shopFrame.add(BorderLayout.CENTER, image);
            shopFrame.add(BorderLayout.SOUTH, name);
            shopFrame.add(BorderLayout.EAST, options);
            shopFrame.setSize(image.nobosh.getIconWidth()+375,image.nobosh.getIconHeight()+50);
            //when the shopkeeper isn't there.
        } else {
            shopFrame.add(BorderLayout.NORTH,new JLabel("It seems like there's nobody here."));
            shopFrame.setSize(500,100);
        }
        shopFrame.setVisible(true);
    }
    //decides on that day's stock and prices for the different items.
    //Also decided on whether the man himself is present.
    static void restock() {
        if((20*Math.random())>10.5) {
            present = true;
            bookStock = (int)(3*Math.random());
            bookPrice = 80+(int)(80*Math.random());
            jewelPrice = 1+(int)(15*Math.random());
            System.out.println("    ! There's a scratching sound coming from the old hut.");
        } else {
            present = false;
        }
    }
}