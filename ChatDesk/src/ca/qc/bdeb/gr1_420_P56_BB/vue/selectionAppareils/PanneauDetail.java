package ca.qc.bdeb.gr1_420_P56_BB.vue.selectionAppareils;

import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.Appareil;

import javax.swing.*;
import java.awt.*;

/**
 * Created by 1372883 on 2015-11-03.
 */
public class PanneauDetail extends JPanel implements Rappeleur {

    private Appareil appareil;
    private JLabel lblNom;

    public PanneauDetail() {
        this.lblNom = new JLabel();
        this.add(lblNom);
    }

    @Override
    public void rappeler(Appareil appareil) {
        this.appareil = appareil;
        mettreElementsAJour();
    }

    private void mettreElementsAJour() {
        if (appareil != null) {
            this.lblNom.setText(appareil.getNom());
        }
    }
}
