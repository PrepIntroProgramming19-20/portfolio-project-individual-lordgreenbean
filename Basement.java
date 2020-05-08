import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
class Basement {
    String userName;
    int userEnergy;
    int maxEnergy = 3;
    int brain = 2;
    int research = 0;
    ButtonGroup actions;
    JPanel actionPanel;
    ImageGuyPanel image;
    JLabel nametag;

    public static int days;
    public static int rocks=0;
    public static int wood=0;
    public static int food = 20;
    public static int ratCap;
    public static int totRat=0;
    public static int books=0;

    JFrame userFrame;
    void userAction() {
        userEnergy--;
        userFrame = new JFrame();
        JRadioButton one = new JRadioButton("Research. (Intelligence="+brain+")");
        one.setActionCommand("1");
        JRadioButton two = new JRadioButton("Expand Brain. (Books="+0+")");
        two.setActionCommand("2");
        JRadioButton three = new JRadioButton("Build Rat. (Schematics="+research+")");
        three.setActionCommand("3");
        actions = new ButtonGroup();
        actionPanel = new JPanel();
        actionPanel.setLayout(new BorderLayout());
        actions.add(one);
        actions.add(two);
        actions.add(three);
        actionPanel.add(BorderLayout.NORTH,one);
        actionPanel.add(BorderLayout.CENTER,two);
        actionPanel.add(BorderLayout.SOUTH,three);
        one.addActionListener(new chooseAction());
        two.addActionListener(new chooseAction());
        three.addActionListener(new chooseAction());
        userFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        image = new ImageGuyPanel();
        nametag = new JLabel("You: The Small Child");
        userFrame.add(BorderLayout.NORTH, nametag);
        userFrame.add(BorderLayout.CENTER, image);
        userFrame.add(BorderLayout.EAST, actionPanel);
        userFrame.setSize(image.nobosh.getIconWidth()+375,image.nobosh.getIconHeight()+50);
        userFrame.setVisible(true);
    }

    ArrayList<Rat> rats = new ArrayList<Rat>();
    JList<Rat> army;
    void advanceTime() {
        for(Rat c: rats) {
            c.charged = true;
        }
        userEnergy = maxEnergy;
        days++;
        food = food - (1+totRat);
    }

    public void menu() {

    }

    public static void main(String[] args) {
        Rat r1 = new Rat();
        //r1.ratAction();
    }
}