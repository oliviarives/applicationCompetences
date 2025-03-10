package vue;

import javax.swing.*;

public class EmployeView extends JPanel {
    private JTable tableEmploye;
    private JScrollPane scrollEmploye;
    private JPanel panelBouttons;
    private JButton bouttonModifierEmploye;
    private JButton bouttonAjouterEmploye;

    public EmployeView() {
        this.bouttonAjouterEmploye = new JButton("Ajouter");
        this.bouttonModifierEmploye = new JButton("Modifier");
        this.panelBouttons = new JPanel();
        this.scrollEmploye = new JScrollPane();
        this.tableEmploye = new JTable();
    }
}
