package modele;
/**
 * Représente une compétence avec son identifiant, sa catégorie et ses noms en anglais et en français
 */
public class Competence {
    /**
     * Identifiant de la catégorie de compétence
     */
    private String idCatCmp;
    /**
     * Identifiant de la compétence
     */
    private int idCmp;
    /**
     * Nom de la compétence en anglais
     */
    private String nomCmpEn;
    /**
     * Nom de la compétence en français
     */
    private String nomCmpFr;
    /**
     * Compteur statique pour générer les ID automatiquement
     */
    public static int compteurCmp=0;
    /**
     * Constructeur avec trois arguments
     * @param idCat catégorie de la compétence
     * @param nomEn nom en anglais
     * @param nomFr nom en français
     */
    public Competence(String idCat, String nomEn, String nomFr) {
        this.idCmp = ++compteurCmp;
        this.idCatCmp = idCat;
        this.nomCmpEn = nomEn;
        this.nomCmpFr = nomFr;
    }
    /**
     * Constructeur avec identifiant fourni
     * @param id identifiant de la compétence
     * @param idCat catégorie de la compétence
     * @param nomEn nom en anglais
     * @param nomFr nom en français
     */
    public Competence(int id,String idCat, String nomEn, String nomFr) {
        this.idCmp = id;
        this.idCatCmp = idCat;
        this.nomCmpEn = nomEn;
        this.nomCmpFr = nomFr;
    }
    /**
     * Retourne l'identifiant de la catégorie
     * @return idCatCmp
     */
    public String getIdCatCmp() {
        return this.idCatCmp;
    }
    /**
     * Retourne l'identifiant de la compétence
     * @return idCmp
     */
    public int getIdCmp() {
        return this.idCmp;
    }
    /**
     * Retourne le nom de la compétence en anglais
     * @return nomCmpEn
     */
    public String getNomCmpEn() {
        return this.nomCmpEn;
    }
    /**
     * Retourne le nom de la compétence en français
     * @return nomCmpFr
     */
    public String getNomCmpFr() {
        return this.nomCmpFr;
    }
}


