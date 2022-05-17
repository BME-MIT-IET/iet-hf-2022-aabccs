package Model;

import View.AlienView;
import View.AsteroidView;

//Egy aszteroidát reprezentáló osztály
public class Asteroid extends Location{

    private static int stat_id = 0;

    //Birtoklások
    private Entity EntityInside;
    private Material MaterialInside;

    private int id;

    private AsteroidView asteroidView;

    //Konstruktorok

    /**
     * Konstruktor
     */
    public Asteroid(){
        super();
        id = stat_id;
        stat_id++;
        asteroidView = new AsteroidView(this);
    }

    /**
     * Konstruktor
     *
     * A paraméterként kapott s Model.Space-t beállítja az eltárolt Model.Space-nek (Model.Location)
     *
     * @param s erre állítja be a Model.Space-t
     */
    public Asteroid(Space s){
        super(s);
        id = stat_id;
        stat_id++;
        asteroidView = new AsteroidView(this);
        s.AddLocation(this);

    }

    //--------------Függvények----------------------

    /**
     * Az aszteroida robbanását modellezõ függvény
     *
     * Minden rajta tartõzkodó entitás Explode függvényét meghívja
     * Robbanás esetén az aszteroida meghal, ezért minden szomszédjának szomszédlistájából
     * eltávolítja magát
     * Végül a Model.Space Model.Location listájából is eltávolítja magát
     */
    @Override
    public void Explode() {
        int n = EntitiesOnSurface.size();
        for (int i = 0; i < n; i++){
            EntitiesOnSurface.get(0).Explode();
        }

        for(int i = 0; i < Neighbours.size(); i++){
            Neighbours.get(i).RemoveNeighbour(this);
        }
        Space.RemoveLocation(this);
    }

    /**
     * Az aszteroida fúrását modellezõ függvény
     *
     * Ha már nulla a kéregréteg száma, akkor nem történik semmi
     * Egyébként pedig a rétegszám csökken eggyel
     * Ha ezzel a fúrással lett a rétegszám nulla,
     * akkor a nyersanyag fedetlenné válik,
     * meghívódik a MaterialInside Exposed függvénye
     */
    public void ReduceLayers() {
        if(NumberOfLayers <= 0){
            return;
        }
        NumberOfLayers--;
        if(NumberOfLayers <= 0 && GetMaterial() != null){
            MaterialInside.Exposed(this);
        }
    }

    /**
     * Egy entitás aszteroidában kibújását-elbújását modellezõ függvény
     *
     * Ha az aktuális entitás az aszteroida magjában van, akkor kibújik
     * Ha a kéregréteg száma nagyobb, mint nulla, akkor nem lehet elbújni
     * Ha van már nyersanyag, vagy entitás a magban, nem lehet elbújni
     * Ha nincs akadályozó tényezõ, el lehet bújni
     *
     * @param e A kibújni-elõbújni kívánó entitás
     */
    public void Hide(Entity e) {
        if(EntityInside == e){
            AddEntity(e);
            setEntityInside(null);
            return;
        }
        if (NumberOfLayers > 0){
            return;
        }
        if (EntityInside != null || MaterialInside != null){
            return;
        }
        setEntityInside(e);
        RemoveEntity(e);
    }

    /**
     * Az aszteroida magjába nyersanyag belehelyezését modellezõ függvény
     *
     * Ha még van sziklaréteg, nem lehet beletenni a nyersanyagot, visszatérés false
     * Ha van már nyersanyag, vagy entitás a magban, nem lehet beletenni a nyersanyagot, visszatérés false
     * Ha nincs akadályozó tényezõ, bele lehet tenni a nyersanyagot
     * A behelyezés után meghívódik a material exposed függvénye, visszatérés true
     *
     * @param m Az elhelyezmi kívánt Model.Material
     * @return A mûvelet sikerességét mutatja
     */
    public boolean Place(Material m) {
        if(NumberOfLayers > 0){
            return false;
        }
        if(EntityInside != null || MaterialInside != null){
            return false;
        }
        SetMaterialInside(m);
        m.Exposed(this);
        return true;
    }

    /**
     * Az aszteroida magjában található nyersanyag kibányászását modellezõ függvény
     *
     * Ha még van sziklaréteg, nem lehet bányászni, visszatérés null értékkel (sikertelenség)
     *
     * Ha nincs akadályozó tényezõ, lehet bányászni
     * Kivesszük a nyersanyagot, és visszatérünk vele
     * (ha üres volt, akkor null értékkel)
     *
     * @return a magban található nyersanyag
     */
    public Material MineMaterialInside() {
        if(NumberOfLayers > 0){
            return null;
        }
        Material tmp = MaterialInside;
        this.RemoveMaterial();
        return tmp;
    }

    /**
     * A materialInside-ot frissíti null értékre
     */
    @Override
    public void RemoveMaterial() {
        MaterialInside = null;
    }


    //Setterek

    /**
     * Az entityInside-ot frissíti a paraméterként kapott Entitásra (Setter)
     *
     * @param entityInside erre állítja be az EntityInside-ot
     */
    public void setEntityInside(Entity entityInside) {
        EntityInside = entityInside;
    }

    /**
     * A materialInside-ot frissíti a paraméterként kapott Materialra (Setter)
     *
     * @param m erre frissíti a MaterialInside-ot
     */
    public void SetMaterialInside(Material m) {
        MaterialInside = m;
    }

    @Override
    public void Die(){
        super.Die();
        EntityInside.Die();
    }

    //Getterek

    public Entity GetEntityInside(){
        return EntityInside;
    }

    public Material GetMaterial(){
        return MaterialInside;
    }

    public String toString(){
        return "A" + id;
    }

    @Override
    public AsteroidView getView(){
        return asteroidView;
    }


}
