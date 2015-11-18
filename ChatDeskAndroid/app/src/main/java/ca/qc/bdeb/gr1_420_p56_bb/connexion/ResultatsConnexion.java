package ca.qc.bdeb.gr1_420_p56_bb.connexion;

/**
 * Résualtats possibles d'une demande de connexion au serveur
 */
public enum ResultatsConnexion {
    /**
     * Indique que les informations sont valides et que la connexion est établie
     */
    VALIDE,
    /**
     * Indique que les informations sont invalides et que la connexion n'est pas établie
     */
    INVALIDE,
    /**
     * Indique que la connexion n'est pas établie parce que le serveur n'a pu être rejoint ou qu'une erreur s'est produite
     */
    IMPOSSIBLE
}
