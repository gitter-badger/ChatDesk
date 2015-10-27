package ca.qc.bdeb.gr1_420_P56_BB.chatDesk;

import ca.qc.bdeb.gr1_420_P56_BB.connexion.EnveloppeMessage;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by Alexandre on 2015-09-07.
 */
public class GestionnaireConversationTest {

    private final EnveloppeMessage ENVELOPPE_1 = new EnveloppeMessage("A", 1, new Date(), true);
    private final EnveloppeMessage ENVELOPPE_2 = new EnveloppeMessage("B", 1, new Date(), true);
    private final EnveloppeMessage ENVELOPPE_3 = new EnveloppeMessage("C", 2, new Date(), true);
    private final EnveloppeMessage ENVELOPPE_4 = new EnveloppeMessage("D", 2, new Date(), true);
    private ArrayList<EnveloppeMessage> listEnveloppes;
    private GestionnaireConversation gestionnaireConversation;

    @Before
    public void setUp() {
        gestionnaireConversation = new GestionnaireConversation();
        listEnveloppes = new ArrayList<>();
    }

    @Test
    public void testAjouterMessagesNouvelleConversation() {
        listEnveloppes = new ArrayList<>();
        listEnveloppes.add(ENVELOPPE_1);
        listEnveloppes.add(ENVELOPPE_3);

        gestionnaireConversation.ajouterMessages(listEnveloppes);

        assertEquals(2, gestionnaireConversation.getConversationsDTO().size());
        assertEquals(ENVELOPPE_1.getNumeroTelephone(), gestionnaireConversation.getConversationsDTO().get(1).getNumeroTelephone());
        assertEquals(ENVELOPPE_3.getNumeroTelephone(), gestionnaireConversation.getConversationsDTO().get(0).getNumeroTelephone());
    }

    @Test
    public void testAjouterMessagesConversationDejaExistante() {
        listEnveloppes = new ArrayList<>();
        listEnveloppes.add(ENVELOPPE_1);
        listEnveloppes.add(ENVELOPPE_3);
        listEnveloppes.add(ENVELOPPE_2);
        listEnveloppes.add(ENVELOPPE_4);

        gestionnaireConversation.ajouterMessages(listEnveloppes);

        assertEquals(2, gestionnaireConversation.getConversationsDTO().size());
        assertEquals(ENVELOPPE_1.getNumeroTelephone(), gestionnaireConversation.getConversationsDTO().get(1).getNumeroTelephone());
        assertEquals(ENVELOPPE_2.getMessage(), gestionnaireConversation.getConversationsDTO().get(1).getLastMessage().getText());
        assertEquals(ENVELOPPE_3.getNumeroTelephone(), gestionnaireConversation.getConversationsDTO().get(0).getNumeroTelephone());
        assertEquals(ENVELOPPE_4.getMessage(), gestionnaireConversation.getConversationsDTO().get(0).getLastMessage().getText());
    }

    @Test
    public void TestGetConversationDTOParNumero() {
        listEnveloppes.add(ENVELOPPE_1);
        listEnveloppes.add(ENVELOPPE_2);
        listEnveloppes.add(ENVELOPPE_3);
        listEnveloppes.add(ENVELOPPE_4);

        gestionnaireConversation.ajouterMessages(listEnveloppes);

        ArrayList<EnveloppeMessage> expected = new ArrayList<>();
        expected.add(ENVELOPPE_1);
        expected.add(ENVELOPPE_2);

        ConversationDTO conversationDTO = gestionnaireConversation.getConversationDTO(ENVELOPPE_1.getNumeroTelephone());

        ArrayList<Message> actual = conversationDTO.getMessages();
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