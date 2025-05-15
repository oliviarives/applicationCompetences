package vue;

import com.toedter.calendar.JDateChooser;
import modele.Mission;
import utilitaires.StyleManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.HashSet;
import java.util.List;

public class MissionVue extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTextField txtFiltreNom;
    private final JDateChooser dateChooser;
    private JComboBox<String> comboStatut;
    private JButton btnFiltrer;
    private JButton btnReset;
    private JTable tableMission;
    private JScrollPane scrollMission;
    private TableRowSorter<DefaultTableModel> sorter;
    private JButton ajouterMission;
    private JButton modifierMission;
    private int idMissionSelect;

    private static final String FORMAT_DATE = "yyyy-MM-dd";

    public MissionVue() {
        StyleManager.setupFlatLaf();
        setLayout(new BorderLayout());

        JPanel panelFiltreMission = new JPanel(new FlowLayout(FlowLayout.CENTER));

        txtFiltreNom = new JTextField(15);
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString(FORMAT_DATE);
        comboStatut = new JComboBox<>();

        comboStatut.addItem("Tous");
        comboStatut.addItem("Préparation");
        comboStatut.addItem("En cours");
        comboStatut.addItem("Terminée");

        btnFiltrer = new JButton("Filtrer");
        btnReset = new JButton("Réinitialiser");

        panelFiltreMission.add(new JLabel("Nom :"));
        panelFiltreMission.add(txtFiltreNom);
        panelFiltreMission.add(new JLabel("Date :"));
        panelFiltreMission.add(dateChooser);
        panelFiltreMission.add(new JLabel("Statut :"));
        panelFiltreMission.add(comboStatut);
        panelFiltreMission.add(btnFiltrer);
        panelFiltreMission.add(btnReset);

        add(panelFiltreMission, BorderLayout.NORTH);

        tableMission = new JTable();
        scrollMission = new JScrollPane(tableMission);
        scrollMission.setPreferredSize(new Dimension(1100, 600));
        add(scrollMission, BorderLayout.CENTER);

        ajouterMission = new JButton("Créer une nouvelle mission");
        modifierMission = new JButton("Modifier une mission");
        JPanel panelBtnModif = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBtnModif.add(ajouterMission);
        panelBtnModif.add(modifierMission);
        add(panelBtnModif, BorderLayout.SOUTH);
    }

    public void setMissions(List<Mission> missions) {
        String[] columnNames = {"Id Mission", "Nom Mission", "Date de début", "Date de fin", "Description", "Statut"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        HashSet<String> statuts = new HashSet<>();
        comboStatut.removeAllItems();
        comboStatut.addItem("Tous");

        for (Mission mission : missions) {
            if (mission.getIdSta() != 5) {
                model.addRow(new Object[]{
                        mission.getIdMission(),
                        mission.getTitreMis(),
                        mission.getDateDebutMis(),
                        mission.getDateFinMis(),
                        mission.getDescription(),
                        mission.getNomSta()
                });
                statuts.add(mission.getNomSta());
            }
        }

        for (String s : statuts) {
            comboStatut.addItem(s);
        }

        tableMission.setModel(model);
        sorter = new TableRowSorter<>(model);
        tableMission.setRowSorter(sorter);
    }

    public Mission getMissionSelectionnee() throws java.sql.SQLException {
        int selectedRow = tableMission.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner la mission que vous souhaitez modifier.");
            return null;
        }
        int idMission = (int) tableMission.getValueAt(selectedRow, 0);
        this.idMissionSelect = idMission;
        return controleur.NavigationControleur.getMissionDao().getMissionById(idMission);
    }

    public JTable getMissionTable() {
        return this.tableMission;
    }

    public int getIdMissionSelect() {
        return this.idMissionSelect;
    }

    public JTextField getTxtFiltreNom() {
        return txtFiltreNom;
    }

    public JDateChooser getDateChooser() {
        return dateChooser;
    }

    public JComboBox<String> getComboStatut() {
        return comboStatut;
    }

    public JButton getBtnFiltrer() {
        return btnFiltrer;
    }

    public JButton getBtnReset() {
        return btnReset;
    }

    public JButton getButtonAjouterMission() {
        return this.ajouterMission;
    }

    public JButton getButtonModifierMission() {
        return this.modifierMission;
    }

    public TableRowSorter<DefaultTableModel> getSorter() {
        return sorter;
    }
}
