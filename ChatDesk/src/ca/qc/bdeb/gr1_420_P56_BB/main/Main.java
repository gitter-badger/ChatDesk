package ca.qc.bdeb.gr1_420_P56_BB.main;

import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.Appareil;
import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.FacadeModele;
import ca.qc.bdeb.gr1_420_P56_BB.vue.FrmChatDesk;
import ca.qc.bdeb.gr1_420_P56_BB.vue.FrmConnexion;
import ca.qc.bdeb.gr1_420_P56_BB.vue.selectionAppareils.FenetreSelectionAppareil;

/**
 * Created by 1372883 on 2015-09-09.
 */
class Main {
    private static final int FENETRE_PRINCIPAL = 2;
    private static final int TEST_APPAREILS = 1;
    private static final int DEROULEMENT_NORMAL = 3;

    public static void main(String[] args) {
        final int SELECTION = FENETRE_PRINCIPAL;

        switch (SELECTION) {
            case TEST_APPAREILS:
                testerSelectionAppareils();
                break;
            case FENETRE_PRINCIPAL:
                testerFenetrePrincipale();
                break;
            case DEROULEMENT_NORMAL:
                FrmConnexion frmConnexion = new FrmConnexion();
                break;
        }
    }

    private static void testerFenetrePrincipale() {
        FacadeModele facadeModele = new FacadeModele();
        FrmChatDesk fentreConvo = new FrmChatDesk(facadeModele);
        fentreConvo.setVisible(true);
    }

    private static void testerSelectionAppareils() {

    }
}