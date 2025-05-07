package vue;

import modele.Competence;
import modele.Employe;
import modele.dao.DAOEmploye;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Map;

public class InformationEmpVue extends JPanel {
    private JButton croixRetour;
    private JTable tableCmp;
    private JTextField prenomField;
    private JTextField nomField;
    private JTextField posteField;
    private JFormattedTextField dateEntreeField;
    private JScrollPane scrollPaneCmp;
    private DAOEmploye daoEmp;

    public InformationEmpVue(DAOEmploye daoEmploye) {
        this.daoEmp = daoEmploye;
        setSize(new Dimension(500, 600));
        setLayout(new BorderLayout());

        // Panel principal pour organiser haut/milieu/bas
        JPanel panelHaut = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel panelMilieu = new JPanel(new GridBagLayout());
        JPanel panelBas = new JPanel(new BorderLayout());

        // Croix de retour
        this.croixRetour = new JButton("❌");
        panelHaut.add(croixRetour);

        // Formulaire
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel lblPrenom = new JLabel("Prénom : ");
        prenomField = new JTextField(15);
        panelMilieu.add(lblPrenom, gbc);

        gbc.gridx = 1;
        panelMilieu.add(prenomField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblNom = new JLabel("Nom : ");
        nomField = new JTextField(15);
        panelMilieu.add(lblNom, gbc);

        gbc.gridx = 1;
        panelMilieu.add(nomField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblPoste = new JLabel("Poste : ");
        posteField = new JTextField(15);
        panelMilieu.add(lblPoste, gbc);

        gbc.gridx = 1;
        panelMilieu.add(posteField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblDateEntree = new JLabel("Date d'entrée : ");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        DateFormatter dateFormatter = new DateFormatter(formatter);
        dateEntreeField = new JFormattedTextField(dateFormatter);
        dateEntreeField.setColumns(15);
        panelMilieu.add(lblDateEntree, gbc);

        gbc.gridx = 1;
        panelMilieu.add(dateEntreeField, gbc);

        // Table des compétences
        tableCmp = new JTable();
        scrollPaneCmp = new JScrollPane(tableCmp);
        scrollPaneCmp.setPreferredSize(new Dimension(450, 200));
        panelBas.add(scrollPaneCmp, BorderLayout.CENTER);

        // Ajout des panels dans le BorderLayout
        add(panelHaut, BorderLayout.NORTH);
        add(panelMilieu, BorderLayout.CENTER);
        add(panelBas, BorderLayout.SOUTH);
    }


    public void setEmpSelectionne(Employe emp){
        this.prenomField.setText(emp.getPrenom());
        this.nomField.setText(emp.getNom());
        this.posteField.setText(emp.getPoste());


        //this.dateEntreeField.setText(emp.getDateEntree().toString());

        //this.dateEntreeField.setValue(emp.getDateEntree());

        String[] columnNames = {"Id", "Categorie","Nom (En)","Nom (FR)"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0){
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        for (Map.Entry<Employe, Competence> entry: daoEmp.getHashMapEmpCmp().entrySet()) {
            Employe empl = entry.getKey();
            if(empl.getLogin().equals(emp.getLogin())) {
                Competence cmp = entry.getValue();
                Object[] row = {cmp.getIdCmp(), cmp.getIdCatCmp(), cmp.getNomCmpEn(), cmp.getNomCmpFr()};
                model.addRow(row);
            }
        }

        this.tableCmp.setModel(model);
    }

    public JButton getCroixRetour() {
        return this.croixRetour;
    }

}

