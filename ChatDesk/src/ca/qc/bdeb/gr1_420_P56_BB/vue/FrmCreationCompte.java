package ca.qc.bdeb.gr1_420_P56_BB.vue;

import javax.swing.*;

/**
 * Created by Alexandre on 2015-11-17.
 */
public class FrmCreationCompte extends JFrame {

    private JPanel panneauPrincipale;
    private JButton btnInscrite;
    private JButton btnAnnuler;

    public FrmCreationCompte(){
        panneauPrincipale = new JPanel();
        this.add(panneauPrincipale);
    }

    public void mettreComposantes(){
        this.add(new JLabel("Nom d'utilisateur : "));
        this.add(new JLabel("Mot de passe : "));
        this.add(new JLabel("Entrez le mot de passe à nouveau : "));


    }

}
