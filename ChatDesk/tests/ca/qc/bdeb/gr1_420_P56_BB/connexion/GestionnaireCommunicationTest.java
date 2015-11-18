package ca.qc.bdeb.gr1_420_P56_BB.connexion;

import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.Appareil;
import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.FacadeModele;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by 47 on 2015-10-13.
 */
public class GestionnaireCommunicationTest {
    FacadeModele facadeModele;
    GestionnaireCommunication gestionnaireCommunicationTest;

    @Before
    public void setUp() throws Exception {
        facadeModele = new FacadeModele();
        gestionnaireCommunicationTest = new GestionnaireCommunication(facadeModele);


    }
    @Test
    public void ajouterAppareilTest(){
        gestionnaireCommunicationTest = new GestionnaireCommunication(facadeModele);
        String xmlAjoutAppareil = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<serveur>\n" +
                "    <requete>liens</requete>\n" +
                "    <id>0</id>\n" +
                "    <nom>Téléphone de Louis</nom>\n" +
                "    <id>1</id>\n" +
                "    <nom>Téléphone de Alexandre</nom>\n" +
                "</serveur>";

        Appareil[] expected = new Appareil[2];
        expected[0] = new Appareil("Téléphone de Louis", 0);
        expected[1] = new Appareil("Téléphone de Alexandre", 1);
        gestionnaireCommunicationTest.reception(xmlAjoutAppareil);
        assertTrue(comparerTableau(expected, gestionnaireCommunicationTest.getFacadeModele().getAppareils()));
    }

    public boolean comparerTableau(Appareil[] appareils, Appareil[] appareils2){
        for (int i = 0; i < appareils.length; ++i){
            if (!appareils[i].getNom().equals(appareils2[i].getNom()) && appareils[i].getId() != appareils2[i].getId()){
                return false;
            }
        }
        return true;
    }
}