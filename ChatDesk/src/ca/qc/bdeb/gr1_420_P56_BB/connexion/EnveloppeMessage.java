package ca.qc.bdeb.gr1_420_P56_BB.connexion;

import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.Message;

import java.util.ArrayList;
import java.util.Date;

/**
 * Contient les informations d'un messsage et permet de le convertir en XML
 */
class EnveloppeMessage implements ConvertissableXml {

    private final long numeroTelephone;
    private final String text;
    private final Date date;
    private final boolean envoye;

    public EnveloppeMessage(long numeroTelephone, String text, Date date, boolean envoye) {
        this.numeroTelephone = numeroTelephone;
        this.text = text;
        this.date = date;
        this.envoye = envoye;
    }

    public EnveloppeMessage(Message text) {
        this.numeroTelephone = text.getNumeroTelephone();
        this.text = text.getText();
        this.date = text.getDate();
        this.envoye = text.isEnvoyer();
    }

    public long getNumeroTelephone() {
        return numeroTelephone;
    }

    public String getText() {
        return text;
    }

    public Date getDate() {
        return date;
    }

    public boolean isEnvoye() {
        return envoye;
    }

    public Message genererMessage(){
        return new Message(numeroTelephone, text, date, envoye);
    }

    public static ArrayList<Message> ListeEnveloppeMessagesAListeMessages(ArrayList<EnveloppeMessage> enveloppeMessages){
        ArrayList<Message> messages = new ArrayList<>();
        for(EnveloppeMessage enveloppeMessage : enveloppeMessages){
            messages.add(enveloppeMessage.genererMessage());
        }

        return messages;
    }

    /**
     * Converti les données du text en format XML
     *
     * @return Les données du text en format XML
     */
    @Override
    public String convertirEnXml() {
        return CreateurXMLComm.creationXMLEnveloppe(this);
    }
}