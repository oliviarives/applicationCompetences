package controleur;

import modele.*;
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
    private static NavigationVue vueV;

    private final DAOCompetence competenceDao;
    private final DAOEmploye employeDao;
    private final DAOMission missionDao;
    private static DAOMission missionDaoInstance;

    private final EmployeVue empView;
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

    public NavigationControleur(NavigationVue navView) throws SQLException {
        this.vueV = navView;

        //initialisation des DAO
        this.competenceDao = new DAOCompetence();
        this.employeDao = new DAOEmploye();
        this.missionDao = new DAOMission();
        this.missionDaoInstance = new DAOMission();

        //initialisation des vues
        this.empView = new EmployeVue();
        this.missionV = new MissionVue();
        this.modifMissionV = new ModificationMissionVue();
        this.creaMissionV = new CreationMissionVue();
        this.infosEmpVue = new InformationEmpVue(employeDao);
        this.ajoutPersonnelV = new AjoutEmployeVue();
        this.modifEmployeVue = new ModificationEmployeVue();
        this.accueilV = new AccueilVue();

        //initialisation des contrôleurs
        this.missionC = new MissionControleur(missionV, missionDao, this, creaMissionV, modifMissionV);
        this.ajoutMissionControleur = new AjouterMissionControleur(creaMissionV, missionDao, this, competenceDao, employeDao, infosEmpVue);
        this.ajoutPersonnelC = new AjouterEmployeControleur(ajoutPersonnelV, employeDao, competenceDao, this);
        this.modifEmployeC = new ModifierEmployeControleur(modifEmployeVue, employeDao, competenceDao, this);

        EmployeControleur empC = new EmployeControleur(empView, employeDao, this);

        //chargement des données
        missionC.loadMissions();
        ajoutMissionControleur.loadCompetences();
        ajoutMissionControleur.loadEmployes();
        ajoutPersonnelC.loadCompetences();
        modifEmployeC.loadCompetences();
        empC.loadEmploye();

        CompetencesVue competencesV = new CompetencesVue();
        CompetenceControleur competenceC = new CompetenceControleur(competencesV, competenceDao);
        competenceC.loadCompetences();

        //Ajout des pages de l'app
        vueV.addPage(MOT_ACCUEIL, accueilV);
        vueV.addPage("Missions", missionV);
        vueV.addPage("Competences", competencesV);
        vueV.addPage("Creation", creaMissionV);
        vueV.addPage("Employe", empView);
        vueV.addPage("Modification", modifMissionV);
        vueV.addPage("AjouterEmploye", ajoutPersonnelV);
        vueV.addPage("ModifierEmploye", modifEmployeVue);
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

        empView.getButtonAjouterEmploye().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vueV.showPage("AjouterEmploye");
            }
        });

        empView.getButtonModifierEmploye().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Employe employeSelectionnee = empView.getEmployeSelectionne();
                if (employeSelectionnee != null) {
                    modifEmployeVue.setEmploye(employeSelectionnee);
                    vueV.showPage("ModifierEmploye");
                    modifEmployeC.loadCompetencesEmploye();
                }
            }
        });

        missionV.getButtonAjouterMission().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vueV.showPage("Creation");
                creaMissionV.resetFields();
                //employeDao.miseAJourEmpByCmpByDate();
            }
        });

        missionV.getButtonModifierMission().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Mission missionSelectionnee = missionV.getMissionSelectionnee();
                    if (missionSelectionnee == null) return;

                    if (missionSelectionnee.getIdSta() != 1) {
                        JOptionPane.showMessageDialog(null,
                                "Vous ne pouvez plus modifier cette mission, elle n'est plus en préparation.",
                                "WARNING!", JOptionPane.WARNING_MESSAGE);
                    } else {
                        ModifierMissionControleur modifMC = new ModifierMissionControleur(
                                modifMissionV, missionDao, NavigationControleur.this,
                                competenceDao, employeDao, missionSelectionnee);
                        modifMC.preRemplirFormulaire();

                        int is = missionV.getIdMissionSelect();
                        modifMC.setIdMissionSelect(is);
                        vueV.showPage("Modification");
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

                //MAJ dashboard (AccueilVue)
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
                vueV.showPage("Creation");
            }
        });
        /*creaMissionV.getInfoButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vueV.showPage("Infos");
            }
        })*/

    }

    public void loadEmploye() {
        List<Employe> emp = this.employeDao.findAll();
        empView.setEmploye(emp);
    }

    public static DAOMission getMissionDao() throws SQLException {
        return missionDaoInstance;
    }

    public static NavigationVue getVueV() {
        return vueV;
    }
}
