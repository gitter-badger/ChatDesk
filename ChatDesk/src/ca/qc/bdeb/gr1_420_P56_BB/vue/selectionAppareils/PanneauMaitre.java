package ca.qc.bdeb.gr1_420_P56_BB.vue.selectionAppareils;

import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.Appareil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by 1372883 on 2015-11-03.
 */
public class PanneauMaitre extends JPanel {

    private static final int ESPACEMENT_X = 20;
    private static final String CHEMIN_ICONE = "resources\\images\\chat_desk_icon.png";

    private Appareil[] tabAppareils;
    private Rappeleur rappeleur;

    public PanneauMaitre(Appareil[] tabAppareils, Rappeleur rappeleur) {
        FlowLayout layoutCourant = (FlowLayout) this.getLayout();
        layoutCourant.setHgap(ESPACEMENT_X);
        this.tabAppareils = tabAppareils;
        this.rappeleur = rappeleur;

        initialiserListeAppareil();
    }

    private void initialiserListeAppareil() {
        try {
            for (int i = 0; i < tabAppareils.length; i++) {
                this.add(initialiserPanel(tabAppareils[i]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JPanel initialiserPanel(Appareil appareil) throws IOException {
        JPanel panel = new JPanel();

        panel.setBackground(Color.white);
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        panel.setOpaque(true);
        panel.setLayout(new GridBagLayout());
        panel.addMouseListener(new PanneauAppareilListener(rappeleur, appareil));

        ajouterComposants(panel, appareil.getNom());

        return panel;
    }

    private void ajouterComposants(JPanel panel, String nom) throws IOException {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        panel.add(new JLabel(new ImageIcon(ImageIO.read(new File(CHEMIN_ICONE)))), constraints);
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.weightx = 1;
        constraints.weighty = 1;
        panel.add(new JLabel(nom), constraints);
    }
}