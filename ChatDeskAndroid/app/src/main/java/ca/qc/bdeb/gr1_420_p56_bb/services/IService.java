package ca.qc.bdeb.gr1_420_p56_bb.services;

import java.util.ArrayList;

import ca.qc.bdeb.gr1_420_p56_bb.connexion.EnveloppeContact;
import ca.qc.bdeb.gr1_420_p56_bb.connexion.EnveloppeMessage;

/**
 * Created by Alexandre on 2015-09-04.
 *
 * Un élément qui observe
 */
public interface IService {

    /**
     * Un changement d'état est observé
     */
    void envoyerMessageTelephone(EnveloppeMessage enveloppeMessage);

    void ajouterContactTelephone(EnveloppeContact enveloppeContact);

    ArrayList<EnveloppeMessage> recupererTousMessage();

    ArrayList<EnveloppeContact> recupererTousContact();
}
