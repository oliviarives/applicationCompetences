package vue;

import modele.Competence;
import utilitaires.StyleManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
/**
 * Vue qui affiche la liste des compétences dans un tableau
 */
public class CompetencesVue extends JPanel{
    private JTable tableCompetences;
    private JScrollPane scrollCompetences;

    /**
     * Constructeur qui initialise l'interface graphique de la vue
     */
    public CompetencesVue() {
        StyleManager.setupFlatLaf();
        setLayout(new BorderLayout());
        this.tableCompetences = new JTable();
        this.scrollCompetences = new JScrollPane(tableCompetences);
        StyleManager.autoResizeTable(tableCompetences, scrollCompetences);
        this.scrollCompetences.setPreferredSize(new Dimension(800,300));
        add(scrollCompetences);
    }
    /**
     * Met à jour les données du tableau avec la liste des compétences fournies
     *
     * @param competences liste des compétences à afficher
     */
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
