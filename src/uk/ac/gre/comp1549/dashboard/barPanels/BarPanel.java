package uk.ac.gre.comp1549.dashboard.barPanels;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import uk.ac.gre.comp1549.dashboard.controls.MainPanel;

/**
 * BarPanel. Container for BarDrawPanel to hold bar and label. If a label is not
 * needed BarDrawPanel an be used on its own
 *
 * @author COMP1549
 * @version 2.0
 */
public class BarPanel extends MainPanel {

    private BarDrawPanel bar; // the bar itself
    
    public BarPanel() {
        create();
    }

    @Override
    public void create() {
        setLayout(new BorderLayout());
        // set the style of the border
        setBorder(new BevelBorder(BevelBorder.LOWERED));

        // position the label above the bar 
        lblTitle = new JLabel();
        lblTitle.setHorizontalAlignment(JLabel.CENTER);
        add(lblTitle, BorderLayout.NORTH); 
        bar = new BarDrawPanel();
        add(bar, BorderLayout.CENTER); //Change to CENTER
    }

    @Override
    public void setValue(int value) {
        bar.setValue(value);
    }

    @Override
    public void setLabel(String label) {
        lblTitle.setText(label);
    }
}
