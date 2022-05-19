package Model;

import View.AsteroidView;

//Egy aszteroid�t reprezent�l� oszt�ly
public class Asteroid extends Location{

    private static int stat_id = 0;

    //Birtokl�sok
    private Entity entityInside;
    private Material materialInside;

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
     * A param�terk�nt kapott s Model.Space-t be�ll�tja az elt�rolt Model.Space-nek (Model.Location)
     *
     * @param s erre �ll�tja be a Model.Space-t
     */
    public Asteroid(Space s){
        super(s);
        id = stat_id;
        stat_id++;
        asteroidView = new AsteroidView(this);
        s.AddLocation(this);

    }

    //--------------F�ggv�nyek----------------------

    /**
     * Az aszteroida robban�s�t modellez� f�ggv�ny
     *
     * Minden rajta tart�zkod� entit�s Explode f�ggv�ny�t megh�vja
     * Robban�s eset�n az aszteroida meghal, ez�rt minden szomsz�dj�nak szomsz�dlist�j�b�l
     * elt�vol�tja mag�t
     * V�g�l a Model.Space Model.Location list�j�b�l is elt�vol�tja mag�t
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
     * Az aszteroida f�r�s�t modellez� f�ggv�ny
     *
     * Ha m�r nulla a k�regr�teg sz�ma, akkor nem t�rt�nik semmi
     * Egy�bk�nt pedig a r�tegsz�m cs�kken eggyel
     * Ha ezzel a f�r�ssal lett a r�tegsz�m nulla,
     * akkor a nyersanyag fedetlenn� v�lik,
     * megh�v�dik a MaterialInside Exposed f�ggv�nye
     */
    @Override
    public void ReduceLayers() {
        if(NumberOfLayers <= 0){
            return;
        }
        NumberOfLayers--;
        if(NumberOfLayers <= 0 && GetMaterial() != null){
            materialInside.Exposed(this);
        }
    }

    /**
     * Egy entit�s aszteroid�ban kib�j�s�t-elb�j�s�t modellez� f�ggv�ny
     *
     * Ha az aktu�lis entit�s az aszteroida magj�ban van, akkor kib�jik
     * Ha a k�regr�teg sz�ma nagyobb, mint nulla, akkor nem lehet elb�jni
     * Ha van m�r nyersanyag, vagy entit�s a magban, nem lehet elb�jni
     * Ha nincs akad�lyoz� t�nyez�, el lehet b�jni
     *
     * @param e A kib�jni-el�b�jni k�v�n� entit�s
     */
    @Override
    public void Hide(Entity e) {
        if(entityInside == e){
            AddEntity(e);
            setEntityInside(null);
            return;
        }
        if (NumberOfLayers > 0){
            return;
        }
        if (entityInside != null || materialInside != null){
            return;
        }
        setEntityInside(e);
        RemoveEntity(e);
    }

    /**
     * Az aszteroida magj�ba nyersanyag belehelyez�s�t modellez� f�ggv�ny
     *
     * Ha m�g van sziklar�teg, nem lehet beletenni a nyersanyagot, visszat�r�s false
     * Ha van m�r nyersanyag, vagy entit�s a magban, nem lehet beletenni a nyersanyagot, visszat�r�s false
     * Ha nincs akad�lyoz� t�nyez�, bele lehet tenni a nyersanyagot
     * A behelyez�s ut�n megh�v�dik a material exposed f�ggv�nye, visszat�r�s true
     *
     * @param m Az elhelyezmi k�v�nt Model.Material
     * @return A m�velet sikeress�g�t mutatja
     */
    @Override
    public boolean Place(Material m) {
        if(NumberOfLayers > 0){
            return false;
        }
        if(entityInside != null || materialInside != null){
            return false;
        }
        setMaterialInside(m);
        m.Exposed(this);
        return true;
    }

    /**
     * Az aszteroida magj�ban tal�lhat� nyersanyag kib�ny�sz�s�t modellez� f�ggv�ny
     *
     * Ha m�g van sziklar�teg, nem lehet b�ny�szni, visszat�r�s null �rt�kkel (sikertelens�g)
     *
     * Ha nincs akad�lyoz� t�nyez�, lehet b�ny�szni
     * Kivessz�k a nyersanyagot, �s visszat�r�nk vele
     * (ha �res volt, akkor null �rt�kkel)
     *
     * @return a magban tal�lhat� nyersanyag
     */
    @Override
    public Material MineMaterialInside() {
        if(NumberOfLayers > 0){
            return null;
        }
        Material tmp = materialInside;
        this.RemoveMaterial();
        return tmp;
    }

    /**
     * A materialInside-ot friss�ti null �rt�kre
     */
    @Override
    public void RemoveMaterial() {
        materialInside = null;
    }


    //Setterek

    /**
     * Az entityInside-ot friss�ti a param�terk�nt kapott Entit�sra (Setter)
     *
     * @param entityInside erre �ll�tja be az EntityInside-ot
     */
    public void setEntityInside(Entity entityInside) {
        this.entityInside = entityInside;
    }

    /**
     * A materialInside-ot friss�ti a param�terk�nt kapott Materialra (Setter)
     *
     * @param m erre friss�ti a MaterialInside-ot
     */
    public void setMaterialInside(Material m) {
        materialInside = m;
    }

    @Override
    public void Die(){
        super.Die();
        entityInside.Die();
    }

    //Getterek

    public Entity getEntityInside(){
        return entityInside;
    }

    @Override
    public Material GetMaterial(){
        return materialInside;
    }

    public String toString(){
        return "A" + id;
    }

    @Override
    public AsteroidView getView(){
        return asteroidView;
    }


}
