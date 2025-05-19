package controleur;


import modele.Competence;
import modele.Employe;
import modele.dao.DAOCompetence;
import modele.dao.DAOEmploye;
import vue.ModificationEmployeVue;

import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * Contrôleur chargé de la modification des informations et des compétences d'un employé
 */
public class ModifierEmployeControleur {
    /**
     * Vue de modification d'un employé
     */
    private final ModificationEmployeVue modifPersonnelVue;
    /**
     * DAO pour accéder aux employés
     */
    private final DAOEmploye daoEmploye;
    /**
     * DAO pour accéder aux compétences
     */
    private final DAOCompetence daoCompetence;

    /**
     * Initialise le contrôleur, configure les actions de la vue et prépare les boutons
     *
     * @param modifPersonnelVue vue de modification employé
     * @param daoEmp            DAO employé
     * @param daoCmp            DAO compétence
     */
    public ModifierEmployeControleur(ModificationEmployeVue modifPersonnelVue, DAOEmploye daoEmp, DAOCompetence daoCmp) {
        this.modifPersonnelVue = modifPersonnelVue;
        this.daoEmploye = daoEmp;
        this.daoCompetence = daoCmp;

        modifPersonnelVue.getButtonConfirmer().addActionListener(e -> {
            modifierPersonnel();
            NavigationControleur.loadEmploye();
            NavigationControleur.getVueV().getButtonEmploye().doClick();
        });

        // Double-clic sur table des compétences disponibles : ajouter
        modifPersonnelVue.getTableToutesCompetences().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable source = modifPersonnelVue.getTableToutesCompetences();
                    int row = source.rowAtPoint(e.getPoint());
                    if (row != -1) {
                        DefaultTableModel modelToutes = (DefaultTableModel) source.getModel();
                        DefaultTableModel modelEmp = (DefaultTableModel) modifPersonnelVue.getTableCompetencesEmploye().getModel();
                        Object[] data = {
                                modelToutes.getValueAt(row, 0),
                                modelToutes.getValueAt(row, 1),
                                modelToutes.getValueAt(row, 2)
                        };
                        modelToutes.removeRow(row);
                        modelEmp.addRow(data);
                    }
                }
            }
        });

        //double clic pour modifier
        modifPersonnelVue.getTableCompetencesEmploye().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable source = modifPersonnelVue.getTableCompetencesEmploye();
                    int row = source.rowAtPoint(e.getPoint());
                    if (row != -1) {
                        DefaultTableModel modelEmp = (DefaultTableModel) source.getModel();
                        DefaultTableModel modelToutes = (DefaultTableModel) modifPersonnelVue.getTableToutesCompetences().getModel();
                        Object[] data = {
                                modelEmp.getValueAt(row, 0),
                                modelEmp.getValueAt(row, 1),
                                modelEmp.getValueAt(row, 2)
                        };
                        modelEmp.removeRow(row);
                        modelToutes.addRow(data);
                    }
                }
            }
        });
    }

    /**
     * Modifie les informations de l'employé et met à jour ses compétences
     */
    private void modifierPersonnel() {
        String prenom = modifPersonnelVue.getPrenomField().getText();
        String nom = modifPersonnelVue.getNomField().getText();
        String login = modifPersonnelVue.getLoginField().getText();
        String poste = modifPersonnelVue.getPosteField().getText();
        Date dateEntree = new Date(((java.util.Date) modifPersonnelVue.getDateEntreeField().getValue()).getTime());

        if (prenom.isEmpty() || nom.isEmpty() || login.isEmpty() || poste.isEmpty()) {
            modifPersonnelVue.afficherMessage("Tous les champs sont obligatoires !");
            return;
        }

        try {
            Employe emp = daoEmploye.findEmpByLogin(login);
            emp.setPrenom(prenom);
            emp.setNom(nom);
            emp.setPoste(poste);
            emp.setDateEntree(dateEntree);

            daoEmploye.retirerAllCmpFromEmp(login);
            daoEmploye.modifierEmploye(emp);

            DefaultTableModel modelEmp = (DefaultTableModel) modifPersonnelVue.getTableCompetencesEmploye().getModel();
            for (int i = 0; i < modelEmp.getRowCount(); i++) {
                String cat = (String) modelEmp.getValueAt(i, 0);
                int id = (int) modelEmp.getValueAt(i, 1);
                String titre = (String) modelEmp.getValueAt(i, 2);
                Competence cmp = new Competence(id, cat, "", titre);
                daoEmploye.ajouterCmpToEmp(login, cmp);
                daoEmploye.getHashMapEmpCmp().put(emp, cmp); // inchangé comme demandé
            }

        } catch (SQLException e) {
            modifPersonnelVue.afficherMessage("Erreur lors de la modification de l'employé.");
            e.printStackTrace();
        }
    }

    /**
     * Charge toutes les compétences disponibles et les affiche dans la vue
     */
    public void loadCompetences() {
        modifPersonnelVue.setToutesCompetences(daoCompetence.findAll());
    }

    /**
     * Charge les compétences de l'employé sélectionné et les affiche dans la vue
     */
    public void loadCompetencesEmploye() {
        try {
            List<Competence> liste = daoCompetence.findCmpByLoginEmp(modifPersonnelVue.getLoginField().getText());
            modifPersonnelVue.setTableCompetencesEmploye(liste);
        } catch (SQLException e) {
            modifPersonnelVue.afficherMessage("Erreur chargement compétences employé.");
        }
    }
}
