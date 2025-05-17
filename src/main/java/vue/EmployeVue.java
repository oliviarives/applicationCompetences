package vue;

import modele.Employe;
import utilitaires.StyleManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
/**
 * Vue dédiée à la gestion des employés
 * Permet l'affichage, l'ajout, la modification d'employés et la gestion de leurs vacances
 */
public class EmployeVue extends JPanel {
    private final JTable tableEmploye;
    private final JScrollPane scrollEmploye;
    private final JPanel panelBouttons;
    private final JButton bouttonModifierEmploye;
    private final JButton bouttonAjouterEmploye;
    private final JButton boutonVacance;
    private List<Employe> employes;
    /**
     * Constructeur de la vue des employés
     * Initialise les composants graphiques et le style
     */
    public EmployeVue() {
        StyleManager.setupFlatLaf();
        setLayout(new BorderLayout());

        // Boutons
        this.bouttonAjouterEmploye = new JButton("Ajouter");
        this.bouttonModifierEmploye = new JButton("Modifier");
        this.boutonVacance = new JButton("Vacance");

        // Tables
        this.tableEmploye = new JTable();
        this.scrollEmploye = new JScrollPane(tableEmploye);
        this.scrollEmploye.setPreferredSize(new Dimension(800, 300));
        add(scrollEmploye, BorderLayout.CENTER);

        //Panel des boutons
        this.panelBouttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.panelBouttons.add(this.bouttonAjouterEmploye);
        this.panelBouttons.add(this.bouttonModifierEmploye);
        this.panelBouttons.add(this.boutonVacance);
        add(this.panelBouttons, BorderLayout.SOUTH);
    }
    /**
     * Met à jour le tableau avec la liste des employés fournie
     * @param emp Liste des employés à afficher
     */
    public void setEmploye(List<Employe> emp) {
        this.employes = emp;

        String[] columnNames = {"Prenom","Nom","Poste"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0){
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        for (Employe e : emp) {
            Object[] row = {e.getPrenom(), e.getNom(), e.getPoste()};
            model.addRow(row);
        }

        this.tableEmploye.setModel(model);
    }
    /**
     * Retourne le bouton pour ajouter un employé
     * @return JButton d'ajout
     */
    public JButton getButtonAjouterEmploye() {
        return this.bouttonAjouterEmploye;
    }
    /**
     * Retourne le bouton pour modifier un employé
     * @return JButton de modification
     */
    public JButton getButtonModifierEmploye() {
        this.tableEmploye.setDefaultEditor(Object.class, null);
        return this.bouttonModifierEmploye;
    }
    /**
     * Retourne le bouton permettant d'ajouter une période de vacances à un employé
     * @return JButton pour les vacances
     */
    public JButton getButtonVacance() {
        return this.boutonVacance;
    }
    /**
     * Retourne l'employé sélectionné dans le tableau
     * @return Employé sélectionné ou null sinon
     */
    public Employe getEmployeSelectionne() {
        int selectedRow = tableEmploye.getSelectedRow();
        if (selectedRow >= 0 && employes != null && selectedRow < employes.size()) {
            return employes.get(selectedRow);
        }
        return null;
    }


}
