package modele;

import org.jetbrains.annotations.NotNull;

public class CategorieCmp {
    private String titre;
    private static String idCatCmp;
    private static int compteurId = 0;

    public CategorieCmp(String titre) {
        this.titre = titre;
        this.idCatCmp = generateIdCatCmp();
    }

    /**
     * Génère un identifiant unique pour une catégorie de compétence.
     *
     * @return Une chaîne de caractères représentant l'identifiant unique.
     */
    private String generateIdCatCmp() {
        int num = CategorieCmp.compteurId;
        StringBuilder id = new StringBuilder();

        while (num >= 0) {
            id.insert(0, (char) ('A' + (num % 26))); 
            num = (num / 26) - 1; 
        }

        CategorieCmp.compteurId++; 
        return id.toString();
    }

    /**
    * @return Le titre de la catégorie de compétence.
     */
    public String getTitre() {
        return titre;
    }

}
