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
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;

/**
 * Clase que implementa un cadrado del juego Tetris.
 *
 * Representa visualmente un cuadrado en el panel de juego del Tetris. Cada
 * cuadrado tiene coordenadas, color de relleno y una etiqueta que lo muestra
 * gráficamente.
 *
 *
 */
public class Square {

    /**
     * Coordenadas del cuadrado en el panel de juego.
     */
    private int x, y;
    /**
     * Etiqueta que muestra el cuadrado en el panel.
     */
    private JLabel lblSquare;
    /**
     * Color de relleno del cuadrado.
     */
    private Color fillColor;

    /**
     * Obtiene la coordenada x del cuadrado.
     *
     * @return Coordenada x del cuadrado.
     */
    public int getX() {
        return x;
    }

    /**
     * Establece la coordenada x del cuadrado.
     *
     * @param x Coordenada x del cuadrado.
     */
    public void setX(int x) {
        this.x = x;
        // Establecemos a nova localización da etiqueta e repintámola
        lblSquare.setBounds(x, y, Game.SQUARE_SIDE, Game.SQUARE_SIDE);
        lblSquare.repaint();
    }

   /**
     * Obtiene la coordenada y del cuadrado.
     * 
     * @return Coordenada y del cuadrado.
     */
    public int getY() {
        return y;
    }

     /**
     * Establece la coordenada y del cuadrado.
     * 
     * @param y Coordenada y del cuadrado.
     */
    public void setY(int y) {
        this.y = y;
        // Establecemos a nova localización da etiqueta e repintámola
        lblSquare.setBounds(x, y, Game.SQUARE_SIDE, Game.SQUARE_SIDE);
        lblSquare.repaint();
    }

  /**
     * Obtiene la representación de las coordenadas x, y del cuadrado como una cadena de texto.
     * 
     * @return La cadena que representa las coordenadas x, y del cuadrado.
     */
    public String getCoordinates() {
        return String.valueOf(x) + "," + String.valueOf(y);
    }

    /**
     * Obtiene la etiqueta que representa gráficamente el cuadrado.
     * 
     * @return La etiqueta del cuadrado.
     */
    public JLabel getLblSquare() {
        return lblSquare;
    }

  /**
     * Obtiene el color de relleno del cuadrado.
     * 
     * @return El color de relleno del cuadrado.
     */
    public Color getFillColor() {
        return fillColor;
    }

    /**
     * Establece el color de relleno del cuadrado.
     * 
     * @param fillColor El color de relleno para establecer al cuadrado.
     */
    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
        // Establecemos a cor de fondo para a etiqueta e repintámola
        lblSquare.setBackground(fillColor);
        lblSquare.repaint();
    }

  /**
     * Constructor de la clase que crea un cuadrado con sus coordenadas, color y referencia al juego.
     * 
     * @param x Coordenada x.
     * @param y Coordenada y.
     * @param fillColor Color de relleno del cuadrado.
     * @param game Referencia al objeto de juego.
     */
    public Square(int x, int y, Color fillColor, Game game) {
        this.x = x;
        this.y = y;
        this.fillColor = fillColor;

        // Creamos a etiqueta e establecemos a cor de fondo, coordenadas, 
        // e atributos para que se vexa no panel do xogo
        lblSquare = new JLabel();
        lblSquare.setBackground(fillColor);
        lblSquare.setBounds(x, y, Game.SQUARE_SIDE, Game.SQUARE_SIDE);
        lblSquare.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        lblSquare.setVisible(true);
        lblSquare.setOpaque(true);

        // Chamamos á ventá principal do xogo para pintar o cadrado no panel
        game.getMainWindow().drawSquare(this.lblSquare);
    }
}
