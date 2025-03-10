
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
    private int idSta;
    private String nomSta;
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
        this.idSta = idS;
    }
    //constructeur avec 7 arguments si mission déjà existante dans la base de données
    public Mission(String titre, Date dateDebut, Date dateFin, String d, Date dateC, int nbEmp, String nSta) {
        this.idMis = compteurMis++;
        this.titreMis = titre;
        this.dateDebutMis = dateDebut;
        this.dateFinMis = dateFin;
        this.description = d;
        this.dateCreation = dateC;
        this.nomSta = nSta;
        this.nbEmpMis = nbEmp;
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
        return this.nomSta;
    }

    public int getNbEmpMis() {
        return this.nbEmpMis;
    }

    public String getLoginEmp() {
        return this.loginEmp;
    }
}