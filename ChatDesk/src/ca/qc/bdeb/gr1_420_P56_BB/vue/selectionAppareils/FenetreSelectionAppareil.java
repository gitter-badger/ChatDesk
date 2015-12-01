package ca.qc.bdeb.gr1_420_P56_BB.vue.selectionAppareils;

import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.Appareil;
import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.FacadeModele;
import ca.qc.bdeb.gr1_420_P56_BB.utilitaires.ObservateurAppareils;
import ca.qc.bdeb.gr1_420_P56_BB.vue.FrmChatDesk;

import javax.swing.*;
import java.awt.*;

/**
 * Created by 1372883 on 2015-11-03.
 */
public class FenetreSelectionAppareil extends JFrame implements ObservateurAppareils, Rappeleur {
    private static final Insets MARGES_PANNEAU = new Insets(50, 50, 50, 50);
    private static final String NOM_FENETRE = "Sélection d'appareils";
    private static final String MESSAGE_AUCUN_APPAREIL = "Aucun appareil";
    private static final String MESSAGE_CHARGEMENT_APPAREILS = "Chargement des appareils";
    private static final int AUCUN_APPAREIL = 0;

    private final FacadeModele facadeModele;
    private PanneauMaitre panneauMaster;
    private PanneauDetail panneauDetail;
    private JButton btnRefresh;

    public FenetreSelectionAppareil(FacadeModele facadeModele) {
        super(NOM_FENETRE);
        this.facadeModele = facadeModele;
        facadeModele.ajouterObservateurAppareil(this);

        chargerTableauAppareils();

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void resetLayout(){
        this.setLayout(new GridBagLayout());
        this.getContentPane().removeAll();
    }

    private void chargerTableauAppareils() {
        Appareil[] tabAppareils = facadeModele.getAppareils();

        resetLayout();
        if (tabAppareils == null) {
            initialiserChargementAppareils();
        } else if (tabAppareils.length > AUCUN_APPAREIL) {
            initialiserPanneauxMaitreDetail(tabAppareils);
        } else {
            initialiserAucunAppareils();
        }

        initialiserBoutonRefresh();
        this.pack();
        this.setLocationRelativeTo(null);
    }

    private void initialiserChargementAppareils() {
        ajouterLabelSeul(MESSAGE_CHARGEMENT_APPAREILS);
    }

    private void initialiserBoutonRefresh() {
        btnRefresh = new JButton();
        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(actionEvent -> {
            facadeModele.demanderAppareils();
        });

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.weightx = 1;
        constraints.weighty = 1;
        this.add(btnRefresh);
    }

    private void initierLien(int idAppareil) {
        facadeModele.initierLien(idAppareil);
        FrmChatDesk frmChatDesk = new FrmChatDesk(facadeModele);
        frmChatDesk.setVisible(true);
        this.dispose();
    }

    private void initialiserAucunAppareils() {
        ajouterLabelSeul(MESSAGE_AUCUN_APPAREIL);
    }

    private void ajouterLabelSeul(String message){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = MARGES_PANNEAU;
        this.add(new JLabel(message), constraints);
    }

    private void initialiserPanneauxMaitreDetail(Appareil[] tabAppareils) {
        panneauDetail = new PanneauDetail(this);
        panneauMaster = new PanneauMaitre(tabAppareils, panneauDetail);

        System.out.println(tabAppareils.length);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.insets = MARGES_PANNEAU;
        this.add(panneauMaster, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.insets = MARGES_PANNEAU;
        this.add(panneauDetail, constraints);
    }

    @Override
    public void aviserAppareils() {
        this.chargerTableauAppareils();
    }

    @Override
    public void rappeler(Appareil appareil) {
        System.out.println("fuck");
        initierLien(appareil.getId());
    }
}