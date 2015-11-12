package ca.qc.bdeb.gr1_420_P56_BB.chatDesk;

import java.util.ArrayList;

/**
 * Gère les contacts
 */
class GestionnaireContacts {

    /**
     * Liste de tous les contacts présentement chargés dans le programme
     */
    private final ArrayList<Contact> listeContacts;

    public GestionnaireContacts() {
        this.listeContacts = new ArrayList<>();
    }

    void ajouterContact(Contact contact) {
        listeContacts.add(contact);
    }

    void ajouterContacts(ArrayList<Contact> listeContacts) {
        for (Contact contact : listeContacts) {
            this.listeContacts.add(contact);
        }
    }

    void supprimerContact(Contact contact) {
        listeContacts.remove(contact);
    }

    void supprimerContact() {
        listeContacts.remove(0);
    }

    ArrayList getContacts() {
        return  listeContacts;
    }

    /**
     * Trouve un contact par son numéro de téléphone
     *
     * @param numeroTelephone Le numéro de téléphone du contact à retourner
     * @return Le contact possédant ce numéro de téléphone
     */
    Contact getContact(long numeroTelephone) {
        int position = 0;
        Contact contact = null;
        boolean trouve = false;

        while (position < listeContacts.size() && !trouve) {
            if (listeContacts.get(position).getNumeroTelephone() == numeroTelephone) {
                contact = listeContacts.get(position);
                trouve = true;
            }
            position++;
        }

        if(contact == null){
            contact = new Contact(numeroTelephone, "", null);
        }

        return contact;
    }

    /**
     * Trouve un contact par son nom
     * @param nom Le nom du contact à retourner
     * @return Le contact possédant ce nom
     */
    Contact getContact(String nom) {
        int position = 0;
        Contact contact = null;
        boolean rechercheTermine = false;

        while (position < listeContacts.size() && !rechercheTermine) {
            if (listeContacts.get(position).getNom().equals(nom)) {
                contact = listeContacts.get(position);
                rechercheTermine = true;
            }
            position++;
        }

        return contact;
    }
    public ArrayList<ContactDTO> getContactsDTO(){
        ArrayList<ContactDTO> contactDTOs = new ArrayList<>();
        for (Contact contact: listeContacts) {
            contactDTOs.add(contact.genererContactDTO());
        }
        return contactDTOs;
    }
}
