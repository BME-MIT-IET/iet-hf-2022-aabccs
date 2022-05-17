package Model;

import View.AlienView;
import View.SettlerView;

import java.util.ArrayList;

//A j�t�kos �ltal ir�ny�that� telepest reprezent�l� oszt�ly
public class Settler extends Entity {

    private static int stat_id = 0;

    //T�rolt objektumok
    private ArrayList<TeleportGate> TeleportGateList;
    private Recipe RobotRecipe;
    private Recipe TeleportGateRecipe;

    private SettlerView settlerView;
    private int id;

    //Konstruktorok


    /**
     * Konstruktor,
     * be�ll�tja a t�rolt objektumokat
     *
     * @param l be�ll�tja Model.Location-nek
     */
    public Settler(Location l){
        super(l);
        TeleportGateList = new ArrayList<TeleportGate>();

        RobotRecipe = new Recipe();
        RobotRecipe.SetToRobot();

        TeleportGateRecipe = new Recipe();
        TeleportGateRecipe.SetToTeleportGate();

        settlerView = new SettlerView(this);
        id = stat_id;
        stat_id++;
    }

    public Settler(Location l, Game g){
        super(l);
        Game=g;
        TeleportGateList = new ArrayList<TeleportGate>();

        RobotRecipe = new Recipe();
        RobotRecipe.SetToRobot();

        TeleportGateRecipe = new Recipe();
        TeleportGateRecipe.SetToTeleportGate();

        settlerView = new SettlerView(this);
        id = stat_id;
        stat_id++;
    }


    //--------------F�ggv�nyek----------------------

    /**
     * A telepes l�p�s�t reprezent�l� f�ggv�ny
     */
    @Override
    public void Step() {

        while (Game.getCurrentSettler() == this){
            try{
                Thread.sleep(1000);
            }catch (Exception e){

            }
        }

    }

    /**
     * A telepes hal�l�t reprezent�l� f�ggv�ny
     */
    @Override
    public void Die() {
        location.RemoveEntity(this);
        Game.RemoveSettler(this);
    }

    /**
     * A telepes felrobban�s�t reprezent�l� f�ggv�ny
     */
    @Override
    public void Explode() {
        Die();
    }

    /**
     * A telepes b�ny�sz�s�t reprezent�l� f�ggv�ny
     *
     * Ha tele van az inventory, a b�ny�sz�s nem siker�lt
     * Ha az aktu�lis Model.Location magj�ban nincs nyersanyag,
     * vagy van m�g k�regr�teg, a b�ny�sz�s nem siker�lt
     * K�l�nben pedig megt�rt�nik a b�ny�sz�s,
     * a Model.Location-b�l elt�vol�tja a Materialt
     * a Model.Settler inventory-j�hoz pedig hozz�adja
     */
    public void Mine(){
        if(!CheckInventorySum()){
            return;
        }
        Material material = location.MineMaterialInside();
        if(material == null){
            return;
        }
        this.AddMaterial(material);
    }

    /**
     * Robot �p�t�s�t reprezent�l� f�ggv�ny
     *
     * Ha minden nyersanyag megvan, azok elt�nnek
     * �s az aktu�lis aszteroid�n l�trej�n egy robot
     * K�l�nben sikertelen az �p�t�s
     */
    public void BuildRobot(){
        if(RobotRecipe.HasEverything(Materials)){
            Materials = RobotRecipe.UpdateInventory(Materials);
            Robot r = new Robot(location);
            //location.AddEntity(r);
            Game.AddRobot(r);
        }
    }

    /**
     * Teleportkapu �p�t�s�t reprezent�l� f�ggv�ny
     *
     * Ha nincs hely az inventoryban, az �p�t�s sikertelen
     * Ha minden nyersanyag megvan, azok l�trej�nnek
     * �s a telepes eszk�zt�r�ban l�trej�n egy teleportkapu-p�r
     */
    public void BuildTeleportGate(){
        if(TeleportGateList.size() > 1 || !CheckInventorySum()){
            return;
        }
        if(TeleportGateRecipe.HasEverything(Materials)){
            Materials = TeleportGateRecipe.UpdateInventory(Materials);
            TeleportGate t1 = new TeleportGate(Game.getSpace());
            TeleportGate t2 = new TeleportGate(Game.getSpace());
            t1.SetPair(t2);
            t2.SetPair(t1);

            AddTeleportGate(t1);
            AddTeleportGate(t2);

        }
    }

    /**
     * Teleportkapu lehelyez�s�t reprezent�l� f�ggv�ny
     *
     * Megh�vja a teleportkapu Place f�ggv�ny�t, param�terk�nt
     * az aktu�lis Model.Location-t �tadva
     *
     * @param tp a lehelyezni k�v�nt TeleportGate
     */
    public void PlaceTeleportGate(TeleportGate tp){
        tp.Place(location);
        RemoveTeleportGate(tp);
    }

    /**
     * Nyersanyag aszteroid�ba helyez�s�t reprezent�l� f�ggv�ny
     *
     * Megh�vja a nyersanyag Place f�ggv�ny�t, param�terk�nt
     * az aktu�lis Model.Location-t �tadva
     *
     * @param m a lehelyezni k�v�nt Model.Material
     */
    public void PlaceMaterial(Material m){
        if(m.Place(location)){
            Materials.remove(m);
            m.Exposed(location);
        }
    }

    /**
     * Az eszk�zt�r m�ret�t ellen�rz� f�ggv�ny
     *
     * @return felvehet�-e a nyersanyag (true), vagy sem (false)
     */
    public boolean CheckInventorySum(){
        return Materials.size() < 10;
    }

    /**
     * A telepes teleport�l�s�t reprezent�l� f�ggv�ny
     * Megh�vja az aktu�lis Model.Location teleport f�ggv�ny�t
     */
    public void Teleport(){
        location.Teleport(this);
    }

    /**
     * Elt�vol�tja a param�terk�nt kapott m Materialt a telepes inventory-j�b�l
     *
     * @param m elt�vol�tja a telepes inventory-j�b�l
     */
    public void RemoveMaterial(Material m) {
        Materials.remove(m);
    }

    /**
     * Elt�vol�tja a param�terk�nt kapott t TeleportGate-t a telepes inventory-j�b�l
     *
     * @param t elt�vol�tja a telepes inventory-j�b�l
     */
    public void RemoveTeleportGate(TeleportGate t) {
        TeleportGateList.remove(t);
    }

    /**
     *  Felveszi a param�terk�nt kapott m Materialt a telepes inventory-j�ba
     *
     * @param m hozz�adja a telepes inventory-j�hoz
     */
    public void AddMaterial(Material m){
        Materials.add(m);
    }

    /**
     * Felveszi a param�terk�nt kapott t TeleportGate-t a telepes inventory-j�ba
     *
     * @param t hozz�adja a telepes inventory-j�hoz
     */
    public void AddTeleportGate(TeleportGate t){
        TeleportGateList.add(t);
    }

    //-----------Setterek & Getterek-------\\

    public ArrayList<TeleportGate> GetTeleportGate(){
        return TeleportGateList;
    }

    public SettlerView getSettlerView(){
        return settlerView;
    }

    public String toString(){
        return "S" + id;
    }

}
