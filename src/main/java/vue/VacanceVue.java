package vue;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;
import java.util.Date;
/**
 * Vue pour la déclaration de vacances d’un employé
 */
public class VacanceVue extends JPanel {
    @Serial
    private static final long serialVersionUID = 1L;
    private final JDateChooser dateDebutChooser;
    private final JDateChooser dateFinChooser;
    private final JButton validerBoutton;
    private final JTextField login;
    /**
     * Constructeur de la vue VacanceVue
     * Initialise les composants de l’interface
     */
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

        // Date de début des vacances
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Date début:"), gbc);

        gbc.gridx = 1;
        dateDebutChooser = new JDateChooser();
        dateDebutChooser.setPreferredSize(new Dimension(150, 30));
        formPanel.add(dateDebutChooser, gbc);

        // Date de fin des vacances
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Date fin:"), gbc);

        gbc.gridx = 1;
        dateFinChooser = new JDateChooser();
        dateFinChooser.setPreferredSize(new Dimension(150, 30));
        formPanel.add(dateFinChooser, gbc);

        // Login de l'employé
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
    /**
     * Définit le login de l’employé affiché dans le champ de texte
     * @param loginText identifiant de l’employé
     */
    public void setLogin(String loginText) {
        login.setText(loginText);
    }
    /**
     * @return login de l’employé saisi (non éditable)
     */
    public String getLogin() {
        return login.getText();
    }
    /**
     * @return date de début sélectionnée
     */
    public String getDateDebut() {
        Date date = dateDebutChooser.getDate();
        if (date != null) {
            return new java.sql.Date(date.getTime()).toString();
        }
        return "";
    }
    /**
     * @return date de fin sélectionnée
     */
    public String getDateFin() {
        Date date = dateFinChooser.getDate();
        if (date != null) {
            return new java.sql.Date(date.getTime()).toString();
        }
        return "";
    }
    /**
     * @return bouton permettant de valider la demande de vacances
     */
    public JButton getValiderBoutton() {
        return validerBoutton;
    }
}
