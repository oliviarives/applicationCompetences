package modele;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class MdpUtils {

    /**
     * @param plainPassword, le mot de passe en clair
     * @return le mot de passe crypté
     */
    public static String hashPassword(String plainPassword) {
        return BCrypt.withDefaults().hashToString(12, plainPassword.toCharArray());
    }

    /**
     * Permet de vérifier si le mot de passe est correct 
     * @param plainPassword, le mot de passe en clair
     * @param hashedPassword, le mot de passe crypté
     * @return true si le mot de passe est correct
     */
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.verifyer().verify(plainPassword.toCharArray(), hashedPassword).verified;
    }
}
