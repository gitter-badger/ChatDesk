package ca.qc.bdeb.gr1_420_p56_bb.connexion;

import java.util.ArrayList;

/**
 * Created by Alexandre on 2015-10-27.
 */
class EnveloppeInitiale implements ConvertissableXml {

    EnveloppeMessage[] enveloppeMessages;
    EnveloppeContact[] enveloppeContacts;

    public EnveloppeInitiale(EnveloppeMessage[] enveloppeMessages,
                             EnveloppeContact[] enveloppeContacts) {
        this.enveloppeMessages = enveloppeMessages;
        this.enveloppeContacts = enveloppeContacts;
    }

    public EnveloppeMessage[] getEnveloppeMessages() {
        return enveloppeMessages;
    }

    public EnveloppeContact[] getEnveloppeContacts() {
        return enveloppeContacts;
    }

    @Override
    public String convertirEnXml() {
        return CreateurXMLComm.creationXMLEnveloppe(this);
    }
}
