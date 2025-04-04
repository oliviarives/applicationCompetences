package modele;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import at.favre.lib.crypto.bcrypt.BCrypt;


public class Employe {
    private String login;
    private String prenom;
    private String nom;
    private String hashedPwd;
    private Date dateEntree; 
    private boolean actif;
    private String poste;
    private String idCatCmp;
    private int idcmp;

    //constructeur from BD
    public Employe(String prenom, String nom, String login, String mdp, String poste, Date dateEntree) {
        this.prenom = prenom;
        this.nom = nom;
        this.login = login;
        this.hashedPwd = MdpUtils.hashPassword(mdp);
        this.poste = poste;
        this.dateEntree = dateEntree;
        this.actif = true;
    }
    //Constructeur pour affichage
    public Employe(String prenom, String nom, String poste) {
        this.prenom = prenom;
        this.nom = nom;
        this.poste = poste;

    }
    //constructeur pour compétence
    public Employe(String prenom, String nom, String login, String mdp, String poste, Date dateEntree, String idcatc,int idcmp) {
        this.prenom = prenom;
        this.nom = nom;
        this.login = login;
        this.hashedPwd = MdpUtils.hashPassword(mdp);
        this.poste = poste;
        this.dateEntree = dateEntree;
        this.actif = true;
        this.idCatCmp = idcatc;
        this.idcmp = idcmp;
    }

    public String getLogin() {
        return this.login;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public String getNom() {
        return this.nom;
    }

    public String getPoste(){
        return this.poste;
    }

    public Date getDateEntree() {
        return this.dateEntree;
    }

    public boolean getActif() {
        return this.actif;
    }

    public String gethashedPwd() {
    	return this.hashedPwd;
    }

    public int getIdcmp() {
        return this.idcmp;
    }

    public String getIdCatCmp() {
        return this.idCatCmp;

    }
}

        /*if (services != null) {
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
    }

}*/
