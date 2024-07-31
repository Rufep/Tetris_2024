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
 *SqPiece que estende a clase Piece
 * @author rferpor
 */
public class SqPiece extends Piece {

    /**
     * Construtor da clase, que crea os catro cadrados que forman a peza
     * square
     * @param game
     */
    public SqPiece(Game game) {
        this.game = game;
        
        squares = new Square[4];
        squares[0] = new Square(Game.MAX_X / 2 - Game.SQUARE_SIDE, 0, Color.blue, game);
        squares[1] = new Square(Game.MAX_X / 2, 0, Color.blue, game);
        squares[2] = new Square(Game.MAX_X / 2 - Game.SQUARE_SIDE, Game.SQUARE_SIDE,Color.blue, game);
        squares[3] = new Square(Game.MAX_X / 2, Game.SQUARE_SIDE, Color.blue, game);
    }
  
    /**
     *Metodo que rota a peza , Neste caso a peza cadrada non e necesario rotala
     * @return
     */
    public boolean rotate() {
        //esta peza non rota
        return true;
    }

}
