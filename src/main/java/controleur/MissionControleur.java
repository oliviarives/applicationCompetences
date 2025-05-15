package controleur;

import modele.Mission;
import modele.dao.DAOMission;
import vue.MissionVue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;
import java.util.Map;

public class MissionControleur {

    private MissionVue vueM;
    private DAOMission missionDAO;

    public MissionControleur(MissionVue vue, DAOMission daoM) {
        this.vueM = vue;
        this.missionDAO = daoM;

        vueM.getBtnFiltrer().addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String nomCritere = vueM.getTxtFiltreNom().getText().trim();
            java.util.Date utilDate = vueM.getDateChooser().getDate();
            Date dateCritere = (utilDate != null) ? new Date(utilDate.getTime()) : null;

            String statutSelectionne = (String) vueM.getComboStatut().getSelectedItem();
            Integer statutCritere = null;
            if (!"Tous".equals(statutSelectionne)) {
                if (statutSelectionne.equals("Préparation")) {
                    statutCritere = 1;
                } else if (statutSelectionne.equals("En cours")) {
                    statutCritere = 2;
                } else if (statutSelectionne.equals("Terminée")) {
                    statutCritere = 3;
                }
            }

            List<Mission> missionsFiltrees = missionDAO.filterMissions(nomCritere, dateCritere, statutCritere);

            vueM.setMissions(missionsFiltrees);
        }
    });
}

    public void loadMissions() {
        List<Mission> missions = missionDAO.findAll();
        vueM.setMissions(missions);
    }

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

