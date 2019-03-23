package uk.ac.gre.comp1549.dashboard.designPatterns;

//Factory design pattern class
import uk.ac.gre.comp1549.dashboard.barPanels.BarPanel;
import uk.ac.gre.comp1549.dashboard.controls.MainPanel;
import uk.ac.gre.comp1549.dashboard.digitalDials.DigitalBarPanel;
import uk.ac.gre.comp1549.dashboard.fullDials.DialPanel;
import uk.ac.gre.comp1549.dashboard.halfDials.HalfDialPanel;

public class Factory { //This could be used to decide which dial class gets an instance returned to it by run time maybe

    public static MainPanel createDial(String type) {
        if (type.equals("SPEED") || type.equals("MILES")) {
            return new DialPanel();
        } else if (type.equals("TEMPERATURE")) {
            return new HalfDialPanel();
        } else if (type.equals("OIL")) {
            return new DigitalBarPanel();
        } else if (type.equals("PETROL")) {
            return new BarPanel();
        }
        return null;
    }
}
