//ATTENTION : prévoir le cas où le login ne serait pas unique
//PREVOIR LES CHAMPS DE SAISIE OBLIGATOIRES

package vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import modele.Employe;
import modele.MdpUtils;
import utilitaires.StyleManager;

public class AjoutPersonnelVue extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton buttonConfirmer;
    private JButton buttonEffacer;
    private JTextField prenomField;
    private JTextField nomField;
    private JTextField loginField;
    private JPasswordField mdpField;
    private JTextField posteField;
    private JSpinner dateEntreeSpinner;
    private JLabel messageLabel;

    public AjoutPersonnelVue() {
        StyleManager.setupFlatLaf();
        setLayout(new BorderLayout());

        JPanel formulaire = new JPanel(new GridBagLayout());
        GridBagConstraints gbc;

        // Prénom
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.LINE_END;
        formulaire.add(new JLabel("Prénom "), gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        prenomField = new JTextField(20);
        formulaire.add(prenomField, gbc);

        // Nom
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.LINE_END;
        formulaire.add(new JLabel("Nom "), gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        nomField = new JTextField(20);
        formulaire.add(nomField, gbc);

        // Login
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.LINE_END;
        formulaire.add(new JLabel("Login "), gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        loginField = new JTextField(20);
        formulaire.add(loginField, gbc);

        // Mot de passe
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.LINE_END;
        formulaire.add(new JLabel("Mot de passe "), gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mdpField = new JPasswordField(20);
        formulaire.add(mdpField, gbc);

        // Poste
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.LINE_END;
        formulaire.add(new JLabel("Poste "), gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        posteField = new JTextField(20);
        formulaire.add(posteField, gbc);

        // Date d'entrée
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.LINE_END;
        formulaire.add(new JLabel("Date d'entrée "), gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        dateEntreeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateEntreeSpinner, "dd/MM/yyyy");
        dateEntreeSpinner.setEditor(dateEditor);
        dateEntreeSpinner.setValue(new java.util.Date());
        formulaire.add(dateEntreeSpinner, gbc);

        // Message
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setForeground(Color.RED);
        formulaire.add(messageLabel, gbc);

        // Boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonConfirmer = new JButton("Confirmer");
        buttonEffacer = new JButton("Effacer");
        buttonPanel.add(buttonConfirmer);
        buttonPanel.add(buttonEffacer);

        add(formulaire, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        buttonEffacer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                effacerChamps();
            }
        });
    }

    public JTextField getPrenomField() {
        return prenomField;
    }

    public JTextField getNomField() {
        return nomField;
    }

    public JTextField getLoginField() {
        return loginField;
    }

    public JPasswordField getMdpField() {
        return mdpField;
    }

    public JTextField getPosteField() {
        return posteField;
    }

    public JSpinner getDateEntreeSpinner() {
        return dateEntreeSpinner;
    }

    private void effacerChamps() {
        prenomField.setText("");
        nomField.setText("");
        loginField.setText("");
        mdpField.setText("");
        posteField.setText("");
        dateEntreeSpinner.setValue(new java.util.Date());
    }

    public JButton getButtonConfirmer() {
        return buttonConfirmer;
    }

    public void afficherMessage(String message) {
        messageLabel.setText(message);
    }
}