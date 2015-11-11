package ca.qc.bdeb.gr1_420_p56_bb.utilitaires;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.google.android.gms.wallet.fragment.Dimension;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
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
        String imageEnString;

        int bytes = image.getByteCount();
        ByteBuffer buffer = ByteBuffer.allocate(bytes);
        image.copyPixelsToBuffer(buffer);
        byte[] array = buffer.array();

        StringBuilder toSave = new StringBuilder();
        for (int i = 0; i < array.length - 1; i++) {
            toSave.append(array[i]).append("/");
        }
        toSave.append(array[array.length - 1]);
        imageEnString = toSave.toString();

        return imageEnString;
    }
}