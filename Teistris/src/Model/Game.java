/*
 * Copyright (C) 2019 Antonio de Andrés Lema
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package Model;

import View.MainWindow;
import java.util.HashMap;
import music.PlayMusic;
import teistris.BarPiece;
import teistris.LPiece;
import teistris.LiPiece;
import teistris.SqPiece;
import teistris.TPiece;
import teistris.ZPiece;
import teistris.ZiPiece;

/**
 * Clase que implementa el comportamiento del juego del Tetris.
 *
 * Esta clase gestiona el estado y la lógica del juego Tetris.
 *
 *
 */
public class Game {

    /**
     * Constante que define el tamaño en píxeles del lado de un cuadrado.
     */
    public final static int SQUARE_SIDE = 20;
    /**
     * Constante que define el valor máximo de la coordenada x en el panel de
     * cuadrados.
     */
    public final static int MAX_X = 280;

    /**
     * Constante que define el valor máximo de la coordenada Y en el panel de
     * cuadrados.
     */
    public final static int MAX_Y = 380;

    /**
     * Referencia a la pieza actual del juego, que es la única que se puede
     * mover.
     */
    private Piece currentPiece;

    /**
     * Referencia a los cuadrados que están en el suelo.
     */
    private HashMap<String, Square> groundSquares;

    /**
     * Referencia a la ventana principal del juego.
     */
    private MainWindow mainWindow;

    /**
     * Bandera que indica si el juego está en pausa o no.
     */
    private boolean paused = false;

    /**
     * Número de líneas hechas en el juego.
     */
    private int numberOfLines = 0;

    /**
     * @return Referencia a la ventana principal del juego.
     */
    public MainWindow getMainWindow() {
        return mainWindow;
    }

    /**
     * @param mainWindow Ventana principal del juego a establecer.
     */
    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    /**
     * @return El estado de pausa del juego.
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     * @param paused El estado de pausa a establecer.
     */
    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    /**
     * @return Número de líneas hechas en el juego.
     */
    public int getNumberOfLines() {
        return numberOfLines;
    }

    /**
     * @param numberOfLines El número de líneas hechas en el juego.
     */
    public void setNumberOfLines(int numberOfLines) {
        this.numberOfLines = numberOfLines;
    }

    /**
     * Constructor de la clase, que crea una primera pieza.
     *
     * @param mainWindow Referencia a la ventana principal del juego.
     */
    public Game(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.groundSquares = new HashMap();
        this.createNewPiece();
    }

    /**
     * Mueve la ficha actual a la derecha, si el juego no está en pausa.
     */
    public void movePieceRight() {
        if (!paused) {
            currentPiece.moveRight();

        }
    }

    /**
     * Mueve la ficha actual a la izquierda, si el juego no está en pausa.
     */
    public void movePieceLeft() {
        if (!paused) {
            currentPiece.moveLeft();
        }
    }

    /**
     * Rota la ficha actual, si el juego no está en pausa.
     */
    public void rotatePiece() {
        if (!paused) {
            currentPiece.rotate();
        }
    }

    /**
     * Mueve la pieza actual hacia abajo, si el juego no está en pausa. Si la
     * pieza choca con algo y ya no puede bajar, pasa a formar parte del suelo y
     * se crea una nueva pieza.
     */
    public void movePieceDown() {
        if ((!paused) && (!currentPiece.moveDown())) {
            this.addPieceToGround();
            this.createNewPiece();
            if (this.hitPieceTheGround()) {
                this.mainWindow.showGameOver();
            }
        }
    }

    /**
     * Método que permite saber si una posición x,y es válida para un cuadrado.
     *
     * @param x Coordenada x.
     * @param y Coordenada y.
     * @return true si esa posición es válida, si no, false.
     */
    public boolean isValidPosition(int x, int y) {
        // Comprueba si la posición está en los límites del panel o si ya está ocupada por otro cuadrado
        if ((x == MAX_X) || (x < 0) || (y == MAX_Y) || groundSquares.containsKey(x + "," + y)) {
            return false;
        }
        return true;

    }

    /**
     * Crea una nueva pieza y la establece como pieza actual del juego.
     */
    private void createNewPiece() {

        // Genera un número aleatorio para determinar el tipo de pieza a crear
        int pieceType = new java.util.Random().nextInt(7);

        // Crea una nueva instancia de pieza según el tipo generado aleatoriamente
        switch (pieceType) {
            case 0:
                currentPiece = new SqPiece(this);
                break;
            case 1:
                currentPiece = new LPiece(this);
                break;
            case 2:
                currentPiece = new BarPiece(this);
                break;
            case 3:
                currentPiece = new TPiece(this);
                break;
            case 4:
                currentPiece = new LiPiece(this);
                break;
            case 5:
                currentPiece = new ZPiece(this);
                break;
            case 6:
                currentPiece = new ZiPiece(this);
                break;

        }

    }

    /**
     * Añade una pieza al suelo.
     */
    private void addPieceToGround() {
        // Agrega los cuadrados de la pieza al suelo
        for (Square square : currentPiece.getSquares()) {
            groundSquares.put(square.getCoordinates(), square);
        }
        // Llama al método que elimina las líneas completadas del suelo
        this.deleteCompletedLines();

    }

    /**
     * Elimina las líneas completadas del suelo y aumenta el número de líneas
     * realizadas. Si se eliminan líneas, se actualiza el número de líneas en la
     * ventana principal.
     */
    private void deleteCompletedLines() {
        boolean lineDeleted = false;
        for (int i = 0; i < MAX_Y; i = (i + SQUARE_SIDE)) {
            int result = 0;
            for (int j = 0; j <= MAX_X; j = (j + SQUARE_SIDE)) {
                if (groundSquares.containsKey(j + "," + i)) {
                    result = (result + SQUARE_SIDE);
                }
            }
            if (result == MAX_X) {
                deleteLine(i);
                lineDeleted = true;
                numberOfLines++;
            }

        }

        if (lineDeleted) {
            mainWindow.showNumberOfLines(numberOfLines);
        }
    }

    /**
     * Elimina todos los cuadrados que están en la línea indicada por la
     * coordenada y, y desplaza hacia abajo los cuadrados que estén por encima
     * de esa línea.
     *
     * @param y Coordenada y de la línea a borrar.
     */
    private void deleteLine(int y) {
        for (int i = 0; i < MAX_X; i = (i + SQUARE_SIDE)) {
            Square sq = (Square) groundSquares.get(i + "," + y);

            mainWindow.deleteSquare(sq.getLblSquare());
            groundSquares.remove(sq.getCoordinates());
        }
        for (int d = y - SQUARE_SIDE; d >= 0; d = (d - SQUARE_SIDE)) {
            for (int j = 0; j <= MAX_X; j = (j + SQUARE_SIDE)) {
                Square squp = (Square) groundSquares.get((j + "," + d));
                if (squp != null) {

                    squp.setY(d + SQUARE_SIDE);
                    groundSquares.remove(j + "," + d);
                    groundSquares.put(squp.getCoordinates(), squp);
                }

            }
        }
    }

    /**
     * Comprueba si la pieza actual colisiona con los cuadrados del suelo.
     *
     * @return true si la pieza actual colisiona con los cuadrados del suelo; de
     * lo contrario, false.
     */
    private boolean hitPieceTheGround() {

        for (Square square : currentPiece.getSquares()) {
            if (groundSquares.containsKey(square.getCoordinates())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Rota la pieza actual, si el juego no está en pausa.
     */
    public void rotate() {

        if (!paused) {
            currentPiece.rotate();

        }
    }

}
