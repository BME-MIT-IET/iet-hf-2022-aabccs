package View;

import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Map;

/**
 * Aszteroida megjelenítéséért felelős osztály
 */
public class AsteroidView extends ObjectView{

    Asteroid asteroid;
    static ArrayList<ImageIcon> imagesList;

    /**
     * Konstruktor
     * @param a az aszteroida, amit meg szeretnénk jeleníteni
     */
    public AsteroidView(Asteroid a){
        asteroid = a;
    }

    /**
     * A konkrét rajzolásért felel
     *
     * Ellenőrzi, hogy milyen nyersanyag van a belsejében, illetve, hogy napközeli vagy sem
     * és ennek megfelelően tölti be a képet
     *
     * @param cp Point
     * @param g Graphics2D
     */
    @Override
    public void Draw(Point cp, Graphics2D g) {
        Graphics2D g2d = (Graphics2D) g.create();
        int idx = 0;

        if(asteroid.getNumberOfLayers() > 0){
            idx = 10;
        }
        else{
            if(asteroid.GetMaterial() == null){
                idx = 0;
            }
            else if(asteroid.GetMaterial().MaterialType(new Coal())){
                idx = 2;
            }
            else if(asteroid.GetMaterial().MaterialType(new Uran())){
                idx = 4;
            }
            else if(asteroid.GetMaterial().MaterialType(new Water())){
                idx = 6;
            }
            else if(asteroid.GetMaterial().MaterialType(new Iron())){
                idx = 8;
            }
        }
        if(asteroid.isNearSun()){
            idx++;
        }
        g2d.drawImage(imagesList.get(idx).getImage(), cp.x - size / 2, cp.y - size / 2, size, size, null);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.setColor(Color.WHITE);
        g2d.drawString(asteroid.toString(), cp.x - 12, cp.y - 15);
        if(asteroid.getNumberOfLayers() > 0){
            g2d.setColor(Color.yellow);
            g2d.drawString(Integer.toString(asteroid.getNumberOfLayers()), cp.x - 8, cp.y + 18);
        }
        g2d.dispose();

    }
    @Override
    public Point getCoordinate() {
        return asteroid.getCoordinate();
    }

    /**
     * Photoshopban készített képek betöltése
     */
    public static void LoadTexture(){
        imagesList = new ArrayList<ImageIcon>();
        imagesList.add(new ImageIcon("res/asteroidures.png"));
        imagesList.add(new ImageIcon("res/asteroidnapkozeliures.png"));

        imagesList.add(new ImageIcon("res/asteroidsen1.png"));
        imagesList.add(new ImageIcon("res/asteroidnapkozeliszen.png"));

        imagesList.add(new ImageIcon("res/asteroiduran.png"));
        imagesList.add(new ImageIcon("res/asteroidnapkozeliuran.png"));

        imagesList.add(new ImageIcon("res/asteroidviz.png"));
        imagesList.add(new ImageIcon("res/asteroidnapkozeliviz.png"));

        imagesList.add(new ImageIcon("res/asteroidvas.png"));
        imagesList.add(new ImageIcon("res/asteroidnapkozelivas.png"));

        imagesList.add(new ImageIcon("res/asteroid4.png"));
        imagesList.add(new ImageIcon("res/asteroidnapkozeli.png"));
    }
}
