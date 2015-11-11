package ca.qc.bdeb.gr1_420_P56_BB.chatDesk;

import javax.swing.*;

/**
 * Created by Alexandre on 2015-10-30.
 */
public class ContactDTO {
    /**
     * Le num?ro de t?l?phone du contact
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

    public ContactDTO(String nom, long numeroTelephone, ImageIcon image) {
        this.nom = nom;
        this.numeroTelephone = numeroTelephone;
        this.image = image;
    }

    public String getNom() {
        return nom;
    }

    public long getNumeroTelephone() {
        return numeroTelephone;
    }

    public ImageIcon getImage(){
        return image;
    }
}
