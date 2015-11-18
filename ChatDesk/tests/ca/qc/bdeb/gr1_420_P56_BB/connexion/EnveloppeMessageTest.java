package ca.qc.bdeb.gr1_420_P56_BB.connexion;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

/**
 * Created by 1355991 on 2015-10-06.
 */
public class EnveloppeMessageTest extends TestCase {
    private final  String MESSAGE = "Message test";
    private long numTeleophone;
    private EnveloppeMessage enveloppeMessage;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        numTeleophone = 5145435578l;
        enveloppeMessage = new EnveloppeMessage(numTeleophone, MESSAGE,new Date(), true);
    }

    @Test
    public void testConvertirEnXml() throws Exception {
        String expected =
                new XMLWriter().construireXmlCommunication(
                        new EnveloppeMessage[]{enveloppeMessage});
        String actual = enveloppeMessage.convertirEnXml();
        assertEquals(actual,expected);

    }
}