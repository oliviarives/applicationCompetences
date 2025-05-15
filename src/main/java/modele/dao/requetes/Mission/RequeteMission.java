package modele.dao.requetes.Mission;


import modele.Mission;
import modele.dao.requetes.Requete;

public  abstract class RequeteMission extends Requete<Mission> {
    public abstract String requete();
}
