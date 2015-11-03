package ca.qc.bdeb.gr1_420_P56_BB.utilitaires;

/**
 * Un élément qui est observé
 */
public interface ObservableErreur {

    /**
     * Ajout d'un observateur
     *
     * @param ob un observateur
     */
    void ajouterObservateur(ObservateurErreur ob);

    /**
     * Supprimer l'observateur passé en paramètre
     *
     * @param ob un observateur
     */
    void retirerObservateur(ObservateurErreur ob);

    /**
     * Supprimer l'observateur à une certaine position
     *
     * @param indice La position
     */
    void retirerObservateur(int indice);

    /**
     * Aviser tous les observateurs
     */
    void aviserObservateurs();

    /**
     * Avise l'observateur à une certaine position
     *
     * @param indice La position
     */
    void aviserObservateur(int indice);

}
