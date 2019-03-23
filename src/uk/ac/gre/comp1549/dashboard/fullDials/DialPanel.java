package uk.ac.gre.comp1549.dashboard.fullDials;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import uk.ac.gre.comp1549.dashboard.controls.MainPanel;

/**
 * DialPanel.  Container for DialDrawPanel to hold dial and label.
 * If a label is not needed DialDrawPanel an be used on its own
 *
 * @author COMP1549
 * @version 2.0
 */
public class DialPanel extends MainPanel {

    private DialDrawPanel dial;  // the dial itself

    /**
     * Default constructor
     */
    public DialPanel() {
        create();
    }
    
    @Override
    public void create(){
        setLayout(new BorderLayout());
        
        // set the style of the border
        setBorder(new BevelBorder(BevelBorder.LOWERED));

        // position the label above the dial
        lblTitle = new JLabel();
        lblTitle.setHorizontalAlignment(JLabel.CENTER);
        add(lblTitle, BorderLayout.NORTH);
        dial = new DialDrawPanel();
        add(dial, BorderLayout.CENTER);
    }

   @Override
    public void setValue(int value) {
        dial.setValue(value);
    }

   @Override
    public void setLabel(String label) {
        lblTitle.setText(label);
    }
}
