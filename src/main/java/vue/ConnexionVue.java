package vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import controleur.ConnexionControleur;
import modele.connexion.CictOracleDataSource;
import modele.dao.UtilisateurDAO;
import utilitaires.Config;

public class ConnexionVue extends JDialog {

    private static final long   serialVersionUID = 1L;
    private JTextField identifiantField;
    private JPasswordField passwordField;
    private JLabel messageLabel;
    private ConnexionControleur controleur;

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
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Crée la fenêtre de connexion.
     */
    public ConnexionVue(ConnexionControleur controleur) {
        this.controleur = controleur; 
        setTitle("Connexion");
        setSize(400, 180);
        setModal(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc;

        // Identifiant
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.LINE_END;
        contentPanel.add(new JLabel("Identifiant:"), gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        identifiantField = new JTextField(15);
        identifiantField.setFont(new Font("Arial", Font.PLAIN, 12));
        contentPanel.add(identifiantField, gbc);

        // Mot de passe
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.LINE_END;
        contentPanel.add(new JLabel("Mot de passe:"), gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 12));
        contentPanel.add(passwordField, gbc);

        // Message d'erreur ou succès
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 5, 5);
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setForeground(Color.RED);
        contentPanel.add(messageLabel, gbc);

        add(contentPanel, BorderLayout.CENTER);

        // Panel pour les boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton loginButton = new JButton("Se connecter");
        JButton cancelButton = new JButton("Annuler");

        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(loginButton);

        // Actions des boutons
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verifierConnexion(); 
            }
        });

        cancelButton.addActionListener(e -> dispose());
    }

    /**
     * Vérifie la connexion (appel au contrôleur).
     */
    private void verifierConnexion() {
        String identifiant = identifiantField.getText();
        String motDePasse = new String(passwordField.getPassword());

        boolean connexionReussie = this.controleur.tenterConnexion(identifiant, motDePasse, messageLabel, this);

        if (connexionReussie) {
            messageLabel.setText("Connexion réussie !");
            messageLabel.setForeground(Color.GREEN);
            dispose(); // Ferme la fenêtre après connexion réussie
            // ici ouvrir la fenêtre d'accueil 
        } else {
            messageLabel.setText("Identifiant ou mot de passe incorrect !");
            messageLabel.setForeground(Color.RED);
        }
    }
}
