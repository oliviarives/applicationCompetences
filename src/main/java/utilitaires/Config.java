package utilitaires;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Logger;
/**
 * Classe permettant de charger et lire des propriétés depuis un fichier de configuration
 */
public class Config {
    /** Objet Properties qui contient les paires clé/valeur du fichier de configuration */
    private static Properties properties = null;
    /**
     * Constructeur privé pour empêcher l'instanciation de la classe utilitaire
     */
    private Config() {
        throw new IllegalStateException("Classe utilitaire");
    }

    /**
     * Charge le fichier de configuration
     * Si le fichier est introuvable ou vide, une erreur est loggée
     */
    private static void load() {
        Config.properties = new Properties();
        InputStream stream = null;
        try {
            stream = Objects.requireNonNull(Config.class.getResource("/config.properties")).openStream();
            Config.properties.load(stream);
        } catch (IOException e) {
            Logger.getLogger(Config.class.getName()).severe(e.getMessage());
        } finally {
            if (Config.properties.isEmpty()) {
                Logger.getLogger(Config.class.getName()).severe("Le fichier de configuration n'a pas pu être chargé");
            }
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    Logger.getLogger(Config.class.getName()).severe(e.getMessage());
                }
            }
        }
    }
    /**
     * Récupère la valeur associée à une clé dans le fichier de configuration
     * Si les propriétés ne sont pas encore chargées, elles le sont automatiquement
     *
     * @param key la clé dont on souhaite obtenir la valeur
     * @return la valeur associée à la clé, ou null si elle n'existe pas
     */
    public static String get(String key) {
        if (Config.properties == null ){
            Config.load();
        }
        return Config.properties.getProperty(key);
    }
}
