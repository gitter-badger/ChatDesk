package ca.qc.bdeb.gr1_420_P56_BB.utilitaires;

import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.Appareil;
import ca.qc.bdeb.gr1_420_P56_BB.connexion.ErreursSocket;

/**
 * Created by 1372883 on 2015-11-25.
 */
public interface ObservateurAppareils {

    /**
     * Un changement d'état est observé
     */
    void aviserAppareils();
}
