package ca.qc.bdeb.gr1_420_P56_BB.connexion;

import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.Formatage;
import org.w3c.dom.Document;

import java.util.ArrayList;

/**
 * Créer un nouveau fichier XML et y écris les informations demandés
 */
class XMLWriter {

    /**
     * Valeur si on doit indenter.
     */
    private static boolean INDENTE = true;

    /**
     * Taille de l'indentation
     */
    private static int TAILLE_INDENTATION = 4;

    /**
     * Longueur de caratère maximum par ligne
     */
    private static int LONGUEUR_MAX_LIGNE_XML = 65;

    /**
     * Valeur vide
     */
    private static final int VIDE = 0;

    /**
     * Le document xml
     */
    private Document doc;

    private final static String DEBUT_BALISE = "<";
    private final static String DEBUT_FERMETURE_BALISE = "</";
    private final static String FIN_BALISE = ">";

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
     * @return Le xml en format String
     */
    public String construireXmlCommunication(EnveloppeContact[] tabContacts) {
        return construireXmlCommunication(CommandesClient.CONTACTS, new EnveloppeMessage[VIDE], tabContacts);
    }

    /**
     * Envoie une commande et une liste de messages
     *
     * @return Le xml en format String
     */
    public String construireXmlCommunication(EnveloppeMessage[] tabMessages) {
        return construireXmlCommunication(CommandesClient.MESSAGES, tabMessages, new EnveloppeContact[VIDE]);
    }

    /**
     * Envoie une commande, une liste de contacts et une liste de messages
     *
     * @param commande
     * @return Le xml en format String
     */
    public String construireXmlCommunication(Balises commande, EnveloppeMessage[] tabMessages, EnveloppeContact[] tabContacts) {
        StringBuilder informationCommBuilder = new StringBuilder();

        informationCommBuilder.append(mettreInformationBalise(BalisesCommClient.BALISE_COMMANDE, commande.getBalise()));
        construireEnveloppesContacts(informationCommBuilder, tabContacts);
        construireEnveloppesMessages(informationCommBuilder, tabMessages);

        return mettreInformationBalise(BalisesCommClient.BALISE_COMM, informationCommBuilder.toString());
    }

    private String mettreContourBaliseDebut(Balises commande) {
        StringBuilder baliseDebutBuilder = new StringBuilder();

        baliseDebutBuilder.append(DEBUT_BALISE);
        baliseDebutBuilder.append(commande.getBalise());
        baliseDebutBuilder.append(FIN_BALISE);

        return baliseDebutBuilder.toString();
    }

    private String mettreContourBaliseFin(Balises commande) {
        StringBuilder baliseFinBuilder = new StringBuilder();

        baliseFinBuilder.append(DEBUT_FERMETURE_BALISE);
        baliseFinBuilder.append(commande.getBalise());
        baliseFinBuilder.append(FIN_BALISE);

        return baliseFinBuilder.toString();
    }

    private String mettreInformationBalise(Balises balises, String information) {
        StringBuilder baliseInfoBuilder = new StringBuilder();

        baliseInfoBuilder.append(mettreContourBaliseDebut(balises));
        baliseInfoBuilder.append(information);
        baliseInfoBuilder.append(mettreContourBaliseFin(balises));

        return baliseInfoBuilder.toString();
    }

    /**
     * Construit la liste des balises contacts
     *
     * @param informationCommBuilder Le parent de ces balises
     * @param tabContacts            Le tableau de contacts à convertir en XML
     */
    private void construireEnveloppesContacts(StringBuilder informationCommBuilder, EnveloppeContact[] tabContacts) {
        for (int i = 0; i < tabContacts.length; i++) {
            StringBuilder informationContactBuilder = new StringBuilder();

            informationContactBuilder.append(mettreInformationBalise(BalisesCommClient.BALISE_NOM, tabContacts[i].getNom()));

            StringBuilder informationNumerosTelephone = new StringBuilder();
            ArrayList<Long> listeNumerosTelephone = tabContacts[i].getListeNumeroTelephones();
            for (long numeroTelephone : listeNumerosTelephone) {
                informationNumerosTelephone.append(mettreInformationBalise(BalisesCommClient.BALISE_NUM_TEL, Long.toString(numeroTelephone)));
            }

            informationContactBuilder.append(mettreInformationBalise(BalisesCommClient.BALISE_LISTE_NUMS_TEL, informationNumerosTelephone.toString()));
            informationContactBuilder.append(mettreInformationBalise(BalisesCommClient.BALISE_IMAGE_CONTACT, Formatage.convertirImageEnString(tabContacts[i].getImage())));

            informationCommBuilder.append(mettreInformationBalise(BalisesCommClient.BALISE_CONTACTS, informationContactBuilder.toString()));
        }
    }

    /**
     * Construit la liste des balises messages
     *
     * @param informationCommBuilder Le parent de ces balises
     * @param tabEnveloppes          Le tableau de messages à convertir en XML
     */
    private void construireEnveloppesMessages(StringBuilder informationCommBuilder, EnveloppeMessage[] tabEnveloppes) {
        for (int i = 0; i < tabEnveloppes.length; i++) {
            StringBuilder informationContactBuilder = new StringBuilder();

            informationContactBuilder.append(mettreInformationBalise(BalisesCommClient.BALISE_NUM_TEL, Long.toString(tabEnveloppes[i].getNumeroTelephone())));
            informationContactBuilder.append(mettreInformationBalise(BalisesCommClient.BALISE_TEXTE, tabEnveloppes[i].getText()));
            informationContactBuilder.append(mettreInformationBalise(BalisesCommClient.BALISE_DATE, Long.toString(tabEnveloppes[i].getDate().getTime())));
            informationContactBuilder.append(mettreInformationBalise(BalisesCommClient.BALISE_EST_ENVOYE, Boolean.toString(tabEnveloppes[i].isEnvoye())));

            informationCommBuilder.append(mettreInformationBalise(BalisesCommClient.BALISE_MESSAGE, informationContactBuilder.toString()));
        }
    }

    /**
     * Construit un XML pour l'échange de la clé public
     *
     * @param cle La clé public
     * @return Le xml en format String
     */
    public String construireCleClient(String cle) {
        StringBuilder informationCleClientBuilder = new StringBuilder();

        informationCleClientBuilder.append(mettreInformationBalise(BalisesCommClient.BALISE_COMMANDE, CommandesClient.REQUETE_ECHANGE_CLE.getBalise()));
        informationCleClientBuilder.append(mettreInformationBalise(BalisesCommClient.BALISE_PUBLIC_KEY, cle));

        return mettreInformationBalise(BalisesCommClient.BALISE_COMM, informationCleClientBuilder.toString());
    }

    /**
     * Construit un XML à l'addresse du serveur
     *
     * @param commandesServeur
     * @param gestionnairesBalisesServeur tableau de gestionnairesBalisesServeur qui contiennent une balise et son contenu
     * @return Le xml en format String
     */
    public String construireXmlServeur(CommandesServeur commandesServeur, EnveloppeBalisesCommServeur... gestionnairesBalisesServeur) {
        StringBuilder informationServeurBuilder = new StringBuilder();

        informationServeurBuilder.append(mettreInformationBalise(BalisesCommServeur.BALISE_REQUETE, commandesServeur.getRequete()));

        for (EnveloppeBalisesCommServeur gestionnaireBalisesServeur : gestionnairesBalisesServeur) {
            informationServeurBuilder.append(mettreInformationBalise(gestionnaireBalisesServeur.getBalises(), gestionnaireBalisesServeur.getContenu()));
        }

        return mettreInformationBalise(BalisesCommServeur.BALISE_SERVEUR, informationServeurBuilder.toString());
    }
}
