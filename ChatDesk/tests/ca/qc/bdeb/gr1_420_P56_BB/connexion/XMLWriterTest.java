package ca.qc.bdeb.gr1_420_P56_BB.connexion;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.Date;

import static ca.qc.bdeb.gr1_420_P56_BB.utilitaires.ManipulationFichiers.lireFichierDepuisChemin;

/**
 * Created by Alexandre on 2015-09-16.
 */
public class XMLWriterTest extends TestCase {

    private final EnveloppeMessage[] TAB_ENVELOPPES = {new EnveloppeMessage("blahblahblah", 9367457456l, new Date(1367457456))};
    private final EnveloppeContact[] TAB_CONTACTS = {new EnveloppeContact(9367457456l)};

    private XMLWriter xmlWriter;

    @Override
    public void setUp() {
        xmlWriter = new XMLWriter();
    }

    @Test
    public void testEcrireXmlPermiereConnexion() {
        String PATH_FICHIER_TEST_CONTACTS_MESSAGES = "resources\\fichiersTest\\testCommandeXmlPremiereConnexion.xml";
        String contenu = lireFichierDepuisChemin(PATH_FICHIER_TEST_CONTACTS_MESSAGES);
        assertEquals(contenu, xmlWriter.construireXmlCommunication(CommandesClient.PREMIERE_CONNEXION, TAB_ENVELOPPES, TAB_CONTACTS));
    }

    @Test
    public void testEcrireXmlEnvoiMessages() {
        String PATH_FICHIER_TEST_MESSAGES = "resources\\fichiersTest\\testCommandeXmlMessages.xml";
        String contenu = lireFichierDepuisChemin(PATH_FICHIER_TEST_MESSAGES);
        assertEquals(contenu, xmlWriter.construireXmlCommunication(TAB_ENVELOPPES));
    }

    @Test
    public void testEcrireXmlEnvoiContacts() {
        String PATH_FICHIER_TEST_CONTACTS = "resources\\fichiersTest\\testCommandeXmlContacts.xml";
        String contenu = lireFichierDepuisChemin(PATH_FICHIER_TEST_CONTACTS);
        assertEquals(contenu, xmlWriter.construireXmlCommunication(TAB_CONTACTS));
    }

    @Test
    public void testConstruireXMLServerLogin(){
        EnveloppeBalisesCommServeur enveloppeBalisesCommServeurNom
                = new EnveloppeBalisesCommServeur(BalisesCommServeur.BALISE_NOM_UTILISATEUR, "BeautifulUsername");
        EnveloppeBalisesCommServeur enveloppeBalisesCommServeurPass
                = new EnveloppeBalisesCommServeur(BalisesCommServeur.BALISE_MOT_DE_PASSE, "StrongPassword");

        String comm = xmlWriter.construireXmlServeur(CommandesServeur.REQUETE_LOGIN, enveloppeBalisesCommServeurNom,
                enveloppeBalisesCommServeurPass);

        String PATH_FICHIER_TEST_LOGIN_SERVEUR = "resources\\fichiersTest\\testCommandeXmlServeurLogin.xml";
        String resultatAtt = lireFichierDepuisChemin(PATH_FICHIER_TEST_LOGIN_SERVEUR);
        assertEquals(resultatAtt, comm);
    }

    @Test
    public void testConstruireXMLServerDemandeAppareils(){
        String comm = xmlWriter.construireXmlServeur(CommandesServeur.REQUETE_LIENS);

        String PATH_FICHIER_TEST_DEMANDE_APPAREILS = "resources\\fichiersTest\\testCommandeXmlServeurDemandeAppareils.xml";
        String resultatAtt =  lireFichierDepuisChemin(PATH_FICHIER_TEST_DEMANDE_APPAREILS);

        assertEquals(resultatAtt, comm);
    }
}