package ca.qc.bdeb.gr1_420_p56_bb.utilitaires;

import android.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * Permet l'encryptage et le dégryptage de string pour l'envoie. Encryptage et décryptage de type serveur ou client.
 */
public class Encryptage {

    private static final String ALGO = "AES";

    private static Key key = new SecretKeySpec(new byte[]{'A', 'Y', 'R', 'E', 'D', 'W', 'Q', 'B', 'N', 'L', 'E', 'R', 'Q', 'C', 'V', 'M'},
            ALGO);

    /**
     * Permet d'encrypter un message avec la nouvelle clée
     *
     * @param message        Le message à encrypter
     * @param encryptageType La méthode d'encryptage (serveur vs client)
     * @return Le message encrypter
     */
    public static String encrypter(String message, EncryptageType encryptageType) {
        String messageEncrypte = null;
        try {
            Cipher cipher = Cipher.getInstance(ALGO);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] valeurEncrypte = cipher.doFinal(message.getBytes());
            messageEncrypte = Base64.encodeToString(valeurEncrypte, Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return messageEncrypte;
    }

    /**
     * Décrypter le message passé en paramètre. Si la nouvelle clé ne fonctionne pas, utilisation de l'ancienne clé.
     *
     * @param messageEncrypter Le message à décrypter
     * @param encryptageType   La méthode d'encryptage (serveur vs client)
     * @return Le messsage décrypté
     */
    public static String decrypter(String messageEncrypter, EncryptageType encryptageType) {
        String messageDecrypte = null;
        try {
            Cipher cipher = Cipher.getInstance(ALGO);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] valeurDecorded = Base64.decode(messageEncrypter, Base64.NO_WRAP);
            byte[] valeurDecrypte = cipher.doFinal(valeurDecorded);

            messageDecrypte = new String(valeurDecrypte);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        return messageDecrypte;
    }
}
