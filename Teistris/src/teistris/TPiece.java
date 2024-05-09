/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teistris;

import Model.Game;
import Model.Piece;
import Model.Square;
import java.awt.Color;

/**
 * Clase que representa una pieza en forma de T, que extiende la clase Piece.
 * Permite rotar la pieza T.
 *
 */
public class TPiece extends Piece {

    private int position = 0;

    /**
     * Constructor para la clase TPiece, que crea la pieza T con cuatro
     * cuadrados.
     *
     * @param game El objeto Game que representa el juego actual.
     */
    public TPiece(Game game) {

        this.game = game;
        squares = new Square[4];
        squares[0] = new Square(Game.MAX_X / 2 - Game.SQUARE_SIDE, 0, Color.red, game);
        squares[1] = new Square(Game.MAX_X / 2 - Game.SQUARE_SIDE, Game.SQUARE_SIDE, Color.red, game);
        squares[2] = new Square(Game.MAX_X / 2 - Game.SQUARE_SIDE, 2 * Game.SQUARE_SIDE, Color.red, game);
        squares[3] = new Square(Game.MAX_X / 2, 1 * Game.SQUARE_SIDE, Color.red, game);

    }

    /**
     * Método para rotar la pieza T.
     *
     * @return true si la rotación fue posible, false si no.
     */
    @Override
    public boolean rotate() {

        boolean canRotate = false;
        switch (position) {
            case 0:
                // Condiciones de movimiento de la pieza
                if (game.isValidPosition(squares[0].getX() - Game.SQUARE_SIDE, squares[0].getY() + Game.SQUARE_SIDE)
                        && game.isValidPosition(squares[2].getX() + Game.SQUARE_SIDE, squares[2].getY() - Game.SQUARE_SIDE)
                        && game.isValidPosition(squares[3].getX(), squares[3].getY() - 2 * Game.SQUARE_SIDE)) {
                    //aquere 0
                    squares[0].setX(squares[0].getX() - Game.SQUARE_SIDE);
                    squares[0].setY(squares[0].getY() + Game.SQUARE_SIDE);
                    //square 2
                    squares[2].setX(squares[2].getX() + Game.SQUARE_SIDE);
                    squares[2].setY(squares[2].getY() - Game.SQUARE_SIDE);
                    //square 3
                    squares[3].setX(squares[3].getX() - Game.SQUARE_SIDE);
                    squares[3].setY(squares[3].getY() + Game.SQUARE_SIDE);
                    position++;
                    canRotate = true;
                }
                break;
            case 1:
                // Condiciones de movimiento de la pieza
                if (game.isValidPosition(squares[0].getX() + Game.SQUARE_SIDE, squares[0].getY() + -Game.SQUARE_SIDE)
                        && game.isValidPosition(squares[2].getX() - Game.SQUARE_SIDE, squares[2].getY() + Game.SQUARE_SIDE)
                        && game.isValidPosition(squares[3].getX() - Game.SQUARE_SIDE, squares[3].getY() - Game.SQUARE_SIDE)) {
                    //aquere 0
                    squares[0].setX(squares[0].getX() + Game.SQUARE_SIDE);
                    squares[0].setY(squares[0].getY() - Game.SQUARE_SIDE);
                    //square 2
                    squares[2].setX(squares[2].getX() - Game.SQUARE_SIDE);
                    squares[2].setY(squares[2].getY() + Game.SQUARE_SIDE);
                    //square 3
                    squares[3].setX(squares[3].getX() - Game.SQUARE_SIDE);
                    squares[3].setY(squares[3].getY() - Game.SQUARE_SIDE);
                    position++;
                    canRotate = true;
                }
                break;
            case 2:
                // Condiciones de movimiento de la pieza
                if (game.isValidPosition(squares[0].getX() - Game.SQUARE_SIDE, squares[0].getY() + Game.SQUARE_SIDE)
                        && game.isValidPosition(squares[2].getX() + Game.SQUARE_SIDE, squares[2].getY() - Game.SQUARE_SIDE)
                        && game.isValidPosition(squares[3].getX(), squares[3].getY() - 2 * Game.SQUARE_SIDE)) {
                    //aquere 0
                    squares[0].setX(squares[0].getX() - Game.SQUARE_SIDE);
                    squares[0].setY(squares[0].getY() + Game.SQUARE_SIDE);
                    //square 2
                    squares[2].setX(squares[2].getX() + Game.SQUARE_SIDE);
                    squares[2].setY(squares[2].getY() - Game.SQUARE_SIDE);
                    //square 3
                    squares[3].setX(squares[3].getX() + Game.SQUARE_SIDE);
                    squares[3].setY(squares[3].getY() - Game.SQUARE_SIDE);
                    position++;
                    canRotate = true;
                }
                break;
            case 3:
                // Condiciones de movimiento de la pieza
                if (game.isValidPosition(squares[0].getX() - Game.SQUARE_SIDE, squares[0].getY() + Game.SQUARE_SIDE)
                        && game.isValidPosition(squares[2].getX() + Game.SQUARE_SIDE, squares[2].getY() - Game.SQUARE_SIDE)
                        && game.isValidPosition(squares[3].getX(), squares[3].getY() - 2 * Game.SQUARE_SIDE)) {
                    //aquere 0
                    squares[0].setX(squares[0].getX() + Game.SQUARE_SIDE);
                    squares[0].setY(squares[0].getY() - Game.SQUARE_SIDE);
                    //square 2
                    squares[2].setX(squares[2].getX() - Game.SQUARE_SIDE);
                    squares[2].setY(squares[2].getY() + Game.SQUARE_SIDE);
                    //square 3
                    squares[3].setX(squares[3].getX() + Game.SQUARE_SIDE);
                    squares[3].setY(squares[3].getY() + Game.SQUARE_SIDE);
                    position = 0;
                    canRotate = true;
                }
                break;

        }
        return canRotate;
    }

}
