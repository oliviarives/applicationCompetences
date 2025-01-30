package modele;

import java.util.HashSet;
import java.util.Set;

public class Competence {
    private String nomCmpFr; 
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
}
