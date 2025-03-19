package vue;

import modele.Employe;
import controleur.AjouterPersonnelControleur;
import utilitaires.StyleManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EmployeView extends JPanel {
    private final JTable tableEmploye;
    private final JScrollPane scrollEmploye;
    private final JPanel panelBouttons;
    private final JButton bouttonModifierEmploye;
    private final JButton bouttonAjouterEmploye;

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
    
    public JButton getBouttonAjouterEmploye() {
        return this.bouttonAjouterEmploye;
    }
    
    public JButton getButtonModifierEmploye() {
        this.tableEmploye.setDefaultEditor(Object.class, null);
        return this.bouttonModifierEmploye;
    }
}
