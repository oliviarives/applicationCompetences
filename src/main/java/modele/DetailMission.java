package modele;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DetailMission {
    private String titre;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private LocalDate dateCreation;
    private String commentaires;
    private int nbEmployeTotal;
    private String description;
    private Statut statut;
    private int idMis;
    private static int CompteurId = 1;

    private List<Employe> employes;
    private Responsable responsable;

    public DetailMission(String titre, LocalDate dateDebut, LocalDate dateFin, String commentaires, int nbEmployeTotal,
                   String description, Responsable responsable, Statut statut) {
        this.titre = titre;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.commentaires = commentaires;
        this.nbEmployeTotal = nbEmployeTotal;
        this.description = description;
        this.idMis = DetailMission.CompteurId;
        DetailMission.CompteurId++;
        this.responsable = responsable;
        this.employes = new ArrayList<>();
        this.dateCreation = LocalDate.now();
        this.statut = statut;
    }

    public int getIdMis() {
        return this.idMis;
    }

    public Statut getStatut() {
        return this.statut;
    }

    public void setStatut(Statut statut) {
        this.statut= statut;
    }

    public String getTitre() {
        return this.titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public LocalDate getDateDebut() {
        return this.dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return this.dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setResponsable(Responsable responsable) {
        this.responsable = responsable;
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
}
