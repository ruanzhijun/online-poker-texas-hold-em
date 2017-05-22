package poker_client.graphic;

import entities.Card;
import entities.Player;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class GameWindow extends javax.swing.JFrame {
    private final Player PLAYER;
//    private static final Jugador JUGADOR = new Jugador();
    
    /**
     * Creates new form WindowJugador
     */
    public GameWindow(Player player) {
        initComponents();
        PLAYER = player;
        
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelMain = new JPanel() {
            public void paintComponent(Graphics g) {
                Image img = Toolkit.getDefaultToolkit().getImage(
                    GameWindow.class.getResource("/imagenes/game_menu_background2.jpg"));
                g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
        jLabelPoolComun = new javax.swing.JLabel();
        jLabelCommonPoolImg1 = new javax.swing.JLabel();
        jLabelConnectionTitle = new javax.swing.JLabel();
        jLabelConnectionStatus = new javax.swing.JLabel();
        jPanelActions = new javax.swing.JPanel();
        jButtonRetire = new javax.swing.JButton();
        jButtonBet = new javax.swing.JButton();
        jTextFieldBetAmmount = new javax.swing.JTextField();
        jLabelPhaseTitle = new javax.swing.JLabel();
        jLabelPhaseOutput = new javax.swing.JLabel();
        jPanelLegacy = new javax.swing.JPanel();
        jButtonGanador = new javax.swing.JButton();
        jButtonEnviar = new javax.swing.JButton();
        jButtonDestaparComunes = new javax.swing.JButton();
        jButtonDestaparPropias = new javax.swing.JButton();
        jPanelPlayerInfo = new javax.swing.JPanel();
        jLabelIdImage = new javax.swing.JLabel();
        jLabelIdOutput = new javax.swing.JLabel();
        jLabelOwnChips = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanelCards = new javax.swing.JPanel();
        jPanelCommonCards = new javax.swing.JPanel();
        jLabelCartaMesa6 = new javax.swing.JLabel();
        jLabelCartaMesa7 = new javax.swing.JLabel();
        jLabelCartaMesa8 = new javax.swing.JLabel();
        jLabelCartaMesa9 = new javax.swing.JLabel();
        jLabelCartaMesa10 = new javax.swing.JLabel();
        jPanelPrivateCards = new javax.swing.JPanel();
        jLabelCartaPropia3 = new javax.swing.JLabel();
        jLabelCartaPropia4 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuProperties = new javax.swing.JMenu();
        jMenuItemLeaveGame = new javax.swing.JMenuItem();
        jMenuItemExit = new javax.swing.JMenuItem();
        jMenuLeyend = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Game Window");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/icon.png")));

        jPanelMain.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelPoolComun.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabelPoolComun.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPoolComun.setText("<html><b>Common Pool</b></html>");
        jPanelMain.add(jLabelPoolComun, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 100, -1, 31));

        jLabelCommonPoolImg1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/chip.png"))); // NOI18N
        jPanelMain.add(jLabelCommonPoolImg1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 90, 60, 50));

        jLabelConnectionTitle.setForeground(new java.awt.Color(255, 255, 255));
        jLabelConnectionTitle.setText("Connection");
        jPanelMain.add(jLabelConnectionTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 20, 70, 30));

        jLabelConnectionStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cross.png"))); // NOI18N
        jPanelMain.add(jLabelConnectionStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 10, 80, 47));

        jPanelActions.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Actions", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 18))); // NOI18N

        jButtonRetire.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/chicken2.png"))); // NOI18N
        jButtonRetire.setText("Retire");
        jButtonRetire.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRetireActionPerformed(evt);
            }
        });

        jButtonBet.setText("Bet");
        jButtonBet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBetActionPerformed(evt);
            }
        });

        jTextFieldBetAmmount.setText("50");
        jTextFieldBetAmmount.setToolTipText("");

        jLabelPhaseTitle.setText("Phase:");

        jLabelPhaseOutput.setText("(phase)");

        javax.swing.GroupLayout jPanelActionsLayout = new javax.swing.GroupLayout(jPanelActions);
        jPanelActions.setLayout(jPanelActionsLayout);
        jPanelActionsLayout.setHorizontalGroup(
            jPanelActionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelActionsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelActionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonRetire, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelActionsLayout.createSequentialGroup()
                        .addGroup(jPanelActionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelActionsLayout.createSequentialGroup()
                                .addComponent(jTextFieldBetAmmount)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(jPanelActionsLayout.createSequentialGroup()
                                .addComponent(jLabelPhaseTitle)
                                .addGap(23, 23, 23)))
                        .addGroup(jPanelActionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabelPhaseOutput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonBet, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanelActionsLayout.setVerticalGroup(
            jPanelActionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelActionsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelActionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPhaseTitle)
                    .addComponent(jLabelPhaseOutput))
                .addGap(18, 18, 18)
                .addGroup(jPanelActionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonBet)
                    .addComponent(jTextFieldBetAmmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButtonRetire)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelMain.add(jPanelActions, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 60, 170, 170));

        jPanelLegacy.setBorder(javax.swing.BorderFactory.createTitledBorder("Legacy"));

        jButtonGanador.setText("Get Ganador");
        jButtonGanador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGanadorActionPerformed(evt);
            }
        });

        jButtonEnviar.setText("Enviar");
        jButtonEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEnviarActionPerformed(evt);
            }
        });

        jButtonDestaparComunes.setText("Destapar Comunes");
        jButtonDestaparComunes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDestaparComunesActionPerformed(evt);
            }
        });

        jButtonDestaparPropias.setText("Destapar Propias");
        jButtonDestaparPropias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDestaparPropiasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelLegacyLayout = new javax.swing.GroupLayout(jPanelLegacy);
        jPanelLegacy.setLayout(jPanelLegacyLayout);
        jPanelLegacyLayout.setHorizontalGroup(
            jPanelLegacyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLegacyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLegacyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonDestaparComunes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonDestaparPropias, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonEnviar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonGanador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelLegacyLayout.setVerticalGroup(
            jPanelLegacyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLegacyLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonDestaparPropias)
                .addGap(8, 8, 8)
                .addComponent(jButtonDestaparComunes)
                .addGap(8, 8, 8)
                .addComponent(jButtonEnviar)
                .addGap(8, 8, 8)
                .addComponent(jButtonGanador)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelMain.add(jPanelLegacy, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 240, 170, 190));

        jPanelPlayerInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Player's Info", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N

        jLabelIdImage.setForeground(new java.awt.Color(255, 255, 255));
        jLabelIdImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/user.png"))); // NOI18N

        jLabelIdOutput.setForeground(new java.awt.Color(255, 255, 255));
        jLabelIdOutput.setText("ID");

        jLabelOwnChips.setForeground(new java.awt.Color(255, 255, 255));
        jLabelOwnChips.setText("#Chips");

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/chip2.png"))); // NOI18N

        javax.swing.GroupLayout jPanelPlayerInfoLayout = new javax.swing.GroupLayout(jPanelPlayerInfo);
        jPanelPlayerInfo.setLayout(jPanelPlayerInfoLayout);
        jPanelPlayerInfoLayout.setHorizontalGroup(
            jPanelPlayerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPlayerInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelPlayerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelPlayerInfoLayout.createSequentialGroup()
                        .addComponent(jLabelIdImage, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabelIdOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelPlayerInfoLayout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabelOwnChips)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanelPlayerInfoLayout.setVerticalGroup(
            jPanelPlayerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPlayerInfoLayout.createSequentialGroup()
                .addGroup(jPanelPlayerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelIdImage, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelPlayerInfoLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabelIdOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addGroup(jPanelPlayerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelPlayerInfoLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabelOwnChips, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanelMain.add(jPanelPlayerInfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 120, 120));

        jLabelCartaMesa6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/card_back.png"))); // NOI18N

        jLabelCartaMesa7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/card_back.png"))); // NOI18N

        jLabelCartaMesa8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/card_back.png"))); // NOI18N

        jLabelCartaMesa9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/card_back.png"))); // NOI18N

        jLabelCartaMesa10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/card_back.png"))); // NOI18N

        javax.swing.GroupLayout jPanelCommonCardsLayout = new javax.swing.GroupLayout(jPanelCommonCards);
        jPanelCommonCards.setLayout(jPanelCommonCardsLayout);
        jPanelCommonCardsLayout.setHorizontalGroup(
            jPanelCommonCardsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCommonCardsLayout.createSequentialGroup()
                .addComponent(jLabelCartaMesa6)
                .addGap(44, 44, 44)
                .addComponent(jLabelCartaMesa7)
                .addGap(55, 55, 55)
                .addComponent(jLabelCartaMesa8)
                .addGap(48, 48, 48)
                .addComponent(jLabelCartaMesa9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(jLabelCartaMesa10))
        );
        jPanelCommonCardsLayout.setVerticalGroup(
            jPanelCommonCardsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCommonCardsLayout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(jPanelCommonCardsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelCommonCardsLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabelCartaMesa6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelCommonCardsLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabelCartaMesa7, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabelCartaMesa8, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelCartaMesa9, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelCartaMesa10, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        jLabelCartaPropia3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/card_back.png"))); // NOI18N

        jLabelCartaPropia4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/card_back.png"))); // NOI18N

        javax.swing.GroupLayout jPanelPrivateCardsLayout = new javax.swing.GroupLayout(jPanelPrivateCards);
        jPanelPrivateCards.setLayout(jPanelPrivateCardsLayout);
        jPanelPrivateCardsLayout.setHorizontalGroup(
            jPanelPrivateCardsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPrivateCardsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelCartaPropia3)
                .addGap(50, 50, 50)
                .addComponent(jLabelCartaPropia4)
                .addGap(16, 16, 16))
        );
        jPanelPrivateCardsLayout.setVerticalGroup(
            jPanelPrivateCardsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPrivateCardsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelPrivateCardsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelCartaPropia3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelCartaPropia4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelCardsLayout = new javax.swing.GroupLayout(jPanelCards);
        jPanelCards.setLayout(jPanelCardsLayout);
        jPanelCardsLayout.setHorizontalGroup(
            jPanelCardsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelCommonCards, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCardsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanelPrivateCards, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(160, 160, 160))
        );
        jPanelCardsLayout.setVerticalGroup(
            jPanelCardsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCardsLayout.createSequentialGroup()
                .addComponent(jPanelCommonCards, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanelPrivateCards, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        jPanelMain.add(jPanelCards, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, 570, 240));

        jMenuProperties.setText("Properties");

        jMenuItemLeaveGame.setText("Leave Game");
        jMenuProperties.add(jMenuItemLeaveGame);

        jMenuItemExit.setText("Exit");
        jMenuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemExitActionPerformed(evt);
            }
        });
        jMenuProperties.add(jMenuItemExit);

        jMenuBar1.add(jMenuProperties);

        jMenuLeyend.setText("Leyend");
        jMenuBar1.add(jMenuLeyend);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelMain, javax.swing.GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelMain, javax.swing.GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * Obtencion de las cartas propias exclusivas del Jugador.
     */
    private void getCartasPropias() {
//        if(JUGADOR.getMano().getCartasPropias().isEmpty()) {
//            JUGADOR.obtenerCartasPersonales();
//            JUGADOR.verCartasPropias(); //fixme: Testeo. Sout Cartas. Borrar Cuando no sea necesario.
//        }else System.out.println("Ya tienes tus cartas.");
    }
    
    /**
     * Obtencion de las Cartas Comunes a todos.
     */
    private void getCartasComunes() {
//        JUGADOR.obtenerCartasComunes();
//        JUGADOR.verCartasComunes(); //fixme: Testeo. Sout Cartas. Borrar Cuando no sea necesario.
    }
    
    /**
     * Accion de apostar la cantidad que haya indicada.
     */
    private void apostar() {
//        int pool = JUGADOR.apostar(Integer.parseInt(jTextField1.getText()));
//
//        if(pool != -1) {
//            if(pool != -2) {
//                this.jLabelPoolComun.setText(Integer.toString(pool));
//                this.jLabelFichasPropias.setText(Integer.toString(JUGADOR.getFichas()));
//            } else  System.out.println("Obviamente no puedes apostar fichas que no tienes...");
//        } else System.out.println("No es momento de apostar.");
    }
    
    private void comprobarJugada() {
//        Jugadas.checkJugada(JUGADOR.getMano().getCartasPropias(), JUGADOR.getMano().getCartas_mesa());
//        String jugada = Jugadas.jugada;
//        this.jLabelJugadaPropia.setText(jugada);
    }
    
    private void destaparCarta(JLabel label, Card carta) {
//        String palo = carta.getPALO();
//        int valor = Jugadas.getValor(carta);
//        String nombreIMG = "../imagenes/" +palo +"/" +palo +valor +".png".trim();
//        System.out.println("Nombre de la imagen: " +nombreIMG);
//        label.setIcon(new javax.swing.ImageIcon(getClass().getResource(nombreIMG)));
    }
    
    private void destaparPropias() {
//        destaparCarta(jLabelCartaPropia1, JUGADOR.getMano().getCartasPropias().get(0));
//        destaparCarta(jLabelCartaPropia2, JUGADOR.getMano().getCartasPropias().get(1));
    }
    
    private void destaparComunes() {
//        int cartas = JUGADOR.getMano().getCartas_mesa().size();
//        System.out.println("Cartas Length: " +cartas);
//        if(cartas == 3) {
//            destaparCarta(jLabelCartaMesa1, JUGADOR.getMano().getCartas_mesa().get(0));
//            destaparCarta(jLabelCartaMesa2, JUGADOR.getMano().getCartas_mesa().get(1));
//            destaparCarta(jLabelCartaMesa3, JUGADOR.getMano().getCartas_mesa().get(2));
//        } else {
//            if(cartas == 4) destaparCarta(jLabelCartaMesa4, JUGADOR.getMano().getCartas_mesa().get(3));
//            else {
//                if(cartas == 5) destaparCarta(jLabelCartaMesa5, JUGADOR.getMano().getCartas_mesa().get(4));
//            }
//        }
    }
    
    private void taparCarta(JLabel label) {
//        String rutaIMG = "../imagenes/windows/juego/card_back.png";
//        label.setIcon(new javax.swing.ImageIcon(getClass().getResource(rutaIMG)));
    }
    
    private void cubrirCartas() {
//        taparCarta(jLabelCartaPropia1);
//        taparCarta(jLabelCartaPropia2);
//        taparCarta(jLabelCartaMesa1);
//        taparCarta(jLabelCartaMesa2);
//        taparCarta(jLabelCartaMesa3);
//        taparCarta(jLabelCartaMesa4);
//        taparCarta(jLabelCartaMesa5);
    }
    
    private void jButtonDestaparPropiasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDestaparPropiasActionPerformed
//        getCartasPropias();
//        comprobarJugada();
//        destaparPropias();
    }//GEN-LAST:event_jButtonDestaparPropiasActionPerformed

    private void jButtonBetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBetActionPerformed
//        apostar();
    }//GEN-LAST:event_jButtonBetActionPerformed

    private void jButtonDestaparComunesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDestaparComunesActionPerformed
//        getCartasComunes();
//        comprobarJugada();
//        destaparComunes();
    }//GEN-LAST:event_jButtonDestaparComunesActionPerformed

    private void jButtonEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEnviarActionPerformed
//        JUGADOR.enviarJugada();
    }//GEN-LAST:event_jButtonEnviarActionPerformed
   
    private void jButtonGanadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGanadorActionPerformed
//        cubrirCartas();
//        int ganancias = JUGADOR.getWinner();
//        if(ganancias == -2) {
//            JOptionPane.showMessageDialog(this, "Te has quedado sin fichas y has sido eliminado.");
//            jButton1.setEnabled(false);
//            jButton2.setEnabled(false);
//            jButton3.setEnabled(false);
//            jButton4.setEnabled(false);
//            jButton5.setEnabled(false);
//            jButton6.setEnabled(false);
//            jTextField1.setEnabled(false);
//        }
//        else {
//            jLabelFichasPropias.setText(Integer.toString(ganancias));
//            jLabelPoolComun.setText("");
//            jLabelJugadaPropia.setText("");
//            JUGADOR.finRonda();
//        }
    }//GEN-LAST:event_jButtonGanadorActionPerformed

    private void retirarse() {
//        boolean retirado = JUGADOR.retirarse();
//        if(retirado) {
//            System.out.println("Jugador retirado.");
//            jButton1.setEnabled(false);
//            jButton2.setEnabled(false);
//            jButton3.setEnabled(false);
//            jButton4.setEnabled(false);
//            jButton5.setEnabled(false);
//            jButton6.setEnabled(false);
//            jTextField1.setEnabled(false);
//        }
//        else System.out.println("Problemas al retirar al jugador.");
    }
    
    private void esperarFinRonda(java.awt.event.ActionEvent evt) {
//        jButton2ActionPerformed(evt);
//        
//        jButton1.setEnabled(true);
//        jButton2.setEnabled(true);
//        jButton3.setEnabled(true);
//        jButton4.setEnabled(true);
//        jButton5.setEnabled(true);
//        jButton6.setEnabled(true);
//        jTextField1.setEnabled(true);
    }
    
    private void jButtonRetireActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRetireActionPerformed
//        retirarse();
//        new Thread(() -> esperarFinRonda(evt)).start();
    }//GEN-LAST:event_jButtonRetireActionPerformed

    private void jMenuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemExitActionPerformed
//        Conexion.cerradoCabecerasConexion();
//        this.dispose();
    }//GEN-LAST:event_jMenuItemExitActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBet;
    private javax.swing.JButton jButtonDestaparComunes;
    private javax.swing.JButton jButtonDestaparPropias;
    private javax.swing.JButton jButtonEnviar;
    private javax.swing.JButton jButtonGanador;
    private javax.swing.JButton jButtonRetire;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelCartaMesa10;
    private javax.swing.JLabel jLabelCartaMesa6;
    private javax.swing.JLabel jLabelCartaMesa7;
    private javax.swing.JLabel jLabelCartaMesa8;
    private javax.swing.JLabel jLabelCartaMesa9;
    private javax.swing.JLabel jLabelCartaPropia3;
    private javax.swing.JLabel jLabelCartaPropia4;
    private javax.swing.JLabel jLabelCommonPoolImg1;
    private javax.swing.JLabel jLabelConnectionStatus;
    private javax.swing.JLabel jLabelConnectionTitle;
    private javax.swing.JLabel jLabelIdImage;
    private javax.swing.JLabel jLabelIdOutput;
    private javax.swing.JLabel jLabelOwnChips;
    private javax.swing.JLabel jLabelPhaseOutput;
    private javax.swing.JLabel jLabelPhaseTitle;
    private javax.swing.JLabel jLabelPoolComun;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItemExit;
    private javax.swing.JMenuItem jMenuItemLeaveGame;
    private javax.swing.JMenu jMenuLeyend;
    private javax.swing.JMenu jMenuProperties;
    private javax.swing.JPanel jPanelActions;
    private javax.swing.JPanel jPanelCards;
    private javax.swing.JPanel jPanelCommonCards;
    private javax.swing.JPanel jPanelLegacy;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JPanel jPanelPlayerInfo;
    private javax.swing.JPanel jPanelPrivateCards;
    private javax.swing.JTextField jTextFieldBetAmmount;
    // End of variables declaration//GEN-END:variables
}
