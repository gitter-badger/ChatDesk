package ca.qc.bdeb.gr1_420_p56_bb.connexion;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Créer un nouveau fichier XML et y écris les informations demandés
 * Created by Louis-Simon Mc Nicoll on 2015-09-16.
 */
class XMLWriter {
    private static int VIDE = 0;
    private static int LONGUEUR_MAX_LIGNE_XML = 65;
    private static int TAILLE_INDENTATION = 4;
    private static boolean INDENTE = true;

    private Document doc;

    /**
     * N'envoie qu'une commande
     *
     * @param commande
     * @return Le xml en format String
     */
    public String construireXmlCommunication(Balises commande) {
        return construireXmlCommunication(commande, new EnveloppeMessage[VIDE], new EnveloppeContact[VIDE]);
    }

    /**
     * Envoie une commande et une liste de contacts
     *
     * @param commande
     * @return Le xml en format String
     */
    public String construireXmlCommunication(Balises commande, EnveloppeContact[] tabContacts) {
        return construireXmlCommunication(commande, new EnveloppeMessage[VIDE], tabContacts);
    }

    /**
     * Envoie une commande et une liste de messages
     *
     * @param commande
     * @return Le xml en format String
     */
    public String construireXmlCommunication(Balises commande, EnveloppeMessage[] tabEnveloppes) {
        return construireXmlCommunication(commande, tabEnveloppes, new EnveloppeContact[VIDE]);
    }

    /**
     * Envoie une commande, une liste de contacts et une liste de messages
     *
     * @param commande
     * @return Le xml en format String
     */
    public String construireXmlCommunication(Balises commande, EnveloppeMessage[] tabMessages, EnveloppeContact[] tabContacts) {
        Element rootElement = construireDoc(BalisesCommClient.BALISE_COMM);

        Element elementCommande = creerElement(BalisesCommClient.BALISE_COMMANDE, commande.getBalise());
        rootElement.appendChild(elementCommande);

        construireEnveloppesContacts(rootElement, tabContacts);
        construireEnveloppesMessages(rootElement, tabMessages);

        return convertirDocToString();
    }

    /**
     * Construit la liste des balises contacts
     *
     * @param rootElement Le parent de ces balises
     * @param tabContacts Le tableau de contacts à convertir en XML
     */
    private void construireEnveloppesContacts(Element rootElement, EnveloppeContact[] tabContacts) {
        for (int i = 0; i < tabContacts.length; i++) {
            Element contact = creerElement(BalisesCommClient.BALISE_CONTACTS);
            Element nom = creerElement(BalisesCommClient.BALISE_NOM, tabContacts[i].getNom());
            Element numero = creerElement(BalisesCommClient.BALISE_NUM_TEL, Long.toString(tabContacts[i].getNumeroTelephone()));

            contact.appendChild(nom);
            contact.appendChild(numero);
            rootElement.appendChild(contact);
        }
    }

    /**
     * Construit la liste des balises messages
     *
     * @param rootElement   Le parent de ces balises
     * @param tabEnveloppes Le tableau de messages à convertir en XML
     */
    private void construireEnveloppesMessages(Element rootElement, EnveloppeMessage[] tabEnveloppes) {
        for (int i = 0; i < tabEnveloppes.length; i++) {
            Element enveloppe = creerElement(BalisesCommClient.BALISE_ENVELOPPES);
            Element numero = creerElement(BalisesCommClient.BALISE_NUM_TEL, Long.toString(tabEnveloppes[i].getNumeroTelephone()));
            Element message = creerElement(BalisesCommClient.BALISE_MESSAGE, tabEnveloppes[i].getMessage());
            Element date = creerElement(BalisesCommClient.BALISE_DATE, Long.toString(tabEnveloppes[i].getDate().getTime()));
            Element envoye = creerElement(BalisesCommClient.BALISE_EST_ENVOYE, Boolean.toString(tabEnveloppes[i].isEnvoye()));

            enveloppe.appendChild(numero);
            enveloppe.appendChild(message);
            enveloppe.appendChild(date);
            enveloppe .appendChild(envoye);
            rootElement.appendChild(enveloppe);
        }
    }

    /**
     * Construit un XML à l'addresse du serveur
     *
     * @param commandesServeur
     * @param gestionnairesBalisesServeur tableau de gestionnairesBalisesServeur qui contiennent une balise et son contenu
     * @return Le xml en format String
     */
    public String construireXmlServeur(CommandesServeur commandesServeur, GestionnaireBalisesCommServeur... gestionnairesBalisesServeur) {
        Element rootElement = construireDoc(BalisesCommServeur.BALISE_SERVEUR);

        Element elementCommande = creerElement(BalisesCommServeur.BALISE_REQUETE, commandesServeur.getRequete());
        rootElement.appendChild(elementCommande);

        for (GestionnaireBalisesCommServeur gestionnaireBalisesServeur : gestionnairesBalisesServeur) {
            Element element = creerElement(gestionnaireBalisesServeur.getBalises(), gestionnaireBalisesServeur.getContenu());
            rootElement.appendChild(element);
        }

        return convertirDocToString();
    }

    /**
     * Construit un nouveau document
     *
     * @param rootBalise La balise racine
     * @return Le nouveau document
     */
    private Element construireDoc(Balises rootBalise) {
        Element rootElement = null;

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.newDocument();

            rootElement = doc.createElement(rootBalise.getBalise());
            doc.appendChild(rootElement);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        return rootElement;
    }

    /**
     * Converti le document XML en un string XML
     *
     * @return Le XML en format String
     */
    private String convertirDocToString() {
        String xmlString = "";
        doc.normalizeDocument();

        try {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            xmlString = writer.toString();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException ex) {
            ex.printStackTrace();
        }

        return xmlString;
    }

    private Element creerElement(BalisesCommClient balise) {
        return creerElement(balise, null);
    }

    /**
     * Créer un élément en lui ajoutant un contenu
     *
     * @param balise  La balise de l'élément
     * @param contenu Le contenu
     * @return L'élément
     */
    private Element creerElement(Balises balise, String contenu) {
        Element lastname = doc.createElement(balise.getBalise());

        if (contenu != null) {
            lastname.appendChild(doc.createTextNode(contenu));
        }

        return lastname;
    }
}
