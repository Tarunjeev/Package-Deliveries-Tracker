package cmpt213.assignment3.packagedeliveries.view;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.Objects;
import cmpt213.assignment3.packagedeliveries.control.PackageFactory;
import cmpt213.assignment3.packagedeliveries.model.Package;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.optionalusertools.DateTimeChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateTimeChangeEvent;
import cmpt213.assignment3.packagedeliveries.control.PackageManager;
public class AddPackageDialog extends JDialog implements ActionListener, DateTimeChangeListener {
    private int typepkg;
    private LocalDateTime pkgdeliverydate;
    private LocalDateTime pkgexpirydate;
    private DateTimePicker datetimepicker;
    private final PackageManager pManager = PackageManager.getInstance();
    private JLabel extraLabel;
    private JComboBox<String> jComboBox;
    private final String[] optionsToChoose = {"Book", "Perishable", "Electronic"};
    JTextField namefield;
    JTextField notesfield;
    JTextField pricefield;
    JTextField weightfield;
    JTextField datefield;
    JTextField extraField;

    public AddPackageDialog(Frame fr) {
        super(fr, "Add", true);
        Point loc = fr.getLocation();
        setLocation(loc.x+100, loc.y+100);
        JPanel mainpanel = new JPanel();
        mainpanel.setLayout(new BoxLayout(mainpanel, BoxLayout.Y_AXIS));
        jComboBox = new JComboBox<>(optionsToChoose);
        jComboBox.setPreferredSize(new Dimension(200, 40));
        jComboBox.addActionListener(this);
        JPanel namepanel = getnamepanel();
        JPanel notespanel = getnotespanel();
        JPanel pricepanel = getpricepanel();
        JPanel weightpanel = getweightpanel();
        JPanel datepanel = getdatepanel();
        JPanel buttonpanel = getbuttonpanel();
        JPanel extrapanel = getextrapanel();
        mainpanel.add(jComboBox);
        mainpanel.add(namepanel);
        mainpanel.add(notespanel);
        mainpanel.add(pricepanel);
        mainpanel.add(weightpanel);
        mainpanel.add(datepanel);
        mainpanel.add(extrapanel);
        mainpanel.add(buttonpanel);
        getContentPane().setSize(800,800);
        getContentPane().add(mainpanel);
        pack();
        this.setVisible(true);
    }

    private JPanel getextrapanel() {
        JPanel extrapanel = new JPanel();
        extrapanel.setLayout(new BoxLayout(extrapanel, BoxLayout.X_AXIS));
        extraLabel = new JLabel();
        extraLabel.setText("Author: ");
        extraLabel.setPreferredSize(new Dimension(45, 22));
        extraField = new JTextField();
        extrapanel.add(extraLabel);
        extrapanel.add(extraField);
        extrapanel.setPreferredSize(new Dimension(280,23));
        return extrapanel;
    }

    private JPanel getbuttonpanel() {
        JPanel buttonpanel = new JPanel();
        buttonpanel.setLayout(new BoxLayout(buttonpanel, BoxLayout.X_AXIS));
        JButton createbutton = new JButton("Create");
        JButton cancelbutton = new JButton("Cancel");
        createbutton.addActionListener(this);
        cancelbutton.addActionListener(this);
        buttonpanel.add(createbutton);
        buttonpanel.add(cancelbutton);
        return buttonpanel;
    }

    private JPanel getdatepanel() {
        JPanel datePanel = new JPanel();
        datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.X_AXIS));
        JLabel dateLabel = new JLabel();
        dateLabel.setText("Date: ");
        dateLabel.setPreferredSize(new Dimension(45, 22));
        datetimepicker = new DateTimePicker();
        datetimepicker.addDateTimeChangeListener(this);
        datePanel.add(dateLabel);
        datePanel.add(datetimepicker);
        datePanel.setPreferredSize(new Dimension (280, 23));
        return datePanel;
    }

    private JPanel getweightpanel() {
        JPanel weightpanel = new JPanel();
        weightpanel.setLayout(new BoxLayout(weightpanel, BoxLayout.X_AXIS));
        JLabel weightLabel = new JLabel();
        weightLabel.setText("Weight: ");
        weightLabel.setPreferredSize(new Dimension(45, 22));
        weightfield = new JTextField();
        weightpanel.add(weightLabel);
        weightpanel.add(weightfield);
        weightpanel.setPreferredSize(new Dimension(280,23));
        return weightpanel;
    }

    private JPanel getnamepanel() {
        JPanel namepanel = new JPanel();
        namepanel.setLayout(new BoxLayout(namepanel, BoxLayout.X_AXIS));
        JLabel nameLabel = new JLabel();
        nameLabel.setText("Name: ");
        nameLabel.setPreferredSize(new Dimension(45, 22));
        namefield = new JTextField();
        namefield.addActionListener(this);
        namepanel.add(nameLabel);
        namepanel.add(namefield);
        namepanel.setPreferredSize(new Dimension(280,23));
        return namepanel;
    }
    private JPanel getnotespanel() {
        JPanel notespanel = new JPanel();
        notespanel.setLayout(new BoxLayout(notespanel, BoxLayout.X_AXIS));
        JLabel notesLabel = new JLabel();
        notesLabel.setText("Notes: ");
        notesLabel.setPreferredSize(new Dimension(45, 22));
        notesfield = new JTextField();
        notespanel.add(notesLabel);
        notespanel.add(notesfield);
        notespanel.setPreferredSize(new Dimension(280,23));
        return notespanel;
    }
    private JPanel getpricepanel() {
        JPanel pricepanel = new JPanel();
        pricepanel.setLayout(new BoxLayout(pricepanel, BoxLayout.X_AXIS));
        JLabel priceLabel = new JLabel();
        priceLabel.setText("Price: ");
        priceLabel.setPreferredSize(new Dimension(45, 22));
        pricefield = new JTextField();
        pricepanel.add(priceLabel);
        pricepanel.add(pricefield);
        pricepanel.setPreferredSize(new Dimension(280,23));
        return pricepanel;
    }
    private void addmyPackage() {
        try {
            String name = getmyName();
            String authorname = getauthorextra();
            String notes = getmyNotes();
            double price = getmyPrice();
            double weight = getmyWeight();
            double handlingfee = getmyhandling();

            if (name.equals("")) {
                JOptionPane.showMessageDialog(this,
                        "Error: name field cannot be empty",
                        "Input is Invalid",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (price < 0) {
                JOptionPane.showMessageDialog(this,
                        "Error: price cannot be negative",
                        "Input is Invalid",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (weight < 0) {
                JOptionPane.showMessageDialog(this,
                        "Error: Weight cannot be negative",
                        "Input is Invalid",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            Package newpack = PackageFactory.getInstance(typepkg, name,authorname,notes, price, weight, pkgdeliverydate, pkgexpirydate,handlingfee );
            pManager.addPackage(newpack);
            dispose();
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this,
                    "Error: invalid input",
                    "Invalid input",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
    private String getmyName() {
        if (namefield.getText() == null) {
            return "";
        }
        return namefield.getText();
    }
    private String getauthorextra() {
        if (extraField.getText() == null) {
            return "";
        }
        return extraField.getText();
    }
    private String getmyNotes() {
        if (notesfield.getText() == null) {
            return "";
        }
        return notesfield.getText();
    }
    private double getmyPrice() {
        if (pricefield.getText() == null) {
            return -1;
        }
        try {
            return Double.parseDouble(pricefield.getText());
        } catch (NumberFormatException nfe) {
            //do nothing
        }
        return -1;
    }
    private double getmyhandling() {
        return 0;
    }
    private double getmyWeight(){
        if (weightfield.getText() == null) {
            return -1;
        }
        try {
            return Double.parseDouble(weightfield.getText());
        } catch (NumberFormatException nfe) {
            //do nothing
        }
        return -1;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (Objects.equals(jComboBox.getSelectedItem(), "Book")) {
            typepkg = 1;
            extraLabel.setText("Author Name");
        } else if (Objects.equals(jComboBox.getSelectedItem(), "Perishable")) {
            typepkg = 2;
            extraLabel.setText("Expiry Date");
        } else if (Objects.equals(jComboBox.getSelectedItem(), "Electronic")) {
            typepkg = 3;
            extraLabel.setText("Handling fee");
        }
        if (Objects.equals(e.getActionCommand(),"Create")) {
            addmyPackage();
        } else if (Objects.equals(e.getActionCommand(), "Cancel")) {
            this.dispose();
        }


    }

    @Override
    public void dateOrTimeChanged(DateTimeChangeEvent dateTimeChangeEvent) {
        pkgdeliverydate = datetimepicker.getDateTimePermissive();

    }
}

