package ca.qc.bdeb.gr1_420_P56_BB.utilitaires;

import org.junit.Test;

import java.security.PublicKey;

import static org.junit.Assert.assertEquals;

/**
 * Created by Alexandre on 2015-10-04.
 */
public class EncryptageTest {
    private static final String PATH_FICHIER_TEST_CONTACTS = "resources\\FichiersTest\\testCommandeXmlPremiereConnexion.xml";

    @Test
    public void testEncryptageDecryptage() {
        Encryptage encryptageClient = Encryptage.getInstanceServeur();
        Encryptage encryptageServeur = new Encryptage();

        PublicKey publicKeyClient = encryptageClient.createKeyToPair();
        PublicKey publicKeyServeur = encryptageServeur.createKeyToPair();

        encryptageClient.createKey(publicKeyServeur);
        encryptageServeur.createKey(publicKeyClient);

        String messageEncrypter = encryptageClient.encrypter(ManipulationFichiers.lireFichierDepuisChemin(PATH_FICHIER_TEST_CONTACTS));
        String messageDecrypter = encryptageServeur.decrypter(messageEncrypter);

        assertEquals(ManipulationFichiers.lireFichierDepuisChemin(PATH_FICHIER_TEST_CONTACTS), messageDecrypter);
    }
}