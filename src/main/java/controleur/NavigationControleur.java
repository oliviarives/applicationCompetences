package controleur;

import modele.dao.DAOCompetence;
import modele.dao.DAOMission;
import vue.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class NavigationControleur {
    private static NavigationView vueV;

    public NavigationControleur( NavigationView navView) throws SQLException {
        this.vueV = navView;

        MissionView misssionV = new MissionView();
        DAOMission missionDao = new DAOMission();
        CreationMissionView creaMissionV = new CreationMissionView();
        MissionControleur missionC = new MissionControleur(misssionV, missionDao, this, creaMissionV);

        CompetencesView competencesV = new CompetencesView();
        DAOCompetence competenceDao = new DAOCompetence();
        CompetenceControleur competenceC = new CompetenceControleur(competencesV, competenceDao);

        AccueilVue accueilV = new AccueilVue();


        missionC.loadMissions();
        competenceC.loadCompetences();

        vueV.addPage("Accueil", accueilV);
        vueV.addPage("Missions",misssionV);
        vueV.addPage("Competences",competencesV);
        vueV.addPage("Creation",creaMissionV);



        vueV.getButtonMissions().addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        vueV.showPage("Missions");
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

        misssionV.getButtonModifierMission().addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        vueV.showPage("Creation");
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

    /*public void ShowModifierMissionView(){
        vueV.showPage("Creation");
    }*/
}