package ca.qc.bdeb.gr1_420_p56_bb.connexion;

import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.Appareil;
import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.FacadeModele;
import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.EncryptageType;

import static ca.qc.bdeb.gr1_420_P56_BB.utilitaires.Encryptage.*;


/**
 * Gère la connexion entre l'Android et l'ordinateur
 * <p>
 * Created by Alexandre on 2015-09-02.
 */
public class GestionnaireConnexion {

    /**
     * Nombre de champs par appareils
     */
    private static final int NOMBRE_CHAMPS_APPAREIL = 2;

    private FacadeModele facadeModele;
    private GestionnaireSocket gestionnaireSocket;

    public GestionnaireConnexion(FacadeModele facadeModele) {
        this.facadeModele = facadeModele;
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
            case REQUETE_LIENS:
                GestionnaireBalisesCommServeur[] tabAppareils = xmlReaderServeur.lireContenu();
                lireAppareils(tabAppareils);
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
     * Gère la lecture des appareils depuis le tableau de contenu
     *
     * @param tabCommAppareils
     */
    private void lireAppareils(GestionnaireBalisesCommServeur[] tabCommAppareils) {
        //Soustrait 1 au length parce qu'une ligne est occupée par la balise de commande
        Appareil[] tabAppareils = new Appareil[(tabCommAppareils.length - 1) / NOMBRE_CHAMPS_APPAREIL];

        int i = 1, j = 0;
        while (i < tabCommAppareils.length) {
            tabAppareils[j] = lireAppareil(tabCommAppareils[i], tabCommAppareils[i + 1]);
            i += NOMBRE_CHAMPS_APPAREIL;
            j++;
        }

        facadeModele.setAppareils(tabAppareils);

        /*
         * Lignes seulement présentes à fin de test, elles font se connecter le programme au premier appareil
         * de la liste s'il y en a un
         */
        if (tabAppareils.length > 0) {
            initierLien(tabAppareils[0].getId());
        }
    }

    /**
     * Lis un appareil depuis plusieurs GestionnaireBalisesCommServeur
     *
     * @param champId Le champ contenant l'id
     * @param champNom Le champ contenant le nom
     * @return Un nouvel appareil doté des champs passés en paramètre
     */
    private Appareil lireAppareil(GestionnaireBalisesCommServeur champId, GestionnaireBalisesCommServeur champNom) {
        int id = Integer.parseInt(champId.getContenu());
        String nom = champNom.getContenu();
        return new Appareil(nom, id);
    }

    /**
     * Envoi au serveur une demande de connexion avec un autre client, appelé un lien
     * @param idAppareil L'id de l'appareil auquel se connecter
     */
    public void initierLien(int idAppareil) {
        XMLWriter xmlWriter = new XMLWriter();
        String comm = xmlWriter.construireXmlServeur(CommandesServeur.REQUETE_LIEN,
                new GestionnaireBalisesCommServeur(BalisesCommServeur.BALISE_ID_APPAREIL, Integer.toString(idAppareil)));
        this.gestionnaireSocket.envoyerMessage(encrypter(comm, EncryptageType.ENCRYPTAGE_SERVER));
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
            case PREMIERE_CONNEXION:
                facadeModele.ajouterContacts(xmlReader.lireContacts());
            case MESSAGES:
                facadeModele.ajouterMessages(xmlReader.lireMessages());
                break;
            case CONTACTS:
                facadeModele.ajouterContacts(xmlReader.lireContacts());
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
     * @param nom Le nom d'utilisateur
     * @param pass Le mot de passe
     * @return Une des valeurs de l'énum ResultatsConnexion : Valide, Invalide ou Impossible
     */
    public ResultatsConnexion seConnecter(String nom, String pass) {
        return gestionnaireSocket.commencerCommunication(nom, pass);
    }

    /**
     * Arrête le thread de la communication
     */
    public void arreterProgramme() {
        gestionnaireSocket.terminerCommuication();
    }
}
