package controleur;

import modele.dao.DAOCompetence;
import modele.dao.DAOEmploye;
import modele.dao.DAOMission;
import vue.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class NavigationControleur {
    private static NavigationView vueV;

    public NavigationControleur( NavigationView navView) throws SQLException {
        this.vueV =navView;

        MissionView misssionV = new MissionView();
        DAOMission missionDao = new DAOMission();
        CreationMissionView creaMissionV = new CreationMissionView();
        ModificationMissionView modifMissionV = new ModificationMissionView();
        MissionControleur missionC = new MissionControleur(misssionV, missionDao, this, creaMissionV, modifMissionV);

        CompetencesView competencesV = new CompetencesView();
        DAOCompetence competenceDao = new DAOCompetence();
        CompetenceControleur competenceC = new CompetenceControleur(competencesV, competenceDao);

        EmployeView empView = new EmployeView();
        DAOEmploye employeDao = new DAOEmploye();
        EmployeControleur empC =new EmployeControleur(empView,employeDao);
        AccueilVue accueilV = new AccueilVue();

        AjouterMissionControleur ajoutMC = new AjouterMissionControleur(creaMissionV,missionDao,this,competenceDao,employeDao);
        
        ModifierMissionControleur modifMC = new ModifierMissionControleur(modifMissionV, missionDao, this, competenceDao);

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

    public static NavigationView getVueV() {
        return vueV;
    }

    /*public void ShowModifierMissionView(){
        vueV.showPage("Creation");
    }*/

}