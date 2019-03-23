package uk.ac.gre.comp1549.dashboard.interfaces;

//Interface to be used by all classes that draw items onto screen
import java.awt.Graphics2D;

public interface DrawPanelIF {

    public void drawDialEnd(Graphics2D g2, double angle);

    public void drawHand(Graphics2D g2, double angle, double handLength);
}
 