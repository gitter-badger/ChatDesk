package ca.qc.bdeb.gr1_420_p56_bb.connexion;


import ca.qc.bdeb.gr1_420_p56_bb.services.ChatDeskService;
import ca.qc.bdeb.gr1_420_p56_bb.utilitaires.EncryptageType;

import static ca.qc.bdeb.gr1_420_p56_bb.utilitaires.Encryptage.decrypter;
import static ca.qc.bdeb.gr1_420_p56_bb.utilitaires.Encryptage.encrypter;


/**
 * Gère la connexion entre l'Android et l'ordinateur
 * <p>
 * Created by Alexandre on 2015-09-02.
 */
public class GestionnaireConnexion {

    private ChatDeskService service;
    private GestionnaireSocket gestionnaireSocket;

    /**
     * Indique si ce programme est un téléphone, dans notre cas évidemment non
     */
    private static final boolean IS_TELEPHONE = false;

    public GestionnaireConnexion(ChatDeskService service) {
        this.service = service;
        this.gestionnaireSocket = new GestionnaireSocket(this);
    }

    /**
     * Gère la réception d'une communication du serveur en l'envoyant à la bonne méthode dépendamment de la commande
     *
     * @param messageRecu
     */
    synchronized void reception(String messageRecu) {
        String messageServeurDecrypter = decrypter(messageRecu, EncryptageType.ENCRYPTAGE_SERVER);
        XMLReaderServeur xmlReaderServeur = new XMLReaderServeur(messageServeurDecrypter);

        switch (xmlReaderServeur.lireCommande()) {
            case REQUETE_NOUVEAU_COMPTE:
                //Pas encore implémenté
                break;
            case REQUETE_LIEN:
                //Pas encore implémenté
                break;
            case REQUETE_MESSAGES:
                GestionnaireBalisesCommServeur[] tabMessages = xmlReaderServeur.lireContenu();
                for (int i = 1; i < tabMessages.length; i++) {
                    lireFichierXmlClient(tabMessages[i].getContenu());
                }
                break;
        }
    }

    /**
     * Envoi une demande au serveur pour qu'il envoie tous les appareils auxquels il est possible de se connecter
     */
    public void demanderAppareils() {
        XMLWriter xmlWriter = new XMLWriter();
        String comm = xmlWriter.construireXmlServeur(CommandesServeur.REQUETE_LIENS);
        this.gestionnaireSocket.envoyerMessage(encrypter(comm, EncryptageType.ENCRYPTAGE_SERVER));
    }

    /**
     * Gère la lecture de la connexion addressée au client
     * @param communication Le xml de la connextion addressée au xlient
     */
    private void lireFichierXmlClient(String communication) {
        String message = decrypter(communication, EncryptageType.ENCRYPTAGE_MESSAGE);
        XMLReader xmlReader = new XMLReader(message);
        switch (xmlReader.lireCommande()) {
            case MESSAGES:
                service.envoyerMessages(xmlReader.lireMessages());
                break;
            case CONTACTS:
                service.ajouterContacts(xmlReader.lireContacts());
                break;
        }
    }

    /**
     * Envoie une enveloppe au serveur
     * @param enveloppe Une classe implémentant l'interface convertissableXml
     */
    public void envoyerEnveloppe(ConvertissableXml enveloppe) {
        String xmlClientMessage = encrypter(enveloppe.convertirEnXml(), EncryptageType.ENCRYPTAGE_MESSAGE);
        String xmlServer = encrypter(new XMLWriter().construireXmlServeur(CommandesServeur.REQUETE_MESSAGES,
                        new GestionnaireBalisesCommServeur(BalisesCommServeur.BALISE_MESSAGE, xmlClientMessage)),
                EncryptageType.ENCRYPTAGE_SERVER);

        gestionnaireSocket.envoyerMessage(xmlServer);

        XMLReader xmlReader = new XMLReader(enveloppe.convertirEnXml());
    }

    /**
     * Envoi une demande de connexion au serveur
     * @param user Le nom d'utilisateur
     * @param pass Le mot de passe
     * @return Une des valeurs de l'énum ResultatsConnexion : Valide, Invalide ou Impossible
     */
    public ResultatsConnexion seConnecter(String user, String pass) {
        XMLWriter xmlWriter = new XMLWriter();

        GestionnaireBalisesCommServeur gestionnaireBalisesCommServeurNom
                = new GestionnaireBalisesCommServeur(BalisesCommServeur.BALISE_NOM_UTILISATEUR, user);
        GestionnaireBalisesCommServeur gestionnaireBalisesCommServeurPass
                = new GestionnaireBalisesCommServeur(BalisesCommServeur.BALISE_MOT_DE_PASSE, pass);
        GestionnaireBalisesCommServeur gestionnaireBalisesCommServeurIsTelephone
                = new GestionnaireBalisesCommServeur(BalisesCommServeur.BALISE_IS_TELEPHONE, Boolean.toString(IS_TELEPHONE));

        String infoConnexionComm = xmlWriter.construireXmlServeur(CommandesServeur.REQUETE_LOGIN, gestionnaireBalisesCommServeurNom,
                gestionnaireBalisesCommServeurPass, gestionnaireBalisesCommServeurIsTelephone);

        return gestionnaireSocket.commencerCommunication(infoConnexionComm);
    }

    /**
     * Arrête le thread de la communication
     */
    public void arreterProgramme() {
        gestionnaireSocket.terminerCommuication();
    }
}
