package ca.qc.bdeb.gr1_420_p56_bb.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import java.util.Date;

import ca.qc.bdeb.gr1_420_p56_bb.connexion.EnveloppeMessage;
import ca.qc.bdeb.gr1_420_p56_bb.utilitaires.Formatage;

import static ca.qc.bdeb.gr1_420_p56_bb.services.ChatDeskService.SMS_RECEIVED;

/**
 * Created by Alexandre on 2015-10-27.
 */
public class SmsReceiver extends BroadcastReceiver {

    private final static boolean VALEUR_TEXT_RECU = false;

    IService service;

    public SmsReceiver(IService service) {
        this.service = service;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(SMS_RECEIVED)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");

                final SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                if (messages.length > -1) {
                    final String messageBody = messages[0].getMessageBody();
                    final long phoneNumber = Formatage.convertirNumeroTelephoneEnLong(messages[0].getDisplayOriginatingAddress());
                    final Date date = new Date(messages[0].getTimestampMillis());

                    EnveloppeMessage enveloppeMessage = new EnveloppeMessage(messageBody, phoneNumber, date, VALEUR_TEXT_RECU);
                    service.receptionMessageText(enveloppeMessage);
                }
            }
        }
    }
}