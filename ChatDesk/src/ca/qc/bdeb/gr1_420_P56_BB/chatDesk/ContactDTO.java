package ca.qc.bdeb.gr1_420_P56_BB.chatDesk;

/**
 * Created by Alexandre on 2015-10-30.
 */
public class ContactDTO {
    /**
     * Le num?ro de t?l?phone du contact
     */
    private final long numeroTelephone;

    /**
     * Le nom du contact
     */
    private final String nom;

    public ContactDTO(String nom, long numeroTelephone) {
        this.nom = nom;
        this.numeroTelephone = numeroTelephone;
    }

    public String getNom() {
        return nom;
    }

    public long getNumeroTelephone() {
        return numeroTelephone;
    }
}
