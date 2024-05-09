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
 * Esta clase representa una pieza de tipo Barra en el juego Tetris. La pieza
 * Barra se compone de cuatro cuadrados dispuestos en línea vertical. Esta clase
 * gestiona la creación y rotación de la pieza.
 *
 *
 */
public class BarPiece extends Piece {

    private int position = 0;

    /**
     * Crea una nueva instancia de la pieza Barra en el juego Tetris.
     *
     * @param game El juego en el que se encuentra la pieza.
     */
    public BarPiece(Game game) {

        this.game = game;

        squares = new Square[4];
        squares[0] = new Square(Game.MAX_X / 2 - Game.SQUARE_SIDE, 0, Color.yellow, game);
        squares[1] = new Square(Game.MAX_X / 2 - Game.SQUARE_SIDE, Game.SQUARE_SIDE, Color.yellow, game);
        squares[2] = new Square(Game.MAX_X / 2 - Game.SQUARE_SIDE, 2 * Game.SQUARE_SIDE, Color.yellow, game);
        squares[3] = new Square(Game.MAX_X / 2 - Game.SQUARE_SIDE, 3 * Game.SQUARE_SIDE, Color.yellow, game);

    }

    /**
     * Rota la pieza Barra en el sentido de las agujas del reloj.
     *
     * @return true si la rotación es posible, de lo contrario false.
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
                    //square 0
                    squares[0].setX(squares[0].getX() - Game.SQUARE_SIDE);
                    squares[0].setY(squares[0].getY() + Game.SQUARE_SIDE);
                    //square 2
                    squares[2].setX(squares[2].getX() + Game.SQUARE_SIDE);
                    squares[2].setY(squares[2].getY() - Game.SQUARE_SIDE);
                    //square 3
                    squares[3].setX(squares[3].getX() + 2 * Game.SQUARE_SIDE);
                    squares[3].setY(squares[3].getY() - 2 * Game.SQUARE_SIDE);;
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
                    squares[0].setX(squares[0].getX() + Game.SQUARE_SIDE);
                    squares[0].setY(squares[0].getY() - Game.SQUARE_SIDE);
                    //square 2
                    squares[2].setX(squares[2].getX() - Game.SQUARE_SIDE);
                    squares[2].setY(squares[2].getY() + Game.SQUARE_SIDE);
                    //square 3
                    squares[3].setX(squares[3].getX() - 2 * Game.SQUARE_SIDE);
                    squares[3].setY(squares[3].getY() + 2 * Game.SQUARE_SIDE);
                    position = 0;
                    canRotate = true;
                }
                break;
            case 2:
                // Condiciones de movimiento de la pieza
                if (game.isValidPosition(squares[0].getX() - Game.SQUARE_SIDE, squares[0].getY() + Game.SQUARE_SIDE)
                        && game.isValidPosition(squares[2].getX() + Game.SQUARE_SIDE, squares[2].getY() - Game.SQUARE_SIDE)
                        && game.isValidPosition(squares[3].getX(), squares[3].getY() - 2 * Game.SQUARE_SIDE)) {
                    //square 0
                    squares[0].setX(squares[0].getX() - Game.SQUARE_SIDE);
                    squares[0].setY(squares[0].getY() + Game.SQUARE_SIDE);
                    //square 2
                    squares[2].setX(squares[2].getX() + Game.SQUARE_SIDE);
                    squares[2].setY(squares[2].getY() - Game.SQUARE_SIDE);
                    //square 3
                    squares[3].setX(squares[3].getX() + 2 * Game.SQUARE_SIDE);
                    squares[3].setY(squares[3].getY() - 2 * Game.SQUARE_SIDE);;
                    position++;
                    canRotate = true;
                }
            case 3:
                // Condiciones de movimiento de la pieza
                if (game.isValidPosition(squares[0].getX() + Game.SQUARE_SIDE, squares[0].getY() + Game.SQUARE_SIDE)
                        && game.isValidPosition(squares[2].getX() - Game.SQUARE_SIDE, squares[2].getY() - Game.SQUARE_SIDE)
                        && game.isValidPosition(squares[3].getX() - Game.SQUARE_SIDE, squares[3].getY())) {
                    //square 0
                    squares[0].setX(squares[0].getX() + Game.SQUARE_SIDE);
                    squares[0].setY(squares[0].getY() - Game.SQUARE_SIDE);
                    //square 2
                    squares[2].setX(squares[2].getX() - Game.SQUARE_SIDE);
                    squares[2].setY(squares[2].getY() + Game.SQUARE_SIDE);
                    //square 3
                    squares[3].setX(squares[3].getX() - 2 * Game.SQUARE_SIDE);
                    squares[3].setY(squares[3].getY() + 2 * Game.SQUARE_SIDE);
                    position = 0;
                    canRotate = true;
                }

        }

        return canRotate;
    }

}
