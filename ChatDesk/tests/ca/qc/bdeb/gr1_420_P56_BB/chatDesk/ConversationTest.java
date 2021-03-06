package ca.qc.bdeb.gr1_420_P56_BB.chatDesk;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by Alexandre on 2015-09-07.
 */
public class ConversationTest {
    private Conversation conversation;

    @Before
    public void setUp() throws Exception {
        conversation = new Conversation();
    }

    @Test
    public void testGetLastMessage() {
        Message messageTest = new Message(1, "Allo", new Date(), false);
        Message messageTest2 = new Message(1, "Bonjour", new Date(), false);
        Message messageTest3 = new Message(1, "Salut", new Date(), false);

        conversation.ajouterMessage(messageTest3);
        conversation.ajouterMessage(messageTest2);
        conversation.ajouterMessage(messageTest);
        assertEquals(messageTest, conversation.getLastMessage());
    }
}
