package ca.qc.bdeb.gr1_420_P56_BB.chatDesk;

import java.util.ArrayList;

/**
 * Created by Alexandre on 2015-09-04.
 * <p>
 * Une conversation basée sur un contact du téléphone ou un numéro de téléphone
 * Contient plusieurs message envoyer ou recu
 */
class Conversation {

    /**
     * Liste des messages d'une conversation
     */
    private ArrayList<Message> messages = new ArrayList<>();

    /**
     * Numéro de téléphone du contact avec qui la conversation a lieu
     */
    private long numeroTelephone;

    /**
     * Constructeur par défaut d'une conversation
     * Créer une conversation vide
     */
    public Conversation() {
    }

    /**
     * Constructeur qui crée une nouvelle conversation avec le numéro d'un contact
     *
     * @param numeroTelephone Le numero de téléphone d'un contact
     */
    Conversation(long numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    /**
     * Constructeur qui crée une nouvelle conversation avec un contact et
     * ajoute un message à cette conversation
     *
     * @param message
     */
    Conversation(Message message, long numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
        messages.add(message);
    }

    /**
     * Ajout d'un message a la conversation
     *
     * @param message Un message
     */
    public void ajouterMessage(Message message) {
        messages.add(message);
    }

    /**
     * @return Le numéro de téléphone du contact avec qui la conversation a lieu
     */
    long getNumeroTelephone() {
        return numeroTelephone;
    }

    /**
     * @return Liste des messages échangés
     */
    public ArrayList<Message> getMessages() {
        return messages;
    }

    /**
     * Permet de récupérer le dernier message envoyé
     *
     * @return Le dernier message envoyé
     */
    public Message getLastMessage() {
        return messages.size() - 1 >= 0 ? messages.get(messages.size() - 1) : null;
    }

    ConversationDTO genererDTO(){
        return new ConversationDTO(this.getMessages(), this.getNumeroTelephone());
    }
}