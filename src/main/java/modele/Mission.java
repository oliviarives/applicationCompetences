package modele;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Mission {
    private String titre;
    private Date dateDebut;
    private Date dateFin;
    private Date dateCreation;
    private String commentaires;
    private int nbEmployeTotal;
    private String description; 
    private Statut statut; 
    private int idMis;    
    private static int CompteurId = 1;
    
    private List<Employe> employes;
    private Responsable responsable;
    
    public Mission(String titre, Date dateDebut, Date dateFin, String commentaires, int nbEmployeTotal, 
    		String description, Responsable responsable) {
        this.titre = titre;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.commentaires = commentaires;
        this.nbEmployeTotal = nbEmployeTotal;
        this.description = description;
        this.idMis = Mission.CompteurId;
        Mission.CompteurId ++;
        this.responsable = responsable;
        this.employes = new ArrayList<>();
        this.dateCreation = new Date();
    }
    
    public void addEmploye(Employe employe) {
        if (employe != null && !this.employes.contains(employe)) {
            this.employes.add(employe);
            employe.addMission(this);
        }
    }

    public void removeEmploye(Employe employe) {
        if (employe != null) {
            this.employes.remove(employe);
            employe.removeMission(this);
        }
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(String commentaires) {
        this.commentaires = commentaires;
    }

    public int getNbEmployeTotal() {
        return nbEmployeTotal;
    }

    public void setNbEmployeTotal(int nbEmployeTotal) {
        this.nbEmployeTotal = nbEmployeTotal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Employe> getEmployes() {
        return employes;
    }

    public Responsable getResponsable() {
        return responsable;
    }

    public void setResponsable(Responsable responsable) {
        this.responsable = responsable;
    }
}
