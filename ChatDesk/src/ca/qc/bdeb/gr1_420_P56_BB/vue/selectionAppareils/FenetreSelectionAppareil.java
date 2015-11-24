package ca.qc.bdeb.gr1_420_P56_BB.vue.selectionAppareils;

import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.Appareil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by 1372883 on 2015-11-03.
 */
public class FenetreSelectionAppareil extends JFrame {
    private static final Insets MARGES_PANNEAU = new Insets(50, 50, 50, 50);
    private static final String NOM_FENETRE = "Sélection d'appareils";

    private PanneauMaitre panneauMaster;
    private PanneauDetail panneauDetail;

    public FenetreSelectionAppareil(ArrayList<Appareil> listeAppareils) {
        super(NOM_FENETRE);

        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);

        initialiserPanneauxMaitreDetail(listeAppareils);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void initialiserPanneauxMaitreDetail(ArrayList<Appareil> listeAppareils) {
        panneauDetail = new PanneauDetail();
        panneauMaster = new PanneauMaitre(listeAppareils, panneauDetail);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.insets = MARGES_PANNEAU;
        this.add(panneauMaster, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.insets = MARGES_PANNEAU;
        this.add(panneauDetail, constraints);
    }
}