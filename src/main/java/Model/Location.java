package Model;

import View.ObjectView;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

//Egy absztrakt osztály, mely egy játékbeli helyszínt reprezentál
public abstract class Location implements Stepable {

    //Jellemzõ tulajdonságok
    private Point Coordinate;
    protected int NumberOfLayers = 106;
    protected boolean NearSun = false;

    //Tárolt objektumok
    protected ArrayList<Location> Neighbours;
    protected ArrayList<Entity> EntitiesOnSurface;
    protected Space Space;

    //Konstruktorok

    /**
     * Paraméter nélküli konstruktor, beállítja a tulajdonságokat
     */
    public Location(){
        Neighbours = new ArrayList<Location>();
        EntitiesOnSurface = new ArrayList<Entity>();
        Space = new Space();
        Random r = new Random();
        Coordinate = new Point(r.nextInt(200), r.nextInt(200));
    }

    /**
     * Konstruktor
     *
     * @param s beállítja a Model.Space-nek
     */
    public Location(Space s){
        Neighbours = new ArrayList<Location>();
        EntitiesOnSurface = new ArrayList<Entity>();
        Space = s;
        Random r = new Random();
        Coordinate = new Point(r.nextInt(500), r.nextInt(500));
    }
    
    //--------------Függvények----------------------

    /**
     * A paraméterként kapott entitást hozzáadja az EntitiesOnSurface listához
     *
     * @param e hozzáadja az EntitiesOnSurface listához
     */
    public void AddEntity(Entity e){
        EntitiesOnSurface.add(e);

        ArrayList<Material> Items = new ArrayList<Material>();
        for(int i = 0; i < EntitiesOnSurface.size(); i++){
            for(int j = 0; j < EntitiesOnSurface.get(i).getInventory().size(); j++){
                Items.add(EntitiesOnSurface.get(i).getInventory().get(j));
            }
        }
        Space.getGame().CheckWin(Items);
    }

    /**
     * A paraméterként kapott Locationt hozzáadja a Neighbours listához
     *
     * @param l hozzáadja a Neighbours listához
     */
    public void AddNeighbour(Location l){
        Neighbours.add(l);
    }

    /**
     * A Model.Location-t elérõ napvihar hatását modellezõ függvény
     *
     * Minden rajta levõ entitás Sunstorm függvényét meghívja
     */
    public void SunStorm(){
        int n = EntitiesOnSurface.size();
        for(int i = 0; i < n; i++){
            EntitiesOnSurface.get(0).SunStorm();
        }
    }

    /**
     * Eltávolítja a paraméterként kapott entitást az EntitiesOnSurface listából
     *
     * @param e eltávolítja az EntitiesOnSurface listából
     */
    public void RemoveEntity(Entity e){
        EntitiesOnSurface.remove(e);
    }

    /**
     * Eltávolítja a paraméterként kapott Model.Location-t a Neighbours listából
     *
     * @param l eltávolítja a Neighbours listából
     */
    public void RemoveNeighbour(Location l){
        Neighbours.remove(l);
    }

    /**
     * Egy Model.Location-ön történõ elbújási kísérletet reprezentáló függvény
     * Alapvetõen sikertelen, csak aszteroidán más, így ott felüldefiniálódik
     *
     * @param e az elbújni kívánt entitás
     */
    public void Hide(Entity e){
    }


    /**
     * Egy Model.Location-ön történõ fúrási kísérletet reprezentáló függvény
     * Alapvetõen sikertelen, csak aszteroidán más, így ott felüldefiniálódik
     */
    public void ReduceLayers(){
    }

    /**
     * Egy Model.Location felrobbanását reprezentáló függvény
     */
    public void Explode(){
    }

    /**
     * Egy Model.Location-ön történõ bányászási kísérletet reprezentáló függvény
     * Alapvetõen sikertelen, csak aszteroidán más, így ott felüldefiniálódik
     *
     * @return default null
     */
    public Material MineMaterialInside(){
        return null;
    }

    /**
     * Egy Model.Location-ön történõ nyersanyag-lehelyezési kísérletet reprezentáló függvény
     * Alapvetõen sikertelen, csak aszteroidán más, így ott felüldefiniálódik
     *
     * @param m a lehelyezni kívánt nyersanyag
     * @return default false
     */
    public boolean Place(Material m){
        return false;
    }

    /**
     * Egy Model.Location-ön történõ teleportálási kísérletet reprezentáló függvény
     * Alapvetõen sikertelen, csak teleportkapun más, így ott felüldefiniálódik
     *
     * @param e az entitás, amely teleportálni szeretne
     */
    public void Teleport(Entity e){}

    /**
     * Lépés (TeleportGate-nél van szerepe)
     */
    public void Step(){}

    public void Die(){
        for(Entity e : EntitiesOnSurface){
            e.Die();
        }
        for(Location l : Neighbours){
            l.RemoveNeighbour(this);
        }
        Space.RemoveLocation(this);
    }

    //-----------Setterek & Getterek ------------\\

    /**
     * Getter, a NearSun tulajdonság értékét adja vissza
     *
     * @return a NearSun tulajdonság értéke
     */
    public boolean isNearSun() {
        return NearSun;
    }

    /**
     * Setter, beállítja a NearSun tulajdonságot a paraméterként kapott értékre
     *
     * @param nearSun erre állítja a NearSun-t
     */
    public void setNearSun(boolean nearSun) {
        NearSun = nearSun;
    }

    /**
     * Getter, egy Model.Location szomszédait visszaadó függvény
     *
     * @return adott Model.Location szomszédait
     */
    public ArrayList<Location> GetNeighbours(){
        return Neighbours;
    }

    /**
     * Setter
     * Egy Model.Location sziklarétegeinek számát a paraméterként kapott értékre állító függvény
     *
     * @param numberOfLayers kéregréteg számát erre állítja
     */
    public void setNumberOfLayers(int numberOfLayers) {
        NumberOfLayers = numberOfLayers;
    }

    public void setCoordinate(Point c){
        Coordinate = c;
    }

    /**
     * Egy Model.Location belsejében levõ nyersanyagot eltávolító függvény
     * Alapvetõen üres, de a leszármazottak felüldefiniálhatják
     */
    public void RemoveMaterial(){
    }

    /**
     * Setter
     * Egy Model.Location által tárolt Model.Space tulajdonságot a paraméterként kapott értékre állító függvény
     *
     * @param s erre állítja a Model.Space-t
     */
    public void SetSpace(Space s){
        Space = s;
    }

    /**
     * Getter
     *
     * @return a Model.Location koordinátája
     */
    public Point getCoordinate() {
        return Coordinate;
    }

    /**
     * Getter
     *
     * @return a kéregréteg száma
     */
    public int getNumberOfLayers() {
        return NumberOfLayers;
    }


    /**
     * Getter
     *
     * @return adott Model.Location-ön tartozkodó entitások
     */
    public ArrayList<Entity> getEntitiesOnSurface() {
        return EntitiesOnSurface;
    }

    /**
     * Getter
     *
     * @return visszaadja a Model.Space-t
     */
    public Space getSpace() {
        return Space;
    }

    public Material GetMaterial(){return null;}

    public abstract ObjectView getView();

}
