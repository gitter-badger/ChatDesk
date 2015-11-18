package ca.qc.bdeb.gr1_420_P56_BB.chatDesk;

import ca.qc.bdeb.gr1_420_P56_BB.connexion.EnveloppeContact;
import ca.qc.bdeb.gr1_420_P56_BB.connexion.EnveloppeMessage;
import ca.qc.bdeb.gr1_420_P56_BB.connexion.GestionnaireCommunication;
import ca.qc.bdeb.gr1_420_P56_BB.connexion.ResultatsConnexion;
import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.Observateur;
import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.ObservateurErreur;

import java.util.ArrayList;

/**
 * La facade du modèle pour la communication avec tous ce qui n'est pas interface.
 */
public class FacadeModele {

    private final GestionnaireCommunication gestionnaireCommunication;
    private final GestionnaireConversation gestionnaireConversation;
    private final GestionnaireContacts gestionnaireContacts;
    private final GestionnaireAppareils gestionnaireAppareils;

    /**
     * Initialise les différents éléments du modèle
     */
    public FacadeModele() {
        gestionnaireCommunication = new GestionnaireCommunication(this);
        gestionnaireContacts = new GestionnaireContacts();
        gestionnaireConversation = new GestionnaireConversation();
        gestionnaireAppareils = new GestionnaireAppareils();
    }

    public GestionnaireContacts getGestionnaireContacts() {
        return gestionnaireContacts;
    }
    public ArrayList<ContactDTO> getContacts(){
        return gestionnaireContacts.getContactsDTO();
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
        gestionnaireCommunication.envoyerEnveloppe(new EnveloppeMessage(message.getText(), numeroTelephone, message.getDate(), true));
    }

    public void demanderAppareils(){
        this.gestionnaireCommunication.demanderAppareils();
    }

    public void initierLien(int idAppareil){
        gestionnaireCommunication.initierLien(idAppareil);
    }

    public ArrayList<ConversationDTO> getConversations() {
        return this.gestionnaireConversation.getConversationsDTO();
    }

    public void ajouterContacts(ArrayList<Contact> listeContacts) {
        gestionnaireContacts.ajouterContacts(listeContacts);
    }

    public void envoyerContact(EnveloppeContact enveloppe) {
        gestionnaireCommunication.envoyerEnveloppe(enveloppe);
    }

    public Contact getContact(long numeroTelephone) {
        return gestionnaireContacts.getContact(numeroTelephone);
    }

    public void ajouterObservateur(Observateur observateur) {
        this.gestionnaireConversation.ajouterObservateur(observateur);
    }

    public void ajouterObservateurErreur(ObservateurErreur observateur) {
        this.gestionnaireCommunication.ajouterObservateurErreur(observateur);
    }

    public void supprimerObservateur(Observateur observateur) {
        this.gestionnaireConversation.retirerObservateur(observateur);
    }

    public ConversationDTO getConversationDTO(long numeroTelephone) {
        return gestionnaireConversation.getConversationDTO(numeroTelephone);
    }

    public ResultatsConnexion seConnecter(String nom, String pass) {
        return this.gestionnaireCommunication.seConnecter(nom, pass);
    }

    public void setAppareils(Appareil[] appareils) {
        gestionnaireAppareils.setAppareils(appareils);
    }

    public GestionnaireConversation getGestionnaireConversation() {
        return gestionnaireConversation;
    }

    public void arreterProgramme() {
        gestionnaireCommunication.arreterProgramme();
    }

    public GestionnaireAppareils getGestionnaireAppareils() {
        return gestionnaireAppareils;
    }
    public Appareil[] getAppareils(){
        return gestionnaireAppareils.getAppareils();
    }
}
