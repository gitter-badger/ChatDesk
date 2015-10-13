package ca.qc.bdeb.gr1_420_P56_BB.utilitaires;

import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.Formatage;
import org.junit.Assert;
import org.junit.Test;

import javax.swing.*;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Alexandre on 2015-09-09.
 */
public class FormatageTest {

    @Test
    public void testFormatageDateJour() {

        Calendar calendrier = Calendar.getInstance();
        long WEEK_IN_MILLI_SEC = 604800000l;
        calendrier.setTimeInMillis(new Date().getTime() - WEEK_IN_MILLI_SEC);

        int valeurJourSemaine = calendrier.get(Calendar.DAY_OF_WEEK);
        int valeurJourMois = calendrier.get(Calendar.DAY_OF_MONTH);

        int DAYS_IN_WEEK = 7;
        for (int i = 1; i <= DAYS_IN_WEEK; i++) {

            calendrier.set(Calendar.DAY_OF_MONTH, valeurJourMois);

            Date date = calendrier.getTime();
            String resultat = Formatage.formatageDate(date);

            if (valeurJourSemaine > Calendar.DAY_OF_WEEK) {
                int PREMIER_JOUR = 1;
                valeurJourSemaine = PREMIER_JOUR;
            }
            Assert.assertEquals(Formatage.JourFormat.values()[valeurJourSemaine - 1].toString(), resultat);
            valeurJourSemaine++;
            valeurJourMois++;

        }
    }

    @Test
    public void testFormatageDateHeure() {
        Calendar calendrier = Calendar.getInstance();
        long DEUX_HEURE_MILLI_SEC = 7200000l;
        calendrier.setTimeInMillis(new Date().getTime() - DEUX_HEURE_MILLI_SEC);

        Date date = calendrier.getTime();
        String resultat = Formatage.formatageDate(date);
        Assert.assertEquals(resultat, "2" + Formatage.HeureFormat.HEURE);
    }

    @Test
    public void testFormatageDateMinutes() {
        Calendar calendrier = Calendar.getInstance();
        long CINQ_MIN_MILLI_SEC = 300000l;
        calendrier.setTimeInMillis(new Date().getTime() - CINQ_MIN_MILLI_SEC);

        Date date = calendrier.getTime();
        String resultat = Formatage.formatageDate(date);
        Assert.assertEquals(resultat, "5" + Formatage.HeureFormat.MINUTE);
    }

    @Test
    public void testFormatageDateMaintenant() {
        String resultat = Formatage.formatageDate(new Date());
        Assert.assertEquals(resultat, "Maintenant");
    }

    @Test
    public void testRedimensionnerImage(){
        ImageIcon imageBar = new ImageIcon("ChatDesk/src/ca/qc/bdeb/gr1_420_P56_BB/Images/bar.png");
        int tailleY = 20;
        int tailleX = 20;
        ImageIcon imageBarModifiee = Formatage.redimensionnerImage(imageBar, tailleX, tailleY);

        Assert.assertEquals(imageBarModifiee.getIconWidth(), tailleX);
        Assert.assertEquals(imageBarModifiee.getIconHeight(), tailleY);
    }
}