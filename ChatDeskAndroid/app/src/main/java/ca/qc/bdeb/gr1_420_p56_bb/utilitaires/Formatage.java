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

    /**
     * Les jours de la semaine
     */
    public enum JourFormat {
        DIMANCHE("dim."),
        LUNDI("lun."),
        MARDI("mar."),
        MERCREDI("mer."),
        JEUDI("jeu."),
        VENDREDI("ven."),
        SAMEDI("sam.");

        /**
         * La valeur de la journée de la semaine
         */
        private String valeur;

        JourFormat(String valeur) {
            this.valeur = valeur;
        }

        public String toString() {
            return valeur;
        }
    }

    /**
     * Les format d'heure
     */
    public enum HeureFormat {
        MAINTENANT("Maintenant"),
        HEURE(" h"),
        MINUTE(" m");

        private String valeur;

        HeureFormat(String valeur) {
            this.valeur = valeur;
        }

        public String toString() {
            return valeur;
        }
    }

    /**
     * Retourne une date formater selon le temps qui c'est écoulé entre la réception/envoie du message
     * et le temps du système
     *
     * @param dateMsg La date d'un message
     * @return La date formater
     */
    public static String formatageDate(Date dateMsg) {
        Date dateSystem = new Date();

        long valeurDif = dateSystem.getTime() - dateMsg.getTime();
        long valeurSeconde = (long) (valeurDif * 0.001);
        long valeurMinute = valeurSeconde / 60;
        long valeurHeure = valeurMinute / 60;

        Calendar calendrier = Calendar.getInstance();
        calendrier.setTime(dateMsg);
        int dayOfWeek = calendrier.get(Calendar.DAY_OF_WEEK);

        if (valeurHeure < 24) {
            if (valeurHeure == 0 && valeurMinute == 0) {
                return HeureFormat.MAINTENANT.toString();
            } else if (valeurHeure == 0) {
                return Long.toString(valeurMinute) + HeureFormat.MINUTE.toString();
            } else {
                return Long.toString(valeurHeure) + HeureFormat.HEURE.toString();
            }
        } else {
            switch (dayOfWeek) {
                case 1:
                    return JourFormat.DIMANCHE.toString();
                case 2:
                    return JourFormat.LUNDI.toString();
                case 3:
                    return JourFormat.MARDI.toString();
                case 4:
                    return JourFormat.MERCREDI.toString();
                case 5:
                    return JourFormat.JEUDI.toString();
                case 6:
                    return JourFormat.VENDREDI.toString();
                case 7:
                    return JourFormat.SAMEDI.toString();
                default:
                    return "";
            }
        }
    }

    public static long convertirNumeroTelephoneEnLong(String numeroTelephone) {
        numeroTelephone = numeroTelephone.replaceAll("[\\D]", "");
        return Long.parseLong(numeroTelephone);
    }
}
