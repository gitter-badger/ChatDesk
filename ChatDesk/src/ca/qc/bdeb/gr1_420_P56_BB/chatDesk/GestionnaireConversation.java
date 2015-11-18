package ca.qc.bdeb.gr1_420_P56_BB.chatDesk;

import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.ObservableMessage;
import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.ObservateurMessage;

import java.util.ArrayList;

/**
 * Gère les conversations
 */
public class GestionnaireConversation implements ObservableMessage {

    /**
     * Liste des observateurMessages
     */
    private final ArrayList<ObservateurMessage> observateurMessages = new ArrayList<>();

    /**
     * Liste des conversations
     */
    private final ArrayList<Conversation> conversations = new ArrayList<>();

    private boolean premiereAjout = true;

    /**
     * Le constructeur du gestionnaire de conversation
     */
    GestionnaireConversation() {
    }

    void ajouterMessages(ArrayList<Message> listeMessages) {
        for (Message message : listeMessages) {
            ajouterMessage(message);
        }

        if(premiereAjout){
            aviserObservateursPremiereAjout();
            premiereAjout = false;
        }
    }

    /**
     * Ajouter un message à la bonne conversation
     * Si aucune conversation, en créé une
     */
    void ajouterMessage(Message message) {
        boolean trouver = false;
        int ligne = 0;
        Conversation conversation = null;

        while (!trouver && ligne < conversations.size()) {
            conversation = conversations.get(ligne);
            ligne++;
            if (message.getNumeroTelephone() == conversation.getNumeroTelephone()) {
                faireMonterConversation(message, conversation);
                trouver = true;
            }
        }

        if (!trouver) {
            conversation = new Conversation(message, message.getNumeroTelephone());
            conversations.add(0, conversation);
        }

        if (conversation != null && !premiereAjout) {
            aviserObservateursMessageRecu(conversation.getNumeroTelephone());
        }
    }

    /**
     * Réordonne la liste de message pour mettre le plus récent en haut
     *
     * @param message
     * @param conversation
     */
    private void faireMonterConversation(Message message, Conversation conversation) {
        conversation.ajouterMessage(message);
        conversations.remove(conversation);
        conversations.add(0, conversation);
    }

    ArrayList<ConversationDTO> getConversationsDTO() {
        return convertirConversationsToConversationsDTO();
    }

    private ArrayList<ConversationDTO> convertirConversationsToConversationsDTO() {
        ArrayList<ConversationDTO> conversationsDTO = new ArrayList<>();
        for (Conversation conversation : conversations) {
            conversationsDTO.add(conversation.genererDTO());
        }
        return conversationsDTO;
    }

    ConversationDTO getConversationDTO(long numeroTelephone) {
        ConversationDTO conversationDTO = null;

        for (int i = 0; i < conversations.size(); i++) {
            if (conversations.get(i).getNumeroTelephone() == numeroTelephone) {
                conversationDTO = conversations.get(i).genererDTO();
                i = conversations.size();
            }
        }

        return conversationDTO;
    }

    @Override
    public void ajouterObservateur(ObservateurMessage ob) {
        observateurMessages.add(ob);
    }

    @Override
    public void retirerObservateur(ObservateurMessage ob) {
        observateurMessages.remove(ob);
    }

    @Override
    public void aviserObservateursMessageRecu(long num) {
        for (ObservateurMessage ob : observateurMessages) {
            ob.receptionMessage(num);
        }
    }

    @Override
    public void aviserObservateursPremiereAjout(){
        for (ObservateurMessage ob : observateurMessages) {
            ob.finReceptionConnexionInitiale();
        }
    }
}
