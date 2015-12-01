package ca.qc.bdeb.gr1_420_P56_BB.vue;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * La bar d'option/menu en haut de la fen�tre principale du programme.
 */
class OptionBar extends JPanel {
    /**
     * Image du bouton de fermeture
     */
    private static final ImageIcon IMAGE_FERMER = new ImageIcon("resources/images/fermer.png");
    /**
     * Image du bouton d'agrandisement
     */
    private static final ImageIcon IMAGE_AGRANDIR = new ImageIcon("resources/images/agrandir.png");
    /**
     * Image du bouton de diminution
     */
    private static final ImageIcon IMAGE_DIMINUER = new ImageIcon("resources/images/diminuer.png");
    /**
     * Image du bouton de minimisation
     */
    private static final ImageIcon IMAGE_MINIMISER = new ImageIcon("resources/images/minimiser.png");
    /**
     * Image du bouton d'options
     */
    private static final ImageIcon IMAGE_OPTIONS = new ImageIcon("resources/images/options.png");
    /**
     * Image du bouton de profile
     */
    private static final ImageIcon IMAGE_PROFILE = new ImageIcon("resources/images/profile.png");
    /**
     * Image du bouton d'ajout de conversation
     */
    private static final ImageIcon IMAGE_AJOUT_CONVO = new ImageIcon("resources/images/android_add_circle_outline.png");

    /**
     * Nombre de cliques avant d'être considéré comme un double clique
     */
    private static final int CLICK_COUNT = 2;

    /**
     * Couleur de fond d'écran
     */
    private static final Color BACKGROUND_COLOR = new Color(0, 142, 198);

    /**
     * JLabel agissant comme bouton de fermeture
     */
    private JLabel fermer;
    /**
     * JLabel agissant comme bouton d'agrandissement
     */
    private JLabel agrandir;
    /**
     * JLabel agissant comme bouton de minimisation
     */
    private JLabel minimiser;
    /**
     * JLabel agissant comme bouton d'options
     */
    private JLabel options;
    /**
     * JLabel agissant comme bouton de profile
     */
    private JLabel profile;
    /**
     * Le point du click initial lors d'un drag and drop
     */
    private Point clickInitial;

    /**
     *
     */
    private JLabel ajoutConversation;

    /**
     * Indique si le frame est en plein écran
     */
    private boolean isFullScreen;
    /**
     * Conserve la dernière longueur
     */
    private int derniereLongueur;
    /**
     * Conserve la dernière hauteur
     */
    private int derniereHauteur;
    /**
     * Conserve la dernière position en X
     */
    private int dernierePositionX;
    /**
     * Conserve la dernière position en Y
     */
    private int dernierePositionY;

    /**
     * Pointeur sur la fenetre
     */
    private final FrmChatDesk frmPrincipale;

    private Point point = new Point();

    private boolean resizing = false;

    /**
     * Construit la bar d'option de l'application
     *
     * @param frmPrincipale La fen�tre principale du programme
     */
    public OptionBar(FrmChatDesk frmPrincipale) {
        this.frmPrincipale = frmPrincipale;
        this.setLocation(0, 0);
        this.setLayout(null);

        this.setBackground(BACKGROUND_COLOR);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                clickInitial = e.getPoint();
                getComponentAt(clickInitial);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == CLICK_COUNT) {
                    changerScreenSize();
                }
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (!resizing) {
                    bougerFenetre(e);
                }
            }
        });

        isFullScreen = false;
        initialiserBoutons();
        initialiserPanel();
        initialiserResizable();
    }

    /**
     * Initialise tous les boutons
     */
    private void initialiserBoutons() {
        try {
            initialiserBoutonFermer();
            initialiserBoutonGrandir();
            initialiserBoutonMinimiser();
            initialiserBoutonAjoutConversation();
            initialiserBoutonOptions();
            initialiserBoutonProfile();
        } catch (Exception e){

        }
    }

    /**
     * Initialise le bouton fermer
     */
    private void initialiserBoutonFermer() {
        fermer = new JLabel(IMAGE_FERMER);
        fermer.setSize(IMAGE_FERMER.getIconWidth(), IMAGE_FERMER.getIconHeight());
        fermer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                frmPrincipale.arreterProgramme();
            }
        });
        this.add(fermer);
    }

    /**
     * initialise le bouton pour ajouter une conversation à partir d'un numéro de téléphone
     */
    private void initialiserBoutonAjoutConversation() {
        ajoutConversation = new JLabel(IMAGE_AJOUT_CONVO);
        ajoutConversation.setSize(IMAGE_AJOUT_CONVO.getIconWidth(), IMAGE_AJOUT_CONVO.getIconHeight());
        ajoutConversation.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frmPrincipale.ajouterConversation();
            }
        });
        this.add(ajoutConversation);
    }

    /**
     * Initialise le bouton grandir
     */
    private void initialiserBoutonGrandir() {
        agrandir = new JLabel(IMAGE_AGRANDIR);
        agrandir.setSize(IMAGE_AGRANDIR.getIconWidth(), IMAGE_AGRANDIR.getIconHeight());
        agrandir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                changerScreenSize();
            }
        });
        this.add(agrandir);
    }

    /**
     * Initialise le bouton minimiser
     */
    private void initialiserBoutonMinimiser() {
        minimiser = new JLabel(IMAGE_MINIMISER);
        minimiser.setSize(IMAGE_MINIMISER.getIconWidth(), IMAGE_MINIMISER.getIconHeight());
        minimiser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                frmPrincipale.setState(JFrame.ICONIFIED);
            }
        });
        this.add(minimiser);
    }

    /**
     * Initialise le bouton des options
     */
    private void initialiserBoutonOptions() {
        options = new JLabel(IMAGE_OPTIONS);
        options.setSize(IMAGE_OPTIONS.getIconWidth(), IMAGE_OPTIONS.getIconHeight());
        options.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                try {
                    FrmOption frmOption = new FrmOption(frmPrincipale.getHeight(), frmPrincipale.getWidth(),
                            frmPrincipale.getPnlConversation().getPnlBulles().getCouleurBullesEnvoyees(),
                            frmPrincipale.getPnlConversation().getPnlBulles().getCouleurBullesRecues(), frmPrincipale);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        this.add(options);
    }

    /**
     * Initialise le bouton de profile
     */
    private void initialiserBoutonProfile() {
        profile = new JLabel(IMAGE_PROFILE);
        profile.setSize(IMAGE_PROFILE.getIconWidth(), IMAGE_PROFILE.getIconHeight());
        profile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {

                JFrame jFrameContacts = new JFrame();
                FrmContacts frmContacts = new FrmContacts(frmPrincipale, frmPrincipale.getFacadeModele(), jFrameContacts);
                jFrameContacts.setLayout(null);
                jFrameContacts.setBounds(frmContacts.getBounds());
                jFrameContacts.setMinimumSize(frmContacts.getSize());
                jFrameContacts.add(frmContacts);
                jFrameContacts.setVisible(true);
                jFrameContacts.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                jFrameContacts.setLocationRelativeTo(null);

            }
        });
        this.add(profile);
    }

    /**
     * Permet de changer la grandeur de l'�cran de pleine �cran � �cran normal et vise versa
     */
    private void changerScreenSize() {
        if (!isFullScreen) {
            agrandir.setIcon(IMAGE_DIMINUER);
            derniereLongueur = frmPrincipale.getWidth();
            derniereHauteur = frmPrincipale.getHeight();
            dernierePositionX = frmPrincipale.getLocation().x;
            dernierePositionY = frmPrincipale.getLocation().y;
            frmPrincipale.setExtendedState(JFrame.MAXIMIZED_BOTH);
            isFullScreen = true;

        } else {
            agrandir.setIcon(IMAGE_AGRANDIR);
            frmPrincipale.setSize(derniereLongueur, derniereHauteur);
            frmPrincipale.setLocation(dernierePositionX, dernierePositionY);
            isFullScreen = false;
        }
    }

    /**
     * Change l'emplacement de la fen�tre selon le mouvement de la souris
     *
     * @param e Le mouse event
     */
    private void bougerFenetre(MouseEvent e) {
        int locationFenetreX = (int) frmPrincipale.getLocation().getX();
        int locationFenetreY = (int) frmPrincipale.getLocation().getY();
        int x = locationFenetreX + (locationFenetreX + e.getX()) - (locationFenetreX + (int) clickInitial.getX());
        int y = locationFenetreY + (locationFenetreY + e.getY()) - (locationFenetreY + (int) clickInitial.getY());

        if (isFullScreen) {
            changerScreenSize();
        }

        frmPrincipale.setLocation(x, y);
    }

    /**
     * Initialise le panneau avec les composants au bon endroit.
     */
    public void initialiserPanel() {
        this.setSize(frmPrincipale.getWidth(), IMAGE_FERMER.getIconHeight() + IMAGE_FERMER.getIconHeight() / 2);
        fermer.setLocation(this.getWidth() - fermer.getWidth() - 10, this.getHeight() / 2 - fermer.getHeight() / 2);
        agrandir.setLocation((int) fermer.getLocation().getX() - agrandir.getWidth() - 10, this.getHeight() / 2
                - agrandir.getHeight() / 2);
        minimiser.setLocation((int) agrandir.getLocation().getX() - minimiser.getWidth() - 10,
                this.getHeight() / 2 - minimiser.getHeight() / 2);
        options.setLocation(10, this.getHeight() / 2 - options.getHeight() / 2);
        profile.setLocation(options.getWidth() + 20, this.getHeight() / 2 - profile.getHeight() / 2);
        ajoutConversation.setLocation(minimiser.getLocation().x - ajoutConversation.getWidth() - 10, this.getHeight() / 2 - ajoutConversation.getHeight() / 2);
    }

    public void initialiserResizable() {
        long eventMask = AWTEvent.MOUSE_MOTION_EVENT_MASK + AWTEvent.MOUSE_EVENT_MASK;
        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
            public void eventDispatched(AWTEvent e) {
                if (e instanceof MouseEvent) {
                    MouseEvent me = (MouseEvent) e;

                    PointerInfo pi = MouseInfo.getPointerInfo();
                    Point cursorLocation = pi.getLocation();

                    int xPos = cursorLocation.x - (int) frmPrincipale.getLocation().getX();
                    int yPos = cursorLocation.y - (int) frmPrincipale.getLocation().getY();

                    if (me.getID() == MouseEvent.MOUSE_MOVED) {
                        if (xPos <= 5 && xPos >= 0 && yPos <= 5 && yPos >= 0) {
                            me.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
                        } else if (xPos <= 5 && xPos >= 0 && yPos >= frmPrincipale.getContentPane().getHeight() - 5 && yPos <= frmPrincipale.getContentPane().getHeight()) {
                            me.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
                        } else if (yPos <= 5 && yPos >= 0 && xPos >= frmPrincipale.getContentPane().getWidth() - 5 && xPos <= frmPrincipale.getContentPane().getWidth()) {
                            me.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
                        } else if (xPos >= frmPrincipale.getContentPane().getWidth() - 5 && xPos <= frmPrincipale.getContentPane().getWidth() &&
                                yPos >= frmPrincipale.getContentPane().getHeight() - 5 && yPos <= frmPrincipale.getContentPane().getHeight()) {
                            me.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
                        } else if (xPos <= 5 && xPos >= 0) {
                            me.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
                        } else if (xPos >= frmPrincipale.getContentPane().getWidth() - 5 && xPos <= frmPrincipale.getContentPane().getWidth()) {
                            me.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
                        } else if (yPos <= 5 && yPos >= 0) {
                            me.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
                        } else if (yPos >= frmPrincipale.getContentPane().getHeight() - 5 && yPos <= frmPrincipale.getContentPane().getHeight()) {
                            me.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
                        } else {
                            me.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                        }
                    } else if (me.getID() == MouseEvent.MOUSE_DRAGGED) {
                        if (resizing) {
                            if (me.getComponent().getCursor().getType() == Cursor.NW_RESIZE_CURSOR) {
                                frmPrincipale.setSize(frmPrincipale.getWidth() - cursorLocation.x + point.x, frmPrincipale.getHeight() - cursorLocation.y + point.y);
                                frmPrincipale.setLocation(frmPrincipale.getLocation().x + cursorLocation.x - point.x, frmPrincipale.getLocation().y + cursorLocation.y - point.y);
                            } else if (me.getComponent().getCursor().getType() == Cursor.SW_RESIZE_CURSOR) {
                                frmPrincipale.setSize(frmPrincipale.getWidth() - cursorLocation.x + point.x, frmPrincipale.getHeight() + cursorLocation.y - point.y);
                                frmPrincipale.setLocation(frmPrincipale.getLocation().x + cursorLocation.x - point.x, frmPrincipale.getLocation().y);
                            } else if (me.getComponent().getCursor().getType() == Cursor.NE_RESIZE_CURSOR) {
                                frmPrincipale.setSize(frmPrincipale.getWidth() + cursorLocation.x - point.x, frmPrincipale.getHeight() - cursorLocation.y + point.y);
                                frmPrincipale.setLocation(frmPrincipale.getLocation().x, frmPrincipale.getLocation().y + cursorLocation.y - point.y);
                            } else if (me.getComponent().getCursor().getType() == Cursor.SE_RESIZE_CURSOR) {
                                frmPrincipale.setSize(frmPrincipale.getWidth() + cursorLocation.x - point.x, frmPrincipale.getHeight() + cursorLocation.y - point.y);
                            } else if (me.getComponent().getCursor().getType() == Cursor.W_RESIZE_CURSOR) {
                                frmPrincipale.setSize(frmPrincipale.getWidth() - cursorLocation.x + point.x, frmPrincipale.getHeight());
                                frmPrincipale.setLocation(frmPrincipale.getLocation().x + cursorLocation.x - point.x, frmPrincipale.getLocation().y);
                            } else if (me.getComponent().getCursor().getType() == Cursor.E_RESIZE_CURSOR) {
                                frmPrincipale.setSize(frmPrincipale.getWidth() + cursorLocation.x - point.x, frmPrincipale.getHeight());
                            } else if (me.getComponent().getCursor().getType() == Cursor.N_RESIZE_CURSOR) {
                                frmPrincipale.setSize(frmPrincipale.getWidth(), frmPrincipale.getHeight() - cursorLocation.y + point.y);
                                frmPrincipale.setLocation(frmPrincipale.getLocation().x, frmPrincipale.getLocation().y + cursorLocation.y - point.y);
                            } else if (me.getComponent().getCursor().getType() == Cursor.S_RESIZE_CURSOR) {
                                frmPrincipale.setSize(frmPrincipale.getWidth(), frmPrincipale.getHeight() + cursorLocation.y - point.y);
                            }

                            point.x = cursorLocation.x;
                            point.y = cursorLocation.y;
                        }
                    } else if (me.getID() == MouseEvent.MOUSE_PRESSED) {
                        resizing = me.getComponent().getCursor().equals(Cursor.getDefaultCursor()) ? false : true;
                        if (!me.isMetaDown()) {
                            point.x = cursorLocation.x;
                            point.y = cursorLocation.y;
                        }
                    }
                }
            }
        }, eventMask);
    }
}
