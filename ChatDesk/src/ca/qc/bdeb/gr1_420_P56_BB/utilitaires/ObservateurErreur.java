package ca.qc.bdeb.gr1_420_P56_BB.utilitaires;

import ca.qc.bdeb.gr1_420_P56_BB.connexion.ErreursSocket;

/**
 * Un élément qui observe
 */
public interface ObservateurErreur {

    /**
     * Un changement d'état est observé
     */
    void aviserErreur(ErreursSocket erreursSocket);

}
