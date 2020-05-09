import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
class Basement {
    static int userEnergy = 3;
    static int maxEnergy = 3;
    static int brain = 2;
    static int research = 0;
    //declaring these early so I don't run into null pointers.
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
    public static int totRat=0;
    public static int books=0;
    public static int fireHeight=36;

    public static boolean hasKey = false;
    //This is so you don't get in an endless loop at the very end.
    public static boolean stopTheGameWhenItIsOver = true;

    //This constructor isn't really necessary but I dunno it's nice to have.
    public Basement() {
        userEnergy = 3;
        maxEnergy = 3;
        brain = 2;
        research = 0;
        days=0;
        rocks=0;
        wood=15;
        food = 20;
        totRat=0;
        books=0;
        fireHeight=36;
        hasKey = false;
        stopTheGameWhenItIsOver = true;
    }
    //This is for the radio buttons for choosing what the child is going to do.
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
            //The fire goes down after all the boy's actions.
            updateFire();
            userFrame.dispose();
        }
    }
    //The GUI that comes up when you decide to perform an action as the boy.
    void userAction() {
        userFrame = new JFrame();
        JRadioButton one = new JRadioButton("Research. (Intelligence="+brain+")");
        one.setActionCommand("1");
        JRadioButton two = new JRadioButton("Expand Brain. (Books="+books+"/1)");
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
        nametag = new JLabel("You: The Small Child (Energy: "+userEnergy+")");
        userFrame.add(BorderLayout.NORTH, nametag);
        userFrame.add(BorderLayout.CENTER, image);
        userFrame.add(BorderLayout.EAST, actionPanel);
        userFrame.setSize(image.nobosh.getIconWidth()+375,image.nobosh.getIconHeight()+50);
        userFrame.setVisible(true);
    }
    //I don't know why this is so low in the code, really.
    static ArrayList<Rat> rats = new ArrayList<Rat>();
    //This is the command to reset all of the intra-day stats like energy
    //And the shopkeeper's stuff. Also there's some movement in your resources.
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
        System.out.println("");
        System.out.println("");
        System.out.println("");
        //These are all the ways a day can end: normally and then a bunch where
        //you die because of no food.
        if(food>=0) {
            System.out.println("There's light coming from the ceiling cracks.");
            System.out.println("It must be morning.");
            System.out.println((int)(1+totRat)+" rations consumed.");
            System.out.println(rockLoss+" jewels have disappeared from the storehouse.");
            System.out.println(woodLoss+" wood bured to keep the fire going.");
            System.out.println("You've survived another day in the basement.");
            System.out.println("   ~~INVENTORY~~");
            System.out.println("Day: "+days);
            System.out.println("Fire: "+(int)(100*fireHeight/40)+"%");
            System.out.println("Wood: "+wood);
            System.out.println("Jewels: "+rocks);
            System.out.println("Rations: "+food);
            System.out.println("Books: "+books);
            System.out.println("Schematics: "+research);
            System.out.println("Gold Coind: "+Shopkeep.gold);
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

        Shopkeep.restock();
    }
    //This is the method to make the fire gradually go down.
    //(makes wood a resource you need to think about.)
    static void updateFire() {
        fireHeight = fireHeight-(int)(3+(4*Math.random()));
        System.out.println("Fire: "+(int)(100*fireHeight/40)+"%");
    }

    //So this is a timer that's used both to check if the fire goes out and
    //to check if the user has the key at the very end of the game.
    //In one of these scenarios the user dies, in the other one he comes
    // out of the basement.
    class autoCheckFire implements ActionListener {
        public void actionPerformed (ActionEvent event) {
            if(fireHeight<1&&stopTheGameWhenItIsOver) {
                System.out.println("");
                System.out.println("");
                System.out.println("");
                System.out.println("The fire went out.");
                System.out.println("It's dark.");
                System.out.println("You are scared.");
                System.out.println("You hear whispers. Getting closer.");
                System.out.println("You are dead.");
                menuFrame.dispose();
                stopTheGameWhenItIsOver=false;
            }
            if (hasKey&&stopTheGameWhenItIsOver) {
                System.out.println("");
                System.out.println("");
                System.out.println("");
                System.out.println("So you did it.");
                System.out.println("You sold your rats.");
                System.out.println("All for a key to go outside?");
                System.out.println("You don't remember what outside is anymore.");
                System.out.println("Did your friends mean so little to you?");
                System.out.println("Well, I'm sure they'd understand.");
                System.out.println("It's not like they wanna stay in this basement");
                System.out.println("with that creepy shopkeeper any more than you did.");
                System.out.println("But that doesn't matter.");
                System.out.println("All of that is behind you.");
                System.out.println("You escaped the basement.");
                menuFrame.dispose();
                stopTheGameWhenItIsOver=false;
            }
        }
    }
    //This is how the user gets the schematics which are used to create rats.
    void doResearch() {
        userEnergy--;
        int yield=(brain)+(int)(2*Math.random()*brain);
        System.out.println("Your research yielded "+yield+" schematics.");
        Basement.research=Basement.research+yield;
    }
    //This both boosts the rate at which the player can generate schematics
    //for rats and raises the maximum stats of the new rats themselves.
    void read() {
        if(books>0) {
            userEnergy--;
            brain++;
            System.out.println("You read one of the shopkeeper's books.");
            System.out.println("Your mind fills with heretical ideas.");
            books--;
            Rat.tech++;
        } else {System.out.println("You have no books to read.");}
    }
    //Birth a rat. Pretty self explanatory, really. How does the kid do it?
    //Uhhh....... I'm sure it's an important lore thing that I shouldn't give away.
    void buildRat() {
        if(research>7) {
            userEnergy--;
            rats.add(new Rat());
            research=research-8;
        } else {System.out.println("You don't have the resources for that.");}
    }

    JFrame menuFrame;
    
    //These three classes correspond to the left buttons on the menu GUI.
    //They bring up the user/workshop GUI, the rat GUI, and the shopkeeper GUI,
    //respectively.
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
    //Just displays what you have in your inventory, nothing so special.
    class inventoryChoose implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            System.out.println("   ~~INVENTORY~~");
            System.out.println("Day: "+days);
            System.out.println("Fire: "+(int)(100*fireHeight/40)+"%");
            System.out.println("Wood: "+wood);
            System.out.println("Jewels: "+rocks);
            System.out.println("Rations: "+food);
            System.out.println("Books: "+books);
            System.out.println("Schematics: "+research);
            System.out.println("Gold Coind: "+Shopkeep.gold);
        }
    }
    //takes five wood to restore the fire by a random amount.
    class feedFire implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            if(wood>=5){
                System.out.println("-5 wood.");
                wood = wood-5;
                fireHeight=fireHeight+(int)(6+(6*Math.random()));
                if (fireHeight>40) {fireHeight=40;}
                System.out.println("Fire height: "+(int)(100*fireHeight/40)+"%");
            } else {System.out.println("You don't have enough wood.");}
        }
    }
    //The listener for the "done for the day" button.
    class dayChoose implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            advanceTime();
        }
    }
    //this is the centeral hub of the game. The buttons on the left panel
    // will bring up GUIs that you can interact with to perform tasks, while
    // the buttons on the left will perform functions in the console.
    public void menu() {
        menuFrame = new JFrame();
        JButton workshopButton = new JButton("Your Workshop.");
        JButton villageButton = new JButton("The Villiage of Rat.");
        JButton shopButton = new JButton("The Shopkeeper's Hut.");
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
    //So you shouldn't name rats the same thing because this goes through
    //the list in order until it gets to the one with THE NAME you selected,
    //which will not necessarily be the rat that you selected, if you have
    //multiple with the same name. Put simply, if you have two rats with the
    //same name you'll never be able to choose the later of the two.
    class selectRat implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent le) {
            for(Rat c: rats) {
                //also don't name your rat " (asleep)".
                if (c.name.equals(yourRats.getSelectedValue().replace(" (asleep)",""))) {
                    c.ratAction();
                    villageFrame.dispose();
                }
            }
        }
    }
    //Shows all of the rats that you have available.
    void ratVillage() {
        JLabel prompt = new JLabel("The Village of Rat. Population: "+totRat);
        villageFrame = new JFrame();
        String[] ratNames = new String[rats.size()];
        //lets you know if the rats have battery.
        for(int i=0;i<rats.size();i++) {
            if(rats.get(i).charged) {
                ratNames[i]=rats.get(i).name;
            } else {
                ratNames[i]=rats.get(i).name+" (asleep)";
            }
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
    //Just running the code.
    public static void main(String[] args) {
        Basement game = new Basement();
        game.menu();
        game.rats.add(new Rat());
    }
}