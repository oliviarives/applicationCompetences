package vue;

import modele.Competence;
import modele.Employe;
import controleur.AjouterPersonnelControleur;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EmployeView extends JPanel {
    private JTable tableEmploye;
    private JScrollPane scrollEmploye;
    private JPanel panelBouttons;
    private JButton bouttonModifierEmploye;
    private JButton bouttonAjouterEmploye;

    public EmployeView() {
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
        String[] columnNames = {"Prenom", "Nom", "Poste"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

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
        return this.bouttonModifierEmploye;
    }
}
