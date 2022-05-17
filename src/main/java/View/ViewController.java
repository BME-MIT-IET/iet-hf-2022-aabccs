package View;
import Control.Control;
import Model.Asteroid;
import Model.Game;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class ViewController {

    Game game;
    Control control;
    private ArrayList<ObjectView> objects;
    Point cp;
    protected Graphics2D g;
    double scale = 1;
    Point translate = new Point(0,0);
    BufferStrategy bs;

    /**
     * Konstruktor
     */
    public ViewController(){
        objects = new ArrayList<ObjectView>();
        AlienView.LoadTexture();
        AsteroidView.LoadTexture();
        RobotView.LoadTexture();
        SettlerView.LoadTexture();
        TeleportGateView.LoadTexture();
    }

    /**
     * Az egész pálya megjelenítése
     */
    public void DrawAll(){
        bs = control.getCanvas().getBufferStrategy();
        if(bs == null) {
            control.getCanvas().createBufferStrategy(3);
            return;
        }
        g = (Graphics2D) bs.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, control.getCanvas().getWidth(), control.getCanvas().getHeight());
        g.scale(scale, scale);
        g.translate(translate.x, translate.y);
        if (game.getCurrentSettler() != null) {
            cp = ScaleToCanvas(game.getCurrentSettler().getLocation().getCoordinate());
            Point trans = new Point(control.getCanvas().getWidth() / 2 - cp.x, control.getCanvas().getHeight() / 2 - cp.y);
            for (ObjectView ov : objects) {
                if (ov == null) continue;
                Point locCoord = new Point(ScaleToCanvas(ov.getCoordinate()).x + trans.x, ScaleToCanvas(ov.getCoordinate()).y + trans.y);
                ov.Draw(locCoord, g);
            }
        }
        bs.show();
        bs.show();
        g.dispose();
    }

    /**
     * Játék végetérése esetén üzenetek megjelenítése
     */
    public void DrawEndScreen(){
        bs = control.getCanvas().getBufferStrategy();
        if(bs == null) {
            control.getCanvas().createBufferStrategy(3);
            return;
        }
        g = (Graphics2D) bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, control.getCanvas().getWidth(), control.getCanvas().getHeight());
        g.setColor(game.isWin() ? Color.GREEN : Color.RED);
        g.drawString(game.isWin() ? "Settlers won the game!" : "All settlers dead",  control.getCanvas().getWidth() / 2, control.getCanvas().getHeight() / 2);
        bs.show();
        bs.show();
        g.dispose();
    }


    /**
     * Setter
     * @param c beállítani kívánt Control
     */
    public void setControl(Control c){
        control = c;
    }

    /**
     * Setter
     * @param g beállítani kívánt Game
     */
    public void setGame(Game g){
        game = g;
    }

    /**
     * Add függvény
     * @param ov hozzáadni kívánt ObjectView
     */
    public void addObject(ObjectView ov){
        objects.add(ov);
    }

    /**
     * Remove függvény
     * @param ov eltávolítani kívánt ObjectView
     */
    public void removeObject(ObjectView ov){
        objects.remove(ov);
    }

    public Point ScaleToCanvas(Point wCoordinates){
        double scaleX = control.getCanvas().getWidth() / (double) 200;
        double scaleY = control.getCanvas().getHeight() / (double) 200;
        return new Point((int) (wCoordinates.x * scaleX), (int)(wCoordinates.y * scaleY));

    }

    public void Scale(double sc){
        scale += sc;
        if(scale <= 0){
            scale = 0.1;
        }
    }

    /**
     * Transzformálás
     * @param tr point amit ehhez felhasznál
     */
    public void Translate(Point tr){
        translate = new Point(translate.x + (int)(tr.x * 1/scale), translate.y + (int)(tr.y*1/scale));
    }

    /**
     * Transzformáció reset
     */
    public void ResetTransformations(){
        scale = 1;
        translate = new Point(0,0);
    }

}
