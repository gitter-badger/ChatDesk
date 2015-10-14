package ca.qc.bdeb.gr1_420_P56_BB.utilitaires;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
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
        private final String valeur;

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

        private final String valeur;

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

    /**
     * Changer la taille de l'image passé en paramètre
     *
     * @param imageIcon L'image à redimensionner
     * @param width     La longueur voulu
     * @param height    La hauteur voulu
     * @return L'image qui est redimensionner
     */
    public static ImageIcon redimensionnerImage(ImageIcon imageIcon, int width, int height) {
        Image img = imageIcon.getImage();

        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        g.drawImage(img, 0, 0, width, height, null);

        return new ImageIcon(bi);
    }

    /**
     * Permet de récupérer la longueur d'un string qui est affecté par un font
     *
     * @param texte Le string que l'on veut la longueur
     * @param font Le font du texte
     * @return La longueur du string
     */
    public static Dimension calculerDimensionString(String texte, Font font){
        Canvas c = new Canvas();
        FontMetrics fontMetrics = c.getFontMetrics(font);

        return new Dimension(fontMetrics.stringWidth(texte), fontMetrics.getHeight());
    }
}
