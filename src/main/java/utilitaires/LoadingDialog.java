package utilitaires;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
/**
 * Boîte de dialogue pour une animation de chargement
 */
public class LoadingDialog extends JDialog {
    /**
     * Crée une boîte de dialogue de chargement avec un GIF
     *
     * @param parent la fenêtre parente de la boîte de dialogue
     */
    public LoadingDialog(Window parent) {
        super(parent, "Chargement...", ModalityType.APPLICATION_MODAL);

        // Récupération du GIF
        URL imgURL = getClass().getResource("/G4g.gif");
        ImageIcon icon = imgURL != null ? new ImageIcon(imgURL) : null;

        JLabel label = new JLabel();
        if (icon != null) {
            label.setIcon(icon);
        } else {
            label.setText("Chargement...");
        }

        // Ajout du label dans la boîte de dialogue
        add(label, BorderLayout.CENTER);

        // Supprime les bordures et le titre de la fenêtre
        setUndecorated(true);
        pack();

        // Centre horizontalement en haut de l'écran
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int dialogWidth = getWidth();
        int x = (screenSize.width - dialogWidth) / 2;
        int y = 0;
        setLocation(x, y);
    }
}
