package ca.qc.bdeb.gr1_420_P56_BB.connexion;

import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.Encryptage;
import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.EncryptageType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import static ca.qc.bdeb.gr1_420_P56_BB.utilitaires.Encryptage.encrypter;

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
     * Indique si ce programme est un téléphone, dans notre cas évidemment non
     */
    private static final boolean IS_TELEPHONE = false;
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

    private PrintWriter out;
    private BufferedReader in;

    public GestionnaireSocket(GestionnaireConnexion gestionnaireConnexion) {
        this.gestionnaireConnexion = gestionnaireConnexion;
        this.socket = new Socket();
    }

    /**
     * Envoi une demande de connexion au serveur
     *
     * @param nom  Le nom d'utilisateur
     * @param pass Le mot de passe
     * @return Une des valeurs de l'énum ResultatsConnexion : Valide, Invalide ou Impossible
     */
    public ResultatsConnexion commencerCommunication(String nom, String pass) {
        ResultatsConnexion resultatsConnexion = ResultatsConnexion.IMPOSSIBLE;

        if (!socket.isConnected()) {
            try {
                this.socket = new Socket();
                this.socket.connect(new InetSocketAddress(HOST_NAME, PORT), TEMPS_CONNEXION_SOCKET);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
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
            if (connecter(nom, pass)) {
                new Thread(this).start();
                resultatsConnexion = ResultatsConnexion.VALIDE;
            }
        }

        return resultatsConnexion;
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
     * @param user Le nom d'utilisateur
     * @param pass Le mot de passe
     * @return Boolean indiquant si les données sont valides
     */
    private boolean connecter(String user, String pass) {
        XMLWriter xmlWriter = new XMLWriter();
        boolean connecte = false;

        EnveloppeBalisesCommServeur enveloppeBalisesCommServeurNom
                = new EnveloppeBalisesCommServeur(BalisesCommServeur.BALISE_NOM_UTILISATEUR, user);
        EnveloppeBalisesCommServeur enveloppeBalisesCommServeurPass
                = new EnveloppeBalisesCommServeur(BalisesCommServeur.BALISE_MOT_DE_PASSE, pass);
        EnveloppeBalisesCommServeur enveloppeBalisesCommServeurIsTelephone
                = new EnveloppeBalisesCommServeur(BalisesCommServeur.BALISE_IS_TELEPHONE, Boolean.toString(IS_TELEPHONE));

        String comm = xmlWriter.construireXmlServeur(CommandesServeur.REQUETE_LOGIN, enveloppeBalisesCommServeurNom,
                enveloppeBalisesCommServeurPass, enveloppeBalisesCommServeurIsTelephone);
        envoyerMessage(encrypter(comm, EncryptageType.ENCRYPTAGE_SERVER));

        try {
            this.socket.setSoTimeout(TEMPS_ATTENTE_LECTURE);
            String contenu = readAllLines();
            contenu = Encryptage.decrypter(contenu, EncryptageType.ENCRYPTAGE_SERVER);
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
        } catch (SocketException e ) {
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
     * @param communication
     */
    void envoyerMessage(String communication) {
        out.println(mettreBaliseNombreLigne(communication));
        out.flush();
    }

    /**
     * Ajoute les balises contenant le nombre de lignes au début de la communication
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
