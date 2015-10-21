package ca.qc.bdeb.gr1_420_p56_bb.utilitaires;

/**
 * Created by Alexandre on 2015-09-04.
 *
 * Un élément qui observe
 */
public interface Observateur{

    /**
     * Un changement d'état est observé
     */
    void changementEtat(long num);

}
