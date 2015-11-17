package ca.qc.bdeb.gr1_420_P56_BB.vue;

import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.ManipulationFichiers;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.ColorModel;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 47 on 2015-10-31.
 */
public class FrmOption extends JFrame{
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

    private JPanel panneauConteneur;

    private JButton boutonAppliquer;



    public FrmOption(int dimensionHauteurFenetre, int dimensionLargeurFenetre, Color couleurBullesEnvoye, Color couleurBullesRecues, FrmChatDesk fenetrePrinciple) throws HeadlessException, IOException {
        panneauConteneur = new JPanel();
        
        panneauConteneur.setLayout(new FlowLayout(FlowLayout.LEADING, dimensionHauteurFenetre / 100, dimensionLargeurFenetre / 100));
        this.fenetrePrincipale = fenetrePrinciple;
        this.dimensionHauteurFenetre = dimensionHauteurFenetre;
        this.dimensionLargeurFenetre = dimensionLargeurFenetre;
        this.couleurBullesEnvoyes = couleurBullesEnvoye;
        this.couleurBullesRecues = couleurBullesRecues;
        this.boutonAppliquer = new JButton("Appliquer");
        panneauConteneur.setMinimumSize(new Dimension(dimensionLargeurFenetre / 6, dimensionHauteurFenetre / 6));
        this.setMinimumSize(new Dimension(dimensionLargeurFenetre / 3, dimensionHauteurFenetre / 3));
        boutonAppliquer.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    sauveGarderOptions();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        panneauConteneur.setMaximumSize(new Dimension(dimensionLargeurFenetre / 2, dimensionHauteurFenetre / 2));
        initialiserChampsDeChoixCouleur();

        panneauConteneur.add(boutonAppliquer);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }


    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        if (champsCouleurEnvoyeTab != null){
            couleurActuelOptionEnvoyees.setBackground(
                    new Color((int) (champsCouleurEnvoyeTab[0].getValue()),
                            (int) champsCouleurEnvoyeTab[1].getValue(),
                            (int) champsCouleurEnvoyeTab[2].getValue()));
            couleurActuelOptionEnvoyees.repaint();
        }
        if (champsCouleurRecueTab != null){
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
        labelOptionCouleurRecu.setText("Couleur des bulles reçues     ");
        retablirOption();
        initialiserChampNumberSpinnerCouleures();
    }

    private void initialiserChampNumberSpinnerCouleures(){
        initialisationChamps();
        initialisationModeleNombre();
        champsCouleurEnvoyeTab[0].setMaximumSize(new Dimension(40, 20));
        champsCouleurEnvoyeTab[1].setSize(40, 20);
        champsCouleurEnvoyeTab[2].setSize(40, 20);
        champsCouleurRecueTab[0].setSize(40, 20);
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

    @Override
    public void paint(Graphics g) {
        super.paint(g);

    }

    private void initialisationChamps() {
        champsCouleurEnvoyeTab = new JSpinner[3];
        champsCouleurEnvoyeTab[0] = new JSpinner();
        champsCouleurEnvoyeTab[1] = new JSpinner();
        champsCouleurEnvoyeTab[2] = new JSpinner();

        champsCouleurEnvoyeTab[0].addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                paintComponents(getGraphics());
            }
        });
        champsCouleurEnvoyeTab[1].addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                paintComponents(getGraphics());
            }
        });
        champsCouleurEnvoyeTab[2].addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                paintComponents(getGraphics());
            }
        });
        champsCouleurRecueTab= new JSpinner[3];
        champsCouleurRecueTab[0] = new JSpinner();
        champsCouleurRecueTab[1] = new JSpinner();
        champsCouleurRecueTab[2] = new JSpinner();
        champsCouleurRecueTab[0].addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                paintComponents(getGraphics());
            }
        });
        champsCouleurRecueTab[1].addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                paintComponents(getGraphics());
            }
        });
        champsCouleurRecueTab[2].addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                paintComponents(getGraphics());
            }
        });
    }

    private void initialisationModeleNombre() {
        champsCouleurEnvoyeTab[0].setModel(new SpinnerNumberModel(couleurBullesEnvoyes.getRed(),0,255,1));
        champsCouleurEnvoyeTab[1].setModel(new SpinnerNumberModel(couleurBullesEnvoyes.getGreen(), 0, 255, 1));
        champsCouleurEnvoyeTab[2].setModel(new SpinnerNumberModel(couleurBullesEnvoyes.getBlue(), 0, 255, 1));
        champsCouleurRecueTab[0].setModel(new SpinnerNumberModel(couleurBullesRecues.getRed(), 0, 255, 1));
        champsCouleurRecueTab[1].setModel(new SpinnerNumberModel(couleurBullesRecues.getGreen(), 0, 255, 1));
        champsCouleurRecueTab[2].setModel(new SpinnerNumberModel(couleurBullesRecues.getBlue(), 0, 255, 1));
    }

    private void setColorBulleEnvoyee(){
        fenetrePrincipale.changerCouleurBulleEnvoye(couleurActuelOptionEnvoyees.getBackground());

    }
    private void setColorBulleRecues(){
        fenetrePrincipale.changerCouleurBulleRecue(couleurActuelOptionRecues.getBackground());

    }

    private void sauveGarderOptions() throws IOException {
        String chemin = System.getProperty("user.home");
        chemin += "\\Documents\\optionChatDesk";
        ManipulationFichiers.EcrireFichierAvecChemin(chemin,couleurActuelOptionRecues.getBackground()
                + "|" + couleurActuelOptionRecues.getBackground());
    }

    private void retablirOption()  {
        String chemin = System.getProperty("user.home");
        chemin += "\\Documents\\optionChatDesk";
        String valeur = null;
        try {
            valeur = ManipulationFichiers.LireFichierAvecChemin(chemin);

        String regex = "(.*)[|](.*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(valeur);


        couleurActuelOptionRecues.setBackground(Color.decode(matcher.group(1)));
        couleurActuelOptionEnvoyees.setBackground(Color.decode(matcher.group(0)));
        } catch (IOException e) {

        }

    }
}
