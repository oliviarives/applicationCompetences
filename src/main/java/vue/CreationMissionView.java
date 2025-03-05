package vue;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

// @TODO CB select calendar

public class CreationMissionView extends JPanel {
    private JButton buttonConfirmer;
    private JTextField titreMisField;
    private JTextField descriptionMisField;
    private JFormattedTextField dateDebutMisField;
    private JFormattedTextField dateFinMisField;
    private JTextField nbEmpField;
    private JTextField logEmpField;

    public CreationMissionView() {
        setLayout(new FlowLayout());
        DateFormatter dateFormatter = new DateFormatter(new SimpleDateFormat("dd/MM/yyyy"));

        this.titreMisField = new JTextField(20);
        this.descriptionMisField = new JTextField(40);
        this.dateDebutMisField = new JFormattedTextField(dateFormatter);
        this.dateFinMisField = new JFormattedTextField(dateFormatter);
        this.nbEmpField = new JTextField(20);
        this.logEmpField = new JTextField(20);
        this.buttonConfirmer = new JButton("Confirmer");

        add(new JLabel("Titre Mission : "));
        add(titreMisField);
        add(new JLabel("Description : "));
        add(descriptionMisField);
        add(new JLabel("Date de début : "));
        add(dateDebutMisField);
        add(new JLabel("Date de fin : "));
        add(dateFinMisField);
        add(new JLabel("Nombre d'émployé necessaires : "));
        add(nbEmpField);
        add(new JLabel("login employé : "));
        add(logEmpField);
        add(buttonConfirmer);
    }

    public JButton getButtonConfirmer() {
        return this.buttonConfirmer;
    }

    public String getTitreMisFieldValue() {
        return this.titreMisField.getText();
    }

    public String getDescriptionMisFieldValue() {
        return this.descriptionMisField.getText();
    }

    public String getDateDebutMisField() {
        return this.dateDebutMisField.getText();
    }

    public String getDateFinMisField() {
        return this.dateFinMisField.getText();
    }

    public int getNbEmpField() {
        return Integer.parseInt(this.nbEmpField.getText());
    }

    public String getLogEmpField() {
        return this.logEmpField.getText();
    }


}
