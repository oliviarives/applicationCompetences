package controleur;

import modele.Mission;
import modele.Responsable;
import modele.Statut;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MissionControleur {
    private List<Mission> missions;

    public MissionControleur() {

        this.missions = new ArrayList<>();
    }

    public void ajouterMission(String titre, LocalDate dateDebut, LocalDate dateFin, String commentaires,
                               int nbEmployeTotal, String description, Responsable responsable, Statut statut) {
        Mission mission = new Mission(titre, dateDebut, dateFin, commentaires, nbEmployeTotal, description, responsable, statut);
        missions.add(mission);
    }

    public void supprimerMission(int id) {
        missions.removeIf(mission -> mission.getIdMis() == id);
    }

    
    public List<Mission> getMissions() {
        return new ArrayList<>(missions);
    }

    public Mission chercherMissionParId(int id) {
        return missions.stream().filter(m -> m.getIdMis() == id).findFirst().orElse(null);
    }

    public void modifierStatutMission(int id, Statut nouveauStatut) {
        Mission mission = chercherMissionParId(id);
        if (mission != null) {
            mission.setStatut(nouveauStatut);
        }
    }
}