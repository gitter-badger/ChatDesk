package ca.qc.bdeb.gr1_420_p56_bb.services;

import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

import ca.qc.bdeb.gr1_420_p56_bb.connexion.EnveloppeContact;
import ca.qc.bdeb.gr1_420_p56_bb.connexion.EnveloppeMessage;

import static ca.qc.bdeb.gr1_420_p56_bb.utilitaires.Formatage.convertirImageEnString;
import static ca.qc.bdeb.gr1_420_p56_bb.utilitaires.Formatage.convertirNumeroTelephoneEnLong;

/**
 * Created by Alexandre on 2015-10-27.
 */
public class RecuperateurInfo {

    private static int VALEUR_MESSAGE_ENVOYER = 2;

    static EnveloppeMessage[] lireTousMessage(Service service) {
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
        return enveloppeMessages.toArray(new EnveloppeMessage[enveloppeMessages.size()]);
    }

    static EnveloppeContact[] lireTousContact(Service service) {
        ArrayList<EnveloppeContact> enveloppeContacts = new ArrayList<>();

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        Cursor curContact = service.getContentResolver().query(uri, null, null, null, null);

        int indexName = curContact.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        int indexNumber = curContact.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        int indexImage = curContact.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI);

        curContact.moveToFirst();
        do {
            String nom = curContact.getString(indexName);
            long numero = convertirNumeroTelephoneEnLong(curContact.getString(indexNumber));
            String image = "";
            try {
                String image_uri = curContact.getString(indexImage);
                if(image_uri != null) {
                    image = convertirImageEnString(MediaStore.Images.Media.getBitmap(service.getContentResolver(), Uri.parse(image_uri)));
                }
            } catch (IOException e) {
            }

            enveloppeContacts.add(new EnveloppeContact(numero, nom, image));
        } while (curContact.moveToNext());

        return enveloppeContacts.toArray(new EnveloppeContact[enveloppeContacts.size()]);
    }
}
