package ca.qc.bdeb.gr1_420_P56_BB.vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by 47 on 2015-11-25.
 */
public class FrmNotification {
    public static final int WIDTH_NOTIFICATION = 300;

    public static final int HEIGHT_NOTIFICATION = 125;

    public static final int INSETS_NOTIFICATION_HEADER = 5;

    public static final Insets INSETS_NOTIFICATION_MESSAGE = new Insets(INSETS_NOTIFICATION_HEADER,
            INSETS_NOTIFICATION_HEADER, INSETS_NOTIFICATION_HEADER, INSETS_NOTIFICATION_HEADER);

    public static final Insets INSETS_HEADER = new Insets(INSETS_NOTIFICATION_HEADER,
            INSETS_NOTIFICATION_HEADER, INSETS_NOTIFICATION_HEADER, INSETS_NOTIFICATION_HEADER);

    public static final Insets MARGIN_BOUTON_FERMER = new Insets(1, 4, 1, 4);

    public static final ImageIcon ICON_NOTIFICATION = new ImageIcon("resources\\images\\chat_desk_icon_mini.png");
    String nom;
    String recumessage;
    public FrmNotification(String nom, String messageRecu) {
        this.nom = nom;
        String message = "Nouveau message" ;
        if (nom != null){
            message += " de " + nom;
        }
        String header = "";
        if (messageRecu != null){
            header = messageRecu;
        }
        JFrame frame = new JFrame();
        frame.setSize(WIDTH_NOTIFICATION, HEIGHT_NOTIFICATION);
        frame.setUndecorated(true);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1.0f;
        constraints.weighty = 1.0f;
        constraints.insets = INSETS_HEADER;
        constraints.fill = GridBagConstraints.BOTH;
        JLabel headingLabel = new JLabel(header);
        headingLabel.setIcon(ICON_NOTIFICATION);
        headingLabel.setOpaque(false);
        frame.add(headingLabel, constraints);
        constraints.gridx++;
        constraints.weightx = 0f;
        constraints.weighty = 0f;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.NORTH;
        JButton boutonFermer = new JButton(new AbstractAction("x") {
            @Override
            public void actionPerformed(final ActionEvent e) {
                frame.dispose();
            }
        });
        boutonFermer.setMargin(MARGIN_BOUTON_FERMER);
        boutonFermer.setFocusable(false);
        frame.add(boutonFermer, constraints);
        constraints.gridx = 0;
        constraints.gridy++;
        constraints.weightx = 1.0f;
        constraints.weighty = 1.0f;
        constraints.insets = INSETS_NOTIFICATION_MESSAGE;
        constraints.fill = GridBagConstraints.BOTH;
        JLabel messageLabel = new JLabel("<HTML>" + message);
        frame.add(messageLabel, constraints);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
        Insets toolHeight = Toolkit.getDefaultToolkit().getScreenInsets(frame.getGraphicsConfiguration());
        frame.setLocation(scrSize.width - frame.getWidth(), scrSize.height - toolHeight.bottom - frame.getHeight());
    }
}
