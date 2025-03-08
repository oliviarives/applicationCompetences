package vue;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBarVue {

    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Menu "Employé"
        JMenu employee = new JMenu("Employé");
        // Sous Menu du menu Employé
        JMenuItem ouvrir = new JMenuItem("Ouvrir");
        JMenuItem exitItem = new JMenuItem("Quitter");

        // Ajouter un écouteur à l'élément de menu "Ouvrir"
        ouvrir.addActionListener(e -> {
            Test testFrame = new Test();
            testFrame.setVisible(true);
        });

        JMenu mission = new JMenu("Mission");
        // Ajouter des éléments pour faire un sous dossier
        JMenuItem coco = new JMenuItem("Cucu");
        JMenuItem cucu = new JMenuItem("Coco");


        // Ajouter le menu Employe et Mission à la barre de menu
        menuBar.add(employee);
        menuBar.add(mission);

        //Ajout des sous menu du menu Employee
        employee.add(ouvrir);
        employee.add(exitItem);
        mission.add(coco);
        mission.addSeparator(); // pour ajouter un séparateur entre les sous-menu
        mission.add(cucu);

        return menuBar;
    }
}
