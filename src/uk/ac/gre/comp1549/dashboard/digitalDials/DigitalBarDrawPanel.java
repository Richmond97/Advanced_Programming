package uk.ac.gre.comp1549.dashboard.digitalDials;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import uk.ac.gre.comp1549.dashboard.interfaces.MyValues;

/**
 * BarDrawPanel. Draw a horizontal bar indicator and show its current value
 * @author The Emperor
 */
public class DigitalBarDrawPanel extends JPanel implements MyValues{
    
    private int value; // current value - will be indicated on the bar

    private int barLength; // length/width of the bar
    private int barHeight; // height of the bar

    private int padding; // padding around the bar
    private float barMaxValue; // bar runs from 0 to barMaxValue
  
    
    public DigitalBarDrawPanel() { //Non Param constructor calls constructor with 5 params right below it
        this(100, 20, 8, 100, 0);
    }
    /**
     *
     * @param length - length of the horizontal bar
     * @param height - height of the bar
     * @param padding - padding around the bar
     * @param barMaxValue - bar runs from 0 to barMaxValue
     * @param value - current value that will be indicated on the bar
     */
    public DigitalBarDrawPanel(int length, int height, int padding, int barMaxValue, int value) {
        // set size of the JPanel to be big enough to hold the bar plus padding
        setPreferredSize(new Dimension(length + (2 * padding), height + (2 * padding)));

        this.barLength = length;
        this.barHeight = height;
        this.padding = padding;
        this.barMaxValue = barMaxValue;
        this.value = value;
    }
    
    
    @Override //Need to change this to get it working
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g; // get a Graphics2D object to draw with
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the bar itself.   The first 10% of the bar is red.  The last 30% is yellow.  Between 10% and 30% 
        // the colour graduates from red to yellow.   Check the API documentation for GradientPaint to see
        // how this works.
        Rectangle2D barx = new Rectangle2D.Double(padding, padding, barLength, barHeight);
        g2.setPaint(Color.BLACK); //Sets color of bar to black to give a digital clock feel
        g2.fill(barx);

        Font barFont = new Font("Unispace", Font.BOLD, 20); //Define digital like font to be used on bar
        g2.setPaint(Color.cyan); //Set colour of text on digital dial
        g2.drawString("°C", 88, 24); //Position text onto dial to give digital look
        g2.setFont(barFont); //Set bar font

        // draw the input from the text field to the digital dial
        g2.drawString(String.valueOf(value * 1), 9, 25);
    }

   @Override
    public void setValue(int value) {
        // don't let the value go over the maximum for the bar.  But what about the minimum?
        this.value = value > barMaxValue ? (int) barMaxValue : value;
        repaint(); // causes paintComponent() to be called
    }    
}
