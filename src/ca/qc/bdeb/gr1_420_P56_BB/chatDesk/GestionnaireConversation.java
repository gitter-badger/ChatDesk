package ca.qc.bdeb.gr1_420_P56_BB.chatDesk;

import ca.qc.bdeb.gr1_420_P56_BB.connexion.EnveloppeMessage;
import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.Observable;
import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.Observateur;

import java.util.ArrayList;

/**
 * Created by Alexandre on 2015-09-02.
 * <p>
 * Gère les conversations
 */
public class GestionnaireConversation implements Observable {

    /**
     * Liste des observateurs
     */
    private ArrayList<Observateur> observateurs = new ArrayList<>();

    /**
     * Liste des conversations
     */
    private ArrayList<Conversation> conversations = new ArrayList<>();

    /**
     * Le constructeur du gestionnaire de conversation
     */
    GestionnaireConversation() {
    }

    void ajouterMessages(ArrayList<EnveloppeMessage> listeEnveloppes) {
        for (EnveloppeMessage enveloppe : listeEnveloppes) {
            ajouterMessage(enveloppe.getNumeroTelephone(), creerMessage(enveloppe));
        }
    }

    private Message creerMessage(EnveloppeMessage enveloppe) {
        return new Message(enveloppe.getMessage(), enveloppe.getDate(), false);
    }

    /**
     * Ajouter un message à la bonne conversation
     * Si aucune conversation, en créé une
     */
    void ajouterMessage(long numeroTelephone, Message message) {
        boolean trouver = false;
        int ligne = 0;
        Conversation conversation = null;

        while (!trouver && ligne < conversations.size()) {
            conversation = conversations.get(ligne);
            ligne++;
            if (numeroTelephone == conversation.getNumeroTelephone()) {
                faireMonterConversation(message, conversation);
                trouver = true;
            }
        }

        if (!trouver) {
            conversations.add(0, new Conversation(message, numeroTelephone));
        }
        if (conversation != null) {
            aviserObservateurs(conversation.getNumeroTelephone());
        }
    }

    /**
     * Réordonne la liste de message pour mettre le plus récent en haut
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

    ArrayList<ConversationDTO> convertirConversationsToConversationsDTO() {
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
    public void ajouterObservateur(Observateur ob) {
        observateurs.add(ob);
    }

    @Override
    public void retirerObservateur(Observateur ob) {
        observateurs.remove(ob);
    }

    @Override
    public void retirerObservateur(int indice) {
        observateurs.remove(indice);
    }

    @Override
    public void aviserObservateurs(long num) {
        for (Observateur ob : observateurs) {
            ob.changementEtat(num);
        }
    }

    @Override
    public void aviserObservateur(int indice, long num) {
        observateurs.get(indice).changementEtat(num);
    }



}
