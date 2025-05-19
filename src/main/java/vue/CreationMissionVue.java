package vue;

import com.toedter.calendar.JDateChooser;
import modele.Competence;
import modele.Employe;
import utilitaires.StyleManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
/**
 * Vue pour la création d'une mission
 * Permet de saisir les informations de base d'une mission, d'ajouter des compétences et des employés
 */
public class CreationMissionVue extends JPanel {
    private final static String FORMAT_DATE = "yyyy-MM-dd";
    private JButton buttonConfirmer;
    private JTextField titreMisField;
    private JTextArea descriptionMisField;
    private JDateChooser dateDebutMisField;
    private JDateChooser dateFinMisField;
    private JSpinner nbEmpField;
    private JTextField logEmpField;
    private JButton ajouterCompetences;
    private JButton ajouterEmployes;
    private JSplitPane splitPane;
    private JPanel formulaire;
    private JTable competenceTable;
    private JScrollPane competenceScrollPane;
    private JTable employesTable;
    private JScrollPane employeScrollPane;
    private CardLayout cardLayout;
    private JPanel cardLayoutPanel;
    private JLabel titreLabel;
    private JTable tableCompetencesAjoutees;
    private JScrollPane listeCompetenceScrollPane;
    private JTable tableEmployesAjoutee;
    private JScrollPane listeEmployesScrollPane;
    private JButton bouttonModifierDates;
    private JButton bouttonConfirmerDates;
    private JButton infosEmp;


    public CreationMissionVue() {
        StyleManager.setupFlatLaf();
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        this.splitPane = new JSplitPane();
        this.splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        this.splitPane.setDividerLocation(600);

        this.formulaire = new JPanel();
        this.formulaire.setLayout(new BoxLayout(formulaire, BoxLayout.Y_AXIS));

        // Définition des panels
        JPanel panelTitre = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelDescription = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelDate = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelNbEmp = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel loginEmp = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelBouttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panellisteCompetences = new JPanel(new BorderLayout());
        JPanel panellisteEmployes = new JPanel(new BorderLayout());
        JPanel modifDates = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Champs du formulaire
        this.titreMisField = new JTextField(20);
        this.descriptionMisField = new JTextArea(3,10);
        this.descriptionMisField.setPreferredSize(new Dimension(350,10));
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


        // Panel qui affiche soit le tableau des compétences ou des employés
        this.cardLayout = new CardLayout();
        this.cardLayoutPanel = new JPanel(cardLayout);
        this.titreLabel = new JLabel("Création d'une mission");

        // Titre mission
        panelTitre.add(new JLabel("Titre Mission : "));
        panelTitre.add(titreMisField);
        formulaire.add(panelTitre);

        // Description mission
        panelDescription.add(new JLabel("Description : "));
        panelDescription.add(descriptionMisField);
        formulaire.add(panelDescription);

        // Date debut et fin mission
        panelDate.add(new JLabel("Date de début : "));
        panelDate.add(dateDebutMisField);
        panelDate.add(new JLabel("Date de fin : "));
        panelDate.add(dateFinMisField);
        formulaire.add(panelDate);

        // Boutons pour modifier et confirmer les dates
        this.bouttonModifierDates = new JButton("Modifier les dates");
        this.bouttonConfirmerDates = new JButton("Confirmer");
        modifDates.add(bouttonModifierDates);
        modifDates.add(bouttonConfirmerDates);
        setDatesModifiables(false);
        formulaire.add(modifDates);

        //Nombre d'employés nécessaires pour la mission
        panelNbEmp.add(new JLabel("Nombre d'employé nécessaires : "));
        panelNbEmp.add(nbEmpField);

        //Login employé créateur de la mission
        loginEmp.add(new JLabel("Login employé : "));
        loginEmp.add(logEmpField);
        formulaire.add(panelNbEmp);
        formulaire.add(loginEmp);

        //Tableau des compétences ajoutées à la mission
        JLabel competenceLabel = new JLabel("Compétences ajoutées :");
        panellisteCompetences.add(competenceLabel, BorderLayout.NORTH);
        this.tableCompetencesAjoutees = new JTable();
        String[] columnNames = {"Id", "Categorie", "Nom (En)", "Nom (FR)"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tableCompetencesAjoutees.setModel(model);
        this.listeCompetenceScrollPane = new JScrollPane(tableCompetencesAjoutees);
        listeCompetenceScrollPane.setPreferredSize(new Dimension(200, 100));
        panellisteCompetences.add(listeCompetenceScrollPane, BorderLayout.CENTER);
        formulaire.add(panellisteCompetences);


        // Tableau des employés ajoutées à la mission
        JLabel employesLabel = new JLabel("Employés ajoutés :");
        panellisteEmployes.add(employesLabel, BorderLayout.NORTH);
        this.tableEmployesAjoutee = new JTable();
        String[] employesColumnNames = {"login","Prenom", "Nom", "Poste"};
        DefaultTableModel employesModel = new DefaultTableModel(employesColumnNames, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tableEmployesAjoutee.setModel(employesModel);
        TableColumn column = this.tableEmployesAjoutee.getColumnModel().getColumn(0);
        column.setMinWidth(0);
        column.setMaxWidth(0);
        column.setPreferredWidth(0);
        column.setResizable(false);
        this.listeEmployesScrollPane = new JScrollPane(tableEmployesAjoutee);
        listeEmployesScrollPane.setPreferredSize(new Dimension(150, 200));
        panellisteEmployes.add(listeEmployesScrollPane, BorderLayout.CENTER);
        formulaire.add(panellisteEmployes);

        // Boutons d'ajouts des compétences et employés et de confirmation de la création de la mission
        panelBouttons.add(ajouterCompetences);
        panelBouttons.add(ajouterEmployes);
        panelBouttons.add(buttonConfirmer);


        // Partie ajout compétence
        this.competenceTable = new JTable();
        this.competenceScrollPane = new JScrollPane(competenceTable);
        this.competenceScrollPane.setPreferredSize(new Dimension(150,200));
        this.cardLayoutPanel.add(this.competenceScrollPane,"tabCompetences");

        // Partie ajout employé
        this.employesTable = new JTable();
        this.employeScrollPane = new JScrollPane(employesTable);
        JPanel panelBouttonInfosEmp = new JPanel();
        this.infosEmp = new JButton("Informations Employé");
        panelBouttonInfosEmp.add(infosEmp);
        JPanel partieEmp = new JPanel(new BorderLayout());
        partieEmp.add(employeScrollPane, BorderLayout.CENTER);
        partieEmp.add(panelBouttonInfosEmp, BorderLayout.SOUTH);
        this.cardLayoutPanel.add(partieEmp,"tabEmployes");

        // Construction visuelle finale
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

    public JTable getTableCompetencesAjoutees() {
        return this.tableCompetencesAjoutees;
    }
    public JTable getTableEmployesAjoutee() {
        return this.tableEmployesAjoutee;
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

    /**
     * Définit les compétences affichées dans la liste de sélection
     * @param competences Liste des compétences à afficher
     */
    public void setCompetencesAjout(List<Competence> competences) {
        String[] columnNames = {"Id", "Categorie","Nom (En)","Nom (FR)"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0){
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
    /**
     * Définit les employés affichés dans la liste de sélection
     * @param emp Liste des employés à afficher
     */
    public void setEmploye(List<Employe> emp) {
        String[] columnNames = {"login","Prenom","Nom","Poste"};
        HashSet<String> listeLoginUnicite = new HashSet<>();
        DefaultTableModel model = new DefaultTableModel(columnNames, 0){
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


    /**
     * Affiche un panneau selon son nom
     * @param pageName Nom du panneau à afficher
     */
    public void showPage(String pageName) {
        cardLayout.show(cardLayoutPanel, pageName);
    }

    /**
     * Retourne la compétence sélectionnée dans la liste de sélection
     * @return Compétence sélectionnée ou null
     */
    public Competence getCompetenceSelectionnee() {
        int selectedRow = competenceTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) competenceTable.getValueAt(selectedRow, 0);
            String categorie = (String) competenceTable.getValueAt(selectedRow, 1);
            String nomEn = (String) competenceTable.getValueAt(selectedRow, 2);
            String nomFr = (String) competenceTable.getValueAt(selectedRow, 3);
            return new Competence(id, categorie, nomEn, nomFr);
        }
        return null;
    }

    /**
     * Retourne l'employé sélectionné dans la liste de sélection
     * @return Employé sélectionné ou null
     */
    public Employe getEmployeSelectionne() {
        int selectedRow = employesTable.getSelectedRow();
        if (selectedRow != -1) {
            String login = (String) employesTable.getValueAt(selectedRow, 0);
            String prenom = (String) employesTable.getValueAt(selectedRow, 1);
            String nom = (String) employesTable.getValueAt(selectedRow, 2);
            String poste = (String) employesTable.getValueAt(selectedRow, 3);
            return new Employe(login,prenom, nom, poste);
        }
        return null;
    }
    /**
     * Ajoute une compétence à la liste des compétences ajoutées
     * @param cmp Compétence à ajouter
     */
    public void ajouterCompetenceAjoutee(Competence cmp) {
        DefaultTableModel model = (DefaultTableModel) tableCompetencesAjoutees.getModel();
        Object[] row = {cmp.getIdCmp(), cmp.getIdCatCmp(), cmp.getNomCmpEn(), cmp.getNomCmpFr()};
        model.addRow(row);
    }
    /**
     * Ajoute un employé à la liste des employés ajoutés
     * @param emp Employé à ajouter
     */
    public void ajouterEmployesAjoutee(Employe emp) {
        DefaultTableModel model = (DefaultTableModel) tableEmployesAjoutee.getModel();
        Object[] row = {emp.getLogin(),emp.getPrenom(), emp.getNom(), emp.getPoste()};
        model.addRow(row);
    }
    /**
     * Retourne la liste des compétences ajoutées à la mission
     * @return Liste de compétences
     */
    public List<Competence> getCompetencesAjoutees() {
        List<Competence> competences = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) tableCompetencesAjoutees.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            int idCmp = (int) model.getValueAt(i, 0);
            String idCatCmp = (String) model.getValueAt(i, 1);
            String nomEn = (String) model.getValueAt(i, 2);
            String nomFr = (String) model.getValueAt(i, 3);

            competences.add(new Competence(idCmp, idCatCmp, nomEn, nomFr));
        }
        return competences;
    }
    /**
     * Retourne la liste des logins des employés ajoutés à la mission
     * @return Liste des logins
     */
    public List<String> getLogEmployeAjoutees() {
        HashSet<String> empsALogin = new HashSet<>();
        DefaultTableModel model = (DefaultTableModel) tableEmployesAjoutee.getModel();
        // Récuperation des logins des employés ajoutés pour comparaison
        for (int i = 0; i < model.getRowCount(); i++) {
            String loginEmp = (String) model.getValueAt(i, 0);
            empsALogin.add(loginEmp);
        }
        // Hashset transformé en ArrayList
        List<String> resultSet = new ArrayList<>(empsALogin);
        return  resultSet;
    }

    /**
     * Active ou désactive la modification des dates de mission
     * @param b true pour activer, false pour désactiver
     */
    public void setDatesModifiables(boolean b){
        this.dateDebutMisField.setEnabled(b);
        this.dateFinMisField.setEnabled(b);
    }

    /**
     * Réinitialise tous les champs du formulaire
     */
    public void resetFields() {

        titreMisField.setText("");
        descriptionMisField.setText("");
        logEmpField.setText("");

        dateDebutMisField.setDate(null);
        dateFinMisField.setDate(null);

        nbEmpField.setValue(0);

        DefaultTableModel modelComp = (DefaultTableModel) tableCompetencesAjoutees.getModel();
        modelComp.setRowCount(0);

        DefaultTableModel modelEmp = (DefaultTableModel) tableEmployesAjoutee.getModel();
        modelEmp.setRowCount(0);
    }

    public JButton getInfoButton() {
        return this.infosEmp;
    }

    public JTable getTableEmployesAjoutees() {
        return this.tableEmployesAjoutee;
    }

}