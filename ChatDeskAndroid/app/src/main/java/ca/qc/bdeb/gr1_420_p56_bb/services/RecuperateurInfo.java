package ca.qc.bdeb.gr1_420_p56_bb.services;

import android.app.Service;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

import ca.qc.bdeb.gr1_420_p56_bb.connexion.EnveloppeContact;
import ca.qc.bdeb.gr1_420_p56_bb.connexion.EnveloppeMessage;
import ca.qc.bdeb.gr1_420_p56_bb.utilitaires.Formatage;

import static android.provider.ContactsContract.CommonDataKinds.Phone.*;
import static ca.qc.bdeb.gr1_420_p56_bb.utilitaires.Formatage.convertirNumeroTelephoneEnLong;

/**
 * Created by Alexandre on 2015-10-27.
 */
public class RecuperateurInfo {

    private static int VALEUR_MESSAGE_ENVOYER = 2;

    static ArrayList<EnveloppeMessage> lireTousMessage(Service service) {
        ArrayList<EnveloppeMessage> enveloppeMessages = new ArrayList<>();
        Uri uri = Uri.parse("content://sms/");
        Cursor cur = service.getContentResolver().query(uri, null, null, null, null);

        if (cur.moveToFirst()) {
            for (int i = 0; i < cur.getCount(); i++) {
                String message = cur.getString(cur.getColumnIndexOrThrow("body")).toString();
                long numero = convertirNumeroTelephoneEnLong(cur.getString(cur.getColumnIndexOrThrow("address")).toString());
                Date date = new Date(Long.parseLong(cur.getString(cur.getColumnIndexOrThrow("date")).toString()));
                boolean envoye = Integer.parseInt(cur.getString(cur.getColumnIndexOrThrow("type")).toString()) == VALEUR_MESSAGE_ENVOYER;

                enveloppeMessages.add(new EnveloppeMessage(message, numero, date, envoye));
                cur.moveToNext();
            }
        }
        cur.close();
        return enveloppeMessages;
    }

    static ArrayList<EnveloppeContact> lireTousContact(Service service) {
        ArrayList<EnveloppeContact> enveloppeContacts = new ArrayList<>();

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};

        Cursor people = service.getContentResolver().query(uri, projection, null, null, null);

        int indexName = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        int indexNumber = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

        people.moveToFirst();
        do {
            String nom = people.getString(indexName);
            long numero = convertirNumeroTelephoneEnLong(people.getString(indexNumber));
            enveloppeContacts.add(new EnveloppeContact(numero, nom));
        } while (people.moveToNext());

        return enveloppeContacts;
    }
}
