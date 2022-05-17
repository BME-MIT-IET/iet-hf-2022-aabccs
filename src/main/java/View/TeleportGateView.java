package View;

import Model.*;

import javax.swing.*;
import java.awt.*;

/**
 * TeleportGate megjelenítéséért felelős
 */
public class TeleportGateView extends ObjectView{

    TeleportGate teleportGate;
    static ImageIcon img_ac;
    static ImageIcon img_inac;

    /**
     * Konstruktor
     * @param tg az a teleportgate, amit meg kell jeleníteni
     */
    public TeleportGateView(TeleportGate tg){
        teleportGate = tg;
    }

    /**
     * A konkrét rajzolásért felel
     * @param cp Point
     * @param g Graphics2D
     */
    @Override
    public void Draw(Point cp, Graphics2D g) {
        Graphics2D g2d = (Graphics2D) g.create();
        ImageIcon img = teleportGate.GetPair().IsActive() ? img_ac : img_inac;
        g2d.drawImage(img.getImage(), cp.x - size / 2, cp.y - size / 2, size, size, null);
        g2d.setFont(new Font("Serif", Font.BOLD, 20));
        g2d.setColor(Color.WHITE);
        g2d.drawString(teleportGate.toString(), cp.x - 12, cp.y - 15);
        g2d.dispose();

    }

    /**
     * Getter koordináta lekéréshez
     * @return teleportgate koordinátája
     */
    @Override
    public Point getCoordinate() {
        return teleportGate.getCoordinate();
    }

    /**
     * Photoshopban készített képek betöltése
     */
    public static void LoadTexture(){
        img_ac = new ImageIcon("res/teleportactive.png");
        img_inac = new ImageIcon("res/teleportinactive.png");
    }
}
