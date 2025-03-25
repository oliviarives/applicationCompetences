package vue;

import modele.Competence;
import utilitaires.StyleManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CompetencesView extends JPanel{
    private final JTable tableCompetences;


    public CompetencesView() {
        StyleManager.setupFlatLaf();
        setLayout(new BorderLayout());
        this.tableCompetences = new JTable();
        JScrollPane scrollCompetences = new JScrollPane(tableCompetences);
        StyleManager.autoResizeTable(tableCompetences, scrollCompetences);
        scrollCompetences.setPreferredSize(new Dimension(800,300));
        add(scrollCompetences);
    }

    public void setCompetences(List<Competence> competences) {
        String[] columnNames = {"Id Competence", "Id Categorie","Nom (En)","Nom (FR)"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0){
            @Override
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
