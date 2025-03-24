package utilitaires;

import modele.Mission;
import modele.dao.DAOMission;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MissionStatutUpdater {
    private DAOMission daoMission;

    public MissionStatutUpdater(DAOMission daoMission) {
        this.daoMission = daoMission;
    }

    public void start() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            try {
                List<Mission> missions = daoMission.findAll();
                Date now = new Date();
                for (Mission mission : missions) {
                    int currentStatus = mission.getIdSta();
                    // Passage de préparation à planifiée n'est pas automatique ici
                    // car il dépend de l'affectation des compétences et employés.

                    if (currentStatus == 2) { // planifiée
                        // Vérifier si la date de début est atteinte
                        if (now.compareTo(mission.getDateDebutMis()) >= 0) {
                            // Mettre à jour en "en cours" (par exemple, idSta = 3)
                            daoMission.updateMissionStatus(mission, 3);
                        }
                    } else if (currentStatus == 3) { // en cours
                        // Vérifier si la date de fin est atteinte
                        if (now.compareTo(mission.getDateFinMis()) >= 0) {
                            // Mettre à jour en "terminée" (par exemple, idSta = 4)
                            daoMission.updateMissionStatus(mission, 4);
                        }
                    }
                    // Pour les missions en préparation, on ne change le statut ici
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }, 0, 1, TimeUnit.MINUTES); // Vérification toutes les minutes (ajuste si besoin)
    }
}
