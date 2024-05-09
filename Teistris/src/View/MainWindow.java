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
 * Clase que representa la ventana principal del juego Tetris.
 *
 * Esta ventana contiene la interfaz de usuario y gestiona la interacción con el
 * jugador, así como la lógica del juego y la manipulación de la base de datos
 * para almacenar las puntuaciones.
 */
public class MainWindow extends javax.swing.JFrame implements KeyEventDispatcher {

    KeyboardFocusManager keyFocus = KeyboardFocusManager.getCurrentKeyboardFocusManager();

    private Game game = null;  // Referencia al objeto del juego actual
    private int dificultyLines = 20; // Número de líneas necesarias para aumentar la dificultad
    private int delay = 1000; // Retardo inicial entre movimientos de las piezas
    private Timer timer = null; // Temporizador para controlar la caída automática de las piezas

    /**
     * Representa una variable de tecla.
     */
    public char key;
    /**
     * Lista de puntos.
     */
    private ArrayList<Integer> ponitslist = new ArrayList();
    /**
     * Fondo de imagen.
     */
    ImagenFondo background = new ImagenFondo();

    /**
     * Constructor de la clase MainWindow.
     *
     * Inicializa la ventana principal del juego Tetris, configurando su
     * contenido, como la interfaz de usuario y la gestión de eventos de
     * teclado.
     */
    public MainWindow() {
        // Establece el contenido de la ventana como el fondo de imagen
        this.setContentPane(background);
        // Deshabilita la capacidad de redimensionar la ventana
        setResizable(false);
        // Inicializa los componentes de la interfaz de usuario
        initComponents();
        // Agrega el despachador de eventos de teclado a la ventana
        keyFocus.addKeyEventDispatcher(this);
        // Establece la conexión con la base de datos
        DataBasePoints.getConnect();
        // Crea la tabla de puntuaciones en la base de datos si no existe
        DataBasePoints.createPointsTable();

        // Carga las puntuaciones desde la base de datos y las muestra en la ventana;
        loadPoints();

    }

    /**
     * Carga las puntuaciones almacenadas en la base de datos y las muestra en
     * el combo box.
     */
    private void loadPoints() {
        // Obtiene la lista de puntuaciones almacenadas en la base de datos
        ArrayList<Score> scores = DataBasePoints.getPoints();
        // Formato de fecha personalizado para mostrar la fecha y hora de la puntuación
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy  -  hh:mm");
        // Elimina todos los elementos del combo box de puntuaciones
        PointsMax.removeAllItems();
        // Itera sobre cada puntuación y la agrega al combo box con el formato adecuado
        for (Score score : scores) {
            PointsMax.addItem(score.getPoints() + " Points " + " -- " + " Date & Hour " + dateFormat.format(score.getDate()) + " -- " + " Name : " + score.getName());
        }

    }

    /**
     * Agrega un cuadrado al panel de juego.
     *
     * @param lblSquare Etiqueta con el cuadrado que se desea agregar al panel
     */
    public void drawSquare(JLabel lblSquare) {
        // Agrega la etiqueta del cuadrado al panel de juego
        pnlGame1.add(lblSquare);
        // Vuelve a pintar el panel para mostrar el cuadrado agregado
        pnlGame1.repaint();
    }

    /**
     * Elimina un cuadrado del panel de juego.
     *
     * @param lblSquare Etiqueta con el cuadrado que se desea eliminar del panel
     */
    public void deleteSquare(JLabel lblSquare) {
        // Elimina la etiqueta del cuadrado del panel de juego
        pnlGame1.remove(lblSquare);
        // Vuelve a pintar el panel para reflejar el cambio
        pnlGame1.repaint();
    }

    /**
     * Actualiza en la ventana el número de líneas completadas en el juego.
     *
     * @param numberOfLines Número de líneas completadas en el juego
     */
    public void showNumberOfLines(int numberOfLines) {
        // Obtiene el número de líneas ya completadas
        int alreadyMadeLines = Integer.parseInt(lblNumberOfLines.getText());
        // Calcula las líneas eliminadas en esta jugada
        int linesDeleted = numberOfLines - alreadyMadeLines;
        // Actualiza el número de líneas en la ventana
        lblNumberOfLines.setText(String.valueOf(numberOfLines));

        // Calcula los puntos obtenidos por las líneas eliminadas
        int pointsPerLine, totalPoints, pointsacumulator = Integer.parseInt(lblnumberOfPoints.getText()), pointsLineCalc;
        if (linesDeleted >= 4) {
            pointsPerLine = 25;
            PlayMusic.playTetrisSound();// Reproduce un sonido de Tetris
        } else {
            pointsPerLine = 10;
            PlayMusic.playLineCompletedSound();// Reproduce un sonido de línea completada
        }

        pointsLineCalc = linesDeleted * pointsPerLine;// Calcula los puntos por líneas eliminadas
        totalPoints = pointsacumulator + pointsLineCalc;// Calcula el total de puntos
        // Actualiza los puntos totales en la ventana
        lblnumberOfPoints.setText(String.valueOf(totalPoints));

        // Aumenta la velocidad del juego si se alcanza un cierto número de líneas
        if (numberOfLines >= dificultyLines) {
            delay = delay / 2;
            timer.setDelay(delay);
            dificultyLines += 20;
        }

    }

    /**
     * Muestra el mensaje de fin de juego y guarda las puntuaciones en la base
     * de datos.
     */
    public void showGameOver() {
        game = null;// Limpia la referencia al juego

        timer.stop();// Detiene el temporizado
        DataBasePoints.getPoints();// Obtiene las puntuaciones desde la base de datos

        int points = Integer.parseInt(lblnumberOfPoints.getText()); // Obtiene la cantidad de puntos del juego
        String name = JOptionPane.showInputDialog(this, "Introduce as 3 iniciais do teu Nome: ");// Solicita al usuario sus iniciales
        Score score = new Score(points, null, name);// Crea un nuevo registro de puntuación
        DataBasePoints.savePoints(score);// Guarda la puntuación en la base de datos
        loadPoints();// Actualiza la lista de puntuaciones mostrada
        PlayMusic.pararMusica();// Detiene la reproducción de música
        JOptionPane.showMessageDialog(this, "Fin do xogo");// Muestra un mensaje de fin de juego
    }

    /**
     * Maneja los eventos de teclado para controlar el movimiento de las piezas
     * y la rotación.
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent keysMove) {
        if (game != null) { // Verifica si el juego está en curso
            if (keysMove.getID() == KeyEvent.KEY_PRESSED) {// Verifica si se presionó una tecla
                switch (keysMove.getKeyCode()) {// Detecta qué tecla se presionó
                    case KeyEvent.VK_W:
                    case KeyEvent.VK_UP:

                        game.rotatePiece(); // Rota la pieza hacia arriba

                        break;
                    case KeyEvent.VK_A:
                    case KeyEvent.VK_LEFT:

                        game.movePieceLeft();// Mueve la pieza hacia la izquierda
                        break;
                    case KeyEvent.VK_S:
                    case KeyEvent.VK_DOWN:

                        game.movePieceDown();// Mueve la pieza hacia abajo
                        break;
                    case KeyEvent.VK_D:
                    case KeyEvent.VK_RIGHT:

                        game.movePieceRight();// Mueve la pieza hacia la derecha
                        break;

                }
            }

        }

        //permite que el evento sea reenviado
        return false;
    }

    /**
     * Inicia un nuevo juego.
     */
    private void startGame() {

        // Reproduce un clip de sonido
        PlayMusic.reproducirMusica();

        // Limpia todo lo que podría haberse dibujado en el panel de juego
        pnlGame1.removeAll();
        // Crea un nuevo objeto juego
        game = new Game(this);
        /// Desactiva el botón de pausa
        tglbtnPause.setSelected(false);
        // Establece el número de líneas mostradas en la ventana a cero
        lblNumberOfLines.setText("0");
        lblnumberOfPoints.setText("0");

        if (timer == null) {
            // Crea un temporizador y lo inicializa con un retraso de 1000 milisegundos
            timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent btnDownActionPerformed) {
                    if (game != null) {
                        game.movePieceDown();
                    }
                }
            });
            // Inicia el temporizador
            timer.start();

        } else {
            // Restablece el temporizador al retraso inicial
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
                        .addContainerGap(79, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(PointsMax, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnNewGame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(27, 27, 27)
                                .addComponent(tglbtnPause, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(EraseButton)))
                        .addContainerGap())))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(116, 116, 116)
                .addComponent(jLabel3)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 57, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
/**
     * Al hacer clic en el botón "Nueva partida", invocamos al método privado
     * que inicia un nuevo juego
     *
     * @param evt
     */
    private void btnNewGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewGameActionPerformed

        startGame();

    }//GEN-LAST:event_btnNewGameActionPerformed
/**
 * Al hacer clic en el botón de "Pausa", llamamos al objeto de juego para
 * establecer el atributo de pausa según el estado del botón
 * @param evt 
 */
    private void tglbtnPauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tglbtnPauseActionPerformed
 
        if (game != null) {
            game.setPaused(tglbtnPause.isSelected());

            // Cuando el botón de pausa está seleccionado, se llaman a los métodos para detener y para iniciar el clip de sonido
            if (tglbtnPause.isSelected()) {
                PlayMusic.pararMusica();
            } else {
                PlayMusic.reproducirMusica();
            }
        }
    }//GEN-LAST:event_tglbtnPauseActionPerformed
/**
 * Al hacer clic en el botón de "Rotar", llamamos al objeto de juego para que
 * rote la pieza actual
 * @param evt 
 */
    private void btnRotateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRotateActionPerformed
     
        if (game != null) {
            game.rotatePiece();
        }
    }//GEN-LAST:event_btnRotateActionPerformed
/**
 * Al hacer clic en el botón de "Izquierda", llamamos al objeto de juego para que
 * mueva la pieza actual hacia la izquierda
 * @param evt 
 */
    private void btnLeftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLeftActionPerformed

        if (game != null) {
            game.movePieceLeft();
        }
    }//GEN-LAST:event_btnLeftActionPerformed
/**
 * Al hacer clic en el botón de "Derecha", llamamos al objeto de juego para que
 * mueva la pieza actual hacia la derecha
 * @param evt 
 */
    private void btnRightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRightActionPerformed
        // Ao picar no botón de "Dereita", chamamos ao obxecto xogo para que
        // se mova a peza actual á dereita
        if (game != null) {
            game.movePieceRight();
        }
    }//GEN-LAST:event_btnRightActionPerformed
/**
 * Al hacer clic en el botón de "Derecha", llamamos al objeto de juego para que
 *  mueva la pieza actual hacia la derecha
 * @param evt 
 */
    private void btnDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDownActionPerformed
        // Ao picar no botón de "Abaixo", chamamos ao obxecto xogo para que
        // se mova a peza actual cara abaixo
        if (game != null) {
            game.movePieceDown();
        }
    }//GEN-LAST:event_btnDownActionPerformed
/**
 * Al hacer clic en el botón de "Borrar", borramos todos los puntos de la base de datos
 * y eliminamos todos los elementos del combo box
 * @param evt 
 */
    private void EraseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EraseButtonActionPerformed
        // TODO add your handling code here:
        DataBasePoints.deleteAllPoints();
        PointsMax.removeAllItems();
        // loadPoints();
    }//GEN-LAST:event_EraseButtonActionPerformed

    /**
     * Método principal que inicia la aplicación.
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
                // Configura el aspecto y la sensación del Swing.
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lblLines;
    private javax.swing.JLabel lblNumberOfLines;
    private javax.swing.JLabel lblnumberOfPoints;
    private javax.swing.JPanel pnlGame;
    private javax.swing.JPanel pnlGame1;
    private javax.swing.JToggleButton tglbtnPause;
    // End of variables declaration//GEN-END:variables

    /**
     * Clase que proporciona un panel con una imagen de fondo. Esta clase
     * extiende JPanel y se encarga de pintar la imagen de fondo en el panel.
     */
    public class ImagenFondo extends JPanel {

        private Image imagen;

        /**
         * Método para pintar la imagen de fondo en el panel.
         *
         * @param g Objeto Graphics utilizado para dibujar en el panel.
         */
        @Override
        public void paint(Graphics g) {
            // Carga la imagen desde el recurso ubicado en "/View/fondo.jpg"
            imagen = new ImageIcon(getClass().getResource("/imagen/fondo.jpg")).getImage();
            // Dibuja la imagen en el panel con las dimensiones del panel
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
            // Hace que el panel sea transparente para que la imagen de fondo se vea a través de él   
            setOpaque(false);
            // Pinta los componentes hijos del panel
            super.paint(g);

        }
    }

}
