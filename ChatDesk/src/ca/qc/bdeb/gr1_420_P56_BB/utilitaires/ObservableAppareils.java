package ca.qc.bdeb.gr1_420_P56_BB.utilitaires;

import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.Appareil;
import ca.qc.bdeb.gr1_420_P56_BB.connexion.ErreursSocket;

/**
 * Created by 1372883 on 2015-11-25.
 */
public interface ObservableAppareils {

    /**
     * Ajout d'un observateur
     *
     * @param ob un observateur
     */
    void ajouterObservateur(ObservateurAppareils ob);

    /**
     * Supprimer l'observateur passé en paramètre
     *
     * @param ob un observateur
     */
    void retirerObservateur(ObservateurAppareils ob);

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
