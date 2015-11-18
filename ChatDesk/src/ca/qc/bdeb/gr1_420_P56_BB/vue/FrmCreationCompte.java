package ca.qc.bdeb.gr1_420_P56_BB.vue;

import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.FacadeModele;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Alexandre on 2015-11-17.
 */
public class FrmCreationCompte extends JFrame {

    private static final String TITRE_FENETRE = "Inscription";

    private static final String TEXTE_NOM_UTILISATEUR = "Nom d'utilisateur ";

    public static final String TEXTE_MOT_DE_PASSE = "Mot de passe ";

    public static final String TEXTE_REENTRER_MOT_DE_PASSE = "Entrez, de nouveau, le mot de passe ";

    public static final String TEXTE_SINSCRIRE = "S'inscrire";

    public static final String TEXTE_ANNULER = "Annuler";

    private static final String MESSAGE_MOT_DE_PASSE = "Les mots de passe ne corresponde pas";

    private static final String MESSAGE_CHAMPS_REMPLIS = "Les champs doivent tous être remplis";

    private static final String MESSAGE_COMPTE_CREE = "Votre compte a été créé";

    private static final String MESSAGE_NOM_UTIL_DEJA_EXISTANT = "Le nom d'utilisateur existe déjà";

    /**
     * Nom de la police dans le panel
     */
    private static final String NOM_POLICE = "Segoe UI Black";

    /**
     * Taille du texte du titre
     */
    private static final int GRANDEUR_TEXTE_TITRE = 25;

    /**
     * Taille du texte des champs
     */
    private static final int GRANDEUR_TEXTE_CHAMPS = 18;

    /**
     * Taille du texte des boutons
     */
    private static final int GRANDEUR_TEXTE_BTN = 14;

    /**
     * Font du titre de la frm
     */
    private final Font FONT_TITRE = new Font(NOM_POLICE, Font.BOLD, GRANDEUR_TEXTE_TITRE);

    /**
     * Font du texte des champs
     */
    private final Font FONT_CHAMPS = new Font(NOM_POLICE, Font.PLAIN, GRANDEUR_TEXTE_CHAMPS);

    /**
     * Font du texte des boutons
     */
    private final Font FONT_BTN = new Font(NOM_POLICE, Font.PLAIN, GRANDEUR_TEXTE_BTN);

    /**
     * Couleur du titre
     */
    private static final Color COULEUR_TEXTE_TITRE = new Color(0, 142, 198);

    /**
     * Couleur du texte de champ
     */
    private static final Color COULEUR_TEXTE_CHAMPS = new Color(100, 160, 198);

    private FacadeModele facadeModele;

    private JButton btnInscrite;
    private JButton btnAnnuler;

    private JTextField txfNomUtil;
    private JPasswordField txfMotPasse;
    private JPasswordField txfMotPasseReEntre;


    public FrmCreationCompte(FacadeModele facadeModele) {
        this.facadeModele = facadeModele;
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new BorderLayout());

        placerComposant();

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void placerComposant() {
        initialiserTitre();
        initialiserChamps();
        initialiserBtn();
    }

    private void initialiserTitre() {
        JLabel lblInscription = new JLabel(TITRE_FENETRE);
        lblInscription.setFont(FONT_TITRE);
        lblInscription.setHorizontalAlignment(JLabel.CENTER);
        lblInscription.setVerticalAlignment(JLabel.CENTER);
        lblInscription.setForeground(COULEUR_TEXTE_TITRE);

        this.add(lblInscription, BorderLayout.NORTH);
    }

    private void initialiserChamps() {

        txfNomUtil = new JTextField();
        txfMotPasse = new JPasswordField();
        txfMotPasseReEntre = new JPasswordField();

        JPanel pnlChamps = new JPanel();
        pnlChamps.setLayout(new GridLayout(9, 2));

        pnlChamps.add(new JLabel());
        pnlChamps.add(new JLabel());
        pnlChamps.add(new JLabel());
        pnlChamps.add(new JLabel());

        pnlChamps.add(initialiserLblChamps(TEXTE_NOM_UTILISATEUR));
        pnlChamps.add(txfNomUtil);

        pnlChamps.add(new JLabel());
        pnlChamps.add(new JLabel());

        pnlChamps.add(initialiserLblChamps(TEXTE_MOT_DE_PASSE));
        pnlChamps.add(txfMotPasse);

        pnlChamps.add(new JLabel());
        pnlChamps.add(new JLabel());

        pnlChamps.add(initialiserLblChamps(TEXTE_REENTRER_MOT_DE_PASSE));
        pnlChamps.add(txfMotPasseReEntre);

        pnlChamps.add(new JLabel());
        pnlChamps.add(new JLabel());
        pnlChamps.add(new JLabel());
        pnlChamps.add(new JLabel());

        this.add(pnlChamps, BorderLayout.CENTER);
    }

    private JLabel initialiserLblChamps(String texte){
        JLabel lblChamp = new JLabel(texte);
        lblChamp.setFont(FONT_CHAMPS);
        lblChamp.setForeground(COULEUR_TEXTE_CHAMPS);
        lblChamp.setHorizontalAlignment(JLabel.LEFT);
        lblChamp.setVerticalAlignment(JLabel.CENTER);

        return lblChamp;
    }

    private void initialiserBtn() {
        btnInscrite = new JButton(TEXTE_SINSCRIRE);
        btnInscrite.setFont(FONT_BTN);
        btnAnnuler = new JButton(TEXTE_ANNULER);
        btnAnnuler.setFont(FONT_BTN);
        initialiserActionListenerBtn();

        JPanel pnlBoutons = new JPanel();
        pnlBoutons.setLayout(new GridLayout(0, 2));

        pnlBoutons.add(btnAnnuler);
        pnlBoutons.add(btnInscrite);

        this.add(pnlBoutons, BorderLayout.SOUTH);
    }

    private void initialiserActionListenerBtn() {
        btnInscrite.addActionListener(actionEvent -> {
            if (verifierTousChampsRemplit()) {
                if(comparerMotDePasse()){
                    if(facadeModele.sinscrire(txfNomUtil.getText(), txfMotPasse.getText())){
                        JOptionPane.showMessageDialog(this, MESSAGE_COMPTE_CREE);
                        this.dispose();
                    } else{
                        JOptionPane.showMessageDialog(this, MESSAGE_NOM_UTIL_DEJA_EXISTANT);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, MESSAGE_MOT_DE_PASSE);
                }
            } else {
                JOptionPane.showMessageDialog(this, MESSAGE_CHAMPS_REMPLIS);
            }
        });

        btnAnnuler.addActionListener(actionEvent -> {
            this.dispose();
        });
    }

    private boolean verifierTousChampsRemplit() {
        return !txfNomUtil.getText().isEmpty() && !txfMotPasse.getText().isEmpty()
                && !txfMotPasseReEntre.getText().isEmpty();
    }

    private boolean comparerMotDePasse() {
        return txfMotPasse.getText().equals(txfMotPasseReEntre.getText());
    }
}
