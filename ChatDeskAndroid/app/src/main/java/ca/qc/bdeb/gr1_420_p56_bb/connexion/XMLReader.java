package ca.qc.bdeb.gr1_420_p56_bb.connexion;

import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.Contact;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Date;

import static ca.qc.bdeb.gr1_420_P56_BB.utilitaires.ManipulationFichiers.lireXmlDepuisContenu;

/**
 * Lis un document en format XML
 * Created by Louis-Simon Mc Nicoll on 2015-09-15.
 */
class XMLReader {
    private static final String MESSAGE_ERREUR_TELEPHONE = "Numéro de téléphone invalide";
    private static final String MESSAGE_ERREUR_TELEPHONE_OU_DATE = "Numéro de téléphone ou date invalide";

    private Document document;

    public XMLReader(String contenu) {
        document = lireXmlDepuisContenu(contenu);
    }

    public CommandesClient lireCommande() {
        String commandeTexte;

        NodeList nList = getNodesParBalise(BalisesCommClient.BALISE_COMM);
        Node node = nList.item(0);
        commandeTexte = getElementParBalise(node, BalisesCommClient.BALISE_COMMANDE);

        return CommandesClient.getCommandeParString(commandeTexte);
    }

    /**
     * Lis toutes les balises contacts et créé de nouveaux contacts pour chacune
     * @return La liste des nouveaux contacts
     */
    public ArrayList<Contact> lireContacts() {
        ArrayList<Contact> listeContacts = new ArrayList();
        Contact nouveauContact;
        long numeroTel;
        String nomContact;

        NodeList nList = getNodesParBalise(BalisesCommClient.BALISE_CONTACTS);

        for (int i = 0; i < nList.getLength(); i++) {
            try {
                Node node = nList.item(i);
                numeroTel = Long.parseLong(getElementParBalise(node, BalisesCommClient.BALISE_NUM_TEL));
                nomContact = getElementParBalise(node, BalisesCommClient.BALISE_NOM);
                nouveauContact = new Contact(numeroTel, nomContact);
                listeContacts.add(nouveauContact);
            } catch (NumberFormatException nfe) {
                System.out.println(MESSAGE_ERREUR_TELEPHONE);
                i = nList.getLength();
            }
        }

        return listeContacts;
    }

    /**
     * Lis toutes les balises messages et créé de nouveaux messages pour chacune
     * @return La liste des nouveaux messages
     */
    public ArrayList<EnveloppeMessage> lireMessages() {
        ArrayList<EnveloppeMessage> listeEnveloppes = new ArrayList();
        EnveloppeMessage nouvelEnveloppe;
        long numeroTel;
        String message;
        Date dateMessage;

        NodeList nList = getNodesParBalise(BalisesCommClient.BALISE_ENVELOPPES);

        for (int i = 0; i < nList.getLength(); i++) {
            try {
                Node node = nList.item(i);
                numeroTel = Long.parseLong(getElementParBalise(node, BalisesCommClient.BALISE_NUM_TEL));
                message = getElementParBalise(node, BalisesCommClient.BALISE_MESSAGE);
                dateMessage = new Date(Long.parseLong(getElementParBalise(node, BalisesCommClient.BALISE_DATE)));
                nouvelEnveloppe = new EnveloppeMessage(message, numeroTel, dateMessage);
                listeEnveloppes.add(nouvelEnveloppe);
            } catch (NumberFormatException nfe) {
                System.out.println(MESSAGE_ERREUR_TELEPHONE_OU_DATE);
                i = nList.getLength();
            }
        }

        return listeEnveloppes;
    }

    /**
     * Retourne la liste de nodes (c'est à dire une balise et son contenu) correspondant à une balise
     * @param balise La balise dont chercher les nodes
     * @return La liste des nodes correspondants à la balise
     */
    private NodeList getNodesParBalise(Balises balise) {
        return document.getElementsByTagName(balise.getBalise());
    }

    /**
     * Retourne le contenu d'une balise dans un node par exemple :
     * <node>
     *     <element>Jacques</element>
     * </node>
     * retourerait Jacques
     * @param node Le node dont éxtraire le contenu
     * @param balise La balise
     * @return Le contenu de la balise dans le node
     */
    private String getElementParBalise(Node node, Balises balise) {
        Element eElement = (Element) node;
        return eElement.getElementsByTagName(balise.getBalise()).item(0).getTextContent().trim();
    }
}
