package ca.qc.bdeb.gr1_420_p56_bb.connexion;

/**
 * Contient les informations d'un contact et permet de le convertir en XML
 * Created by Alexandre on 2015-09-22.
 */
public class EnveloppeContact implements ConvertissableXml {
    /**
     * Le numéro de téléphone du contact
     */
    private long numeroTelephone;

    /**
     * Le nom du contact
     */
    private String nom;

    /**
     * L'image du profil en chaine de caractère
     */
    private String image;

    public EnveloppeContact(long numeroTelephone, String nom, String image) {
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
     * @return L'image du profile en chaine de caractère
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
        return new XMLWriter().construireXmlCommunication(CommandesClient.CONTACTS, new EnveloppeContact[]{this});
    }
}
