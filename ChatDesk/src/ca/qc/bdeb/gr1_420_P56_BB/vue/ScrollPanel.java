package ca.qc.bdeb.gr1_420_P56_BB.vue;

import javax.swing.*;
import java.awt.*;

/**
 * Un panneau avec un layout pour y mettre un jScrollpane.
 */
class ScrollPanel extends JPanel {

    public ScrollPanel(JPanel panel, int width, int height) {
        this.setSize(width, height);
        this.setLayout(new BorderLayout());

        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.getViewport().add(panel, null);

        int scrollBarSize = ((Integer)UIManager.get("ScrollBar.width")).intValue();
        panel.setLocation(0, 0);
        panel.setPreferredSize(new Dimension(width - scrollBarSize, height));
        panel.setSize(width, height);


        this.add(jScrollPane, BorderLayout.CENTER);
    }

    /**
     * Mettre a jour le panneau pour faire apparaitre la scroll bar.
     */
    public void mettreAJour(){
        this.revalidate();
        this.repaint();
    }
}