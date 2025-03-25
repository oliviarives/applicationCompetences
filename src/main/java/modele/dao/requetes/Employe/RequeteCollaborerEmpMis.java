package modele.dao.requetes.Employe;

public class RequeteCollaborerEmpMis extends RequeteEmploye{
    @Override
    public String requete() {
        return "SELECT * FROM COLLABORER C, MISSION M WHERE C.IDMIS=M.IDMIS";
    }
}
