package vue;

import modele.Mission;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
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
    private JButton modifierMission;
    private JLabel labelFiltreStatut;


    public MissionView() {
        setLayout(new BorderLayout());

        this.tableMission = new JTable();
        //this.tableMission.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //this.tableMission.setRowSorter();

        this.scrollMission = new JScrollPane(tableMission);
        this.scrollMission.setPreferredSize(new Dimension(1100,600));

        this.filtreTitre = new JComboBox();
        this.filtreTitre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedMission = (String) filtreTitre.getSelectedItem();
                //System.out.println("Mission sélectionnée : " + selectedMission);

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
        this.modifierMission = new JButton("Modifier");
        this.panelBtnModif = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.panelBtnModif.add(this.ajouterMission);
        this.panelBtnModif.add(this.modifierMission);
        add(this.panelBtnModif, BorderLayout.SOUTH);
        //add(ajouterMission,BorderLayout.SOUTH);

    }

    public void setMissions(List<Mission> missions) {
        String[] columnNames = {"Id Mission", "Titre Mission","Date Debut Mission","dateFinMis","description","Statut"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0){
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        HashSet<String> statuts = new HashSet<>();
        this.filtreTitre.removeAllItems();
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




    public JButton getButtonAjouterMission(){
        return this.ajouterMission;
    }
    
    public JButton getButtonModifierMission(){
        return this.modifierMission;
    }

    public Mission getMissionSelectionnee() {
        int selectedRow = tableMission.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une mission.");
            return null;
        }

        int idMission = (int) tableMission.getValueAt(selectedRow, 0);
        String titre = (String) tableMission.getValueAt(selectedRow, 1);
        Date dateDebut = (Date) tableMission.getValueAt(selectedRow, 2);
        Date dateFin = (Date) tableMission.getValueAt(selectedRow, 3);
        String description = (String) tableMission.getValueAt(selectedRow, 4);
        String nomSta = (String) tableMission.getValueAt(selectedRow, 5);

        return new Mission(titre, dateDebut, dateFin, description, new Date(System.currentTimeMillis()), 0, nomSta);
    }

    public JTable getMissionTable(){
        return this.tableMission;
    }

}
