package ca.qc.bdeb.gr1_420_P56_BB.vue;

import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.FacadeModele;
import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.Formatage;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Alexandre on 2015-11-17.
 */
public class FrmCreationCompte extends JFrame {

    private static final String TITRE_FENETRE = "Inscription";

    public static final String TEXTE_MOT_DE_PASSE = "Mot de passe : ";

    public static final String TEXTE_REENTRER_MOT_DE_PASSE = "Entrez le mot de passe à nouveau : ";

    public static final String TEXTE_SINSCRIRE = "S'inscrire";

    public static final String TEXTE_ANNULER = "Annuler";

    private static final String TEXTE_NOM_UTILISATEUR = "Nom d'utilisateur : ";

    private static final String MESSAGE_MOT_DE_PASSE = "Les mots de passe ne corresponde pas";

    private static final String MESSAGE_CHAMPS_REMPLIS = "Les champs doivent tous être remplis";

    private static final String MESSAGE_COMPTE_CREE = "Votre compte a été créé";

    private static final String MESSAGE_NOM_UTIL_DEJA_EXISTANT = "Le nom d'utilisateur existe déjà";

    /**
     * Nom de la police dans le panel
     */
    private static final String NOM_POLICE = "Segoe UI Black";

    /**
     * Taille du texte
     */
    private static final int GRANDEUR_TEXTE = 25;

    /**
     * Font dans le panel
     */
    private final Font FONT = new Font(NOM_POLICE, Font.BOLD, GRANDEUR_TEXTE);

    /**
     * Couleur du titre
     */
    private static final Color COULEUR_TITRE = new Color(0, 142, 198);

    private FacadeModele facadeModele;

    private JButton btnInscrite;
    private JButton btnAnnuler;

    private JTextField txfNomUtil;
    private JPasswordField txfMotPasse;
    private JPasswordField txfMotPasseReEntre;


    public FrmCreationCompte(FacadeModele facadeModele) {
        this.facadeModele = facadeModele;
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
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
        lblInscription.setFont(FONT);
        lblInscription.setHorizontalAlignment(JLabel.CENTER);
        lblInscription.setVerticalAlignment(JLabel.CENTER);
        lblInscription.setForeground(COULEUR_TITRE);

        this.add(lblInscription, BorderLayout.NORTH);
    }

    private void initialiserChamps() {

        txfNomUtil = new JTextField();
        txfMotPasse = new JPasswordField();
        txfMotPasseReEntre = new JPasswordField();

        JPanel pnlChamps = new JPanel();
        pnlChamps.setLayout(new GridLayout(5, 2));

        pnlChamps.add(new JLabel());
        pnlChamps.add(new JLabel());
        pnlChamps.add(new JLabel(TEXTE_NOM_UTILISATEUR));
        pnlChamps.add(txfNomUtil);
        pnlChamps.add(new JLabel(TEXTE_MOT_DE_PASSE));
        pnlChamps.add(txfMotPasse);
        pnlChamps.add(new JLabel(TEXTE_REENTRER_MOT_DE_PASSE));
        pnlChamps.add(txfMotPasseReEntre);
        pnlChamps.add(new JLabel());
        pnlChamps.add(new JLabel());

        this.add(pnlChamps, BorderLayout.CENTER);
    }

    private void initialiserBtn() {
        btnInscrite = new JButton(TEXTE_SINSCRIRE);
        btnAnnuler = new JButton(TEXTE_ANNULER);
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
