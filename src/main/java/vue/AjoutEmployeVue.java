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

    private final static String MOT_COMPETENCE = "Compétence";
    private final static String MOT_CATEGORIE = "Catégorie";
    private final static String MOT_TITRE = "Titre";
    private final JButton buttonConfirmer;
    private final JButton buttonEffacer;
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

        JPanel panelGauche = new JPanel(new BorderLayout());
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

        JPanel panelTableEmploye = new JPanel(new BorderLayout());
        panelTableEmploye.add(new JLabel("Compétences de l'employé", SwingConstants.CENTER), BorderLayout.NORTH);

        DefaultTableModel modelCompetencesEmploye = new DefaultTableModel(new String[]{MOT_CATEGORIE, MOT_COMPETENCE, MOT_TITRE}, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tableCompetencesEmploye = new JTable(modelCompetencesEmploye);
        JScrollPane scrollCompetences = new JScrollPane(tableCompetencesEmploye);
        scrollCompetences.setPreferredSize(new Dimension(450, 150));
        panelTableEmploye.add(scrollCompetences, BorderLayout.CENTER);
        panelGauche.add(panelTableEmploye, BorderLayout.CENTER);

        tableCompetencesEmploye.getColumnModel().getColumn(0).setPreferredWidth(40);
        tableCompetencesEmploye.getColumnModel().getColumn(1).setPreferredWidth(60);
        tableCompetencesEmploye.getColumnModel().getColumn(2).setPreferredWidth(400);

        JPanel panelDroite = new JPanel(new BorderLayout());
        panelDroite.add(new JLabel("Liste des compétences", SwingConstants.CENTER), BorderLayout.NORTH);

        DefaultTableModel modelToutesCompetences = new DefaultTableModel(new String[]{MOT_CATEGORIE, MOT_COMPETENCE, MOT_TITRE}, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tableToutesCompetences = new JTable(modelToutesCompetences);
        JScrollPane scrollToutesCompetences = new JScrollPane(tableToutesCompetences);
        panelDroite.add(scrollToutesCompetences, BorderLayout.CENTER);

        tableToutesCompetences.getColumnModel().getColumn(0).setPreferredWidth(40);
        tableToutesCompetences.getColumnModel().getColumn(1).setPreferredWidth(60);
        tableToutesCompetences.getColumnModel().getColumn(2).setPreferredWidth(400);

        splitPane.setLeftComponent(panelGauche);
        splitPane.setRightComponent(panelDroite);
        add(splitPane, BorderLayout.CENTER);

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

    public JTable getTableCompetencesEmploye() { return tableCompetencesEmploye; }
    public JTable getTableToutesCompetences() { return tableToutesCompetences; }

    public void setToutesCompetences(List<Competence> competences) {
        DefaultTableModel model = (DefaultTableModel) tableToutesCompetences.getModel();
        model.setRowCount(0);
        for (Competence cmp : competences) {
            model.addRow(new Object[]{cmp.getIdCatCmp(), cmp.getIdCmp(), cmp.getNomCmpFr()});
        }
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
