import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
class Basement {
    String userName;
    static int userEnergy = 3;
    static int maxEnergy = 3;
    static int brain = 2;
    static int research = 0;
    ButtonGroup actions;
    ButtonGroup dayActions;
    JPanel actionPanel;
    JPanel dayPanel;
    ImageGuyPanel image;
    JLabel nametag;
    JFrame userFrame;
    static JButton fireCounter;

    public static int days;
    public static int rocks=0;
    public static int wood=15;
    public static int food = 20;
    public static int ratCap;
    public static int totRat=0;
    public static int books=0;
    public static int fireHeight=36;
    
    public static boolean hasKey = false;


    class chooseAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            if(userEnergy>0){
                if(actions.getSelection().getActionCommand().equals("1")) {
                    doResearch();
                }
                if(actions.getSelection().getActionCommand().equals("2")) {
                    read();
                }
                if(actions.getSelection().getActionCommand().equals("3")) {
                    buildRat();
                }
            } else {System.out.println("You're too tired. You just wanna go to bed.");}
            updateFire();
            userFrame.dispose();
        }
    }

    void userAction() {
        userFrame = new JFrame();
        JRadioButton one = new JRadioButton("Research. (Intelligence="+brain+")");
        one.setActionCommand("1");
        JRadioButton two = new JRadioButton("Expand Brain. (Books="+0+"/1)");
        two.setActionCommand("2");
        JRadioButton three = new JRadioButton("Build Rat. (Schematics="+research+"/8)");
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
        int rockLoss = (int)(rocks*(0.6*Math.random()));
        int woodLoss = (int)(2+(6*Math.random()));
        if(woodLoss>wood){woodLoss=wood;}
        rocks = rocks-rockLoss;
        wood = wood-woodLoss;

        if(food>=0) {
            System.out.println("There's light coming from the ceiling cracks.");
            System.out.println("It must be morning.");
            System.out.println((int)(1+totRat)+" rations consumed.");
            System.out.println(rockLoss+" jewels have disappeared from the storehouse.");
            System.out.println(woodLoss+" wood bured to keep the fire going.");
            System.out.println("You've survived another day in the basement.");
        } else if (totRat>0){
            System.out.println("Uh oh... It's feeding time and we're out of rations.");
            System.out.println("Well, you know what that means.");
            System.out.println("Your rats eat you alive.");
            System.out.println("You are dead.");
            menuFrame.dispose();
        } else {
            System.out.println("You have no food.");
            System.out.println("And no rats to get any.");
            System.out.println("Well, that's unfortunate.");
            System.out.println("You are dead.");
            menuFrame.dispose();
        }
    }

    static void updateFire() {
        fireHeight = fireHeight-(int)(3+(4*Math.random()));
        System.out.println("Fire: "+(int)(100*fireHeight/40)+"%");
    }
    
    class autoCheckFire implements ActionListener {
        public void actionPerformed (ActionEvent event) {
            if(fireHeight<1) {
            System.out.println("The fire went out.");
            System.out.println("It's dark.");
            System.out.println("You are scared.");
            System.out.println("You hear whispers. Getting closer.");
            System.out.println("You are dead.");
            menuFrame.dispose();
        }
    }
}
    
    void doResearch() {
        userEnergy--;
        int yield=(brain)+(int)(2*Math.random()*brain);
        System.out.println("Your research yielded "+yield+" schematics.");
        Basement.research=Basement.research+yield;
    }

    void read() {
        if(books>0) {
            userEnergy--;
            brain++;
            System.out.println("You read one of the shopkeeper's books.");
            System.out.println("Your mind fills with heretical ideas.");
            books--;
        } else {System.out.println("You have no books to read.");}
    }

    void buildRat() {
        if(research>7) {
            userEnergy--;
            rats.add(new Rat());
            research=research-8;
        } else {System.out.println("You don't have the resources for that.");}
    }

    JFrame menuFrame;
    class workshopChoose implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            userAction();
        }
    }
    class villageChoose implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            ratVillage();
        }
    }
    class shopChoose implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            Shopkeep.shop();
        }
    }

    class inventoryChoose implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {

            System.out.println("Day: "+days);
            System.out.println("Fire: "+(int)(100*fireHeight/40)+"%");
            System.out.println("Wood: "+wood);
            System.out.println("Jewels: "+rocks);
            System.out.println("Rations: "+food);
            System.out.println("Books: "+books);
            System.out.println("Schematics: "+research);
        }
    }
    class feedFire implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            if(wood>5){
                System.out.println("-5 wood.");
                wood = wood-5;
                fireHeight=fireHeight+(int)(6+(6*Math.random()));
                if (fireHeight>40) {fireHeight=40;}
                System.out.println("Fire height: "+(int)(100*fireHeight/40)+"%");
            } else {System.out.println("You don't have enough wood.");}
        }
    }
    class dayChoose implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            advanceTime();
        }
    }

    public void menu() {
        menuFrame = new JFrame();
        JButton workshopButton = new JButton("Your Workshop.");
        JButton villageButton = new JButton("The Villiage of Rat.");
        JButton shopButton = new JButton("The Mumbling Shopkeeper.");
        actionPanel = new JPanel();
        actionPanel.setLayout(new BorderLayout());
        actionPanel.add(BorderLayout.NORTH,workshopButton);
        actionPanel.add(BorderLayout.CENTER,villageButton);
        actionPanel.add(BorderLayout.SOUTH,shopButton);
        workshopButton.addActionListener(new workshopChoose());
        villageButton.addActionListener(new villageChoose());
        shopButton.addActionListener(new shopChoose());

        JButton inventoryButton = new JButton("Check Inventory.");
        fireCounter = new JButton("Kindle Fire.");
        JButton dayButton = new JButton("Go to Sleep.");
        dayPanel = new JPanel();
        dayPanel.setLayout(new BorderLayout());
        dayPanel.add(BorderLayout.NORTH,inventoryButton);
        dayPanel.add(BorderLayout.CENTER,fireCounter);
        dayPanel.add(BorderLayout.SOUTH,dayButton);
        inventoryButton.addActionListener(new inventoryChoose());
        fireCounter.addActionListener(new feedFire());
        dayButton.addActionListener(new dayChoose());

        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.add(BorderLayout.NORTH, new JLabel("Where do you go?"));
        menuFrame.add(BorderLayout.CENTER, actionPanel);
        menuFrame.add(BorderLayout.EAST, dayPanel);
        menuFrame.setSize(375,150);
        menuFrame.setVisible(true);
        
        javax.swing.Timer fireTimer = new javax.swing.Timer(2, new autoCheckFire());
        fireTimer.start();
    }

    JList<String> yourRats;
    JFrame villageFrame;

    class selectRat implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent le) {
            for(Rat c: rats) {
                if (c.name.equals(yourRats.getSelectedValue())) {
                    c.ratAction();
                    villageFrame.dispose();
                }
            }
        }
    }

    void ratVillage() {
        JLabel prompt = new JLabel("The Village of Rat. Population: "+totRat);
        villageFrame = new JFrame();
        String[] ratNames = new String[rats.size()];
        for(int i=0;i<rats.size();i++) {
            ratNames[i]=rats.get(i).name;
        }
        yourRats = new JList<String>(ratNames);
        yourRats.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        yourRats.addListSelectionListener(new selectRat());
        JScrollPane ratScroll=new JScrollPane(yourRats);
        villageFrame.add(BorderLayout.NORTH, prompt);
        villageFrame.add(BorderLayout.CENTER,ratScroll);
        villageFrame.setSize(375,150);
        villageFrame.setVisible(true);
    }

    public static void main(String[] args) {
        Basement game = new Basement();
        game.menu();
        game.rats.add(new Rat());
    }
}