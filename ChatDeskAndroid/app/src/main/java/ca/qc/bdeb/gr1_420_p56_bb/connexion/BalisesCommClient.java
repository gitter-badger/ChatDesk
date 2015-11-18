package ca.qc.bdeb.gr1_420_p56_bb.connexion;

/**
 * Balises utilisées pour les communications avec le client
 * Created by Alexandre on 2015-09-16.
 */
enum BalisesCommClient implements Balises {

    /**
     * Balise générale toujours présente et qui englobe toute la communication avec le client
     */
    BALISE_COMM("comm"),
    /**
     * Balise commande, précisant quel est le but de la communication
     */
    BALISE_COMMANDE("commande"),
    /**
     * Contient un enveloppe, c'est à dire un message et le numéro de son envoyeur
     */
    BALISE_MESSAGE("message"),
    /**
     * Contient un contact
     */
    BALISE_CONTACTS("contact"),
    /**
     * Contient le nom d'un contact
     */
    BALISE_NOM("nom"),
    /**
     * Contient le numéro de tél d'un contact
     */
    BALISE_NUM_TEL("numeroTelephone"),

    /**
     * L'image du contact
     */
    BALISE_IMAGE_CONTACT("imageContact"),

    /**
     * Contient la date d'envoi d'un message
     */
    BALISE_DATE("date"),
    /**
     * Le contenu d'un message
     */
    BALISE_TEXTE("texte"),
    /**
     * Si c'est un message envoyé ou reçu
     */
    BALISE_EST_ENVOYE("estEnvoye"),

    BALISE_PUBLIC_KEY("public_key");

    private String balise;

    BalisesCommClient(String balise) {
        this.balise = balise;
    }

    @Override
    public String getBalise() {
        return this.balise;
    }
}
