package controleur;

import modele.dao.DAOCompetence;
import modele.dao.DAOMission;
import vue.CompetencesView;
import vue.CreationMissionView;
import vue.MissionView;
import vue.NavigationView;

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



        missionC.loadMissions();
        competenceC.loadCompetences();

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

    }

    /*public void ShowModifierMissionView(){
        vueV.showPage("Creation");
    }*/
}