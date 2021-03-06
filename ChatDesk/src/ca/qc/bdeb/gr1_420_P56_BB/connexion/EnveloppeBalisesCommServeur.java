package ca.qc.bdeb.gr1_420_P56_BB.connexion;

/**
 * Permet d'associer une balises à un contenu dans la communication avec le serveur.
 */
class EnveloppeBalisesCommServeur {

    private final BalisesCommServeur balises;
    private final String contenu;

    public EnveloppeBalisesCommServeur(BalisesCommServeur balises, String contenu) {
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
        if (!(o instanceof EnveloppeBalisesCommServeur)) return false;

        EnveloppeBalisesCommServeur that = (EnveloppeBalisesCommServeur) o;

        if (balises != that.balises) return false;
        return contenu.equals(that.contenu);
    }
}
