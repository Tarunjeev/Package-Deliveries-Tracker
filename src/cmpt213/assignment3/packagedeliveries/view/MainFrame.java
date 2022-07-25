package cmpt213.assignment3.packagedeliveries.view;
import javax.swing.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Container;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import cmpt213.assignment3.packagedeliveries.control.PackageManager;
import java.util.Objects;
public class MainFrame implements ActionListener {
    JFrame applicationFrame;
    JTextPane displayPane;
    JScrollPane PackageListView;
    JLabel categoryLabel;

    private final PackageManager pkgmanager = PackageManager.getInstance();
    private int DISPLAY_OPTION = 0;

    /**
     * Displaying the main menu
     */
    public void display() {

        pkgmanager.loadFile();

        applicationFrame = new JFrame(" My Package Tracker");
        applicationFrame.setSize(800, 800);
        applicationFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        applicationFrame.setLayout(new BoxLayout(applicationFrame.getContentPane(), BoxLayout.Y_AXIS));
        applicationFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                pkgmanager.writeFile();
                super.windowClosing(e);
                applicationFrame.dispose();
            }
        });

        setupTopButtons();
        setupCategoryLabel();
        setupListView();
        setupAddRemoveButton();
//        setuptogglebox();
        updateView();

        applicationFrame.setSize(700, 700);
        applicationFrame.pack();
        applicationFrame.setVisible(true);
    }

    private void setuptogglebox() {
    }

    /**
     * Setting up the functions for the buttons
     */
    private void setupTopButtons() {
        JButton showAllButton = new JButton("All");
        JButton showOverdueButton = new JButton("Overdue");
        JButton showUpcomingButton = new JButton("Upcoming");

        showAllButton.addActionListener(this);
        showOverdueButton.addActionListener(this);
        showUpcomingButton.addActionListener(this);

        JPanel listTabsPanel = new JPanel();
        listTabsPanel.setLayout(new BoxLayout(listTabsPanel, BoxLayout.X_AXIS));

        listTabsPanel.add(showAllButton);
        listTabsPanel.add(showOverdueButton);
        listTabsPanel.add(showUpcomingButton);

        listTabsPanel.setPreferredSize(new Dimension(800, 90));
        addPanel(listTabsPanel, applicationFrame);
    }

    /**
     * Setting up the function for the category Label
     */
    private void setupCategoryLabel() {
        categoryLabel = new JLabel("All Packages");
        JPanel categoryPanel = new JPanel();
        categoryPanel.add(categoryLabel);
        categoryPanel.setPreferredSize(new Dimension(800, 20));
        addPanel(categoryPanel, applicationFrame);
    }

    /**
     * Setting up function for central pane for all packages
     */
    private void setupListView() {
        displayPane = new JTextPane();
        displayPane.setEditable(false);
        PackageListView = new JScrollPane(displayPane);
        PackageListView.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        PackageListView.setPreferredSize(new Dimension(800, 500));
        PackageListView.setAlignmentX(Component.CENTER_ALIGNMENT);
        applicationFrame.add(PackageListView);

    }

    /**
     * setting layout for add and remove buttons
     */
    private void setupAddRemoveButton() {
        JButton addNewButton = new JButton("Add");
        JButton removeButton = new JButton("Remove");

        addNewButton.addActionListener(this);
        removeButton.addActionListener(this);

        JPanel addRemovePanel = new JPanel();
        addRemovePanel.setLayout(new BoxLayout(addRemovePanel, BoxLayout.X_AXIS));

        addRemovePanel.add(addNewButton);
        addRemovePanel.add(removeButton);
        addRemovePanel.setPreferredSize(new Dimension(800, 90));
        addPanel(addRemovePanel, applicationFrame);
    }

    /**
     * adding panal to the main application frame
     */
    private static void addPanel(JPanel jpanel, Container container) {
        jpanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(jpanel);
    }
    private void setuptogglepanel(){

    }

    /**
     * Displays all items on the central pane
     */
    private void viewAllPackages() {
        displayPane.setText(pkgmanager.getAllPackages());
        displayPane.setCaretPosition(0);

    }

    /**
     * Panel for  all Overdue items on the central pane
     */
    private void viewAllOverdue() {
        displayPane.setText(pkgmanager.getAllOverdue());
        displayPane.setCaretPosition(0);
    }

    /**
     * Panel for  all upcoming items on the central pane
     */
    private void viewAllUpcoming() {
        displayPane.setText(pkgmanager.getAllUpcoming());
        displayPane.setCaretPosition(0);
    }

    /**
     * function to update the  the central pane whenever necessary
     */
    private void updateView() {
        if (DISPLAY_OPTION == 0) {
            viewAllPackages();
            categoryLabel.setText("All Packages");
        } else if (DISPLAY_OPTION == 1) {
            viewAllOverdue();
            categoryLabel.setText("Packages overdue");
        } else if (DISPLAY_OPTION == 2) {
            viewAllUpcoming();
            categoryLabel.setText("Packages upcoming");
        }
    }

    /**
     * Prompts the user for the index of the item to delete
     * @return the index given by the user
     */
    private int getDeletionIndex() {
        try {
            String input = JOptionPane.showInputDialog(applicationFrame,
                    "Which consumable would you like to delete?",
                    "Enter an index",
                    JOptionPane.QUESTION_MESSAGE);
            if (input == null) {
                return -1;
            }
            return Integer.parseInt(input);
        } catch (NumberFormatException nfe) {
            //do nothing
        }
        return -1;
    }

    /**
     * Creates the dialog prompt which asks users to enter the relevant information.
     */
    private void addPackage() {
        new AddPackageDialog(applicationFrame);
        updateView();
    }

    /**
     * Validates the index given by the user, then removed the associated Consumable from the list
     */
    private void removePackage() {
        if (pkgmanager.getSize() == 0) {
            JOptionPane.showMessageDialog(applicationFrame, "No packages to delete");
            return;
        }
        int toDelete = getDeletionIndex();
        if (toDelete < 1 || toDelete > pkgmanager.getSize()) {
            JOptionPane.showMessageDialog(applicationFrame,
                    "Enter a number from 1 to " + pkgmanager.getSize() + ".",
                    "Information",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        pkgmanager.removePackage(toDelete-1);
        updateView();
        JOptionPane.showMessageDialog(applicationFrame,
                "Item #" + toDelete + " has been removed!",
                "Information",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Defining a function that is responsible when the button is pressed
     *
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (Objects.equals(e.getActionCommand(), "All")) {
            DISPLAY_OPTION = 0;
            updateView();
        } else if (Objects.equals(e.getActionCommand(), "Overdue")) {
            DISPLAY_OPTION = 1;
            updateView();
        } else if (Objects.equals(e.getActionCommand(), "Upcoming")) {
            DISPLAY_OPTION = 2;
            updateView();
        }  else if (Objects.equals(e.getActionCommand(), "Add")) {
            addPackage();
        } else if (Objects.equals(e.getActionCommand(), "Remove")) {
            removePackage();
        }
    }
}