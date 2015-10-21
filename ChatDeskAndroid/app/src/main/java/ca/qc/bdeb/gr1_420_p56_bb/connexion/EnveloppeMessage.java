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
        return new XMLWriter().construireXmlCommunication(CommandesClient.MESSAGES, new EnveloppeMessage[]{this});
    }
}