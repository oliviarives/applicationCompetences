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
    private final JTable tableEmploye;
    private final JScrollPane scrollEmploye;
    private final JPanel panelBouttons;
    private final JButton bouttonModifierEmploye;
    private final JButton bouttonAjouterEmploye;
    private List<Employe> employes;

    public EmployeView() {
        StyleManager.setupFlatLaf();
        setLayout(new BorderLayout());

        // Initialisation des boutons
        this.bouttonAjouterEmploye = new JButton("Ajouter");
        this.bouttonModifierEmploye = new JButton("Modifier");

        // Table et scroll
        this.tableEmploye = new JTable();
        this.scrollEmploye = new JScrollPane(tableEmploye);
        this.scrollEmploye.setPreferredSize(new Dimension(800, 300));
        add(scrollEmploye, BorderLayout.CENTER);

        // Panel des boutons
        this.panelBouttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.panelBouttons.add(this.bouttonAjouterEmploye);
        this.panelBouttons.add(this.bouttonModifierEmploye);
        add(this.panelBouttons, BorderLayout.SOUTH);
    }

    public void setEmploye(List<Employe> emp) {
        this.employes = emp; // pour permettre la récupération exacte via getEmployeSelectionne()

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
