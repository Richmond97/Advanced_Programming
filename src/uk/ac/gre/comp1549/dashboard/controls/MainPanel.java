
package uk.ac.gre.comp1549.dashboard.controls;

import javax.swing.JPanel;

//This will serve as our super class and should be inherited by the other panel classes to make code more flexible
abstract public class MainPanel extends JPanel {
    
    private int value;
    
    //Abstract methods 
    abstract public void setValue(int value);
    abstract public void setLabel(String label);
    
    //Each sub class will call this to create their graphic
    abstract public void create();
    
    public MainPanel(){
        
    }  
}
