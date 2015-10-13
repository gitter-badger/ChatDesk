package ca.qc.bdeb.gr1_420_P56_BB.utilitaires;

import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.Encryptage;
import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.EncryptageType;
import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.ManipulationFichiers;
import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Alexandre on 2015-10-04.
 */
public class EncryptageTest {
    private static final String PATH_FICHIER_TEST_CONTACTS = "resources\\FichiersTest\\testCommandeXmlPremiereConnexion.xml";

    @Test
    public void testEncryptageDecryptage() {
        Encryptage encryptage = new Encryptage();

        String messageEncrypter = encryptage.encrypter(ManipulationFichiers.lireFichierDepuisChemin(PATH_FICHIER_TEST_CONTACTS), EncryptageType.ENCRYPTAGE_MESSAGE);
        String messageDecrypter = encryptage.decrypter(messageEncrypter, EncryptageType.ENCRYPTAGE_MESSAGE);

        assertEquals(ManipulationFichiers.lireFichierDepuisChemin(PATH_FICHIER_TEST_CONTACTS), messageDecrypter);
    }
}