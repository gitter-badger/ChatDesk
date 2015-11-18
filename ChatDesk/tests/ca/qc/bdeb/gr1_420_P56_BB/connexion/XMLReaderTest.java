package ca.qc.bdeb.gr1_420_P56_BB.connexion;

import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.Contact;
import org.junit.Test;

import java.util.ArrayList;

import static ca.qc.bdeb.gr1_420_P56_BB.utilitaires.ManipulationFichiers.lireFichierDepuisChemin;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Alexandre on 2015-09-07.
 */
public class XMLReaderTest {

    private XMLReader xmlH;
    private final String PATH_FICHIER_TEST_PREMIERE_CONNEXION = "resources\\fichiersTest\\testCommandeXmlPremiereConnexion.xml";
    private final String PATH_FICHIER_TEST_MESSAGES = "resources\\fichiersTest\\testCommandeXmlMessages.xml";

    @Test
    public void testCreerHandler() {
        xmlH = new XMLReader(lireFichierDepuisChemin(PATH_FICHIER_TEST_PREMIERE_CONNEXION));
        assertNotNull(xmlH);
    }

    @Test
    public void testLireCommandePremiereConnexion() {
        xmlH = new XMLReader(lireFichierDepuisChemin(PATH_FICHIER_TEST_PREMIERE_CONNEXION));
        CommandesClient commande = xmlH.lireCommande();
        assertNotNull(commande);
        assertEquals(CommandesClient.PREMIERE_CONNEXION, commande);
    }

    @Test
    public void testLireCommandeMessage() {
        xmlH = new XMLReader(lireFichierDepuisChemin(PATH_FICHIER_TEST_MESSAGES));
        CommandesClient commande = xmlH.lireCommande();
        assertNotNull(commande);
        assertEquals(CommandesClient.MESSAGES, commande);
    }

    @Test
    public void testLireUnContact() {
        String PATH_FICHIER_TEST_CONTACTS = "resources\\fichiersTest\\testCommandeXmlPremiereConnexion.xml";
        xmlH = new XMLReader(lireFichierDepuisChemin(PATH_FICHIER_TEST_CONTACTS));
        ArrayList<EnveloppeContact> listeContacts = xmlH.lireContacts();
        assertNotNull(listeContacts);
        assertEquals("jacques", listeContacts.get(0).getNom());
    }

    @Test
    public void testLireAucunContact() {
        xmlH = new XMLReader(lireFichierDepuisChemin(PATH_FICHIER_TEST_MESSAGES));
        ArrayList<EnveloppeContact> listeContacts = xmlH.lireContacts();
        assertNotNull(listeContacts);
        assertEquals(0, listeContacts.size());
    }

    @Test
    public void testLireUnMessage() {
        xmlH = new XMLReader(lireFichierDepuisChemin(PATH_FICHIER_TEST_MESSAGES));
        ArrayList<EnveloppeMessage> listeEnveloppes = xmlH.lireMessages();
        assertNotNull(listeEnveloppes);
        assertEquals("blahblahblah", listeEnveloppes.get(0).getText());
    }

    @Test
    public void testLireAucunMessage() {
        String PATH_FICHIER_TEST_ACCUSE = "resources\\fichiersTest\\testCommandeXmlAccuse.xml";
        xmlH = new XMLReader(lireFichierDepuisChemin(PATH_FICHIER_TEST_ACCUSE));
        ArrayList<EnveloppeMessage> listeEnveloppes = xmlH.lireMessages();
        assertNotNull(listeEnveloppes);
        assertEquals(0, listeEnveloppes.size());
    }
}