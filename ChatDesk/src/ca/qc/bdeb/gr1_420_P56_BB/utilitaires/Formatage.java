package ca.qc.bdeb.gr1_420_P56_BB.utilitaires;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;

/**
 * Toutes les méthodes de formatage.
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
        Graphics2D g = bi.createGraphics();
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHints(rh);

        g.drawImage(img, 0, 0, width, height, null);

        return new ImageIcon(bi);
    }

    /**
     * Permet d'arrondir les cotés d'une image. Pour que l'image soit un cercle. Met un contour noir autour de l'image
     *
     * @param image L'image que l'on veut arrondir
     * @return L'image arrondi
     */
    public static ImageIcon arrondirImage(ImageIcon image) {
        BufferedImage bi = new BufferedImage(
                image.getIconWidth(),
                image.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bi.createGraphics();
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHints(rh);

        g.setClip(new Ellipse2D.Float(0, 0, image.getIconWidth(), image.getIconHeight()));
        image.paintIcon(null, g, 0, 0);

        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2));
        g.drawOval(1, 1, image.getIconWidth() - 2, image.getIconHeight() - 2);

        g.dispose();

        return new ImageIcon(bi);
    }

    /**
     * Permet de récupérer la longueur d'un string qui est affecté par un font
     *
     * @param texte Le string que l'on veut la longueur
     * @param font  Le font du texte
     * @return La longueur du string
     */
    public static Dimension calculerDimensionString(String texte, Font font) {
        Canvas c = new Canvas();
        FontMetrics fontMetrics = c.getFontMetrics(font);

        return new Dimension(fontMetrics.stringWidth(texte), fontMetrics.getHeight());
    }

    /**
     * Permet de convertir un string encoder d'une image en image affichable
     *
     * @param imageString Le string de l'image
     * @return L'image du string
     */
    public static ImageIcon convertirStringEnImage(String imageString) {
        ImageIcon stringEnImage = null;
        if (imageString != null && !imageString.isEmpty()) {
            byte[] btDataFile;
            try {
                btDataFile = Base64.decode(imageString);
                stringEnImage = new ImageIcon(btDataFile);
            } catch (Base64DecodingException e) {
                e.printStackTrace();
            }
        }
        return stringEnImage;
    }

    /**
     * Permet de convertir une image en un string encoder en base 64
     *
     * @param image L'image à convertir
     * @return L'image encoder en string
     */
    public static String convertirImageEnString(ImageIcon image) {
        String imageEnString = "";
        if (image != null) {
            try {
                File imgPath = new File(image.getDescription());
                byte[] array = Files.readAllBytes(imgPath.toPath());
                imageEnString = Base64.encode(array);
            } catch (IOException ex) {
            }
        }

        return imageEnString;
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