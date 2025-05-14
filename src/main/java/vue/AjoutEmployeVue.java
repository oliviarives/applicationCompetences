package vue;

import modele.Competence;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

public class AjoutEmployeVue extends JPanel {
    @Serial
    private static final long serialVersionUID = 1L;

    private final JButton buttonConfirmer;
    private final JButton buttonEffacer;
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

    public AjoutEmployeVue() {
        setLayout(new BorderLayout());

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(550);

        //panel gauche (formulaire + compétences emp)
        JPanel panelGauche = new JPanel(new BorderLayout());

        //formulaire
        JPanel formulaire = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.LINE_END;

        gbc.gridx = 0; gbc.gridy = 0;
        formulaire.add(new JLabel("Prénom "), gbc);
        gbc.gridx = 1;
        prenomField = new JTextField(20);
        formulaire.add(prenomField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formulaire.add(new JLabel("Nom "), gbc);
        gbc.gridx = 1;
        nomField = new JTextField(20);
        formulaire.add(nomField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formulaire.add(new JLabel("Login "), gbc);
        gbc.gridx = 1;
        loginField = new JTextField(20);
        formulaire.add(loginField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formulaire.add(new JLabel("Mot de passe "), gbc);
        gbc.gridx = 1;
        mdpField = new JPasswordField(20);
        formulaire.add(mdpField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        formulaire.add(new JLabel("Poste "), gbc);
        gbc.gridx = 1;
        posteField = new JTextField(20);
        formulaire.add(posteField, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        formulaire.add(new JLabel("Date d'entrée "), gbc);
        gbc.gridx = 1;
        dateEntreeSpinner = new JSpinner(new SpinnerDateModel());
        dateEntreeSpinner.setEditor(new JSpinner.DateEditor(dateEntreeSpinner, "dd/MM/yyyy"));
        dateEntreeSpinner.setValue(new java.util.Date());
        formulaire.add(dateEntreeSpinner, gbc);

        panelGauche.add(formulaire, BorderLayout.NORTH);

        // === Tableau compétences employé + titre ===
        JPanel panelTableEmploye = new JPanel(new BorderLayout());
        panelTableEmploye.add(new JLabel("Compétences de l'employé", SwingConstants.CENTER), BorderLayout.NORTH);
        DefaultTableModel modelCompetencesEmploye = new DefaultTableModel(new String[]{"Catégorie", "Compétence", "Titre"}, 0) {
            public boolean isCellEditable(int row, int col) {  //cellules de la table ne sont plus editables
                return false;
            }
        };
        tableCompetencesEmploye = new JTable(modelCompetencesEmploye);
        JScrollPane scrollCompetences = new JScrollPane(tableCompetencesEmploye);
        scrollCompetences.setPreferredSize(new Dimension(450, 150));
        panelTableEmploye.add(scrollCompetences, BorderLayout.CENTER);
        panelGauche.add(panelTableEmploye, BorderLayout.CENTER);

        //panel droite : Toutes les compétences + flèches
        JPanel panelDroite = new JPanel(new BorderLayout());

        panelDroite.add(new JLabel("Liste des compétences", SwingConstants.CENTER), BorderLayout.NORTH);

        DefaultTableModel modelToutesCompetences = new DefaultTableModel(new String[]{"Catégorie", "Compétence", "Titre"}, 0) {
            public boolean isCellEditable(int row, int col) {  //cellules de la table ne sont plus editables
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
        buttonEffacer = new JButton("Effacer");
        buttonPanel.add(buttonConfirmer);
        buttonPanel.add(buttonEffacer);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public JTextField getPrenomField() { return prenomField; }
    public JTextField getNomField() { return nomField; }
    public JTextField getLoginField() { return loginField; }
    public JPasswordField getMdpField() { return mdpField; }
    public JTextField getPosteField() { return posteField; }
    public JSpinner getDateEntreeField() { return dateEntreeSpinner; }

    public JButton getButtonConfirmer() { return buttonConfirmer; }
    public JButton getButtonEffacer() { return buttonEffacer; }
    public JButton getButtonAjouter() { return buttonAjouter; }
    public JButton getButtonRetirer() { return buttonRetirer; }

    public JTable getTableCompetencesEmploye() { return tableCompetencesEmploye; }
    public JTable getTableToutesCompetences() { return tableToutesCompetences; }

    public void setToutesCompetences(java.util.List<Competence> competences) {
        DefaultTableModel model = new DefaultTableModel(new String[]{"Catégorie", "Compétence", "Titre"}, 0);
        for (Competence cmp : competences) {
            model.addRow(new Object[]{cmp.getIdCatCmp(), cmp.getIdCmp(), cmp.getNomCmpFr()});
        }
        tableToutesCompetences.setModel(model);
    }

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

    public void afficherMessage(String message) {
        messageLabel.setText(message);
    }
}
