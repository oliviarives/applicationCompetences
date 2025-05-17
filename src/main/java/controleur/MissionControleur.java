package controleur;

import modele.Mission;
import modele.dao.DAOMission;
import vue.MissionVue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Contrôleur pour gérer des missions dans la vue MissionVue
 * Gère le chargement, le filtrage et la mise à jour des statuts de mission
 */
public class MissionControleur {
    /**
     * Vue qui affiche la liste des missions
     */
    private MissionVue vueM;
    /**
     * DAO pour accéder aux missions
     */
    private DAOMission missionDAO;
    /**
     * Liste de toutes les missions chargées depuis le DAO
     */
    private List<Mission> listMissionDaoFindAll;

    /**
     * Initialise le contrôleur et configure les actions des boutons de filtre et de réinitialisation
     * @param vue vue des missions
     * @param daoM DAO des missions
     */
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
    /**
     * Charge toutes les missions, met à jour les statuts si nécessaire et les transmet à la vue
     * @throws SQLException en cas d'erreur lors de la récupération ou de la mise à jour des missions
     */
    public void loadMissions() throws SQLException {
        this.listMissionDaoFindAll.clear();
        this.listMissionDaoFindAll = missionDAO.findAll();
        for (Mission mission : this.listMissionDaoFindAll) {
            updateMissionStatusIfNeeded(mission, new java.util.Date());
        }
        vueM.setMissions(this.listMissionDaoFindAll);
    }
    /**
     * Filtre les missions affichées selon le nom ou le statut sélectionnés dans la vue
     */
    public void filterMissions() {
        List<Mission> listTrieeFinale = new ArrayList<>();

        String nomCritere = vueM.getTxtFiltreNom().getText();
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

    /**
     * Met à jour le statut d'une mission en fonction de la date actuelle si besoin
     * @param mission mission à vérifier
     * @param now date actuelle
     * @throws SQLException en cas d'erreur lors de la mise à jour
     */
    public void updateMissionStatusIfNeeded(Mission mission, java.util.Date now) throws SQLException {
        int currentStatus = mission.getIdSta();
        if (currentStatus == 2 && now.compareTo(mission.getDateDebutMis()) >= 0) {
            missionDAO.updateMissionStatus(mission, 3);
        } else if (now.compareTo(mission.getDateFinMis()) >= 0) {
            missionDAO.updateMissionStatus(mission, 4);    }}

}

