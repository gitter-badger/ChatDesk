package ca.qc.bdeb.gr1_420_P56_BB.utilitaires;

/**
 * Un élément qui est observé
 */
public interface Observable {

    /**
     * Ajout d'un observateur
     *
     * @param ob un observateur
     */
    void ajouterObservateur(Observateur ob);

    /**
     * Supprimer l'observateur passé en paramètre
     *
     * @param ob un observateur
     */
    void retirerObservateur(Observateur ob);

    /**
     * Supprimer l'observateur à une certaine position
     *
     * @param indice La position
     */
    void retirerObservateur(int indice);

    /**
     * Aviser tous les observateurs
     */
    void aviserObservateurs(long num);

    /**
     * Avise l'observateur à une certaine position
     *
     * @param indice La position
     */
    void aviserObservateur(int indice, long num);

}
