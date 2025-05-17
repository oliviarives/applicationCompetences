package vue;

import modele.Competence;
import modele.Employe;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
/**
 * Vue permettant de modifier un employé
 * Affiche un formulaire avec ses informations et ses compétences
 */
public class ModificationEmployeVue extends JPanel {
    @Serial
    private static final long serialVersionUID = 1L;

    private final JButton buttonConfirmer;
    private final JButton buttonAjouter;
    private final JButton buttonRetirer;
    private final JTextField prenomField;
    private final JTextField nomField;
    private final JTextField loginField;
    private final JPasswordField mdpField;
    private final JTextField posteField;
    private final JSpinner dateEntreeSpinner;
    private final JLabel messageLabel;
    private final JTable tableCompetencesEmploye;
    private final JTable tableToutesCompetences;

    private static final String CATEGORIE = "Catégorie";
    private static final String COMPETENCE = "Compétence";
    private static final String TITRE = "Titre";
    /**
     * Construit la vue de modification d'un employé avec son formulaire et ses compétences
     */
    public ModificationEmployeVue() {
        setLayout(new BorderLayout());

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(550);

        JPanel panelGauche = new JPanel(new BorderLayout());

        JPanel formulaire = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.LINE_END;

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
        loginField.setEditable(false);
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
        dateEntreeSpinner.setEditor(new JSpinner.DateEditor(dateEntreeSpinner, "dd/MM/yyyy"));
        dateEntreeSpinner.setValue(new java.util.Date());
        formulaire.add(dateEntreeSpinner, gbc);

        panelGauche.add(formulaire, BorderLayout.NORTH);

        JPanel panelTableEmploye = new JPanel(new BorderLayout());
        panelTableEmploye.add(new JLabel("Compétences de l'employé", SwingConstants.CENTER), BorderLayout.NORTH);
        DefaultTableModel modelCompetencesEmploye = new DefaultTableModel(new String[]{CATEGORIE, COMPETENCE, TITRE}, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tableCompetencesEmploye = new JTable(modelCompetencesEmploye);
        JScrollPane scrollCompetences = new JScrollPane(tableCompetencesEmploye);
        scrollCompetences.setPreferredSize(new Dimension(450, 150));
        panelTableEmploye.add(scrollCompetences, BorderLayout.CENTER);
        panelGauche.add(panelTableEmploye, BorderLayout.CENTER);

        JPanel panelDroite = new JPanel(new BorderLayout());
        panelDroite.add(new JLabel("Liste des compétences", SwingConstants.CENTER), BorderLayout.NORTH);

        DefaultTableModel modelToutesCompetences = new DefaultTableModel(new String[]{CATEGORIE, COMPETENCE, TITRE}, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tableToutesCompetences = new JTable(modelToutesCompetences);
        JScrollPane scrollToutesCompetences = new JScrollPane(tableToutesCompetences);
        panelDroite.add(scrollToutesCompetences, BorderLayout.CENTER);

        JPanel panelCentre = new JPanel();
        panelCentre.setLayout(new BoxLayout(panelCentre, BoxLayout.Y_AXIS));
        buttonAjouter = new JButton("←");
        buttonRetirer = new JButton("→");
        buttonAjouter.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonRetirer.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentre.add(Box.createVerticalGlue());
        panelCentre.add(buttonAjouter);
        panelCentre.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentre.add(buttonRetirer);
        panelCentre.add(Box.createVerticalGlue());

        splitPane.setLeftComponent(panelGauche);
        splitPane.setRightComponent(panelDroite);

        add(splitPane, BorderLayout.CENTER);
        add(panelCentre, BorderLayout.EAST);

        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setForeground(Color.RED);
        add(messageLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonConfirmer = new JButton("Confirmer");
        buttonPanel.add(buttonConfirmer);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Getters
    public JTextField getPrenomField() { return prenomField; }
    public JTextField getNomField() { return nomField; }
    public JTextField getLoginField() { return loginField; }
    public JTextField getPosteField() { return posteField; }
    public JButton getButtonConfirmer() { return buttonConfirmer; }
    public JButton getButtonAjouter() { return buttonAjouter; }
    public JButton getButtonRetirer() { return buttonRetirer; }
    public JTable getTableCompetencesEmploye() { return tableCompetencesEmploye; }
    public JTable getTableToutesCompetences() { return tableToutesCompetences; }
    /**
     * Remplit la table de toutes les compétences disponibles
     * @param competences liste de toutes les compétences
     */
    public void setToutesCompetences(List<Competence> competences) {
        DefaultTableModel model = new DefaultTableModel(new String[]{CATEGORIE, COMPETENCE, TITRE}, 0);
        for (Competence cmp : competences) {
            model.addRow(new Object[]{cmp.getIdCatCmp(), cmp.getIdCmp(), cmp.getNomCmpFr()});
        }
        tableToutesCompetences.setModel(model);
    }
    /**
     * Remplit la table des compétences associées à l'employé
     * @param competences liste des compétences de l'employé
     */
    public void setTableCompetencesEmploye(List<Competence> competences) {
        DefaultTableModel model = new DefaultTableModel(new String[]{CATEGORIE, COMPETENCE, TITRE}, 0);
        for (Competence cmp : competences) {
            model.addRow(new Object[]{cmp.getIdCatCmp(), cmp.getIdCmp(), cmp.getNomCmpFr()});
        }
        tableCompetencesEmploye.setModel(model);
    }
    /**
     * @return la compétence sélectionnée dans la table de toutes les compétences
     */
    public Competence getCompetenceSelectionneeToutesCmp() {
        int selectedRow = tableToutesCompetences.getSelectedRow();
        if (selectedRow != -1) {
            String idCategorie = (String) tableToutesCompetences.getValueAt(selectedRow, 0);
            int idCompetence = (int) tableToutesCompetences.getValueAt(selectedRow, 1);
            String nomFr = (String) tableToutesCompetences.getValueAt(selectedRow, 2);
            return new Competence(idCompetence, idCategorie, null, nomFr);
        }
        return null;
    }
    /**
     * @return la compétence sélectionnée dans la table des compétences de l'employé
     */
    public Competence getCompetenceSelectionneeEmploye() {
        int selectedRow = tableCompetencesEmploye.getSelectedRow();
        if (selectedRow != -1) {
            String idCategorie = (String) tableCompetencesEmploye.getValueAt(selectedRow, 0);
            int idCompetence = (int) tableCompetencesEmploye.getValueAt(selectedRow, 1);
            String nomFr = (String) tableCompetencesEmploye.getValueAt(selectedRow, 2);
            return new Competence(idCompetence, idCategorie, null, nomFr);
        }
        return null;
    }
    /**
     * @return la liste des compétences affichées dans la table des compétences de l'employé
     */
    public List<Competence> getCompetencesAjoutees() {
        List<Competence> competences = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) tableCompetencesEmploye.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            String idCatCmp = (String) model.getValueAt(i, 0);
            int idCmp = (int) model.getValueAt(i, 1);
            String nomFr = (String) model.getValueAt(i, 2);
            competences.add(new Competence(idCmp, idCatCmp, "", nomFr));
        }
        return competences;
    }
    /**
     * Remplit le formulaire avec les informations d'un employé
     * @param employeSelectionne l'employé à afficher
     */
    public void setEmploye(Employe employeSelectionne) {
        prenomField.setText(employeSelectionne.getPrenom());
        nomField.setText(employeSelectionne.getNom());
        loginField.setText(employeSelectionne.getLogin());
        mdpField.setText(employeSelectionne.gethashedPwd());
        posteField.setText(employeSelectionne.getPoste());
        dateEntreeSpinner.setValue(employeSelectionne.getDateEntree());
    }
    /**
     * Affiche un message d'erreur ou d'information
     * @param message le message à afficher
     */
    public void afficherMessage(String message) {
        messageLabel.setText(message);
    }
}
