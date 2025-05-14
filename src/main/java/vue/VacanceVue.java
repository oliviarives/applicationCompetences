package vue;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.io.Serial;
import java.util.Date;

public class VacanceVue extends JPanel {
    @Serial
    private static final long serialVersionUID = 1L;

    private final JDateChooser dateDebutChooser;
    private final JDateChooser dateFinChooser;
    private final JButton validerBoutton;
    private final JTextField login;

    public VacanceVue() {
        setLayout(new BorderLayout());

        // Titre
        JLabel titleLabel = new JLabel("Formulaire de Vacances", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        // Panneau pour le formulaire
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Date début
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Date début:"), gbc);

        gbc.gridx = 1;
        dateDebutChooser = new JDateChooser();
        dateDebutChooser.setPreferredSize(new Dimension(150, 30));
        formPanel.add(dateDebutChooser, gbc);

        // Date fin
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Date fin:"), gbc);

        gbc.gridx = 1;
        dateFinChooser = new JDateChooser();
        dateFinChooser.setPreferredSize(new Dimension(150, 30));
        formPanel.add(dateFinChooser, gbc);

        // Login
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Login:"), gbc);

        gbc.gridx = 1;
        login = new JTextField(20);
        login.setEditable(false);
        login.setPreferredSize(new Dimension(150, 30));
        formPanel.add(login, gbc);

        // Bouton Valider
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        validerBoutton = new JButton("Valider");
        validerBoutton.setPreferredSize(new Dimension(150, 40));
        formPanel.add(validerBoutton, gbc);

        add(formPanel, BorderLayout.CENTER);
    }

    public void setLogin(String loginText) {
        login.setText(loginText);
    }

    public String getLogin() {
        return login.getText();
    }

    public String getDateDebut() {
        Date date = dateDebutChooser.getDate();
        if (date != null) {
            return new java.sql.Date(date.getTime()).toString();
        }
        return "";
    }

    public String getDateFin() {
        Date date = dateFinChooser.getDate();
        if (date != null) {
            return new java.sql.Date(date.getTime()).toString();
        }
        return "";
    }

    public JButton getValiderBoutton() {
        return validerBoutton;
    }

    public void afficherMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
