package ca.qc.bdeb.gr1_420_P56_BB.connexion;

/**
 * Résualtats possibles d'une demande de connexion au serveur
 */
public enum ResultatsConnexion {
    /**
     * Indique que les informations sont valides et que la connexion est établie
     */
    VALIDE,
    /**
     * Indique qu'il y a plusieurs appareils et qu'un choix est nécessaire ou bien qu'il n'y a pas d'appareil
     */
    VALIDE_PLUSIEURS_OU_AUCUN_APPAREIL,
    /**
     * Indique que les informations sont invalides et que la connexion n'est pas établie
     */
    INVALIDE,
    /**
     * Indique que la connexion n'est pas établie parce que le serveur n'a pu être rejoint ou qu'une erreur s'est produite
     */
    IMPOSSIBLE
}
