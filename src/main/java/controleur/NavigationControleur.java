package controleur;

import modele.Employe;
import modele.dao.DAOCompetence;
import modele.dao.DAOEmploye;
import modele.dao.DAOMission;
import vue.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

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
    private ModifierMissionControleur modifMC = new ModifierMissionControleur(modifMissionV, missionDao, this, competenceDao, employeDao);


    private AjoutPersonnelVue ajoutPersonnelV = new AjoutPersonnelVue();
    private AjouterPersonnelControleur ajoutPersonnelC= new AjouterPersonnelControleur(ajoutPersonnelV, employeDao, this);

    private AccueilVue accueilV = new AccueilVue();

    public NavigationControleur( NavigationView navView) throws SQLException {
        this.vueV =navView;



        missionC.loadMissions();
        competenceC.loadCompetences();
        ajoutMC.loadCompetences();
        ajoutMC.loadEmployes();
        empC.loadEmploye();
        modifMC.loadCompetences();

        vueV.addPage("Accueil", accueilV);
        vueV.addPage("Missions",misssionV);
        vueV.addPage("Competences",competencesV);
        vueV.addPage("Creation",creaMissionV);
        vueV.addPage("Employe",empView);
        vueV.addPage("Modification", modifMissionV);
        vueV.addPage("AjouterEmploye", ajoutPersonnelV);


        vueV.getButtonMissions().addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        vueV.showPage("Missions");
                        missionC.loadMissions();
                    }
                }
        );

        vueV.getButtonCompetences().addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        vueV.showPage("Competences");
                    }
                }
        );

        vueV.getButtonEmploye().addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        vueV.showPage("Employe");
                    }
                }
        );

        empView.getBouttonAjouterEmploye().addActionListener(
        		new ActionListener() {
        			@Override
        			public void actionPerformed(ActionEvent e) {
                        vueV.showPage("AjouterEmploye");
        			}
        		}
        		
        		);
        
        misssionV.getButtonAjouterMission().addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        vueV.showPage("Creation");
                    }
                }
        );
        
        misssionV.getButtonModifierMission().addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        vueV.showPage("Modification");
                    }
                }
        );

        vueV.getButtonAccueil().addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                            vueV.showPage("Accueil");
                    }
                }
        );

    }

    public void loadEmploye() {
        List<Employe> emp = this.employeDao.findAll();
        empView.setEmploye(emp);
    }

    public static NavigationView getVueV() {
        return vueV;
    }

    /*public void ShowModifierMissionView(){
        vueV.showPage("Creation");
    }*/

}
