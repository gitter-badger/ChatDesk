package ca.qc.bdeb.gr1_420_P56_BB.connexion;

import ca.qc.bdeb.gr1_420_P56_BB.connexion.*;
import ca.qc.bdeb.gr1_420_P56_BB.connexion.CommandesClient;
import ca.qc.bdeb.gr1_420_P56_BB.connexion.EnveloppeContact;
import ca.qc.bdeb.gr1_420_P56_BB.connexion.EnveloppeMessage;
import ca.qc.bdeb.gr1_420_P56_BB.connexion.XMLWriter;
import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.EncryptageType;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Date;

import static ca.qc.bdeb.gr1_420_P56_BB.utilitaires.Encryptage.encrypter;
import static ca.qc.bdeb.gr1_420_P56_BB.utilitaires.ManipulationFichiers.lireFichierDepuisChemin;

/**
 * Created by Alexandre on 2015-09-16.
 */
public class XMLWriterTest extends TestCase {

    private final String PATH_FICHIER_TEST_CONTACTS_MESSAGES = "resources\\fichiersTest\\testCommandeXmlPremiereConnexion.xml";
    private final String PATH_FICHIER_TEST_MESSAGES = "resources\\fichiersTest\\testCommandeXmlMessages.xml";
    private final String PATH_FICHIER_TEST_CONTACTS = "resources\\fichiersTest\\testCommandeXmlContacts.xml";
    private final String PATH_FICHIER_TEST_LOGIN_SERVEUR = "resources\\fichiersTest\\testCommandeXmlServeurLogin.xml";
    private final String PATH_FICHIER_TEST_DEMANDE_APPAREILS = "resources\\fichiersTest\\testCommandeXmlServeurDemandeAppareils.xml";

    private final EnveloppeMessage[] TAB_ENVELOPPES = {new EnveloppeMessage("blahblahblah", 9367457456l, new Date(1367457456))};
    private final EnveloppeContact[] TAB_CONTACTS = {new EnveloppeContact(9367457456l, "jacques")};

    private XMLWriter xmlWriter;

    @Override
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
        assertEquals(contenu, xmlWriter.construireXmlCommunication(CommandesClient.MESSAGES, TAB_ENVELOPPES));
    }

    @Test
    public void testEcrireXmlEnvoiContacts() {
        String contenu = lireFichierDepuisChemin(PATH_FICHIER_TEST_CONTACTS);
        assertEquals(contenu, xmlWriter.construireXmlCommunication(CommandesClient.CONTACTS, TAB_CONTACTS));
    }

    @Test
    public void testConstruireXMLServerLogin(){
        GestionnaireBalisesCommServeur gestionnaireBalisesCommServeurNom
                = new GestionnaireBalisesCommServeur(BalisesCommServeur.BALISE_NOM_UTILISATEUR, "BeautifulUsername");
        GestionnaireBalisesCommServeur gestionnaireBalisesCommServeurPass
                = new GestionnaireBalisesCommServeur(BalisesCommServeur.BALISE_MOT_DE_PASSE, "StrongPassword");

        String comm = xmlWriter.construireXmlServeur(CommandesServeur.REQUETE_LOGIN, gestionnaireBalisesCommServeurNom,
                gestionnaireBalisesCommServeurPass);

        String resultatAtt = lireFichierDepuisChemin(PATH_FICHIER_TEST_LOGIN_SERVEUR);
        assertEquals(resultatAtt, comm);
    }

    @Test
    public void testConstruireXMLServerDemandeAppareils(){
        String comm = xmlWriter.construireXmlServeur(CommandesServeur.REQUETE_LIENS);

        String resultatAtt =  lireFichierDepuisChemin(PATH_FICHIER_TEST_DEMANDE_APPAREILS);

        assertEquals(resultatAtt, comm);
    }
}