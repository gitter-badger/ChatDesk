package ca.qc.bdeb.gr1_420_P56_BB.vue;


import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.ContactsTest;
import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.ConversationDTO;
import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.FacadeModele;

import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.ContactDTO;
import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.Formatage;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Panneau qui affiche toutes les contacts que l'utilisateur ?. Le dernier message recu/envoy?
 * pour chaque contact et la date de reception/envoie du dernier message pour chaque contact
 */
class FrmContacts extends JPanel {

    /**
     * Ratio du panneau selon la fenêtre principale
     */
    private static final int LONGUEUR_SELON_FENETRE_PRINCIPALE = 7;

    /**
     * Ratio de la longueur du label du nom selon la longueur du panneau d'une contact
     */
    private static final double POURCENTAGE_LONGUEUR_LBL_NOM = 0.6;

    /**
     * Ratio de la hauteur du label du nom selon la hauteur du panneau d'une contact
     */
    private static final double POURCENTAGE_HAUTEUR_LBL_NOM = 0.2;

    /**
     * Ratio de la longueur du label du dernier message selon la longueur du panneau d'une contact
     */
    private static final double POURCENTAGE_LONGUEUR_LBL_DERNIER_MSG = 0.6;

    /**
     * Ratio de la hauteur du label du dernier message selon la hauteur du panneau d'une contact
     */
    private static final double POURCENTAGE_HAUTEUR_LBL_DERNIER_MSG = 0.15;

    /**
     * Ratio de la longueur de label de la date selon la longueur du panneau d'une contact
     */
    private static final double POURCENTAGE_LONGUEUR_LBL_DATE = 0.2;

    /**
     * Ratio de la hauteur du label de la date selon la hauteur du panneau d'une contact
     */
    private static final double POURCENTAGE_HAUTEUR_LBL_DATE = 0.13;

    /**
     * Ratio du contour vide autour du panneau d'une contact
     */
    private static final double POURCENTAGE_COUTOUR_VIDE = 0.15;

    /**
     * Nombre de click maximum pour ouvrir une contact
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
     * La longueur du panneau d'une contact
     */
    private int longueurPnlcontactX;

    /**
     * La hauteur du panneau d'une contact
     */
    private int hauteurPnlcontactY;

    /**
     * La dimension du label du nom du contact
     */
    private Dimension dimLblNom;

    /**
     * La dimension du label du dernier message qu'il soit reçu ou envoyé
     */
    private Dimension dimLblDernierMessage;

    /**
     * Grandeur du contour d'une contact qui est vide
     */
    private int borderVideSize;

    /**
     * Le nombre de contact qui sont affiché sur le panneau qui contient toutes les contacts
     */
    private int contactCount;

    /**
     * longueur du plus grand contact
     */
    private int longuerMaxContact;

    private JFrame jFrameContacts;

    /**
     * Constructeur qui cr?e et initialise le panneau des contacts
     *
     * @param fenetrePrincipale La fenetre principale
     * @param facadeModele      Le gestionnaire de contact
     */
    public FrmContacts(FrmChatDesk fenetrePrincipale, FacadeModele facadeModele, JFrame jFrameContacts) {
        longuerMaxContact = 0;
        this.facadeModele = facadeModele;
        this.fenetrePrincipale = fenetrePrincipale;
        this.jFrameContacts = jFrameContacts;
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

        longueurPnlcontactX = 500;
        hauteurPnlcontactY = fenetrePrincipale.getHeight() / LONGUEUR_SELON_FENETRE_PRINCIPALE;

        dimLblNom = new Dimension((int) (longueurPnlcontactX * POURCENTAGE_LONGUEUR_LBL_NOM),
                (int) (hauteurPnlcontactY * POURCENTAGE_HAUTEUR_LBL_NOM));

        dimLblDernierMessage = new Dimension((int) (longueurPnlcontactX * POURCENTAGE_LONGUEUR_LBL_DERNIER_MSG),
                (int) (hauteurPnlcontactY * POURCENTAGE_HAUTEUR_LBL_DERNIER_MSG));


        borderVideSize = (int) (hauteurPnlcontactY * POURCENTAGE_COUTOUR_VIDE);
    }


    private void ajouterContact(ContactDTO contactDTO) {
        JPanel pnlcontact = new JPanel();
        initialiserPanneauContact(pnlcontact, contactDTO);
        initialiserPanneaucontactNom(pnlcontact, contactDTO);
        this.add(pnlcontact);
    }

    /**
     * Initialise le panneau d'une contact
     *
     * @param pnlcontact Le panneau ? initialiser
     * @param contactDTO Une contact
     */
    private void initialiserPanneauContact(JPanel pnlcontact, ContactDTO contactDTO) {
        pnlcontact.setLayout(null);
        pnlcontact.setLocation(0, hauteurPnlcontactY * contactCount);
        pnlcontact.setSize(longueurPnlcontactX, hauteurPnlcontactY);
        pnlcontact.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == CLICK_COUNT) {
                    fenetrePrincipale.ouvrirConversation(new ConversationDTO(null, contactDTO.getNumeroTelephone()));
                    jFrameContacts.dispose();
                }
                dimLblDernierMessage = new Dimension((int) (longueurPnlcontactX * POURCENTAGE_LONGUEUR_LBL_DERNIER_MSG),
                        (int) (hauteurPnlcontactY * POURCENTAGE_HAUTEUR_LBL_DERNIER_MSG));


                borderVideSize = (int) (hauteurPnlcontactY * POURCENTAGE_COUTOUR_VIDE);
            }
        });
    }

    /**
     * Initialise le nom de la personne avec qui la contact a lieu
     *
     * @param pnlcontact Le panneau d'une contact
     * @param contactDTO Une contact
     */
    private void initialiserPanneaucontactNom(JPanel pnlcontact, ContactDTO contactDTO) {
        JLabel nom = new JLabel(contactDTO.getNom());
        nom.setFont(new Font(nom.getFont().getFontName(), Font.BOLD, (int) dimLblNom.getHeight()));
        nom.setSize(Formatage.calculerDimensionString(contactDTO.getNom(), nom.getFont()));
    }

    /**
     * @param pnlConversation Le panneau d'une contact
     * @param contactDTO      Une contact
     */
    private void initialiserPanneauNom(JPanel pnlConversation, ContactDTO contactDTO) {
        //JLabel nom = new JLabel(facadeModele.getContact(conversationDTO.getNumeroTelephone()).getNom());
        JLabel nom = new JLabel(facadeModele.getContact(contactDTO.getNumeroTelephone()).getNom());
        nom.setFont(new Font(nom.getFont().getFontName(), Font.BOLD, (int) dimLblNom.getHeight()));
        //nom.setSize(Formatage.calculerDimensionString(contactDTO.getNom(), new Font("raleway", Font.TRUETYPE_FONT, 12)));
        nom.setSize(Formatage.calculerDimensionString(contactDTO.getNom(), nom.getFont()));
        if (nom.getWidth() > longuerMaxContact) {
            longuerMaxContact = nom.getWidth();
        }
        nom.setLocation(borderVideSize, (int) (pnlConversation.getHeight() / 2 - (dimLblNom.getHeight() +
                dimLblDernierMessage.getHeight()) / 2));
        pnlConversation.add(nom);
    }


    /**
     * Mettre ? jour les contacts
     */
    public void initialiserContacts() {
        this.removeAll();
        contactCount = 0;
        for (ContactDTO contactDTO : ContactsTest.asList()) {
            ajouterContact(contactDTO);
            contactCount++;
        }
        this.setBounds(0, 0, longuerMaxContact + 50, hauteurPnlcontactY * contactCount);
        this.repaint();
    }

}