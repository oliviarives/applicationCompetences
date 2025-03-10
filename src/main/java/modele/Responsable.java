package modele;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

public class Responsable extends Employe {
/*
    private Set<DetailMission> missions; 

    public Responsable(String prenom, String nom, String login, String mdp, String poste, Date dateEntree,
            Service[] services, Competence[] competences) {
        super(prenom, nom, login, mdp, poste, dateEntree, services, competences);
        this.missions = new HashSet<>();
    }

    public void addMission(DetailMission mission) {
        if (mission != null) {
            mission.setResponsable(this);
            this.missions.add(mission);
        }
    }

    public void removeMission(DetailMission mission) {
        if (mission != null) {
            mission.setResponsable(null);
            this.missions.remove(mission);
        }
    }

    public Set<DetailMission> getMissions() {
        return this.missions;
    }*/
}
