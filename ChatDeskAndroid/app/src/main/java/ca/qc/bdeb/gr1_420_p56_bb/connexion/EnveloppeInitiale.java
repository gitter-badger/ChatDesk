package ca.qc.bdeb.gr1_420_p56_bb.connexion;

import java.util.ArrayList;

/**
 * Created by Alexandre on 2015-10-27.
 */
class EnveloppeInitiale implements ConvertissableXml {

    ArrayList<EnveloppeMessage> enveloppeMessages;
    ArrayList<EnveloppeContact> enveloppeContacts;

    public EnveloppeInitiale(ArrayList<EnveloppeMessage> enveloppeMessages,
                             ArrayList<EnveloppeContact> enveloppeContacts) {
        this.enveloppeMessages = enveloppeMessages;
        this.enveloppeContacts = enveloppeContacts;
    }

    @Override
    public String convertirEnXml() {
        return new XMLWriter().construireXmlCommunication(CommandesClient.PREMIERE_CONNEXION,
                enveloppeMessages.toArray(new EnveloppeMessage[enveloppeMessages.size()]),
                enveloppeContacts.toArray(new EnveloppeContact[enveloppeContacts.size()]));
    }
}
