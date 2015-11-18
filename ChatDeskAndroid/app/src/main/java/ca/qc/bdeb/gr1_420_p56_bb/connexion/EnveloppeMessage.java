package ca.qc.bdeb.gr1_420_p56_bb.connexion;

import java.util.Date;

/**
 * Contient les informations d'un messsage et permet de le convertir en XML
 * Created by Louis-Simon on 2015-09-07.
 */
public class EnveloppeMessage implements ConvertissableXml {

    private String message;
    private Date date;
    private long numeroTelephone;
    private boolean envoye;

    public EnveloppeMessage(String message, long numeroTelephone, Date date, boolean envoye) {
        this.message = message;
        this.numeroTelephone = numeroTelephone;
        this.date = date;
        this.envoye = envoye;
    }

    public Date getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }

    public long getNumeroTelephone() {
        return numeroTelephone;
    }

    public boolean isEnvoye() {
        return envoye;
    }

    /**
     * Converti les données du message en format XML
     *
     * @return Les données du message en format XML
     */
    @Override
    public String convertirEnXml() {
        return CreateurXMLComm.creationXMLEnveloppe(this);
    }
}
