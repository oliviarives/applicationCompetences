package vue;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

import controleur.MissionControleur;
import modele.Mission;
import modele.Statut;

public class MissionVue {
    private MissionControleur controleur;
    private JFrame frame;
    private JLabel lblTitre, lblStatut, lblDateDebut, lblDateFin, lblDescription, lblCollaborateurs;
    private JTable tableCollaborateurs;
    private JScrollPane scrollPane;
    private JButton btnModifier;
    private JButton btnRetour;


    public MissionVue(MissionControleur controleur) {
        this.controleur = controleur;
        initialiserUI();
    }

    private void initialiserUI() {
        frame = new JFrame("Détail de la Mission");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Panel principal 
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(240, 240, 240));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel Titre 
        JPanel panelTitre = new JPanel(new GridLayout(2, 1));
        lblTitre = new JLabel("Titre mission", SwingConstants.CENTER);
        lblTitre.setFont(new Font("Arial", Font.BOLD, 24));
        lblStatut = new JLabel("Statut", SwingConstants.CENTER);
        lblStatut.setFont(new Font("Arial", Font.PLAIN, 20));
        panelTitre.add(lblTitre);
        panelTitre.add(lblStatut);
        panelPrincipal.add(panelTitre, BorderLayout.NORTH);

        // Panel Infos
        JPanel panelInfos = new JPanel(new GridLayout(3, 2, 10, 10));
        panelInfos.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        panelInfos.add(new JLabel("Date début :"));
        lblDateDebut = new JLabel();
        panelInfos.add(lblDateDebut);

        panelInfos.add(new JLabel("Date fin :"));
        lblDateFin = new JLabel();
        panelInfos.add(lblDateFin);

        panelInfos.add(new JLabel("Description :"));
        lblDescription = new JLabel();
        panelInfos.add(lblDescription);

        panelPrincipal.add(panelInfos, BorderLayout.CENTER);

        // Panel Collaborateurs 
        JPanel panelCollaborateurs = new JPanel(new BorderLayout());
        lblCollaborateurs = new JLabel("Collaborateurs", SwingConstants.CENTER);
        lblCollaborateurs.setFont(new Font("Arial", Font.BOLD, 18));
        panelCollaborateurs.add(lblCollaborateurs, BorderLayout.NORTH);

        String[] colonnes = {"Nom", "Prénom", "Rôle"};
        String[][] donnees = {}; 
        tableCollaborateurs = new JTable(donnees, colonnes);
        tableCollaborateurs.setFont(new Font("Arial", Font.PLAIN, 16));
        tableCollaborateurs.setRowHeight(30);
        scrollPane = new JScrollPane(tableCollaborateurs);
        scrollPane.setPreferredSize(new Dimension(600, 200));
        panelCollaborateurs.add(scrollPane, BorderLayout.CENTER);

        // Panel des boutons
        JPanel panelBoutons = new JPanel();
        btnModifier = new JButton("Modifier la mission");
        btnRetour = new JButton("Retour aux missions");
        panelBoutons.add(btnModifier);
        panelBoutons.add(btnRetour);

        // Panel principal avec gestion du redimensionnement
        JPanel panelCentre = new JPanel(new BorderLayout());
        panelCentre.add(panelInfos, BorderLayout.NORTH);
        panelCentre.add(panelCollaborateurs, BorderLayout.CENTER);

        panelPrincipal.add(panelCentre, BorderLayout.CENTER);
        panelPrincipal.add(panelBoutons, BorderLayout.SOUTH);

        frame.add(panelPrincipal);
        
        btnModifier.addActionListener(e -> modifierMission());
        btnRetour.addActionListener(e -> retourAuxMissions());

        frame.setVisible(true);
    }


    public void afficherMission(Mission mission) {
        lblTitre.setText(mission.getTitre());
        lblStatut.setText(mission.getStatut().toString());
        lblDateDebut.setText(mission.getDateDebut().toString());
        lblDateFin.setText(mission.getDateFin().toString());
        lblDescription.setText(mission.getDescription());
    }
    
    private void modifierMission() {
        Mission mission = controleur.chercherMissionParId(1); 
        if (mission == null) {
            JOptionPane.showMessageDialog(frame, "Mission introuvable !");
            return;
        }

        // Fenêtre de saisie avec plusieurs champs
        JTextField txtTitre = new JTextField(mission.getTitre());
        JTextField txtStatut = new JTextField(mission.getStatut().toString());
        JTextField txtDateDebut = new JTextField(mission.getDateDebut().toString());
        JTextField txtDateFin = new JTextField(mission.getDateFin().toString());
        JTextField txtDescription = new JTextField(mission.getDescription());

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.add(new JLabel("Titre :"));
        panel.add(txtTitre);
        panel.add(new JLabel("Statut :"));
        panel.add(txtStatut);
        panel.add(new JLabel("Date de début :"));
        panel.add(txtDateDebut);
        panel.add(new JLabel("Date de fin :"));
        panel.add(txtDateFin);
        panel.add(new JLabel("Description :"));
        panel.add(txtDescription);

        int result = JOptionPane.showConfirmDialog(frame, panel, 
                "Modifier la mission", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            mission.setTitre(txtTitre.getText());
            JComboBox<Statut> cbStatut = new JComboBox<>(Statut.values()); // ComboBox avec tous les statuts possibles
            cbStatut.setSelectedItem(mission.getStatut()); 
            mission.setDateDebut(LocalDate.parse(txtDateDebut.getText())); 
            mission.setDateFin(LocalDate.parse(txtDateFin.getText()));
            mission.setDescription(txtDescription.getText());

            // Mise à jour de l'affichage
            afficherMission(mission);
        }
    }


    private void retourAuxMissions() {
        frame.dispose();
       
    }

}
