package vue;

import modele.Competence;
import modele.Employe;
import modele.Mission;
import utilitaires.StyleManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ModificationMissionView extends JPanel {

    private JButton buttonConfirmer;
    private JTextField titreMisField;
    private JTextArea descriptionMisField;
    private JFormattedTextField dateDebutMisField;
    private JFormattedTextField dateFinMisField;
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
    private JTable listeCompetenceAjoutee;
    private JScrollPane listeCompetenceScrollPane;
    private JTable listeEmployesAjoutee;
    private JScrollPane listeEmployesScrollPane;
    private JTextField nomStaField;

    private static final String NOM_EN = "Nom (En)";
    private static final String NOM_FR = "Nom (Fr)";

    public ModificationMissionView() {
        StyleManager.setupFlatLaf();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //Partie Formulaire Mission
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        DateFormatter dateFormatter = new DateFormatter(formatter);

        this.splitPane = new JSplitPane();
        this.splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        this.splitPane.setDividerLocation(600);

        //this.formulaire.setLayout(new BoxLayout(formulaire, BoxLayout.Y_AXIS));
        this.formulaire = new JPanel();
        //this.affichage = new JPanel();

        this.formulaire.setLayout(new BoxLayout(formulaire, BoxLayout.Y_AXIS));
        //this.affichage.setLayout(new BorderLayout());

        JPanel panelTitre = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelDescription = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelDate = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelNbEmp = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel loginEmp = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelBouttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panellisteCompetences = new JPanel(new BorderLayout());
        JPanel panellisteEmployes = new JPanel(new BorderLayout());


        this.titreMisField = new JTextField(20);
        this.descriptionMisField = new JTextArea(3, 30);
        this.dateDebutMisField = new JFormattedTextField(dateFormatter);
        this.dateDebutMisField.setValue(new Date(System.currentTimeMillis()));
        this.dateFinMisField = new JFormattedTextField(dateFormatter);
        this.dateFinMisField.setValue(new Date(System.currentTimeMillis()));
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

        //Nbr d'employé dans mission
        panelNbEmp.add(new JLabel("Nombre d'émployé necessaires : "));
        panelNbEmp.add(nbEmpField);
        //login employé créateur mission
        loginEmp.add(new JLabel("login employé : "));
        loginEmp.add(logEmpField);
        formulaire.add(panelNbEmp);
        formulaire.add(loginEmp);

        //Tableau des compétences ajoutées à la mission
        JLabel competenceLabel = new JLabel("Compétences ajoutées :");
        panellisteCompetences.add(competenceLabel, BorderLayout.NORTH); // Place le label en haut
        this.listeCompetenceAjoutee = new JTable();
        String[] columnNames = {"Id", "Categorie", NOM_EN, NOM_FR };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int col) {  //cellules de la table ne sont plus editables
                return false;
            }
        };
        listeCompetenceAjoutee.setModel(model);
        this.listeCompetenceScrollPane = new JScrollPane(listeCompetenceAjoutee);
        listeCompetenceScrollPane.setPreferredSize(new Dimension(200, 100));
        panellisteCompetences.add(listeCompetenceScrollPane, BorderLayout.CENTER); // Place la table sous le label
        formulaire.add(panellisteCompetences);


        //Tableau des employés ajoutées à la mission
        JLabel employesLabel = new JLabel("Employés ajoutés :");
        panellisteEmployes.add(employesLabel, BorderLayout.NORTH); // Place le label en haut
        this.listeEmployesAjoutee = new JTable();
        String[] employesColumnNames = {"Prenom", "Nom", "Poste"};
        DefaultTableModel employesModel = new DefaultTableModel(employesColumnNames, 0) {
            public boolean isCellEditable(int row, int col) { //cellules de la table ne sont plus editables
                return false;
            }
        };
        listeEmployesAjoutee.setModel(employesModel);
        this.listeEmployesScrollPane = new JScrollPane(listeEmployesAjoutee);
        listeEmployesScrollPane.setPreferredSize(new Dimension(150, 200));
        panellisteEmployes.add(listeEmployesScrollPane, BorderLayout.CENTER); // Place la table sous le label
        formulaire.add(panellisteEmployes);

        //Boutons ajouts et confirmation
        panelBouttons.add(ajouterCompetences);
        panelBouttons.add(ajouterEmployes);
        panelBouttons.add(buttonConfirmer);


        //partie Ajout compétence
        this.competenceTable = new JTable();
        this.competenceScrollPane = new JScrollPane(competenceTable);
        this.competenceScrollPane.setPreferredSize(new Dimension(150, 200));
        this.cardLayoutPanel.add(this.competenceScrollPane, "tabCompetences");
        showPage("tabCompetences");

        //partie Ajout Employe
        this.employesTable = new JTable();
        this.employeScrollPane = new JScrollPane(employesTable);
        this.cardLayoutPanel.add(this.employeScrollPane, "tabEmployes");
       // showPage("tabEmployes");

        //Construction visuel final
        JPanel panelForm = new JPanel(new BorderLayout());
        JScrollPane scrollFormulaire = new JScrollPane(formulaire);
        panelForm.add(scrollFormulaire, BorderLayout.CENTER);
        panelForm.add(panelBouttons, BorderLayout.SOUTH);
        splitPane.setLeftComponent(panelForm);
        splitPane.setRightComponent(cardLayoutPanel);
        add(titreLabel);
        add(splitPane);

    }

    public JButton getButtonConfirmer() {
        return this.buttonConfirmer;
    }

    public String getTitreMisField() {
        return this.titreMisField.getText();
    }

    public String getDescriptionMisField() {
        return this.descriptionMisField.getText();
    }

    public Date getDateDebutMisField() {
        return java.sql.Date.valueOf(this.dateDebutMisField.getText());
    }

    public Date getDateFinMisField() {
        return java.sql.Date.valueOf(this.dateFinMisField.getText());
    }

    public String getLogEmpField() {
        return this.logEmpField.getText();
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

    public void setCompetencesAjout(List<Competence> competences) {
        //System.out.println("Mise à jour de la table des compétences avec " + competences.size() + " entrées."); // Debug
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


    //Affiche soit liste des employés ou liste compétences ajoutables à mission
    public void showPage(String pageName) {
        cardLayout.show(cardLayoutPanel, pageName);
    }

    //renvoi compétence selectionner avce double click dans liste compétence disponible
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

    //renvoi employé selectionner avec double click dans liste employés disponible
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

    public void setMission(Mission missionSelectionnee) {

        titreMisField.setText(missionSelectionnee.getTitreMis());
        dateDebutMisField.setText(missionSelectionnee.getDateDebutMis().toString());
        dateFinMisField.setText(missionSelectionnee.getDateFinMis().toString());
        descriptionMisField.setText(missionSelectionnee.getDescription());
        nbEmpField.setValue(missionSelectionnee.getNbEmpMis());
        nomStaField.setText(missionSelectionnee.getNomSta());
        logEmpField.setText(missionSelectionnee.getLoginEmp());
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

    /**
     * Remplit le tableau des compétences avec la liste fournie.
     * @param competences la liste des compétences à afficher.
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
        this.listeCompetenceAjoutee.setModel(model);
    }

    public void setMissionData(Mission mission) {
        this.titreMisField.setText(mission.getTitreMis());
        this.dateDebutMisField.setText(mission.getDateDebutMis().toString());
        this.dateFinMisField.setText(mission.getDateFinMis().toString());
        this.descriptionMisField.setText(mission.getDescription());
        this.logEmpField.setText(mission.getLoginEmp());
    }

    //retourne une liste d'employe ajoutés à la mission pour insertion BD à cretion mission
    public List<String> getLogEmployeAjoutees() {
        HashSet<String> empsALogin = new HashSet<>();
        DefaultTableModel model = (DefaultTableModel) listeEmployesAjoutee.getModel();
        //recuperation des login des emp ajoutés pour comparaison
        for (int i = 0; i < model.getRowCount(); i++) {
            String loginEmp = (String) model.getValueAt(i, 0);
            empsALogin.add(loginEmp);
        }
        List<String> resultSet = new ArrayList<>(empsALogin);
        return  resultSet;
    }

}


