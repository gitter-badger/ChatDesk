package ca.qc.bdeb.gr1_420_P56_BB.vue;


import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.ContactsTest;
import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.ConversationDTO;
import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.FacadeModele;
import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.ContactPourQueCaFonctionneDTO;

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
     * Ratio du panneau selon la fenêtre principale
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
     * La fenêtre principale du programme
     */
    private final FrmChatDesk fenetrePrincipale;

    /**
     * La facade du modèle pour accèder au modèle
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
     * La dimension du label du dernier message qu'il soit reçu ou envoyé
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
     * Le nombre de conversation qui sont affiché sur le panneau qui contient toutes les conversations
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
        initialiserPanel();
        this.setDoubleBuffered(true);
    }

    /**
     * Initialise le panneau
     */
    public void initialiserPanel() {
        this.setLayout(null);
        initialiserGrandeursComposants();
        initialiserContacts();
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


    private void ajouterContact(ContactPourQueCaFonctionneDTO contactPourQueCaFonctionneDTO) {
        JPanel pnlConversation = new JPanel();
        initialiserPanneauContact(pnlConversation, contactPourQueCaFonctionneDTO);
        initialiserPanneauNom(pnlConversation, contactPourQueCaFonctionneDTO);
        this.add(pnlConversation);
    }

    /**
     * Initialise le panneau d'une conversation
     *  @param pnlConversation Le panneau ? initialiser
     * @param contactPourQueCaFonctionneDTO Une conversation
     */
    private void initialiserPanneauContact(JPanel pnlConversation, ContactPourQueCaFonctionneDTO contactPourQueCaFonctionneDTO) {
        pnlConversation.setLayout(null);
        pnlConversation.setLocation(0, hauteurPnlConversationY * conversationCount);
        pnlConversation.setSize(longueurPnlConversationX, hauteurPnlConversationY);
        pnlConversation.setBackground(Color.RED);
        pnlConversation.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == CLICK_COUNT) {
                    fenetrePrincipale.ouvrirConversation(new ConversationDTO(null, contactPourQueCaFonctionneDTO.getNumeroTelephone()));
                }
            }
        });
    }

    /**
     * Initialise le nom de la personne avec qui la conversation a lieu
     *  @param pnlConversation Le panneau d'une conversation
     * @param conversationDTO Une conversation
     */
    private void initialiserPanneauNom(JPanel pnlConversation, ContactPourQueCaFonctionneDTO conversationDTO) {
        //JLabel nom = new JLabel(facadeModele.getContact(conversationDTO.getNumeroTelephone()).getNom());
        JLabel nom = new JLabel(facadeModele.getContact(conversationDTO.getNumeroTelephone()).getNom());
        nom.setFont(new Font(nom.getFont().getFontName(), Font.BOLD, (int) dimLblNom.getHeight()));
        nom.setSize(dimLblNom);
        nom.setLocation(borderVideSize, (int) (pnlConversation.getHeight() / 2 - (dimLblNom.getHeight() +
                dimLblDernierMessage.getHeight()) / 2));
        pnlConversation.add(nom);
    }





    /**
     * Mettre ? jour les contacts
     */
    public void initialiserContacts() {
        this.removeAll();
        conversationCount = 0;
        for (ContactPourQueCaFonctionneDTO contactPourQueCaFonctionneDTO : ContactsTest.asList()) {
            ajouterContact(contactPourQueCaFonctionneDTO);
            conversationCount++;
        }
        this.setBounds(0,0,this.getWidth(), hauteurPnlConversationY * conversationCount);
        this.repaint();
    }



}
