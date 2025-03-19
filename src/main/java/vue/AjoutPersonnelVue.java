package vue;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;

public class AjoutPersonnelVue extends JPanel {
    @Serial
    private static final long serialVersionUID = 1L;

    private final JButton buttonConfirmer;
    private final JButton buttonEffacer;
    private final JButton buttonAjouterCompetence;
    private final JTextField prenomField;
    private final JTextField nomField;
    private final JTextField loginField;
    private final JPasswordField mdpField;
    private final JTextField posteField;
    private final JSpinner dateEntreeSpinner;
    private final JLabel messageLabel;
    private final JTable tableCompetencesEmploye;
    private final JTable tableToutesCompetences;

    public AjoutPersonnelVue() {
        setLayout(new BorderLayout());

        // Panel Principal avec JSplitPane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(500); // Sépare la partie gauche (formulaire) de la droite (tableau)

        // Panel Gauche (Formulaire + Tableau des compétences de l'employé)
        JPanel panelGauche = new JPanel(new BorderLayout());

        // FORMULAIRE
        JPanel formulaire = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.LINE_END;

        // Champs de saisie
        gbc.gridx = 0; gbc.gridy = 0;
        formulaire.add(new JLabel("Prénom : "), gbc);
        gbc.gridx = 1;
        prenomField = new JTextField(20);
        formulaire.add(prenomField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formulaire.add(new JLabel("Nom : "), gbc);
        gbc.gridx = 1;
        nomField = new JTextField(20);
        formulaire.add(nomField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formulaire.add(new JLabel("Login : "), gbc);
        gbc.gridx = 1;
        loginField = new JTextField(20);
        formulaire.add(loginField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formulaire.add(new JLabel("Mot de passe : "), gbc);
        gbc.gridx = 1;
        mdpField = new JPasswordField(20);
        formulaire.add(mdpField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        formulaire.add(new JLabel("Poste : "), gbc);
        gbc.gridx = 1;
        posteField = new JTextField(20);
        formulaire.add(posteField, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        formulaire.add(new JLabel("Date d'entrée : "), gbc);
        gbc.gridx = 1;
        dateEntreeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateEntreeSpinner, "dd/MM/yyyy");
        dateEntreeSpinner.setEditor(dateEditor);
        dateEntreeSpinner.setValue(new java.util.Date());
        formulaire.add(dateEntreeSpinner, gbc);

        // Ajout du formulaire au panel gauche
        panelGauche.add(formulaire, BorderLayout.NORTH);

        // TABLEAU DES COMPÉTENCES DE L'EMPLOYÉ (en bas du formulaire)
        tableCompetencesEmploye = new JTable(new String[][]{}, new String[]{"Compétence", "Niveau"});
        JScrollPane scrollCompetences = new JScrollPane(tableCompetencesEmploye);
        scrollCompetences.setPreferredSize(new Dimension(450, 150));
        scrollCompetences.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollCompetences.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        panelGauche.add(scrollCompetences, BorderLayout.CENTER);

        // PANEL DROITE (Tableau principal : Toutes les compétences)
        JPanel panelDroite = new JPanel(new BorderLayout());
        tableToutesCompetences = new JTable(new String[][]{}, new String[]{"Nom", "Poste", "Date d'entrée"});
        JScrollPane scrollToutesCompetences = new JScrollPane(tableToutesCompetences);
        scrollToutesCompetences.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollToutesCompetences.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        panelDroite.add(scrollToutesCompetences, BorderLayout.CENTER);

        // Ajout des panneaux au SplitPane
        splitPane.setLeftComponent(panelGauche);
        splitPane.setRightComponent(panelDroite);

        // MESSAGE D'ERREUR OU VALIDATION
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setForeground(Color.RED);

        // PANEL BOUTONS
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonConfirmer = new JButton("Confirmer");
        buttonAjouterCompetence = new JButton("Ajouter une compétence");
        buttonEffacer = new JButton("Effacer");
        buttonPanel.add(buttonConfirmer);
        buttonPanel.add(buttonAjouterCompetence);
        buttonPanel.add(buttonEffacer);

        // Ajout des composants principaux
        add(splitPane, BorderLayout.CENTER);
        add(messageLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Getters pour récupérer les valeurs des champs
    public JTextField getPrenomField() { return prenomField; }
    public JTextField getNomField() { return nomField; }
    public JTextField getLoginField() { return loginField; }
    public JPasswordField getMdpField() { return mdpField; }
    public JTextField getPosteField() { return posteField; }
    public JSpinner getDateEntreeField() { return dateEntreeSpinner; }

    // Getters pour les boutons
    public JButton getButtonConfirmer() { return buttonConfirmer; }
    public JButton getButtonAjouterCompetence() { return buttonAjouterCompetence; }
    public JButton getButtonEffacer() { return buttonEffacer; }

    // Getters pour les tableaux
    public JTable getTableCompetencesEmploye() { return tableCompetencesEmploye; }
    public JTable getTableToutesCompetences() { return tableToutesCompetences; }

    // Affichage des messages
    public void afficherMessage(String message) {
        messageLabel.setText(message);
    }
}
