package ca.qc.bdeb.gr1_420_P56_BB.vue;

import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.ConversationDTO;
import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.Message;
import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.Formatage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Le panneau  qui contient toutes les bulles de message d'une conversation.
 */
class PnlBulles extends JPanel {

    /**
     * Head room des bulles
     */
    private static final int HAUTEUR_EN_HAUT_EN_BAS_BULLE = 10;
    /**
     * Longueur maximale d'une bukle en pourcentage du panel
     */
    private static final double LONGUEUR_MAX_BULLE = 0.7;

    /**
     * Le DTO de la conversation active du panel
     */
    private ConversationDTO conversationDTO;
    /**
     * La hauteur totale du panel
     */
    private int hauteurTotale;

    private Color couleurBullesRecues = new Color(51, 153, 102);
    private Color couleurBullesEnvoyees = new Color(80, 150, 180);
    public PnlBulles() {
        super(true);
    }

    /**
     * Mettre � jour le panneau des bulles en redessinant les bulles
     *
     * @param conversationDTO La conversation o� sont les message
     */
    public void mettreAJour(ConversationDTO conversationDTO) {
        this.conversationDTO = conversationDTO;
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (conversationDTO != null) {
            genererConversation(g);
        }
        g.dispose();
    }

    /**
     * Dessiner toutes les bulles d'une conversation
     *
     * @param g Le graphics � dessiner de�u
     */
    private void genererConversation(Graphics g) {
        hauteurTotale = 0;
        for (Message message : conversationDTO.getMessages()) {
            dessinerBulle(g, message);
        }
        this.setPreferredSize(new Dimension(this.getWidth(), hauteurTotale));
    }

    /**
     * Dessiner une bulle de la conversation selon si elle est re�u ou envoy�e.
     *
     * @param g       Le graphics � dessiner de�u
     * @param message Le message de la conversation.
     */
    private void dessinerBulle(Graphics g, Message message) {
        Bulle bulleTemp = new Bulle(message, (int) (this.getWidth() * LONGUEUR_MAX_BULLE),couleurBullesEnvoyees,couleurBullesRecues);
        BufferedImage bufferedImage = bulleTemp.getBulleBufferedImage();

        String dateFormate = Formatage.formatageDate(message.getDate());
        Dimension dimDateFormate = Formatage.calculerDimensionString(dateFormate, g.getFont());

        if (message.isEnvoyer()) {
            g.drawImage(bufferedImage, this.getSize().width - (bufferedImage.getWidth() + HAUTEUR_EN_HAUT_EN_BAS_BULLE),
                    hauteurTotale + HAUTEUR_EN_HAUT_EN_BAS_BULLE, bufferedImage.getWidth(), bufferedImage.getHeight(), null);

            g.drawString(dateFormate,
                    this.getSize().width - HAUTEUR_EN_HAUT_EN_BAS_BULLE - (int) dimDateFormate.getWidth(),
                    hauteurTotale + HAUTEUR_EN_HAUT_EN_BAS_BULLE + bufferedImage.getHeight()
                            + (int) dimDateFormate.getHeight());
        } else {
            g.drawImage(bufferedImage, HAUTEUR_EN_HAUT_EN_BAS_BULLE, hauteurTotale + HAUTEUR_EN_HAUT_EN_BAS_BULLE,
                    bufferedImage.getWidth(), bufferedImage.getHeight(), null);
            g.drawString(Formatage.formatageDate(message.getDate()), HAUTEUR_EN_HAUT_EN_BAS_BULLE,
                    hauteurTotale + HAUTEUR_EN_HAUT_EN_BAS_BULLE + bufferedImage.getHeight()
                            + (int) dimDateFormate.getHeight());
        }

        hauteurTotale += bufferedImage.getHeight() + HAUTEUR_EN_HAUT_EN_BAS_BULLE * 2 + (int) dimDateFormate.getHeight();
    }

    public Color getCouleurBullesEnvoyees() {
        return couleurBullesEnvoyees;
    }

    public Color getCouleurBullesRecues() {
        return couleurBullesRecues;
    }


    public void setCouleurBullesEnvoyees(Color couleurBullesEnvoyees) {
        this.couleurBullesEnvoyees = couleurBullesEnvoyees;
    }

    public void setCouleurBullesRecues(Color couleurBullesRecues) {
        this.couleurBullesRecues = couleurBullesRecues;
    }

}