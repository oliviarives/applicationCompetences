package vue;

import modele.Competence;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;

// @TODO CB select calendar

public class CreationMissionView extends JPanel {
    private JButton buttonConfirmer;
    private JTextField titreMisField;
    private JTextArea descriptionMisField;
    private JFormattedTextField dateDebutMisField;
    private JFormattedTextField dateFinMisField;
    private JTextField nbEmpField;
    private JTextField logEmpField;
    private JButton ajouterCompetences;
    private JButton ajouterEmployes;
    private JSplitPane splitPane;
    private JPanel formulaire;
    private JPanel affichage;
    private JTable competenceTable;
    private JScrollPane competenceScrollPane;
    private CardLayout cardLayout;
    private JPanel cardLayoutPanel;
    private JLabel titreLabel;

    public CreationMissionView() {
        setLayout(new FlowLayout(FlowLayout.CENTER));

        //Partie Formulaire Mission
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        DateFormatter dateFormatter = new DateFormatter(formatter);

        this.splitPane = new JSplitPane();
        this.splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        this.splitPane.setDividerLocation(500);

        //this.formulaire.setLayout(new BoxLayout(formulaire, BoxLayout.Y_AXIS));
        this.formulaire = new JPanel();
        this.affichage = new JPanel();

        this.formulaire.setLayout(new BoxLayout(formulaire, BoxLayout.Y_AXIS));
        this.affichage.setLayout(new BorderLayout());

        JPanel panelTitre = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelDescription = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelDate = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelEmp = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelBouttons = new JPanel(new FlowLayout(FlowLayout.LEFT));


        this.titreMisField = new JTextField(20);
        this.descriptionMisField = new JTextArea(3,30);
        this.dateDebutMisField = new JFormattedTextField(dateFormatter);
        this.dateDebutMisField.setValue(new Date(System.currentTimeMillis()));
        this.dateFinMisField = new JFormattedTextField(dateFormatter);
        this.dateFinMisField.setValue(new Date(System.currentTimeMillis()));
        this.nbEmpField = new JTextField(5);
        this.logEmpField = new JTextField(15);
        this.buttonConfirmer = new JButton("Confirmer");
        this.ajouterCompetences = new JButton("Ajouter Compétences");
        this.ajouterEmployes = new JButton("Ajouter Employés");
        this.cardLayout = new CardLayout();
        this.cardLayoutPanel = new JPanel(cardLayout);
        this.titreLabel = new JLabel("création d'une mission");

        panelTitre.add(new JLabel("Titre Mission : "));
        panelTitre.add(titreMisField);
        formulaire.add(panelTitre);

        panelDescription.add(new JLabel("Description : "));
        panelDescription.add(descriptionMisField);
        formulaire.add(panelDescription);

        panelDate.add(new JLabel("Date de début : "));
        panelDate.add(dateDebutMisField);
        panelDate.add(new JLabel("Date de fin : "));
        panelDate.add(dateFinMisField);
        formulaire.add(panelDate);

        panelEmp.add(new JLabel("Nombre d'émployé necessaires : "));
        panelEmp.add(nbEmpField);
        panelEmp.add(new JLabel("login employé : "));
        panelEmp.add(logEmpField);
        formulaire.add(panelEmp);

        panelBouttons.add(ajouterCompetences);
        panelBouttons.add(ajouterEmployes);
        panelBouttons.add(buttonConfirmer);
        formulaire.add(panelBouttons);

        //partie Ajout compétence
        this.competenceTable = new JTable();
        this.competenceScrollPane = new JScrollPane(competenceTable);
        this.competenceScrollPane.setPreferredSize(new Dimension(300,300));
        this.cardLayoutPanel.add(this.competenceScrollPane,"tabCompetences");
        this.cardLayoutPanel.add(this.affichage,"affichage");




        splitPane.setLeftComponent(formulaire);
        affichage.add(new JTextArea(2,30));
        splitPane.setRightComponent(cardLayoutPanel);
        add(titreLabel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
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

    public java.sql.Date getDateDebutMisField() {
        return java.sql.Date.valueOf( this.dateDebutMisField.getText());
    }

    public java.sql.Date getDateFinMisField() {return java.sql.Date.valueOf(this.dateFinMisField.getText());}

    public int getNbEmpField() {
        return Integer.parseInt(this.nbEmpField.getText());
    }

    public String getLogEmpField() {
        return this.logEmpField.getText();
    }

    public void setCompetencesAjout(List<Competence> competences) {
        System.out.println("Mise à jour de la table des compétences avec " + competences.size() + " entrées."); // Debug
        String[] columnNames = {"Id", "Categorie","Nom (En)","Nom (FR)"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Competence cmp : competences) {
            Object[] row = {cmp.getIdCmp(),cmp.getIdCatCmp(),cmp.getNomCmpEn(),cmp.getNomCmpFr()};
            model.addRow(row);
        }

        this.competenceTable.setModel(model);

    }

    public JButton getAjouterCompetences() {
        return this.ajouterCompetences;
    }

    public JButton getAjouterEmployes() {
        return this.ajouterEmployes;
    }

    public void showPage(String pageName) {
        cardLayout.show(cardLayoutPanel, pageName);
    }

}
