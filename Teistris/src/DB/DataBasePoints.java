/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;

import Model.Score;
import View.MainWindow;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;


/**
 * Esta clase gestiona la conexión y operaciones con la base de datos de puntos.
 * 
 *
 * @author rferpor
 */
public class DataBasePoints extends MainWindow {

    private static Connection c;

    /**
     * Establece la conexión con la base de datos.
     */
    public static void getConnect() {
        try {
            // Creamos la conexión con la base de datos
            c = DriverManager.getConnection("jdbc:sqlite:E:\\netbeans proyectos\\Teistris\\\\SqlitePointsBd\\Points.db");
            System.out.println("Conexión realizada");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No se pudo establecer la conexión con el servidor de bases de datos.");
        }
    }

    /**
     * Crea la tabla de puntos si no existe.
     */
    public static void createPointsTable() {
        try {
            // Creamos la tabla si no existe
            Statement stmt = c.createStatement();
            String sqlCreateTable = "CREATE TABLE IF NOT EXISTS Points  (id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + " puntuacion String, fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP, name varchar(100));";
            stmt.execute(sqlCreateTable);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No se pudo establecer la conexión con el servidor de bases de datos.");
        }
    }

    /**
     * Guarda una puntuación en la base de datos.
     *
     * @param score La puntuación a guardar.
     */
    public static void savePoints(Score score) {
        try {
            // Insertamos una puntuación
            PreparedStatement pstmt = c.prepareStatement("INSERT INTO Points (puntuacion,fecha,name) VALUES (?,?,?)");
            pstmt.setInt(1, score.getPoints());
            pstmt.setDate(2, new java.sql.Date(new Date().getTime()));
            pstmt.setString(3, score.getName());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No se pudo establecer la conexión con el servidor de bases de datos.");
        }
    }

    /**
     * Recupera las puntuaciones almacenadas en la base de datos.
     *
     * @return La lista de puntuaciones recuperadas.
     */
    public static ArrayList<Score> getPoints() {
        ArrayList<Score> scores = new ArrayList<>();
        // Recuperamos las puntuaciones ordenadas por puntuación
        try {
            Statement stmt = c.createStatement();
            String sql = "SELECT * FROM Points ORDER BY puntuacion DESC";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int puntos = rs.getInt("puntuacion");
                Date fecha = rs.getDate("fecha");
                String namePlayerRecord = rs.getString("name");
                scores.add(new Score(puntos, fecha, namePlayerRecord));
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No se pudo establecer la conexión con el servidor de bases de datos.");
        }
        return scores;
    }

    /**
     * Elimina todas las puntuaciones almacenadas en la base de datos.
     */
    public static void deleteAllPoints() {
        try {
            // Sentencia sql para borrar todos los datos de la tabla Points
            Statement stmt = c.createStatement();
            String sql = "DELETE FROM Points";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No se pudieron borrar los datos.");
        }
    }
}
