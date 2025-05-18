package vue;

import controleur.NavigationControleur;
import modele.Mission;
import utilitaires.StyleManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
/**
 * Vue permettant d'afficher et gérer la liste des missions
 * Fournit des filtres par nom et statut, des boutons pour ajouter ou modifier une mission
 * Affiche les missions dans un tableau
 */
public class MissionVue extends JPanel {
    private JTextField txtFiltreNom;
    private JComboBox<String> comboStatut;
    private JButton btnFiltrer;
    private JTable tableMission;
    private JScrollPane scrollMission;
    private JPanel panelFiltreMission;
    private TableRowSorter<DefaultTableModel> sorter;
    private JButton ajouterMission;
    private JButton modifierMission;
    private int idMissionSelect;
    private JButton btnReset;

    /**
     * Constructeur qui initialise la vue Mission avec les champs, filtres et tableaux
     */
    public MissionVue() {
        StyleManager.setupFlatLaf();
        setLayout(new BorderLayout());

        panelFiltreMission = new JPanel(new FlowLayout(FlowLayout.CENTER));

        txtFiltreNom = new JTextField(15);
        panelFiltreMission.add(new JLabel("Nom :"));
        panelFiltreMission.add(txtFiltreNom);

        comboStatut = new JComboBox<>();

        comboStatut.addItem("Tous");
        comboStatut.addItem("En préparation");
        comboStatut.addItem("En cours");
        comboStatut.addItem("Terminée");
        panelFiltreMission.add(new JLabel("Statut :"));
        panelFiltreMission.add(comboStatut);

        // Bouton filtrer
        btnFiltrer = new JButton("Filtrer");
        panelFiltreMission.add(btnFiltrer);

        // Bouton réinitialiser
        this.btnReset = new JButton("Réinitialiser");
        panelFiltreMission.add(btnReset);

        add(panelFiltreMission, BorderLayout.NORTH);

        // Création de la table des missions
        tableMission = new JTable();
        scrollMission = new JScrollPane(tableMission);
        scrollMission.setPreferredSize(new Dimension(1100, 600));
        add(scrollMission, BorderLayout.CENTER);

        // Boutons d'ajout et de modification
        ajouterMission = new JButton("Créer une nouvelle mission");
        modifierMission = new JButton("Modifier une mission");
        JPanel panelBtnModif = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBtnModif.add(ajouterMission);
        panelBtnModif.add(modifierMission);
        add(panelBtnModif, BorderLayout.SOUTH);

    }
    /**
     * Remplit le tableau avec une liste de missions
     * @param missions liste des missions à afficher
     */
    public void setMissions(List<Mission> missions) {
        String[] columnNames = {"Id Mission", "Nom Mission", "Date de début", "Date de fin", "Description", "Statut"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        for (Mission mission : missions) {
            if(mission.getIdSta()!=5){
            Object[] row = {
                    mission.getIdMission(),
                    mission.getTitreMis(),
                    mission.getDateDebutMis(),
                    mission.getDateFinMis(),
                    mission.getDescription(),
                    mission.getNomSta()
            };
            model.addRow(row);
        }}
        tableMission.setModel(model);
        sorter = new TableRowSorter<>(model);
        tableMission.setRowSorter(sorter);
    }
    // Getters
    public JTextField getTxtFiltreNom() {
        return txtFiltreNom;
    }

    public JButton getBtnReset(){
        return this.btnReset;
    }

    public JComboBox<String> getComboStatut() {
        return comboStatut;
    }

    public JButton getBtnFiltrer() {
        return btnFiltrer;
    }

    public JButton getButtonAjouterMission() {
        return this.ajouterMission;
    }

    public JButton getButtonModifierMission() {
        return this.modifierMission;
    }

    /**
     * Retourne l'objet Mission sélectionné dans le tableau
     * @return Mission sélectionnée ou null si aucune
     * @throws SQLException en cas d'erreur lors de la récupération de la mission
     */
    public Mission getMissionSelectionnee() throws SQLException {
        int selectedRow = tableMission.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner la mission que vous souhaitez modifier.");
            return null;
        }
        int idMission = (int) tableMission.getValueAt(selectedRow, 0);
        Mission mission = NavigationControleur.getMissionDao().getMissionById(idMission);
        if(mission == null) {
            JOptionPane.showMessageDialog(this, "Aucune mission trouvée pour l'id " + idMission);
        }
        this.idMissionSelect = idMission;
        return mission;
    }
    /**
     * Retourne l'identifiant de la mission sélectionnée
     * @return identifiant de la mission sélectionnée
     */
    public int getIdMissionSelect(){
        return this.idMissionSelect;
    }
    /**
     * Réinitialise les champs de filtre
     */
    public void ResetFiltres(){
        this.txtFiltreNom.setText("");
        this.comboStatut.setSelectedIndex(0);
    }
}

