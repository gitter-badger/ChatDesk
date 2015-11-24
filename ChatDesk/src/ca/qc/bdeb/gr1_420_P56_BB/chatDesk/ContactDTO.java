package ca.qc.bdeb.gr1_420_P56_BB.chatDesk;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Alexandre on 2015-10-30.
 */
public class ContactDTO {
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

    public ContactDTO(String nom, ArrayList<Long> listeNumerosTelephone, ImageIcon image) {
        this.nom = nom;
        this.listeNumerosTelephone = listeNumerosTelephone;
        this.image = image;
    }

    public boolean isContactNumeroTelephone(long numeroTelephone) {
        return listeNumerosTelephone.contains(numeroTelephone);
    }

    public String getNom() {
        return nom;
    }

    public ArrayList<Long> getNumeroTelephone() {
        return listeNumerosTelephone;
    }

    public ImageIcon getImage() {
        return image;
    }
}
