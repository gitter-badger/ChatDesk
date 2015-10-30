package ca.qc.bdeb.gr1_420_p56_bb.utilitaires;

import android.util.Base64;
import android.util.Log;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyAgreement;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

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
            c = Cipher.getInstance("AES/ECB/PKCS5Padding");
            keyPairGenerator = KeyPairGenerator.getInstance("DH");
            keyPairGenerator.initialize(2048);
        } catch (NoSuchAlgorithmException e) {
        } catch (NoSuchPaddingException e) {
        }
    }

    public String createKeyToPair() {
        Log.i("Encryptage", "1");
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        Log.i("Encryptage", "2");

        privateKey = keyPair.getPrivate();
        return Base64.encodeToString(keyPair.getPublic().getEncoded(), Base64.NO_WRAP);
    }

    public void createKey(final String keyEncode) {
        try {
            PublicKey publicKeyServeur = KeyFactory.getInstance("DH").generatePublic(new X509EncodedKeySpec(Base64.decode(keyEncode, Base64.NO_WRAP)));

            KeyAgreement keyAgreement = KeyAgreement.getInstance("DH");
            keyAgreement.init(privateKey);
            keyAgreement.doPhase(publicKeyServeur, true);
            byte[] secret = keyAgreement.generateSecret();
            MessageDigest sha256 = MessageDigest.getInstance("SHA-512");
            byte[] bytesKey = Arrays.copyOf(sha256.digest(secret), AES_KEY_SIZE / Byte.SIZE);
            key = new SecretKeySpec(bytesKey, "AES");
        } catch (Exception e) {
        }
    }

    public String encrypter(final String messageDecrypte) {
        String messageEncrypte = messageDecrypte;
        if (key != null) {
            try {
                c.init(Cipher.ENCRYPT_MODE, key);
                byte[] valeurEncrypte = c.doFinal(messageDecrypte.getBytes("UTF8"));
                messageEncrypte = Base64.encodeToString(valeurEncrypte, Base64.NO_WRAP);
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
                byte[] valeurDecorded = Base64.decode(messageEncrypte, Base64.NO_WRAP);
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
}


