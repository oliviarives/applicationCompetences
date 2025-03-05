package vue;

import modele.Mission;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.List;

public class MissionView extends JPanel{

    private JTable tableMission;
    private JScrollPane scrollMission;
    private JPanel panelFiltreMission;
    private JPanel panelBtnModif;
    private JComboBox filtreTitre;
    private TableRowSorter<DefaultTableModel> sorter;
    private JButton ajouterMission;
    private JButton ModifierMission;
    private JLabel labelFiltreStatut;


    public MissionView() {
        setLayout(new BorderLayout());

        this.tableMission = new JTable();
        //this.tableMission.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //this.tableMission.setRowSorter();

        this.scrollMission = new JScrollPane(tableMission);
        this.scrollMission.setPreferredSize(new Dimension(800,300));

        this.filtreTitre = new JComboBox();
        this.filtreTitre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedMission = (String) filtreTitre.getSelectedItem();
                System.out.println("Mission sélectionnée : " + selectedMission);

                if (sorter == null) {
                    System.out.println("Erreur : sorter est null !");
                    return;
                }

                if (selectedMission == null || selectedMission.isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + selectedMission, 5));
                }
            }
        });
        this.labelFiltreStatut = new JLabel("Filtre statut");
        //add(labelFiltreStatut, BorderLayout.WEST);
        ///add(filtreTitre,BorderLayout.NORTH);
        this.panelFiltreMission = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.panelFiltreMission.add(this.labelFiltreStatut);
        this.panelFiltreMission.add(this.filtreTitre);
        add(this.panelFiltreMission, BorderLayout.NORTH);
        add(scrollMission,BorderLayout.CENTER);
        this.ajouterMission = new JButton("Creer une nouvelle mission");
        this.ModifierMission = new JButton("Modifier");
        this.panelBtnModif = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.panelBtnModif.add(this.ajouterMission);
        this.panelBtnModif.add(this.ModifierMission);
        add(this.panelBtnModif, BorderLayout.SOUTH);
        //add(ajouterMission,BorderLayout.SOUTH);

    }

    public void setMissions(List<Mission> missions) {
        String[] columnNames = {"Id Mission", "Titre Mission","Date Debut Mission","dateFinMis","description","Statut"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        HashSet<String> statuts = new HashSet<>();
        this.filtreTitre.addItem("");
        for (Mission mission : missions) {
            Object[] row = {mission.getIdMission(), mission.getTitreMis(),mission.getDateDebutMis(),mission.getDateFinMis(),mission.getDescription(),mission.getNomSta()};
            model.addRow(row);
            if (statuts.add(row[5].toString())) {
                this.filtreTitre.addItem(row[5]);
            }
        }
        ;
        this.tableMission.setModel(model);
        this.sorter = new TableRowSorter<>(model);
        //TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        this.tableMission.setRowSorter(sorter);
        //this.tableMission.getColumnModel().getColumn(0).setPreferredWidth(70);
    }




    public JButton getButtonModifierMission(){
        return this.ajouterMission;
    }
}
