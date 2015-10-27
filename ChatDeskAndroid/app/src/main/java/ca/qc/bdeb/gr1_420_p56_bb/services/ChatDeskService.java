package ca.qc.bdeb.gr1_420_p56_bb.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

import ca.qc.bdeb.gr1_420_p56_bb.connexion.EnveloppeContact;
import ca.qc.bdeb.gr1_420_p56_bb.connexion.EnveloppeMessage;
import ca.qc.bdeb.gr1_420_p56_bb.connexion.GestionnaireConnexion;
import ca.qc.bdeb.gr1_420_p56_bb.connexion.ResultatsConnexion;

import static ca.qc.bdeb.gr1_420_p56_bb.utilitaires.Formatage.convertirNumeroTelephoneEnLong;

public class ChatDeskService extends Service implements IService {

    private final IBinder binder = new LocalBinder();
    private GestionnaireConnexion gestionnaireConnexion;


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            Log.d("receiver", "Got message: " + message);
        }
    };


    public ChatDeskService() {
        gestionnaireConnexion = new GestionnaireConnexion(this);
    }

    public class LocalBinder extends Binder {
        public ChatDeskService getService() {
            return ChatDeskService.this;
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }

    public ResultatsConnexion seConnecter(String nomUtilisateur, String pass) {
        return gestionnaireConnexion.seConnecter(nomUtilisateur, pass);
    }


    @Override
    public void envoyerMessageTelephone(EnveloppeMessage enveloppeMessage) {

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

}
