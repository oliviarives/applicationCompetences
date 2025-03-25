package vue;

import modele.Employe;
import utilitaires.StyleManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.Serial;
import java.util.List;

public class EmployeView extends JPanel {
    @Serial
    private static final long serialVersionUID = 1L;
    private final JButton bouttonModifierEmploye;
    private final JButton bouttonAjouterEmploye;
    private static List<Employe> employes;
    private final JTable tableEmploye;

    public EmployeView() {
        StyleManager.setupFlatLaf();
        setLayout(new BorderLayout());

        // Initialisation des boutons
        this.bouttonAjouterEmploye = new JButton("Ajouter");
        this.bouttonModifierEmploye = new JButton("Modifier");

        // Table et scroll
        this.tableEmploye = new JTable();
        JScrollPane scrollEmploye = new JScrollPane(tableEmploye);
        scrollEmploye.setPreferredSize(new Dimension(800, 300));
        add(scrollEmploye, BorderLayout.CENTER);

        // Panel des boutons
        JPanel panelBouttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBouttons.add(this.bouttonAjouterEmploye);
        panelBouttons.add(this.bouttonModifierEmploye);
        add(panelBouttons, BorderLayout.SOUTH);
    }

    //Correctifs SonarQube
    public static synchronized void updateEmployes(List<Employe> emp) {
        employes = emp;
    }

    public void setEmploye(List<Employe> emp) {
        updateEmployes(emp); // pour permettre la récupération exacte via getEmployeSelectionne()

        String[] columnNames = {"Prenom","Nom","Poste"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0){
            @Override
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

    public JButton getButtonAjouterEmploye() {
        return this.bouttonAjouterEmploye;
    }

    public JButton getButtonModifierEmploye() {
        this.tableEmploye.setDefaultEditor(Object.class, null);
        return this.bouttonModifierEmploye;
    }

    public Employe getEmployeSelectionne() {
        int selectedRow = tableEmploye.getSelectedRow();
        if (selectedRow >= 0 && employes != null && selectedRow < employes.size()) {
            return employes.get(selectedRow);
        }
        return null;
    }
}
