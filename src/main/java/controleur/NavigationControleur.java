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
    private static NavigationVue vueV;
    private final DAOCompetence competenceDao = new DAOCompetence();

    private final EmployeVue empView = new EmployeVue();
    private final DAOEmploye employeDao = new DAOEmploye();


    private final MissionVue missionV = new MissionVue();
    private final ModificationMissionVue modifMissionV = new ModificationMissionVue();
    private final CreationMissionVue creaMissionV = new CreationMissionVue();

    private DAOMission missionDao = new DAOMission();
    private InformationEmpVue infosEmpView = new InformationEmpVue(employeDao);
    private MissionControleur missionC = new MissionControleur(missionV, missionDao, this, creaMissionV, modifMissionV);
    private AjouterMissionControleur ajoutMC = new AjouterMissionControleur(creaMissionV,missionDao,this,competenceDao,employeDao,infosEmpView);

    private AjoutEmployeVue ajoutPersonnelV = new AjoutEmployeVue();
    private AjouterPersonnelControleur ajoutPersonnelC= new AjouterPersonnelControleur(ajoutPersonnelV, employeDao, competenceDao, this);
    private ModificationEmployeVue modifEmployeVue = new ModificationEmployeVue();
    private ModifierPersonnelControleur modifEmployeC = new ModifierPersonnelControleur(modifEmployeVue, employeDao, competenceDao, this);

    private AccueilVue accueilV = new AccueilVue();

    private static DAOMission missionDaoInstance; // variable statique pour DAOMission

    public NavigationControleur( NavigationVue navView) throws SQLException {
        this.vueV =navView;

        MissionVue missionV = new MissionVue();
        InformationEmpVue infosEmpView = new InformationEmpVue(employeDao);
        ModificationMissionVue modifMissionV = new ModificationMissionVue();
        CreationMissionVue creaMissionV = new CreationMissionVue();
        DAOMission missionDao = new DAOMission();
        MissionControleur missionC = new MissionControleur(missionV, missionDao, this, creaMissionV, modifMissionV);
        AjouterMissionControleur ajoutMC = new AjouterMissionControleur(creaMissionV, missionDao, this, competenceDao, employeDao, infosEmpView);
        missionDaoInstance = missionDao;

        AjoutEmployeVue ajoutPersonnelV = new AjoutEmployeVue();
        AjouterPersonnelControleur ajoutPersonnelC = new AjouterPersonnelControleur(ajoutPersonnelV, employeDao, competenceDao, this);

        missionC.loadMissions();
        CompetencesVue competencesV = new CompetencesVue();
        CompetenceControleur competenceC = new CompetenceControleur(competencesV, competenceDao);
        competenceC.loadCompetences();
        ajoutMC.loadCompetences();
        ajoutMC.loadEmployes();
        ajoutPersonnelC.loadCompetences();

        EmployeControleur empC = new EmployeControleur(empView, employeDao, this);

        modifEmployeC.loadCompetences();

        empC.loadEmploye();

        vueV.addPage(MOT_ACCUEIL, accueilV);
        vueV.addPage("Missions", missionV);
        vueV.addPage("Competences", competencesV);
        vueV.addPage("Creation", creaMissionV);
        vueV.addPage("Employe", empView);
        vueV.addPage("Modification", modifMissionV);
        vueV.addPage("AjouterEmploye", ajoutPersonnelV);
        vueV.addPage("ModifierEmploye", modifEmployeVue);
        vueV.addPage("InfosEmp", infosEmpView);

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
                Mission missionSelectionnee = missionV.getMissionSelectionnee();
                System.out.println("idsta mis select :"+missionSelectionnee.getIdSta());
                /*if(missionSelectionnee.getIdSta()!=1){

                    JOptionPane.showMessageDialog(null, "Vous ne pouvez plus modifier cette mission, elle n'est plus en préparation.",
                            "WARNING!" , JOptionPane.WARNING_MESSAGE);
                } else if  (missionSelectionnee != null) {*/
                //faire if avec : loginField.setEditable(false);
                if(missionSelectionnee != null) {
                    // Crée une instance de ModifierMissionControleur en passant la mission sélectionnée
                    ModifierMissionControleur modifMC = new ModifierMissionControleur(modifMissionV, missionDao, NavigationControleur.this, competenceDao, employeDao, missionSelectionnee);

                    try {
                        modifMC.preRemplirFormulaire();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    int is = missionV.getIdMissionSelect();
                    modifMC.setIdMissionSelect(is);
                    vueV.showPage("Modification");
                }

                if(missionSelectionnee.getIdSta()!=1 || missionSelectionnee == null ){
                    modifMissionV.getTitreMisField2().setEditable(false);
                    modifMissionV.getDescriptionMisField2().setEditable(false);
                    modifMissionV.getLogEmpField2().setEditable(false);
                    modifMissionV.getButtonConfirmer().setEnabled(false);
                    modifMissionV.getAjouterCompetences().setEnabled(false);
                    modifMissionV.getAjouterEmployes().setEnabled(false);
                    modifMissionV.getDateDebutMisFieldComponent().setEnabled(false);
                    modifMissionV.getDateFinMisFieldComponent().setEnabled(false);
                    modifMissionV.getNbEmpFieldComponent().setEnabled(false);
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
                vueV.showPage(MOT_ACCUEIL);
            }
        });

        creaMissionV.getInfoButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                infosEmpView.setEmpSelectionne(creaMissionV.getEmployeSelectionne());
                vueV.showPage("InfosEmp");
            }
        });

        infosEmpView.getCroixRetour().addActionListener(new ActionListener() {
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

    public static DAOMission getMissionDao() {
        return missionDaoInstance;
    }

    public static NavigationVue getVueV() {
        return vueV;
    }
}
