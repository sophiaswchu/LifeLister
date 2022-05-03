package ui;

import model.Bird;
import model.EventLog;
import model.Event;
import model.LifeList;
import model.Sighting;
import persistence.Reader;
import persistence.Writer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.util.Scanner;

// Life Lister deals with all user input, and calls upon model's public methods to fulfill instructions
public class LifeLister extends JFrame {
    private QuitListener quitListener;
    private LifeList lifeList;
    private DefaultListModel listModel;

    private static final String addString = "Add Bird";
    private JButton addButton;
    private AddButtonListener addListener;
    private static final String saveString = "Save Life List to file";
    private JButton saveButton;
    private static final String loadString = "Load Life List from file";
    private JButton loadButton;

    private JScrollPane listScrollPane;
    private JScrollPane logScrollPane;

    private JTextField nameField;
    private JTextField locationField;
    private JTextField yearField;
    private JSpinner monthSpinner;
    private JSpinner daySpinner;

    private final Writer writer;
    private final Reader reader;
    private static final String JSON = "./data/lifelister.json";
    private Scanner input;

    // EFFECTS: runs the Life Lister application
    public LifeLister() {
        super("Life Lister");
        writer = new Writer(JSON);
        reader = new Reader(JSON);

        createListScrollPane();
        createButtons();
        createTextFields();
        createSpinners();
        JPanel panel = createPanel();
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, listScrollPane, logScrollPane);
        splitPane.setResizeWeight(0.5);
        add(panel, BorderLayout.PAGE_START);
        add(splitPane, BorderLayout.CENTER);
        try {
            Image myPicture = ImageIO.read(new File("./data/left-bird.jpg"));
            myPicture = myPicture.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            add(new JLabel(new ImageIcon(myPicture)), BorderLayout.PAGE_END);
        } catch (IOException e) {
            //failed to create image
        }
        quitListener = new QuitListener();
        addWindowListener(quitListener);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setVisible(true);

        runLifeList();
    }

    // MODIFIES: lifeList
    // EFFECTS: processes the users string inputs from keyboard
    private void runLifeList() {
        boolean userIsListing = true;
        String instruction = null;
        init();

        while (userIsListing) {
            menu();
            instruction = input.next();

            if (instruction.equals("q")) {
                userIsListing = false;
                setVisible(false);
                printLog();
                System.exit(0);
            } else {
                processInstruction(instruction);
            }
        }
    }

    // EFFECTS: prints all the events in Event Log to the screen
    public void printLog() {
        for (Event next : EventLog.getInstance()) {
            System.out.println(next.toString() + "\n\n");
        }
    }

    // MODIFIES: lifeList
    // EFFECTS: initializes the Life List
    private void init() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of instruction options to user screen
    private void menu() {
        System.out.println("\nWelcome to Life Lister! Please select from the following options:");
        System.out.println("\tCheck if you have seen a specific bird before (1)");
        System.out.println("\tView the last species you saw (2)");
        System.out.println("\tView a specific bird in life list (3)");
        System.out.println("\tUpdate the last sighting of a bird in life list (4)");
        System.out.println("\tShow a specific time period of birds in life list (5)");
        System.out.println("\tShow birds from a specific location in life list (6)");
        System.out.println("\tQuit the program (q)");
        System.out.println("\nTo select please type the letter in the brackets. Thank you!");
    }

    // MODIFIES: lifeList
    // EFFECTS: processes the user's instruction
    private void processInstruction(String instruction) {
        if (instruction.equals("1")) {
            printIsBirdInLifeList();
        } else if (instruction.equals("2")) {
            printLastBirdInLifeList();
        } else if (instruction.equals("3")) {
            printGivenBird();
        } else if (instruction.equals("4")) {
            updateBirdSighting();
        } else if (instruction.equals("5")) {
            printListInTimePeriod();
        } else if (instruction.equals("6")) {
            printListInLocation();
        } else {
            System.out.println("Instruction not valid.");
        }
    }

    // EFFECTS: prints all Bird's that had sightings in given location
    private void printListInLocation() {
        System.out.println("\nPlease type the location you would like to consider.");
        String location = input.next();
        LifeList locationBirds = lifeList.birdsInLocation(location);
        if (locationBirds.isEmpty()) {
            System.out.println("Sorry! There are no birds from this location.");
        } else {
            System.out.println(locationBirds.listToString());
        }
    }

    // MODIFIES: lifeList
    // EFFECTS: changes lastSighting of given Bird in lifeList to a new given Sighting
    //          if a Bird with given name is not in lifeList prints polite message
    private void updateBirdSighting() {
        String birdName = getBirdName();
        Bird givenBird = lifeList.getBird(birdName);
        if (givenBird != null) {
            removeFromListModel(givenBird);
            Sighting birdSighting = getBirdLastSighting();
            givenBird.updateSighting(birdSighting);
            listModel.addElement(givenBird.toLine());
        } else {
            System.out.println("Sorry, " + birdName + " is not in your life list and so cannot be viewed.");
            System.out.println("\nPlease add now to view.");
        }
    }

    // MODIFIES: listModel
    // EFFECTS: removes a given Bird object from listModel
    private void removeFromListModel(Bird bird) {
        for (int i = 0; i < listModel.getSize(); i++) {
            Object item = listModel.get(i);
            if (item.equals(bird.toLine())) {
                listModel.remove(i);
            }
        }
    }

    // REQUIRES: the user to input numeric values
    // EFFECTS: prints a list of Bird's from a given time period to screen
    //          0 is understood as user only wants birds in previous broader time period
    //          if there are no Bird's in time period prints polite message
    private void printListInTimePeriod() {
        System.out.println("\nPlease type the dates you would like to consider.");
        System.out.println("\nTo keep broad enter 0. Ex. birds from 2001... enter 2001, 0, 0.");
        System.out.println("\nPlease type the desired year (yyyy).");
        int year = Integer.parseInt(input.next());
        System.out.println("\nPlease type the desired month (mm).");
        int month = Integer.parseInt(input.next());
        System.out.println("\nPlease type the desired day (dd).");
        int day = Integer.parseInt(input.next());
        LifeList shortList = lifeList.birdsInTimePeriod(year, month, day);
        if (shortList.isEmpty()) {
            System.out.println("Sorry! There are no birds in this time period.");
        } else {
            System.out.println(shortList.listToString());
        }
    }

    // EFFECTS: prints a statement saying if given Bird is in list or not
    private void printIsBirdInLifeList() {
        String birdName = getBirdName();
        boolean inList = lifeList.isBirdInLifeList(birdName);
        if (inList) {
            System.out.println("Yes, " + birdName + " is in your life list!");
        } else {
            System.out.println("Sorry, " + birdName + " is not in your life list.");
        }
    }

    // EFFECTS: prints the String representation of last new Bird seen to screen
    private void printLastBirdInLifeList() {
        Bird lastBird = lifeList.lastBird();
        System.out.println(lastBird.toString());
    }

    // EFFECTS: prints the String representation of given Bird to screen,
    //          if given Bird not in list prints polite message saying so
    private void printGivenBird() {
        String birdName = getBirdName();
        Bird givenBird = lifeList.getBird(birdName);
        if (givenBird != null) {
            System.out.println(givenBird);
        } else {
            System.out.println("Sorry, " + birdName + " is not in your life list and so cannot be viewed.");
            System.out.println("\nPlease add now to view.");
        }
    }

    // REQUIRES: a name that is not already in list
    // EFFECTS: collects and returns a String of a bird's name from the user
    private String getBirdName() {
        System.out.println("\nPlease type the name of the bird.");
        return input.next();
    }

    // REQUIRES: numerical input from the user for year, month, day
    // EFFECTS: collects and returns a String of a Bird's last sighting from the user
    private Sighting getBirdLastSighting() {
        while (true) {
            System.out.println("\nPlease type the year of the last sighting of the bird (yyyy).");
            int year = Integer.parseInt(input.next());
            System.out.println("\nPlease type the month of the last sighting of the bird (mm).");
            int month = Integer.parseInt(input.next());
            System.out.println("\nPlease type the day of the last sighting of the bird (dd).");
            int day = Integer.parseInt(input.next());
            System.out.println("\nPlease type the location of the last sighting of the bird.");
            String location = input.next();
            if (Sighting.properDate(month, day, year)) {
                return new Sighting(month, day, year, location);
            } else {
                System.out.println("\nInvalid date. Please enter a proper date.");
            }
        }
    }

    // MODIFIES: monthSpinner, daySpinner
    // EFFECTS: initializes the spinners for the date, sets boundaries 1-12 for month and 1-31 for day
    private void createSpinners() {
        monthSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 12, 1));
        daySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1));
    }

    // EFFECTS: initializes a panel with all labels, spinners, buttons and text fields laid out
    private JPanel createPanel() {
        JPanel newPanel = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        newPanel.add(new JLabel("Name:", JLabel.LEFT), constraints);

        constraints.gridx = 1;
        newPanel.add(nameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        newPanel.add(new JLabel("Year:", JLabel.LEFT), constraints);
        constraints.gridx = 1;
        newPanel.add(yearField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        newPanel.add(new JLabel("Month:", JLabel.LEFT), constraints);
        constraints.gridx = 1;
        newPanel.add(monthSpinner, constraints);

        return extraPanelSetup(newPanel);
    }

    // EFFECTS: helper method for createPanel, produces the second half of the panel
    private JPanel extraPanelSetup(JPanel newPanel) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 3;
        newPanel.add(new JLabel("Day:", JLabel.LEFT), constraints);
        constraints.gridx = 1;
        newPanel.add(daySpinner, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        newPanel.add(new JLabel("Location:", JLabel.LEFT), constraints);

        constraints.gridx = 1;
        newPanel.add(locationField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        newPanel.add(addButton, constraints);

        constraints.gridy = 6;
        newPanel.add(saveButton, constraints);

        constraints.gridy = 7;
        newPanel.add(loadButton, constraints);

        return newPanel;
    }

    // MODIFIES: nameField, locationField, yearField
    // EFFECTS: initializes all the text fields and adds addListener to each
    private void createTextFields() {
        nameField = new JTextField(15);
        nameField.addActionListener(addListener);
        nameField.setText("");
        locationField = new JTextField(15);
        locationField.addActionListener(addListener);
        locationField.setText("");
        yearField = new JTextField(15);
        yearField.addActionListener(addListener);
        yearField.setText("");
        JTextArea nameArea = new JTextArea(0, 0);
        logScrollPane = new JScrollPane(nameArea);
    }

    // MODIFIES: addButton, saveButton, loadButton
    // EFFECTS: initializes all buttons, adds a respective listener to each
    private void createButtons() {
        addButton = new JButton(addString);
        addListener = new AddButtonListener();
        addButton.setActionCommand(addString);
        addButton.addActionListener(addListener);

        saveButton = new JButton(saveString);
        SaveButtonListener saveListener = new SaveButtonListener();
        saveButton.setActionCommand(saveString);
        saveButton.addActionListener(saveListener);

        loadButton = new JButton(loadString);
        LoadButtonListener loadListener = new LoadButtonListener();
        loadButton.setActionCommand(loadString);
        loadButton.addActionListener(loadListener);
    }

    // MODIFIES: lifeList, listModel, listScrollPane
    // EFFECTS: initializes all three lists and scroll pane display with an example line of demoBird
    private void createListScrollPane() {
        lifeList = new LifeList();
        Bird demoBird = new Bird("Demo Bird", new Sighting(1, 1, 2021, "UBC"));
        listModel = new DefaultListModel<>();
        listModel.addElement(demoBird.toLine());
        JList list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setSelectedIndex(0);
        list.setVisibleRowCount(10);
        listScrollPane = new JScrollPane(list);
    }

    // LoadButtonListener loads data from JSON to scroll pane and lifeList upon press
    class LoadButtonListener implements ActionListener {

        // MODIFIES: lifeList, listModel
        // EFFECTS: adds all birds stored in JSON to both lists
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                LifeList extraLifeList = reader.read();
                for (Bird b : extraLifeList.getLinkedList()) {
                    if (lifeList.getBird(b.getName()) == null) {
                        lifeList.addBird(b);
                        listModel.addElement(b.toLine());
                    }
                }
            } catch (IOException i) {
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }

    // quitListener allows the quit items to be executed upon window closure
    class QuitListener implements WindowListener {

        // EFFECTS: none
        @Override
        public void windowOpened(WindowEvent e) {}

        // EFFECTS: when user clicks x, print the EventLog, close window, and quit program
        @Override
        public void windowClosing(WindowEvent e) {
            setVisible(false);
            printLog();
            System.exit(0);
        }

        // EFFECTS: none
        @Override
        public void windowClosed(WindowEvent e) {
            printLog();
        }

        // EFFECTS: none
        @Override
        public void windowIconified(WindowEvent e) {}

        // EFFECTS: none
        @Override
        public void windowDeiconified(WindowEvent e) {}

        // EFFECTS: none
        @Override
        public void windowActivated(WindowEvent e) {}

        @Override
        public void windowDeactivated(WindowEvent e) {}
    }

    // LoadButtonListener saves data to JSON from lifeList upon press
    class SaveButtonListener implements ActionListener {

        // MODIFIES: JSON
        // EFFECTS: writes all birds in lifeList to JSON
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                writer.open();
                writer.write(lifeList);
                writer.close();
            } catch (FileNotFoundException f) {
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }

    // updates scrollPane and lifeList to include entered Bird upon press
    class AddButtonListener implements ActionListener {
        // REQUIRES: year, month, and day input to be of form int
        // MODIFIES: lifeList, listModel, nameField, locationField, yearField
        // EFFECTS: when addButton pressed, constructs a Bird if given valid input
        //          adds the Bird to lifeList and listModel, resets text fields to ""
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            String location = locationField.getText();
            int year = Integer.parseInt(yearField.getText());
            int month = Integer.parseInt(monthSpinner.getValue().toString());
            int day = Integer.parseInt(daySpinner.getValue().toString());
            if (name.equals("") || lifeList.getBird(name) != null
                    || !Sighting.properDate(month, day, year)
                    || location.equals("")) {
                Toolkit.getDefaultToolkit().beep();
                return;
            }

            Bird newBird = new Bird(name, new Sighting(month, day, year, location));
            lifeList.addBird(newBird);
            listModel.addElement(newBird.toLine());
            nameField.requestFocusInWindow();
            nameField.setText("");
            locationField.setText("");
            yearField.setText("");
        }
    }
}
