package ca.qc.bdeb.gr1_420_P56_BB.chatDesk;


import javax.swing.*;

/**
 * Un contact
 */
public class Contact{

    /**
     * Le numéro de téléphone du contact
     */
    private final long numeroTelephone;

    /**
     * Le nom du contact
     */
    private final String nom;

    /**
     * L'image du contact
     */
    private final ImageIcon image;


    /**
     * Constructeur qui permet de crée un contact
     *
     * @param numeroTelephone Le numéro de téléphone du contact
     * @param nom Le nom du contact
     */
    public Contact(long numeroTelephone, String nom, ImageIcon image){
        this.numeroTelephone = numeroTelephone;
        this.nom = nom;
        this.image = image;
    }

    /**
     * @return Le numéro de téléphone du contact
     */
    public long getNumeroTelephone() {
        return numeroTelephone;
    }

    /**
     * @return Le nom du contact
     */
    public String getNom() {
        return nom;
    }

    public ImageIcon getImage(){
        return image;
    }

    public ContactDTO genererContactDTO(){
        return new ContactDTO(nom, numeroTelephone, image);
    }
}
