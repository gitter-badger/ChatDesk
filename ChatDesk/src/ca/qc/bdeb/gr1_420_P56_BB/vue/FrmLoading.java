package ca.qc.bdeb.gr1_420_P56_BB.vue;

import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.Formatage;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Alexandre on 2015-11-17.
 */
public class FrmLoading extends JFrame implements Runnable {

    private static final String MESSAGE = "Téléchargement de tous les messages et contact";

    /**
     * Nom de la police dans le panel
     */
    private static final String NOM_POLICE = "Segoe UI Black";

    /**
     * Taille du texte
     */
    private static final int GRANDEUR_TEXTE = 16;

    /**
     * Font dans le panel
     */
    private final Font FONT = new Font(NOM_POLICE, Font.BOLD, GRANDEUR_TEXTE);

    private boolean chargement = false;

    private JLabel jLblProgression;

    public FrmLoading(JFrame frm) {
        this.setUndecorated(true);
        this.setLayout(new FlowLayout());
        this.setAlwaysOnTop(true);
        this.setBackground(Color.DARK_GRAY);

        JLabel lblMessage = new JLabel(MESSAGE);
        lblMessage.setFont(FONT);

        jLblProgression = new JLabel("...");
        jLblProgression.setFont(FONT);

        this.add(lblMessage);
        this.add(jLblProgression);

        this.pack();
        this.setLocationRelativeTo(frm);
    }

    @Override
    public void run() {
        this.setVisible(true);

        while (chargement) {
            try {
                Thread.sleep(800);
            } catch (InterruptedException ex) {
            }

            switch (jLblProgression.getText()) {
                case ".":
                    jLblProgression.setText("..");
                    break;
                case "..":
                    jLblProgression.setText("...");
                    break;
                case "...":
                    jLblProgression.setText(".");
                    break;
            }
        }
    }

    public void commencerChargement() {
        this.chargement = true;
        new Thread(this).start();
        this.setVisible(true);
    }

    public void arreterChargement() {
        chargement = false;
        this.dispose();
    }
}
