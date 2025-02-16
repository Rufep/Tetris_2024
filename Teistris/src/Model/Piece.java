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
 * Clase que implementa a peza cadrada do xogo do Tetris
 *
 * @author Profe de Programación
 */
public abstract class Piece {

    /**
     * Referenza ao obxecto xogo
     */
    protected Game game;

    /**
     * Referenzas aos catro cadrados que forman a peza
     */
    //protected Square a = squares[0], b = squares[1], c = squares[2], d = squares[3];
    protected Square[] squares;

    /**
     *
     * @return
     */
    public Game getGame() {
        return game;
    }

    /**
     *
     * @param game
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     *
     * @return
     */
    public Square[] getSquares() {
        return squares;
    }

    /**
     *
     * @param squares
     */
    public void setSquares(Square[] squares) {
        this.squares = squares;
    }

  
 
    /**
     * Move a ficha á dereita se é posible
     *
     * @return true se o movemento da ficha é posible, se non false
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
     * Move a ficha á esquerda se é posible
     *
     * @return true se o movemento da ficha é posible, se non false
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
     * Move a ficha a abaixo se é posible
     *
     * @return true se o movemento da ficha é posible, se non false
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
     * Rota a ficha se é posible
     *
     * @return true se o movemento da ficha é posible, se non false
     */
    public boolean rotate() {
        // A rotación da ficha cadrada non supón ningunha variación na ficha,
        // por iso simplemente devolvemos true
        return true;
    }

}
