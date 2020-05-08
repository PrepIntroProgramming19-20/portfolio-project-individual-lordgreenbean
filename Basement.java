import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
class Basement {
    String userName;
    int userEnergy;
    int maxEnergy = 3;
    void userAction() {
        userEnergy--;
        //GUI WITH ACTIONS
    }
    public static int days;
    public static int rocks=0;
    public static int wood=0;
    public static int food = 20;
    public static int ratCap;
    public static int totRat=0;
    
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