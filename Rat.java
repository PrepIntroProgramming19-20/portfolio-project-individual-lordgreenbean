import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
class Rat {
    String name;
    boolean charged = false;
    int str;
    int spd;
    //tech is like how high their stats can go.
    static int tech = 4;

    JTextField text;
    JFrame ratFrame;
    ImagePanel image;
    ImagePanel2 noBattery;
    JLabel nametag;
    JLabel message;
    JPanel actionPanel;
    ButtonGroup actions;

    //The class for naming a rat.
    class nameRat implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            name = text.getText();
            ratFrame.dispose();
            System.out.println("The Village of Rat accepts "+name+".");
            Basement.totRat++;
        }
    }
    //The listener for the buttons for deciding what task the rat should do.
    class chooseAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            if(charged){
                if(actions.getSelection().getActionCommand().equals("1")) {
                    gatherWood();
                    charged = false;
                }
                if(actions.getSelection().getActionCommand().equals("2")) {
                    gatherStone();
                    charged = false;
                }
                if(actions.getSelection().getActionCommand().equals("3")) {
                    hunt();
                    charged = false;
                }
            } else {System.out.println(name + " is out of batteries for the day.");}
            ratFrame.dispose();
        }
    }
    //Blank constructor for rat, brings up a GUI where the user can enter a name.
    public Rat() {
        charged = false;
        str = 1+(int)(tech*Math.random());
        spd = 1+(tech-str);
        ratFrame = new JFrame();
        text = new JTextField("Precious One");
        text.addActionListener(new nameRat());
        ratFrame.add(BorderLayout.CENTER, text);
        ratFrame.add(BorderLayout.NORTH, new JLabel("Name your new rat."));
        JLabel baseStats = new JLabel("Strength: "+str+"   Speed: "+spd);
        ratFrame.add(BorderLayout.SOUTH, baseStats);
        ratFrame.setSize(300,100);
        ratFrame.setVisible(true);
    }
    //used for testing purposes only.
    public Rat(String nomen) {
        charged = false;
        str = 1+(int)(tech*Math.random());
        spd = 1+(tech-str);
        name=nomen;
        System.out.println("The Village of Rat accepts "+name+".");
        Basement.totRat++;
    }
    //This is the GUI that comes up when you choose a rat.
    void ratAction() {

        ratFrame = new JFrame();
        JRadioButton one = new JRadioButton("Gather wood. (Strength="+str+")");
        one.setActionCommand("1");
        JRadioButton two = new JRadioButton("Mine jewels. (Strength="+str+")");
        two.setActionCommand("2");
        JRadioButton three = new JRadioButton("Hunt for food. (Speed="+spd+")");
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
        image = new ImagePanel();
        noBattery = new ImagePanel2();
        nametag = new JLabel(name);
        ratFrame.add(BorderLayout.NORTH, nametag);
        if(charged){
            ratFrame.add(BorderLayout.CENTER, image);
        }else{ratFrame.add(BorderLayout.CENTER, noBattery);}
        ratFrame.add(BorderLayout.EAST, actionPanel);
        ratFrame.setSize(image.yosh.getIconWidth()+375,image.yosh.getIconHeight()+50);
        ratFrame.setVisible(true);
    }
    //This tells you how much of which resources the rats come back with,
    String message(int number, String units) {
        return (name+" has returned with "+number+" "+units+".");
    }
    //These three are the tasks that the rat can perform.
    void gatherWood() {
        int yield=(2*str)+(int)(3*Math.random()*str);
        System.out.println(message(yield, "wood"));
        Basement.wood=Basement.wood+yield;
        //This one doesn't update fire because killing somebody because their
        //   fire goes out while they're getting wood for it just seems mean.
    }

    void gatherStone() {
        int yield=(2*str)+(int)(3*Math.random()*str);
        System.out.println(message(yield, "jewels"));
        Basement.rocks=Basement.rocks+yield;
        Basement.updateFire();
    }

    void hunt() {
        int yield=(2*spd)+(int)(Math.random()*spd);
        System.out.println(message(yield, "food"));
        Basement.food=Basement.food+yield;
        Basement.updateFire();
    }
}