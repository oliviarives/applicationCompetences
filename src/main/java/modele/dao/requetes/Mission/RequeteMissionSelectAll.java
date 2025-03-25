package modele.dao.requetes.Mission;

public class RequeteMissionSelectAll extends RequeteMission {
    @Override
    public String requete() {
        return "SELECT * FROM MISSION, STATUT WHERE MISSION.idSta=STATUT.idSta ORDER BY IDMIS DESC";
    }
}