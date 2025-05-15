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

public class ModifierEmployeControleur {

    private final ModificationEmployeVue modifPersonnelVue;
    private final DAOEmploye daoEmploye;
    private final DAOCompetence daoCompetence;

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

        // Double-clic sur table des compétences de l'employé : retirer
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

    public void loadCompetences() {
        modifPersonnelVue.setToutesCompetences(daoCompetence.findAll());
    }

    public void loadCompetencesEmploye() {
        try {
            List<Competence> liste = daoCompetence.findCmpByLoginEmp(modifPersonnelVue.getLoginField().getText());
            modifPersonnelVue.setTableCompetencesEmploye(liste);
        } catch (SQLException e) {
            modifPersonnelVue.afficherMessage("Erreur chargement compétences employé.");
        }
    }
}
