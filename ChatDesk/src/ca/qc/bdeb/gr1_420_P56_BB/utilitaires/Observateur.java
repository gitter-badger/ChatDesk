package ca.qc.bdeb.gr1_420_P56_BB.utilitaires;

/**
 * Un élément qui observe
 */
public interface Observateur{

    /**
     * Un changement d'état est observé
     */
    void changementEtat(long num);

}
