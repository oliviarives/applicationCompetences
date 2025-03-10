package modele;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import at.favre.lib.crypto.bcrypt.BCrypt;


public class Employe {
    /*private String prenom;
    private String nom; 
    private String login; 
    private String hashedPwd; 
    private String poste; 
    private Date dateEntree; 
    private boolean actif;
    private Set<Service> services; 
    private Set<Competence> competences; 
    private Set<DetailMission> missions; 

    public Employe(String prenom, String nom, String login, String mdp, String poste, Date dateEntree, 
                   Service[] services, Competence[] competences) {
        this.prenom = prenom;
        this.nom = nom;
        this.login = login;
        this.hashedPwd = MdpUtils.hashPassword(mdp);
        this.poste = poste;
        this.dateEntree = dateEntree;
        this.actif = true; 
        this.services = new HashSet<>();
        this.competences = new HashSet<>();
        this.missions = new HashSet<>();

        if (services != null) {
            for (Service service : services) {
                this.addService(service);
            }
        }
        if (competences != null) {
            for (Competence competence : competences) {
                this.addCompetence(competence);
            }
        }
    }
   
    public void addCompetence(Competence competence) {
        if (competence != null && this.competences.add(competence)) {
            competence.addEmploye(this); // Mise à jour de l'autre côté
        }
    }

    public void removeCompetence(Competence competence) {
        if (competence != null && this.competences.remove(competence)) {
            competence.removeEmploye(this); 
        }
    }

    public void addService(Service service) {
        if (service != null) {
            this.services.add(service);
        }
    }

    public void removeService(Service service) {
        this.services.remove(service);
    }
    
    public void addMission(DetailMission mission) {
        if (mission != null) {
            this.missions.add(mission);
        }
    }

    public void removeMission(DetailMission mission) {
        this.missions.remove(mission);
    }

    public Set<Competence> getCompetences() {
        return this.competences;
    }

    public Set<Service> getServices() {
        return this.services;
    }*/

}
