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
 * Esta clase representa una pieza cuadrada, que extiende la clase Piece. No
 * rota ya que es un cuadrado.
 *
 */
public class SqPiece extends Piece {

    /**
     * Constructor para la clase SqPiece, que crea cuatro cuadrados formando la
     * pieza cuadrada.
     *
     * @param game El objeto Game que representa el juego actual.
     */
    public SqPiece(Game game) {
        this.game = game;

        // Crea cuatro cuadrados para la pieza cuadrada
        squares = new Square[4];
        squares[0] = new Square(Game.MAX_X / 2 - Game.SQUARE_SIDE, 0, Color.blue, game);
        squares[1] = new Square(Game.MAX_X / 2, 0, Color.blue, game);
        squares[2] = new Square(Game.MAX_X / 2 - Game.SQUARE_SIDE, Game.SQUARE_SIDE, Color.blue, game);
        squares[3] = new Square(Game.MAX_X / 2, Game.SQUARE_SIDE, Color.blue, game);
    }

    /**
     * Método para rotar la pieza cuadrada. Dado que es un cuadrado, no rota,
     * por lo que siempre devuelve true.
     *
     * @return Siempre devuelve true ya que las piezas cuadradas no rotan.
     */
    public boolean rotate() {
        // Las piezas cuadradas no rotan
        return true;
    }

}
