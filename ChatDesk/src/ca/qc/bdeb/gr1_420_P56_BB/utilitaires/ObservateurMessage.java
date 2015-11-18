package ca.qc.bdeb.gr1_420_P56_BB.utilitaires;

/**
 * Un élément qui observe
 */
public interface ObservateurMessage {

    /**
     * Un nouveau message est recu
     */
    void receptionMessage(long num);

    /**
     * Fin du chargement de tous les messages et contacts
     */
    void finReceptionConnexionInitiale();

}
