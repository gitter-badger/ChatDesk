package ca.qc.bdeb.gr1_420_P56_BB.vue.selectionAppareils;

import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.Appareil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by 1372883 on 2015-11-03.
 */
public class PanneauDetail extends JPanel implements Rappeleur {

    private static final Insets INSETS_LABELS_INVITE = new Insets(5, 5, 15, 15);
    private static final GridBagConstraints CONSTRAINTS = new GridBagConstraints();

    /**
     * Affectation des valeurs de la constante de contraintes de facon statique puisqu'ils ne peuvent etre tous pass�
     * dans un constructeur
     */
    static {
        CONSTRAINTS.gridx = 0;
        CONSTRAINTS.gridy = 0;
        CONSTRAINTS.weightx = 1;
        CONSTRAINTS.weighty = 1;
        CONSTRAINTS.anchor = GridBagConstraints.WEST;
        CONSTRAINTS.insets = INSETS_LABELS_INVITE;
    }

    private static final String INVITE_NOM = "Nom :";
    private static final String INVITE_ID = "Identifiant :";
    private static final String INVITE_IP = "Adresse IP :";
    private static final String INVITE_LOCATION = "Emplacement :";
    private static final String STRING_BOUTON_CONNECTER = "Se connecter";

    private Appareil appareil;
    private JLabel lblNom;
    private JLabel lblInviteNom;
    private JLabel lblIp;
    private JLabel lblInviteIp;
    private JLabel lblId;
    private JLabel lblInviteId;
    private JLabel lblLocation;
    private JLabel lblInviteLocation;
    private JPanel pnlInvite;
    private JPanel pnlValeurs;
    private JButton btnConnecter;

    private Rappeleur rappeleur;

    public PanneauDetail(Rappeleur rappeleur) {
        this.rappeleur = rappeleur;

        this.setLayout(new GridBagLayout());

        this.lblNom = new JLabel();
        this.lblIp = new JLabel();
        this.lblId = new JLabel();
        this.lblLocation = new JLabel();

        this.lblInviteLocation = new JLabel(INVITE_LOCATION);
        this.lblInviteIp = new JLabel(INVITE_IP);
        this.lblInviteId = new JLabel(INVITE_ID);
        this.lblInviteNom = new JLabel(INVITE_NOM);

        initialiserPanelInvites();
        initialiserPanelValeurs();
        initialiserBtnConnexion();
    }

    private void initialiserBtnConnexion() {
        this.btnConnecter = new JButton(STRING_BOUTON_CONNECTER);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridwidth = 2;
        constraints.insets = INSETS_LABELS_INVITE;

        btnConnecter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                rappeleur.rappeler(appareil);
            }
        });

        this.add(btnConnecter, constraints);
    }

    private void initialiserPanelInvites() {
        pnlInvite = new JPanel(new GridBagLayout());

        ajouterLblsAPanneau(pnlInvite, lblInviteNom, lblInviteId, lblInviteIp, lblInviteLocation);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.insets = INSETS_LABELS_INVITE;

        this.add(pnlInvite, constraints);
    }

    private void initialiserPanelValeurs() {
        pnlValeurs = new JPanel(new GridBagLayout());

        ajouterLblsAPanneau(pnlValeurs, lblNom, lblId, lblIp, lblLocation);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.insets = INSETS_LABELS_INVITE;

        this.add(pnlValeurs, constraints);
    }

    private void ajouterLblsAPanneau(JPanel panneau, JLabel... tabLabels) {
        for (JLabel label : tabLabels) {
            panneau.add(label, CONSTRAINTS);
            CONSTRAINTS.gridy++;
        }
        CONSTRAINTS.gridy = 0;
    }

    @Override
    public void rappeler(Appareil appareil) {
        this.appareil = appareil;
        mettreElementsAJour();
    }

    private void mettreElementsAJour() {
        if (appareil != null) {
            this.lblNom.setText(appareil.getNom());
            this.lblId.setText(Integer.toString(appareil.getId()));
            this.lblIp.setText(appareil.getIp());
            this.lblLocation.setText(appareil.getLocation());
            this.revalidate();
        }
    }
}
