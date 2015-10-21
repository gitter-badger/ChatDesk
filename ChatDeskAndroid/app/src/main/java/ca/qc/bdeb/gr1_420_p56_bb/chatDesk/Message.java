package ca.qc.bdeb.gr1_420_p56_bb.chatDesk;

import java.util.Date;

/**
 * Created by Alexandre on 2015-09-04.
 * <p>
 * Un text (sms)
 */
public class Message {

    /**
     * Le contenu du message
     */
    private String text;

    /**
     * La date que le message à été recu ou envoyer
     */
    private Date date;

    /**
     * Si le message est envoyé ou recu
     */
    private boolean envoyer;

    /**
     * @param text    Le text
     * @param date    La date que le text à été recu ou envoyer
     * @param envoyer Si le text est envoyé ou recu
     */
    public Message(String text, Date date, boolean envoyer) {
        this.text = text;
        this.date = date;
        this.envoyer = envoyer;
    }
    public String getText() {
        return text;
    }

    public Date getDate() {
        return date;
    }

    public boolean isEnvoyer() {
        return envoyer;
    }
}