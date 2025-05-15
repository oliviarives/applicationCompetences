package controleur;

import modele.Employe;
import modele.Mission;
import modele.dao.DAOCompetence;
import modele.dao.DAOEmploye;
import modele.dao.DAOMission;
import vue.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class NavigationControleur {

    private static final String MOT_ACCUEIL = "ACCUEIL";
    private static final String MOT_CREATION = "CREATION";
    private static final String MOT_MODIFICATION = "MODIFICATION";
    private static NavigationVue vueV;

    private final DAOCompetence competenceDao;
    private final static DAOEmploye employeDao = new DAOEmploye();
    private final DAOMission missionDao;
    private static DAOMission missionDaoInstance;

    private final static EmployeVue empV = new EmployeVue();
    private final MissionVue missionV;
    private final ModificationMissionVue modifMissionV;
    private final CreationMissionVue creaMissionV;
    private final InformationEmpVue infosEmpVue;

    private final MissionControleur missionC;
    private final AjouterMissionControleur ajoutMissionControleur;

    private final AjoutEmployeVue ajoutPersonnelV;
    private final AjouterEmployeControleur ajoutPersonnelC;
    private final ModificationEmployeVue modifEmployeVue;
    private final ModifierEmployeControleur modifEmployeC;

    private final AccueilVue accueilV;

    private final VacanceVue vacanceVue;
    private final VacanceControleur vacanceC;

    public NavigationControleur(NavigationVue navView) throws SQLException {
        this.vueV = navView;

        //Initialisation DAO
        this.competenceDao = new DAOCompetence();
        this.missionDao = new DAOMission();
        this.missionDaoInstance = new DAOMission();

        //Initialisation vues
        this.missionV = new MissionVue();
        this.modifMissionV = new ModificationMissionVue();
        this.creaMissionV = new CreationMissionVue();
        this.infosEmpVue = new InformationEmpVue(employeDao);
        this.ajoutPersonnelV = new AjoutEmployeVue();
        this.modifEmployeVue = new ModificationEmployeVue();
        this.accueilV = new AccueilVue();
        this.vacanceVue = new VacanceVue();

        //Initialisation contrôleurs
        this.missionC = new MissionControleur(missionV, missionDao);
        this.ajoutMissionControleur = new AjouterMissionControleur(creaMissionV, missionDao, this, competenceDao, employeDao, infosEmpVue);
        this.ajoutPersonnelC = new AjouterEmployeControleur(ajoutPersonnelV, employeDao, competenceDao);
        this.modifEmployeC = new ModifierEmployeControleur(modifEmployeVue, employeDao, competenceDao);
        this.vacanceC = new VacanceControleur(vacanceVue, employeDao);
        EmployeControleur empC = new EmployeControleur(empV, employeDao);

        //chargement données
        missionC.loadMissions();
        ajoutMissionControleur.loadCompetences();
        ajoutMissionControleur.loadEmployes();
        ajoutPersonnelC.loadCompetences();
        modifEmployeC.loadCompetences();
        empC.loadEmploye();

        CompetencesVue competencesV = new CompetencesVue();
        CompetenceControleur competenceC = new CompetenceControleur(competencesV, competenceDao);
        competenceC.loadCompetences();

        //ajout des pages de l'appli
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

        int nbEnPreparation = missionDao.countMissionsByStatus(1);
        int nbEnCours = missionDao.countMissionsByStatus(2);
        int nbTerminees = missionDao.countMissionsByStatus(3);
        Map<String, Integer> statsMois = missionDao.getMissionsStatsParMois();

        accueilV.updateDashboard(nbEnPreparation, nbEnCours, nbTerminees, statsMois);
        vueV.showPage(MOT_ACCUEIL);

        vueV.getButtonMissions().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vueV.showPage("Missions");
                missionC.loadMissions();
            }
        });

        vueV.getButtonCompetences().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vueV.showPage("Competences");
            }
        });

        vueV.getButtonEmploye().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vueV.showPage("Employe");
            }
        });

        empV.getButtonAjouterEmploye().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vueV.showPage("AjouterEmploye");
            }
        });

        empV.getButtonModifierEmploye().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Employe employeSelectionnee = empV.getEmployeSelectionne();
                if (employeSelectionnee != null) {
                    modifEmployeVue.setEmploye(employeSelectionnee);
                    vueV.showPage("ModifierEmploye");
                    modifEmployeC.loadCompetencesEmploye();
                }
            }
        });

        empV.getButtonVacance().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Employe employeSelectionne = empV.getEmployeSelectionne();
                if (employeSelectionne != null) {
                    vacanceVue.setLogin(employeSelectionne.getLogin());
                    vueV.showPage("Vacance");
                } else {
                    JOptionPane.showMessageDialog(null, "Veuillez sélectionner un employé.");
                }
            }
        });

        missionV.getButtonAjouterMission().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vueV.showPage(MOT_CREATION);
                creaMissionV.resetFields();
            }
        });

        missionV.getButtonModifierMission().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Mission missionSelectionnee = missionV.getMissionSelectionnee();
                    //if (missionSelectionnee == null) return;

                    if (missionSelectionnee.getIdSta() != 1) {
                        ModifierMissionControleur modifMC = new ModifierMissionControleur(
                                modifMissionV, missionDao, NavigationControleur.this,
                                competenceDao, employeDao, missionSelectionnee);
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
                                modifMissionV, missionDao, NavigationControleur.this,
                                competenceDao, employeDao, missionSelectionnee);
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
            }
        });

        vueV.getButtonAccueil().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int nbEnPreparation = missionDao.countMissionsByStatus(1);
                int nbEnCours = missionDao.countMissionsByStatus(2);
                int nbTerminees = missionDao.countMissionsByStatus(3);
                Map<String, Integer> statsMois = missionDao.getMissionsStatsParMois();

                // MAJ dashboard (AccueilVue)
                accueilV.updateDashboard(nbEnPreparation, nbEnCours, nbTerminees, statsMois);
                vueV.showPage(MOT_ACCUEIL);
            }
        });

        creaMissionV.getInfoButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                infosEmpVue.setEmpSelectionne(creaMissionV.getEmployeSelectionne());
                vueV.showPage("InfosEmp");
            }
        });

        infosEmpVue.getCroixRetour().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vueV.showPage(MOT_CREATION);
            }
        });
    }

    public static void loadEmploye() {
        List<Employe> emp = employeDao.findAll();
        empV.setEmploye(emp);
    }

    public static DAOMission getMissionDao() {
        return missionDaoInstance;
    }

    public static NavigationVue getVueV() {
        return vueV;
    }
}
