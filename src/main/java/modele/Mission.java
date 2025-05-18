package modele;

import java.sql.Date;
/**
 * Représente une mission avec ses caractéristiques
 */
public class Mission {
    /** Identifiant ude la mission */
    private int idMis;
    /** Titre de la mission */
    private String titreMis;
    /** Nombre d’employés nécessaires à la mission */
    private int nbEmpMis;
    /** Date de début de la mission */
    private Date dateDebutMis;
    /** Date de fin de la mission */
    private Date dateFinMis;
    /** Description de la mission */
    private String description;
    /** Date à laquelle la mission a été créée */
    private Date dateCreation;
    /** Login de l’employé responsable de la mission */
    private String loginEmp;
    /** Statut de la mission */
    private Statut statut;
    /** Compteur pour générer des identifiants de missions */
    static int compteurMis = 20;


    /**
     * Constructeur utilisé lors de la création d'une nouvelle mission
     * @param titre titre de la mission
     * @param dateDebut date de début
     * @param dateFin date de fin
     * @param d description
     * @param dateC date de création
     * @param nbEmp nombre d'employés nécessaires
     * @param login login du responsable
     * @param idS identifiant du statut
     */
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
    /**
     * Constructeur utilisé lors du chargement depuis la base
     */
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
    /**
     * Constructeur pour modification d'une mission existante
     */
    public Mission(String titreMis, String description, Date dateDebutMis, Date dateFinMis, String loginEmp, int nbEmp,int idm) {
        this.titreMis = titreMis;
        this.dateDebutMis = dateDebutMis;
        this.dateFinMis = dateFinMis;
        this.description = description;
        this.loginEmp = loginEmp;
        this.idMis = idm;

    }

    /** @return id de la mission */
    public int getIdMission() {
        return this.idMis;
    }
    /** @return titre de la mission */
    public String getTitreMis() {
        return this.titreMis;
    }
    /** @return date de début de la mission */
    public Date getDateDebutMis() {
        return this.dateDebutMis;
    }
    /** @return date de fin de la mission */
    public Date getDateFinMis() {
        return this.dateFinMis;
    }
    /** @return description de la mission */
    public String getDescription() {
        return this.description;
    }
    /** @return date de création de la mission */
    public Date getDateCreation() {
        return this.dateCreation;
    }
    /** @return nom du statut de la mission */
    public String getNomSta() {
        return this.statut.toString();
    }
    /** @return nombre d’employés affectés à la mission */
    public int getNbEmpMis() {
        return this.nbEmpMis;
    }
    /** @return login de l’employé responsable de la mission */
    public String getLoginEmp() {
        return this.loginEmp;
    }
    /** @return id du statut de la mission */
    public int getIdSta(){
        return this.statut.getIdStatut();
    }
    /**
     * Définit l'identifiant de la mission après insertion
     */
    public void setIdMission(int id) {
        this.idMis = id;
    }



}