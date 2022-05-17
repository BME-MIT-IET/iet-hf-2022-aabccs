package Model;

import View.AlienView;
import View.TeleportGateView;

import java.awt.*;
import java.util.Random;

//A játékbeli teleportkaput, mint Model.Location-t reprezentáló osztály
public class TeleportGate extends Location {

    private static int stat_id = 0;

    //Tulajdonságok
    private boolean Active;

    //Tárolt objektumok
    private TeleportGate Pair;

    private TeleportGateView teleportGateView;

    private int id;

    //Konstruktorok

    /**
     * Konstruktor
     */
    public TeleportGate(){
        super();
        id = stat_id;
        stat_id++;
        teleportGateView = new TeleportGateView(this);
    }

    /**
     * Konstruktor
     *
     * @param s beállítja Model.Space-nek
     */
    public TeleportGate(Space s){
        super(s);
        s.RemoveLocation(this);
        id = stat_id;
        stat_id++;
        teleportGateView = new TeleportGateView(this);
        SunStorm();
    }
    
    //--------------Függvények----------------------

    /**
     * A paraméterként kapott teleportkaput beállítja a párjának
     *
     * @param t beállítja párnak
     */
    public void SetPair(TeleportGate t){
        this.Pair = t;

    }

    /**
     * Az inventoryból a teleportkaput pályára lehelyezését reprezentáló függvény
     *
     * @param l felveszi a Model.Location-ök közé, beállítja szomszédait, és aktívvá teszi
     */
    public void Place(Location l){
        setCoordinate(new Point(l.getCoordinate().x + 10, l.getCoordinate().y));
        Space.AddLocation(this);

        l.AddNeighbour(this);
        this.AddNeighbour(l);

        SetActive();
    }

    /**
     * Setter
     * A teleportkapu aktiválása
     */
    public void SetActive(){
        Active = true;
    }

    /**
     * Napfény hatására megkergül
     */
    public void SunStorm(){
        Space.AddStepperLocation(this);
        for(Entity e: EntitiesOnSurface){
            e.SunStorm();
        }
    }

    @Override
    public void RemoveNeighbour(Location l){
        Random rand = new Random();
        if(Neighbours.size() < 1) return;
        int randIx = rand.nextInt(Neighbours.get(0).GetNeighbours().size());
        if(randIx >= 0){
            Space.RemoveLocation(this);
            this.Place(Neighbours.get(0).GetNeighbours().get(randIx));
            Neighbours.remove(l);
        }

    }

    /**
     * A teleportálni vágyó e Entity telportálási kísérlete a teleportkapuról annak párjához
     *
     * Ha nincs párja, vagy nem aktív a pár, akkor nem történik semmi
     * Egyébként megtörténik az Entity áthelyezése a párra
     *
     * @param e az entitás, amely teleportálni szeretne
     */
    public void Teleport(Entity e){
        if(Pair == null){
            return;
        }
        if(Pair.IsActive()) {
            this.RemoveEntity(e);
            Pair.AddEntity(e);
            e.setLocation(Pair);
        }

    }

    /**
     * Megkergüléshez használt Step függvény
     */
    public void Step(){
        Random rand = new Random();
        if(Neighbours.size() < 1) return;
        int randIx = rand.nextInt(Neighbours.get(0).GetNeighbours().size());
        if(randIx >= 0){
            Space.RemoveLocation(this);
            this.Place(Neighbours.get(0).GetNeighbours().get(randIx));
            RemoveNeighbour(Neighbours.get(0));
        }

    }

    //-------------Getterek & Setterek------------\\

    /**
     * Getter
     *
     * @return Active tulajdonság értéke
     */
    public boolean IsActive(){
        return Active;
    }

    public TeleportGate GetPair(){
        return Pair;
    }

    public String toString(){
        return "TeleportGate" + id;
    }

    @Override
    public TeleportGateView getView(){
        return teleportGateView;
    }
}
