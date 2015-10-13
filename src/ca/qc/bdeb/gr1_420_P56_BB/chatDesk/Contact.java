package ca.qc.bdeb.gr1_420_P56_BB.chatDesk;


/**
 * Created by Alexandre on 2015-09-06.
 *
 * Un contact
 */
public class Contact{

    /**
     * Le numï¿½ro de tï¿½lï¿½phone du contact
     */
    private long numeroTelephone;

    /**
     * Le nom du contact
     */
    private String nom;

    /**
     * Constructeur qui permet de crï¿½e un contact
     *
     * @param numeroTelephone Le numï¿½ro de tï¿½lï¿½phone du contact
     * @param nom Le nom du contact
     */
    public Contact(long numeroTelephone, String nom){
        this.numeroTelephone = numeroTelephone;
        this.nom = nom;
    }

    /**
     * @return Le numï¿½ro de tï¿½lï¿½phone du contact
     */
    public long getNumeroTelephone() {
        return numeroTelephone;
    }

    /**
     * @return Le nom du contact
     */
    public String getNom() {
        return nom;
    }
}
