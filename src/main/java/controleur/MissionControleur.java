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

    }

    public void loadMissions() {
        List<Mission> missions = missionDAO.findAll();
        vueM.setMissions(missions);
    }

}

