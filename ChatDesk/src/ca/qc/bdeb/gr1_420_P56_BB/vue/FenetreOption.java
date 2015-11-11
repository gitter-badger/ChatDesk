package ca.qc.bdeb.gr1_420_P56_BB.vue;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.DefaultMenuLayout;
import java.awt.*;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;

/**
 * Created by 47 on 2015-10-31.
 */
public class FenetreOption extends JFrame{
    /**
     *  Panneau contenant toutes les options
     */
    private JPanel panelOptions;
    /**
     * Label � gauche du champ pour choisir la couleur des bulles envoy�es
     */
    private JLabel labelOptionCouleurEnvoye;
    /**
     * Label � gauche du champ pour choisir la couleur des bulles re�ues
     */
    private JLabel labelOptionCouleurRecu;

    /**
     * Dimensions de la fenetre principale
     */
    private int dimensionLargeurFenetre;

    /**
     * Dimensions de la fenetre principale
     */
    private int dimensionHauteurFenetre;
    /**
     * Couleur des bulles recues
     */
    private JLabel couleurActuelOptionRecues;
    /**
     *Couleur des bulles envoy�es
     */
    private JLabel couleurActuelOptionEnvoyees;
    /**
     *  couleur des bulles envoye�s
     */
    private Color couleurBullesEnvoyes;
    /**
     *  couleur des bulles re�ues
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

    JPanel panneauConteneur;

    public FenetreOption(int dimensionHauteurFenetre, int dimensionLargeurFenetre, Color couleurBullesEnvoye, Color couleurBullesRecues, FrmChatDesk fenetrePrinciple) throws HeadlessException {
        panneauConteneur = new JPanel();
        
        panneauConteneur.setLayout(new FlowLayout(FlowLayout.CENTER, dimensionHauteurFenetre / 100 , dimensionLargeurFenetre / 100));
        this.fenetrePrincipale = fenetrePrinciple;
        this.dimensionHauteurFenetre = dimensionHauteurFenetre;
        this.dimensionLargeurFenetre = dimensionLargeurFenetre;
        this.couleurBullesEnvoyes = couleurBullesEnvoye;
        this.couleurBullesRecues = couleurBullesRecues;
        panneauConteneur.setMinimumSize(new Dimension(dimensionLargeurFenetre / 6, dimensionHauteurFenetre / 6));
        this.setMinimumSize(new Dimension(dimensionLargeurFenetre / 3, dimensionHauteurFenetre / 3));
        panneauConteneur.setMaximumSize(new Dimension(dimensionLargeurFenetre / 2, dimensionHauteurFenetre / 2));
        initialiserChampsDeChoixCouleur();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }



    private void initialiserChampsDeChoixCouleur(){
        couleurActuelOptionEnvoyees = new JLabel();
        couleurActuelOptionEnvoyees.setText("      ");
        couleurActuelOptionEnvoyees.setBackground(couleurBullesEnvoyes);
        couleurActuelOptionEnvoyees.setOpaque(true);
        couleurActuelOptionRecues = new JLabel();
        couleurActuelOptionRecues.setText("      ");
        couleurActuelOptionRecues.setBackground(couleurBullesRecues);
        couleurActuelOptionRecues.setOpaque(true);
        labelOptionCouleurEnvoye = new JLabel();
        labelOptionCouleurRecu = new JLabel();
        labelOptionCouleurEnvoye.setText("Couleur des bulles envoyées");
        labelOptionCouleurRecu.setText("Couleur des bulles reçues");
        initialiserChampNumberSpinnerCouleures();
    }

    private void initialiserChampNumberSpinnerCouleures(){
        initialisationChamps();
        initialisationModeleNombre();
        champsCouleurEnvoyeTab[0].setMaximumSize(new Dimension(40, 20));
        champsCouleurEnvoyeTab[1].setSize(40, 20);
        champsCouleurEnvoyeTab[2].setSize(40,20);
        champsCouleurRecueTab[0].setSize(40,20);
        champsCouleurRecueTab[1].setSize(couleurActuelOptionEnvoyees.getSize());
        champsCouleurRecueTab[2].setSize(couleurActuelOptionEnvoyees.getSize());
        ajoutChamps();
    }

    private void ajoutChamps() {


        panneauConteneur.add(labelOptionCouleurEnvoye);
        panneauConteneur.add(champsCouleurEnvoyeTab[0]);
        panneauConteneur.add(champsCouleurEnvoyeTab[1]);
        panneauConteneur.add(champsCouleurEnvoyeTab[2]);
        panneauConteneur.add(couleurActuelOptionEnvoyees);
        panneauConteneur.add(labelOptionCouleurRecu);
        panneauConteneur.add(champsCouleurRecueTab[0]);
        panneauConteneur.add(champsCouleurRecueTab[1]);
        panneauConteneur.add(champsCouleurRecueTab[2]);
        panneauConteneur.add(couleurActuelOptionRecues);
        this.add(panneauConteneur);
    }

    private void initialisationChamps() {
        champsCouleurEnvoyeTab = new JSpinner[3];
        champsCouleurEnvoyeTab[0] = new JSpinner();
        champsCouleurEnvoyeTab[1] = new JSpinner();
        champsCouleurEnvoyeTab[2] = new JSpinner();

        champsCouleurEnvoyeTab[0].addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                couleurActuelOptionEnvoyees.setBackground(
                        new Color((int) (champsCouleurEnvoyeTab[0].getValue()),
                                (int) champsCouleurEnvoyeTab[1].getValue(),
                                (int) champsCouleurEnvoyeTab[2].getValue()));
            }
        });
        champsCouleurEnvoyeTab[1].addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                couleurActuelOptionEnvoyees.setBackground(
                        new Color((int) (champsCouleurEnvoyeTab[0].getValue()),
                                (int) champsCouleurEnvoyeTab[1].getValue(),
                                (int) champsCouleurEnvoyeTab[2].getValue()));
            }
        });
        champsCouleurEnvoyeTab[2].addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                couleurActuelOptionEnvoyees.setBackground(
                        new Color((int) (champsCouleurEnvoyeTab[0].getValue()),
                                (int) champsCouleurEnvoyeTab[1].getValue(),
                                (int) champsCouleurEnvoyeTab[2].getValue()));
            }
        });
        champsCouleurRecueTab= new JSpinner[3];
        champsCouleurRecueTab[0] = new JSpinner();
        champsCouleurRecueTab[1] = new JSpinner();
        champsCouleurRecueTab[2] = new JSpinner();
    }

    private void initialisationModeleNombre() {
        champsCouleurEnvoyeTab[0].setModel(new SpinnerNumberModel(couleurBullesEnvoyes.getRed(),0,255,1));
        champsCouleurEnvoyeTab[1].setModel(new SpinnerNumberModel(couleurBullesEnvoyes.getGreen(), 0, 255, 1));
        champsCouleurEnvoyeTab[2].setModel(new SpinnerNumberModel(couleurBullesEnvoyes.getBlue(), 0, 255, 1));
        champsCouleurRecueTab[0].setModel(new SpinnerNumberModel(couleurBullesRecues.getRed(),0,255,1));
        champsCouleurRecueTab[1].setModel(new SpinnerNumberModel(couleurBullesRecues.getGreen(), 0, 255, 1));
        champsCouleurRecueTab[2].setModel(new SpinnerNumberModel(couleurBullesRecues.getBlue(), 0, 255, 1));
    }

    private void setColorEnvoye(){
        fenetrePrincipale.changerCouleurBulleEnvoye(couleurActuelOptionEnvoyees.getBackground());
    }
}
