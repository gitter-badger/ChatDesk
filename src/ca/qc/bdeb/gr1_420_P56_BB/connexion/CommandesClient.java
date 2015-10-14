package ca.qc.bdeb.gr1_420_P56_BB.connexion;

/**
 * Commandes à mettre dans la balise BalisesCommClient.BALISE_COMMANDE
 * Created by 1372883 on 2015-09-15.
 */
enum CommandesClient implements Balises {

    /**
     * Demande les informations propros à la première connexion, c'est à dire tous les contacts et message d'un appareil
     */
    PREMIERE_CONNEXION("premiere_connexion"),
    /**
     * Demande tous les contacts
     */
    CONTACTS("contacts"),
    /**
     * Demande tous les messages
     */
    MESSAGES("messages");

    private final String commande;

    CommandesClient(String commande) {
        this.commande = commande;
    }

    public String getBalise() {
        return this.commande;
    }

    public static CommandesClient getCommandeParString(String commandeString) {
        CommandesClient commande;
        switch (commandeString) {
            case "premiere_connexion":
                commande = PREMIERE_CONNEXION;
                break;
            case "contacts":
                commande = CONTACTS;
                break;
            case "messages":
                commande = MESSAGES;
                break;
            default:
                commande = null;
                break;
        }
        return commande;
    }
}
