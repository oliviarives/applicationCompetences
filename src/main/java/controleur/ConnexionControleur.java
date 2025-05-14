package controleur;

import modele.dao.DAOUtilisateur;
import vue.ConnexionVue;
import vue.NavigationVue;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class ConnexionControleur {

    private final DAOUtilisateur daoUtilisateur;
    private final ConnexionVue connexionVue;

    public ConnexionControleur(ConnexionVue connexionVue, DAOUtilisateur daoUtilisateur) {
        this.daoUtilisateur = daoUtilisateur;
        this.connexionVue = connexionVue;

        connexionVue.getLoginButton().addActionListener(e -> verifierConnexion());
    }

    public boolean tenterConnexion(String identifiant, String motDePasse) {
        return daoUtilisateur.verifierUtilisateur(identifiant, motDePasse);
    }

    private void verifierConnexion() {
        String identifiant = connexionVue.getIdentifiant();
        String motDePasse = connexionVue.getMotDePasse();
        boolean connexionReussie = tenterConnexion(identifiant, motDePasse);

        if (connexionReussie) {
            connexionVue.setMessage(
                    "<html><div style='text-align: center;'><b>Chargement en cours,<br>merci pour votre patience</b></div></html>",
                    new Color(0, 128, 0)
            );

            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() {
                    return null;
                }

                @Override
                protected void done() {
                    try {
                        NavigationVue navigationView = new NavigationVue();
                        new NavigationControleur(navigationView);
                        navigationView.setVisible(true);
                        connexionVue.dispose();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }.execute();

        } else {
            connexionVue.setMessage("Identifiant ou mot de passe incorrect", Color.RED);
        }
    }

}
