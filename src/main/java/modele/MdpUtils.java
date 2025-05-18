package modele;

import at.favre.lib.crypto.bcrypt.BCrypt;
/**
 * Utilitaires pour le hachage et la vérification de mots de passe
 * Utilise l'algorithme BCrypt
 */
public class MdpUtils {

    /**
     * Génère un mot de passe haché à partir d'un mot de passe en clair
     * @param plainPassword mot de passe en clair
     * @return mot de passe haché
     */
    public static String hashPassword(String plainPassword) {
        return BCrypt.withDefaults().hashToString(12, plainPassword.toCharArray());
    }
    /**
     * Vérifie si un mot de passe en clair correspond à un mot de passe haché
     * @param plainPassword mot de passe saisi
     * @param hashedPassword mot de passe stocké
     * @return true si la correspondance est vérifiée, false sinon
     */
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.verifyer().verify(plainPassword.toCharArray(), hashedPassword).verified;
    }
}
