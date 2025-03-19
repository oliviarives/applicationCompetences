package utilitaires;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;

public class StyleManager {

    // Définir les nouvelles couleurs
    private static final Color BLEU_SITE = new Color(40,62,80);
    private static final Color BLEU_VERT = new Color(139, 173, 179);     // #768A96
    private static final Color BLEU_CLAIR = new Color(170, 199, 216);     // #AAC7D8
    private static final Color BLEU_TRES_CLAIR = new Color(133, 167, 188); // #DFEBF6
    private static final Color VIOLET = new Color(138, 151, 197);
    private static final Color VIOLET_FONCE = new Color(49, 50, 85);
    private static final Color ROSE_CLAIR = new Color(231, 223, 232);
    private static final Color COLOR_BACKGROUND = new Color(237, 227, 228);


    public static void setupFlatLaf() {
        FlatLightLaf.setup();
        applyGlobalStyles();
    }

    private static void applyGlobalStyles() {
        // Police par défaut
        Font defaultFont = new Font("Arial", Font.PLAIN, 14);

        // Styles pour les JPanel (y compris ceux dans un CardLayout)
        UIManager.put("Panel.background", COLOR_BACKGROUND);


        // Styles pour les boutons
        UIManager.put("Button.background", VIOLET);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.font", defaultFont.deriveFont(Font.BOLD, 14));
        UIManager.put("Button.arc", 25);

        // Styles pour les labels
        UIManager.put("Label.foreground", Color.black);
        UIManager.put("Label.font", defaultFont.deriveFont(Font.BOLD, 16));

        // Styles pour les tables
        UIManager.put("Table.background", Color.WHITE); // Fond des cellules en blanc
        UIManager.put("Table.foreground", BLEU_SITE); // Texte des cellules en noir bleuté
        UIManager.put("Table.font", defaultFont);
        UIManager.put("Table.gridColor", BLEU_VERT); // Couleur des lignes de grille en bleu moyen
        UIManager.put("Table.selectionBackground", BLEU_CLAIR); // Fond de la sélection en bleu clair
        UIManager.put("Table.selectionForeground", Color.WHITE); // Texte de la sélection en blanc


        // Améliorer la bordure des cellules
        UIManager.put("Table.cellFocusColor", ROSE_CLAIR); // Couleur de la bordure de focus
        UIManager.put("Table.showHorizontalLines", true); // Afficher les lignes horizontales
        UIManager.put("Table.showVerticalLines", true); // Afficher les lignes verticales
        UIManager.put("Table.intercellSpacing", new Dimension(1, 1)); // Espacement entre les cellules

        // Styles pour l'en-tête des tableaux (JTableHeader)
        UIManager.put("TableHeader.background", BLEU_TRES_CLAIR); // Fond de l'en-tête en bleu foncé
        UIManager.put("TableHeader.foreground", Color.WHITE); // Texte de l'en-tête en blanc
        UIManager.put("TableHeader.font", defaultFont.deriveFont(Font.BOLD, 14)); // Police en gras
        UIManager.put("TableHeader.cellBorder", BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BLEU_VERT, 1), // Bordure extérieure
                BorderFactory.createEmptyBorder(5, 10, 5, 10) // Espacement intérieur
        ));

        // Styles pour le JSpinner
        UIManager.put("Spinner.background", Color.WHITE); // Fond du Spinner en blanc
        UIManager.put("Spinner.foreground", BLEU_SITE); // Texte du Spinner en noir bleuté
        UIManager.put("Spinner.font", defaultFont); // Police par défaut
        UIManager.put("Spinner.border", BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BLEU_VERT, 1), // Bordure extérieure en bleu moyen
                BorderFactory.createEmptyBorder(5, 10, 5, 10) // Espacement intérieur
        ));
        UIManager.put("Spinner.arc", 10);

        // Styles pour les boutons du Spinner (flèches haut/bas)
        UIManager.put("Spinner.buttonBackground", BLEU_VERT); // Fond des boutons en bleu foncé
        UIManager.put("Spinner.buttonForeground", Color.WHITE); // Texte des boutons en blanc
        UIManager.put("Spinner.buttonBorder", BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Bordure des boutons
        UIManager.put("Spinner.buttonArrowIcon", new Color(255, 255, 255)); // Couleur des flèches en blanc
        UIManager.put("Spinner.buttonHoverBackground", BLEU_CLAIR); // Fond des boutons au survol en bleu clair
    }

    public static void autoResizeTable(JTable table, JScrollPane scrollPane) {
        int rowCount = table.getRowCount();
        int totalHeight = rowCount * table.getRowHeight();
        int headerHeight = table.getTableHeader().getPreferredSize().height;

        // Limite si jamais tu veux éviter que la table grandisse trop
        int maxHeight = 400; // Ajuste cette valeur selon ton besoin

        int finalHeight = Math.min(totalHeight + headerHeight, maxHeight);
        scrollPane.setPreferredSize(new Dimension(scrollPane.getPreferredSize().width, finalHeight));
    }

}