package controleur;

import modele.Employe;
import modele.Mission;
import modele.dao.DAOCompetence;
import modele.dao.DAOEmploye;
import modele.dao.DAOMission;
import vue.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class NavigationControleur {
    private static NavigationView vueV;
    private CompetencesView competencesV = new CompetencesView();
    private DAOCompetence competenceDao = new DAOCompetence();
    private CompetenceControleur competenceC = new CompetenceControleur(competencesV, competenceDao);

    private EmployeView empView = new EmployeView();
    private DAOEmploye employeDao = new DAOEmploye();
    private EmployeControleur empC = new EmployeControleur(empView,employeDao, this);

    private MissionView misssionV = new MissionView();
    private ModificationMissionView modifMissionV = new ModificationMissionView();
    private CreationMissionView creaMissionV = new CreationMissionView();
    private DAOMission missionDao = new DAOMission();
    private MissionControleur missionC = new MissionControleur(misssionV, missionDao, this, creaMissionV, modifMissionV);
    private AjouterMissionControleur ajoutMC = new AjouterMissionControleur(creaMissionV,missionDao,this,competenceDao,employeDao);

    private AjoutPersonnelVue ajoutPersonnelV = new AjoutPersonnelVue();
    private AjouterPersonnelControleur ajoutPersonnelC= new AjouterPersonnelControleur(ajoutPersonnelV, employeDao, competenceDao, this);

    private AccueilVue accueilV = new AccueilVue();

    private static DAOMission missionDaoInstance; // variable statique pour DAOMission

    public NavigationControleur( NavigationView navView) throws SQLException {
        this.vueV =navView;

        //EmployeView empView = new EmployeView();
        //DAOEmploye employeDao = new DAOEmploye();
        //EmployeControleur empC = new EmployeControleur(empView, employeDao, this);

        MissionView missionV = new MissionView();
        ModificationMissionView modifMissionV = new ModificationMissionView();
        CreationMissionView creaMissionV = new CreationMissionView();
        DAOMission missionDao = new DAOMission();
        MissionControleur missionC = new MissionControleur(missionV, missionDao, this, creaMissionV, modifMissionV);
        AjouterMissionControleur ajoutMC = new AjouterMissionControleur(creaMissionV, missionDao, this, competenceDao, employeDao);
        missionDaoInstance = missionDao;

        AjoutPersonnelVue ajoutPersonnelV = new AjoutPersonnelVue();
        AjouterPersonnelControleur ajoutPersonnelC = new AjouterPersonnelControleur(ajoutPersonnelV, employeDao, competenceDao, this);

        missionC.loadMissions();
        competenceC.loadCompetences();
        ajoutMC.loadCompetences();
        ajoutMC.loadEmployes();
        ajoutPersonnelC.loadCompetences();
        empC.loadEmploye();


        vueV.addPage("Accueil", accueilV);
        vueV.addPage("Missions", missionV);
        vueV.addPage("Competences", competencesV);
        vueV.addPage("Creation", creaMissionV);
        vueV.addPage("Employe", empView);
        vueV.addPage("Modification", modifMissionV);
        vueV.addPage("AjouterEmploye", ajoutPersonnelV);

        int nbEnPreparation = missionDao.countMissionsByStatus(1);
        int nbEnCours = missionDao.countMissionsByStatus(2);
        int nbTerminees = missionDao.countMissionsByStatus(3);
        Map<String, Integer> statsMois = missionDao.getMissionsStatsParMois();

        accueilV.updateDashboard(nbEnPreparation, nbEnCours, nbTerminees, statsMois);
        vueV.showPage("Accueil");

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

        empView.getBouttonAjouterEmploye().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vueV.showPage("AjouterEmploye");
            }
        });

        missionV.getButtonAjouterMission().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vueV.showPage("Creation");
            }
        });

        missionV.getButtonModifierMission().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Mission missionSelectionnee = missionV.getMissionSelectionnee();
                if (missionSelectionnee != null) {
                    // Crée une instance de ModifierMissionControleur en passant la mission sélectionnée
                    ModifierMissionControleur modifMC = new ModifierMissionControleur(modifMissionV, missionDao, NavigationControleur.this, competenceDao, employeDao, missionSelectionnee);

                    try {
                        modifMC.preRemplirFormulaire();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    vueV.showPage("Modification");
                }
            }
        });


        vueV.getButtonAccueil().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Récupérer les données réelles depuis le DAO/Service
                int nbEnPreparation = missionDao.countMissionsByStatus(1);
                int nbEnCours = missionDao.countMissionsByStatus(2);
                int nbTerminees = missionDao.countMissionsByStatus(3);
                Map<String, Integer> statsMois = missionDao.getMissionsStatsParMois();

                // Mettre à jour le dashboard (AccueilVue)
                accueilV.updateDashboard(nbEnPreparation, nbEnCours, nbTerminees, statsMois);
                vueV.showPage("Accueil");
            }
        });

    }

    public void loadEmploye() {
        List<Employe> emp = this.employeDao.findAll();
        empView.setEmploye(emp);
    }

    public static DAOMission getMissionDao() {
        return missionDaoInstance;
    }

    public static NavigationView getVueV() {
        return vueV;
    }
}
