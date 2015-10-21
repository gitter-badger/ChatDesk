package ca.qc.bdeb.gr1_420_P56_BB.chatDesk;

import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.*;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by 47 on 2015-10-07.
 */
public class MessageTest {


    @Test
    public void testIsEnvoyer() throws Exception {
        Message messageTest = new Message("Bonjour", new Date(), false);
        assertFalse(messageTest.isEnvoyer());
    }
    @Test
    public void testNonEnvoyer(){
        Message messageTest = new Message("Bonjour", new Date(), true);
        assertTrue(messageTest.isEnvoyer());
    }
}