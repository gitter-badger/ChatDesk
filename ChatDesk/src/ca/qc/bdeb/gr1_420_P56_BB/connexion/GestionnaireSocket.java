package ca.qc.bdeb.gr1_420_P56_BB.connexion;

import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.Encryptage;
import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.EncryptageType;
import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.ObservableErreur;
import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.ObservateurErreur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Gère les communications avec le serveur niveau socket.
 */
class GestionnaireSocket implements Runnable, ObservableErreur {

    private static ArrayList<ObservateurErreur> listeObservableErreurs = new ArrayList<>();

    /**
     * Le port auquel se connecté sur le serveur
     */
    private final static int PORT = 8080;

    /**
     * Host names auquel se connecté si le serveur est distant
     */
    private final static String HOST_NAME = "chatdesk.ddns.net";

    /**
     * Temps d'attente maximal lors d'une lecture obligatoire
     */
    private static final int TEMPS_ATTENTE_LECTURE = 500;

    /**
     * Temps d'attente infini
     */
    private static final int TEMPS_REPONSE_INFINI = 0;

    /**
     * Temps d'attente maximal lors de la connexion initale du socket
     */
    private static final int TEMPS_CONNEXION_SOCKET = 1500;

    /**
     * La position de la confirmation du login dans le tableau de contenu
     */
    private static final int POSITION_CONFIRMATION = 1;

    /**
     * La position du début des données dans le tableau de contenu
     */
    private static final int POSITION_DEBUT_DONNEES = 1;

    /**
     * La première ligne du string
     */
    private static final int NOMBRE_LIGNES_INITIAL = 1;

    /**
     * Balises ouvrante contenant le nombre de lignes
     */
    private final String DEBUT_BALISE_LIGNES = "<lines>";

    /**
     * Balises fermante contenant le nombre de lignes
     */
    private final String FIN_BALISE_LIGNES = "</lines>";

    /**
     * Le socket de la communication avec le serveur
     */
    private Socket socket;

    /**
     * Détermine si le socket devrait écouter en lecture, sa mise à false entrainerait la fin de la connexion
     */
    private boolean actif;

    /**
     * Le gestionnaire de connexion. Ge
     */
    private final GestionnaireConnexion gestionnaireConnexion;

    /**
     * Pour l'envoie de données au serveur
     */
    private PrintWriter out;

    /**
     * Pour la réception de données du serveur
     */
    private BufferedReader in;

    private static final Encryptage encryptageServeur = Encryptage.getInstance(EncryptageType.ENCRYPTAGE_SERVEUR);
    private static final Encryptage encryptageClient = Encryptage.getInstance(EncryptageType.ENCRYPTAGE_CLIENT);

    public GestionnaireSocket(GestionnaireConnexion gestionnaireConnexion) {
        this.gestionnaireConnexion = gestionnaireConnexion;
        this.socket = new Socket();
    }

    /**
     * Envoi une demande de connexion au serveur
     *
     * @param infoConnexionComm Le string à envoyer au serveur
     * @return Une des valeurs de l'énum ResultatsConnexion : Valide, Invalide ou Impossible
     */
    public ResultatsConnexion commencerCommunication(String infoConnexionComm) {
        ResultatsConnexion resultatsConnexion ;

        if (!socket.isConnected()) {
            try {
                this.socket = new Socket();
                this.socket.connect(new InetSocketAddress(HOST_NAME, PORT), TEMPS_CONNEXION_SOCKET);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                creationCleServeur();
                resultatsConnexion = ResultatsConnexion.INVALIDE;
            } catch (IOException e) {
                resultatsConnexion = ResultatsConnexion.IMPOSSIBLE;
            }
        } else {
            resultatsConnexion = ResultatsConnexion.INVALIDE;
        }

        if (resultatsConnexion != ResultatsConnexion.IMPOSSIBLE) {
            if (connecter(infoConnexionComm)) {
                new Thread(this).start();
                resultatsConnexion = ResultatsConnexion.VALIDE;
            }
        }

        return resultatsConnexion;
    }

    private void creationCleServeur() {
        String clientPublicKey = encryptageServeur.createKeyToPair();
        XMLWriter xmlWriter = new XMLWriter();
        String messageEnv = xmlWriter.construireXmlServeur(CommandesServeur.REQUETE_ECHANGE_CLE,
                new EnveloppeBalisesCommServeur(BalisesCommServeur.BALISE_PUBLIC_KEY, clientPublicKey));
        envoyerMessage(messageEnv, EncryptageType.ENCRYPTAGE_SERVEUR);

        String messageRecu = readAllLines();

        // TODO : Supprimer cette ligne lors du changement d'encryptage
        messageRecu = Encryptage.getInstance(EncryptageType.ENCRYPTAGE_SERVEUR).decrypter(messageRecu);
        XMLReaderServeur xmlReaderServeur = new XMLReaderServeur(messageRecu);
        String serveurPublicKey = xmlReaderServeur.lireContenu()[1].getContenu();
        encryptageServeur.createKey(serveurPublicKey);
    }

    public void creationCleClient() {
        String clientPublicKey = encryptageClient.createKeyToPair();
        XMLWriter xmlWriter = new XMLWriter();
        String messageEnv = xmlWriter.construireCleClient(clientPublicKey);
        envoyerMessage(messageEnv, EncryptageType.ENCRYPTAGE_CLIENT);

        String messageRecu = readAllLines();
        XMLReaderServeur xmlReaderServeur = new XMLReaderServeur(messageRecu);
        String autreClientPublicKey = xmlReaderServeur.lireContenu()[1].getContenu();
        encryptageClient.createKey(autreClientPublicKey);
    }

    /**
     * Thread d'écoute en lecture des communications du serveur
     */
    @Override
    public synchronized void run() {
        String contenu;
        this.actif = true;
        while (actif) {
            try {
                this.socket.setSoTimeout(TEMPS_ATTENTE_LECTURE);
            } catch (SocketException e) {
                e.printStackTrace();
            }
            contenu = readAllLines();

            gestionnaireConnexion.reception(contenu);
        }
    }

    /**
     * Envoi une requête de login au serveur
     *
     * @param infoConnexionComm Le string à envoyer au serveur
     * @return Boolean indiquant si les données sont valides
     */
    private boolean connecter(String infoConnexionComm) {
        envoyerMessage(infoConnexionComm, EncryptageType.ENCRYPTAGE_SERVEUR);
        return receptionReponseConnexion();
    }

    /**
     * Attend la réponse du serveur à la demande de connexion
     *
     * @return Si la demande a fonctionné
     */
    private boolean receptionReponseConnexion() {
        boolean connecte = false;
        try {
            this.socket.setSoTimeout(TEMPS_ATTENTE_LECTURE);
            String contenu = readAllLines();
            contenu = encryptageServeur.decrypter(contenu);
            XMLReaderServeur xmlReaderServeur = new XMLReaderServeur(contenu);
            if (xmlReaderServeur.lireCommande() == CommandesServeur.REQUETE_LOGIN) {
                connecte = xmlReaderServeur.lireContenu()[POSITION_CONFIRMATION].getContenu().equals(Boolean.toString(!connecte));
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return connecte;
    }

    /**
     * Lis toutes les lignes d'une communication et les retournent dans un seul String
     *
     * @return String des lignes concaténées
     */
    private synchronized String readAllLines() {
        String contenu = "";
        String inputLine;

        try {
            this.socket.setSoTimeout(TEMPS_REPONSE_INFINI);
            inputLine = in.readLine();
            inputLine = inputLine.replace(DEBUT_BALISE_LIGNES, "");
            inputLine = inputLine.replace(FIN_BALISE_LIGNES, "");
            int nbrLigne = Integer.parseInt(inputLine);

            for (int i = POSITION_DEBUT_DONNEES; i < nbrLigne; i++) {
                inputLine = in.readLine();
                contenu += inputLine;
            }
        } catch (IOException e) {
            aviserObservateurs();
        }

        return contenu;
    }

    /**
     * Met un terme au thread d'écoute
     */
    void terminerCommuication() {
        this.actif = false;
    }

    /**
     * Envoi un message
     *
     * @param communication
     */
    void envoyerMessage(String communication, EncryptageType encryptageType) {
        if (out != null) {
            out.println(mettreBaliseNombreLigne(Encryptage.getInstance(encryptageType).encrypter(communication)));
            out.flush();
        }
    }

    /**
     * Ajoute les balises contenant le nombre de lignes au début de la communication
     *
     * @param communication La communication
     * @return La communication avec les balises ajoutées
     */
    private String mettreBaliseNombreLigne(String communication) {
        // Indicateur d'entier pour le formattage de string
        String INDICATEUR_ENTIER = "%d";
        communication = DEBUT_BALISE_LIGNES + INDICATEUR_ENTIER + FIN_BALISE_LIGNES + "\n" + communication;
        communication = String.format(communication, trouverNombreLignes(communication));
        return communication;
    }

    /**
     * Trouve le nombre de lignes que fait la communication
     *
     * @param communication La communication
     * @return Le nombre de lignes que fait la communication
     */
    private int trouverNombreLignes(String communication) {
        int counter = NOMBRE_LIGNES_INITIAL;

        for (int i = 0; i < communication.length(); i++) {
            if (communication.charAt(i) == '\n') {
                counter++;
            }
        }

        return counter;
    }

    void terminerConnexion() {
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ajouterObservateur(ObservateurErreur ob) {
        listeObservableErreurs.add(ob);
    }

    @Override
    public void retirerObservateur(ObservateurErreur ob) {
        listeObservableErreurs.remove(ob);
    }

    @Override
    public void retirerObservateur(int indice) {
        listeObservableErreurs.remove(indice);
    }

    @Override
    public void aviserObservateurs() {
        for (ObservateurErreur observateurErreur : listeObservableErreurs) {
            observateurErreur.aviserErreur();
        }
    }

    @Override
    public void aviserObservateur(int indice) {
        listeObservableErreurs.get(indice).aviserErreur();
    }
}
