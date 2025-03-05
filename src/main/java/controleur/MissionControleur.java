package controleur;

import modele.Mission;
import modele.dao.DAOMission;
import vue.CreationMissionView;
import vue.MissionView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class MissionControleur {

    private MissionView vueM;
    private DAOMission missionDAO;
    private NavigationControleur navControleur;
    private CreationMissionView creationMV;

    public MissionControleur(MissionView vue, DAOMission daoM, NavigationControleur navC, CreationMissionView creationMV) {
        this.vueM = vue;
        this.missionDAO=daoM;
        this.navControleur=navC;
        this.creationMV=creationMV;

        /*vueM.getButtonModifierMission().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    navControleur.ShowModifierMissionView();
            }
        });*/

        creationMV.getButtonConfirmer().addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        Mission misInsert= new Mission(
                                creationMV.getTitreMisFieldValue(),
                                Date.valueOf(creationMV.getDateDebutMisField()),
                                Date.valueOf(creationMV.getDateFinMisField()),
                                creationMV.getDescriptionMisFieldValue(),
                                Date.valueOf("00/00/0000"),
                                creationMV.getNbEmpField(),
                                creationMV.getLogEmpField()
                        );
                        try {
                            missionDAO.ajouterMission(misInsert);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
        );
    }

    public void loadMissions() {
        List<Mission> missions = missionDAO.findAll();
        vueM.setMissions(missions);
    }

    public void ajouterMission(Mission mission) {

    }




}

