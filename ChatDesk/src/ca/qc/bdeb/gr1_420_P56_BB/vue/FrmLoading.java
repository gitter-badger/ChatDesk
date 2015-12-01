package ca.qc.bdeb.gr1_420_P56_BB.vue;

import javax.swing.*;
import javax.swing.text.ChangedCharSetException;
import java.awt.*;

/**
 * Created by Alexandre on 2015-11-17.
 */
public class FrmLoading extends JInternalFrame implements Runnable {

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

    public FrmLoading(FrmChatDesk frmChatDesk) {
        this.putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
        this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        this.setLayout(new FlowLayout());
        this.setBackground(Color.LIGHT_GRAY);

        JLabel lblMessage = new JLabel(MESSAGE);
        lblMessage.setFont(FONT);

        jLblProgression = new JLabel("...");
        jLblProgression.setFont(FONT);

        this.add(lblMessage);
        this.add(jLblProgression);

        this.pack();

        this.setLocation(frmChatDesk.getWidth() / 2 - this.getWidth() / 2, frmChatDesk.getHeight() / 2 - this.getHeight() / 2);
        frmChatDesk.add(this);
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
