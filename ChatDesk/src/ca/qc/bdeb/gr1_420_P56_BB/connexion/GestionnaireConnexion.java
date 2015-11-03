package ca.qc.bdeb.gr1_420_P56_BB.connexion;

import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.Appareil;
import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.FacadeModele;
import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.Encryptage;
import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.EncryptageType;
import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.ObservateurErreur;

/**
 * Gère la connexion entre l'Android et l'ordinateur
 */
public class GestionnaireConnexion {

    /**
     * Nombre de champs par appareils
     */
    private static final int NOMBRE_CHAMPS_APPAREIL = 2;

    /**
     * Indique si ce programme est un téléphone, dans notre cas évidemment non
     */
    private static final boolean IS_TELEPHONE = false;

    /**
     * La facade du modele pour accèder au modèle
     */
    private final FacadeModele facadeModele;

    /**
     * Le gestionnaire de socket.
     */
    private final GestionnaireSocket gestionnaireSocket;

    public GestionnaireConnexion(FacadeModele facadeModele) {
        this.facadeModele = facadeModele;
        this.gestionnaireSocket = new GestionnaireSocket(this);
    }

    /**
     * Gère la réception d'une communication du serveur en l'envoyant à la bonne méthode dépendamment de la commande
     *
     * @param messageRecu le message reçu du serveur en version xml encrypter selon la méthode du serveur
     */
    synchronized void reception(String messageRecu) {
        String messageServeurDecrypte = Encryptage.getInstance(EncryptageType.ENCRYPTAGE_SERVEUR).decrypter(messageRecu);
        if (messageServeurDecrypte != null && !messageServeurDecrypte.isEmpty()) {
            XMLReaderServeur xmlReaderServeur = new XMLReaderServeur(messageServeurDecrypte);
            switch (xmlReaderServeur.lireCommande()) {
                case REQUETE_NOUVEAU_COMPTE:
                    //Pas encore implémenté
                    break;
                case REQUETE_LIEN:
                    echangerClePremiereFois();
                    break;
                case REQUETE_LIENS:
                    EnveloppeBalisesCommServeur[] tabAppareils = xmlReaderServeur.lireContenu();
                    lireAppareils(tabAppareils);
                    break;
                case REQUETE_MESSAGES:
                    EnveloppeBalisesCommServeur[] tabMessages = xmlReaderServeur.lireContenu();
                    for (int i = 1; i < tabMessages.length; i++) {
                        lireFichierXmlClient(tabMessages[i].getContenu());
                    }
                    break;
                case REQUETE_ECHANGE_CLE:
                    changementCleServeur(xmlReaderServeur.lireContenu()[1].getContenu());
                    break;
            }
        } else {
            gestionnaireSocket.terminerCommuication();
        }
    }

    private void echangerClePremiereFois() {
        gestionnaireSocket.creationCleClient();
    }

    /**
     * Gère la lecture des appareils depuis le tableau de contenu
     *
     * @param tabCommAppareils
     */
    private void lireAppareils(EnveloppeBalisesCommServeur[] tabCommAppareils) {
        //Soustrait 1 au length parce qu'une ligne est occupée par la balise de commande
        Appareil[] tabAppareils = new Appareil[(tabCommAppareils.length - 1) / NOMBRE_CHAMPS_APPAREIL];


        for (int indiceDonnees = 1, indiceTableauAppareils = 0; indiceDonnees < tabCommAppareils.length;
             indiceDonnees += NOMBRE_CHAMPS_APPAREIL, indiceTableauAppareils++) {
            tabAppareils[indiceTableauAppareils] = lireAppareil(tabCommAppareils[indiceDonnees],
                    tabCommAppareils[indiceDonnees + 1]);
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
     * Lis un appareil depuis plusieurs EnveloppeBalisesCommServeur
     *
     * @param champId  Le champ contenant l'id
     * @param champNom Le champ contenant le nom
     * @return Un nouvel appareil doté des champs passés en paramètre
     */
    private Appareil lireAppareil(EnveloppeBalisesCommServeur champId, EnveloppeBalisesCommServeur champNom) {
        int id = Integer.parseInt(champId.getContenu());
        String nom = champNom.getContenu();
        return new Appareil(nom, id);
    }

    /**
     * Envoi au serveur une demande de connexion avec un autre client, appelé un lien
     *
     * @param idAppareil L'id de l'appareil auquel se connecter
     */
    public void initierLien(int idAppareil) {
        XMLWriter xmlWriter = new XMLWriter();
        String comm = xmlWriter.construireXmlServeur(CommandesServeur.REQUETE_LIEN,
                new EnveloppeBalisesCommServeur(BalisesCommServeur.BALISE_ID_APPAREIL, Integer.toString(idAppareil)));
        this.gestionnaireSocket.envoyerMessage(comm, EncryptageType.ENCRYPTAGE_SERVEUR);
    }

    /**
     * Envoi une demande au serveur pour qu'il envoie tous les appareils auxquels il est possible de se connecter
     */
    public void demanderAppareils() {
        XMLWriter xmlWriter = new XMLWriter();
        String comm = xmlWriter.construireXmlServeur(CommandesServeur.REQUETE_LIENS);
        this.gestionnaireSocket.envoyerMessage(comm, EncryptageType.ENCRYPTAGE_SERVEUR);
    }

    /**
     * Gère la lecture de la connexion addressée au client
     *
     * @param communication Le xml de la connextion addressée au xlient
     */
    private void lireFichierXmlClient(String communication) {
        String message = Encryptage.getInstance(EncryptageType.ENCRYPTAGE_CLIENT).decrypter(communication);
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
            case REQUETE_ECHANGE_CLE:
                changementCleClient(xmlReader.lireCle());
                break;
        }
    }

    private void changementCleServeur(String publicKeyServeur) {
        Encryptage.getInstance(EncryptageType.ENCRYPTAGE_SERVEUR).createKey(publicKeyServeur);
    }

    private void changementCleClient(String publicKeyClient) {
        Encryptage.getInstance(EncryptageType.ENCRYPTAGE_CLIENT).createKey(publicKeyClient);
    }

    /**
     * Envoie une enveloppe au serveur
     *
     * @param enveloppe Une classe implémentant l'interface convertissableXml
     */
    public void envoyerEnveloppe(ConvertissableXml enveloppe) {
        String xmlClientMessage = Encryptage.getInstance(EncryptageType.ENCRYPTAGE_CLIENT).encrypter(enveloppe.convertirEnXml());
        String xmlServer = new XMLWriter().construireXmlServeur(CommandesServeur.REQUETE_MESSAGES,
                new EnveloppeBalisesCommServeur(BalisesCommServeur.BALISE_MESSAGE, xmlClientMessage));

        gestionnaireSocket.envoyerMessage(xmlServer, EncryptageType.ENCRYPTAGE_SERVEUR);
    }

    /**
     * Envoi une demande de connexion au serveur
     *
     * @param nom  Le nom d'utilisateur
     * @param pass Le mot de passe
     * @return Une des valeurs de l'énum ResultatsConnexion : Valide, Invalide ou Impossible
     */
    public ResultatsConnexion seConnecter(String nom, String pass) {
        XMLWriter xmlWriter = new XMLWriter();

        EnveloppeBalisesCommServeur enveloppeBalisesCommServeurNom
                = new EnveloppeBalisesCommServeur(BalisesCommServeur.BALISE_NOM_UTILISATEUR, nom);
        EnveloppeBalisesCommServeur enveloppeBalisesCommServeurPass
                = new EnveloppeBalisesCommServeur(BalisesCommServeur.BALISE_MOT_DE_PASSE, pass);
        EnveloppeBalisesCommServeur enveloppeBalisesCommServeurIsTelephone
                = new EnveloppeBalisesCommServeur(BalisesCommServeur.BALISE_IS_TELEPHONE, Boolean.toString(IS_TELEPHONE));

        String comm = xmlWriter.construireXmlServeur(CommandesServeur.REQUETE_LOGIN, enveloppeBalisesCommServeurNom,
                enveloppeBalisesCommServeurPass, enveloppeBalisesCommServeurIsTelephone);

        return gestionnaireSocket.commencerCommunication(comm);
    }

    /**
     * Arrête le thread de la communication
     */
    public void arreterProgramme() {
        gestionnaireSocket.terminerCommuication();
    }

    public FacadeModele getFacadeModele() {
        return facadeModele;
    }

    public void ajouterObservateurErreur(ObservateurErreur observateurErreur) {
        this.gestionnaireSocket.ajouterObservateur(observateurErreur);
    }
}
