package ca.qc.bdeb.gr1_420_P56_BB.connexion;

import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.Contact;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Contient les informations d'un contact et permet de le convertir en XML
 */
class EnveloppeContact implements ConvertissableXml {

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

    public EnveloppeContact(Contact contact){
        this.numeroTelephone = contact.getNumeroTelephone();
        this.nom = contact.getNom();
        this.image = contact.getImage();
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

    public Contact genererContact(){
        return new Contact(numeroTelephone, nom, image);
    }

    public static ArrayList<Contact> ListeEnveloppeContactsAListeContacts(ArrayList<EnveloppeContact> enveloppeContacts){
        ArrayList<Contact> contacts = new ArrayList<>();
        for(EnveloppeContact enveloppeContact : enveloppeContacts){
            contacts.add(enveloppeContact.genererContact());
        }

        return contacts;
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