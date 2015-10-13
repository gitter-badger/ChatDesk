package ca.qc.bdeb.gr1_420_P56_BB.main;

import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.Utilisateur;
import ca.qc.bdeb.gr1_420_P56_BB.vue.FenetreConnexion;

/**
 * Created by 1372883 on 2015-09-09.
 */
public class Main {
    public static void main(String[] args) {
        FenetreConnexion fenetreConnexion = new FenetreConnexion(new Utilisateur("chose", "chose"));
    }
}
