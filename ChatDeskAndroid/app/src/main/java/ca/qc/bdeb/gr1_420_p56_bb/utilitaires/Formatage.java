package ca.qc.bdeb.gr1_420_p56_bb.utilitaires;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Base64;
import android.util.Log;

import com.google.android.gms.wallet.fragment.Dimension;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Alexandre on 2015-09-09.
 */
public class Formatage {

    private static char CARACTERE_DEBUT_NUMERO_TELEPHONE = '1';

    public static long convertirNumeroTelephoneEnLong(String numeroTelephone) {
        numeroTelephone = numeroTelephone.replaceAll("[\\D]", "");
        if (numeroTelephone.getBytes()[0] != CARACTERE_DEBUT_NUMERO_TELEPHONE) {
            numeroTelephone = CARACTERE_DEBUT_NUMERO_TELEPHONE + numeroTelephone;
        }
        return Long.parseLong(numeroTelephone);
    }

    public static String convertirImageEnString(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        return imageEncoded;
    }

    public static Bitmap convertirStringEnImage(String imageString) {
        Bitmap stringEnImage;
        byte[] encodeByte = Base64.decode(imageString, Base64.DEFAULT);
        stringEnImage = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        return stringEnImage;
    }

    public static String hashMotDePasse(String pass, String salt) {
        byte[] hashMotDePasse = new byte[0];
        try {
            MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
            hashMotDePasse = mDigest.digest((salt + pass).getBytes());
        } catch (NoSuchAlgorithmException ex) {
        }

        return new BigInteger(1, hashMotDePasse).toString(16);
    }
}