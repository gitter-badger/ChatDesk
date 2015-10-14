package ca.qc.bdeb.gr1_420_P56_BB.chatDesk;

import ca.qc.bdeb.gr1_420_P56_BB.connexion.EnveloppeContact;
import ca.qc.bdeb.gr1_420_P56_BB.connexion.EnveloppeMessage;
import ca.qc.bdeb.gr1_420_P56_BB.connexion.GestionnaireConnexion;
import ca.qc.bdeb.gr1_420_P56_BB.connexion.ResultatsConnexion;
import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.Observateur;

import java.util.ArrayList;

/**
 * Created by 1372883 on 2015-09-16.
 */
public class FacadeModele {

    private final GestionnaireConnexion gestionnaireConnexion;
    private final GestionnaireConversation gestionnaireConversation;
    private final GestionnaireContacts gestionnaireContacts;
    private final GestionnaireAppareils gestionnaireAppareils;

    /**
     * Initialise les différents éléments du modèle
     */
    public FacadeModele() {
        gestionnaireConnexion = new GestionnaireConnexion(this);
        gestionnaireContacts = new GestionnaireContacts();
        gestionnaireConversation = new GestionnaireConversation();
        gestionnaireAppareils = new GestionnaireAppareils();
    }

    public void ajouterMessages(ArrayList<EnveloppeMessage> listeEnveloppes) {
        gestionnaireConversation.ajouterMessages(listeEnveloppes);
    }

    /**
     * Ajout le message aux converstions de l'application et l'envoi au téléphone
     * @param numeroTelephone le numéro de tél du destinataire
     * @param message Le contenu du message
     */
    public void envoyerMessage(long numeroTelephone, Message message) {
        gestionnaireConversation.ajouterMessage(numeroTelephone, message);
        gestionnaireConnexion.envoyerEnveloppe(new EnveloppeMessage(message.getText(), numeroTelephone, message.getDate()));
    }

    public void demanderAppareils(){
        this.gestionnaireConnexion.demanderAppareils();
    }

    public void initierLien(int idAppareil){
        gestionnaireConnexion.initierLien(idAppareil);
    }

    public ArrayList<ConversationDTO> getConversations() {
        return this.gestionnaireConversation.getConversationsDTO();
    }

    public void ajouterContacts(ArrayList<Contact> listeContacts) {
        gestionnaireContacts.ajouterContacts(listeContacts);
    }

    public void envoyerContact(EnveloppeContact enveloppe) {
        gestionnaireConnexion.envoyerEnveloppe(enveloppe);
    }

    public Contact getContact(long numeroTelephone) {
        return gestionnaireContacts.getContact(numeroTelephone);
    }

    public void ajouterObservateur(Observateur observateur) {
        this.gestionnaireConversation.ajouterObservateur(observateur);
    }

    public void supprimerObservateur(Observateur observateur) {
        this.gestionnaireConversation.retirerObservateur(observateur);
    }

    public ConversationDTO getConversationDTO(long numeroTelephone) {
        return gestionnaireConversation.getConversationDTO(numeroTelephone);
    }

    public ResultatsConnexion seConnecter(String nom, String pass) {
        return this.gestionnaireConnexion.seConnecter(nom, pass);
    }

    public void setAppareils(Appareil[] appareils) {
        gestionnaireAppareils.setAppareils(appareils);
    }

    public GestionnaireConversation getGestionnaireConversation() {
        return gestionnaireConversation;
    }

    public void arreterProgramme() {
        gestionnaireConnexion.arreterProgramme();
    }

    public GestionnaireAppareils getGestionnaireAppareils() {
        return gestionnaireAppareils;
    }
    public Appareil[] getAppareils(){
        return gestionnaireAppareils.getAppareils();
    }
}
