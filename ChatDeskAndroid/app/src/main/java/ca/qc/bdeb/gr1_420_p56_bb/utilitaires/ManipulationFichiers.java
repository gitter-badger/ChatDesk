package ca.qc.bdeb.gr1_420_p56_bb.utilitaires;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

/**
 * Permet la manipulation de fichiers.
 */
public class ManipulationFichiers {

    public static String lireFichierDepuisChemin(String packagePath) {
        String contenu = "";

        try {
            BufferedReader lecteurFichier = new BufferedReader(new FileReader(packagePath));
            String ligne;
            ligne = lecteurFichier.readLine();
            while (ligne != null) {
                contenu += ligne + "\n";
                ligne = lecteurFichier.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fichier introuvable : " + packagePath);
        } catch (IOException e) {
            System.out.println("Erreur de lecture");
        }

        return contenu;
    }

    public static Document lireXmlDepuisContenu(String contenu) {
        Document document = null;

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(contenu));
            document = builder.parse(is);
            document.getDocumentElement().normalize();
        } catch (FileNotFoundException e) {
            System.out.println("Fichier introuvable");
        } catch (IOException e) {
            System.out.println("Erreur de lecture");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return document;
    }
}
