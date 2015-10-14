package ca.qc.bdeb.gr1_420_P56_BB.chatDesk;

import java.util.Date;

/**
 * Un text (sms)
 */
public class Message {

    /**
     * Le contenu du message
     */
    private final String text;

    /**
     * La date que le message à été recu ou envoyer
     */
    private final Date date;

    /**
     * Si le message est envoyé ou recu
     */
    private final boolean envoyer;

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
