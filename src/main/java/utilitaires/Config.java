package utilitaires;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Logger;

public class Config {
    private static Properties properties = null;

    private Config() {
        throw new IllegalStateException("Classe utilitaire");
    }

    /**
     * Charge le fichier de configuration
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

    public static String get(String key) {
        if (Config.properties == null ){
            Config.load();
        }
        return Config.properties.getProperty(key);
    }
}
