package Model;

import java.util.ArrayList;

public abstract class Entity implements Stepable{

    //Tulajdonságok
    protected ArrayList<Material> Materials;
    protected Location location;
    protected Game Game;

    //Konstruktorok

    /**
     * Konstruktor
     */
    public Entity(){
        Materials = new ArrayList<Material>();
    }

    /**
     * Konstruktor
     *
     * A paraméterként kapott Game-t eltárolja
     *
     * @param g beállítja az eltárolt Game-nek
     */
    public Entity(Game g){
        Game = g;
        Materials = new ArrayList<Material>();
    }

    /**
     * Konstruktor
     *
     * A paraméterként kapott Model.Location-t eltárolja
     *
     * @param l beállítja az Entitás Model.Location-jének
     */
    public Entity(Location l){
        setLocation(l);
        Materials = new ArrayList<Material>();
        l.AddEntity(this);
    }

    /**
     * Konstruktor
     *
     * A paraméterként kapott Model.Location-t és Game-t eltárolja
     *
     * @param l beállítja az Entitás Model.Location-jének
     * @param g beállítja az eltárolt Game-nek
     */
    public Entity(Location l, Game g){
        String attrib = l.toString()+", " + g.toString();
        setLocation(l);
        Game = g;
        Materials = new ArrayList<Material>();
        l.AddEntity(this);
    }

    //-------------Függvények---------------

    /**
     * Az entitás l Model.Location-re történõ átmozgását reprezentáló függvény
     *
     * Ha a szomszédok között van, sikeres, egyébként sikertelen
     *
     * @param l erre a location-re kíván átmozogni az entitás
     */
    public void Move(Location l){
        if(location.GetNeighbours().contains(l)){
            location.RemoveEntity(this);
            l.AddEntity(this);
            setLocation(l);
        }
    }

    /**
     * Az entitás kibújását-elbújását reprezentáló függvény
     */
    public void Hide(){
        location.Hide(this);
    }

    /**
     * Az entitás furását reprezentáló függvény
     */
    public void Drill(){
        location.ReduceLayers();
    }

    /**
     * Az entitás bányászászását reprezentáló függvény
     */
    public void Mine(){}

    /**
     * Az entitás halálát reprezentáló absztrakt függvény
     */
    public abstract void Die();

    /**
     * Az entitás lépését reprezentáló függvény
     */
    @Override
    public abstract void Step();

    /**
     * A napvihar hatását az entitáson reprezentáló függvény
     */
    public void SunStorm(){
        Die();
    }

    /**
     * A robbanás hatását az entitáson reprezentáló függvény
     */
    public void Explode(){}

    /**
     * Az entitás teleportálását reprezentáló függvény
     */
    public void Teleport(){}

    //--------------------Setterek, getterek----------------------

    /**
     * A paraméterként kapott Model.Location-t eltárolja (Setter)
     *
     * @param location beállítja az Entitás location-jének
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    public void setGame(Game game){
        Game = game;
    }

    /**
     * Getter
     *
     * @return az eltárolt Model.Location-t adja vissza
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Getter
     *
     * @return az entitás Inventory-ját adja vissza
     */
    public ArrayList<Material> getInventory(){
        return Materials;
    }

    public void AddMaterial(Material m){}

}
