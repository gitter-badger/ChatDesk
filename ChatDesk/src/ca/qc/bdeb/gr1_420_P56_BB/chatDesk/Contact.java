package ca.qc.bdeb.gr1_420_P56_BB.chatDesk;


import javax.swing.*;
import java.util.ArrayList;

/**
 * Un contact
 */
public class Contact {

    /**
     * Le numéro de téléphone du contact
     */
    private final ArrayList<Long> listeNumerosTelephone;

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
     * @param listeNumerosTelephone La liste des numero de téléphone Le numéro de téléphone du contact
     * @param nom                  Le nom du contact
     */
    public Contact(ArrayList<Long> listeNumerosTelephone, String nom, ImageIcon image) {
        this.listeNumerosTelephone = listeNumerosTelephone;
        this.nom = nom;
        this.image = image;
    }

    /**
     * Constructeur qui permet de crée un contact
     *
     * @param numeroTelephone Le numéro de téléphone du contact
     * @param nom                  Le nom du contact
     */
    public Contact(long numeroTelephone, String nom, ImageIcon image) {
        this.listeNumerosTelephone = new ArrayList<>();
        this.listeNumerosTelephone.add(numeroTelephone);
        this.nom = nom;
        this.image = image;
    }

    /**
     * @return Le numéro de téléphone du contact
     */
    public ArrayList<Long> getListeNumeroTelephones() {
        return listeNumerosTelephone;
    }

    public boolean isContactNumeroTelephone(long numeroTelephone) {
        return listeNumerosTelephone.contains(numeroTelephone);
    }

    /**
     * @return Le nom du contact
     */
    public String getNom() {
        return nom;
    }

    public ImageIcon getImage() {
        return image;
    }

    public ContactDTO genererContactDTO() {
        return new ContactDTO(nom, listeNumerosTelephone, image);
    }
}
