package modele.dao.requetes.Competence;

import modele.Competence;
import modele.dao.requetes.Requete;

public abstract class RequeteCompetence extends Requete<Competence> {
    public abstract String requete();
}