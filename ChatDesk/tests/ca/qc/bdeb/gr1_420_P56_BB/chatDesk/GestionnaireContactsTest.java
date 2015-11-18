package ca.qc.bdeb.gr1_420_P56_BB.chatDesk;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Alexandre on 2015-09-21.
 */
public class GestionnaireContactsTest {

    private final Contact CONTACT_1 = new Contact(1, "A", null);
    private final Contact CONTACT_2 = new Contact(2, "B", null);
    private final Contact CONTACT_3 = new Contact(3, "C", null);
    private final Contact CONTACT_4 = new Contact(4, "D", null);

    private GestionnaireContacts gestionnaireContacts;

    @Before
    public void setUp() {
        gestionnaireContacts = new GestionnaireContacts();
        gestionnaireContacts.ajouterContact(CONTACT_1);
        gestionnaireContacts.ajouterContact(CONTACT_2);
        gestionnaireContacts.ajouterContact(CONTACT_3);
        gestionnaireContacts.ajouterContact(CONTACT_4);
    }

    @Test
    public void testRecupererContactParNumeroNonExistant() {
        Assert.assertEquals("", gestionnaireContacts.getContact(5).getNom());
    }

    @Test
    public void testRecupererContactParNumeroExistant() {
        Assert.assertEquals(CONTACT_2, gestionnaireContacts.getContact(2));
    }

    @Test
    public void testRecupererContactParNom(){
        Assert.assertEquals(CONTACT_3, gestionnaireContacts.getContact("C"));
        Assert.assertNull(gestionnaireContacts.getContact("H"));
    }

    @Test
    public void testSupprimerContact(){
        gestionnaireContacts.supprimerContact(CONTACT_1);

        Assert.assertEquals(3, gestionnaireContacts.getContacts().size());
        Assert.assertEquals(CONTACT_2, gestionnaireContacts.getContacts().get(0));
        Assert.assertEquals(CONTACT_3, gestionnaireContacts.getContacts().get(1));
        Assert.assertEquals(CONTACT_4, gestionnaireContacts.getContacts().get(2));
    }

    @Test
    public void testSupprimerContactPosition(){
        gestionnaireContacts.supprimerContact(0);

        Assert.assertEquals(3, gestionnaireContacts.getContacts().size());
        Assert.assertEquals(CONTACT_2, gestionnaireContacts.getContacts().get(0));
        Assert.assertEquals(CONTACT_3, gestionnaireContacts.getContacts().get(1));
        Assert.assertEquals(CONTACT_4, gestionnaireContacts.getContacts().get(2));
    }

    @Test
    public void testAjoutContacts(){
        ArrayList<Contact> contacts = new ArrayList<>();
        contacts.add(CONTACT_1);
        contacts.add(CONTACT_2);
        contacts.add(CONTACT_3);
        contacts.add(CONTACT_4);
        gestionnaireContacts.ajouterContacts(contacts);

        Assert.assertEquals(8, gestionnaireContacts.getContacts().size());
    }
}