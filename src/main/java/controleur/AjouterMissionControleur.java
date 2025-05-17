package controleur;

import modele.Competence;
import modele.Employe;
import modele.Mission;
import modele.dao.DAOCompetence;
import modele.dao.DAOEmploye;
import modele.dao.DAOMission;
import vue.CreationMissionVue;
import vue.InformationEmpVue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

/**
 * Contrôleur pour créer d'une mission
 * Gère la validation des données, l'ajout de compétences et d'employés à la mission
 */
public class AjouterMissionControleur {
    /**
     * Vue de création de mission
     */
    private CreationMissionVue creationMV;
    /**
     * DAO pour les missions
     */
    private DAOMission daoMission;
    /**
     * Contrôleur de navigation
     */
    private NavigationControleur navC;
    /**
     * DAO pour les compétences
     */
    private DAOCompetence daoCompetence;
    /**
     * DAO pour les employés
     */
    private DAOEmploye daoEmploye;
    /**
     * Vue d'information employé
     */
    private InformationEmpVue informationEmpView;
    /**
     * Liste des compétences sélectionnées pour la mission
     */
    private List<Competence> listeCompetencesSelectionnees;
    /**
     * Liste des employés sélectionnés pour la mission
     */
    private List<Employe> listeEmployesSelectiones;
    /**
     * Message d'erreur utilisé dans les boîtes de dialogue
     */
    private static final String MESSAGE_ERREUR = "ERREUR";

    /**
     * Contrôleur qui initialise le contrôleur et configure les actions de la vue
     * @param creationMV vue de création de mission
     * @param daoMission DAO mission
     * @param navigationC contrôleur de navigation
     * @param daoComp DAO compétence
     * @param daoEmp DAO employé
     * @param infoEmpV vue d'information employé
     */
    public AjouterMissionControleur(CreationMissionVue creationMV, DAOMission daoMission, NavigationControleur navigationC, DAOCompetence daoComp, DAOEmploye daoEmp, InformationEmpVue infoEmpV) {
        this.creationMV = creationMV;
        this.daoMission = daoMission;
        this.navC = navigationC;
        this.daoCompetence = daoComp;
        this.daoEmploye = daoEmp;
        this.informationEmpView = infoEmpV;


        creationMV.getButtonConfirmer().addActionListener(e -> {
            // Récupération des valeurs saisies
            String titre = creationMV.getTitreMisFieldValue().trim();
            String login = creationMV.getLogEmpField().trim();
            java.sql.Date dateDebut = creationMV.getDateDebutMisField();
            java.sql.Date dateFin = creationMV.getDateFinMisField();
            String description = creationMV.getDescriptionMisFieldValue();

            // Vérification des champs obligatoires
            if(titre.isEmpty() || login.isEmpty() || dateDebut == null || dateFin == null) {
                JOptionPane.showMessageDialog(null,
                        "Les champs Titre, Login Employé, Date de début et Date de fin sont obligatoires.",
                        MESSAGE_ERREUR, JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Vérifier que la date de début et la date de fin ne sont pas antérieures à la date du jour
            java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
            if(dateDebut.before(today) || dateFin.before(today)) {
                JOptionPane.showMessageDialog(null,
                        "Les dates de début et de fin doivent être supérieures à la date d'aujourd'hui.",
                        MESSAGE_ERREUR, JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Vérifier que la date de fin n'est pas antérieure à la date de début
            if(dateFin.before(dateDebut)) {
                JOptionPane.showMessageDialog(null,
                        "La date de fin ne peut pas être antérieure à la date de début.",
                        MESSAGE_ERREUR, JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Vérifier la validité du login employé
            try {
                if(!daoEmploye.isLoginExists(login)) {
                    JOptionPane.showMessageDialog(null,
                            "Le login employé saisi n'est pas valide. Veuillez entrer un login existant.",
                            MESSAGE_ERREUR, JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,
                        "Erreur lors de la vérification du login employé.",
                        MESSAGE_ERREUR, JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(daoMission.missionTitleExists(titre)) {
                JOptionPane.showMessageDialog(null,
                        "Le titre de la mission existe déjà pour une mission non terminée. Veuillez choisir un autre titre.",
                        MESSAGE_ERREUR, JOptionPane.ERROR_MESSAGE);
                return;
            }

            Mission misInsert = new Mission(
                    titre,
                    dateDebut,
                    dateFin,
                    description,
                    new java.sql.Date(System.currentTimeMillis()),
                    creationMV.getNbEmpField(),
                    login,
                    1
            );

            List<Competence> cmpAjoutees = creationMV.getCompetencesAjoutees();
            List<String> logEmpAjoutes = creationMV.getLogEmployeAjoutees();

            try {
                daoMission.ajouterMission(misInsert);
                daoMission.ajouterCmpToMission(misInsert, cmpAjoutees);
                daoMission.ajouterEmpToMission(misInsert, logEmpAjoutes);

                if (!logEmpAjoutes.isEmpty()) {
                    daoMission.updateMissionStatus(misInsert, 2);
                }
                creationMV.showPage("tabCompetences");
                NavigationControleur.getVueV().getButtonMissions().doClick();
                creationMV.resetFields();
                for (String logEmp : logEmpAjoutes) {
                    daoEmp.addEmpCollaborerToMap(logEmp,dateDebut,dateFin);
                }

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        creationMV.getAjouterCompetences().addActionListener(
                e -> creationMV.showPage("tabCompetences")
        );

        creationMV.getAjouterEmployes().addActionListener(
                e -> {
                    if(creationMV.getCompetencesAjoutees().isEmpty() || creationMV.getDateDebutMisField()==null || creationMV.getDateFinMisField()==null) {
                        JOptionPane.showMessageDialog(null, "Veuillez d'abord saisir des compétences et des dates à la mission!",
                                "Erreur de saisie!", JOptionPane.WARNING_MESSAGE);}
                    else{
                        creationMV.showPage("tabEmployes");
                    }
                }
        );

        creationMV.getCompetenceTable().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    Competence cmp = creationMV.getCompetenceSelectionnee();
                    if (cmp != null) {
                        creationMV.ajouterCompetenceAjoutee(cmp);
                    }
                    List<Competence> lcmpAjout = creationMV.getCompetencesAjoutees();
                    List<Employe> listeEmployesSelectiones2 = daoEmploye.findEmpByCmp(lcmpAjout);
                    creationMV.setEmploye(listeEmployesSelectiones2); // Mise à jour de la table des employés
                }
            }
        });

        creationMV.getEmployesTable().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int nbEmpMax = creationMV.getNbEmpField();
                    int nbEmpAjoutes = creationMV.getTableEmployesAjoutee().getRowCount();

                    if (nbEmpAjoutes < nbEmpMax) {
                        Employe emp = creationMV.getEmployeSelectionne();
                        if (emp != null) {
                            creationMV.ajouterEmployesAjoutee(emp);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Vous ne pouvez pas ajouter plus d'employés que le nombre spécifié !",
                                "Limite employés atteinte", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        creationMV.getTableCompetencesAjoutees().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int selectedRow = creationMV.getTableCompetencesAjoutees().getSelectedRow();
                    if (selectedRow != -1) {
                        DefaultTableModel model = (DefaultTableModel) creationMV.getTableCompetencesAjoutees().getModel();
                        model.removeRow(selectedRow);
                    }
                    listeCompetencesSelectionnees = creationMV.getCompetencesAjoutees();
                    listeEmployesSelectiones = daoEmploye.findEmpByCmp(listeCompetencesSelectionnees);
                    for(Employe emp : listeEmployesSelectiones) {
                        System.out.println("empdslistecontroleur apres ajout cmp"+emp.getLogin());
                    }
                    creationMV.setEmploye(listeEmployesSelectiones);

                }
            }
        });
        // Retirer un employé des employés ajoutés
        creationMV.getTableEmployesAjoutee().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int selectedRow = creationMV.getTableEmployesAjoutee().getSelectedRow();
                    if (selectedRow != -1) {
                        DefaultTableModel model = (DefaultTableModel) creationMV.getTableEmployesAjoutee().getModel();
                        model.removeRow(selectedRow);
                    }
                }
            }
        });

        creationMV.getBoutonModifierDates().addActionListener(
                e -> creationMV.setDatesModifiables(true)
        );

        creationMV.getBouttonConfirmerDates().addActionListener(
                e -> {
                    creationMV.setDatesModifiables(false);
                    try {
                        JOptionPane.showMessageDialog(null, "Lorsque vous modifiez les dates, les employés ajoutés sont éffacés !",
                                "INFORMATION", JOptionPane.WARNING_MESSAGE);
                        creationMV.setEmploye(daoEmp.miseAJourEmpByCmpByDate(creationMV.getDateDebutMisField(), creationMV.getDateFinMisField()));
                        DefaultTableModel model = new DefaultTableModel(new String[]{"login","Prenom", "Nom", "Poste"},0);
                        creationMV.getTableEmployesAjoutees().setModel(model);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
        );
    }

    /**
     * Charge toutes les compétences depuis la base et les injecte dans la vue
     */
    public void loadCompetences(){
        List<Competence> competencesTable = daoCompetence.findAll();
        creationMV.setCompetencesAjout(competencesTable);
    }

    /**
     * Charge tous les employés depuis la base et les injecte dans la vue
     */
    public void loadEmployes(){
        List<Employe> employeTable = daoEmploye.findAll();
        creationMV.setEmploye(employeTable);
    }
}