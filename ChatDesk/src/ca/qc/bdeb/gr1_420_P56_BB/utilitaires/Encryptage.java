package ca.qc.bdeb.gr1_420_P56_BB.utilitaires;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;

/**
 * Permet l'encryptage et le dégryptage de string pour l'envoie. Encryptage et décryptage de type serveur ou client.
 */
public class Encryptage {

    /**
     * Le type d'algorithme
     */
    private static final String ALGO = "AES";

    /**
     * Le nombre de valeurs dans la clée
     */
    private static final int NBR_VALEURS_CLEE = 16;

    /**
     * Le temps en milliseconde d'une heure
     */
    private static final int HEURE_MILLI = 3600000;

    /**
     * Caractère ascii de la lettre 'A'
     */
    private static final int ASCII_PREMIERE_LETTRE = 65;

    /**
     * Caractère ascii de la lettre 'Z'
     */
    private static final int ASCII_DERNIERE_LETTRE = 90;

    /**
     * L'ancienne date en milliseconde pour changer la clé à chaque heure
     */
    private static long seedAncienneDateMilli = 0;

    /**
     * L'ancienne clée d'encryptage et de décryptage
     */
    private static Key ancienneClee;

    /**
     * La nouvelle clée d'encryptage et de décryptage
     */
    private static Key nouvelleClee;

    /**
     * Permet d'encrypter un message avec la nouvelle clée
     *
     * @param message        Le message à encrypter
     * @param encryptageType La méthode d'encryptage (serveur vs client)
     * @return Le message encrypter
     */
    public static String encrypter(String message, EncryptageType encryptageType) {
        verificationKeyValue(encryptageType);
        String messageEncrypte = null;
        try {
            Cipher cipher = Cipher.getInstance(ALGO);
            cipher.init(Cipher.ENCRYPT_MODE, nouvelleClee);
            byte[] valeurEncrypte = cipher.doFinal(message.getBytes());
            messageEncrypte = new BASE64Encoder().encode(valeurEncrypte);
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
        verificationKeyValue(encryptageType);
        String messageDecrypte = null;
        try {
            messageDecrypte = decrypterAvecBonneCle(nouvelleClee, messageEncrypter);
        } catch (BadPaddingException bpe) {
            try {
                messageDecrypte = decrypterAvecBonneCle(ancienneClee, messageEncrypter);
            } catch (BadPaddingException e) {
                e.printStackTrace();
            }
        }
        return messageDecrypte;
    }

    /**
     * Permet de décrypter le message qui a été recu avec la clé passé en paramètre
     *
     * @param key              La clé
     * @param messageEncrypter Le message qui est encrypter
     * @return Le string qui est decrypté
     * @throws Exception Si une erreur arrive
     */
    private static String decrypterAvecBonneCle(Key key, String messageEncrypter) throws BadPaddingException {

        Cipher cipher = null;
        String messageDecrypte = null;
        try {
            cipher = Cipher.getInstance(ALGO);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] valeurDecorded = new BASE64Decoder().decodeBuffer(messageEncrypter);
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
        } catch (IOException e) {
            e.printStackTrace();
        }

        return messageDecrypte;
    }

    /**
     * Permet de verifier la valeur de la clé. Après 1 heure, une nouvelle clé est créé et l'ancien clé est concervé
     * pour 1 heure.
     * <p>
     * À changer : pour l'instant, il s'agit toujours de la même clé.
     *
     * @param encryptageType La méthode d'encryptage (serveur vs client)
     */
    private static void verificationKeyValue(EncryptageType encryptageType) {
        byte[] keyValue = new byte[NBR_VALEURS_CLEE];

        long seedNouvelleDateMilli = new Date().getTime();

        if (nouvelleClee == null || seedNouvelleDateMilli - seedAncienneDateMilli >= HEURE_MILLI) {
            Random rdm = new Random(seedNouvelleDateMilli % encryptageType.getValue());
            seedAncienneDateMilli = seedNouvelleDateMilli;
            for (int i = 0; i < NBR_VALEURS_CLEE; i++) {
                keyValue[i] = (byte) (rdm.nextInt(ASCII_DERNIERE_LETTRE - ASCII_PREMIERE_LETTRE) + ASCII_PREMIERE_LETTRE);
            }
            ancienneClee = nouvelleClee;
            nouvelleClee = new SecretKeySpec(new byte[]{'A', 'Y', 'R', 'E', 'D', 'W', 'Q', 'B', 'N', 'L', 'E', 'R', 'Q', 'C', 'V', 'M'},
                    ALGO);
        }
    }
}
