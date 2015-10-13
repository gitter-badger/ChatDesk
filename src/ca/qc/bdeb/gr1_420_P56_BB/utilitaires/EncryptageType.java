package ca.qc.bdeb.gr1_420_P56_BB.utilitaires;

/**
 * Permet de différencier l'encryptage au niveau serveur et l'encryptage au niveau client
 */
public enum EncryptageType {

    ENCRYPTAGE_SERVER(10),
    ENCRYPTAGE_MESSAGE(40);

    private long value;

    EncryptageType(long value) {
        this.value = value;
    }

    public long getValue() {
        return this.value;
    }

}
