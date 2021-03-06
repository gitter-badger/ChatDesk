package ca.qc.bdeb.gr1_420_P56_BB.connexion;

import org.junit.Test;

import static ca.qc.bdeb.gr1_420_P56_BB.utilitaires.ManipulationFichiers.lireFichierDepuisChemin;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by Louis-Simon on 10/10/2015.
 */
public class XMLReaderServeurTest {

    private XMLReaderServeur xmlReaderServeur;

    @Test
    public void testLireContenu() throws Exception {
        EnveloppeBalisesCommServeur gestUser = new EnveloppeBalisesCommServeur(BalisesCommServeur.BALISE_NOM_UTILISATEUR, "Alexandre");
        EnveloppeBalisesCommServeur gestPass = new EnveloppeBalisesCommServeur(BalisesCommServeur.BALISE_MOT_DE_PASSE, "1234");

        String PATH_FICHIER_TEST_PREMIERE_CONNEXION = "resources\\fichiersTest\\testCommandeXmlServeurLireContenu.xml";
        xmlReaderServeur = new XMLReaderServeur(lireFichierDepuisChemin(PATH_FICHIER_TEST_PREMIERE_CONNEXION));
        EnveloppeBalisesCommServeur[] tabGest = xmlReaderServeur.lireContenu();

        assertTrue(tabGest.length == 3);
        assertEquals(gestUser, tabGest[1]);
        assertEquals(gestPass, tabGest[2]);
    }

    @Test
    public void testLireCommande() throws Exception {
        CommandesServeur expected = CommandesServeur.REQUETE_COMM_CLIENT;

        String PATH_FICHIER_TEST_COMMANDE_MESSAGES = "resources\\fichiersTest\\testCommandeXmlServeurCommandeMessages.xml";
        xmlReaderServeur = new XMLReaderServeur(lireFichierDepuisChemin(PATH_FICHIER_TEST_COMMANDE_MESSAGES));
        CommandesServeur actual = xmlReaderServeur.lireCommande();

        assertEquals(expected, actual);
    }
}