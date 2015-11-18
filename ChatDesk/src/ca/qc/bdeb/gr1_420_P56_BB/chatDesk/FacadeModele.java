package ca.qc.bdeb.gr1_420_P56_BB.chatDesk;
import ca.qc.bdeb.gr1_420_P56_BB.connexion.FacadeConnexion;
import ca.qc.bdeb.gr1_420_P56_BB.connexion.ResultatsConnexion;
import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.ObservateurMessage;
import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.ObservateurErreur;

import java.util.ArrayList;

/**
 * La facade du modèle pour la communication avec tous ce qui n'est pas interface.
 */
public class FacadeModele {

    private final FacadeConnexion facadeConnexion;
    private final GestionnaireConversation gestionnaireConversation;
    private final GestionnaireContacts gestionnaireContacts;
    private final GestionnaireAppareils gestionnaireAppareils;

    /**
     * Initialise les différents éléments du modèle
     */
    public FacadeModele() {
        facadeConnexion = new FacadeConnexion(this);
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
    public void ajouterMessages(ArrayList<Message> listeMessages) {
        gestionnaireConversation.ajouterMessages(listeMessages);
    }

    /**
     * Ajout le message aux converstions de l'application et l'envoi au téléphone
     * @param message Le contenu du message
     */
    public void envoyerMessage(Message message) {
        gestionnaireConversation.ajouterMessage(message);
        facadeConnexion.envoyerMessage(message);
    }

    public void envoyerContact(Contact contact) {
        facadeConnexion.envoyerContact(contact);
    }

    public ResultatsConnexion seConnecter(String nom, String pass) {
        return this.facadeConnexion.seConnecter(nom, pass);
    }

    public boolean sinscrire(String nom, String pass){
        return facadeConnexion.sinscrire(nom, pass);
    }

    public void demanderAppareils(){
        this.facadeConnexion.demanderAppareils();
    }

    public void initierLien(int idAppareil){
        facadeConnexion.initierLien(idAppareil);
    }

    public void ajouterObservateurErreur(ObservateurErreur observateur) {
        this.facadeConnexion.ajouterObservateurErreur(observateur);
    }

    public void arreterProgramme() {
        facadeConnexion.arreterProgramme();
    }

    public ArrayList<ConversationDTO> getConversations() {
        return this.gestionnaireConversation.getConversationsDTO();
    }

    public void ajouterContacts(ArrayList<Contact> listeContacts) {
        gestionnaireContacts.ajouterContacts(listeContacts);
    }


    public Contact getContact(long numeroTelephone) {
        return gestionnaireContacts.getContact(numeroTelephone);
    }

    public void ajouterObservateur(ObservateurMessage observateurMessage) {
        this.gestionnaireConversation.ajouterObservateur(observateurMessage);
    }

    public void supprimerObservateur(ObservateurMessage observateurMessage) {
        this.gestionnaireConversation.retirerObservateur(observateurMessage);
    }

    public ConversationDTO getConversationDTO(long numeroTelephone) {
        return gestionnaireConversation.getConversationDTO(numeroTelephone);
    }

    public void setAppareils(Appareil[] appareils) {
        gestionnaireAppareils.setAppareils(appareils);
    }

    public GestionnaireConversation getGestionnaireConversation() {
        return gestionnaireConversation;
    }

    public GestionnaireAppareils getGestionnaireAppareils() {
        return gestionnaireAppareils;
    }

    public Appareil[] getAppareils(){
        return gestionnaireAppareils.getAppareils();
    }
}
