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
import java.util.Map;

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


    // Ajoute le listener sur le bouton "Filtrer"
        vueM.getBtnFiltrer().addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Récupère le filtre sur le nom
            String nomCritere = vueM.getTxtFiltreNom().getText().trim();

            // Récupère la date (JDateChooser retourne un java.util.Date)
            java.util.Date utilDate = vueM.getDateChooser().getDate();
            // Convertir en java.sql.Date si non null
            Date dateCritere = (utilDate != null) ? new Date(utilDate.getTime()) : null;

            // Récupère le statut sélectionné
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

            // Appelle la méthode de filtrage dans le DAO
            List<Mission> missionsFiltrees = missionDAO.filterMissions(nomCritere, dateCritere, statutCritere);

            // Met à jour la vue avec les missions filtrées
            vueM.setMissions(missionsFiltrees);
        }
    });
}

    public void loadMissions() {
        List<Mission> missions = missionDAO.findAll();
        vueM.setMissions(missions);
    }

    public void ajouterMission(Mission mission) {

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

