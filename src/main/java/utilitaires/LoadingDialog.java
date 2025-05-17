package utilitaires;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class LoadingDialog extends JDialog {

    public LoadingDialog(Window parent) {
        super(parent, "Chargement...", ModalityType.APPLICATION_MODAL);

        // Charge le gif depuis les ressources
        URL imgURL = getClass().getResource("/G4g.gif");
        ImageIcon icon = imgURL != null ? new ImageIcon(imgURL) : null;

        JLabel label = new JLabel();
        if (icon != null) {
            label.setIcon(icon);
        } else {
            label.setText("Chargement...");
        }

        add(label, BorderLayout.CENTER);
        setUndecorated(true); // sans bordure de fenêtre (optionnel)
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int dialogWidth = getWidth();
        int dialogHeight = getHeight();
        int x = (screenSize.width - dialogWidth) / 2;
        int y = 0; // tout en haut
        setLocation(x, y);// centre par rapport à la fenêtre parent
    }
}
