package ca.qc.bdeb.gr1_420_p56_bb.connexion;

import ca.qc.bdeb.gr1_420_p56_bb.services.IService;
import ca.qc.bdeb.gr1_420_p56_bb.utilitaires.Encryptage;
import ca.qc.bdeb.gr1_420_p56_bb.utilitaires.EncryptageType;

/**
 * Gère la connexion entre l'Android et l'ordinateur
 * <p/>
 * Created by Alexandre on 2015-09-02.
 */
public class GestionnaireCommunication {

    private IService service;
    private GestionnaireSocket gestionnaireSocket;

    /**
     * La position de la confirmation du login dans le tableau de contenu
     */
    private static final int POSITION_CONFIRMATION = 1;

    public GestionnaireCommunication(IService service) {
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
            case REQUETE_COMM_CLIENT:
                EnveloppeBalisesCommServeur[] tabMessages = xmlReaderServeur.lireContenu();
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
     * @param commClient Le xml de la connextion addressée au xlient
     */
    private void lireFichierXmlClient(String commClient) {
        XMLReader xmlReader = new XMLReader(Encryptage.getInstance(EncryptageType.ENCRYPTAGE_CLIENT).decrypter(commClient));
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
        gestionnaireSocket.envoyer(CreateurXMLComm.creationXMLEnvoieEnveloppe(enveloppe));
    }

    /**
     * Envoi une demande de connexion au serveur
     *
     * @param nom  Le nom d'utilisateur du compte
     * @param pass Le mot de passe du compte
     * @return Une des valeurs de l'énum ResultatsConnexion : Valide, Invalide ou Impossible
     */
    public ResultatsConnexion seConnecter(String nom, String pass) {
        String comm = CreateurXMLComm.creationXMLConnexionServeur(nom, pass);

        ResultatsConnexion resultatsConnexion = gestionnaireSocket.connexionAuServeur();

        if (resultatsConnexion != ResultatsConnexion.IMPOSSIBLE) {
            String contenu = gestionnaireSocket.seConnecter(comm);
            XMLReaderServeur xmlReaderServeur = new XMLReaderServeur(contenu);
            if (xmlReaderServeur.lireCommande() == CommandesServeur.REQUETE_LOGIN) {
                if (xmlReaderServeur.lireContenu()[POSITION_CONFIRMATION].getContenu().equals(Boolean.toString(true))) {
                    resultatsConnexion = ResultatsConnexion.VALIDE;
                    gestionnaireSocket.commencerEcouteServeur();
                }
            }
        }

        return resultatsConnexion;
    }

    /**
     * Arrête la communication avec le serveur
     */
    public void arreterProgramme() {
        gestionnaireSocket.terminerCommunication();
    }
}
