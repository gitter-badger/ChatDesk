package ca.qc.bdeb.gr1_420_P56_BB.connexion;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static ca.qc.bdeb.gr1_420_P56_BB.utilitaires.ManipulationFichiers.lireFichierDepuisChemin;
import static org.junit.Assert.assertEquals;

/**
 * Created by Alexandre on 2015-09-16.
 */
public class XMLWriterTest {

    final static String PATH_FICHIER_TEST_CONTACTS_MESSAGES = "resources\\fichiersTest\\testCommandeXmlPremiereConnexion.xml";
    final static String PATH_FICHIER_TEST_MESSAGES = "resources\\fichiersTest\\testCommandeXmlMessages.xml";
    final static String PATH_FICHIER_TEST_CONTACTS = "resources\\fichiersTest\\testCommandeXmlContacts.xml";
    final static String PATH_FICHIER_TEST_LOGIN_SERVEUR = "resources\\fichiersTest\\testCommandeXmlServeurLogin.xml";
    final static String PATH_FICHIER_TEST_DEMANDE_APPAREILS = "resources\\fichiersTest\\testCommandeXmlServeurDemandeAppareils.xml";


    private final EnveloppeMessage[] TAB_ENVELOPPES = {new EnveloppeMessage(9367457456l, "blahblahblah", new Date(1367457456), true)};

    private final static long[] tabNumeroTelephones = {9367457456l, 1461447151l};

    public static ArrayList<Long> asList(final long[] tabNumeroTelephones) {
        ArrayList<Long> listeNumeroTelephones = new ArrayList<>();

        for (int i = 0; i < tabNumeroTelephones.length; i++) {
            listeNumeroTelephones.add(tabNumeroTelephones[i]);
        }

        return listeNumeroTelephones;
    }

    private final EnveloppeContact[] TAB_CONTACTS = {new EnveloppeContact(asList(tabNumeroTelephones), "jacques", null)};

    private XMLWriter xmlWriter;

    @Before
    public void setUp() {
        xmlWriter = new XMLWriter();
    }

    @Test
    public void testEcrireXmlPermiereConnexion() {
        String contenu = lireFichierDepuisChemin(PATH_FICHIER_TEST_CONTACTS_MESSAGES);
        assertEquals(contenu, xmlWriter.construireXmlCommunication(CommandesClient.PREMIERE_CONNEXION, TAB_ENVELOPPES, TAB_CONTACTS));
    }

    @Test
    public void testEcrireXmlEnvoiMessages() {
        String contenu = lireFichierDepuisChemin(PATH_FICHIER_TEST_MESSAGES);
        assertEquals(contenu, xmlWriter.construireXmlCommunication(TAB_ENVELOPPES));
    }

    @Test
    public void testEcrireXmlEnvoiContacts() {
        String contenu = lireFichierDepuisChemin(PATH_FICHIER_TEST_CONTACTS);
        assertEquals(contenu, xmlWriter.construireXmlCommunication(TAB_CONTACTS));
    }

    @Test
    public void testConstruireXMLServerLogin() {
        EnveloppeBalisesCommServeur enveloppeBalisesCommServeurNom
                = new EnveloppeBalisesCommServeur(BalisesCommServeur.BALISE_NOM_UTILISATEUR, "BeautifulUsername");
        EnveloppeBalisesCommServeur enveloppeBalisesCommServeurPass
                = new EnveloppeBalisesCommServeur(BalisesCommServeur.BALISE_MOT_DE_PASSE, "StrongPassword");

        String comm = xmlWriter.construireXmlServeur(CommandesServeur.REQUETE_LOGIN, enveloppeBalisesCommServeurNom,
                enveloppeBalisesCommServeurPass);

        String resultatAtt = lireFichierDepuisChemin(PATH_FICHIER_TEST_LOGIN_SERVEUR);
        assertEquals(resultatAtt, comm);
    }

    @Test
    public void testConstruireXMLServerDemandeAppareils() {
        String comm = xmlWriter.construireXmlServeur(CommandesServeur.REQUETE_LIENS);

        String resultatAtt = lireFichierDepuisChemin(PATH_FICHIER_TEST_DEMANDE_APPAREILS);

        assertEquals(resultatAtt, comm);
    }
}