/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import DB.DataBasePoints;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import Model.Game;
import Model.Score;
import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import music.PlayMusic;

/**
 * Clase que implementa a ventá principal do xogo do Tetris
 *
 * @author Profe de Programación
 */
public class MainWindow extends javax.swing.JFrame implements KeyEventDispatcher {

    KeyboardFocusManager keyFocus = KeyboardFocusManager.getCurrentKeyboardFocusManager();

    private Game game = null; // Referenza ao obxecto do xogo actual
    private int dificultyLines = 20;
    private int delay = 1000;
    private Timer timer = null;

    /**
     *
     */
    public char key;
    private ArrayList<Integer> ponitslist = new ArrayList();

    ImagenFondo background = new ImagenFondo();

    /**
     * Creates new form MainWindow
     */
    public MainWindow() {

        this.setContentPane(background);

        setResizable(false);

        initComponents();

        keyFocus.addKeyEventDispatcher(this);

        DataBasePoints.getConnect();

        DataBasePoints.createPointsTable();

        //  DataBasePoints.getPoints();
        loadPoints();

    }

    /**
     * Recarga o combobox cos datos das puntuacións
     */
    private void loadPoints() {
        ArrayList<Score> scores = DataBasePoints.getPoints();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy  -  hh:mm");

        PointsMax.removeAllItems();

        for (Score score : scores) {
            PointsMax.addItem(score.getPoints() + " Points " + " -- " + " Date & Hour " + dateFormat.format(score.getDate()) + " -- " + " Name : " + score.getName());
        }

    }

    /**
     * Pinta un cadrado no panel de cadrados
     *
     * @param lblSquare Etiqueta co cadrado que se quere pintar no panel
     */
    public void drawSquare(JLabel lblSquare) {
        pnlGame1.add(lblSquare);
        pnlGame1.repaint();
    }

    /**
     * Borra un cadrado do panel de cadrados
     *
     * @param lblSquare Etiqueta co cadrado que se quere borrar do panel
     */
    public void deleteSquare(JLabel lblSquare) {
        pnlGame1.remove(lblSquare);
        pnlGame1.repaint();
    }

    /**
     * Actualiza na ventá o número de liñas que van feitas no xogo
     *
     * @param numberOfLines Número de liñas feitas no xogo
     */
    public void showNumberOfLines(int numberOfLines) {
        int alreadyMadeLines = Integer.parseInt(lblNumberOfLines.getText());
        
        int linesDeleted = numberOfLines - alreadyMadeLines;
        
        lblNumberOfLines.setText(String.valueOf(numberOfLines));

        //muestra puntos
        int pointsPerLine, totalPoints, pointsacumulator =Integer.parseInt(lblnumberOfPoints.getText()), pointsLineCalc;
        if (linesDeleted >= 4) {
            pointsPerLine = 25;
            PlayMusic.playTetrisSound();
        } else {
            pointsPerLine = 10;
            PlayMusic.playLineCompletedSound();
        }
       
        pointsLineCalc = linesDeleted * pointsPerLine;
        totalPoints = pointsacumulator + pointsLineCalc;
    
        
        lblnumberOfPoints.setText(String.valueOf(totalPoints));

        //aaumenta la velocidad del juego
        if (numberOfLines >= dificultyLines) {
            delay = delay / 2;
            timer.setDelay(delay);
            dificultyLines += 20;
        }

    }

    /**
     * Metodo que mostra a mensaxe de final do xogo tamen se gardan os puntos e
     * se cargan no combo box e para a musica
     */
    public void showGameOver() {
        game = null;

        timer.stop();
        DataBasePoints.getPoints();

        int points = Integer.parseInt(lblnumberOfPoints.getText());
        String name = JOptionPane.showInputDialog(this, "Introduce as 3 iniciais do teu Nome: ");
        Score score = new Score(points, null, name);
        DataBasePoints.savePoints(score);
        loadPoints();
        PlayMusic.pararMusica();
        JOptionPane.showMessageDialog(this, "Fin do xogo");
    }

    //Switch que controla o movemento das pezas e a rotacion mediante tecras
    /**
     *
     * @param keysMove
     * @return
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent keysMove) {
        if (game != null) {
            if (keysMove.getID() == KeyEvent.KEY_PRESSED) {
                switch (keysMove.getKeyCode()) {
                    case KeyEvent.VK_W:
                    case KeyEvent.VK_UP:

                        game.rotatePiece();

                        break;
                    case KeyEvent.VK_A:
                    case KeyEvent.VK_LEFT:

                        game.movePieceLeft();
                        break;
                    case KeyEvent.VK_S:
                    case KeyEvent.VK_DOWN:

                        game.movePieceDown();
                        break;
                    case KeyEvent.VK_D:
                    case KeyEvent.VK_RIGHT:

                        game.movePieceRight();
                        break;

                }
            }

        }

        //permite que el evento sea reenviado
        return false;
    }

    /**
     * Inicia un novo xogo
     */
    private void startGame() {

        //chama o metodo reproducirMusica da clase PlayMusic para reproducir un clip de son
        PlayMusic.reproducirMusica();

        // Limpamos todo o que puidese haber pintado no panel do xogo
        pnlGame1.removeAll();
        // Creamos un novo obxecto xogo
        game = new Game(this);
        // Desactivamos o botón de pausa
        tglbtnPause.setSelected(false);
        // Establecemos o número de liñas que se mostran na ventá a cero
        lblNumberOfLines.setText("0");
        lblnumberOfPoints.setText("0");

        if (timer == null) {
            //creamos un timer e facemos que o volver a inicialo se setee el timer al deley inicial
            timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent btnDownActionPerformed) {
                    if (game != null) {
                        game.movePieceDown();
                    }
                }
            });
            //inicia o timer
            timer.start();

        } else {
            timer.setDelay(1000);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlGame = new javax.swing.JPanel();
        btnNewGame = new javax.swing.JButton();
        tglbtnPause = new javax.swing.JToggleButton();
        pnlGame1 = new javax.swing.JPanel();
        btnRotate = new javax.swing.JButton();
        btnLeft = new javax.swing.JButton();
        btnRight = new javax.swing.JButton();
        btnDown = new javax.swing.JButton();
        lblLines = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblNumberOfLines = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblnumberOfPoints = new javax.swing.JLabel();
        PointsMax = new javax.swing.JComboBox<>();
        EraseButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        pnlGame.setBackground(java.awt.Color.white);
        pnlGame.setPreferredSize(new java.awt.Dimension(160, 200));

        javax.swing.GroupLayout pnlGameLayout = new javax.swing.GroupLayout(pnlGame);
        pnlGame.setLayout(pnlGameLayout);
        pnlGameLayout.setHorizontalGroup(
            pnlGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 494, Short.MAX_VALUE)
        );
        pnlGameLayout.setVerticalGroup(
            pnlGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 433, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("The Crazy Game of Squaares");

        btnNewGame.setBackground(new java.awt.Color(0, 0, 0));
        btnNewGame.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        btnNewGame.setForeground(new java.awt.Color(255, 255, 255));
        btnNewGame.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/botoninicio.png"))); // NOI18N
        btnNewGame.setText("Nova partida");
        btnNewGame.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btnNewGame.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnNewGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewGameActionPerformed(evt);
            }
        });

        tglbtnPause.setBackground(new java.awt.Color(0, 0, 0));
        tglbtnPause.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        tglbtnPause.setForeground(new java.awt.Color(255, 255, 255));
        tglbtnPause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/botonPausa.png"))); // NOI18N
        tglbtnPause.setText("Pausa");
        tglbtnPause.setToolTipText("");
        tglbtnPause.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tglbtnPause.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tglbtnPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tglbtnPauseActionPerformed(evt);
            }
        });

        pnlGame1.setBackground(java.awt.Color.black);
        pnlGame1.setPreferredSize(new java.awt.Dimension(180, 200));

        javax.swing.GroupLayout pnlGame1Layout = new javax.swing.GroupLayout(pnlGame1);
        pnlGame1.setLayout(pnlGame1Layout);
        pnlGame1Layout.setHorizontalGroup(
            pnlGame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 280, Short.MAX_VALUE)
        );
        pnlGame1Layout.setVerticalGroup(
            pnlGame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 380, Short.MAX_VALUE)
        );

        btnRotate.setBackground(new java.awt.Color(0, 0, 0));
        btnRotate.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        btnRotate.setForeground(new java.awt.Color(255, 255, 255));
        btnRotate.setText("Rotar / w");
        btnRotate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btnRotate.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnRotate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRotateActionPerformed(evt);
            }
        });

        btnLeft.setBackground(new java.awt.Color(0, 0, 0));
        btnLeft.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        btnLeft.setForeground(new java.awt.Color(255, 255, 255));
        btnLeft.setText("Esquerda / a");
        btnLeft.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btnLeft.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnLeft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLeftActionPerformed(evt);
            }
        });

        btnRight.setBackground(new java.awt.Color(0, 0, 0));
        btnRight.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        btnRight.setForeground(new java.awt.Color(255, 255, 255));
        btnRight.setText("Dereita / d");
        btnRight.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btnRight.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnRight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRightActionPerformed(evt);
            }
        });

        btnDown.setBackground(new java.awt.Color(0, 0, 0));
        btnDown.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        btnDown.setForeground(new java.awt.Color(255, 255, 255));
        btnDown.setText("Abaixo / s");
        btnDown.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btnDown.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDownActionPerformed(evt);
            }
        });

        lblLines.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblLines.setForeground(new java.awt.Color(255, 255, 255));
        lblLines.setText("Liñas:");

        jLabel1.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Created by Ana P. & Ruben FP.");

        lblNumberOfLines.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblNumberOfLines.setForeground(new java.awt.Color(255, 51, 51));
        lblNumberOfLines.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Puntos:");

        lblnumberOfPoints.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblnumberOfPoints.setForeground(new java.awt.Color(102, 255, 102));
        lblnumberOfPoints.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        PointsMax.setBackground(new java.awt.Color(0, 0, 0));
        PointsMax.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        PointsMax.setForeground(new java.awt.Color(102, 255, 255));
        PointsMax.setMaximumRowCount(50);

        EraseButton.setBackground(new java.awt.Color(0, 0, 0));
        EraseButton.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        EraseButton.setForeground(new java.awt.Color(255, 255, 255));
        EraseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/papelera.png"))); // NOI18N
        EraseButton.setText("Borrar Puntuaciones");
        EraseButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        EraseButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        EraseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EraseButtonActionPerformed(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/logo.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblLines)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblNumberOfLines, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblnumberOfPoints, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(btnRotate, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(pnlGame1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(EraseButton))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnNewGame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(27, 27, 27)
                                .addComponent(tglbtnPause, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(122, 122, 122)
                                .addComponent(btnLeft)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnRight, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(148, 148, 148)
                                .addComponent(btnDown, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(PointsMax, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNewGame)
                    .addComponent(tglbtnPause, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlGame1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLines, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNumberOfLines, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblnumberOfPoints, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRotate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLeft)
                    .addComponent(btnRight))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDown)
                .addGap(24, 24, 24)
                .addComponent(PointsMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(EraseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewGameActionPerformed
        // Ao picar no botón de "Nova partida", invocamos ao método privado
        // que inicia un novo xogo
        startGame();

    }//GEN-LAST:event_btnNewGameActionPerformed

    private void tglbtnPauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tglbtnPauseActionPerformed
        // Ao picar no botón de "Pausa", chamamos ao obxecto xogo para
        // establecer o atributo de pausa no estado do botón
        if (game != null) {
            game.setPaused(tglbtnPause.isSelected());

            //cando o boton de pausa este seleccionado llamase os metodos para deter e para iniciar o clip de son
            if (tglbtnPause.isSelected()) {
                PlayMusic.pararMusica();
            } else {
                PlayMusic.reproducirMusica();
            }
        }
    }//GEN-LAST:event_tglbtnPauseActionPerformed

    private void btnRotateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRotateActionPerformed
        // Ao picar no botón de "Rotar", chamamos ao obxecto xogo para que
        // rote a peza actual
        if (game != null) {
            game.rotatePiece();
        }
    }//GEN-LAST:event_btnRotateActionPerformed

    private void btnLeftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLeftActionPerformed
        // Ao picar no botón de "Esquerda", chamamos ao obxecto xogo para que
        // se mova a peza actual á esquerda
        if (game != null) {
            game.movePieceLeft();
        }
    }//GEN-LAST:event_btnLeftActionPerformed

    private void btnRightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRightActionPerformed
        // Ao picar no botón de "Dereita", chamamos ao obxecto xogo para que
        // se mova a peza actual á dereita
        if (game != null) {
            game.movePieceRight();
        }
    }//GEN-LAST:event_btnRightActionPerformed

    private void btnDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDownActionPerformed
        // Ao picar no botón de "Abaixo", chamamos ao obxecto xogo para que
        // se mova a peza actual cara abaixo
        if (game != null) {
            game.movePieceDown();
        }
    }//GEN-LAST:event_btnDownActionPerformed

    private void EraseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EraseButtonActionPerformed
        // TODO add your handling code here:
        DataBasePoints.deleteAllPoints();
        PointsMax.removeAllItems();
        // loadPoints();
    }//GEN-LAST:event_EraseButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                /**
                 * Try - Catch que fai que o Look and Feel usado sea FlatDarkLaf
                 * que o temos na libreria FlatLaf-3.0.jar
                 */
                try {
                    UIManager.setLookAndFeel(new FlatDarkLaf());
                } catch (Exception ex) {
                    System.err.println("Failed to initialize LaF");
                }

                // create UI here..
                new MainWindow().setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton EraseButton;
    private javax.swing.JComboBox<String> PointsMax;
    private javax.swing.JButton btnDown;
    private javax.swing.JButton btnLeft;
    private javax.swing.JButton btnNewGame;
    private javax.swing.JButton btnRight;
    private javax.swing.JButton btnRotate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lblLines;
    private javax.swing.JLabel lblNumberOfLines;
    private javax.swing.JLabel lblnumberOfPoints;
    private javax.swing.JPanel pnlGame;
    private javax.swing.JPanel pnlGame1;
    private javax.swing.JToggleButton tglbtnPause;
    // End of variables declaration//GEN-END:variables

    // clase interna para colocar imagen de fondo al  JFrame
    /**
     * clase que vai cargar unha imaxen de fondo no Jframe
     */
    public class ImagenFondo extends JPanel {

        private Image imagen;

        /**
         * metodo que pinta una imaxen de fondo no Jframe
         *
         * @param g
         */
        @Override
        public void paint(Graphics g) {

            imagen = new ImageIcon(getClass().getResource("/imagen/fondo.jpg")).getImage();

            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
            setOpaque(false);
            super.paint(g);

        }
    }

}
