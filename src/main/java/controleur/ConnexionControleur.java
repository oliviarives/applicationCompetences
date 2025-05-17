package controleur;

import modele.dao.DAOUtilisateur;
import utilitaires.LoadingDialog;
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

   /* private void verifierConnexion() {
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
    }*/

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
                long minDuration = 25000; // Durée min en ms

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
