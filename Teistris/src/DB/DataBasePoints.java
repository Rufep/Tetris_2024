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
 * jdbc:sqlite:/home/rferpor/NetBeansProjects/Teistris/SqlitePointsBd/Points.db
 * 
 *
 * @author rferpor
 */
public class DataBasePoints extends MainWindow {

    private static Connection c;

    /**
     * conectamonos a base de datos
     */
    public static void getConnect() {
        try {

            // Creamos la conexión con la base de datos
            c = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\rb\\Documents\\NetBeansProjects\\Teistris\\SqlitePointsBd\\Points.db");
            System.out.println("conexion realizada");
        } catch (Exception e) {

            e.printStackTrace();
            System.out.println("A conexión co servidor de bases de datos non se puido establecer");

        }
    }

    /**
     * Crea a taboa de puntos se non existe
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
            System.out.println("A conexión co servidor de bases de datos non se puido establecer");
        }

    }

    /**
     * jdbc:sqlite:/home/rferpor/NetBeansProjects/Teistris/SqlitePointsBd/Points.db
     *  garda os puntos
     * @param score
     * @param lblnumberOfPoints
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
            System.out.println("A conexión co servidor de bases de datos non se puido establecer");
        }
    }

    /**
     * recupera os puntos e os carga no combo box PointsMax
     *
     * @param PointsMax
     * @return 
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
            System.out.println("A conexión co servidor de bases de datos non se puido establecer");
        }
        return scores;
    }

    /**
     *metido que borra la tabla de puntos
     */
    public static void deleteAllPoints() {
    try {
        // Sentencia sql para borrar todos os datos da BD Points
        Statement stmt = c.createStatement();
        String sql = "DELETE FROM Points";
        stmt.executeUpdate(sql);
        stmt.close();
        c.close();
        // Actualizamos os datos do combo box PointsMax
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("No se pudieron borrar los datos.");
    }
}

    

}
