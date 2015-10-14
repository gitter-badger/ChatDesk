package ca.qc.bdeb.gr1_420_P56_BB.chatDesk;

import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.*;
import ca.qc.bdeb.gr1_420_P56_BB.connexion.EnveloppeMessage;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by 1355991 on 2015-10-06.
 */
public class FacadeModeleTest extends TestCase {
    private FacadeModele facadeModeleTest;
    private final EnveloppeMessage ENVELOPPE_1 = new EnveloppeMessage("A", 1, new Date());
    private final EnveloppeMessage ENVELOPPE_2 = new EnveloppeMessage("B", 1, new Date());
    private final EnveloppeMessage ENVELOPPE_3 = new EnveloppeMessage("C", 2, new Date());
    private final EnveloppeMessage ENVELOPPE_4 = new EnveloppeMessage("D", 2, new Date());

    public void setUp() throws Exception {
        super.setUp();
        facadeModeleTest = new FacadeModele();
    }

    public void tearDown() throws Exception {
        facadeModeleTest = null;
    }

    /**
     * Test la méthode d'ajout message de la facade
     *
     * @throws Exception
     */
    public void testAjouterMessages() throws Exception {
        ArrayList<EnveloppeMessage> listEnveloppes = new ArrayList<>();
        listEnveloppes.add(ENVELOPPE_1);
        listEnveloppes.add(ENVELOPPE_2);
        listEnveloppes.add(ENVELOPPE_3);
        listEnveloppes.add(ENVELOPPE_4);

        facadeModeleTest.ajouterMessages(listEnveloppes);

        ArrayList<EnveloppeMessage> expected = new ArrayList<>();
        expected.add(ENVELOPPE_3);
        expected.add(ENVELOPPE_4);

        ConversationDTO conversationTemp =
                facadeModeleTest.getGestionnaireConversation().getConversationDTO(ENVELOPPE_4.getNumeroTelephone());

        ArrayList<Message> actual = conversationTemp.getMessages();
        assertTrue(VerifierMessages(expected, actual));
    }

    private boolean VerifierMessages(ArrayList<EnveloppeMessage> expected, ArrayList<Message> actual) {
        boolean listeIdentique = true;
        if (actual.size() == expected.size()) {
            for (int i = 0; i < expected.size() && listeIdentique; ++i) {
                listeIdentique = actual.get(i).getText().equals(expected.get(i).getMessage());
            }

        } else {
            listeIdentique = false;
        }
        return listeIdentique;
    }

}