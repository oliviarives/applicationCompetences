package modele;

import java.sql.Date;

public class Mission {
    private int idMis;
    private String titreMis;
    private int nbEmpMis;
    private Date dateDebutMis;
    private Date dateFinMis;
    private String description;
    private Date dateCreation;
    private String loginEmp;
    //private int idSta;
    //private String nomSta;
    private Statut statut;
    static int compteurMis = 20;
    //constructeur avec 7 dont login arguments si creation d'une mission
    public Mission(String titre, Date dateDebut, Date dateFin, String d, Date dateC, int nbEmp, String login,int idS) {
        this.idMis = compteurMis++;
        this.titreMis = titre;
        this.dateDebutMis = dateDebut;
        this.dateFinMis = dateFin;
        this.description = d;
        this.dateCreation = dateC;
        this.loginEmp = login;
        this.nbEmpMis = nbEmp;
        this.statut = Statut.fromId(idS);
    }

    public Mission(int idMis, String titre, Date dateDebut, Date dateFin, String description, Date dateCreation, int nbEmp, String nomSta, int idS, String logEmp) {
        this.idMis = idMis;
        this.titreMis = titre;
        this.dateDebutMis = dateDebut;
        this.dateFinMis = dateFin;
        this.description = description;
        this.dateCreation = dateCreation;
        this.nbEmpMis = nbEmp;
        this.statut = Statut.fromId(idS);
        this.loginEmp=logEmp;
    }

    public Mission(String titreMis, String description, Date dateDebutMis, Date dateFinMis, String loginEmp, int nbEmp,int idm) {
        this.titreMis = titreMis;
        this.dateDebutMis = dateDebutMis;
        this.dateFinMis = dateFinMis;
        this.description = description;
        this.loginEmp = loginEmp;
        this.idMis = idm;

    }

    public int getIdMission() {
        return this.idMis;
    }

    public String getTitreMis() {
        return this.titreMis;
    }

    public Date getDateDebutMis() {return this.dateDebutMis;}

    public Date getDateFinMis() {
        return this.dateFinMis;
    }

    public String getDescription() {
        return this.description;
    }

    public Date getDateCreation() {
        return this.dateCreation;
    }

    public String getNomSta() {
        return this.statut.toString();
    }

    public int getNbEmpMis() {
        return this.nbEmpMis;
    }

    public String getLoginEmp() {
        return this.loginEmp;
    }

    public int getIdSta(){
        return this.statut.getIdStatut();
    }

    public void setIdMission(int id) {
        this.idMis = id;
    }



}