package vue;

import modele.Competence;
import modele.Employe;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

public class ModificationEmployeVue extends JPanel {
    @Serial
    private static final long serialVersionUID = 1L;

    private final JButton buttonConfirmer;
    private final JTextField prenomField;
    private final JTextField nomField;
    private final JTextField loginField;
    private final JTextField posteField;
    private final JSpinner dateEntreeSpinner;
    private final JLabel messageLabel;
    private final JTable tableCompetencesEmploye;
    private final JTable tableToutesCompetences;

    private static final String CATEGORIE = "Catégorie";
    private static final String COMPETENCE = "Compétence";
    private static final String TITRE = "Titre";

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
        formulaire.add(new JLabel("Poste : "), gbc);
        gbc.gridx = 1;
        posteField = new JTextField(20);
        formulaire.add(posteField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
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
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tableCompetencesEmploye = new JTable(modelCompetencesEmploye);
        JScrollPane scrollCompetences = new JScrollPane(tableCompetencesEmploye);
        scrollCompetences.setPreferredSize(new Dimension(450, 150));
        panelTableEmploye.add(scrollCompetences, BorderLayout.CENTER);
        panelGauche.add(panelTableEmploye, BorderLayout.CENTER);

        JPanel panelDroite = new JPanel(new BorderLayout());
        panelDroite.add(new JLabel("Liste des compétences", SwingConstants.CENTER), BorderLayout.NORTH);
        DefaultTableModel modelToutesCompetences = new DefaultTableModel(new String[]{CATEGORIE, COMPETENCE, TITRE}, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tableToutesCompetences = new JTable(modelToutesCompetences);
        JScrollPane scrollToutesCompetences = new JScrollPane(tableToutesCompetences);
        panelDroite.add(scrollToutesCompetences, BorderLayout.CENTER);

        // Redimensionner les colonnes pour visibilité
        tableCompetencesEmploye.getColumnModel().getColumn(0).setPreferredWidth(50); // Catégorie
        tableCompetencesEmploye.getColumnModel().getColumn(1).setPreferredWidth(50); // Compétence
        tableToutesCompetences.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableToutesCompetences.getColumnModel().getColumn(1).setPreferredWidth(50);

        splitPane.setLeftComponent(panelGauche);
        splitPane.setRightComponent(panelDroite);
        add(splitPane, BorderLayout.CENTER);

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
    public JSpinner getDateEntreeField() { return dateEntreeSpinner; }
    public JButton getButtonConfirmer() { return buttonConfirmer; }
    public JTable getTableCompetencesEmploye() { return tableCompetencesEmploye; }
    public JTable getTableToutesCompetences() { return tableToutesCompetences; }

    public void setToutesCompetences(List<Competence> competences) {
        DefaultTableModel model = (DefaultTableModel) tableToutesCompetences.getModel();
        model.setRowCount(0);
        for (Competence cmp : competences) {
            model.addRow(new Object[]{cmp.getIdCatCmp(), cmp.getIdCmp(), cmp.getNomCmpFr()});
        }
    }

    public void setTableCompetencesEmploye(List<Competence> competences) {
        DefaultTableModel model = (DefaultTableModel) tableCompetencesEmploye.getModel();
        model.setRowCount(0);
        for (Competence cmp : competences) {
            model.addRow(new Object[]{cmp.getIdCatCmp(), cmp.getIdCmp(), cmp.getNomCmpFr()});
        }
    }

    public void setEmploye(Employe e) {
        prenomField.setText(e.getPrenom());
        nomField.setText(e.getNom());
        loginField.setText(e.getLogin());
        posteField.setText(e.getPoste());
        dateEntreeSpinner.setValue(e.getDateEntree());
    }

    public void afficherMessage(String message) {
        messageLabel.setText(message);
    }
}
