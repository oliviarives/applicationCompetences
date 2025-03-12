package modele.dao.requetes.Employe;

import modele.Employe;
import modele.dao.requetes.Requete;

public abstract class RequeteEmploye extends Requete<Employe> {
    public abstract String requete();
}
