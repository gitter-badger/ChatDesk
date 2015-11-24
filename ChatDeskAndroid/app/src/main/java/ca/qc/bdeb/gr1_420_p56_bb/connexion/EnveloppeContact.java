package ca.qc.bdeb.gr1_420_p56_bb.connexion;

import java.util.ArrayList;

/**
 * Contient les informations d'un contact et permet de le convertir en XML
 * Created by Alexandre on 2015-09-22.
 */
public class EnveloppeContact implements ConvertissableXml {
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
    private final String image;

    /**
     * Constructeur qui permet de crée un contact
     *
     * @param listeNumerosTelephone Le numéro de téléphone du contact
     * @param nom Le nom du contact
     */
    public EnveloppeContact(ArrayList<Long> listeNumerosTelephone, String nom, String image){
        this.listeNumerosTelephone = listeNumerosTelephone;
        this.nom = nom;
        this.image = image;
    }

    /**
     * @return Le numéro de téléphone du contact
     */
    public ArrayList<Long> getListeNumeroTelephones() {
        return listeNumerosTelephone;
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
    public String getImage(){
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
