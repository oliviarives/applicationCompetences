package vue;

import com.toedter.calendar.JDateChooser;
import controleur.NavigationControleur;
import modele.Mission;
import modele.dao.DAOMission;
import utilitaires.StyleManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MissionVue extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTextField txtFiltreNom;
    private final JDateChooser dateChooser;
    private JComboBox<String> comboStatut;
    private JButton btnFiltrer;
    private JTable tableMission;
    private JScrollPane scrollMission;
    private JPanel panelFiltreMission;
    private TableRowSorter<DefaultTableModel> sorter;
    private JButton ajouterMission;
    private JButton modifierMission;
    private DAOMission missionDAO;
    private int idMissionSelect;

    private static final String FORMAT_DATE = "yyyy-MM-dd";//format utile pour l'insertion SQL

    public MissionVue() {
        StyleManager.setupFlatLaf();
        setLayout(new BorderLayout());

        // Création du panel de filtres multi-critères
        panelFiltreMission = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Filtre sur le nom
        txtFiltreNom = new JTextField(15);
        panelFiltreMission.add(new JLabel("Nom :"));
        panelFiltreMission.add(txtFiltreNom);

        // Filtre sur la date (utilise JDateChooser)
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString(FORMAT_DATE);
        panelFiltreMission.add(new JLabel("Date :"));
        panelFiltreMission.add(dateChooser);

        // Filtre sur le statut
        comboStatut = new JComboBox<>();
        // On peut remplir le combo avec "Tous" puis les statuts récupérés (ici on met en dur)
        comboStatut.addItem("Tous");
        comboStatut.addItem("Préparation");
        comboStatut.addItem("En cours");
        comboStatut.addItem("Terminée");
        panelFiltreMission.add(new JLabel("Statut :"));
        panelFiltreMission.add(comboStatut);

        // Bouton pour lancer le filtre
        btnFiltrer = new JButton("Filtrer");
        panelFiltreMission.add(btnFiltrer);
        btnFiltrer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<RowFilter<Object, Object>> filters = new ArrayList<>();

                // Filtre sur le nom (colonne 1 : Nom Mission)
                String nom = txtFiltreNom.getText().trim();
                if (!nom.isEmpty()) {
                    filters.add(RowFilter.regexFilter("(?i)" + nom, 1));
                }

                // Filtre sur la date (colonne 2 : Date de début)
                if (dateChooser.getDate() != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
                    String dateStr = sdf.format(dateChooser.getDate());
                    filters.add(RowFilter.regexFilter(dateStr, 2));
                }

                // Filtre sur le statut (colonne 5)
                String statut = (String) comboStatut.getSelectedItem();
                if (statut != null && !statut.equals("Tous")) {
                    filters.add(RowFilter.regexFilter("(?i)" + statut, 5));
                }

                // Si aucun critère n'est saisi, réinitialise le filtre
                if (filters.isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    try {
                        sorter.setRowFilter(RowFilter.andFilter(filters));
                    } catch (IllegalArgumentException ex) {
                        // Si la combinaison de filtres échoue pour une raison quelconque, on réinitialise
                        sorter.setRowFilter(null);
                    }
                }
            }
        });


        JButton btnReset = new JButton("Réinitialiser");
        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtFiltreNom.setText("");
                dateChooser.setDate(null);
                comboStatut.setSelectedIndex(0); // Sélectionne "Tous"
                if(sorter != null) {
                    sorter.setRowFilter(null);
                }
                List<Mission> toutesLesMissions = missionDAO.findAll();
                setMissions(toutesLesMissions);
            }
        });
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
        JPanel panelBtnModif = new JPanel(new FlowLayout(FlowLayout.CENTER));//panel recevant les boutons pour modifier et ajouter une mission
        panelBtnModif.add(ajouterMission);
        panelBtnModif.add(modifierMission);
        add(panelBtnModif, BorderLayout.SOUTH);//ajout du conteneur des boutons au conteneur de la vue

        // Action sur le bouton "Filtrer"
        btnFiltrer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<RowFilter<Object, Object>> filters = new ArrayList<>();

                // Filtre sur le nom (colonne 1 : Nom Mission)
                String nom = txtFiltreNom.getText().trim();
                if (!nom.isEmpty()) {
                    // "(?i)" rend le filtre insensible à la casse
                    filters.add(RowFilter.regexFilter("(?i)" + nom, 1));
                }

                // Filtre sur la date (colonne 2 : Date de début)
                if (dateChooser.getDate() != null) {
                    // On formate la date pour correspondre au format de la table
                    SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
                    String dateStr = sdf.format(dateChooser.getDate());
                    // On applique le filtre sur la colonne 2 (date de début)
                    filters.add(RowFilter.regexFilter(dateStr, 2));
                }

                // Filtre sur le statut (colonne 5)
                String statut = (String) comboStatut.getSelectedItem();
                if (statut != null && !statut.equals("Tous")) {
                    filters.add(RowFilter.regexFilter("(?i)" + statut, 5));
                }

                // Combine les filtres avec AND (tous les critères doivent être respectés)
                RowFilter<Object, Object> combinedFilter = RowFilter.andFilter(filters);
                if (sorter != null) {
                    sorter.setRowFilter(combinedFilter);
                }
            }
        });
    }
    //rempli le tableau de la liste de mission fournie
    public void setMissions(List<Mission> missions) {
        String[] columnNames = {"Id Mission", "Nom Mission", "Date de début", "Date de fin", "Description", "Statut"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        HashSet<String> statuts = new HashSet<>();
        // On peut aussi mettre à jour le combo de statut à partir des données
        comboStatut.removeAllItems();
        comboStatut.addItem("Tous");
        for (Mission mission : missions) {
            Object[] row = {
                    mission.getIdMission(),
                    mission.getTitreMis(),
                    mission.getDateDebutMis(),
                    mission.getDateFinMis(),
                    mission.getDescription(),
                    mission.getNomSta()
            };
            model.addRow(row);
            statuts.add(mission.getNomSta());
        }
        // Remplissage du combo avec les statuts uniques
        for (String s : statuts) {
            comboStatut.addItem(s);
        }
        tableMission.setModel(model);
        sorter = new TableRowSorter<>(model);
        tableMission.setRowSorter(sorter);
    }
    // Méthodes d'accès pour le contrôleur
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

    public JButton getButtonAjouterMission() {
        return this.ajouterMission;
    }

    public JButton getButtonModifierMission() {
        return this.modifierMission;
    }

    //permet de recupererer la mission selectionne dans le tableau
    public Mission getMissionSelectionnee() {
        int selectedRow = tableMission.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner la mission que vous souhaitez modifier.");
            return null;
        }
        int idMission = (int) tableMission.getValueAt(selectedRow, 0);
        // Utiliser le DAO pour récupérer l'objet complet à partir de l'id
        Mission mission = NavigationControleur.getMissionDao().getMissionById(idMission);
        if(mission == null) {
            JOptionPane.showMessageDialog(this, "Aucune mission trouvée pour l'id " + idMission);
        }
        this.idMissionSelect = idMission;
        return mission;
    }



    public JTable getMissionTable() {
        return this.tableMission;
    }

    //retourne seulement l'id de la mission selectionnée
    public int getIdMissionSelect(){
        return this.idMissionSelect;
    }
}

