package ca.qc.bdeb.gr1_420_P56_BB.utilitaires;

/**
 * Un élément qui est observé
 */
public interface ObservableMessage {

    /**
     * Ajout d'un observateur
     *
     * @param ob un observateur
     */
    void ajouterObservateur(ObservateurMessage ob);

    /**
     * Supprimer l'observateur passé en paramètre
     *
     * @param ob un observateur
     */
    void retirerObservateur(ObservateurMessage ob);

    /**
     * Aviser tous les observateurs qu'un message est recu
     */
    void aviserObservateursMessageRecu(long num);

    /**
     * Aviser tous les observateurs qu'il s'agit de la première connexion
     */
    void aviserObservateursPremiereAjout();

}
