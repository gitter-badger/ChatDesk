package ca.qc.bdeb.gr1_420_P56_BB.main;

import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.Appareil;
import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.Utilisateur;
import ca.qc.bdeb.gr1_420_P56_BB.vue.FenetreConnexion;
import ca.qc.bdeb.gr1_420_P56_BB.vue.selectionAppareils.FenetreSelectionAppareil;

import java.util.ArrayList;

/**
 * Created by 1372883 on 2015-09-09.
 */
class Main {
    public static void main(String[] args) {
        //FenetreConnexion fenetreConnexion = new FenetreConnexion();
        //FacadeModele facadeModele = new FacadeModele();
        //FrmChatDesk fentreConvo = new FrmChatDesk(facadeModele);
        //fentreConvo.setVisible(true);
        testerFenetreSelectionAppareils();
    }

    private static void testerFenetreSelectionAppareils() {
        ArrayList<Appareil> listeAppareils = new ArrayList<>();
        listeAppareils.add(new Appareil("Greg", 1));
        listeAppareils.add(new Appareil("Jacques", 2));
        listeAppareils.add(new Appareil("Bob", 3));
        listeAppareils.add(new Appareil("Léon", 4));
        listeAppareils.add(new Appareil("Cancer", 5));
        listeAppareils.add(new Appareil("Stéphane", 6));
        new FenetreSelectionAppareil(listeAppareils);
    }
}
