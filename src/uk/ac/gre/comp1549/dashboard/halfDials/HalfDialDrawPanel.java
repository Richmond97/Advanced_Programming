package uk.ac.gre.comp1549.dashboard.halfDials;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import javax.swing.JPanel;
import uk.ac.gre.comp1549.dashboard.interfaces.DrawPanelIF;
import uk.ac.gre.comp1549.dashboard.interfaces.MyValues;

public class HalfDialDrawPanel extends JPanel implements DrawPanelIF, MyValues {

    private int value; // current value - where the hand will point

    private int radius; // radius of dial
    private int padding; // padding outside the dial
    private double handLength; // length of indicator hand
    private int dialMaxValue;  // dial runs from 0 to dialMaxValue

    /**
     * The extent of the dial. For a full circle this would be 360
     */
    public static final float DIAL_EXTENT_DEGREES = 180; //Changed to half of a full circle to make it a half dial

    /**
     * Where the dial starts being draw from. Due north is 90.
     */
    public static final float DIAL_START_OFFSET_DEGREES = 45;

    public HalfDialDrawPanel(int radius, int padding, int dialMaxValue, int value) {
        // set size of the JPanel to be big enough to hold the dial plus padding
        setPreferredSize(new Dimension(2 * (radius + padding), 2 * (radius + padding)));
        this.radius = radius;
        this.padding = padding;
        this.dialMaxValue = dialMaxValue;
        this.value = value;
        handLength = 0.9 * radius; // hand length is fixed at 90% of radius
    }

    public HalfDialDrawPanel() { // We now need to call our constructor
        this(40, 5, 100, 0); //Setting radius of Dial to 50, padding to 5, Maximum value of dial to 100 & starting value to 0
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g; // get a Graphics2D object to draw with
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, 0));

        // draw centre of the dial as a small circle of fixed size
        Ellipse2D circle = new Ellipse2D.Double((radius + padding) - 4, (radius + padding) - 5, 8, 8);
        g2.fill(circle);

        // draw the dial itself
        Arc2D arc = new Arc2D.Double(padding, padding, 2 * radius, 2 * radius, DIAL_START_OFFSET_DEGREES, DIAL_EXTENT_DEGREES, Arc2D.Double.OPEN);
        g2.draw(arc);

        // draw the little lines at the start and end of the dial
        drawDialEnd(g2, Math.toRadians(DIAL_START_OFFSET_DEGREES));
        drawDialEnd(g2, Math.toRadians(DIAL_START_OFFSET_DEGREES + DIAL_EXTENT_DEGREES));

        // draw the hand to indicate the current value
        double angle = Math.toRadians(225 - (value * (DIAL_EXTENT_DEGREES / dialMaxValue)));
        
        g2.setColor(Color.cyan);

        g2.drawString("20", 9, 55); // @param 1 - X --- @param 2 - Y 

        g2.drawString("40", 11, 39);        
        g2.drawString("60", 25, 24);  
        g2.drawString("80", 44, 19);

        drawHand(g2, angle, handLength);
    }

    @Override
    public void drawDialEnd(Graphics2D g2, double angle) {
        // calculate endpoint of line furthest from centre of dial
        Point2D outerEnd = new Point2D.Double((radius + padding) + radius * Math.cos(angle),
                (radius + padding) - radius * Math.sin(angle));
        // calculate endpoint of line closest to centre of dial
        Point2D innerEnd = new Point2D.Double((radius + padding) + ((radius + padding) * .8) * Math.cos(angle),
                (radius + padding) - ((radius + padding) * .8) * Math.sin(angle));
        // draw the line
        g2.draw(new Line2D.Double(outerEnd, innerEnd));
    }

    @Override
    public void drawHand(Graphics2D g2, double angle, double handLength) {
        // calculate the outer end of the hand
        Point2D end = new Point2D.Double((radius + padding) + handLength * Math.cos(angle),
                (radius + padding) - handLength * Math.sin(angle));
        // calculate the centre 
        Point2D center = new Point2D.Double(radius + padding, radius + padding);
        //     Draw the line
        g2.draw(new Line2D.Double(center, end));
    }

    @Override
    public void setValue(int value) {
        // don't let the value go over the maximum for the dial.  But what about the minimum?
        this.value = value > dialMaxValue ? dialMaxValue : value;
        repaint(); // causes paintComponent() to be called
    }
}
