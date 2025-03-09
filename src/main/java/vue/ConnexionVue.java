package vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.*;
import controleur.ConnexionControleur;
import modele.connexion.CictOracleDataSource;
import modele.dao.UtilisateurDAO;
import utilitaires.Config;

public class ConnexionVue extends JDialog {
    private final ConnexionControleur controleur;
    private JPanel page;
    private JPanel gauche;
    private JPanel droite;
    private JTextField IdentifiantJTextField;
    private JPasswordField MdpJTextFieldPwd;
    private JLabel MdpJLabel;
    private JLabel IdentifiantJLabel;
    private JLabel ConnexionJLabel;
    private JLabel messageLabel;
    private JButton loginButton;

    /**
     * Crée la fenêtre de connexion.
     */
    public ConnexionVue(ConnexionControleur controleur) {
        super(); // Appel au constructeur de JDialog
        this.controleur = controleur;

        // Configurer la fenêtre
        setContentPane(page);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // Ajouter un ActionListener aux champs de texte pour pouvoir appuyer sur le bouton Entrer du clavier
        IdentifiantJTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verifierConnexion();
            }
        });
        MdpJTextFieldPwd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verifierConnexion();
            }
        });
        // Actions des boutons
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verifierConnexion();
            }
        });
    }

    /**
     * Lance l'application.
     */
    public static void main(String[] args) {
        try {
            // Création de l'accès à la bd
            CictOracleDataSource.creerAcces(Config.get("db.user"), Config.get("db.password"));

            Connection connection = CictOracleDataSource.getConnectionBD();
            if (connection == null || connection.isClosed()) {
                throw new SQLException("Impossible d'obtenir une connexion valide à la base de données.");
            }

            // Création du contrôleur
            ConnexionControleur controleur = new ConnexionControleur(new UtilisateurDAO(connection));

            // Passer le contrôleur à la vue
            ConnexionVue dialog = new ConnexionVue(controleur);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Vérifie la connexion (appel au contrôleur).
     */
    private void verifierConnexion() {
        String identifiant = IdentifiantJTextField.getText();
        String motDePasse = new String(MdpJTextFieldPwd.getPassword());

        boolean connexionReussie = this.controleur.tenterConnexion(identifiant, motDePasse, messageLabel, this);

        if (connexionReussie) {
            messageLabel.setText("Connexion réussie !");
            messageLabel.setForeground(Color.GREEN);

            setVisible(false); // Masque la fenêtre
            dispose(); // Ferme la fenêtre

            // Ouvrir la fenêtre d'accueil
            SwingUtilities.invokeLater(() -> {
                AccueilVue accueil = new AccueilVue();
                accueil.setVisible(true);
            });
        } else {
            messageLabel.setText("Identifiant ou mot de passe incorrect !");
            messageLabel.setForeground(Color.RED);
        }
    }
}
