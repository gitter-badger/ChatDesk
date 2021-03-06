package ca.qc.bdeb.gr1_420_P56_BB.vue;

import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.ManipulationFichiers;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 47 on 2015-10-31.
 */
public class FrmOptions extends JFrame {
    private static final Insets INSETS_OPTIONS_ELEMENTS = new Insets(20, 0, 20, 0);
    private static final String STRING_BOUTON_APPLIQUER = "Appliquer";
    private static final String CHEMIN_PROPERTY = "user.home";
    private static final String CHEMING_PROJET = "\\Documents\\optionsChatDesk";
    private static final String CARACTERE_SEPARATION = "|";
    private static final String STRING_LBL_COULEUR_ENVOYE = "Couleur des bulles envoyées";
    private static final String STRING_LBL_COULEUR_RECU = "Couleur des bulles reçues     ";
    private static final String STRING_DEFAULT_COULEURS_ENVOYES = "      ";
    private static final String CHEMIN_DOSSIER_NOTIFICATIONS = "resources/notifications";
    private static final String STRING_INVITE_CBX_SONS = "Son de notification";
    private static final String STRING_BOUTON_ANNULER = "Annuler";
    /**
     * Label � gauche du champ pour choisir la couleur des bulles envoy�es
     */
    private JLabel labelOptionCouleurEnvoye;
    /**
     * Label � gauche du champ pour choisir la couleur des bulles re�ues
     */
    private JLabel labelOptionCouleurRecu;
    /**
     * Couleur des bulles recues
     */
    private JLabel couleurActuelOptionRecues;
    /**
     * Couleur des bulles envoy�es
     */
    private JLabel couleurActuelOptionEnvoyees;
    /**
     * couleur des bulles envoye�s
     */
    private Color couleurBullesEnvoyes;
    /**
     * couleur des bulles re�ues
     */
    private Color couleurBullesRecues;
    /**
     * Champs pour choisir les valeurs RGB des bulles
     */
    private JSpinner[] champsCouleurRecueTab;
    /**
     * Champs pour choisir les valeurs RGB des bulles
     */
    private JSpinner[] champsCouleurEnvoyeTab;
    /**
     * Fenetre frmchatdesk
     */
    private FrmChatDesk fenetrePrincipale;
    private JPanel panneauConteneur;
    private JButton boutonAppliquer;
    private JButton boutonAnnuler;
    private GridBagLayout layoutFenetre;
    private JComboBox<String> cbxFichiersSons;

    public FrmOptions(int dimensionHauteurFenetre, int dimensionLargeurFenetre, Color couleurBullesEnvoye, Color couleurBullesRecues, FrmChatDesk fenetrePrinciple) throws HeadlessException, IOException {
        panneauConteneur = new JPanel();
        layoutFenetre = new GridBagLayout();
        panneauConteneur.setLayout(layoutFenetre);

        this.fenetrePrincipale = fenetrePrinciple;
        this.couleurBullesEnvoyes = couleurBullesEnvoye;
        this.couleurBullesRecues = couleurBullesRecues;
        panneauConteneur.setMinimumSize(new Dimension(dimensionLargeurFenetre / 6, dimensionHauteurFenetre / 6));
        this.setMinimumSize(new Dimension(dimensionLargeurFenetre / 3, dimensionHauteurFenetre / 3));
        panneauConteneur.setMaximumSize(new Dimension(dimensionLargeurFenetre / 2, dimensionHauteurFenetre / 2));

        initialiserChampsDeChoixCouleur();
        initialiserBoutons();
        initialiserCbx();
        ajoutChamps();

        retablirOption();
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    private void initialiserCbx() {
        cbxFichiersSons = new JComboBox<>(new File(CHEMIN_DOSSIER_NOTIFICATIONS).list());
    }

    private void initialiserBoutons() {
        this.boutonAppliquer = new JButton(STRING_BOUTON_APPLIQUER);
        this.boutonAnnuler = new JButton(STRING_BOUTON_ANNULER);
        boutonAppliquer.addActionListener(e -> fermerFenetre(true));
        boutonAnnuler.addActionListener(e -> fermerFenetre(false));
    }

    private void fermerFenetre(boolean sauvegarder) {
        if (sauvegarder) {
            sauvegarderOptions();
        }
        fenetrePrincipale.setVisible(true);
        this.dispose();
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        if (champsCouleurEnvoyeTab != null) {
            couleurActuelOptionEnvoyees.setBackground(
                    new Color((int) (champsCouleurEnvoyeTab[0].getValue()),
                            (int) champsCouleurEnvoyeTab[1].getValue(),
                            (int) champsCouleurEnvoyeTab[2].getValue()));
            couleurActuelOptionEnvoyees.repaint();
        }
        if (champsCouleurRecueTab != null) {
            couleurActuelOptionRecues.setBackground(
                    new Color((int) (champsCouleurRecueTab[0].getValue()),
                            (int) champsCouleurRecueTab[1].getValue(),
                            (int) champsCouleurRecueTab[2].getValue()));
            couleurActuelOptionRecues.repaint();
        }
        setColorBulleEnvoyee();
        setColorBulleRecues();
    }

    private void initialiserChampsDeChoixCouleur() throws IOException {
        couleurActuelOptionEnvoyees = new JLabel();
        couleurActuelOptionEnvoyees.setText(STRING_DEFAULT_COULEURS_ENVOYES);
        couleurActuelOptionEnvoyees.setBackground(couleurBullesEnvoyes);
        couleurActuelOptionEnvoyees.setOpaque(true);
        couleurActuelOptionRecues = new JLabel();
        couleurActuelOptionRecues.setText(STRING_DEFAULT_COULEURS_ENVOYES);
        couleurActuelOptionRecues.setBackground(couleurBullesRecues);
        couleurActuelOptionRecues.setOpaque(true);
        labelOptionCouleurEnvoye = new JLabel();
        labelOptionCouleurRecu = new JLabel();
        labelOptionCouleurEnvoye.setText(STRING_LBL_COULEUR_ENVOYE);
        labelOptionCouleurRecu.setText(STRING_LBL_COULEUR_RECU);
        initialiserChampNumberSpinnerCouleures();
    }

    private void initialiserChampNumberSpinnerCouleures() {
        initialisationChamps();
        initialisationModeleNombre();
        champsCouleurEnvoyeTab[0].setMaximumSize(new Dimension(40, 20));
        champsCouleurEnvoyeTab[1].setSize(40, 20);
        champsCouleurEnvoyeTab[2].setSize(40, 20);
        champsCouleurRecueTab[0].setSize(40, 20);
        champsCouleurRecueTab[1].setSize(couleurActuelOptionEnvoyees.getSize());
        champsCouleurRecueTab[2].setSize(couleurActuelOptionEnvoyees.getSize());
    }

    private void ajoutChamps() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = INSETS_OPTIONS_ELEMENTS;

        panneauConteneur.setLayout(layoutFenetre);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
        panneauConteneur.add(labelOptionCouleurEnvoye, constraints);
        constraints.gridx = 1;
        constraints.gridy = 0;
        panneauConteneur.add(champsCouleurEnvoyeTab[0], constraints);
        constraints.gridx = 2;
        constraints.gridy = 0;
        panneauConteneur.add(champsCouleurEnvoyeTab[1], constraints);
        constraints.gridx = 3;
        constraints.gridy = 0;
        panneauConteneur.add(champsCouleurEnvoyeTab[2], constraints);
        constraints.gridx = 5;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.NONE;
        panneauConteneur.add(couleurActuelOptionEnvoyees, constraints);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 1;
        panneauConteneur.add(labelOptionCouleurRecu, constraints);
        constraints.gridx = 1;
        constraints.gridy = 1;
        panneauConteneur.add(champsCouleurRecueTab[0], constraints);
        constraints.gridx = 2;
        constraints.gridy = 1;
        panneauConteneur.add(champsCouleurRecueTab[1], constraints);
        constraints.gridx = 3;
        constraints.gridy = 1;
        panneauConteneur.add(champsCouleurRecueTab[2], constraints);
        constraints.gridx = 5;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.NONE;
        panneauConteneur.add(couleurActuelOptionRecues, constraints);
        constraints.gridx = 0;
        constraints.gridy = 3;
        panneauConteneur.add(new Label(STRING_INVITE_CBX_SONS), constraints);
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 3;
        panneauConteneur.add(cbxFichiersSons, constraints);
        constraints.gridx = 0;
        constraints.gridy = 4;
        panneauConteneur.add(boutonAnnuler, constraints);
        constraints.gridx = 3;
        constraints.gridy = 4;
        panneauConteneur.add(boutonAppliquer, constraints);
        this.add(panneauConteneur);

        this.pack();
        this.setLocationRelativeTo(null);
    }

    private void initialisationChamps() {
        champsCouleurEnvoyeTab = new JSpinner[3];
        champsCouleurEnvoyeTab[0] = new JSpinner();
        champsCouleurEnvoyeTab[1] = new JSpinner();
        champsCouleurEnvoyeTab[2] = new JSpinner();

        champsCouleurEnvoyeTab[0].addChangeListener(e -> paintComponents(getGraphics()));
        champsCouleurEnvoyeTab[1].addChangeListener(e -> paintComponents(getGraphics()));
        champsCouleurEnvoyeTab[2].addChangeListener(e -> paintComponents(getGraphics()));
        champsCouleurRecueTab = new JSpinner[3];
        champsCouleurRecueTab[0] = new JSpinner();
        champsCouleurRecueTab[1] = new JSpinner();
        champsCouleurRecueTab[2] = new JSpinner();
        champsCouleurRecueTab[0].addChangeListener(e -> paintComponents(getGraphics()));
        champsCouleurRecueTab[1].addChangeListener(e -> paintComponents(getGraphics()));
        champsCouleurRecueTab[2].addChangeListener(e -> paintComponents(getGraphics()));
    }

    private void initialisationModeleNombre() {
        champsCouleurEnvoyeTab[0].setModel(new SpinnerNumberModel(couleurBullesEnvoyes.getRed(), 0, 255, 1));
        champsCouleurEnvoyeTab[1].setModel(new SpinnerNumberModel(couleurBullesEnvoyes.getGreen(), 0, 255, 1));
        champsCouleurEnvoyeTab[2].setModel(new SpinnerNumberModel(couleurBullesEnvoyes.getBlue(), 0, 255, 1));
        champsCouleurRecueTab[0].setModel(new SpinnerNumberModel(couleurBullesRecues.getRed(), 0, 255, 1));
        champsCouleurRecueTab[1].setModel(new SpinnerNumberModel(couleurBullesRecues.getGreen(), 0, 255, 1));
        champsCouleurRecueTab[2].setModel(new SpinnerNumberModel(couleurBullesRecues.getBlue(), 0, 255, 1));
    }

    private void setColorBulleEnvoyee() {
        fenetrePrincipale.changerCouleurBulleEnvoye(couleurActuelOptionEnvoyees.getBackground());
    }

    private void setColorBulleRecues() {
        fenetrePrincipale.changerCouleurBulleRecue(couleurActuelOptionRecues.getBackground());
    }

    private void sauvegarderOptions() {
        String chemin = System.getProperty(CHEMIN_PROPERTY) + CHEMING_PROJET;
        try {
            ManipulationFichiers.EcrireFichierAvecChemin(chemin, couleurActuelOptionRecues.getBackground().getRGB()
                    + CARACTERE_SEPARATION + couleurActuelOptionRecues.getBackground().getRGB() + CARACTERE_SEPARATION
                    + cbxFichiersSons.getSelectedItem());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void retablirOption() {
        String chemin = System.getProperty(CHEMIN_PROPERTY) + CHEMING_PROJET;
        try {
            String valeur = ManipulationFichiers.LireFichierAvecChemin(chemin);
            Matcher matcher = Pattern.compile("(.*)[|](.*)[|](.*)").matcher(valeur);

            matcher.find();
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
            System.out.println(matcher.group(3));
            couleurActuelOptionEnvoyees.setBackground(Color.decode(matcher.group(1)));
            couleurActuelOptionRecues.setBackground(Color.decode(matcher.group(2)));
            cbxFichiersSons.setSelectedItem(matcher.group(3));
        } catch (IOException e) {

        }
    }
}
