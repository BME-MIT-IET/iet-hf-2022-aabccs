package View;


import Model.Robot;

import javax.swing.*;
import java.awt.*;

/**
 * Robot kirajzolásáért felelős
 */
public class  RobotView extends ObjectView{

    Robot robot;
    static ImageIcon img;

    public RobotView(Robot r){
        robot = r;
        size = 50;
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
        int locSize =  robot.getLocation().getView().getSize();
        double angle = Math.toRadians(-137.5) * posOnLoc();
        Point center = new Point((int)(cp.x + Math.sin(angle) * locSize / 2),(int) (cp.y - Math.cos(angle) * locSize / 2));
        g2d.rotate(angle, center.x, center.y);
        g2d.drawImage(img.getImage(), center.x - size / 2, center.y - size / 2, size, size, null);
        g2d.dispose();
    }

    /**
     * Getter koordinátához
     * @return a robot koordinátája
     */
    @Override
    public Point getCoordinate() {
        return robot.getLocation().getCoordinate();
    }

    /**
     * Segédfüggvény a Draw-hoz
     * @return visszaadja a robot indexét
     */
    public int posOnLoc(){
        return robot.getLocation().getEntitiesOnSurface().indexOf(robot);
    }

    /**
     * Photoshopban készített kép betöltése
     */
    public static void LoadTexture(){
        img = new ImageIcon("res/robot.png");
    }
}
