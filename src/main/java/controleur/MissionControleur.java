package controleur;

import modele.Mission;
import modele.dao.DAOMission;
import vue.CreationMissionView;
import vue.MissionView;
import vue.ModificationMissionView;

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
    private ModificationMissionView modificationMV;

    public MissionControleur(MissionView vue, DAOMission daoM, NavigationControleur navC, CreationMissionView creationMV, ModificationMissionView modificationMV) {
        this.vueM = vue;
        this.missionDAO = daoM;
        this.navControleur = navC;
        this.creationMV = creationMV;
        this.modificationMV = modificationMV; 

        /*vueM.getButtonModifierMission().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    navControleur.ShowModifierMissionView();
            }
        });*/

       /* creationMV.getButtonConfirmer().addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        Mission misInsert= new Mission(
                                creationMV.getTitreMisFieldValue(),
                                creationMV.getDateDebutMisField(),
                                creationMV.getDateFinMisField(),
                                //Date.valueOf("1970-01-01"),
                                //Date.valueOf("1970-01-01"),
                                creationMV.getDescriptionMisFieldValue(),
                                new Date(System.currentTimeMillis()),
                                //Date.valueOf("1970-01-01"),
                                creationMV.getNbEmpField(),
                                creationMV.getLogEmpField(),
                                1

                        );
                        try {
                            missionDAO.ajouterMission(misInsert);
                            navControleur.getVueV().getButtonMissions().doClick();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }

                    }
                }
        );*/
    }

    public void loadMissions() {
        List<Mission> missions = missionDAO.findAll();
        vueM.setMissions(missions);
    }

    public void ajouterMission(Mission mission) {

    }




}

