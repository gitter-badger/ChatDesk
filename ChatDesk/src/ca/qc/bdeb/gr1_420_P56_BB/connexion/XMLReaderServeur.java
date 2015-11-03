package ca.qc.bdeb.gr1_420_P56_BB.connexion;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

import static ca.qc.bdeb.gr1_420_P56_BB.utilitaires.ManipulationFichiers.lireXmlDepuisContenu;

/**
 * Lis un document en format XML provenant du serveur
 */
class XMLReaderServeur {

    /**
     * Le document xml
     */
    private final Document document;

    public XMLReaderServeur(String contenu) {
        document = lireXmlDepuisContenu(contenu);
    }

    /**
     * Lis et retourne la commande
     *
     * @return La commande
     */
    CommandesServeur lireCommande() {
        String requeteTexte;

        NodeList nList = getNodesParBalise();
        Node node = nList.item(0);
        requeteTexte = getElementParBalise(node);

        return CommandesServeur.getRequeteParString(requeteTexte);
    }

    /**
     * Lis le contenu et le retourne dans un tableau de EnveloppeBalisesCommServeur
     *
     * @return Un tableau de toutes les balises et leurs contenus
     */
    EnveloppeBalisesCommServeur[] lireContenu() {
        ArrayList<EnveloppeBalisesCommServeur> listeGest = new ArrayList<>();

        NodeList nList = getNodesParBalise();
        nList = nList.item(0).getChildNodes();

        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                String nodeName = node.getNodeName();
                BalisesCommServeur balisesServeur = BalisesCommServeur.getBaliseParString(nodeName);
                String contenu = node.getTextContent();
                EnveloppeBalisesCommServeur enveloppeBalisesCommServeur = new EnveloppeBalisesCommServeur(balisesServeur, contenu);
                listeGest.add(enveloppeBalisesCommServeur);
            }
        }

        return listeGest.toArray(new EnveloppeBalisesCommServeur[listeGest.size()]);
    }

    /**
     * Retourne la liste de nodes (c'est à dire une balise et son contenu) correspondant à une balise
     *
     * @return La liste des nodes correspondants à la balise
     */
    private NodeList getNodesParBalise() {
        return document.getElementsByTagName(BalisesCommServeur.BALISE_SERVEUR.getBalise());
    }

    private String getElementParBalise(Node node) {
        return getElementParBalise(node, BalisesCommServeur.BALISE_REQUETE.getBalise());
    }

    /**
     * Retourne le contenu d'une balise dans un node par exemple :
     * <node>
     * <element>Jacques</element>
     * </node>
     * retourerait Jacques
     *
     * @param node   Le node dont éxtraire le contenu
     * @param balise La balise en string
     * @return Le contenu de la balise dans le node
     */
    private String getElementParBalise(Node node, String balise) {
        Element eElement = (Element) node;
        return eElement.getElementsByTagName(balise).item(0).getTextContent().trim();
    }
}
