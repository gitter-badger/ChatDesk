package ca.qc.bdeb.gr1_420_P56_BB.connexion;

/**
 * Created by 1372883 on 2015-11-25.
 */
public enum ErreursSocket {
    ERREUR_LECTURE_SOCKET("Le socket n'a pu être lu"),
    ERREUR_AFFECTATION_TIMEOUT("Le timeout du socket n'a pu être affecté"),
    ERREUR_RECEPTION_REPONSE("La réponse n'a pas été récue à temps");

    private final String message;

    ErreursSocket(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
