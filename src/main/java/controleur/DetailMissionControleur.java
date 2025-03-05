package controleur;

import modele.DetailMission;
import modele.Responsable;
import modele.Statut;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DetailMissionControleur {
    private List<DetailMission> missions;

    public DetailMissionControleur() {

        this.missions = new ArrayList<>();
    }

    public void ajouterMission(String titre, LocalDate dateDebut, LocalDate dateFin, String commentaires,
                               int nbEmployeTotal, String description, Responsable responsable, Statut statut) {
        DetailMission mission = new DetailMission(titre, dateDebut, dateFin, commentaires, nbEmployeTotal, description, responsable, statut);
        missions.add(mission);
    }

    public void supprimerMission(int id) {
        missions.removeIf(mission -> mission.getIdMis() == id);
    }

    public List<DetailMission> getMissions() {
        return new ArrayList<>(missions);
    }

    public DetailMission chercherMissionParId(int id) {
        return missions.stream().filter(m -> m.getIdMis() == id).findFirst().orElse(null);
    }

    public void modifierStatutMission(int id, Statut nouveauStatut) {
        DetailMission mission = chercherMissionParId(id);
        if (mission != null) {
            mission.setStatut(nouveauStatut);
        }
    }
}