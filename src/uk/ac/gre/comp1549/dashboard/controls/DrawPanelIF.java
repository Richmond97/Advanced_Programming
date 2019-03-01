package uk.ac.gre.comp1549.dashboard.controls;

//Interface to be used by all classes that draw items onto screen
public interface DrawPanelIF {
    
    //Classes that implement should overwrrite these
    public void setValue(int val);
    public void getValue();  
}
