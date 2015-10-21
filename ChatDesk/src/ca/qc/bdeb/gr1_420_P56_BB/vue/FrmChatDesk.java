package ca.qc.bdeb.gr1_420_P56_BB.vue;

import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.ConversationDTO;
import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.FacadeModele;
import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.Observateur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * La fen�tre principale de l'application
 */
class FrmChatDesk extends JFrame implements Observateur {

    /**
     * Le panneau qui affiche la conversation en cours
     */
    private PnlConversation pnlConversation;

    /**
     * Le panneau qui affiche toutes les conversation qu'� l'utilisateur
     */
    private PnlConversations pnlConversations;

    /**
     * La bar d'option/menu en haut de la fen�tre
     */
    private OptionBar optionBar;

    /**
     * Le séparateur du panneau d'une conversation et des conversations.
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

    public FrmChatDesk(FacadeModele facadeModele) {
        this.facadeModele = facadeModele;
        initialiserFenetre();
        facadeModele.ajouterObservateur(this);
    }

    /**
     * Initialise la fen�tre de l'application et toutes ses composantes
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

    @Override
    public void changementEtat(long num) {
        pnlConversation.mettreAJour(num);
        pnlConversations.mettreAJour();
        scrollPanelConversations.mettreAJour();
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
     * Permet d'arr�ter le programme
     */
    public void arreterProgramme(){
        this.dispose();
        facadeModele.arreterProgramme();
    }
}
