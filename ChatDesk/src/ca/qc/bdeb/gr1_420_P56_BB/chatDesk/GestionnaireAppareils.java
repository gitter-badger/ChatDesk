package ca.qc.bdeb.gr1_420_P56_BB.chatDesk;

/**
 * Gère les appareils auxquels il est possible de se connecter
 */
class GestionnaireAppareils {

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
