package ca.qc.bdeb.gr1_420_P56_BB.connexion;

import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.Appareil;
import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.Contact;
import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.FacadeModele;
import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.Message;
import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.ObservateurErreur;

import java.util.ArrayList;

/**
 * Created by Alexandre on 2015-11-17.
 */
public class FacadeConnexion {

    private FacadeModele facadeModele;
    private GestionnaireCommunication gestionnaireCommunication;

    public FacadeConnexion(FacadeModele facadeModele){
        this.facadeModele = facadeModele;
        this.gestionnaireCommunication = new GestionnaireCommunication(this);
    }

    public void envoyerMessage(Message message) {
        this.gestionnaireCommunication.envoyerEnveloppe(new EnveloppeMessage(message));
    }

    public void envoyerContact(Contact contact) {
        this.gestionnaireCommunication.envoyerEnveloppe(new EnveloppeContact(contact));
    }

    public ResultatsConnexion seConnecter(String nom, String pass) {
        return this.gestionnaireCommunication.seConnecter(nom, pass);
    }

    public void demanderAppareils() {
        this.gestionnaireCommunication.demanderAppareils();
    }

    public void initierLien(int idAppareil) {
        this.gestionnaireCommunication.initierLien(idAppareil);
    }

    public void ajouterObservateurErreur(ObservateurErreur observateur) {
        this.gestionnaireCommunication.ajouterObservateurErreur(observateur);
    }

    public void arreterProgramme() {
        this.gestionnaireCommunication.arreterProgramme();
    }

    public void setAppareils(Appareil[] tabAppareils) {
        this.facadeModele.setAppareils(tabAppareils);
    }

    public void ajouterContacts(ArrayList<EnveloppeContact> enveloppeContacts) {
        this.facadeModele.ajouterContacts(EnveloppeContact.ListeEnveloppeContactsAListeContacts(enveloppeContacts));
    }

    public void ajouterMessages(ArrayList<EnveloppeMessage> enveloppeMessages) {
        this.facadeModele.ajouterMessages(EnveloppeMessage.ListeEnveloppeMessagesAListeMessages(enveloppeMessages));
    }

    public FacadeModele getFacadeModele() {
        return this.facadeModele;
    }

}
