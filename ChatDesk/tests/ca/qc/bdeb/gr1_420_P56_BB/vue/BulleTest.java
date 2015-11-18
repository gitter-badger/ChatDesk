package ca.qc.bdeb.gr1_420_P56_BB.vue;

import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.Message;
import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.Formatage;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by 47 on 2015-09-09.
 */
public class BulleTest {

    private StringBuilder message;
    private Bulle bulle;
    @Before
    public void setUp() throws Exception {
        message = new StringBuilder("Bonjour, ceci est un message trop long de plus de 20 caractères pour faire des choses");

    }


    @Test
    public void testCalculerDimensionBulle() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        bulle = new Bulle(new Message(1, message.toString(), new Date(),false), (895 * 3) / 2, null, null);
        Dimension expected = Formatage.calculerDimensionString(message.toString(), new Font("Helvetica", Font.PLAIN, 20));
        Method method = bulle.getClass().getDeclaredMethod("calculerDimensionTotalMessage", String.class);
        Field dimensionTemp = bulle.getClass().getDeclaredField("dimTotal");
        dimensionTemp.setAccessible(true);
        method.setAccessible(true);
        method.invoke(bulle, message.toString());
        Dimension actual = (Dimension) dimensionTemp.get(bulle);
        assertEquals(expected, actual);

    }

    @Test
    public void testCalculerDimensionBulleEnvoyer() throws InvocationTargetException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException {
        bulle = new Bulle(new Message(1, message.toString(), new Date(),true), (895 * 3) / 2, null, null);
        Dimension expected = Formatage.calculerDimensionString(message.toString(), new Font("Helvetica", Font.PLAIN,
                20));
        expected.height = (int) (Math.ceil(expected.getWidth() / (double) (895 * 3) / 2) * expected.height);
        Method method = bulle.getClass().getDeclaredMethod("calculerDimensionTotalMessage", String.class);
        Field dimensionTemp = bulle.getClass().getDeclaredField("dimTotal");
        dimensionTemp.setAccessible(true);
        method.setAccessible(true);
        method.invoke(bulle, message.toString());
        Dimension actual = (Dimension) dimensionTemp.get(bulle);
        assertEquals(expected, actual);
    }



}