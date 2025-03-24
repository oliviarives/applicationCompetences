package modele.dao.requetes.Mission;

public class RequeteSelectCollaborer extends RequeteMission{
    @Override
    public String requete(){
        return "SELECT * FROM COLLABORER C, MISSION M WHERE M.IDMIS=C.IDMIS";
    }
}
