package controleur;

import modele.dao.UtilisateurDAO;

public class ConnexionControleur {

    private final UtilisateurDAO utilisateurDAO;

    public ConnexionControleur(UtilisateurDAO utilisateurDAO) {
        this.utilisateurDAO=utilisateurDAO;
    }

    public boolean tenterConnexion(String identifiant, String motDePasse) {
        return utilisateurDAO.verifierUtilisateur(identifiant, motDePasse);
    }
}
