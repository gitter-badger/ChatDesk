package ca.qc.bdeb.gr1_420_P56_BB.connexion;

import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.Appareil;
import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.FacadeModele;
import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.Encryptage;
import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.EncryptageType;
import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.ObservateurErreur;

/**
 * Gère la connexion entre l'Android et l'ordinateur
 */
class GestionnaireCommunication {

    /**
     * Nombre de champs par appareils
     */
    private static final int NOMBRE_CHAMPS_APPAREIL = 2;

    /**
     * La facade du modele pour accèder au modèle
     */
    private final FacadeConnexion facadeConnexion;

    /**
     * Le gestionnaire de socket.
     */
    private final GestionnaireSocket gestionnaireSocket;

    /**
     * La position de la confirmation du login dans le tableau de contenu
     */
    private static final int POSITION_CONFIRMATION = 1;

    public GestionnaireCommunication(FacadeConnexion facadeConnexion) {
        this.facadeConnexion = facadeConnexion;
        this.gestionnaireSocket = new GestionnaireSocket(this);
    }

    /**
     * Gère la réception d'une communication du serveur en l'envoyant à la bonne méthode dépendamment de la commande
     *
     * @param messageRecu le message reçu du serveur en version xml encrypter selon la méthode du serveur
     */
    synchronized void reception(String messageRecu) {
        if (messageRecu != null && !messageRecu.isEmpty()) {
            XMLReaderServeur xmlReaderServeur = new XMLReaderServeur(messageRecu);
            switch (xmlReaderServeur.lireCommande()) {
                case REQUETE_NOUVEAU_COMPTE:
                    //Pas encore implémenté
                    break;
                case REQUETE_LIEN:
                    //echangerClePremiereFois();
                    break;
                case REQUETE_LIENS:
                    EnveloppeBalisesCommServeur[] tabAppareils = xmlReaderServeur.lireContenu();
                    lireAppareils(tabAppareils);
                    break;
                case REQUETE_COMM_CLIENT:
                    lireFichierXmlClient(xmlReaderServeur.lirePartieClient(messageRecu));
                    break;
                case REQUETE_ECHANGE_CLE:
                    changementCleServeur(xmlReaderServeur.lireContenu()[1].getContenu());
                    break;
            }
        } else {
            gestionnaireSocket.terminerCommunication();
        }
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

        facadeConnexion.setAppareils(tabAppareils);

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
        this.gestionnaireSocket.envoyer(CreateurXMLComm.creationXMLLien(idAppareil));
    }

    /**
     * Envoi une demande au serveur pour qu'il envoie tous les appareils auxquels il est possible de se connecter
     */
    public void demanderAppareils() {
        this.gestionnaireSocket.envoyer(CreateurXMLComm.creationXMLDemandeAppareils());
    }

    /**
     * Gère la lecture de la connexion addressée au client
     *
     * @param commClient Le xml de la connextion addressée au xlient
     */
    private void lireFichierXmlClient(String commClient) {
        XMLReader xmlReader = new XMLReader(Encryptage.getInstance(EncryptageType.ENCRYPTAGE_CLIENT).decrypter(commClient));
        switch (xmlReader.lireCommande()) {
            case PREMIERE_CONNEXION:
                facadeConnexion.ajouterContacts(xmlReader.lireContacts());
            case MESSAGES:
                facadeConnexion.ajouterMessages(xmlReader.lireMessages());
                break;
            case CONTACTS:
                facadeConnexion.ajouterContacts(xmlReader.lireContacts());
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
        ResultatsConnexion resultatsConnexion = gestionnaireSocket.connexionAuServeur();

        if (resultatsConnexion != ResultatsConnexion.IMPOSSIBLE) {
            String comm = CreateurXMLComm.creationXMLConnexionServeur(nom, pass);

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

    public boolean sinscrire(String nom, String pass) {
        boolean inscrit = false;
        ResultatsConnexion resultatsConnexion = gestionnaireSocket.connexionAuServeur();

        if (resultatsConnexion != ResultatsConnexion.IMPOSSIBLE) {
            String comm = CreateurXMLComm.creationXMLSInscrire(nom, pass);

            String contenu = gestionnaireSocket.sinscrire(comm);
            XMLReaderServeur xmlReaderServeur = new XMLReaderServeur(contenu);
            if (xmlReaderServeur.lireCommande() == CommandesServeur.REQUETE_NOUVEAU_COMPTE) {
                inscrit = xmlReaderServeur.lireContenu()[POSITION_CONFIRMATION].getContenu().equals(Boolean.toString(true));
            }
        }

        return inscrit;
    }

    /**
     * Arrête la communication avec le serveur
     */
    public void arreterProgramme() {
        gestionnaireSocket.terminerCommunication();
    }

    public FacadeModele getFacadeModele() {
        return facadeConnexion.getFacadeModele();
    }

    public void ajouterObservateurErreur(ObservateurErreur observateurErreur) {
        this.gestionnaireSocket.ajouterObservateur(observateurErreur);
    }
}
