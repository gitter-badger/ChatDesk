package ca.qc.bdeb.gr1_420_P56_BB.vue;

import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.ConversationDTO;
import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.FacadeModele;
import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.ObservateurMessage;
import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.ObservateurErreur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * La fen�tre principale de l"application
 */
public class FrmChatDesk extends JFrame implements ObservateurMessage, ObservateurErreur {

    private static final String MESSAGE_ERREUR_CONNEXION_INTERROMPUE = "La connexion avec le serveur a été interrompue";

    public static final int WIDTH_NOTIFICATION = 300;

    public static final int HEIGHT_NOTIFICATION = 125;

    public static final int INSETS_NOTIFICATION_HEADER = 5;

    public static final Insets INSETS_NOTIFICATION_MESSAGE = new Insets(INSETS_NOTIFICATION_HEADER,
            INSETS_NOTIFICATION_HEADER, INSETS_NOTIFICATION_HEADER, INSETS_NOTIFICATION_HEADER);

    public static final Insets INSETS_HEADER = new Insets(INSETS_NOTIFICATION_HEADER,
            INSETS_NOTIFICATION_HEADER, INSETS_NOTIFICATION_HEADER, INSETS_NOTIFICATION_HEADER);

    public static final Insets MARGIN_BOUTON_FERMER = new Insets(1, 4, 1, 4);

    public static final ImageIcon ICON_NOTIFICATION = new ImageIcon("resources\\images\\chat_desk_icon_mini.png");
    /**
     * Le panneau qui affiche la conversation en cours
     */
    private PnlConversation pnlConversation;

    /**
     * Le panneau qui affiche toutes les conversation qu"� l"utilisateur
     */
    private PnlConversations pnlConversations;

    /**
     * La bar d"option/menu en haut de la fen�tre
     */
    private OptionBar optionBar;

    /**
     * Le séparateur du panneau d"une conversation et des conversations.
     */
    private JSeparator separateurVertical;

    /**
     * La facade du modèle
     */
    private final FacadeModele facadeModele;

    /**
     * Le scroll panel du panneau des conversations
     */
    private ScrollPanel scrollPanelConversations;

    /**
     * La fenetre de loading
     */
    private FrmLoading frmLoading;

    public FrmChatDesk(FacadeModele facadeModele) {
        this.facadeModele = facadeModele;
        initialiserFenetre();
        this.facadeModele.ajouterObservateur(this);
        this.facadeModele.ajouterObservateurErreur(this);
        FrmNotification notification = new FrmNotification(null,null);
        frmLoading = new FrmLoading(this);
        frmLoading.commencerChargement();
    }

    /**
     * Initialise la fen�tre de l"application et toutes ses composantes
     */
    private void initialiserFenetre() {
        this.setLayout(null);
        this.setUndecorated(true);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        double POURCENTAGE_ECRAN_GRANDEUR_FENETRE = 0.8;
        int dimensionAppX = (int) (dimension.width * POURCENTAGE_ECRAN_GRANDEUR_FENETRE);
        int dimensionAppY = (int) (dimension.height * POURCENTAGE_ECRAN_GRANDEUR_FENETRE);

        this.setSize(dimensionAppX, dimensionAppY);
        int VALEUR_CENTRER = 2;
        this.setLocation(dimension.width / VALEUR_CENTRER - dimensionAppX / VALEUR_CENTRER,
                dimension.height / VALEUR_CENTRER - dimensionAppY / VALEUR_CENTRER);

        optionBar = new OptionBar(this);

        pnlConversations = new PnlConversations(this, facadeModele);
        double POURCENTAGE_ECRAN_GRANDEUR_PNL_CONVERSATION = 0.3;
        scrollPanelConversations = new ScrollPanel(pnlConversations,
                (int) (this.getWidth() * POURCENTAGE_ECRAN_GRANDEUR_PNL_CONVERSATION),
                this.getHeight() - optionBar.getHeight());
        scrollPanelConversations.setLocation(0, optionBar.getHeight());

        separateurVertical = new JSeparator(SwingConstants.VERTICAL);

        pnlConversation = new PnlConversation(facadeModele);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                changerGrandeurComposant();
            }
        });

        this.add(optionBar);
        this.add(scrollPanelConversations);
        this.add(separateurVertical);
        this.add(pnlConversation);
    }


    public void ajouterConversation() {
        String numeroTelephone = JOptionPane.showInputDialog("Entrez le numéro du contact");
        this.ouvrirConversation(new ConversationDTO(null, Long.parseLong(numeroTelephone)));
    }

    /**
     * Changement de grandeur de la fen�tre
     */
    private void changerGrandeurComposant() {
        optionBar.initialiserPanel();
        pnlConversations.initialiserPanel();

        int LARGEUR_SEPARATEUR = 1;
        separateurVertical.setSize(LARGEUR_SEPARATEUR, this.getHeight());
        separateurVertical.setLocation(scrollPanelConversations.getWidth(), optionBar.getHeight());

        pnlConversation.setSize(this.getWidth() - LARGEUR_SEPARATEUR - scrollPanelConversations.getWidth(),
                this.getHeight() - optionBar.getHeight());
        pnlConversation.setLocation(scrollPanelConversations.getWidth() + LARGEUR_SEPARATEUR, optionBar.getHeight());
        pnlConversation.initialiserPanel();
    }

    /**
     * Ouvrir une couversation dans le panneau conversation
     *
     * @param conversationDTO La conversation � ouvrir
     */
    public void ouvrirConversation(ConversationDTO conversationDTO) {
        pnlConversation.changerConversation(conversationDTO.getNumeroTelephone());
    }

    /**
     * R�cup�rer le panel de conversation pour les tests.
     *
     * @return Le panel conversation
     */
    public PnlConversation getPnlConversation() {
        return pnlConversation;
    }

    /**
     * Permet d"arr�ter le programme
     */
    public void arreterProgramme() {
        this.dispose();
        facadeModele.arreterProgramme();
    }

    public void changerCouleurBulleEnvoye(Color couleur) {
        pnlConversation.getPnlBulles().setCouleurBullesEnvoyees(couleur);
    }

    public FacadeModele getFacadeModele() {
        return facadeModele;
    }

    @Override
    public void receptionMessage(long num) {
        pnlConversation.mettreAJour(num);
        pnlConversations.mettreAJour();
        FrmNotification notification = new FrmNotification(facadeModele.getContact(num).getNom(), facadeModele.getConversationDTO(num).getLastMessage().getText());
        scrollPanelConversations.mettreAJour();
    }




    @Override
    public void finReceptionConnexionInitiale() {
        pnlConversations.mettreAJour();
        frmLoading.arreterChargement();
    }

    @Override
    public void aviserErreur() {
        new FrmConnexion();
        this.dispose();
        JOptionPane.showMessageDialog(this, MESSAGE_ERREUR_CONNEXION_INTERROMPUE);
    }

    public void changerCouleurBulleRecue(Color background) {
        pnlConversation.getPnlBulles().setCouleurBullesRecues(background);
    }
}
