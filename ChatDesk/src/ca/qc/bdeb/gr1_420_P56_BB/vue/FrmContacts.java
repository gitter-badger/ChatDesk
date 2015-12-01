package ca.qc.bdeb.gr1_420_P56_BB.vue;

import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.ContactDTO;
import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.FacadeModele;
import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.Formatage;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Alexandre on 2015-11-30.
 */
public class FrmContacts extends JFrame {

    private static final int PREMIER_NUMERO_CONTACT = 0;
    private static final String TEXTE_VIDE = "";
    private static final int SIZE_IMAGE_CARREE = 40;

    private FrmChatDesk frmChatDesk;
    private FacadeModele facadeModele;

    private JList lstContacts;
    private JList lstNumeroTelephone;

    private JPanel pnlInfoContact;

    private ArrayList<ContactDTO> listeContacts;

    private DefaultListModel modelListContact;
    private DefaultListModel modelListNumeroTelephone;

    /**
     * Contient le nom du contact ainsi que sont image
     */
    private JLabel lblImageNomContact;

    private ContactDTO contactSelectionne;


    public FrmContacts(FrmChatDesk frmChatDesk) {
        this.frmChatDesk = frmChatDesk;
        this.facadeModele = frmChatDesk.getFacadeModele();

        this.setLayout(new GridLayout(1, 2));

        listeContacts = facadeModele.getContacts();

        initialiserListeContacts();

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }


    private void initialiserListeContacts() {
        modelListContact = new DefaultListModel();
        lstContacts = new JList();
        lstContacts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstContacts.setModel(modelListContact);
        lstContacts.addListSelectionListener(listSelectionEvent -> {
            contactSelectionne = listeContacts.get(lstContacts.getSelectedIndices()[0]);
            initialiserPnlInfoContact();
        });

        JScrollPane scrollpane = new JScrollPane(lstContacts);

        remplirListeContact();

        this.add(scrollpane);
    }

    private void remplirListeContact() {
        for (ContactDTO contact : listeContacts) {
            if (contact.getNom() != null && !contact.getNom().isEmpty()) {
                modelListContact.addElement(contact.getNom());
            } else {
                modelListContact.addElement(Long.toString(contact.getNumeroTelephone().get(PREMIER_NUMERO_CONTACT)));
            }
        }
    }

    private void initialiserPnlInfoContact() {
        if (pnlInfoContact != null) {
            this.remove(pnlInfoContact);
        }

        pnlInfoContact = new JPanel();
        pnlInfoContact.setLayout(new GridLayout(2, 1));

        modelListNumeroTelephone = new DefaultListModel();
        lstNumeroTelephone = new JList();
        lstNumeroTelephone.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstNumeroTelephone.setModel(modelListNumeroTelephone);
        remplirListeNumeroTelephone();

        JScrollPane scrollpane = new JScrollPane(lstNumeroTelephone);

        JPanel pnlNumeroTelephone = new JPanel();
        pnlNumeroTelephone.setLayout(new BoxLayout(pnlNumeroTelephone, BoxLayout.Y_AXIS));

        JLabel lbl = new JLabel("Numéros de téléphone : ");

        lblImageNomContact = new JLabel();

        if (contactSelectionne.getNom() != TEXTE_VIDE) {
            lblImageNomContact.setText(contactSelectionne.getNom());
        }
        if (contactSelectionne.getImage() != null) {
            lblImageNomContact.setIcon(Formatage.redimensionnerImage(contactSelectionne.getImage(), SIZE_IMAGE_CARREE, SIZE_IMAGE_CARREE));
        } else {
            lblImageNomContact.setIcon(Formatage.redimensionnerImage(FrmChatDesk.IMAGE_CONTACT_DEFAUT, SIZE_IMAGE_CARREE, SIZE_IMAGE_CARREE));
        }

        JButton bouton = new JButton("Envoyer un message");
        bouton.addActionListener(actionEvent -> contacter());

        pnlNumeroTelephone.add(lbl);
        pnlNumeroTelephone.add(scrollpane);
        pnlNumeroTelephone.add(bouton);

        pnlInfoContact.add(lblImageNomContact);
        pnlInfoContact.add(pnlNumeroTelephone);

        this.add(pnlInfoContact);
        this.pack();
    }

    private void remplirListeNumeroTelephone() {
        for (Long numeroTelephone : contactSelectionne.getNumeroTelephone()) {
            modelListNumeroTelephone.addElement(Long.toString(numeroTelephone));
        }
    }

    private void contacter() {
        if (lstNumeroTelephone.getSelectedIndices().length > 0) {
            Long numeroTelephone = contactSelectionne.getNumeroTelephone().get(lstNumeroTelephone.getSelectedIndices()[0]);
            facadeModele.ajouterConversation(numeroTelephone);
            frmChatDesk.ouvrirConversation(facadeModele.getConversationDTO(numeroTelephone));
            this.dispose();
        }
    }
}
