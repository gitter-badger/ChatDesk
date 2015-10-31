package ca.qc.bdeb.gr1_420_p56_bb.utilitaires;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.google.android.gms.wallet.fragment.Dimension;

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

}
