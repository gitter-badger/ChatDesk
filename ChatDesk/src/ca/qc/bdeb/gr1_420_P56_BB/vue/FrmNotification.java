package ca.qc.bdeb.gr1_420_P56_BB.vue;

import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.Contact;
import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.FacadeModele;
import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.Message;
import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.Formatage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by Alexandre on 2015-11-25.
 */
public class FrmNotification extends JFrame implements Runnable {

    private static final int TEMPS_ATTENTE_SEC_MAX = 8;

    private static final int SECONDE_EN_MILLI_SEC = 1000;

    private static final int NOMBRE_PIXEL_TOMBER_FENETRE = 10;

    private static final int VITESSE_TOMBER_FENETRE_MILLI_SEC = 18;

    private static final String TEXTE_VIDE = "";

    private static final int SIZE_IMAGE_CARREE = 40;

    private static final int WIDTH_NOTIFICATION = 300;

    private static final int HEIGHT_NOTIFICATION = 125;

    private static final int INSETS_NOTIFICATION_HEADER = 5;

    private static final Insets INSETS_NOTIFICATION_MESSAGE = new Insets(INSETS_NOTIFICATION_HEADER,
            INSETS_NOTIFICATION_HEADER, INSETS_NOTIFICATION_HEADER, INSETS_NOTIFICATION_HEADER);

    private static final Insets INSETS_HEADER = new Insets(INSETS_NOTIFICATION_HEADER,
            INSETS_NOTIFICATION_HEADER, INSETS_NOTIFICATION_HEADER, INSETS_NOTIFICATION_HEADER);

    private static final Insets MARGIN_BOUTON_FERMER = new Insets(1, 4, 1, 4);

    private FacadeModele facadeModele;

    private JLabel lblImage;

    private JLabel lblTexte;

    public FrmNotification(FacadeModele facadeModele) {
        this.facadeModele = facadeModele;
        this.setType(javax.swing.JFrame.Type.UTILITY);
        this.setSize(WIDTH_NOTIFICATION, HEIGHT_NOTIFICATION);
        this.setUndecorated(true);
        this.setLayout(new GridBagLayout());
        this.setAlwaysOnTop(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1.0f;
        constraints.weighty = 1.0f;
        constraints.insets = INSETS_HEADER;
        constraints.fill = GridBagConstraints.BOTH;

        lblImage = new JLabel();

        this.add(lblImage, constraints);
        constraints.gridx++;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.NORTH;
        JButton boutonFermer = new JButton(new AbstractAction("x") {
            @Override
            public void actionPerformed(final ActionEvent e) {
                FrmNotification.this.closingEffect();
            }
        });
        boutonFermer.setMargin(MARGIN_BOUTON_FERMER);
        boutonFermer.setFocusable(false);
        this.add(boutonFermer, constraints);

        constraints.gridx = 0;
        constraints.gridy++;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.insets = INSETS_NOTIFICATION_MESSAGE;
        constraints.fill = GridBagConstraints.BOTH;
        lblTexte = new JLabel();
        this.add(lblTexte, constraints);

        Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
        Insets toolHeight = Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration());
        this.setLocation(scrSize.width - this.getWidth(), scrSize.height - toolHeight.bottom - this.getHeight());
    }

    public void affichierNotification(Message message) {
        Contact contact = facadeModele.getContact(message.getNumeroTelephone());
        if (contact.getNom() != TEXTE_VIDE) {
            lblImage.setText(contact.getNom());
        } else {
            lblImage.setText(Long.toString(message.getNumeroTelephone()));
        }
        if (contact.getImage() != null) {
            lblImage.setIcon(Formatage.redimensionnerImage(contact.getImage(), SIZE_IMAGE_CARREE, SIZE_IMAGE_CARREE));
        } else {
            lblImage.setIcon(Formatage.redimensionnerImage(FrmChatDesk.IMAGE_CONTACT_DEFAUT, SIZE_IMAGE_CARREE, SIZE_IMAGE_CARREE));
        }

        lblTexte.setText(message.getText());
        this.setVisible(true);
        new Thread(this).start();
    }

    @Override
    public void run() {
        for (int i = 0; i < TEMPS_ATTENTE_SEC_MAX; i++) {
            try {
                Thread.sleep(SECONDE_EN_MILLI_SEC);
            } catch (InterruptedException e) {
            }
        }
        closingEffect();
    }

    private void closingEffect() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double height = screenSize.getHeight();
        while (this.getLocation().y <= height) {
            this.setLocation(this.getLocation().x, this.getLocation().y + NOMBRE_PIXEL_TOMBER_FENETRE);
            try {
                Thread.sleep(VITESSE_TOMBER_FENETRE_MILLI_SEC);
            } catch (InterruptedException e) {
            }
        }
        this.dispose();
    }
}
