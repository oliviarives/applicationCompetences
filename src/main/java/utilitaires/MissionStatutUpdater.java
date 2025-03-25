package utilitaires;

import modele.Mission;
import modele.dao.DAOMission;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MissionStatutUpdater {
    private final DAOMission daoMission;
    private ScheduledExecutorService scheduler;

    public MissionStatutUpdater(DAOMission daoMission) {
        this.daoMission = daoMission;
    }

    public void start() {
        scheduler = Executors.newScheduledThreadPool(1);
        try {
            scheduler.scheduleAtFixedRate(this::updateMissionStatuses, 0, 1, TimeUnit.MINUTES);
        } catch (Exception e) {
            shutdownScheduler();
            e.printStackTrace();
        }
    }

    private void updateMissionStatuses() {
        try {
            List<Mission> missions = daoMission.findAll();
            Date now = new Date();
            for (Mission mission : missions) {
                updateMissionStatusIfNeeded(mission, now);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void updateMissionStatusIfNeeded(Mission mission, Date now) throws SQLException {
        int currentStatus = mission.getIdSta();
        if (currentStatus == 2 && now.compareTo(mission.getDateDebutMis()) >= 0) {
            daoMission.updateMissionStatus(mission, 3);
        } else if (currentStatus == 3 && now.compareTo(mission.getDateFinMis()) >= 0) {
            daoMission.updateMissionStatus(mission, 4);
        }
    }

    private void shutdownScheduler() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(60, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException ex) {
                scheduler.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    public void stop() {
        shutdownScheduler();
    }
}
