package ca.qc.bdeb.gr1_420_P56_BB.connexion;

/**
 * Balises utilisées pour les communications avec le serveur
 * Created by Alexandre on 2015-09-16.
 */
enum BalisesCommServeur implements Balises {

    /**
     * Balise générale toujours présente et qui englobe toute la communication avec le serveur
     */
    BALISE_SERVEUR("serveur"),
    /**
     * Balise requete, précisant quel est le but de la communication
     */
    BALISE_REQUETE("requete"),
    /**
     * Contient le nom d'utilisateur dans le cas d'un login ou de la création d'un compte
     */
    BALISE_NOM_UTILISATEUR("nom_utilisateur"),
    /**
     * Contient le mot de passe dans le cas d'un login ou de la création d'un compte
     */
    BALISE_MOT_DE_PASSE("mot_de_passe"),
    /**
     * Contient le nom d'un appareil
     */
    BALISE_NOM_APPAREIL("nom_appareil"),
    /**
     * Contient l'id d'un appareil
     */
    BALISE_ID_APPAREIL("id_appareil"),
    /**
     * Contient le type d'un appareil
     */
    BALISE_TYPE_APPAREIL("type_appareil"),
    /**
     * Contient l'indicateur de la validité des informations lors du login
     */
    BALISE_VALIDITE_CONNEXION("validite_connexion"),
    /**
     * Précise si un appareil est un téléphone ou autre
     */
    BALISE_IS_TELEPHONE("is_telephone"),
    /**
     * Contient un message
     */
    BALISE_MESSAGE("message");

    private String balise;

    BalisesCommServeur(String balise) {
        this.balise = balise;
    }

    public String getBalise() {
        return this.balise;
    }

    public static BalisesCommServeur getBaliseParString(String baliseString) {
        BalisesCommServeur baliseServeur;

        switch (baliseString) {
            case "serveur":
                baliseServeur = BALISE_SERVEUR;
                break;
            case "nom_utilisateur":
                baliseServeur = BALISE_NOM_UTILISATEUR;
                break;
            case "mot_de_passe":
                baliseServeur = BALISE_MOT_DE_PASSE;
                break;
            case "requete":
                baliseServeur = BALISE_REQUETE;
                break;
            case "nom":
                baliseServeur = BALISE_NOM_APPAREIL;
                break;
            case "type":
                baliseServeur = BALISE_TYPE_APPAREIL;
                break;
            case "validite_connexion":
                baliseServeur = BALISE_VALIDITE_CONNEXION;
                break;
            case "message":
                baliseServeur = BALISE_MESSAGE;
                break;
            case "is_telephone":
                baliseServeur = BALISE_IS_TELEPHONE;
                break;
            default:
                baliseServeur = null;
                break;
        }

        return baliseServeur;
    }
}

