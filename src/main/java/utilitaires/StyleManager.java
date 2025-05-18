package utilitaires;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
/**
 * Gère les styles de l'interface graphique
 */
public class StyleManager {

    // Définition des couleurs
    public static final Color BLEU_SITE = new Color(40,62,80);
    public static final Color BLEU_VERT = new Color(139, 173, 179);
    public static final Color BLEU_CLAIR = new Color(170, 199, 216);
    public static final Color VERT = new Color(5, 128, 7);
    public static final Color BLANC = new Color(255, 255, 255);
    public static final Color GRIS = new Color(189, 189, 189);

    /**
     * Initialise le thème FlatLaf et applique les styles
     */
    public static void setupFlatLaf() {
        FlatLightLaf.setup();
        applyGlobalStyles();
    }
    /**
     * Applique les styles à tous les composants
     */
    private static void applyGlobalStyles() {
        Font defaultFont = new Font("Arial", Font.PLAIN, 14);

        // Fond des panneaux
        UIManager.put("Panel.background", BLANC);

        // Boutons
        UIManager.put("Button.background", BLANC);
        UIManager.put("Button.foreground", BLEU_SITE);
        UIManager.put("Button.font", defaultFont.deriveFont(Font.BOLD, 14));
        UIManager.put("Button.arc", 25);

        // Labels
        UIManager.put("Label.foreground", Color.black);
        UIManager.put("Label.font", defaultFont.deriveFont(Font.BOLD, 16));

        // Tables
        UIManager.put("Table.background", BLANC);
        UIManager.put("Table.foreground", BLEU_SITE);
        UIManager.put("Table.font", defaultFont);
        UIManager.put("Table.gridColor", BLEU_SITE);
        UIManager.put("Table.selectionBackground", GRIS);
        UIManager.put("Table.selectionForeground", BLANC);
        UIManager.put("Table.cellFocusColor", VERT);
        UIManager.put("Table.showHorizontalLines", true);
        UIManager.put("Table.showVerticalLines", true);
        UIManager.put("Table.intercellSpacing", new Dimension(1, 1));

        // En-tête des tableaux
        UIManager.put("TableHeader.background", BLEU_VERT);
        UIManager.put("TableHeader.foreground", BLANC);
        UIManager.put("TableHeader.font", defaultFont.deriveFont(Font.BOLD, 14));
        UIManager.put("TableHeader.cellBorder", BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BLEU_VERT, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // Spinners
        UIManager.put("Spinner.background", BLANC);
        UIManager.put("Spinner.foreground", BLEU_SITE);
        UIManager.put("Spinner.font", defaultFont);
        UIManager.put("Spinner.border", BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BLEU_SITE, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        UIManager.put("Spinner.arc", 10);

        // Boutons des spinners
        UIManager.put("Spinner.buttonBackground", BLEU_VERT);
        UIManager.put("Spinner.buttonForeground", BLANC);
        UIManager.put("Spinner.buttonBorder", BorderFactory.createEmptyBorder(5, 10, 5, 10));
        UIManager.put("Spinner.buttonArrowIcon", BLANC);
        UIManager.put("Spinner.buttonHoverBackground", BLEU_CLAIR);
    }
    /**
     * Adapte automatiquement la hauteur d'une JTable à son contenu
     * @param table JTable à ajuster
     * @param scrollPane JScrollPane qui contient la table
     */
    public static void autoResizeTable(JTable table, JScrollPane scrollPane) {
        int rowCount = table.getRowCount();
        int totalHeight = rowCount * table.getRowHeight();
        int headerHeight = table.getTableHeader().getPreferredSize().height;

        int maxHeight = 400;

        int finalHeight = Math.min(totalHeight + headerHeight, maxHeight);
        scrollPane.setPreferredSize(new Dimension(scrollPane.getPreferredSize().width, finalHeight));
    }

}