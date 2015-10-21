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

    public EnveloppeContact(long numeroTelephone, String nom) {
        this.numeroTelephone = numeroTelephone;
        this.nom = nom;
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
     * Converti les données du contact en format XML
     * @return Les données du contact en format XML
     */
    @Override
    public String convertirEnXml() {
        return new XMLWriter().construireXmlCommunication(CommandesClient.CONTACTS, new EnveloppeContact[]{this});
    }
}
