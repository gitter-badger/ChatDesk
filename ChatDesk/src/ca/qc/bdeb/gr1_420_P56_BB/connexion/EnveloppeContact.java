package ca.qc.bdeb.gr1_420_P56_BB.connexion;

import javax.swing.*;

/**
 * Contient les informations d'un contact et permet de le convertir en XML
 */
public class EnveloppeContact implements ConvertissableXml {

    /**
     * Le numï¿½ro de tï¿½lï¿½phone du contact
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
    public EnveloppeContact(long numeroTelephone, String nom, ImageIcon image){
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

    /**
     * @return L'image de l'utilisateur
     */
    public ImageIcon getImage(){
        return image;
    }

    /**
     * Converti les données du contact en format XML
     * @return Les données du contact en format XML
     */
    @Override
    public String convertirEnXml() {
        return CreateurXMLComm.creationXMLEnveloppe(this);
    }
}