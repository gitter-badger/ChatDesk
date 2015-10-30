package ca.qc.bdeb.gr1_420_p56_bb.connexion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import ca.qc.bdeb.gr1_420_p56_bb.utilitaires.Encryptage;
import ca.qc.bdeb.gr1_420_p56_bb.utilitaires.EncryptageType;

/**
 * Gère les communications avec le serveur niveau socket.
 */
class GestionnaireSocket implements Runnable {

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
     * Balises contenant le nombre de lignes qu'il faut lire dans la connexion
     */
    private final String DEBUT_BALISE_LIGNES = "<lines>", FIN_BALISE_LIGNES = "</lines>";
    /**
     * Indicateur d'entier pour le formattage de string
     */
    private final String INDICATEUR_ENTIER = "%d";


    private Socket socket;
    /**
     * Détermine si le socket devrait écouter en lecture, sa mise à false entrainerait la fin de la connexion
     */
    private boolean actif;
    private GestionnaireConnexion gestionnaireConnexion;

    /**
     * Host name à utilisé pour se connecter localement
     */
    private final String HOST_NAME_LOCAL = "127.0.0.1";
    /**
     * Host names auquel se connecté si le serveur est distant
     */
    private final String HOST_NAME = "chatdesk.ddns.net";
    /**
     * Le port auquel se connecté sur le serveur
     */
    private final int PORT = 8080;

    private static final Encryptage encryptageServeur = Encryptage.getInstance(EncryptageType.ENCRYPTAGE_SERVEUR);
    private static final Encryptage encryptageClient = Encryptage.getInstance(EncryptageType.ENCRYPTAGE_CLIENT);

    private PrintWriter out;
    private BufferedReader in;

    public GestionnaireSocket(GestionnaireConnexion gestionnaireConnexion) {
        this.gestionnaireConnexion = gestionnaireConnexion;



        this.socket = new Socket();
    }

    /**
     * Envoi une demande de connexion au serveur
     *
     * @param infoConnexionComm Les informations de connexion encrypté
     * @return Une des valeurs de l'énum ResultatsConnexion : Valide, Invalide ou Impossible
     */
    public ResultatsConnexion commencerCommunication(String infoConnexionComm) {
        ResultatsConnexion resultatsConnexion = ResultatsConnexion.IMPOSSIBLE;

        if (!socket.isConnected()) {
            try {
                this.socket = new Socket();
                this.socket.connect(new InetSocketAddress(HOST_NAME, PORT), TEMPS_CONNEXION_SOCKET);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                creationCleServeur();
                resultatsConnexion = ResultatsConnexion.INVALIDE;
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
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
                new EnveloppeBalisesComm(BalisesCommServeur.BALISE_PUBLIC_KEY, clientPublicKey));
        envoyerMessage(messageEnv, EncryptageType.ENCRYPTAGE_SERVEUR);

        String messageRecu = readAllLines();
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
     * @param infoConnexionComm Les informations de connexion encrypté
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
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
        if (communication != null) {
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
}
