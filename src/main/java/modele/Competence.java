package modele;

public class Competence {
   /* private String nomCmpFr;
    private String nomCmpEn; 
    private final int numero; 
    private static int compteurId = 1;
    private Set<Employe> employes;
    private CategorieCmp catCmp; 

    public Competence(String nomCmpFr, String nomCmpEn, CategorieCmp catCmp) {
        this.nomCmpFr = nomCmpFr;
        this.nomCmpEn = nomCmpEn;
        this.numero = Competence.compteurId;
        Competence.compteurId++;
        this.catCmp = catCmp; 
        this.employes = new HashSet<>();
    }

    public void addEmploye(Employe employe) {
        if (employe != null && this.employes.add(employe)) {
            employe.addCompetence(this);
        }
    }

    public void removeEmploye(Employe employe) {
        if (employe != null && this.employes.remove(employe)) {
            employe.removeCompetence(this); 
        }
    }

    public Set<Employe> getEmployes() {
        return this.employes;
    }

    public String getNomCmpFr() {
        return this.nomCmpFr;
    }
    public String getNomCmpEn() {
        return this.nomCmpEn;
    }
    public int getNumero() {
        return this.numero;
    }
    public CategorieCmp getCatCmp() {
        return this.catCmp;
    }
    public int getCompteurId() {
        return this.compteurId;
    }*/



    private String idCatCmp;
    private int idCmp;
    private String nomCmpEn;
    private String nomCmpFr;
    public static int compteurCmp=0;

    //Constructeur pour nouvelle mission (3 arguments)
    public Competence(String idCat, String nomEn, String nomFr) {
        this.idCmp = ++compteurCmp;
        this.idCatCmp = idCat;
        this.nomCmpEn = nomEn;
        this.nomCmpFr = nomFr;
    }

    //Constructeur pour instance de compétence déjà existante dans BD (4 arguments)
    public Competence(int id,String idCat, String nomEn, String nomFr) {
        this.idCmp = id;
        this.idCatCmp = idCat;
        this.nomCmpEn = nomEn;
        this.nomCmpFr = nomFr;
    }

    public String getIdCatCmp() {
        return this.idCatCmp;
    }

    public int getIdCmp() {
        return this.idCmp;
    }

    public String getNomCmpEn() {
        return this.nomCmpEn;
    }

    public String getNomCmpFr() {
        return this.nomCmpFr;
    }
}


