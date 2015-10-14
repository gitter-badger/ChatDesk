package ca.qc.bdeb.gr1_420_P56_BB.chatDesk;

/**
 * Created by Louis-Simon on 10/10/2015.
 *
 * Un appareil auquel il est possible de se connecter
 */
public class Appareil {

    private final String nom;
    private final int id;

    public Appareil(String nom, int id) {
        this.nom = nom;
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public int getId() {
        return id;
    }
}
