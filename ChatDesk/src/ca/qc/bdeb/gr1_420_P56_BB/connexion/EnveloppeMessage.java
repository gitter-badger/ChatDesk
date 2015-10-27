package ca.qc.bdeb.gr1_420_P56_BB.connexion;

import java.util.Date;

/**
 * Contient les informations d'un messsage et permet de le convertir en XML
 */
public class EnveloppeMessage implements ConvertissableXml {

    private final String message;
    private final Date date;
    private final long numeroTelephone;
    private final boolean envoye;

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
        return new XMLWriter().construireXmlCommunication(new EnveloppeMessage[]{this});
    }
}
