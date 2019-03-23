package uk.ac.gre.comp1549.dashboard;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import uk.ac.gre.comp1549.dashboard.barPanels.*;
import uk.ac.gre.comp1549.dashboard.fullDials.*;
import uk.ac.gre.comp1549.dashboard.digitalDials.*;
import uk.ac.gre.comp1549.dashboard.halfDials.HalfDialPanel;
import uk.ac.gre.comp1549.dashboard.events.*;
import uk.ac.gre.comp1549.dashboard.scriptreader.DashboardEventGeneratorFromXML;

/**
 * DashboardDemoMain.java Contains the main method for the Dashboard demo
 * application. It: a) provides the controller screen which allows user input
 * which is passed to the display indicators, b) allows the user to run the XML
 * script which changes indicator values, c) creates the dashboard JFrame and
 * adds display indicators to it.
 *
 * @author COMP1549
 * @version 2.0
 */
public class DashboardDemoMain extends JFrame {

    /**
     * Name of the XML script file - change here if you want to use a different
     * filename
     */
    public static final String XML_SCRIPT = "dashboard_script.xml";

    // fields that appear on the control panel
    private JTextField txtSpeedValueInput;
    private JTextField txtPetrolValueInput;
    private JButton btnScript;

    //Modified - ***
    private JTextField txtMilesValueInput;
    private JTextField txtTempValueInput;
    private JTextField txtOilValueInput;

    //Full dials
    private DialPanel speedDial;
    private DialPanel milesDial;
    
    //Bar Panels
    private BarPanel petrolBar;
    private DigitalBarPanel oilBar;

    //Half dial
    private HalfDialPanel tempDial; //Half Dial

    //Setup dashboard for class
    JFrame dashboard = new JFrame("The Norfolkman - 1993");

    //Main Panel used by JFrame
    JPanel panel = new JPanel();

    /**
     * Constructor. Does maybe more work than is good for a constructor.
     */
    public DashboardDemoMain() {
        // Set up the frame for the controller
        setTitle("The Norfolkman - 1993");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Call method to add each component to dashboard -- DIALS && JTextFields
        setup_Oil();
        setup_Speed();
        setup_Temp();
        setup_Miles();
        setup_Petrol();

        //Button to Run Script - ***
        btnScript = new JButton("Run XML Script");

        // When the button is read the XML script will be run
        btnScript.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    public void run() {
                        runXMLScript();
                    }
                }.start();
            }
        });
        panel.add(btnScript);
        add(panel);
        pack();

        setLocationRelativeTo(null); // display in centre of screen
        this.setVisible(true);

        // Set up the dashboard screen        
        dashboard.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        dashboard.setLayout(new FlowLayout());
        dashboard.setResizable(false); //Prevent user form resizing the frame

        dashboard.pack();

        //Position dashboard above controller JFrame
        set_position();
    }
    
    public void set_position(){
        // centre the dashboard frame above the control frame
        Point topLeft = this.getLocationOnScreen(); // top left of control frame (this)
        int hControl = this.getHeight(); // height of control frame (this)
        int wControl = this.getWidth(); // width of control frame (this)
        int hDash = dashboard.getHeight(); // height of dashboard frame 
        int wDash = dashboard.getWidth(); // width of dashboard frame 
        // calculate where top left of the dashboard goes to centre it over the control frame
        Point p2 = new Point((int) topLeft.getX() - (wDash - wControl) / 2, (int) topLeft.getY() - (hDash + hControl));
        dashboard.setLocation(p2);
        dashboard.setVisible(true);
    }
    
    public void setup_Oil(){
        //Oil - **
        panel.add(new JLabel("Oil Value:")); //Set name of text field to be shown to user
        txtOilValueInput = new JTextField("0", 3);
        panel.add(txtOilValueInput);
        DocumentListener oilListener = new OilValueListener();
        txtOilValueInput.getDocument().addDocumentListener(oilListener);

         // add the oil bar
        oilBar = new DigitalBarPanel();
        oilBar.setLabel("Oil"); //Fixed
        //oilBar.setValue(0); //This throws a null pointer exception
        dashboard.add(oilBar);
    }
    
    public void setup_Speed(){
        //Speed 
        panel.add(new JLabel("Speed Value:"));
        txtSpeedValueInput = new JTextField("0", 3);
        panel.add(txtSpeedValueInput);
        DocumentListener speedListener = new SpeedValueListener();
        txtSpeedValueInput.getDocument().addDocumentListener(speedListener); 

        // add the speed Dial
        speedDial = new DialPanel();
        speedDial.setLabel("Speed");
        dashboard.add(speedDial);   
    }
    
    public void setup_Temp(){
        //Temperature - **
        panel.add(new JLabel("Temp Value:"));
        txtTempValueInput = new JTextField("0", 3);
        panel.add(txtTempValueInput);
        DocumentListener tempListener = new TempValueListener();
        txtTempValueInput.getDocument().addDocumentListener(tempListener);
        
        
     // add the Temperature half dial
        tempDial = new HalfDialPanel();
        tempDial.setLabel("Temperature");
        dashboard.add(tempDial);   
    }
    
    public void setup_Miles(){
        //Miles - ***
        panel.add(new JLabel("Miles Value:"));
        txtMilesValueInput = new JTextField("0", 3);
        panel.add(txtMilesValueInput);
        DocumentListener milesListener = new MilesValueListener();
        txtMilesValueInput.getDocument().addDocumentListener(milesListener);

        // add the miles Dial
        milesDial = new DialPanel();
        milesDial.setLabel("Miles");
        dashboard.add(milesDial);
    }
    
    public void setup_Petrol(){
        
        //Fuel
        panel.add(new JLabel("Petrol Value:"));
        txtPetrolValueInput = new JTextField("0", 3);
        panel.add(txtPetrolValueInput);
        DocumentListener petrolListener = new PetrolValueListener();
        txtPetrolValueInput.getDocument().addDocumentListener(petrolListener);
        
        // add the petrol Bar
        petrolBar = new BarPanel();
        petrolBar.setLabel("Petrol");
        petrolBar.setValue(0);
        dashboard.add(petrolBar);
    }
    /**
     * Run the XML script file which generates events for the dashboard
     * indicators
     */
    private void runXMLScript() {
        try {
            DashboardEventGeneratorFromXML dbegXML = new DashboardEventGeneratorFromXML();

            // Register for speed events from the XML script file
            DashBoardEventListener dbelSpeed = new DashBoardEventListener() {
                @Override
                public void processDashBoardEvent(Object originator, DashBoardEvent dbe) {
                    speedDial.setValue(Integer.parseInt(dbe.getValue()));
                }
            };
            dbegXML.registerDashBoardEventListener("speed", dbelSpeed);

            // Register for petrol events from the XML script file
            DashBoardEventListener dbelPetril = new DashBoardEventListener() {
                @Override
                public void processDashBoardEvent(Object originator, DashBoardEvent dbe) {
                    milesDial.setValue(Integer.parseInt(dbe.getValue()));
                    petrolBar.setValue(Integer.parseInt(dbe.getValue()));
                }
            };
            dbegXML.registerDashBoardEventListener("petrol", dbelPetril);

            // Process the script file - it willgenerate events as it runs
            dbegXML.processScriptFile(XML_SCRIPT);

        } catch (Exception ex) {
            Logger.getLogger(DashboardDemoMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Set the speed value to the value entered in the textfield.
     */
    public void setSpeed() { // *** - Should be placed interface 
        try {
            int value = Integer.parseInt(txtSpeedValueInput.getText().trim());
            speedDial.setValue(value);
        } catch (NumberFormatException e) {
        }
        // don't set the speed if the input can't be parsed
    }

    /**
     * Set the petrol value to the value entered in the textfield.
     */
    public void setPetrol() { // *** - Should be placed interface
        try {
            int value = Integer.parseInt(txtPetrolValueInput.getText().trim());
            petrolBar.setValue(value);
        } catch (NumberFormatException e) {
        }
        // don't set the speed if the input can't be parsed
    }

    /**
     * Set the speed value to the value entered in the textfield.
     */
    public void setMiles() { // *** - Should be placed interface 
        try {
            int value = Integer.parseInt(txtMilesValueInput.getText().trim());
            milesDial.setValue(value);
        } catch (NumberFormatException e) {
        }
        // don't set the speed if the input can't be parsed
    }
    
    public void setTemp(){
        try {
            int value = Integer.parseInt(txtTempValueInput.getText().trim());
            tempDial.setValue(value);
        } catch (NumberFormatException e){
            
        }
    }
    
    public void setOil(){
        try {
            int value = Integer.parseInt(txtOilValueInput.getText().trim()); //Get input form Oil textfield convert it to an int and then set it as the value
            oilBar.setValue(value);
        } catch (NumberFormatException e) { //Will be caught if the conversion from int to string fails
        }
    }

    /**
     * Respond to user input in the Speed textfield
     */
    private class SpeedValueListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent event) {
            setSpeed();
        }

        @Override
        public void removeUpdate(DocumentEvent event) {
            setSpeed();
        }

        @Override
        public void changedUpdate(DocumentEvent event) {
        }
    }

    private class MilesValueListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            setMiles();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            setMiles();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }

    }

    /**
     * Respond to user input in the Petrol textfield
     */
    private class PetrolValueListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent event) {
            setPetrol();
        }

        @Override
        public void removeUpdate(DocumentEvent event) {
            setPetrol();
        }

        @Override
        public void changedUpdate(DocumentEvent event) {
        }
    }

    /**
     * Respond to use input in the miles textField
     */
    private class TempValueListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            setTemp();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            setTemp();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }
    }

    private class OilValueListener implements DocumentListener { //****

        @Override
        public void insertUpdate(DocumentEvent e) {
            setOil(); //Sets action listener to correspond with input being took from the oil textfield
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            setOil();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }
    }
    /**
     *
     * @param args - unused
     */
    public static void main(String[] args) {
      final DashboardDemoMain me = new DashboardDemoMain();
    }
}
