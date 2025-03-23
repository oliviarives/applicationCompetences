package controleur;

import modele.dao.DAOUtilisateur;

public class ConnexionControleur {

    private final DAOUtilisateur DAOUtilisateur;

    public ConnexionControleur(DAOUtilisateur DAOUtilisateur) {
        this.DAOUtilisateur = DAOUtilisateur;
    }

    public boolean tenterConnexion(String identifiant, String motDePasse) {
        return DAOUtilisateur.verifierUtilisateur(identifiant, motDePasse);
    }
}
