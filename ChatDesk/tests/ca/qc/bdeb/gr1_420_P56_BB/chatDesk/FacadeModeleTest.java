package ca.qc.bdeb.gr1_420_P56_BB.chatDesk;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static junit.framework.Assert.assertTrue;

/**
 * Created by 1355991 on 2015-10-06.
 */
public class FacadeModeleTest {
    private FacadeModele facadeModeleTest;
    private final Message ENVELOPPE_1 = new Message(1, "A", new Date(), true);
    private final Message ENVELOPPE_2 = new Message(1, "B", new Date(), true);
    private final Message ENVELOPPE_3 = new Message(2, "C", new Date(), true);
    private final Message ENVELOPPE_4 = new Message(2, "D", new Date(), true);

    @Before
    public void setUp() throws Exception {
        facadeModeleTest = new FacadeModele();
    }

    @After
    public void tearDown() throws Exception {
        facadeModeleTest = null;
    }

    @Test
    public void testAjouterMessages() throws Exception {
        ArrayList<Message> listEnveloppes = new ArrayList<>();
        listEnveloppes.add(ENVELOPPE_1);
        listEnveloppes.add(ENVELOPPE_2);
        listEnveloppes.add(ENVELOPPE_3);
        listEnveloppes.add(ENVELOPPE_4);

        facadeModeleTest.ajouterMessages(listEnveloppes);

        ArrayList<Message> expected = new ArrayList<>();
        expected.add(ENVELOPPE_3);
        expected.add(ENVELOPPE_4);

        ConversationDTO conversationTemp =
                facadeModeleTest.getGestionnaireConversation().getConversationDTO(ENVELOPPE_4.getNumeroTelephone());

        ArrayList<Message> actual = conversationTemp.getMessages();
        assertTrue(verifierMessages(expected, actual));
    }

    private boolean verifierMessages(ArrayList<Message> expected, ArrayList<Message> actual) {
        boolean listeIdentique = true;
        if (actual.size() == expected.size()) {
            for (int i = 0; i < expected.size() && listeIdentique; ++i) {
                listeIdentique = actual.get(i).getText().equals(expected.get(i).getText());
            }

        } else {
            listeIdentique = false;
        }
        return listeIdentique;
    }

}