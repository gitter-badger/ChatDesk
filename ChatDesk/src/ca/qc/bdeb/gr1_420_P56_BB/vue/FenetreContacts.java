package ca.qc.bdeb.gr1_420_P56_BB.vue;


import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.ConversationDTO;
import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.FacadeModele;
import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.Formatage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Panneau qui affiche toutes les conversations que l'utilisateur ?. Le dernier message recu/envoy?
 * pour chaque conversation et la date de reception/envoie du dernier message pour chaque conversation
 */
class FenetreContacts extends JPanel {

    /**
     * Ratio du panneau selon la fen�tre principale
     */
    private static final int LONGUEUR_SELON_FENETRE_PRINCIPALE = 7;

    /**
     * Ratio de la longueur du label du nom selon la longueur du panneau d'une conversation
     */
    private static final double POURCENTAGE_LONGUEUR_LBL_NOM = 0.6;

    /**
     * Ratio de la hauteur du label du nom selon la hauteur du panneau d'une conversation
     */
    private static final double POURCENTAGE_HAUTEUR_LBL_NOM = 0.2;

    /**
     * Ratio de la longueur du label du dernier message selon la longueur du panneau d'une conversation
     */
    private static final double POURCENTAGE_LONGUEUR_LBL_DERNIER_MSG = 0.6;

    /**
     * Ratio de la hauteur du label du dernier message selon la hauteur du panneau d'une conversation
     */
    private static final double POURCENTAGE_HAUTEUR_LBL_DERNIER_MSG = 0.15;

    /**
     * Ratio de la longueur de label de la date selon la longueur du panneau d'une conversation
     */
    private static final double POURCENTAGE_LONGUEUR_LBL_DATE = 0.2;

    /**
     * Ratio de la hauteur du label de la date selon la hauteur du panneau d'une conversation
     */
    private static final double POURCENTAGE_HAUTEUR_LBL_DATE = 0.13;

    /**
     * Ratio du contour vide autour du panneau d'une conversation
     */
    private static final double POURCENTAGE_COUTOUR_VIDE = 0.15;

    /**
     * Nombre de click maximum pour ouvrir une conversation
     */
    private static final int CLICK_COUNT = 1;

    /**
     * La fen�tre principale du programme
     */
    private final FrmChatDesk fenetrePrincipale;

    /**
     * La facade du mod�le pour acc�der au mod�le
     */
    private final FacadeModele facadeModele;

    /**
     * La longueur du panneau d'une conversation
     */
    private int longueurPnlConversationX;

    /**
     * La hauteur du panneau d'une conversation
     */
    private int hauteurPnlConversationY;

    /**
     * La dimension du label du nom du contact
     */
    private Dimension dimLblNom;

    /**
     * La dimension du label du dernier message qu'il soit re�u ou envoy�
     */
    private Dimension dimLblDernierMessage;

    /**
     * La dimension du lable de la date
     */
    private Dimension dimLblDate;

    /**
     * Grandeur du contour d'une conversation qui est vide
     */
    private int borderVideSize;

    /**
     * Le nombre de conversation qui sont affich� sur le panneau qui contient toutes les conversations
     */
    private int conversationCount;

    /**
     * Constructeur qui cr?e et initialise le panneau des conversations
     *
     * @param fenetrePrincipale La fenetre principale
     * @param facadeModele      Le gestionnaire de conversation
     */
    public FenetreContacts(FrmChatDesk fenetrePrincipale, FacadeModele facadeModele) {
        this.facadeModele = facadeModele;
        this.fenetrePrincipale = fenetrePrincipale;
        this.setDoubleBuffered(true);
    }

    /**
     * Initialise le panneau
     */
    public void initialiserPanel() {
        this.setLayout(null);
        initialiserGrandeursComposants();
        mettreAJour();
    }

    /**
     * Initialise les grandeurs des composants selon la grandeur de la fen?tre du programme
     */
    private void initialiserGrandeursComposants() {
        longueurPnlConversationX = this.getWidth();
        hauteurPnlConversationY = fenetrePrincipale.getHeight() / LONGUEUR_SELON_FENETRE_PRINCIPALE;

        dimLblNom = new Dimension((int)(longueurPnlConversationX * POURCENTAGE_LONGUEUR_LBL_NOM),
                (int)(hauteurPnlConversationY * POURCENTAGE_HAUTEUR_LBL_NOM));

        dimLblDernierMessage = new Dimension((int)(longueurPnlConversationX * POURCENTAGE_LONGUEUR_LBL_DERNIER_MSG),
                (int)(hauteurPnlConversationY * POURCENTAGE_HAUTEUR_LBL_DERNIER_MSG));


        dimLblDate = new Dimension((int)(longueurPnlConversationX * POURCENTAGE_LONGUEUR_LBL_DATE),
                (int)(hauteurPnlConversationY * POURCENTAGE_HAUTEUR_LBL_DATE));

        borderVideSize = (int)(hauteurPnlConversationY * POURCENTAGE_COUTOUR_VIDE);
    }

    /**
     * Ajoute une conversation au panneau
     *
     * @param conversationDTO La conversation ? ajouter
     */
    private void ajouterConversation(ConversationDTO conversationDTO) {
        JPanel pnlConversation = new JPanel();
        initialiserPanneauConversation(pnlConversation, conversationDTO);
        initialiserPanneauConversationNom(pnlConversation, conversationDTO);
        this.add(pnlConversation);
    }

    /**
     * Initialise le panneau d'une conversation
     *
     * @param pnlConversation Le panneau ? initialiser
     * @param conversationDTO Une conversation
     */
    private void initialiserPanneauConversation(JPanel pnlConversation, ConversationDTO conversationDTO) {
        pnlConversation.setLayout(null);
        pnlConversation.setLocation(0, hauteurPnlConversationY * conversationCount);
        pnlConversation.setSize(longueurPnlConversationX, hauteurPnlConversationY);
        pnlConversation.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == CLICK_COUNT) {
                    fenetrePrincipale.ouvrirConversation(conversationDTO);
                }
            }
        });
    }

    /**
     * Initialise le nom de la personne avec qui la conversation a lieu
     *
     * @param pnlConversation Le panneau d'une conversation
     * @param conversationDTO Une conversation
     */
    private void initialiserPanneauConversationNom(JPanel pnlConversation, ConversationDTO conversationDTO) {
        JLabel nom = new JLabel(facadeModele.getContact(conversationDTO.getNumeroTelephone()).getNom());
        nom.setFont(new Font(nom.getFont().getFontName(), Font.BOLD, (int) dimLblNom.getHeight()));
        nom.setSize(dimLblNom);
        nom.setLocation(borderVideSize, (int)(pnlConversation.getHeight() / 2 - (dimLblNom.getHeight() +
                dimLblDernierMessage.getHeight()) / 2));
        pnlConversation.add(nom);
    }

    /**
     * Initialise le dernier message recu ou envoy? de la conversation
     *
     * @param pnlConversation Le panneau d'une conversation
     * @param conversationDTO Une conversation
     */
    private void initialiserPanneauConversationDernierMessage(JPanel pnlConversation, ConversationDTO conversationDTO) {
        String texteDernierMessage = "";
        if (conversationDTO.getLastMessage() != null) {
            if (conversationDTO.getLastMessage().isEnvoyer()) {
                /*Affich? dans le cas o? le dernier message est envoy? par l'utilisateur*/
                String ENVOYEUR = "Vous : ";
                texteDernierMessage = ENVOYEUR + conversationDTO.getLastMessage().getText();
            } else {
                texteDernierMessage = conversationDTO.getLastMessage().getText();
            }
        }

        JLabel dernierMessage = new JLabel(texteDernierMessage);
        dernierMessage.setFont(new Font(dernierMessage.getFont().getFontName(), Font.PLAIN,
                (int) dimLblDernierMessage.getHeight()));
        dernierMessage.setSize(dimLblDernierMessage);
        dernierMessage.setLocation(borderVideSize, (int) (pnlConversation.getHeight() / 2 -
                (dimLblNom.getHeight() + dimLblDernierMessage.getHeight()) / 2 + dimLblNom.getHeight()));
        pnlConversation.add(dernierMessage);
    }

    /**
     * Initialiser la date du dernier message recu/envoy?
     *
     * @param pnlConversation Le panneau d'une conversation
     * @param conversationDTO Une conversation
     */
    private void initialiserPanneauConversationDate(JPanel pnlConversation, ConversationDTO conversationDTO) {
        JLabel dateMsg = new JLabel(Formatage.formatageDate(conversationDTO.getLastMessage().getDate()));
        dateMsg.setFont(new Font(dateMsg.getFont().getFontName(), Font.PLAIN, (int) dimLblDate.getHeight()));
        dateMsg.setSize(dimLblDate);
        dateMsg.setLocation(pnlConversation.getWidth() - dateMsg.getWidth() - borderVideSize,
                pnlConversation.getHeight() / 2 - dateMsg.getHeight() / 2);
        pnlConversation.add(dateMsg);
    }

    /**
     * Mettre ? jour les conversations
     */
    public void mettreAJour() {
        this.removeAll();
        conversationCount = 0;
        for (ConversationDTO conversationDTO : facadeModele.getConversations()) {
            ajouterConversation(conversationDTO);
            conversationCount++;
        }

        this.setPreferredSize(new Dimension(this.getWidth(), hauteurPnlConversationY * conversationCount));
        this.repaint();
    }


    public FacadeModele getFacadeModele() {
        return facadeModele;
    }
}
