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
        MissionControleur missionC = new MissionControleur(misssionV, missionDao, this, creaMissionV);

        CompetencesView competencesV = new CompetencesView();
        DAOCompetence competenceDao = new DAOCompetence();
        CompetenceControleur competenceC = new CompetenceControleur(competencesV, competenceDao);

        EmployeView empView = new EmployeView();
        DAOEmploye employeDao = new DAOEmploye();
        EmployeControleur empC =new EmployeControleur(empView,employeDao);

        AjouterMissionControleur ajoutMC = new AjouterMissionControleur(creaMissionV,missionDao,this,competenceDao,employeDao);

        missionC.loadMissions();
        competenceC.loadCompetences();
        ajoutMC.loadCompetences();
        ajoutMC.loadEmployes();
        empC.loadEmploye();

        vueV.addPage("Missions",misssionV);
        vueV.addPage("Competences",competencesV);
        vueV.addPage("Creation",creaMissionV);
        vueV.addPage("Employe",empView);


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

        misssionV.getButtonModifierMission().addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        vueV.showPage("Creation");
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