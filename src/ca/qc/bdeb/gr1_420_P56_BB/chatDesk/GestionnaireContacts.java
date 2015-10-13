package ca.qc.bdeb.gr1_420_P56_BB.chatDesk;

import java.util.ArrayList;

/**
 * Created by 1372883 on 2015-09-16.
 */
class GestionnaireContacts {

    /**
     * Liste de tous les contacts présentement chargés dans le programme
     */
    private ArrayList<Contact> listeContacts;

    public GestionnaireContacts() {
        this.listeContacts = new ArrayList<>();
    }

    protected void ajouterContact(Contact contact) {
        listeContacts.add(contact);
    }

    protected void ajouterContacts(ArrayList<Contact> listeContacts) {
        for (Contact contact : listeContacts) {
            this.listeContacts.add(contact);
        }
    }

    protected void supprimerContact(Contact contact) {
        listeContacts.remove(contact);
    }

    protected void supprimerContact(int position) {
        listeContacts.remove(position);
    }

    protected ArrayList getContacts() {
        return listeContacts;
    }

    /**
     * Trouve un contact par son numéro de téléphone
     *
     * @param numeroTelephone Le numéro de téléphone du contact à retourner
     * @return Le contact possédant ce numéro de téléphone
     */
    protected Contact getContact(long numeroTelephone) {
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

        return contact;
    }

    /**
     * Trouve un contact par son nom
     * @param nom Le nom du contact à retourner
     * @return Le contact possédant ce nom
     */
    protected Contact getContact(String nom) {
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
}
