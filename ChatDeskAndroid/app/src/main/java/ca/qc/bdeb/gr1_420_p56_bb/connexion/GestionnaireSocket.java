package ca.qc.bdeb.gr1_420_p56_bb.connexion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

/**
 * Gère les communications avec le serveur niveau socket.
 */
class GestionnaireSocket implements Runnable {

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
     * Fin du message envoyé par le serveur
     */
    private static final char CARAC_FIN_MESSAGE = '\0';

    /**
     * Le socket de la communication avec le serveur
     */
    private Socket socket;

    /**
     * Détermine si le socket devrait écouter en lecture, sa mise à false entrainerait la fin de la connexion
     */
    private boolean actif;

    /**
     * Le gestionnaire de connexion.
     */
    private final GestionnaireCommunication gestionnaireCommunication;

    /**
     * Pour l'envoie de données au serveur
     */
    private PrintWriter out;

    /**
     * Pour la réception de données du serveur
     */
    private BufferedReader in;

    public GestionnaireSocket(GestionnaireCommunication gestionnaireCommunication) {
        this.gestionnaireCommunication = gestionnaireCommunication;
        this.socket = new Socket();
    }

    /**
     * Envoi une demande de connexion au serveur
     *
     * @return Une des valeurs de l'énum ResultatsConnexion : Invalide ou Impossible
     */
    public ResultatsConnexion connexionAuServeur() {
        ResultatsConnexion resultatsConnexion = ResultatsConnexion.INVALIDE;

        if (!socket.isConnected()) {
            try {
                this.socket.connect(new InetSocketAddress(HOST_NAME, PORT), TEMPS_CONNEXION_SOCKET);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                resultatsConnexion = ResultatsConnexion.IMPOSSIBLE;
            }
        }

        return resultatsConnexion;
    }

    /**
     * Envoi une requête de login au serveur
     *
     * @param infoConnexionComm Le string à envoyer au serveur
     * @return String de la réponse du serveur à la connexion
     */
    public String seConnecter(String infoConnexionComm) {
        envoyer(infoConnexionComm);
        return receptionReponseConnexion();
    }

    /**
     * Attend la réponse du serveur à la demande de connexion
     *
     * @return Si la demande a fonctionné
     */
    private String receptionReponseConnexion() {
        String contenu = "";
        try {
            this.socket.setSoTimeout(TEMPS_ATTENTE_LECTURE);
            contenu = readAllLines();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return contenu;
    }

    /**
     * Commencer le thread qui écoute ce que le serveur envoie
     */
    public void commencerEcouteServeur() {
        new Thread(this).start();
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

            gestionnaireCommunication.reception(contenu);
        }
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

            do {
                inputLine = in.readLine();
                contenu += inputLine;
            } while (inputLine.toCharArray()[inputLine.length() - 1] != CARAC_FIN_MESSAGE);

            contenu = contenu.substring(0, contenu.length() - 1);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return contenu;
    }

    /**
     * Envoi un message
     *
     * @param communication
     */
    void envoyer(String communication) {
        if (out != null && communication != null) {
            out.println(communication + CARAC_FIN_MESSAGE);
            out.flush();
        }
    }

    /**
     * Met un terme au thread d'écoute
     */
    void terminerCommunication() {
        try {
            this.actif = false;
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
