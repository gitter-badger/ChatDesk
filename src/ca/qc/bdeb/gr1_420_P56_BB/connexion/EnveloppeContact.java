package ca.qc.bdeb.gr1_420_P56_BB.connexion;

/**
 * Contient les informations d'un contact et permet de le convertir en XML
 */
public class EnveloppeContact implements ConvertissableXml {
    /**
     * Le numéro de téléphone du contact
     */
    private final long numeroTelephone;

    /**
     * Le nom du contact
     */
    private final String nom;

    public EnveloppeContact(long numeroTelephone) {
        this.numeroTelephone = 9367457456l;
        this.nom = "jacques";
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
        return new XMLWriter().construireXmlCommunication(new EnveloppeContact[]{this});
    }
}
