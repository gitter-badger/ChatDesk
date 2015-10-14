package ca.qc.bdeb.gr1_420_P56_BB.vue;

import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.FacadeModele;
import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.Message;
import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.Formatage;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Date;

/**
 * Panneau qui contient la conversation courante d'un utilisateur.
 */
class PnlConversation extends JPanel {

    /**
     * Emplacement de l'image du bouton pour envoyer
     */
    private static final ImageIcon IMAGE_SEND_BUTTON = new ImageIcon("resources/images/send_button.png");

    /**
     * Nom de la police dans le panel
     */
    private static final String NOM_POLICE = "Segoe UI Black";

    /**
     * Taille du texte
     */
    private static final int GRANDEUR_TEXTE = 18;

    /**
     * Font dans le panel
     */
    private final Font font = new Font(NOM_POLICE, Font.BOLD, GRANDEUR_TEXTE);

    /**
     * Variable pour exprimer l'abscence de numéro de téléphone
     */
    private final int AUCUN_NUMERO_TELEPHONE = -1;

    /**
     * Panneau contentant l'information de la conversation
     */
    private final JPanel pnlInfoConvo;
    /**
     * Panneau pour l'envoie
     */
    private final JPanel pnlEnvoie;

    /**
     * Panneau contenant les bulles
     */
    private final PnlBulles pnlBulles;

    /**
     * Le champ d'écriture d'un message
     */
    private JTextArea champAjoutMessage;

    /**
     * Bouton pour envoyer un message
     */
    private JButton btnEnvoyer;

    /**
     * Texte vide... jvoix pas quoi dire de plus
     */
    private final String TEXTE_VIDE = "";

    /**
     * Permet la communication
     */
    private final FacadeModele facadeModele;

    /**
     * Nom du contact
     */
    private final JLabel nom;

    /**
     * Numéro de téléphone dans la liste de conversation
     */
    private long numeroTelephone;

    /**
     * Le scrolling dans les fenetre
     */
    private ScrollPanel scrollPanelConversation;

    public PnlConversation(FacadeModele facadeModele) {
        this.facadeModele = facadeModele;
        this.setLayout(null);
        this.setDoubleBuffered(true);

        numeroTelephone = AUCUN_NUMERO_TELEPHONE;
        pnlBulles = new PnlBulles();
        pnlInfoConvo = new JPanel();
        pnlEnvoie = new JPanel();
        nom = new JLabel();
    }

    /**
     * Initialise le panneau et tous ses composants.
     */
    public void initialiserPanel() {
        this.removeAll();

        initialiserPnlInfoConvo();

        initialiserPnlEnvoie();

        initialiserPnlBulles();

        this.add(pnlInfoConvo);
        this.add(scrollPanelConversation);
        this.add(pnlEnvoie);
        this.repaint();
    }

    /**
     * Initialise les champs de conversation
     */
    private void initialiserPnlInfoConvo() {
        pnlInfoConvo.setLocation(0, 0);
        int PNL_INFO_CONVO_HAUTEUR = 60;
        pnlInfoConvo.setSize(this.getWidth(), PNL_INFO_CONVO_HAUTEUR);
        pnlInfoConvo.setLayout(null);
        nom.setFont(font);
        pnlInfoConvo.add(nom);
    }

    /**
     * Initialise les panneaux d'envoies
     */
    private void initialiserPnlEnvoie() {
        pnlEnvoie.removeAll();
        int PNL_ENVOIE_HAUTEUR = 140;
        pnlEnvoie.setSize(this.getWidth(), PNL_ENVOIE_HAUTEUR);
        pnlEnvoie.setLocation(0, this.getHeight() - pnlEnvoie.getHeight());
        pnlEnvoie.setLayout(null);

        initialiserChampAjoutMessage();
        initialiserBtnEnvoyer();

        pnlEnvoie.add(btnEnvoyer);
        pnlEnvoie.add(champAjoutMessage);
    }

    /**
     * Initialise le champ d'ajout de message
     */
    private void initialiserChampAjoutMessage() {
        champAjoutMessage = new JTextArea();
        champAjoutMessage.setSize(pnlEnvoie.getWidth(), pnlEnvoie.getHeight());
        champAjoutMessage.setLocation(0, 0);
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        int GRANDEUR_CONTOUR_CHAMP_AJOUT_MESSAGE = 10;
        champAjoutMessage.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(GRANDEUR_CONTOUR_CHAMP_AJOUT_MESSAGE,
                        GRANDEUR_CONTOUR_CHAMP_AJOUT_MESSAGE, GRANDEUR_CONTOUR_CHAMP_AJOUT_MESSAGE,
                        GRANDEUR_CONTOUR_CHAMP_AJOUT_MESSAGE)));
    }

    /**
     * Initialise le bouton envoyer
     */
    private void initialiserBtnEnvoyer() {
        btnEnvoyer = new JButton();
        double POURCENTAGE_LONGUEUR_IMG_ENVOYER = 0.08;
        int widthImage = (int) (pnlEnvoie.getWidth() * POURCENTAGE_LONGUEUR_IMG_ENVOYER);
        double POURCENTAGE_HAUTEUR_IMG_ENVOYER = 0.25;
        int heightImage = (int) (pnlEnvoie.getHeight() * POURCENTAGE_HAUTEUR_IMG_ENVOYER);
        btnEnvoyer.setBorderPainted(false);
        btnEnvoyer.setContentAreaFilled(false);
        btnEnvoyer.setFocusPainted(false);
        btnEnvoyer.setOpaque(false);
        btnEnvoyer.setIcon(Formatage.redimensionnerImage(IMAGE_SEND_BUTTON, widthImage, heightImage));
        btnEnvoyer.setSize(widthImage, heightImage);
        btnEnvoyer.setLocation(pnlEnvoie.getWidth() - widthImage, pnlEnvoie.getHeight() - heightImage);
        btnEnvoyer.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                envoyerMessage();
            }
        });
        btnEnvoyer.setEnabled(false);
    }

    /**
     * Initialise le panneau avec des bulles
     */
    private void initialiserPnlBulles() {
        pnlBulles.setLocation(0, pnlInfoConvo.getHeight());
        scrollPanelConversation = new ScrollPanel(pnlBulles, this.getWidth(), this.getHeight() -
                pnlInfoConvo.getHeight() - pnlEnvoie.getHeight());
        scrollPanelConversation.setLocation(0, pnlInfoConvo.getHeight());
    }

    /**
     * Permet le changement de conversation
     *
     * @param numeroTelephone Le num�ro de t�l�phone de la nouvelle conversation
     */
    public void changerConversation(long numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
        try {
            nom.setText(facadeModele.getContact(numeroTelephone).getNom());
        } catch (NullPointerException ex) {
            nom.setText(TEXTE_VIDE);
        }
        Dimension dimensionNom = Formatage.calculerDimensionString(nom.getText(), font);
        nom.setSize(dimensionNom);
        int VALEUR_CENTRER = 2;
        nom.setLocation(pnlInfoConvo.getWidth() / VALEUR_CENTRER - nom.getWidth() / VALEUR_CENTRER,
                pnlInfoConvo.getHeight() / VALEUR_CENTRER - nom.getHeight() / VALEUR_CENTRER);
        if (btnEnvoyer != null) {
            btnEnvoyer.setEnabled(true);
        }
        pnlInfoConvo.repaint();

        mettreAJour(numeroTelephone);
    }

    /**
     * Envoyer un message
     */
    private void envoyerMessage() {
        if (!champAjoutMessage.getText().equals(TEXTE_VIDE) && numeroTelephone != AUCUN_NUMERO_TELEPHONE) {
            facadeModele.envoyerMessage(numeroTelephone,
                    new Message(champAjoutMessage.getText(), new Date(System.currentTimeMillis()), true));
            champAjoutMessage.setText(TEXTE_VIDE);
        }
    }

    /**
     * Mettre � jour les messages de la conversation si le message re�u fait partie de la conversation.
     *
     * @param numeroTelephone Le num�ro de t�l�phone du nouveau message re�u
     */
    public void mettreAJour(long numeroTelephone) {
        if (this.numeroTelephone == numeroTelephone) {
            pnlBulles.mettreAJour(facadeModele.getConversationDTO(this.numeroTelephone));
            if (scrollPanelConversation != null) {
                scrollPanelConversation.mettreAJour();
            }
        }
    }

    public FacadeModele getFacadeModele() {
        return facadeModele;
    }

    public long getNumeroTelephone() {
        return numeroTelephone;
    }
}
