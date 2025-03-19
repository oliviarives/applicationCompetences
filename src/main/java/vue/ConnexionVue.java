package vue;
import controleur.NavigationControleur;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;
import javax.swing.*;
import controleur.ConnexionControleur;
import modele.connexion.CictOracleDataSource;
import modele.dao.UtilisateurDAO;
import utilitaires.Config;

public class ConnexionVue extends JDialog {
    private final ConnexionControleur controleur;
    private static final Logger logger = Logger.getLogger(ConnexionVue.class.getName());
    private JPanel page;
    private JTextField IdentifiantJTextField;
    private JPasswordField MdpJTextFieldPwd;
    private JLabel messageLabel;
    private JButton loginButton;
    @SuppressWarnings("unused")
    private JPanel gauche;
    @SuppressWarnings("unused")
    private JPanel droite;
    @SuppressWarnings("unused")
    private JLabel MdpJLabel;
    @SuppressWarnings("unused")
    private JLabel IdentifiantJLabel;
    @SuppressWarnings("unused")
    private JLabel ConnexionJLabel;


    /**
     * Crée la fenêtre de connexion.
     */
    public ConnexionVue(ConnexionControleur controleur) {
        super(); // Appel au constructeur de JDialog
        this.controleur = controleur;

        // Configurer la fenêtre
        setContentPane(page);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // Ajouter un ActionListener aux champs de texte pour pouvoir appuyer sur le bouton Entrer du clavier
        IdentifiantJTextField.addActionListener(e -> verifierConnexion());
        MdpJTextFieldPwd.addActionListener(e -> verifierConnexion());
        loginButton.addActionListener(e -> verifierConnexion());

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

            ConnexionControleur controleur = new ConnexionControleur(new UtilisateurDAO(connection));

            // Passer le contrôleur à la vue
            new ConnexionVue(controleur);

        } catch (Exception e) {
            logger.severe("Erreur lors de la connexion : " + e.getMessage());
        }
    }

    /**
     * Vérifie la connexion
     */
    private void verifierConnexion() {
        String identifiant = IdentifiantJTextField.getText();
        String motDePasse = new String(MdpJTextFieldPwd.getPassword());
        NavigationView navigationView = new NavigationView();

        boolean connexionReussie = this.controleur.tenterConnexion(identifiant, motDePasse);

        if (connexionReussie) {
            try {
                new NavigationControleur(navigationView);
                navigationView.setVisible(true);
                setVisible(false);
                dispose();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            // Affichage du message d'erreur
            messageLabel.setText("Identifiant ou mot de passe incorrect !");
            messageLabel.setForeground(Color.RED);

            // Démarrer un Timer pour effacer le message après 3 secondes
            Timer timer = new Timer(3000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    messageLabel.setText(""); // Effacer le message
                }
            });

            timer.setRepeats(false); // Exécuter une seule fois
            timer.start();
        }
    }
}

