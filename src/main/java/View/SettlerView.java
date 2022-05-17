package View;

import Model.Settler;

import javax.swing.*;
import java.awt.*;

/**
 * Settler kirajzolásáért felelős
 */
public class SettlerView extends ObjectView{

    Settler settler;
    static ImageIcon img;
    static final int defsize = 50;

    /**
     * Konstruktor
     * @param s settler
     */
    public SettlerView(Settler s){
        settler = s;
    }

    /**
     * A konkrét rajzolásért felel
     *
     * hogy minden entitás látszódjon ezért forgatva vannak a location középpontja körül, ehhez kell a posOnLoc index szorzónak
     *
     * @param cp Point
     * @param g Graphics2D
     */
    @Override
    public void Draw(Point cp, Graphics2D g) {
        Graphics2D g2d = (Graphics2D) g.create();
        int locSize =  settler.getLocation().getView().getSize();
        Point center;
        int fontsize;
        int size;
        if(settler.getLocation().getEntitiesOnSurface().contains(settler)) {
            size = defsize;
            double angle = Math.toRadians(-137.5) * posOnLoc();
            center = new Point((int) (cp.x + Math.sin(angle) * locSize / 2), (int) (cp.y - Math.cos(angle) * locSize / 2));
            g2d.rotate(angle, center.x, center.y);
            fontsize = 15;
        }else{
            center = new Point(cp.x, cp.y);
            size = 25;
            fontsize = 6;
        }
        g2d.drawImage(img.getImage(), center.x - size / 2, center.y - size / 2, size, size, null);
        g2d.setFont(new Font("Arial", Font.BOLD, fontsize));
        g2d.setColor(new Color(168, 114, 208));
        g2d.drawString(settler.toString(), center.x - size / 4 + 2, center.y - size / 2);
        g2d.dispose();
    }

    /**
     * Getter koordináta lekéréshez
     * @return settler koordinátája
     */
    @Override
    public Point getCoordinate() {
        return settler.getLocation().getCoordinate();
    }

    /**
     * Segédfüggvény a Draw-hoz
     * @return visszaadja a settler indexét
     */
    public int posOnLoc(){
        return settler.getLocation().getEntitiesOnSurface().indexOf(settler);
    }

    /**
     * Photoshopban készített kép betöltése
     */
    public static void LoadTexture(){
        img = new ImageIcon("res/settler.png");
    }
}
