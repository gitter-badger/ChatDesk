package ca.qc.bdeb.gr1_420_P56_BB.connexion;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;

import static ca.qc.bdeb.gr1_420_P56_BB.utilitaires.Formatage.convertirStringEnImage;
import static ca.qc.bdeb.gr1_420_P56_BB.utilitaires.ManipulationFichiers.lireXmlDepuisContenu;

/**
 * Lis un document en format XML
 */
class XMLReader {

    /**
     * Message si le numéro de téléphone est invalide
     */
    private static final String MESSAGE_ERREUR_TELEPHONE = "Numéro de téléphone invalide";

    /**
     * Message si le numéro de téléphone ou la date est invalide
     */
    private static final String MESSAGE_ERREUR_TELEPHONE_OU_DATE = "Numéro de téléphone ou date invalide";

    /**
     * Le document xml
     */
    private final Document document;

    public XMLReader(String contenu) {
        document = lireXmlDepuisContenu(contenu);
    }

    /**
     * Lecture de la commande
     *
     * @return La commande client
     */
    public CommandesClient lireCommande() {
        return CommandesClient.getCommandeParString(lireContenuBalise(BalisesCommClient.BALISE_COMMANDE));
    }

    public String lireCle() {
        return lireContenuBalise(BalisesCommClient.BALISE_PUBLIC_KEY);
    }

    private String lireContenuBalise(BalisesCommClient balisesCommClient) {
        String contenu;

        NodeList nList = getNodesParBalise(BalisesCommClient.BALISE_COMM);
        Node node = nList.item(0);
        contenu = getElementParBalise(node, balisesCommClient);

        return contenu;
    }

    /**
     * Lis toutes les balises contacts et créé de nouveaux contacts pour chacune
     *
     * @return La liste des nouveaux contacts
     */
    public ArrayList<EnveloppeContact> lireContacts() {
        ArrayList<EnveloppeContact> listeContacts = new ArrayList();
        EnveloppeContact nouveauContact;
        String nomContact;
        ImageIcon image;

        NodeList nList = getNodesParBalise(BalisesCommClient.BALISE_CONTACTS);

        for (int i = 0; i < nList.getLength(); i++) {
            try {
                Node node = nList.item(i);

                ArrayList<Long> listeNumeroTelephones = new ArrayList<>();
                NodeList nListNumero = getNodeElementParBalise(node, BalisesCommClient.BALISE_LISTE_NUMS_TEL);
                for (int j = 0; j < nListNumero.getLength(); j++) {
                    Node nodeNum = nListNumero.item(j);
                    listeNumeroTelephones.add(Long.parseLong(getElementParBalise(nodeNum, BalisesCommClient.BALISE_NUM_TEL)));
                }

                nomContact = getElementParBalise(node, BalisesCommClient.BALISE_NOM);
                image = convertirStringEnImage(getElementParBalise(node, BalisesCommClient.BALISE_IMAGE_CONTACT));

                nouveauContact = new EnveloppeContact(listeNumeroTelephones, nomContact, image);
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
     *
     * @return La liste des nouveaux messages
     */
    public ArrayList<EnveloppeMessage> lireMessages() {
        ArrayList<EnveloppeMessage> listeEnveloppes = new ArrayList();
        EnveloppeMessage nouvelEnveloppe;
        long numeroTel;
        String message;
        Date dateMessage;
        boolean envoye;

        NodeList nList = getNodesParBalise(BalisesCommClient.BALISE_MESSAGE);
        for (int i = 0; i < nList.getLength(); i++) {
            try {
                Node node = nList.item(i);
                numeroTel = Long.parseLong(getElementParBalise(node, BalisesCommClient.BALISE_NUM_TEL));
                message = getElementParBalise(node, BalisesCommClient.BALISE_TEXTE);
                dateMessage = new Date(Long.parseLong(getElementParBalise(node, BalisesCommClient.BALISE_DATE)));
                envoye = Boolean.parseBoolean(getElementParBalise(node, BalisesCommClient.BALISE_EST_ENVOYE));

                nouvelEnveloppe = new EnveloppeMessage(numeroTel, message, dateMessage, envoye);
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
     *
     * @param balise La balise dont chercher les nodes
     * @return La liste des nodes correspondants à la balise
     */
    private NodeList getNodesParBalise(Balises balise) {
        return document.getElementsByTagName(balise.getBalise());
    }

    private NodeList getNodeElementParBalise(Node node, Balises balise) {
        return ((Element) node).getElementsByTagName(balise.getBalise());
    }

    /**
     * Retourne le contenu d'une balise dans un node par exemple :
     * <node>
     * <element>Jacques</element>
     * </node>
     * retourerait Jacques
     *
     * @param node   Le node dont éxtraire le contenu
     * @param balise La balise
     * @return Le contenu de la balise dans le node
     */
    private String getElementParBalise(Node node, Balises balise) {
        Element eElement = (Element) node;
        return eElement.getElementsByTagName(balise.getBalise()).item(0).getTextContent().trim();
    }
}
