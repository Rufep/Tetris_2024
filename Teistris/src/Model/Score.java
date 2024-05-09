/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;

/**
 * Clase que representa una puntuación en el juego.
 *
 * Cada instancia de esta clase contiene información sobre la puntuación
 * obtenida, la fecha en que se registró y el nombre del jugador que obtuvo la
 * puntuación.
 *
 *
 */
public class Score {

    private int points;
    private Date date;
    private String name;

    /**
     * Obtiene el nombre del jugador asociado a la puntuación.
     *
     * @return El nombre del jugador.
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre del jugador asociado a la puntuación.
     *
     * @param name El nombre del jugador.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene la cantidad de puntos de la puntuación.
     *
     * @return La cantidad de puntos.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Establece la cantidad de puntos de la puntuación.
     *
     * @param points La cantidad de puntos.
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Obtiene la fecha en que se registró la puntuación.
     *
     * @return La fecha de la puntuación.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Establece la fecha en que se registró la puntuación.
     *
     * @param date La fecha de la puntuación.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Constructor de la clase Score.
     *
     * @param points La cantidad de puntos.
     * @param date La fecha de la puntuación.
     * @param name El nombre del jugador.
     */
    public Score(int points, Date date, String name) {
        this.points = points;
        this.date = date;
        this.name = name;
    }

}
