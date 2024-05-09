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

import java.awt.Color;

/**
 * Clase abstracta que representa una pieza del juego Tetris.
 *
 * Esta clase define la estructura básica y el comportamiento de las piezas del
 * Tetris. Cada pieza del juego extiende esta clase y proporciona su propia
 * implementación para los métodos de movimiento y rotación.
 *
 *
 */
public abstract class Piece {

    /**
     * Referencia al objeto de juego.
     */
    protected Game game;

    /**
     * Referencia a los cuatro cuadrados que forman la pieza.
     */
    //protected Square a = squares[0], b = squares[1], c = squares[2], d = squares[3];
    protected Square[] squares;

    /**
     * Obtiene la referencia al objeto de juego.
     *
     * @return Referencia al objeto de juego.
     */
    public Game getGame() {
        return game;
    }

    /**
     * Establece la referencia al objeto de juego.
     *
     * @param game Objeto de juego a establecer.
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Obtiene los cuadrados que forman la pieza.
     *
     * @return Arreglo de cuadrados que forman la pieza.
     */
    public Square[] getSquares() {
        return squares;
    }

    /**
     * Establece los cuadrados que forman la pieza.
     *
     * @param squares Arreglo de cuadrados que forman la pieza.
     */
    public void setSquares(Square[] squares) {
        this.squares = squares;
    }

    /**
     * Mueve la ficha hacia la derecha si es posible.
     *
     * @return true si el movimiento de la ficha es posible, de lo contrario
     * false.
     */
    public boolean moveRight() {

        boolean move = true;

        for (Square sq : squares) {
            if (!game.isValidPosition(sq.getX() + Game.SQUARE_SIDE, sq.getY())) {
                move = false;
            }
        }

        if (move) {
            for (Square i : squares) {
                i.setX(i.getX() + Game.SQUARE_SIDE);
            }
            return true;
        }
        return false;

    }

    /**
     * Mueve la ficha hacia la izquierda si es posible.
     *
     * @return true si el movimiento de la ficha es posible, de lo contrario
     * false.
     */
    public boolean moveLeft() {

        boolean move = true;

        for (Square sq : squares) {
            if (!game.isValidPosition(sq.getX() - Game.SQUARE_SIDE, sq.getY())) {
                move = false;
            }
        }
        if (move) {
            for (Square i : squares) {
                i.setX(i.getX() - Game.SQUARE_SIDE);
            }
            return true;
        }
        return false;
    }

    /**
     * Mueve la ficha hacia abajo si es posible.
     *
     * @return true si el movimiento de la ficha es posible, de lo contrario
     * false.
     */
    public boolean moveDown() {

        boolean move = true;

        for (Square sq : squares) {
            if (!game.isValidPosition(sq.getX(), sq.getY() + Game.SQUARE_SIDE)) {
                move = false;
            }
        }
        if (move) {

            for (Square i : squares) {
                i.setY(i.getY() + Game.SQUARE_SIDE);
            }
            return true;
        }
        return false;

    }

    /**
     * Rota la ficha si es posible.
     *
     * @return true si el movimiento de la ficha es posible, de lo contrario
     * false.
     */
    public boolean rotate() {
        // A rotación da ficha cadrada non supón ningunha variación na ficha,
        // por iso simplemente devolvemos true
        return true;
    }

}
