package ca.qc.bdeb.gr1_420_p56_bb.connexion;

import ca.qc.bdeb.gr1_420_p56_bb.services.IService;

/**
 * Gère la connexion entre l'Android et l'ordinateur
 * <p/>
 * Created by Alexandre on 2015-09-02.
 */
public class GestionnaireConnexion {

    private IService service;
    private GestionnaireSocket gestionnaireSocket;

    /**
     * Indique si ce programme est un téléphone, dans notre cas évidemment oui
     */
    private static final boolean IS_TELEPHONE = true;

    public GestionnaireConnexion(IService service) {
        this.service = service;
        this.gestionnaireSocket = new GestionnaireSocket(this);
    }

    /**
     * Gère la réception d'une communication du serveur en l'envoyant à la bonne méthode dépendamment de la commande
     *
     * @param messageRecu
     */
    synchronized void reception(String messageRecu) {
        XMLReaderServeur xmlReaderServeur = new XMLReaderServeur(messageRecu);

        switch (xmlReaderServeur.lireCommande()) {
            case REQUETE_NOUVEAU_COMPTE:
                //Pas encore implémenté
                break;
            case REQUETE_LIEN:
                //echangerClePremiereFois();
                envoyerMessagesContacts();
                break;
            case REQUETE_MESSAGES:
                EnveloppeBalisesComm[] tabMessages = xmlReaderServeur.lireContenu();
                for (int i = 1; i < tabMessages.length; i++) {
                    lireFichierXmlClient(tabMessages[i].getContenu());
                }
                break;
            case REQUETE_ECHANGE_CLE:
                break;
        }
    }

    private void envoyerMessagesContacts() {
        EnveloppeInitiale enveloppeInitiale = new EnveloppeInitiale(service.recupererTousMessages(),
                service.recupererTousContacts());
        envoyerEnveloppe(enveloppeInitiale);
    }

    /**
     * Gère la lecture de la connexion addressée au client
     *
     * @param communication Le xml de la connextion addressée au xlient
     */
    private void lireFichierXmlClient(String communication) {
        XMLReader xmlReader = new XMLReader(communication);
        switch (xmlReader.lireCommande()) {
            case MESSAGES:
                for (EnveloppeMessage enveloppeMessage : xmlReader.lireMessages()) {
                    service.envoyerMessageTelephone(enveloppeMessage);
                }
                break;
            case CONTACTS:
                for (EnveloppeContact enveloppeContact : xmlReader.lireContacts()) {
                    service.ajouterContactTelephone(enveloppeContact);
                }
                break;
            case REQUETE_ECHANGE_CLE:
                break;
        }
    }

    /**
     * Envoie une enveloppe au serveur
     *
     * @param enveloppe Une classe implémentant l'interface convertissableXml
     */
    public void envoyerEnveloppe(ConvertissableXml enveloppe) {
        String xmlClientMessage = enveloppe.convertirEnXml();
        String xmlServer = new XMLWriter().construireXmlServeur(CommandesServeur.REQUETE_MESSAGES,
                        new EnveloppeBalisesComm(BalisesCommServeur.BALISE_MESSAGE,
                                xmlClientMessage));

        gestionnaireSocket.envoyerMessage(xmlServer);
    }

    /**
     * Envoi une demande de connexion au serveur
     *
     * @param user Le nom d'utilisateur
     * @param pass Le mot de passe
     * @return Une des valeurs de l'énum ResultatsConnexion : Valide, Invalide ou Impossible
     */
    public ResultatsConnexion seConnecter(String user, String pass) {
        XMLWriter xmlWriter = new XMLWriter();

        EnveloppeBalisesComm enveloppeBalisesCommNom
                = new EnveloppeBalisesComm(BalisesCommServeur.BALISE_NOM_UTILISATEUR, user);
        EnveloppeBalisesComm enveloppeBalisesCommPass
                = new EnveloppeBalisesComm(BalisesCommServeur.BALISE_MOT_DE_PASSE, pass);
        EnveloppeBalisesComm enveloppeBalisesCommIsTelephone
                = new EnveloppeBalisesComm(BalisesCommServeur.BALISE_IS_TELEPHONE, Boolean.toString(IS_TELEPHONE));

        String infoConnexionComm = xmlWriter.construireXmlServeur(CommandesServeur.REQUETE_LOGIN, enveloppeBalisesCommNom,
                enveloppeBalisesCommPass, enveloppeBalisesCommIsTelephone);

        return gestionnaireSocket.commencerCommunication(infoConnexionComm);
    }

    /**
     * Arrête le thread de la communication
     */
    public void arreterProgramme() {
        gestionnaireSocket.terminerCommuication();
    }
}
