package ca.qc.bdeb.gr1_420_P56_BB.connexion;

/**
 * Commandes à mettre dans la balise BalisesCommClient.BALISE_COMMANDE
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
    MESSAGES("messages"),

    REQUETE_ECHANGE_CLE("echange_cle");

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
            case "echange_cle":
                commande = REQUETE_ECHANGE_CLE;
                break;
            default:
                commande = null;
                break;
        }
        return commande;
    }
}
