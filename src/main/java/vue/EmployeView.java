package vue;

import modele.Employe;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EmployeView extends JPanel {
    private final JTable tableEmploye;
    private final JScrollPane scrollEmploye;
    private final JPanel panelBouttons;
    private final JButton bouttonModifierEmploye;
    private final JButton bouttonAjouterEmploye;

    public EmployeView() {
        this.bouttonAjouterEmploye = new JButton("Ajouter");
        this.bouttonModifierEmploye = new JButton("Modifier");
        this.panelBouttons = new JPanel();

        setLayout(new BorderLayout());
        this.tableEmploye = new JTable();
        this.scrollEmploye = new JScrollPane(tableEmploye);
        this.scrollEmploye.setPreferredSize(new Dimension(800,300));
        add(scrollEmploye);
    }

    public void setEmploye(List<Employe> emp) {
        String[] columnNames = {"Prenom","Nom","Poste"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Employe e : emp) {
            Object[] row = {e.getPrenom(),e.getNom(),e.getPoste()};
            model.addRow(row);
        }

        this.tableEmploye.setModel(model);
        this.tableEmploye.setDefaultEditor(Object.class, null);
    }
}
