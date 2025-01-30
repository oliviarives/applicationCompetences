package modele;
import org.mindrot.jbcrypt.BCrypt;

public class MdpUtils {

    /**
     * * @param plainPassword, le mot de passe en clair
     * @return le mot de passe crypté
     */
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    /**
     * Permet de vérifier si le mot de passe est correct 
     * @param plainPassword
     * @param hashedPassword
     * @return true si le mot de passe est correct
     */
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}


