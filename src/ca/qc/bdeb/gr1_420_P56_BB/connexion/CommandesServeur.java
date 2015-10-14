package ca.qc.bdeb.gr1_420_P56_BB.connexion;

/**
 * Commandes à mettre dans la balise BalisesCommServeur.BALISE_REQUETE
 * Created by Louis-Simon on 04/10/2015.
 */
enum CommandesServeur {

    REQUETE_LIEN("lien"),
    REQUETE_LIENS("liens"),
    REQUETE_MESSAGES("messages"),
    REQUETE_LOGIN("login"),
    REQUETE_NOUVEAU_COMPTE("compte");

    private final String balise;

    CommandesServeur(String balise) {
        this.balise = balise;
    }

    public String getRequete() {
        return this.balise;
    }

    public static CommandesServeur getRequeteParString(String requeteString) {
        CommandesServeur commande;

        switch (requeteString) {
            case "lien":
                commande = REQUETE_LIEN;
                break;
            case "liens":
                commande = REQUETE_LIENS;
                break;
            case "messages":
                commande = REQUETE_MESSAGES;
                break;
            case "login":
                commande = REQUETE_LOGIN;
                break;
            case "compte":
                commande = REQUETE_NOUVEAU_COMPTE;
                break;
            default:
                commande = null;
                break;
        }

        return commande;
    }
}
