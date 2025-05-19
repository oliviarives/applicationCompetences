package controleur;

import modele.Employe;
import modele.Mission;
import modele.dao.DAOCompetence;
import modele.dao.DAOEmploye;
import modele.dao.DAOMission;
import vue.*;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Contrôleur de navigation entre les différentes vues de l'application
 * Initialise les vues, les contrôleurs et gère les actions des boutons principaux
 */
public class NavigationControleur {
    /**
     * Constante vue d'accueil
     */
    private static final String MOT_ACCUEIL = "ACCUEIL";
    /**
     * Constante vue de création de mission
     */
    private static final String MOT_CREATION = "CREATION";
    /**
     * Constante vue de modification de mission
     */
    private static final String MOT_MODIFICATION = "MODIFICATION";
    /**
     * Vue principale contenant les boutons de navigation
     */
    private static NavigationVue vueV;
    /**
     * DAO pour la gestion des compétences
     */
    private final DAOCompetence competenceDao;
    /**
     * DAO statique pour la gestion des employés
     */
    private final static DAOEmploye employeDao = new DAOEmploye();
    /**
     * DAO pour la gestion des missions
     */
    private final DAOMission missionDao;
    /**
     * DAO statique pour accès global aux missions
     */
    private static DAOMission missionDaoInstance;
    /**
     * Vue des employés
     */
    private final static EmployeVue empV = new EmployeVue();
    /**
     * Vue des missions
     */
    private final MissionVue missionV;
    /**
     * Vue de modification d'une mission
     */
    private final ModificationMissionVue modifMissionV;
    /**
     * Vue de création de mission
     */
    private final CreationMissionVue creaMissionV;
    /**
     * Vue d'information sur un employé
     */
    private final InformationEmpVue infosEmpVue;
    /**
     * Contrôleur des missions
     */
    private final MissionControleur missionC;
    /**
     * Contrôleur de création de mission
     */
    private final AjouterMissionControleur ajoutMissionControleur;
    /**
     * Vue d'ajout d'un employé
     */
    private final AjoutEmployeVue ajoutPersonnelV;
    /**
     * Contrôleur d'ajout d'un employé
     */
    private final AjouterEmployeControleur ajoutPersonnelC;
    /**
     * Vue de modification d'un employé
     */
    private final ModificationEmployeVue modifEmployeVue;
    /**
     * Contrôleur de modification d'un employé
     */
    private final ModifierEmployeControleur modifEmployeC;
    /**
     * Vue d'accueil de l'application
     */
    private final AccueilVue accueilV;
    /**
     * Vue de gestion des vacances
     */
    private final VacanceVue vacanceVue;
    /**
     * Contrôleur de gestion des vacances
     */
    private final VacanceControleur vacanceC;
    /**
     * Initialise toutes les vues, les contrôleurs, charge les données et configure les actions de navigation
     * @param navView vue de navigation
     * @throws SQLException en cas d'erreur lors du chargement initial des données
     */
    public NavigationControleur(NavigationVue navView) throws SQLException {
        this.vueV = navView;

        //Initialisation des DAO
        this.competenceDao = new DAOCompetence();
        this.missionDao = new DAOMission();
        this.missionDaoInstance = new DAOMission();

        //Initialisation des vues
        this.missionV = new MissionVue();
        this.modifMissionV = new ModificationMissionVue();
        this.creaMissionV = new CreationMissionVue();
        this.infosEmpVue = new InformationEmpVue(employeDao);
        this.ajoutPersonnelV = new AjoutEmployeVue();
        this.modifEmployeVue = new ModificationEmployeVue();
        this.accueilV = new AccueilVue();
        this.vacanceVue = new VacanceVue();

        //Initialisation des contrôleurs
        this.missionC = new MissionControleur(missionV, missionDao);
        this.ajoutMissionControleur = new AjouterMissionControleur(creaMissionV, missionDao, this, competenceDao, employeDao, infosEmpVue);
        this.ajoutPersonnelC = new AjouterEmployeControleur(ajoutPersonnelV, employeDao, competenceDao);
        this.modifEmployeC = new ModifierEmployeControleur(modifEmployeVue, employeDao, competenceDao);
        this.vacanceC = new VacanceControleur(vacanceVue, employeDao);
        EmployeControleur empC = new EmployeControleur(empV, employeDao);

        //Chargement des données
        missionC.loadMissions();
        ajoutMissionControleur.loadCompetences();
        ajoutMissionControleur.loadEmployes();
        ajoutPersonnelC.loadCompetences();
        modifEmployeC.loadCompetences();
        empC.loadEmploye();

        CompetencesVue competencesV = new CompetencesVue();
        CompetenceControleur competenceC = new CompetenceControleur(competencesV, competenceDao);
        competenceC.loadCompetences();

        //Ajout des pages de l'application
        vueV.addPage(MOT_ACCUEIL, accueilV);
        vueV.addPage("Missions", missionV);
        vueV.addPage("Competences", competencesV);
        vueV.addPage(MOT_CREATION, creaMissionV);
        vueV.addPage("Employe", empV);
        vueV.addPage(MOT_MODIFICATION, modifMissionV);
        vueV.addPage("AjouterEmploye", ajoutPersonnelV);
        vueV.addPage("ModifierEmploye", modifEmployeVue);
        vueV.addPage("Vacance", vacanceVue);
        vueV.addPage("InfosEmp", infosEmpVue);

        // Compter le nombre de missions par statut
        int nbEnPreparation = missionDao.countMissionsByStatus(2);
        int nbEnCours = missionDao.countMissionsByStatus(3);
        int nbTerminees = missionDao.countMissionsByStatus(4);
        Map<String, Integer> statsMois = missionDao.getMissionsStatsParMois();

        // Mise à jour du dashboard
        accueilV.updateDashboard(nbEnPreparation, nbEnCours, nbTerminees, statsMois);
        vueV.showPage(MOT_ACCUEIL);

        vueV.getButtonMissions().addActionListener(e -> {
            vueV.showPage("Missions");
            try {
                missionC.loadMissions();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        vueV.getButtonCompetences().addActionListener(e -> vueV.showPage("Competences"));

        vueV.getButtonEmploye().addActionListener(e -> {
            loadEmploye();
            vueV.showPage("Employe");
        }); 

        empV.getButtonAjouterEmploye().addActionListener(e -> vueV.showPage("AjouterEmploye"));

        empV.getButtonModifierEmploye().addActionListener(e -> {
            Employe employeSelectionnee = empV.getEmployeSelectionne();
            if (employeSelectionnee != null) {
                modifEmployeVue.setEmploye(employeSelectionnee);
                vueV.showPage("ModifierEmploye");
                modifEmployeC.loadCompetencesEmploye();
            }
        });

        empV.getButtonVacance().addActionListener(e -> {
            Employe employeSelectionne = empV.getEmployeSelectionne();
            if (employeSelectionne != null) {
                vacanceVue.setLogin(employeSelectionne.getLogin());
                vueV.showPage("Vacance");
            } else {
                JOptionPane.showMessageDialog(null, "Veuillez sélectionner un employé.");
            }
        });

        missionV.getButtonAjouterMission().addActionListener(e -> {
            vueV.showPage(MOT_CREATION);
            creaMissionV.resetFields();
        });

        missionV.getButtonModifierMission().addActionListener(e -> {
            try {
                Mission missionSelectionnee = missionV.getMissionSelectionnee();
                // Griser les champs de la vue modification mission si le statut n'est pas "En préparation"
                if (missionSelectionnee.getIdSta() != 1) {
                    ModifierMissionControleur modifMC = new ModifierMissionControleur(
                            modifMissionV, missionDao, competenceDao, employeDao, missionSelectionnee);
                    modifMC.preRemplirFormulaire();

                    int is = missionV.getIdMissionSelect();
                    modifMC.setIdMissionSelect(is);
                    vueV.showPage(MOT_MODIFICATION);
                    modifMissionV.getTitreMisField2().setEditable(false);
                    modifMissionV.getDescriptionMisField2().setEditable(false);
                    modifMissionV.getLogEmpField2().setEditable(false);
                    modifMissionV.getButtonConfirmer().setEnabled(false);
                    modifMissionV.getAjouterCompetences().setEnabled(false);
                    modifMissionV.getAjouterEmployes().setEnabled(false);
                    modifMissionV.getDateDebutMisFieldComponent().setEnabled(false);
                    modifMissionV.getDateFinMisFieldComponent().setEnabled(false);
                    modifMissionV.getNbEmpFieldComponent().setEnabled(false);
                    modifMissionV.getTableCompetencesAjoutees().setEnabled(false);
                    modifMissionV.getTableEmployesAjoutes().setEnabled(false);
                    modifMissionV.getCompetenceTable().setEnabled(false);
                    modifMissionV.getBouttonConfirmerDates().setEnabled(false);
                    modifMissionV.getBoutonModifierDates().setEnabled(false);
                } else {
                    ModifierMissionControleur modifMC = new ModifierMissionControleur(
                            modifMissionV, missionDao, competenceDao, employeDao, missionSelectionnee);
                    modifMC.preRemplirFormulaire();

                    int is = missionV.getIdMissionSelect();
                    modifMC.setIdMissionSelect(is);
                    vueV.showPage(MOT_MODIFICATION);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,
                        "Erreur lors de la récupération de la mission : " + ex.getMessage(),
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        vueV.getButtonAccueil().addActionListener(e -> {
            int nbEnPreparation1 = missionDao.countMissionsByStatus(1);
            int nbEnCours1 = missionDao.countMissionsByStatus(2);
            int nbTerminees1 = missionDao.countMissionsByStatus(3);
            Map<String, Integer> statsMois1 = missionDao.getMissionsStatsParMois();

            // MAJ dashboard (AccueilVue)
            accueilV.updateDashboard(nbEnPreparation1, nbEnCours1, nbTerminees1, statsMois1);
            vueV.showPage(MOT_ACCUEIL);
        });

        creaMissionV.getInfoButton().addActionListener(e -> {
            infosEmpVue.setEmpSelectionne(creaMissionV.getEmployeSelectionne());
            vueV.showPage("InfosEmp");
        });

        infosEmpVue.getCroixRetour().addActionListener(e -> vueV.showPage(MOT_CREATION));
    }
    /**
     * Recharge les employés et les met à jour dans la vue employé
     */
    public static void loadEmploye() {
       // List<Employe> emp = employeDao.findAll();
        List<Employe> emp = employeDao.getAllDataEmploye();
        empV.setEmploye(emp);
    }
    /**
     * Fournit une instance statique du DAO mission
     * @return instance de DAOMission
     */
    public static DAOMission getMissionDao() {
        return missionDaoInstance;
    }
    /**
     * Fournit une instance statique de la vue de navigation
     * @return instance de NavigationVue
     */
    public static NavigationVue getVueV() {
        return vueV;
    }

}
