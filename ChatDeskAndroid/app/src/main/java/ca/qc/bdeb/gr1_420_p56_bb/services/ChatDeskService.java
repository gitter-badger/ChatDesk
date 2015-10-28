package ca.qc.bdeb.gr1_420_p56_bb.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

import ca.qc.bdeb.gr1_420_p56_bb.connexion.EnveloppeContact;
import ca.qc.bdeb.gr1_420_p56_bb.connexion.EnveloppeMessage;
import ca.qc.bdeb.gr1_420_p56_bb.connexion.GestionnaireConnexion;
import ca.qc.bdeb.gr1_420_p56_bb.connexion.ResultatsConnexion;

import static ca.qc.bdeb.gr1_420_p56_bb.utilitaires.Formatage.convertirNumeroTelephoneEnLong;

public class ChatDeskService extends Service implements IService {

    public static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    private final IBinder binder = new LocalBinder();
    private GestionnaireConnexion gestionnaireConnexion;
    private SmsReceiver smsReceiver;

    public ChatDeskService() {
        gestionnaireConnexion = new GestionnaireConnexion(this);
    }

    public class LocalBinder extends Binder {
        public ChatDeskService getService() {
            return ChatDeskService.this;
        }
    }

    @Override
    public void onCreate() {
        smsReceiver = new SmsReceiver(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(SMS_RECEIVED);
        filter.addAction(android.telephony.TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        this.registerReceiver(smsReceiver, filter);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public ResultatsConnexion seConnecter(String nomUtilisateur, String pass) {
        return gestionnaireConnexion.seConnecter(nomUtilisateur, pass);
    }


    @Override
    public void envoyerMessageTelephone(EnveloppeMessage enveloppeMessage) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(Long.toString(enveloppeMessage.getNumeroTelephone())
                , null, enveloppeMessage.getMessage(), null, null);

    }

    @Override
    public void ajouterContactTelephone(EnveloppeContact enveloppeContact) {

    }

    @Override
    public ArrayList<EnveloppeMessage> recupererTousMessage() {
        return RecuperateurInfo.lireTousMessage(this);
    }

    @Override
    public ArrayList<EnveloppeContact> recupererTousContact() {
        return RecuperateurInfo.lireTousContact(this);
    }

    @Override
    public void receptionMessageText(EnveloppeMessage enveloppeMessage) {
        gestionnaireConnexion.envoyerEnveloppe(enveloppeMessage);
    }
}
