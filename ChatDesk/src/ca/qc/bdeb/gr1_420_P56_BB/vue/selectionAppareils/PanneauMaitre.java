package ca.qc.bdeb.gr1_420_P56_BB.vue.selectionAppareils;

import ca.qc.bdeb.gr1_420_P56_BB.chatDesk.Appareil;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by 1372883 on 2015-11-03.
 */
public class PanneauMaitre extends JPanel {

    private ArrayList<Appareil> listeAppareils;
    private Rappeleur rappeleur;
    private JScrollPane scpListeAppareils;
    private JList lstAppareils;

    public PanneauMaitre(ArrayList<Appareil> listeAppareils, Rappeleur rappeleur) {
        this.listeAppareils = listeAppareils;
        this.rappeleur = rappeleur;

        initialiserListeAppareils();
        initialiserScrollListeAppareil();

        sendCallback(1);
    }

    private void initialiserListeAppareils() {
        this.lstAppareils = new JList();

        this.lstAppareils.setListData(listeAppareils.toArray(new Appareil[listeAppareils.size()]));
        this.lstAppareils.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        this.lstAppareils.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        this.lstAppareils.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        this.lstAppareils.addListSelectionListener(listSelectionEvent -> {
            sendCallback(lstAppareils.getSelectedIndex());
        });
    }

    private void initialiserScrollListeAppareil() {
        this.scpListeAppareils = new JScrollPane();

        this.scpListeAppareils.setViewportView(lstAppareils);
        this.scpListeAppareils.setPreferredSize(new Dimension(250, 80));
        this.scpListeAppareils.setHorizontalScrollBar(null);
        this.add(scpListeAppareils);
    }

    private void sendCallback(int indice) {
        rappeleur.rappeler(listeAppareils.get(indice));
    }
}
