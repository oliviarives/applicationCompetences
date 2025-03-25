package vue;

import com.toedter.calendar.JDateChooser;
import modele.Competence;
import modele.Employe;
import utilitaires.StyleManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CreationMissionView extends JPanel {
    private final JButton buttonConfirmer;
    private final JTextField titreMisField;
    private final JTextArea descriptionMisField;
    private final JDateChooser dateDebutMisField;
    private final JDateChooser dateFinMisField;
    private final JSpinner nbEmpField;
    private final JTextField logEmpField;
    private final JButton ajouterCompetences;
    private final JButton ajouterEmployes;
    private final JTable competenceTable;
    private final JTable employesTable;
    private final CardLayout cardLayout;
    private final JPanel cardLayoutPanel;
    private final JTable listeCompetenceAjoutee;
    private final JTable listeEmployesAjoutee;
    private final JButton bouttonModifierDates;
    private final JButton bouttonConfirmerDates;

    private static final String FORMAT_DATE = "yyyy-MM-dd";


    public CreationMissionView() {
        StyleManager.setupFlatLaf();
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        //Partie formulaire
        JSplitPane splitPane = new JSplitPane();
        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(600);

        JPanel formulaire = new JPanel();

        formulaire.setLayout(new BoxLayout(formulaire, BoxLayout.Y_AXIS));

        JPanel panelTitre = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelDescription = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelDate = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelNbEmp = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel loginEmp = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelBouttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panellisteCompetences = new JPanel(new BorderLayout());
        JPanel panellisteEmployes = new JPanel(new BorderLayout());
        JPanel modifDates = new JPanel(new FlowLayout(FlowLayout.LEFT));


        this.titreMisField = new JTextField(20);
        this.descriptionMisField = new JTextArea(3,30);
        this.dateDebutMisField = new JDateChooser();
        dateDebutMisField.setDateFormatString(FORMAT_DATE);
        dateDebutMisField.setDate(new java.util.Date());
        dateDebutMisField.setPreferredSize(new Dimension(100, 25));

        this.dateFinMisField = new JDateChooser();
        dateFinMisField.setDateFormatString(FORMAT_DATE);
        dateFinMisField.setDate(new java.util.Date());
        dateFinMisField.setPreferredSize(new Dimension(100, 25));

        SpinnerModel modelSpinner = new SpinnerNumberModel(0, 0, 30, 1);
        this.nbEmpField = new JSpinner(modelSpinner);
        this.logEmpField = new JTextField(15);
        this.buttonConfirmer = new JButton("Confirmer");
        this.ajouterCompetences = new JButton("Ajouter Compétences");
        this.ajouterEmployes = new JButton("Ajouter Employés");
        this.cardLayout = new CardLayout();
        this.cardLayoutPanel = new JPanel(cardLayout);
        JLabel titreLabel = new JLabel("Création d'une mission");

        //Titre Mission
        panelTitre.add(new JLabel("Titre Mission : "));
        panelTitre.add(titreMisField);
        formulaire.add(panelTitre);

        //Description Mission
        panelDescription.add(new JLabel("Description : "));
        panelDescription.add(descriptionMisField);
        formulaire.add(panelDescription);

        //Date debut et fin Mission
        panelDate.add(new JLabel("Date de début : "));
        panelDate.add(dateDebutMisField);
        panelDate.add(new JLabel("Date de fin : "));
        panelDate.add(dateFinMisField);
        formulaire.add(panelDate);

        //boutons modifier confirmer dates
        this.bouttonModifierDates = new JButton("Modifier les dates");
        this.bouttonConfirmerDates = new JButton("Confirmer");
        modifDates.add(bouttonModifierDates);
        modifDates.add(bouttonConfirmerDates);
        setDatesModifiables(false);
        formulaire.add(modifDates);


        //Nbr d'employé dans la mission
        panelNbEmp.add(new JLabel("Nombre d'employé nécessaires : "));
        panelNbEmp.add(nbEmpField);
        //login employé créateur mission
        loginEmp.add(new JLabel("Login employé : "));
        loginEmp.add(logEmpField);
        formulaire.add(panelNbEmp);
        formulaire.add(loginEmp);

        //Tableau des compétences ajoutées à la mission
        JLabel competenceLabel = new JLabel("Compétences ajoutées :");
        panellisteCompetences.add(competenceLabel, BorderLayout.NORTH); // Place le label en haut
        this.listeCompetenceAjoutee = new JTable();
        String[] columnNames = {"Id", "Categorie", "Nom (En)", "Nom (FR)"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {  //cellules de la table ne sont plus editables
                return false;
            }
        };
        listeCompetenceAjoutee.setModel(model);
        JScrollPane listeCompetenceScrollPane = new JScrollPane(listeCompetenceAjoutee);
        listeCompetenceScrollPane.setPreferredSize(new Dimension(200, 100));
        panellisteCompetences.add(listeCompetenceScrollPane, BorderLayout.CENTER); // Place la table sous le label
        formulaire.add(panellisteCompetences);


        //Tableau des employés ajoutés à la mission
        JLabel employesLabel = new JLabel("Employés ajoutés :");
        panellisteEmployes.add(employesLabel, BorderLayout.NORTH); // Place le label en haut
        this.listeEmployesAjoutee = new JTable();
        String[] employesColumnNames = {"Prenom", "Nom", "Poste"};
        DefaultTableModel employesModel = new DefaultTableModel(employesColumnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { //cellules de la table ne sont plus editables
                return false;
            }
        };
        listeEmployesAjoutee.setModel(employesModel);
        JScrollPane listeEmployesScrollPane = new JScrollPane(listeEmployesAjoutee);
        listeEmployesScrollPane.setPreferredSize(new Dimension(150, 200));
        panellisteEmployes.add(listeEmployesScrollPane, BorderLayout.CENTER); // Place la table sous le label
        formulaire.add(panellisteEmployes);

        //Boutons ajouts et confirmation
        panelBouttons.add(ajouterCompetences);
        panelBouttons.add(ajouterEmployes);
        panelBouttons.add(buttonConfirmer);


        //partie Ajout compétence
        this.competenceTable = new JTable();
        JScrollPane competenceScrollPane = new JScrollPane(competenceTable);
        competenceScrollPane.setPreferredSize(new Dimension(150,200));
        this.cardLayoutPanel.add(competenceScrollPane,"tabCompetences");



        //partie Ajout Employe
        this.employesTable = new JTable();
        JScrollPane employeScrollPane = new JScrollPane(employesTable);
        this.cardLayoutPanel.add(employeScrollPane,"tabEmployes");

        //Construction visuel final
        JPanel panelForm = new JPanel(new BorderLayout());
        JScrollPane scrollFormulaire = new JScrollPane(formulaire);
        panelForm.add(scrollFormulaire,BorderLayout.CENTER);
        panelForm.add(panelBouttons,BorderLayout.SOUTH);
        splitPane.setLeftComponent(panelForm);
        splitPane.setRightComponent(cardLayoutPanel);
        add(titreLabel);
        add(splitPane);



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
        java.util.Date d = dateDebutMisField.getDate();
        return (d != null) ? new java.sql.Date(d.getTime()) : null;
    }


    public String getLogEmpField() {
        return this.logEmpField.getText();
    }

    public java.sql.Date getDateFinMisField() {
        java.util.Date d = dateFinMisField.getDate();
        return (d != null) ? new java.sql.Date(d.getTime()) : null;
    }


    public int getNbEmpField() {
        return Integer.parseInt(this.nbEmpField.getValue().toString());
    }

    public JButton getAjouterCompetences() {
        return this.ajouterCompetences;
    }

    public JButton getAjouterEmployes() {
        return this.ajouterEmployes;
    }

    public JTable getListeCompetenceAjoutee() {
        return this.listeCompetenceAjoutee;
    }
    public JTable getListeEmployesAjoutee() {
        return this.listeEmployesAjoutee;
    }
    public JTable getCompetenceTable() {
        return this.competenceTable;
    }
    public JTable getEmployesTable() {
        return this.employesTable;
    }

    public JButton getBoutonModifierDates(){
        return this.bouttonModifierDates;
    }

    public JButton getBouttonConfirmerDates(){
        return this.bouttonConfirmerDates;
    }

    public void setCompetencesAjout(List<Competence> competences) {
        //System.out.println("Mise à jour de la table des compétences avec " + competences.size() + " entrées."); // Debug
        String[] columnNames = {"Id", "Categorie","Nom (En)","Nom (FR)"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        for (Competence cmp : competences) {
            Object[] row = {cmp.getIdCmp(),cmp.getIdCatCmp(),cmp.getNomCmpEn(),cmp.getNomCmpFr()};
            model.addRow(row);
        }
        this.competenceTable.setModel(model);
    }

    public void setEmploye(List<Employe> emp) {
        String[] columnNames = {"login","Prenom","Nom","Poste"};
        HashSet<String> listeLoginUnicite = new HashSet<>();
        DefaultTableModel model = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        for (Employe e : emp) {
            Object[] row = {e.getLogin(),e.getPrenom(),e.getNom(),e.getPoste()};
            if (listeLoginUnicite.add(e.getLogin())) {
                model.addRow(row);
            }

        }
        this.employesTable.setModel(model);
        TableColumn column = this.employesTable.getColumnModel().getColumn(0);
        column.setMinWidth(0);
        column.setMaxWidth(0);
        column.setPreferredWidth(0);
        column.setResizable(false);
    }


    //Affiche soit la liste des employés ou liste compétences ajoutables à mission
    public void showPage(String pageName) {
        cardLayout.show(cardLayoutPanel, pageName);
    }

    //renvoi la compétence sélectionnée avec un double click dans la liste compétence disponible
    public Competence getCompetenceSelectionnee() {
        int selectedRow = competenceTable.getSelectedRow();
        if (selectedRow != -1) { //  si une ligne est sélectionnée
            int id = (int) competenceTable.getValueAt(selectedRow, 0);
            String categorie = (String) competenceTable.getValueAt(selectedRow, 1);
            String nomEn = (String) competenceTable.getValueAt(selectedRow, 2);
            String nomFr = (String) competenceTable.getValueAt(selectedRow, 3);
            return new Competence(id, categorie, nomEn, nomFr);
        }
        return null; // Aucune ligne sélectionnée
    }

    //renvoi employé sélectionné avec double click dans la liste des employés disponible
    public Employe getEmployeSelectionne() {
        int selectedRow = employesTable.getSelectedRow();
        if (selectedRow != -1) { //  si une ligne est sélectionnée
            String prenom = (String) employesTable.getValueAt(selectedRow, 1);
            String nom = (String) employesTable.getValueAt(selectedRow, 2);
            String poste = (String) employesTable.getValueAt(selectedRow, 3);
            return new Employe(prenom, nom, poste);
        }
        return null; // Aucune ligne sélectionnée
    }
    //ajout compétence selectionner a tables des compétences ajoutées à la mission
    public void ajouterCompetenceAjoutee(Competence cmp) {
        DefaultTableModel model = (DefaultTableModel) listeCompetenceAjoutee.getModel();
        Object[] row = {cmp.getIdCmp(), cmp.getIdCatCmp(), cmp.getNomCmpEn(), cmp.getNomCmpFr()};
        model.addRow(row);
    }
    //ajout employé selectionner a tables des employés ajoutés à la mission
    public void ajouterEmployesAjoutee(Employe emp) {
        DefaultTableModel model = (DefaultTableModel) listeEmployesAjoutee.getModel();
        Object[] row = {emp.getPrenom(), emp.getNom(), emp.getPoste()};
        model.addRow(row);
    }
    //retourne une liste des compétences ajoutées à la mission
    public List<Competence> getCompetencesAjoutees() {
        List<Competence> competences = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) listeCompetenceAjoutee.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            int idCmp = (int) model.getValueAt(i, 0);
            String idCatCmp = (String) model.getValueAt(i, 1);
            String nomEn = (String) model.getValueAt(i, 2);
            String nomFr = (String) model.getValueAt(i, 3);

            competences.add(new Competence(idCmp, idCatCmp, nomEn, nomFr));
        }
        return competences;
    }
    //retourne une liste d'employé ajouté à la mission pour insertion BD à création mission
    public List<String> getLogEmployeAjoutees() {
        HashSet<String> empsALogin = new HashSet<>();
        DefaultTableModel model = (DefaultTableModel) listeEmployesAjoutee.getModel();
        //recuperation des logins des emp ajoutés pour comparaison
        for (int i = 0; i < model.getRowCount(); i++) {
            String loginEmp = (String) model.getValueAt(i, 0);
            empsALogin.add(loginEmp);
        }
        return new ArrayList<>(empsALogin);
    }

    public void setDatesModifiables(boolean b){
        this.dateDebutMisField.setEnabled(b);
        this.dateFinMisField.setEnabled(b);
    }


    public void resetFields() {
        // Vider les champs de texte
        titreMisField.setText("");
        descriptionMisField.setText("");
        logEmpField.setText("");

        // Réinitialiser les JDateChooser
        dateDebutMisField.setDate(null);
        dateFinMisField.setDate(null);

        // Réinitialiser le JSpinner
        nbEmpField.setValue(0); // ou 1, selon votre valeur par défaut souhaitée

        // Réinitialiser les tableaux d'ajouts
        DefaultTableModel modelComp = (DefaultTableModel) listeCompetenceAjoutee.getModel();
        modelComp.setRowCount(0);

        DefaultTableModel modelEmp = (DefaultTableModel) listeEmployesAjoutee.getModel();
        modelEmp.setRowCount(0);
    }



}