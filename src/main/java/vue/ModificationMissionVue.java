package vue;

import com.toedter.calendar.JDateChooser;
import modele.Competence;
import modele.Employe;
import modele.Mission;
import utilitaires.StyleManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
/**
 * Vue permettant la modification d'une mission
 * Contient les champs du formulaire de mission, les tables des compétences et employés,
 * et les fonctionnalités de mise à jour
 */
public class ModificationMissionVue extends JPanel {

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
    private JPanel affichage;
    private JTable competenceTable;
    private JScrollPane competenceScrollPane;
    private JTable employesTable;
    private JScrollPane employeScrollPane;
    private CardLayout cardLayout;
    private JPanel cardLayoutPanel;
    private JLabel titreLabel;
    private JTable tableCompetencesAjoutees;
    private JScrollPane listeCompetenceScrollPane;
    private JTable tableEmployesAjoutes;
    private JScrollPane listeEmployesScrollPane;
    private JTextField nomStaField;
    private JButton bouttonModifierDates;
    private JButton bouttonConfirmerDates;

    private static final String NOM_EN = "Nom (En)";
    private static final String NOM_FR = "Nom (Fr)";
    private static final String FORMAT_DATE = "yyyy-MM-dd";

    /**
     * Crée et initialise les composants de l'interface de modification d'une mission
     */
    public ModificationMissionVue() {
        StyleManager.setupFlatLaf();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Partie Formulaire Mission
        this.splitPane = new JSplitPane();
        this.splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        this.splitPane.setDividerLocation(600);

        this.formulaire = new JPanel();
        this.formulaire.setLayout(new BoxLayout(formulaire, BoxLayout.Y_AXIS));

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
        this.descriptionMisField = new JTextArea(3, 10);
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
        this.nomStaField = new JTextField(15);
        this.buttonConfirmer = new JButton("Confirmer");
        this.ajouterCompetences = new JButton("Ajouter Compétences");
        this.ajouterEmployes = new JButton("Ajouter Employés");
        this.cardLayout = new CardLayout();
        this.cardLayoutPanel = new JPanel(cardLayout);
        this.titreLabel = new JLabel("Modification d'une mission");
        this.logEmpField = new JTextField(15);

        // Titre mission
        panelTitre.add(new JLabel("Titre Mission : "));
        panelTitre.add(titreMisField);
        formulaire.add(panelTitre);

        // Description mission
        panelDescription.add(new JLabel("Description : "));
        panelDescription.add(descriptionMisField);
        formulaire.add(panelDescription);

        //Date debut et fin mission
        panelDate.add(new JLabel("Date de début : "));
        panelDate.add(dateDebutMisField);
        panelDate.add(new JLabel("Date de fin : "));
        panelDate.add(dateFinMisField);
        formulaire.add(panelDate);

        // Boutons modifier et confirmer les dates
        this.bouttonModifierDates = new JButton("Modifier les dates");
        this.bouttonConfirmerDates = new JButton("Confirmer");
        modifDates.add(bouttonModifierDates);
        modifDates.add(bouttonConfirmerDates);
        setDatesModifiables(false);
        formulaire.add(modifDates);

        // Nombre d'employés pour une mission
        panelNbEmp.add(new JLabel("Nombre d'émployé necessaires : "));
        panelNbEmp.add(nbEmpField);

        // Login de l'employé créateur de la mission
        loginEmp.add(new JLabel("login employé : "));
        loginEmp.add(logEmpField);
        formulaire.add(panelNbEmp);
        formulaire.add(loginEmp);

        // Tableau des compétences ajoutées à la mission
        JLabel competenceLabel = new JLabel("Compétences ajoutées :");
        panellisteCompetences.add(competenceLabel, BorderLayout.NORTH);
        this.tableCompetencesAjoutees = new JTable();
        String[] columnNames = {"Id", "Categorie", NOM_EN, NOM_FR };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int col) {  //cellules de la table ne sont plus editables
                return false;
            }
        };
        tableCompetencesAjoutees.setModel(model);
        this.listeCompetenceScrollPane = new JScrollPane(tableCompetencesAjoutees);
        listeCompetenceScrollPane.setPreferredSize(new Dimension(200, 100));
        panellisteCompetences.add(listeCompetenceScrollPane, BorderLayout.CENTER);
        formulaire.add(panellisteCompetences);


        // Tableau des employés ajoutés à la mission
        JLabel employesLabel = new JLabel("Employés ajoutés :");
        panellisteEmployes.add(employesLabel, BorderLayout.NORTH);
        this.tableEmployesAjoutes = new JTable();
        String[] employesColumnNames = {"login","Prenom", "Nom", "Poste"};
        DefaultTableModel employesModel = new DefaultTableModel(employesColumnNames, 0) {
            public boolean isCellEditable(int row, int col) { //cellules de la table ne sont plus editables
                return false;
            }
        };
        tableEmployesAjoutes.setModel(employesModel);
        TableColumn column = this.tableEmployesAjoutes.getColumnModel().getColumn(0);
        column.setMinWidth(0);
        column.setMaxWidth(0);
        column.setPreferredWidth(0);
        column.setResizable(false);
        this.listeEmployesScrollPane = new JScrollPane(tableEmployesAjoutes);
        listeEmployesScrollPane.setPreferredSize(new Dimension(150, 200));
        panellisteEmployes.add(listeEmployesScrollPane, BorderLayout.CENTER);
        formulaire.add(panellisteEmployes);

        // Boutons d'ajouts et de confirmation de la mission
        panelBouttons.add(ajouterCompetences);
        panelBouttons.add(ajouterEmployes);
        panelBouttons.add(buttonConfirmer);


        // Partie Ajout compétence
        this.competenceTable = new JTable();
        this.competenceScrollPane = new JScrollPane(competenceTable);
        this.competenceScrollPane.setPreferredSize(new Dimension(150, 200));
        this.cardLayoutPanel.add(this.competenceScrollPane, "tabCompetences");
        showPage("tabCompetences");

        // Partie Ajout employé
        this.employesTable = new JTable();
        this.employeScrollPane = new JScrollPane(employesTable);
        this.cardLayoutPanel.add(this.employeScrollPane, "tabEmployes");

        // Construction visuelle finale
        JPanel panelForm = new JPanel(new BorderLayout());
        JScrollPane scrollFormulaire = new JScrollPane(formulaire);
        panelForm.add(scrollFormulaire, BorderLayout.CENTER);
        panelForm.add(panelBouttons, BorderLayout.SOUTH);
        splitPane.setLeftComponent(panelForm);
        splitPane.setRightComponent(cardLayoutPanel);
        add(titreLabel);
        add(splitPane);

    }

    // Getters
    public JButton getButtonConfirmer() {
        return this.buttonConfirmer;
    }

    public String getTitreMisField() {
        return this.titreMisField.getText();
    }

    public JTextField getTitreMisField2(){return this.titreMisField;}

    public String getDescriptionMisField() {
        return this.descriptionMisField.getText();
    }

    public JTextArea getDescriptionMisField2() {
        return this.descriptionMisField;
    }

    public java.sql.Date getDateDebutMisField() {
        java.util.Date d = dateDebutMisField.getDate();
        return (d != null) ? new java.sql.Date(d.getTime()) : null;
    }
    public JDateChooser getDateDebutMisFieldComponent() {
        return this.dateDebutMisField;
    }
    public java.sql.Date getDateFinMisField() {
        java.util.Date d = dateFinMisField.getDate();
        return (d != null) ? new java.sql.Date(d.getTime()) : null;
    }

    public JDateChooser getDateFinMisFieldComponent() {
        return this.dateFinMisField;
    }

    public String getLogEmpField() {
        return this.logEmpField.getText();
    }

    public JTextField getLogEmpField2() {
        return this.logEmpField;
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

    public JTable getTableEmployesAjoutes() {
        return this.tableEmployesAjoutes;
    }

    public JTable getCompetenceTable() {
        return this.competenceTable;
    }

    public JTable getEmployesTable() {
        return this.employesTable;
    }

    public JSpinner getNbEmpFieldComponent() {
        return this.nbEmpField;
    }
    /**
     * Définit les compétences disponibles à ajouter à la mission
     * @param competences liste des compétences disponibles
     */
    public void setCompetencesAjout(List<Competence> competences) {
        String[] columnNames = {"Id", "Categorie", NOM_EN, NOM_FR};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        for (Competence cmp : competences) {
            Object[] row = {cmp.getIdCmp(), cmp.getIdCatCmp(), cmp.getNomCmpEn(), cmp.getNomCmpFr()};
            model.addRow(row);
        }
        this.competenceTable.setModel(model);
    }
    /**
     * Définit les employés disponibles à ajouter à la mission
     * @param employes liste des employés
     */
    public void setEmploye(List<Employe> employes) {
        String[] columnNames = {"login", "Prenom", "Nom", "Poste"};
        HashSet<String> listeLoginUnicite = new HashSet<>();
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (Employe e : employes) {
            Object[] row = { e.getLogin(), e.getPrenom(), e.getNom(), e.getPoste() };
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
     * Affiche l'onglet souhaité
     * @param pageName nom de l'onglet
     */
    public void showPage(String pageName) {
        cardLayout.show(cardLayoutPanel, pageName);
    }
    /**
     * @return compétence sélectionnée dans la table des compétences disponibles
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
     * @return employé sélectionné dans la table des employés disponibles
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
     * Ajoute une compétence à la table des compétences sélectionnées
     * @param cmp compétence à ajouter
     */
    public void ajouterCompetenceAjoutee(Competence cmp) {
        DefaultTableModel model = (DefaultTableModel) tableCompetencesAjoutees.getModel();
        Object[] row = {cmp.getIdCmp(), cmp.getIdCatCmp(), cmp.getNomCmpEn(), cmp.getNomCmpFr()};
        model.addRow(row);
    }
    /**
     * Ajoute un employé à la table des employés sélectionnés
     * @param emp employé à ajouter
     */
    public void ajouterEmployesAjoutee(Employe emp) {
        DefaultTableModel model = (DefaultTableModel) tableEmployesAjoutes.getModel();
        Object[] row = {emp.getLogin(),emp.getPrenom(), emp.getNom(), emp.getPoste()};
        model.addRow(row);
    }
    /**
     * Remplit les champs du formulaire avec les données d'une mission
     * @param missionSelectionnee mission à afficher
     */
    public void setMission(Mission missionSelectionnee) {

        titreMisField.setText(missionSelectionnee.getTitreMis());
        dateDebutMisField.setDate(missionSelectionnee.getDateDebutMis());
        dateFinMisField.setDate(missionSelectionnee.getDateFinMis());
        descriptionMisField.setText(missionSelectionnee.getDescription());
        nbEmpField.setValue(missionSelectionnee.getNbEmpMis());
        nomStaField.setText(missionSelectionnee.getNomSta());
        logEmpField.setText(missionSelectionnee.getLoginEmp());
    }
    /**
     * @return liste des compétences ajoutées à la mission
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
     * Remplit le tableau des compétences ajoutées avec les données fournies
     * @param competences liste des compétences à afficher
     */
    public void remplirTableauCompetences(List<Competence> competences) {
        String[] columnNames = {"Id", "Catégorie", NOM_EN, NOM_FR};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (Competence cmp : competences) {
            Object[] row = {cmp.getIdCmp(), cmp.getIdCatCmp(), cmp.getNomCmpEn(), cmp.getNomCmpFr()};
            model.addRow(row);
        }
        this.tableCompetencesAjoutees.setModel(model);
    }
    /**
     * Met à jour les champs de la vue avec les données d'une mission
     * @param mission mission à afficher
     */
    public void setMissionData(Mission mission) {
        this.titreMisField.setText(mission.getTitreMis());
        this.dateDebutMisField.setDate(mission.getDateDebutMis());
        this.dateFinMisField.setDate(mission.getDateFinMis());
        this.descriptionMisField.setText(mission.getDescription());
        this.logEmpField.setText(mission.getLoginEmp());
    }
    /**
     * Active ou désactive la modification des dates de la mission
     * @param b true pour activer la modification, false sinon
     */
    public void setDatesModifiables(boolean b){
        this.dateDebutMisField.setEnabled(b);
        this.dateFinMisField.setEnabled(b);
    }
    /**
     * @return bouton pour activer la modification des dates
     */
    public JButton getBoutonModifierDates(){
        return this.bouttonModifierDates;
    }
    /**
     * @return bouton pour confirmer les dates saisies
     */
    public JButton getBouttonConfirmerDates(){
        return this.bouttonConfirmerDates;
    }
    /**
     * @return liste des logins des employés ajoutés à la mission
     */
    public List<String> getLogEmployeAjoutees() {
        HashSet<String> empsALogin = new HashSet<>();
        DefaultTableModel model = (DefaultTableModel) tableEmployesAjoutes.getModel();
        //recuperation des login des emp ajoutés pour comparaison
        for (int i = 0; i < model.getRowCount(); i++) {
            String loginEmp = (String) model.getValueAt(i, 0);
            empsALogin.add(loginEmp);
        }
        List<String> resultSet = new ArrayList<>(empsALogin);
        return  resultSet;
    }
    /**
     * @return table contenant les employés ajoutés à la mission
     */
    public JTable getTableEmployesAjoutees() {
        return this.tableEmployesAjoutes;
    }

}


