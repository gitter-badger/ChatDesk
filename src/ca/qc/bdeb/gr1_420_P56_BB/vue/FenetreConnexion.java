package ca.qc.bdeb.gr1_420_P56_BB.vue;

import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.FacadeModele;
import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.Utilisateur;

import javax.swing.*;
import java.awt.*;

/**
 * La fen�tre de connexion pour se connecter.
 */
public class FenetreConnexion extends JFrame {
    private static final String MESSAGE_CONNECTION_INVALIDE = "Informations invalides";
    private static final String MESSAGE_CONNECTION_IMPOSSIBLE = "Connection au serveur impossible";
    private static final String CHEMIN_ICONE = "resources\\images\\Mobile-Smartphone-icon.png";
    private static final String TEXTE_BOUTON_CONNEXION = "CONNEXION";
    private static final String TEXT_BOUTON_INSCRIPTION = "INSCRIPTION";
    private static final String TEXT_CHAMP_USAGER = "NOM USAGER";
    private static final String TEXT_CHAMP_MOTDEPASSE = "MOT DE PASSE";
    private static final Dimension DIMENSION_ICON = new Dimension(100, 100);
    private static final Color COULEUR_TEXTE = new Color(108, 149, 234);

    private static final String NOM_POLICE = "raleway";
    private static final int GRANDEUR_TEXTE = 16;
    private static final double RATIO_80_ECRAN = 0.8;

    private static final Font FONT_TEXTE_TRUE_TYPE = new Font(NOM_POLICE, Font.TRUETYPE_FONT, GRANDEUR_TEXTE);
    private static final Font FONT_TEXTE_PLAIN = new Font(NOM_POLICE, Font.PLAIN, GRANDEUR_TEXTE);

    private static final double POURCENTAGE_ECRAN_LOCATION_FENETRE = 0.25;
    private static final double POURCENTAGE_ECRAN_GRANDEUR_FENETRE = 0.4;

    private static final double POURCENTAGE_FENETRE_LONGUEUR_COMPOSANT = 0.4;
    private static final int HAUTEUR_BOUTON = 40;
    private static final int HAUTEUR_CHAMP = 20;
    private static final int MOITIE_ECRAN = 2;
    public static final int HAUTEUR_STRING = 45;
    public static final double RATIO_LONGEUR_CHAINE = 0.1;
    public static final double RATIO_MOITIE_ECRAN = 0.5;
    public static final double RATIO_HAUTEUR_LAYOUT_BOUTON = 0.01;
    public static final int COL_LAYOUT_GRID = 2;
    private Utilisateur utilisateur;
    private FacadeModele facadeModele;

    private JButton btnConnexion;
    private JButton btnInscription;
    private JPanel pnlInformationEntres;
    private JPanel pnlBoutonsActions;
    private JTextArea champDeNomUsager;
    private JTextArea champDeMotDePasse;
    private JLabel lblIcone;
    private ImageIcon iconeImage;

    public FenetreConnexion(Utilisateur utilisateur) {
        this.facadeModele = new FacadeModele();
        initialiserIcone();
        initialiserLayout();
        initialiserChamps();
        initialiserBoutons();
        pnlInformationEntres.setOpaque(false);
        ajouterElements();
        parametrerFenetre();
    }

    private void parametrerFenetre() {
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void ajouterElements() {
        this.add(lblIcone);
        this.add(pnlInformationEntres);
        this.add(pnlBoutonsActions);
    }

    private void initialiserIcone() {
        lblIcone = new JLabel();
        iconeImage = new ImageIcon(CHEMIN_ICONE);
        lblIcone.setIcon(iconeImage);
        lblIcone.setEnabled(true);
        lblIcone.setLocation(0, 0);
        lblIcone.setPreferredSize(DIMENSION_ICON);
    }

    /**
     * D�fini les layouts pour la fen�tre et la positione
     */
    private void initialiserLayout() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLayout(new FlowLayout(FlowLayout.CENTER, (int) (this.getHeight() / MOITIE_ECRAN), (int) (this.getHeight() / MOITIE_ECRAN)));
        this.setSize((int) (dimension.width * POURCENTAGE_ECRAN_GRANDEUR_FENETRE),
                (int) (dimension.getHeight() * POURCENTAGE_ECRAN_GRANDEUR_FENETRE));
        this.setLocationRelativeTo(null);
    }

    /**
     * Initialise les boutons connexion et inscription
     */
    private void initialiserBoutons() {
        pnlBoutonsActions = new JPanel();
        pnlBoutonsActions.setSize(this.getWidth(), (int) (this.getHeight() * RATIO_80_ECRAN));
        pnlBoutonsActions.setLayout(new GridLayout(0, COL_LAYOUT_GRID, (int) (this.getHeight() * RATIO_HAUTEUR_LAYOUT_BOUTON), (int) (this.getHeight() * RATIO_MOITIE_ECRAN)));
        btnConnexion = new JButton(TEXTE_BOUTON_CONNEXION);
        btnInscription = new JButton(TEXT_BOUTON_INSCRIPTION);
        btnInscription.setPreferredSize(new Dimension((int) (this.getWidth() * POURCENTAGE_FENETRE_LONGUEUR_COMPOSANT), HAUTEUR_BOUTON));
        btnConnexion.setPreferredSize(new Dimension((int) (this.getWidth() * POURCENTAGE_FENETRE_LONGUEUR_COMPOSANT), HAUTEUR_BOUTON));
        connectionServeur();
        btnConnexion.setBorderPainted(false);
        btnConnexion.setFont(FONT_TEXTE_PLAIN);
        btnConnexion.setBackground(COULEUR_TEXTE);
        btnConnexion.setOpaque(false);
        btnInscription.setBackground(COULEUR_TEXTE);
        btnInscription.setOpaque(false);
        btnInscription.setBorderPainted(false);
        btnInscription.setFont(FONT_TEXTE_PLAIN);
        pnlBoutonsActions.add(btnConnexion);
        pnlBoutonsActions.add(btnInscription);
        pnlBoutonsActions.add(btnInscription);
    }

    /**
     * Initialisation du bouton de connexion au serveur
     */
    private void connectionServeur() {
        btnConnexion.addActionListener(e -> {
            switch (facadeModele.seConnecter(champDeNomUsager.getText(), champDeMotDePasse.getText())) {
                case VALIDE:
                    FrmChatDesk frmChatDesk = new FrmChatDesk(facadeModele);
                    frmChatDesk.setVisible(true);
                    FenetreConnexion.this.dispose();
                    facadeModele.demanderAppareils();
                    break;
                case INVALIDE:
                    JOptionPane.showMessageDialog(this, MESSAGE_CONNECTION_INVALIDE);
                    break;
                case IMPOSSIBLE:
                    JOptionPane.showMessageDialog(this, MESSAGE_CONNECTION_IMPOSSIBLE);
                    break;
            }
        });
    }


    /**
     * Initialise les JTextArea � remplir pour la connexion
     */
    private void initialiserChamps() {
        pnlInformationEntres = new JPanel();

        pnlInformationEntres.setPreferredSize(new Dimension(this.getWidth(), (int) (this.getHeight() * RATIO_MOITIE_ECRAN)));
        pnlInformationEntres.setLayout(new FlowLayout(FlowLayout.CENTER, (int) (this.getWidth() * RATIO_MOITIE_ECRAN),
                (int) (this.getHeight() * 0.15)));
        champDeNomUsager = new JTextArea();
        champDeMotDePasse = new JTextArea();
        champDeMotDePasse.setFont(FONT_TEXTE_TRUE_TYPE);
        champDeNomUsager.setFont(FONT_TEXTE_TRUE_TYPE);
        pnlInformationEntres.add(champDeNomUsager);
        pnlInformationEntres.add(champDeMotDePasse);
        champDeNomUsager.setPreferredSize(new Dimension((int) (this.getWidth() * POURCENTAGE_FENETRE_LONGUEUR_COMPOSANT),
                HAUTEUR_CHAMP));
        champDeMotDePasse.setPreferredSize(new Dimension((int) (this.getWidth() * POURCENTAGE_FENETRE_LONGUEUR_COMPOSANT),
                HAUTEUR_CHAMP));
        champDeMotDePasse.setBounds(0, 0, champDeNomUsager.getWidth(), champDeNomUsager.getHeight());

    }

    @Override
    public void paint(Graphics g) {
        super.paintComponents(g);
        Graphics2D g2 = (Graphics2D) g;
        RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHints(qualityHints);
        g2.setColor(COULEUR_TEXTE);
        g2.setFont(FONT_TEXTE_TRUE_TYPE);
        g2.setColor(Color.BLACK);
        g2.drawString(TEXT_CHAMP_USAGER, (int) (this.getWidth() * RATIO_LONGEUR_CHAINE),
                champDeNomUsager.getY() + lblIcone.getHeight() + HAUTEUR_STRING);
        g2.drawString(TEXT_CHAMP_MOTDEPASSE, (int) (this.getWidth() * RATIO_LONGEUR_CHAINE),
                champDeMotDePasse.getY() + lblIcone.getHeight() + HAUTEUR_STRING);
    }
}
