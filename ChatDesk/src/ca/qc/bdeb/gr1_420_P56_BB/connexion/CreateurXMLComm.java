package ca.qc.bdeb.gr1_420_P56_BB.connexion;

import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.Encryptage;
import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.EncryptageType;
import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.Formatage;

/**
 * Classe qui crer les xmls avec les bonnes informations pour l'envoie au serveur
 */
class CreateurXMLComm {

    /**
     * Indique si ce programme est un t�l�phone, dans notre cas �videmment non
     */
    private static final boolean IS_TELEPHONE = false;

    /**
     * Le xmlwriter pour �crire des xml
     */
    private static final XMLWriter XML_WRITER = new XMLWriter();

    /**
     * Cr�ation d'un xml pour la connexion � un compte d'utilisateur
     *
     * @param nom  Le nom d'utilisateur du compte
     * @param pass Le mot de passe du compte
     * @return Le xml qui contient les donn�es de connexion
     */
    static String creationXMLConnexionServeur(String nom, String pass) {
        EnveloppeBalisesCommServeur enveloppeBalisesCommServeurNom
                = new EnveloppeBalisesCommServeur(BalisesCommServeur.BALISE_NOM_UTILISATEUR, nom);
        EnveloppeBalisesCommServeur enveloppeBalisesCommServeurPass
                = new EnveloppeBalisesCommServeur(BalisesCommServeur.BALISE_MOT_DE_PASSE, Formatage.hashMotDePasse(pass, nom));
        EnveloppeBalisesCommServeur enveloppeBalisesCommServeurIsTelephone
                = new EnveloppeBalisesCommServeur(BalisesCommServeur.BALISE_IS_TELEPHONE, Boolean.toString(IS_TELEPHONE));

        return XML_WRITER.construireXmlServeur(CommandesServeur.REQUETE_LOGIN, enveloppeBalisesCommServeurNom,
                enveloppeBalisesCommServeurPass, enveloppeBalisesCommServeurIsTelephone);
    }

    public static String creationXMLSInscrire(String nom, String pass) {

        EnveloppeBalisesCommServeur enveloppeBalisesCommServeurNom
                = new EnveloppeBalisesCommServeur(BalisesCommServeur.BALISE_NOM_UTILISATEUR, nom);
        EnveloppeBalisesCommServeur enveloppeBalisesCommServeurPass
                = new EnveloppeBalisesCommServeur(BalisesCommServeur.BALISE_MOT_DE_PASSE, Formatage.hashMotDePasse(pass, nom));

        return XML_WRITER.construireXmlServeur(CommandesServeur.REQUETE_NOUVEAU_COMPTE, enveloppeBalisesCommServeurNom,
                enveloppeBalisesCommServeurPass);
    }

    static String creationXMLLien(int idAppareil) {
        return XML_WRITER.construireXmlServeur(CommandesServeur.REQUETE_LIEN,
                new EnveloppeBalisesCommServeur(BalisesCommServeur.BALISE_ID_APPAREIL, Integer.toString(idAppareil)));
    }

    static String creationXMLDemandeAppareils() {
        return XML_WRITER.construireXmlServeur(CommandesServeur.REQUETE_LIENS);
    }

    static String creationXMLEnvoieEnveloppe(ConvertissableXml enveloppe) {
        String xmlClientMessage = Encryptage.getInstance(EncryptageType.ENCRYPTAGE_CLIENT).encrypter(enveloppe.convertirEnXml());
        String xmlServer = XML_WRITER.construireXmlServeur(CommandesServeur.REQUETE_COMM_CLIENT,
                new EnveloppeBalisesCommServeur(BalisesCommServeur.PARTIE_CLIENT, xmlClientMessage));

        return xmlServer;
    }

    static String creationXMLEnveloppe(EnveloppeMessage enveloppeMessage) {
        return XML_WRITER.construireXmlCommunication(new EnveloppeMessage[]{enveloppeMessage});
    }

    static String creationXMLEnveloppe(EnveloppeContact enveloppeContact) {
        return XML_WRITER.construireXmlCommunication(new EnveloppeContact[]{enveloppeContact});
    }
}
