package ca.qc.bdeb.gr1_420_P56_BB.connexion;

import java.util.Date;

/**
 * Contient les informations d'un messsage et permet de le convertir en XML
 */
public class EnveloppeMessage implements ConvertissableXml {

    private final String message;
    private final Date date;
    private final long numeroTelephone;

    public EnveloppeMessage(String message, long numeroTelephone, Date date) {
        this.message = message;
        this.numeroTelephone = numeroTelephone;
        this.date = date;
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

    /**
     * Converti les données du message en format XML
     * @return Les données du message en format XML
     */
    @Override
    public String convertirEnXml() {
        return new XMLWriter().construireXmlCommunication(new EnveloppeMessage[]{this});
    }
}
