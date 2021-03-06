package ca.qc.bdeb.gr1_420_P56_BB.utilitaires;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

/**
 * Permet l'encryptage et le dégryptage de string pour l'envoie. Encryptage et décryptage de type serveur ou client.
 */
public class Encryptage {

    private static final int AES_KEY_SIZE = 128;

    private PrivateKey privateKey;
    private SecretKey key = null;
    private Cipher c;

    private static Encryptage instanceServeur = new Encryptage();
    private static Encryptage instanceClient = new Encryptage();

    private KeyPairGenerator keyPairGenerator;

    public static Encryptage getInstance(EncryptageType eT) {
        if (eT == EncryptageType.ENCRYPTAGE_CLIENT) {
            return instanceClient;
        } else if (eT == EncryptageType.ENCRYPTAGE_SERVEUR) {
            return instanceServeur;
        }
        return null;
    }

    private Encryptage() {
        try {
            c = Cipher.getInstance(ALGO);
            //c = Cipher.getInstance("AES/ECB/PKCS5Padding");
            keyPairGenerator = KeyPairGenerator.getInstance("DH");
            keyPairGenerator.initialize(1024);
        } catch (NoSuchAlgorithmException e) {
        } catch (NoSuchPaddingException e) {
        }
    }

/*
    public String createKeyToPair() {
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        privateKey = keyPair.getPrivate();

        return Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
    }

    public void createKey(final String keyEncode) {
        try {
            PublicKey publicKeyServeur = KeyFactory.getInstance("DH").generatePublic(
                    new X509EncodedKeySpec(Base64.getDecoder().decode(keyEncode)));

            KeyAgreement keyAgreement = KeyAgreement.getInstance("DH");
            keyAgreement.init(privateKey);
            keyAgreement.doPhase(publicKeyServeur, true);
            byte[] secret = keyAgreement.generateSecret();
            MessageDigest sha256 = MessageDigest.getInstance("SHA-512");
            byte[] bytesKey = Arrays.copyOf(sha256.digest(secret), AES_KEY_SIZE / Byte.SIZE);
            key = new SecretKeySpec(bytesKey, "AES");
            System.out.println("Cle d'encryption : ");
            for (int i = 0; i < key.getEncoded().length; i++) {
                System.out.print(key.getEncoded()[i]);
            }
            System.out.println();
            System.out.println();
        } catch (Exception e) {
        }
    }

    public String encrypter(final String messageDecrypte) {
        String messageEncrypte = messageDecrypte;
        if (key != null) {
            try {
                c.init(Cipher.ENCRYPT_MODE, key);
                byte[] valeurEncrypte = c.doFinal(messageDecrypte.getBytes("UTF8"));
                messageEncrypte = Base64.getEncoder().encodeToString(valeurEncrypte);
            } catch (IllegalBlockSizeException e) {
            } catch (BadPaddingException e) {
            } catch (InvalidKeyException e) {
            } catch (UnsupportedEncodingException e) {
            }
        }
        return messageEncrypte;
    }

    public String decrypter(final String messageEncrypte) {
        String messageDecrypte = messageEncrypte;
        if (key != null) {
            try {
                c.init(Cipher.DECRYPT_MODE, key);
                byte[] valeurDecorded = Base64.getDecoder().decode(messageEncrypte);
                byte[] ciphertext = c.doFinal(valeurDecorded);
                messageDecrypte = new String(ciphertext, "utf-8");
            } catch (InvalidKeyException e) {
            } catch (BadPaddingException e) {
            } catch (IllegalBlockSizeException e) {
            } catch (UnsupportedEncodingException e) {
            }
        }
        return messageDecrypte;
    }
*/

    private static final String ALGO = "AES";
    private static Key clee = new SecretKeySpec(new byte[]{'A', 'Y', 'R', 'E', 'D', 'W', 'Q', 'B', 'N', 'L', 'E', 'R', 'Q', 'C', 'V', 'M'},
            ALGO);

    public String encrypter(String message) {
        String messageEncrypte = null;
        try {
            c.init(Cipher.ENCRYPT_MODE, clee);
            byte[] valeurEncrypte = c.doFinal(message.getBytes());
            messageEncrypte = Base64.getEncoder().encodeToString(valeurEncrypte);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return messageEncrypte;
    }

    public String decrypter(String messageEncrypter) {
        String messageDecrypte = null;
        try {

            c.init(Cipher.DECRYPT_MODE, clee);
            byte[] valeurDecorded = Base64.getDecoder().decode(messageEncrypter);
            byte[] valeurDecrypte = c.doFinal(valeurDecorded);

            messageDecrypte = new String(valeurDecrypte);
        } catch (BadPaddingException bpe) {
        } catch (IllegalBlockSizeException e) {
        } catch (InvalidKeyException e) {
        }
        return messageDecrypte;
    }

    public String createKeyToPair() {
        return "1";
    }

    public void createKey(final String keyEncode) {
    }
}


