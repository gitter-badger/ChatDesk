package ca.qc.bdeb.gr1_420_p56_bb.services;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.telephony.SmsManager;

import ca.qc.bdeb.gr1_420_p56_bb.connexion.EnveloppeContact;
import ca.qc.bdeb.gr1_420_p56_bb.connexion.EnveloppeMessage;
import ca.qc.bdeb.gr1_420_p56_bb.connexion.GestionnaireCommunication;
import ca.qc.bdeb.gr1_420_p56_bb.connexion.ResultatsConnexion;

public class ChatDeskService extends Service implements IService {

    public static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    private final IBinder binder = new LocalBinder();
    private GestionnaireCommunication gestionnaireCommunication;
    private SmsReceiver smsReceiver;

    public ChatDeskService() {
        gestionnaireCommunication = new GestionnaireCommunication(this);
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
        return gestionnaireCommunication.seConnecter(nomUtilisateur, pass);
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
    public EnveloppeMessage[] recupererTousMessages() {
        return RecuperateurInfo.lireTousMessage(this);
    }

    @Override
    public EnveloppeContact[] recupererTousContacts() {
        return RecuperateurInfo.lireTousContact(this);
    }

    @Override
    public void receptionMessageText(EnveloppeMessage enveloppeMessage) {
        gestionnaireCommunication.envoyerEnveloppe(enveloppeMessage);
    }
}
