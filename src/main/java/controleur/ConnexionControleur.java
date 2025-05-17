package controleur;

import modele.dao.DAOUtilisateur;
import utilitaires.LoadingDialog;
import vue.ConnexionVue;
import vue.NavigationVue;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

/**
 * Contrôleur chargé de la gestion de la connexion utilisateur
 * Interagit avec la vue ConnexionVue et le DAOUtilisateur pour vérifier les identifiants
 */

public class ConnexionControleur {
    /**
     * DAO utilisé pour valider les informations de connexion
     */
    private final DAOUtilisateur daoUtilisateur;
    /**
     * Vue de connexion
     */
    private final ConnexionVue connexionVue;

    /**
     * Initialise le contrôleur de connexion avec la vue et le DAO
     * @param connexionVue vue de connexion
     * @param daoUtilisateur DAO utilisateur
     */
    public ConnexionControleur(ConnexionVue connexionVue, DAOUtilisateur daoUtilisateur) {
        this.daoUtilisateur = daoUtilisateur;
        this.connexionVue = connexionVue;

        connexionVue.getLoginButton().addActionListener(e -> verifierConnexion());

    }

    /**
     * Tente une connexion avec les identifiants donnés
     * @param identifiant identifiant saisi
     * @param motDePasse mot de passe saisi
     * @return vrai si les identifiants sont valides, faux sinon
     */
    public boolean tenterConnexion(String identifiant, String motDePasse) {
        return daoUtilisateur.verifierUtilisateur(identifiant, motDePasse);
    }

    /**
     * Vérifie la connexion à partir des données de la vue
     * Affiche un message de chargement puis affiche la navigation ou une erreur
     */
    private void verifierConnexion() {
        String identifiant = connexionVue.getIdentifiant();
        String motDePasse = connexionVue.getMotDePasse();

        // Affiche le message de chargement dès le début
        connexionVue.setMessage(
                "<html><div style='text-align: center;'><b>Chargement en cours,<br>merci pour votre patience</b></div></html>",
                new Color(0, 128, 0)
        );

        LoadingDialog loadingDialog = new LoadingDialog(connexionVue);

        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                long start = System.currentTimeMillis();

                boolean result = tenterConnexion(identifiant, motDePasse);

                long duration = System.currentTimeMillis() - start;
                long minDuration = 25000;

                if (duration < minDuration) {
                    Thread.sleep(minDuration - duration);
                }

                return result;
            }

            @Override
            protected void done() {
                loadingDialog.dispose();

                boolean connexionReussie;
                try {
                    connexionReussie = get();
                } catch (Exception e) {
                    connexionReussie = false;
                }

                if (connexionReussie) {
                    try {
                        NavigationVue navigationView = new NavigationVue();
                        new NavigationControleur(navigationView);
                        navigationView.setVisible(true);
                        connexionVue.dispose();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        connexionVue.setMessage("Erreur lors de l'ouverture de l'application", Color.RED);
                    }
                } else {
                    connexionVue.setMessage("Identifiant ou mot de passe incorrect", Color.RED);
                }
            }
        };

        worker.execute();
        loadingDialog.setVisible(true);
    }

}
