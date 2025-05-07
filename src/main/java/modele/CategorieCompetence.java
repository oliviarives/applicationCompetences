package modele;

public class CategorieCompetence {
    private final String titre;
    //private String idCatCmp;
    private static int compteurId = 0;

    public CategorieCompetence(String titre) {
        this.titre = titre;
        //this.idCatCmp = generateIdCatCmp();
        generateIdCatCmp();
    }

    /**
     * Génère un identifiant unique pour une catégorie de compétence.
     *
     * @return Une chaîne de caractères représentant l'identifiant unique.
     */
    private synchronized String generateIdCatCmp() {
        int num = compteurId;
        StringBuilder id = new StringBuilder();

        while (num >= 0) {
            id.insert(0, (char) ('A' + (num % 26))); 
            num = (num / 26) - 1; 
        }
        CategorieCompetence.compteurId++;
        return id.toString();
    }

    /**
    * @return Le titre de la catégorie de compétence.
     */
    public String getTitre() {
        return titre;
    }
}
