/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package music;

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Esta clase gestiona la reproducción de clips de sonido en el juego Tetris.
 * Proporciona métodos para reproducir la música de fondo, sonidos de línea
 * completada y sonidos de Tetris.
 *
 *
 */
public class PlayMusic {

    private static Clip sonido = null;

    /**
     * Metodo para reproducir un clip de son
     */
    public static void reproducirMusica() {
        try {

            if (sonido == null) {
                // Se obtiene un Clip de sonido
                sonido = AudioSystem.getClip();

                // Se carga con un fichero wav
                sonido.open(AudioSystem.getAudioInputStream(new File("TetrisSong.wav")));

            }

            // Comienza la reproducción
            sonido.start();
            sonido.loop(100);

        } catch (Exception e) {
            System.out.println("ERROR O CARGAR O CLIP DE AUDIO" + e);
        }
    }

    /**
     * Metodo que reproduce un sondico cuando se realizan 4 o mas lineas TETRIS
     */
    public static void playTetrisSound() {
        try {
            Clip tetrisSoundClip = AudioSystem.getClip();
            tetrisSoundClip.open(AudioSystem.getAudioInputStream(new File("SoundTetrisLines.wav")));
            tetrisSoundClip.start();
        } catch (Exception e) {
            System.out.println("ERROR PLAYING TETRIS SOUND: " + e);
        }
    }

    /**
     * Metodo que reproduce un sonido al realizar una linea
     */
    public static void playLineCompletedSound() {
        try {
            Clip lineCompletedSoundClip = AudioSystem.getClip();
            lineCompletedSoundClip.open(AudioSystem.getAudioInputStream(new File("soundLine.wav")));
            lineCompletedSoundClip.start();
        } catch (Exception e) {
            System.out.println("ERROR PLAYING LINE COMPLETED SOUND: " + e);
        }
    }

    /**
     * Metodo para parar o clip de son
     */
    public static void pararMusica() {
        sonido.stop();
    }

}
