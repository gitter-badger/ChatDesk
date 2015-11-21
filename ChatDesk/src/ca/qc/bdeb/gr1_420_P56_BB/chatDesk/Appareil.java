package ca.qc.bdeb.gr1_420_P56_BB.chatDesk;

/**
 * Un appareil auquel il est possible de se connecter
 */
public class Appareil {

    private final String nom;
    private final int id;
    private final String ip;
    private final String location;

    public Appareil(String nom, int id, String ip, String location) {
        this.nom = nom;
        this.id = id;
        this.ip = ip;
        this.location = location;
    }

    public String getNom() {
        return nom;
    }

    public int getId() {
        return id;
    }

    public String getIp() {
        return ip;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return nom;
    }
}