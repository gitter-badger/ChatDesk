package ca.qc.bdeb.gr1_420_P56_BB.chatDesk;

import java.util.ArrayList;

/**
 * Objet de transfert de données pour une conversation
 * Contient plusieurs message envoyer ou recu
 */
public class ConversationDTO {

    private ArrayList<Message> messages = new ArrayList<>();
    private final long numeroTelephone;

    /**
     * Créer une nouvelle conversation avec un contact et
     * ajoute un ou des messages à celle-ci
     */
   public ConversationDTO(ArrayList<Message> messages, long numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
        this.messages = messages;
    }

    public long getNumeroTelephone() {
        return numeroTelephone;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public Message getLastMessage() {
        return messages.size() - 1 >= 0 ? messages.get(messages.size() - 1) : null;
    }

}