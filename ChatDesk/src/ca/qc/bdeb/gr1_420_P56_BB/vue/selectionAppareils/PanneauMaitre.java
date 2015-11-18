package ca.qc.bdeb.gr1_420_P56_BB.vue.selectionAppareils;

import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.Appareil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by 1372883 on 2015-11-03.
 */
public class PanneauMaitre extends JPanel {

    private static final String CHEMIN_ICONE = "resources\\images\\Mobile-Smartphone-icon.png";
    private ArrayList<Appareil> listeAppareils;
    private Rappeleur rappeleur;

    public PanneauMaitre(ArrayList<Appareil> listeAppareils, Rappeleur rappeleur) {
        this.listeAppareils = listeAppareils;
        this.rappeleur = rappeleur;

        initialiserListeAppareil();

        sendCallback(1);
    }

    private void initialiserListeAppareil() {
        try {
            for (Appareil appareil : listeAppareils) {
                this.add(initialiserPanel(appareil.getNom()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JPanel initialiserPanel(String nom) throws IOException {
        JPanel panel = new JPanel();

        panel.setBackground(Color.white);
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        panel.setOpaque(true);
        panel.setLayout(new GridBagLayout());
        ajouterComposants(panel, nom);

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

    private void sendCallback(int indice) {
        rappeleur.rappeler(listeAppareils.get(indice));
    }
}