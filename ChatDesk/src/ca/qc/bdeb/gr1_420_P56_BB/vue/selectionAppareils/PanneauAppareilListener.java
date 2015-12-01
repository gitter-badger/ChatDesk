package ca.qc.bdeb.gr1_420_P56_BB.vue.selectionAppareils;

import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.Appareil;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Louis-Simon on 23/11/2015.
 */
public class PanneauAppareilListener extends MouseAdapter {
    private static final int DOUBLE_CLICK_COUNT = 2;

    private final Rappeleur rappeleur;
    private final Appareil appareil;

    public PanneauAppareilListener(Rappeleur rappeleur, Appareil appareil) {
        this.rappeleur = rappeleur;
        this.appareil = appareil;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == DOUBLE_CLICK_COUNT) {

        }else{
            rappeleur.rappeler(appareil);
        }
    }
}
