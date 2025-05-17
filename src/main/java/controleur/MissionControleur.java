package controleur;

import modele.Mission;
import modele.dao.DAOMission;
import vue.MissionVue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MissionControleur {

    private MissionVue vueM;
    private DAOMission missionDAO;
    private List<Mission> listMissionDaoFindAll;

    public MissionControleur(MissionVue vue, DAOMission daoM) {
        this.vueM = vue;
        this.missionDAO = daoM;
        this.listMissionDaoFindAll = new ArrayList<>();

        vueM.getBtnFiltrer().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterMissions();
            }
        });

        vueM.getBtnReset().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vueM.setMissions(listMissionDaoFindAll);
                vueM.ResetFiltres();
            }
        });


}

    public void loadMissions() throws SQLException {
        //List<Mission> missions = missionDAO.findAll();
        this.listMissionDaoFindAll.clear();
        this.listMissionDaoFindAll = missionDAO.findAll();
        for (Mission mission : this.listMissionDaoFindAll) {
            updateMissionStatusIfNeeded(mission, new java.util.Date());
        }
        vueM.setMissions(this.listMissionDaoFindAll);
    }

    public void filterMissions() {
        List<Mission> listTrieeFinale = new ArrayList<>();

        String nomCritere = vueM.getTxtFiltreNom().getText();
       // java.util.Date utilDate = vueM.getDateChooser().getDate();
        String statutSelectionne = (String) vueM.getComboStatut().getSelectedItem();

        for (Mission m : this.listMissionDaoFindAll) {
            boolean matchNom = (nomCritere == null || nomCritere.isEmpty())
                    || m.getTitreMis().equalsIgnoreCase(nomCritere);



            boolean matchStatut = ("Tous".equals(statutSelectionne))
                    || statutSelectionne.equals(m.getNomSta());

            if (matchNom && matchStatut) {
                listTrieeFinale.add(m);
            }
        }

        vueM.setMissions(listTrieeFinale);
    }



    public void updateMissionStatusIfNeeded(Mission mission, java.util.Date now) throws SQLException {
        int currentStatus = mission.getIdSta();
        if (currentStatus == 2 && now.compareTo(mission.getDateDebutMis()) >= 0) {
            missionDAO.updateMissionStatus(mission, 3);
        } else if (now.compareTo(mission.getDateFinMis()) >= 0) {
            missionDAO.updateMissionStatus(mission, 4);    }}

    public void ajouterMission(Mission mission) {
        throw new UnsupportedOperationException("methode non utilisé");
    }

    public int getNbMissionsEnPreparation() {
        return missionDAO.countMissionsByStatus(1);
    }

    public int getNbMissionsEnCours() {
        return missionDAO.countMissionsByStatus(2);
    }

    public int getNbMissionsTerminees() {
        return missionDAO.countMissionsByStatus(3);
    }

    public Map<String, Integer> getMissionsStatsParMois() {
        return missionDAO.getMissionsStatsParMois();
    }



}

