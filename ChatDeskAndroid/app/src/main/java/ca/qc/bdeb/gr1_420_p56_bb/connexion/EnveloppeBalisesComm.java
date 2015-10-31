package ca.qc.bdeb.gr1_420_p56_bb.connexion;

/**
 * Permet d'associer une balises à un contenu dans la communication avec le serveur.
 */
class EnveloppeBalisesComm {

    private BalisesCommServeur balises;
    private String contenu;

    public EnveloppeBalisesComm(BalisesCommServeur balises, String contenu) {
        this.contenu = contenu;
        this.balises = balises;
    }

    public String getContenu() {
        return contenu;
    }

    public BalisesCommServeur getBalises() {
        return balises;
    }

    /**
     * Autogénéré par Intellij
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EnveloppeBalisesComm)) return false;

        EnveloppeBalisesComm that = (EnveloppeBalisesComm) o;

        if (balises != that.balises) return false;
        return contenu.equals(that.contenu);
    }
}