package Model;

import View.AlienView;

import java.util.ArrayList;
import java.util.Random;

public class Alien extends Entity{

    private AlienView alienView;

    /**
     * Konstruktor
     *
     * @param l beállítja Model.Location-nek
     */
    public Alien(Location l){
        super(l);
        alienView = new AlienView(this);

    }



    //--------------Függvények----------------------

    /**
     * Egyik location-ről (asteroid v. telelporgate) egy másikra való mozgásra használt függvény
     *
     * @param l azon location, amelyre lépni akar
     */
    @Override
    public void Move(Location l){
        if(location.GetNeighbours().contains(l)){
            location.RemoveEntity(this);
            l.AddEntity(this);
            setLocation(l);
        }
        Teleport();
    }

    /**
     * Amely, location-ön tartózkodik, azon megpróbál bányászni
     * azon location-re a függvény meghívja a MineMaterialInside-ot
     */
    @Override
    public void Mine(){
        location.MineMaterialInside();
    }

    /**
     * Teleportálást megvalósító függvény
     */
    @Override
    public void Teleport(){
        location.Teleport(this);
    }

    /**
     * Ha robbanás történik, akkor ez hívodik meg az Alien-en
     *
     * Ha az adott Model.Location-nek, amin tartózkodik nincs szomszédja, akkor meghal
     * Ellenkező esetben egy szomszédra kerül át
     */
    @Override
    public void Explode() {
        if(location.GetNeighbours().size() == 0){
            Die();
            return;
        }
        Random rand = new Random();
        int randIx = rand.nextInt(location.GetNeighbours().size());
        Move(location.GetNeighbours().get(randIx));
    }

    /**
     * A Step függvény az alien egy körének a lebonyolításáért felelős
     *
     * Ha az adott Model.Location-nek, amin tartózkodik, a kéregréteg száma nagyobb, mint nulla akkor az alien nem tud bányászni
     * Ekkor a függvény ellenőrzi, hogy vannak-e a Model.Location-nek szomszédai
     * ha vannak, akkor választ egyet random és arra átmegy
     * ha nincs, akkor meghal
     * Ha pedig az adott Model.Location, amin tartozkódik, már teljesen meg van fúrva, akkor bányászik
     */
    @Override
    public void Step() {
        if(location.getNumberOfLayers() > 0){
            ArrayList<Location> neighbours = location.GetNeighbours();
            if(!neighbours.isEmpty()){
                Random rand = new Random();
                int randIx = rand.nextInt(location.GetNeighbours().size());
                Move(location.GetNeighbours().get(randIx));
            }else{
                Die();
            }
        }
        else{
            Mine();
        }
    }

    /**
     * Ha az Alien valamilyen okból meghal, akkor ez hívódik meg rajt
     * A függvény eltávolítja az Alient az adott Model.Location-ről, amin tartózkodott
     * illetve eltávolítja a játékból
     */
    @Override
    public void Die() {
        location.RemoveEntity(this);
        Game.RemoveAlien(this);
    }

    /**
     * Az Alien nem tud fúrni, ezért felüldefiniálja az entity drill függvényét
     * üres metódussal
     */
    @Override
    public void Drill(){}

    public AlienView getAlienView(){
        return alienView;
    }


}
