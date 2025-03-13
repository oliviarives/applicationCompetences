package vue;

import modele.Competence;
import modele.Mission;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CompetencesView extends JPanel{
    private JTable tableCompetences;
    private JScrollPane scrollCompetences;


    public CompetencesView() {
        setLayout(new BorderLayout());
        this.tableCompetences = new JTable();
        this.scrollCompetences = new JScrollPane(tableCompetences);
        this.scrollCompetences.setPreferredSize(new Dimension(800,300));
        add(scrollCompetences);
    }

    public void setCompetences(List<Competence> competences) {
        String[] columnNames = {"Id Competence", "Id Categorie","Nom (En)","Nom (FR)"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0){
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
       for (Competence cmp : competences) {
           Object[] row = {cmp.getIdCmp(),cmp.getIdCatCmp(),cmp.getNomCmpEn(),cmp.getNomCmpFr()};
            model.addRow(row);
        }

        this.tableCompetences.setModel(model);

    }
}
