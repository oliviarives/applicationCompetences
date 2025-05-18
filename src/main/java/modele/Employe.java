package modele;

import java.sql.Date;
/**
 * Représente un employé avec ses informations et ses compétences
 */
public class Employe {
    /**
     * Identifiant de connexion de l'employé
     */
    private String login;
    /**
     * Prénom de l'employé
     */
    private String prenom;
    /**
     * Nom de l'employé
     */
    private String nom;
    /**
     * Mot de passe hashé de l'employé
     */
    private String hashedPwd;
    /**
     * Date d'entrée dans l'entreprise
     */
    private Date dateEntree;
    /**
     * Statut actif ou non de l'employé
     */
    private boolean actif;
    /**
     * Poste occupé par l'employé
     */
    private String poste;
    /**
     * Identifiant de catégorie de compétence
     */
    private String idCatCmp;
    /**
     * Identifiant de compétence
     */
    private int idcmp;

    /**
     * Constructeur  utilisé lors de la récupération depuis la base
     * @param prenom prénom de l'employé
     * @param nom nom de l'employé
     * @param login identifiant de connexion
     * @param mdp mot de passe en clair (qui sera hashé)
     * @param poste poste occupé
     * @param dateEntree date d'entrée dans l'entreprise
     */
    public Employe(String prenom, String nom, String login, String mdp, String poste, Date dateEntree) {
        this.prenom = prenom;
        this.nom = nom;
        this.login = login;
        this.hashedPwd = MdpUtils.hashPassword(mdp);
        this.poste = poste;
        this.dateEntree = dateEntree;
        this.actif = true;
    }
    /**
     * Constructeur utilisé pour l'affichage
     * @param log identifiant de connexion
     * @param prenom prénom
     * @param nom nom
     * @param poste poste
     */
    public Employe(String log, String prenom, String nom, String poste) {
        this.login = log;
        this.prenom = prenom;
        this.nom = nom;
        this.poste = poste;

    }
    /**
     * Constructeur qui inclut les compétences
     * @param prenom prénom
     * @param nom nom
     * @param login identifiant de connexion
     * @param mdp mot de passe en clair (quo sera hashé)
     * @param poste poste
     * @param dateEntree date d'entrée
     * @param idcatc identifiant de catégorie de compétence
     * @param idcmp identifiant de compétence
     */
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
    /** @return login de l'employé */
    public String getLogin() {
        return this.login;
    }
    /** @return prénom de l'employé */
    public String getPrenom() {
        return this.prenom;
    }
    /** @return nom de l'employé */
    public String getNom() {
        return this.nom;
    }
    /** @return poste de l'employé */
    public String getPoste(){
        return this.poste;
    }
    /** @return date d'entrée dans l'entreprise */
    public Date getDateEntree() {
        return this.dateEntree;
    }
    /** @return mot de passe hashé de l'employé */
    public String gethashedPwd() {
    	return this.hashedPwd;
    }
    /** @return identifiant de compétence */
    public int getIdcmp() {
        return this.idcmp;
    }
    /** @return identifiant de catégorie de compétence */
    public String getIdCatCmp() {
        return this.idCatCmp;
    }
}


