package controleur;

import javax.swing.JLabel;
import javax.swing.JDialog;
import modele.dao.UtilisateurDAO;

public class ConnexionControleur {

    private UtilisateurDAO utilisateurDAO;

    public ConnexionControleur(UtilisateurDAO utilisateurDAO) {
        this.utilisateurDAO = utilisateurDAO;
    }

    public boolean tenterConnexion(String identifiant, String motDePasse, JLabel messageLabel, JDialog dialog) {
        return utilisateurDAO.verifierUtilisateur(identifiant, motDePasse);
    }
}
