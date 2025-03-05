package modele;

public class CategorieCmp {
    private String titre;
    private String idCatCmp;
    private static int compteurId = 0;

    public CategorieCmp(String titre) {
        this.titre = titre;
        this.idCatCmp = generateIdCatCmp();
    }

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

    public String getTitre() {
        return titre;
    }

}
