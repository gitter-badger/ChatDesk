package ca.qc.bdeb.gr1_420_P56_BB.chatDesk;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 47 on 2015-10-28.
 */
public enum ContactsTest {
    CONTACT_1(12345678l,"Greg"),
    CONTACT_2(22445678l,"Louis-Simon fag"),
    CONTACT_3(32645678l,"Louis-Simon fagerester"),
    CONTACT_4(42744678l,"Gabriel faggest");
    private final Contact contact;
    private List<ContactPourQueCaFonctionneDTO> listeContact;
    ContactsTest(long numero, String nom) {
        this.contact = new Contact(numero, nom);
        listeContact = new ArrayList<ContactPourQueCaFonctionneDTO>();
    }

    public Contact getContact() {
        return contact;
    }

    public static ArrayList<ContactPourQueCaFonctionneDTO> asList(){
        ArrayList<ContactPourQueCaFonctionneDTO> listeContacts = new ArrayList<>();

        for (ContactsTest contactsTest : values()){
            listeContacts.add(contactsTest.getContact().genererContactDTO());
        }

        return listeContacts;
    }
}
