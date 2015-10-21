package ca.qc.bdeb.gr1_420_p56_bb.chatDesk;

/**
 * Gère les appareils auxquels il est possible de se connecter
 * Created by Louis-Simon on 10/10/2015.
 */
public class GestionnaireAppareils {

    /**
     * Liste des appareils
     */
    private Appareil[] appareils;

    public GestionnaireAppareils(Appareil... appareils) {
        this.appareils = appareils;
    }

    public Appareil[] getAppareils() {
        return appareils;
    }

    public void setAppareils(Appareil[] appareils) {
        this.appareils = appareils;
    }
}