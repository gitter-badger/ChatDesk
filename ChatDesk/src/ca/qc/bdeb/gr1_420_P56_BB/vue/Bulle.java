package ca.qc.bdeb.gr1_420_P56_BB.vue;

import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.Message;
import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.Formatage;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;

/**
 * Une bulle d'un message re�u ou envoy�.
 */
class Bulle {

    /**
     * Couleur des bulles reçus
     */
    private Color couleurBulleRecue = new Color(51, 153, 102);

    /**
     * Couleur des bulles envoyés
     */
    private  Color couleurBulleEnvoyee = new Color(80, 150, 180);

    /**
     * Couleur vide
     */
    private static final Color COULEUR_VIDE = new Color(0, 0, 0, 0);

    /**
     * Nom de la police dans la fenêtre
     */
    private static final String NOM_POLICE = "Helvetica";

    /**
     * Grandeur du texte
     */
    private static final int GRANDEUR_TEXTE = 20;

    /**
     * Font du texte du message
     */
    private static final Font FONT_TEXTE = new Font(NOM_POLICE, Font.PLAIN, GRANDEUR_TEXTE);

    /**
     * Grandeur de la bordure de vide du jTextArea
     */
    private static final int GRANDEUR_BORDURE_VIDE = 8;

    /**
     * La bulle dessin�
     */
    private final BufferedImage bulle;

    /**
     * Longueur maximal de la bulle
     */
    private final int maxWidth;

    /**
     * Le texte de la bulle
     */
    private JTextArea messageArea;

    /**
     * dimension totale
     */
    private Dimension dimTotal;

    /**
     * Le message de la bulle
     */
    private final String message;

    public Bulle(Message message, int maxWidth) {
        this.maxWidth = maxWidth;
        this.message = message.getText();
        initialisationChampMessage();
        bulle = new BufferedImage(messageArea.getWidth(), messageArea.getHeight(), BufferedImage.TYPE_INT_ARGB);
        paintBulle(message.isEnvoyer());
    }

    public Bulle(Message message, int maxWidth, Color couleurBulleEnvoyee, Color couleurBulleRecue) {
        this.maxWidth = maxWidth;
        this.message = message.getText();
        this.couleurBulleEnvoyee = couleurBulleEnvoyee;
        this.couleurBulleRecue = couleurBulleRecue;
        bulle = new BufferedImage(messageArea.getWidth(), messageArea.getHeight(), BufferedImage.TYPE_INT_ARGB);
    }

    /**
     * Initialisation du format du message (sa longueur, sa hauteur, son font).
     */
    private void initialisationChampMessage() {
        messageArea = new JTextArea();
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setOpaque(false);
        messageArea.setBackground(COULEUR_VIDE);
        messageArea.setFont(FONT_TEXTE);
        messageArea.setBorder(BorderFactory.createEmptyBorder(GRANDEUR_BORDURE_VIDE, GRANDEUR_BORDURE_VIDE,
                GRANDEUR_BORDURE_VIDE, GRANDEUR_BORDURE_VIDE));
        dimTotal = calculerDimensionTotalMessage(this.message);
        messageArea.setSize((int) dimTotal.getWidth() + GRANDEUR_BORDURE_VIDE * 2,
                (int) dimTotal.getHeight() + GRANDEUR_BORDURE_VIDE * 2);
        messageArea.append(this.message);
    }

    /**
     * Calcul la dimension de la bulle
     *
     * @param message le message dans la bulle
     * @return la dimension de la bulle
     */
    private Dimension calculerDimensionTotalMessage(String message) {
        Dimension dimension = new Dimension();
        Dimension dimMessage = Formatage.calculerDimensionString(message, FONT_TEXTE);

        if (dimMessage.getWidth() < maxWidth) {
            dimension.setSize(dimMessage.getWidth(), dimMessage.getHeight());
        } else {
            int i = (int) Math.ceil(dimMessage.getWidth() / (double) maxWidth);
            dimension.setSize(maxWidth, i * dimMessage.getHeight());
        }
        return dimension;
    }

    /**
     * Dessine la bulle
     *
     * @param envoyee Si le message est envoy� ou re�u
     */
    private void paintBulle(boolean envoyee) {
        Graphics2D g = bulle.createGraphics();
        RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHints(renderingHints);
        int width = messageArea.getWidth();
        int height = messageArea.getHeight();
        g.fill(definirContourBulleEnvoye(g, width, height, envoyee));
        messageArea.paint(g);
        g.dispose();
    }

    /**
     * Definit le contour de la bulle selon si elle est envoy� ou re�u
     *
     * @param g       le graphics de la bulle
     * @param width   la largeur de la bulle
     * @param height  la hauteur de la bulle
     * @param envoyee si le message de la bulle est envoy�
     * @return Le general path de la bulle (contour)
     */
    private GeneralPath definirContourBulleEnvoye(Graphics2D g, int width, int height, boolean envoyee) {
        GeneralPath generalPath = new GeneralPath();
        if (!envoyee) {
            definirContourBulleEnvoye(g, width, height, generalPath);
        } else {
            definirContourBulleRecu(g, width, height, generalPath);
        }
        generalPath.closePath();
        return generalPath;
    }

    /**
     * D�finit le contour de la bulle lorsqu'une bulle est re�u
     *
     * @param g      le graphics de la bulle
     * @param width  la largeur de la bulle
     * @param height la hauteur de la bulle
     * @see <a href="http://stackoverflow.com/questions/25821238/draw-beautiful-speech-bubbles-in-swing">
     *     http://stackoverflow.com/questions/25821238/draw-beautiful-speech-bubbles-in-swing</a> (Pris tel quel)
     * @author user3767784
     */
    private void definirContourBulleRecu(Graphics2D g, int width, int height, GeneralPath path) {
        g.setPaint(couleurBulleRecue);
        path.moveTo(width - 5, 10);

        path.curveTo(width - 5, 10, width - 7, 5, width, 0);
        path.curveTo(width, 0, width - 12, 0, width - 12, 5);
        path.curveTo(width - 12, 5, width - 12, 0, width - 20, 0);

        path.lineTo(10, 0);
        path.curveTo(10, 0, 0, 0, 0, 10);
        path.lineTo(0, height - 10);
        path.curveTo(0, height - 10, 0, height, 10, height);
        path.lineTo(width - 15, height);
        path.curveTo(width - 15, height, width - 5, height, width - 5, height - 10);

        path.lineTo(width - 5, 15);
    }

    /**
     * D�finit le contour de la bulle lorsqu'une bulle est envoy�
     *
     * @param g      le graphics de la bulle
     * @param width  la largeur de la bulle
     * @param height la hauteur de la bulle
     *  @see <a href="http://stackoverflow.com/questions/25821238/draw-beautiful-speech-bubbles-in-swing">
     *     http://stackoverflow.com/questions/25821238/draw-beautiful-speech-bubbles-in-swing</a> (modifi�)
     * @author user3767784
     */
    private void definirContourBulleEnvoye(Graphics2D g, int width, int height, GeneralPath path) {
        g.setPaint(couleurBulleEnvoyee);
        path.moveTo(5, 10);

        path.curveTo(5, 10, 7, 5, 0, 0);
        path.curveTo(0, 0, 12, 0, 12, 5);
        path.curveTo(12, 5, 12, 0, 20, 0);

        path.lineTo(width - 10, 0);
        path.curveTo(width - 10, 0, width, 0, width, 10);
        path.lineTo(width, height - 10);
        path.curveTo(width, height - 10, width, height, width - 10, height);
        path.lineTo(15, height);
        path.curveTo(15, height, 5, height, 5, height - 10);

        path.lineTo(5, 15);
    }

    BufferedImage getBulleBufferedImage() {
        return bulle;
    }
}