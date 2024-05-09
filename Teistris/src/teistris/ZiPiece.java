/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teistris;

import Model.Game;
import Model.Square;
import Model.Piece;
import java.awt.Color;

/**
 * Clase que representa una pieza en forma de Z invertida, que extiende la clase
 * Piece. Permite rotar la pieza Zi.
 *
 */
public class ZiPiece extends Piece {

    private int position = 0;

    /**
     * Constructor para la clase ZiPiece, que crea la pieza Z invertida con
     * cuatro cuadrados.
     *
     * @param game El objeto Game que representa el juego actual.
     */
    public ZiPiece(Game game) {

        this.game = game;
        squares = new Square[4];
        squares[0] = new Square(Game.MAX_X / 2 + Game.SQUARE_SIDE, 0, Color.pink, game); // Modificar la posición de la primera casilla
        squares[1] = new Square(Game.MAX_X / 2, 0, Color.pink, game);
        squares[2] = new Square(Game.MAX_X / 2, Game.SQUARE_SIDE, Color.pink, game);
        squares[3] = new Square(Game.MAX_X / 2 - Game.SQUARE_SIDE, Game.SQUARE_SIDE, Color.pink, game); // Modificar la posición de la última casilla
    }

    /**
     * Método que rota la pieza Zi.
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
                    squares[2].setX(squares[2].getX() - Game.SQUARE_SIDE);
                    squares[2].setY(squares[2].getY() - Game.SQUARE_SIDE);
                    //square 3
                    squares[3].setY(squares[3].getY() - 2 * Game.SQUARE_SIDE);
                    position++;
                    canRotate = true;
                }
                break;

            case 1:
                // Condiciones de movimiento de la pieza
                if (game.isValidPosition(squares[0].getX() + Game.SQUARE_SIDE, squares[0].getY() + Game.SQUARE_SIDE)
                        && game.isValidPosition(squares[2].getX() - Game.SQUARE_SIDE, squares[2].getY() - Game.SQUARE_SIDE)
                        && game.isValidPosition(squares[3].getX() - Game.SQUARE_SIDE, squares[3].getY())) {
                    //square 0
                    squares[0].setX(squares[0].getX() - Game.SQUARE_SIDE);
                    squares[0].setY(squares[0].getY() - Game.SQUARE_SIDE);
                    //square 2
                    squares[2].setX(squares[2].getX() + Game.SQUARE_SIDE);
                    squares[2].setY(squares[2].getY() - Game.SQUARE_SIDE);
                    //square 3
                    squares[3].setX(squares[3].getX() + 2 * Game.SQUARE_SIDE);
                    position++;
                    canRotate = true;
                }
                break;

            case 2:
                // Condiciones de movimiento de la pieza
                if (game.isValidPosition(squares[0].getX() + Game.SQUARE_SIDE, squares[0].getY() - Game.SQUARE_SIDE)
                        && game.isValidPosition(squares[2].getX() - Game.SQUARE_SIDE, squares[2].getY() + Game.SQUARE_SIDE)
                        && game.isValidPosition(squares[3].getX(), squares[3].getY() + 2 * Game.SQUARE_SIDE)) {
                    //square 0
                    squares[0].setX(squares[0].getX() + Game.SQUARE_SIDE);
                    squares[0].setY(squares[0].getY() - Game.SQUARE_SIDE);
                    //square 2
                    squares[2].setX(squares[2].getX() + Game.SQUARE_SIDE);
                    squares[2].setY(squares[2].getY() + Game.SQUARE_SIDE);
                    //square 3
                    squares[3].setY(squares[3].getY() + 2 * Game.SQUARE_SIDE);
                    position++;
                    canRotate = true;
                }
                break;

            case 3:
                // Condiciones de movimiento de la pieza
                if (game.isValidPosition(squares[0].getX() - Game.SQUARE_SIDE, squares[0].getY() - Game.SQUARE_SIDE)
                        && game.isValidPosition(squares[2].getX() + Game.SQUARE_SIDE, squares[2].getY() + Game.SQUARE_SIDE)
                        && game.isValidPosition(squares[3].getX() + 2 * Game.SQUARE_SIDE, squares[3].getY())) {
                    //zquare 0
                    squares[0].setX(squares[0].getX() + Game.SQUARE_SIDE);
                    squares[0].setY(squares[0].getY() + Game.SQUARE_SIDE);
                    //square 2
                    squares[2].setX(squares[2].getX() - Game.SQUARE_SIDE);
                    squares[2].setY(squares[2].getY() + Game.SQUARE_SIDE);
                    //square 3
                    squares[3].setX(squares[3].getX() - 2 * Game.SQUARE_SIDE);
                    position = 0;
                    canRotate = true;
                }
                break;
        }
        return canRotate;
    }

}
