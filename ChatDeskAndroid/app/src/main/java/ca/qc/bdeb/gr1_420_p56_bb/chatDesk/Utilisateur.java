package ca.qc.bdeb.gr1_420_p56_bb.chatDesk;

import java.io.Serializable;

/**
 * L'utilisateur du programme.
 */
public class Utilisateur implements Serializable{

    private String nom;
    private String motDePasse;
    private boolean informationsdeConnexionValides;

    public Utilisateur(String motDePasse, String nom) {
        this.motDePasse = motDePasse;
        this.nom = nom;
        determinerInformationValide();
    }

     void determinerInformationValide(){

        informationsdeConnexionValides = true;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public boolean isInformationsdeConnexionValides() {
        return informationsdeConnexionValides;
    }

    public void setInformationsdeConnexionValides(boolean informationsdeConnexionValides) {
        this.informationsdeConnexionValides = informationsdeConnexionValides;
    }
}
