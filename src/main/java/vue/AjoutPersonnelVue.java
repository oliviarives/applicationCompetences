package vue;

import javax.swing.*;
import java.awt.*;

import java.io.Serial;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import modele.Employe;
import modele.MdpUtils;
import utilitaires.StyleManager;

public class AjoutPersonnelVue extends JPanel {
    @Serial
    private static final long serialVersionUID = 1L;

    private final JButton buttonConfirmer;
    private final JButton buttonEffacer;
    private final JTextField prenomField;
    private final JTextField nomField;
    private final JTextField loginField;
    private final JTextField mdpField;
    private final JTextField posteField;
    private final JSpinner dateEntreeSpinner;
    private final JLabel messageLabel;

    public AjoutPersonnelVue() {
        StyleManager.setupFlatLaf();
        setLayout(new BorderLayout());

        JPanel formulaire = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Configuration générale
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.LINE_END;

        // Prénom
        gbc.gridx = 0; gbc.gridy = 0;
        formulaire.add(new JLabel("Prénom : "), gbc);
        gbc.gridx = 1;
        prenomField = new JTextField(20);
        formulaire.add(prenomField, gbc);

        // Nom
        gbc.gridx = 0; gbc.gridy = 1;
        formulaire.add(new JLabel("Nom : "), gbc);
        gbc.gridx = 1;
        nomField = new JTextField(20);
        formulaire.add(nomField, gbc);

        // Login
        gbc.gridx = 0; gbc.gridy = 2;
        formulaire.add(new JLabel("Login : "), gbc);
        gbc.gridx = 1;
        loginField = new JTextField(20);
        formulaire.add(loginField, gbc);

        // Mot de passe
        gbc.gridx = 0; gbc.gridy = 3;
        formulaire.add(new JLabel("Mot de passe : "), gbc);
        gbc.gridx = 1;
        mdpField = new JPasswordField(20);
        formulaire.add(mdpField, gbc);

        // Poste
        gbc.gridx = 0; gbc.gridy = 4;
        formulaire.add(new JLabel("Poste : "), gbc);
        gbc.gridx = 1;
        posteField = new JTextField(20);
        formulaire.add(posteField, gbc);

        // Date d'entrée
        gbc.gridx = 0; gbc.gridy = 5;
        formulaire.add(new JLabel("Date d'entrée : "), gbc);
        gbc.gridx = 1;
        dateEntreeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateEntreeSpinner, "dd/MM/yyyy");
        dateEntreeSpinner.setEditor(dateEditor);
        dateEntreeSpinner.setValue(new java.util.Date());
        formulaire.add(dateEntreeSpinner, gbc);

        // Message d'erreur
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setForeground(Color.RED);
        formulaire.add(messageLabel, gbc);

        // Boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonConfirmer = new JButton("Confirmer");
        buttonEffacer = new JButton("Effacer");
        buttonPanel.add(buttonConfirmer);
        buttonPanel.add(buttonEffacer);

        // Ajout des composants à la vue
        add(formulaire, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Getters pour récupérer les valeurs des champs
    public JTextField getPrenomField() { return prenomField; }
    public JTextField getNomField() { return nomField; }
    public JTextField getLoginField() { return loginField; }
    public JTextField getMdpField() { return mdpField; }
    public JTextField getPosteField() { return posteField; }
    public JSpinner getDateEntreeField() { return dateEntreeSpinner; }

    // Getters pour les boutons
    public JButton getButtonConfirmer() { return buttonConfirmer; }
    public JButton getButtonEffacer() { return buttonEffacer; }

    // Affichage des messages
    public void afficherMessage(String message) {
        messageLabel.setText(message);
    }
}
