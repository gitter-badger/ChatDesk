package ca.qc.bdeb.gr1_420_P56_BB.vue;

import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.ConversationDTO;
import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.FacadeModele;
import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.Message;
import ca.qc.bdeb.gr1_420_P56_BB.vue.FrmChatDesk;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by 47 on 2015-10-11.
 */
public class FrmChatDeskTest {
    private ConversationDTO conversationDTOTest;
    private ArrayList<Message> listeMessageTest;
    private static final long NUM_TEST = 5141234567l;
    private static final Message MESSAGE_1 = new Message("A", new Date(), false);
    private static final Message MESSAGE_2 = new Message("B", new Date(), true);
    private static final Message MESSAGE_3 = new Message("C", new Date(), false);
    private static final Message MESSAGE_4 = new Message("D", new Date(), true);
    private FrmChatDesk frmChatDeskTest;
    private FacadeModele facadeModeleTest;
    @Before
    public void setUp() throws Exception {
        facadeModeleTest = new FacadeModele();
        frmChatDeskTest = new FrmChatDesk(facadeModeleTest);
        listeMessageTest = new ArrayList<Message>();
        listeMessageTest.add(MESSAGE_1);
        listeMessageTest.add(MESSAGE_2);
        listeMessageTest.add(MESSAGE_3);
        listeMessageTest.add(MESSAGE_4);
        conversationDTOTest = new ConversationDTO(listeMessageTest, NUM_TEST);

    }

    @Test
    public void testOuvrirConversation() throws Exception {
        frmChatDeskTest.ouvrirConversation(conversationDTOTest);
        assertEquals(conversationDTOTest.getNumeroTelephone(), frmChatDeskTest.getPnlConversation().getNumeroTelephone());
    }
}