package modele.dao.requetes.Mission;


import modele.dao.requetes.Requete;
import modele.Mission;

public  abstract class RequeteMission extends Requete<Mission> {
    public abstract String requete();
}
