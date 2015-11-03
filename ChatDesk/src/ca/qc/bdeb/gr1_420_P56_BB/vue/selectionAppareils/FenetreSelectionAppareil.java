package ca.qc.bdeb.gr1_420_P56_BB.vue.selectionAppareils;

import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.Appareil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by 1372883 on 2015-11-03.
 */
public class FenetreSelectionAppareil extends JFrame {
    private final static String NOM_FENETRE = "Sélection d'appareils";

    private PanneauMaitre panneauMaster;
    private PanneauDetail panneauDetail;

    public FenetreSelectionAppareil(ArrayList<Appareil> listeAppareils) {
        super(NOM_FENETRE);

        GridLayout layout = new GridLayout(1, 2);
        this.setLayout(layout);

        initialiserPanneauxMaitreDetail(listeAppareils);

        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }

    private void initialiserPanneauxMaitreDetail(ArrayList<Appareil> listeAppareils) {
        panneauDetail = new PanneauDetail();
        panneauMaster = new PanneauMaitre(listeAppareils, panneauDetail);

        this.add(panneauMaster);
        this.add(panneauDetail);
    }
}
