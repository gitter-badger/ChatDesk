package ca.qc.bdeb.gr1_420_P56_BB.utilitaires;

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

    /**
     * Lire le fichier selon un path de fichier
     *
     * @param packagePath Le path du fichier
     * @return Le fichier en string
     */
    public static String lireFichierDepuisChemin(String packagePath) {
        String contenu = "";

        try {
            BufferedReader lecteurFichier = new BufferedReader(new FileReader(packagePath));
            String ligne;
            ligne = lecteurFichier.readLine();
            while (ligne != null) {
                contenu += ligne;
                ligne = lecteurFichier.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fichier introuvable : " + packagePath);
        } catch (IOException e) {
            System.out.println("Erreur de lecture");
        }

        return contenu;
    }

    /**
     * Convertir un string xml en document xml
     *
     * @param contenu Le contenu string xml à convertir
     * @return Un document du string xml
     */
    public static Document lireXmlDepuisContenu(String contenu) {
        Document document = null;
        if (contenu != null && !contenu.isEmpty()) {
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
        }

        return document;
    }

    public static void EcrireFichierAvecChemin(String chemin, String contenu) throws IOException {
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(chemin)));
        out.write(contenu.getBytes());
        out.flush();
        out.close();
    }

    public static String LireFichierAvecChemin(String chemin) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(new File(chemin)));
        String retour = in.readLine();
        while (retour != null ){
            retour = in.readLine();
        }
        in.close();
        return retour;
    }


}
