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
 * Clase que implementa o comportamento do xogo do Tetris
 *
 * @author Profe de Programación
 */
public class Game {

    /**
     * Constante que define o tamaño en pixels do lado dun cadrado
     */
    public final static int SQUARE_SIDE = 20;
    /**
     * Constante que define o valor máximo da coordenada x no panel de cadrados
     */
    public final static int MAX_X = 280;

    /**
     * constante que define o valor maximo da coordenada Y no panel de cadrados
     */
    public final static int MAX_Y = 380;

    /**
     * Referenza á peza actual do xogo, que é a única que se pode mover
     */
    private Piece currentPiece;

    /**
     * Referenza á os cadrados que estan no chan
     */
    private HashMap<String, Square> groundSquares;

    /**
     * Referenza á ventá principal do xogo
     */
    private MainWindow mainWindow;

    /**
     * Flag que indica se o xogo está en pausa ou non
     */
    private boolean paused = false;

    /**
     * Número de liñas feitas no xogo
     */
    private int numberOfLines = 0;

    /**
     * @return Referenza á ventá principal do xogo
     */
    public MainWindow getMainWindow() {
        return mainWindow;
    }

    /**
     * @param mainWindow Ventá principal do xogo a establecer
     */
    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    /**
     * @return O estado de pausa do xogo
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     * @param paused O estado de pausa a establecer
     */
    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    /**
     * @return Número de liñas feitas no xogo
     */
    public int getNumberOfLines() {
        return numberOfLines;
    }

    /**
     * @param numberOfLines O número de liñas feitas no xogo
     */
    public void setNumberOfLines(int numberOfLines) {
        this.numberOfLines = numberOfLines;
    }

    /**
     * Construtor da clase, que crea unha primeira peza
     *
     * @param mainWindow Referenza á ventá principal do xogo
     */
    public Game(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.groundSquares = new HashMap();
        this.createNewPiece();
    }

    /**
     * Move a ficha actual á dereita, se o xogo non está pausado
     */
    public void movePieceRight() {
        if (!paused) {
            currentPiece.moveRight();

        }
    }

    /**
     * Move a ficha actual á esquerda, se o xogo non está pausado
     */
    public void movePieceLeft() {
        if (!paused) {
            currentPiece.moveLeft();
        }
    }

    /**
     * Rota a ficha actual, se o xogo non está pausado
     */
    public void rotatePiece() {
        if (!paused) {
            currentPiece.rotate();
        }
    }

    /**
     * Move a peza actual abaixo, se o xogo non está pausado Se a peza choca con
     * algo e xa non pode baixar, pasa a formar parte do chan e créase unha nova
     * peza
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
     * Método que permite saber se unha posición x,y é válida para un cadrado
     *
     * @param x Coordenada x
     * @param y Coordenada y
     * @return true se esa posición é válida, se non false
     */
    public boolean isValidPosition(int x, int y) {

        if ((x == MAX_X) || (x < 0) || (y == MAX_Y) || groundSquares.containsKey(x + "," + y)) {
            return false;
        }
        return true;

    }

    /**
     * Crea unha nova peza e a establece como peza actual do xogo
     */
    private void createNewPiece() {

        //crea peza aleatoria
        int pieceType = new java.util.Random().nextInt(7);

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
     * Engade unha peza ao chan
     */
    private void addPieceToGround() {
        // Engadimos os cadrados da peza ao chan
        for (Square square : currentPiece.getSquares()) {
            groundSquares.put(square.getCoordinates(), square);
        }
        // Chamamos ao método que borra as liñas do chan que estean completas
        this.deleteCompletedLines();

    }

    /**
     * Se os cadrados que están forman unha liña completa, bórranse eses
     * cadrados do chan e súmase unha nova liña no número de liñas realizadas
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
     * Borra todos os cadrados que se atopan na liña indicada pola coordenada y,
     * e baixa todos os cadrados que estean situados por enriba unha posición
     * cara abaixo
     *
     * @param y Coordenada y da liña a borrar
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
     * Comproba se a peza actual choca cos cadrados do chan
     *
     * @return true se a peza actual choca cos cadrados do chan; se non false
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
     *
     */
    public void rotate() {

        if (!paused) {
            currentPiece.rotate();

        }
    }

}
