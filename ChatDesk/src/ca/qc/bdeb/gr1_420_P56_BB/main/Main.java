package ca.qc.bdeb.gr1_420_P56_BB.main;

import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.Appareil;
import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.FacadeModele;
import ca.qc.bdeb.gr1_420_P56_BB.vue.FrmChatDesk;
import ca.qc.bdeb.gr1_420_P56_BB.vue.FrmConnexion;
import ca.qc.bdeb.gr1_420_P56_BB.vue.selectionAppareils.FenetreSelectionAppareil;

import java.util.ArrayList;

/**
 * Created by 1372883 on 2015-09-09.
 */
class Main {
    public static void main(String[] args) {
        //      FrmConnexion frmConnexion = new FrmConnexion();
      //  testerFenetreAppareils();
        testerFenetrePrincipale();
    }

    private static void testerFenetreAppareils() {
        ArrayList<Appareil> listeAppareils = new ArrayList<>();

        listeAppareils.add(new Appareil("IPhone 14", 1, "12345678910", "Montr�al"));
        listeAppareils.add(new Appareil("Android 45", 2, "10987654321", "Qu�bec"));
        listeAppareils.add(new Appareil("Windows phone", 3, "5555555555", "Kuujjuaq"));

        new FenetreSelectionAppareil(listeAppareils);
    }
    private static void testerFenetrePrincipale(){
        FacadeModele facadeModele = new FacadeModele();
        FrmChatDesk fentreConvo = new FrmChatDesk(facadeModele);
       fentreConvo.setVisible(true);
    }
}