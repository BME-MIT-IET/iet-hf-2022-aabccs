package Model;

import View.AlienView;
import View.SettlerView;

import java.util.ArrayList;

//A játékos által irányítható telepest reprezentáló osztály
public class Settler extends Entity {

    private static int stat_id = 0;

    //Tárolt objektumok
    private ArrayList<TeleportGate> TeleportGateList;
    private Recipe RobotRecipe;
    private Recipe TeleportGateRecipe;

    private SettlerView settlerView;
    private int id;

    //Konstruktorok


    /**
     * Konstruktor,
     * beállítja a tárolt objektumokat
     *
     * @param l beállítja Model.Location-nek
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


    //--------------Függvények----------------------

    /**
     * A telepes lépését reprezentáló függvény
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
     * A telepes halálát reprezentáló függvény
     */
    @Override
    public void Die() {
        location.RemoveEntity(this);
        Game.RemoveSettler(this);
    }

    /**
     * A telepes felrobbanását reprezentáló függvény
     */
    @Override
    public void Explode() {
        Die();
    }

    /**
     * A telepes bányászását reprezentáló függvény
     *
     * Ha tele van az inventory, a bányászás nem sikerült
     * Ha az aktuális Model.Location magjában nincs nyersanyag,
     * vagy van még kéregréteg, a bányászás nem sikerült
     * Különben pedig megtörténik a bányászás,
     * a Model.Location-bõl eltávolítja a Materialt
     * a Model.Settler inventory-jához pedig hozzáadja
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
     * Robot építését reprezentáló függvény
     *
     * Ha minden nyersanyag megvan, azok eltûnnek
     * és az aktuális aszteroidán létrejön egy robot
     * Különben sikertelen az építés
     */
    public void BuildRobot(){
        if(RobotRecipe.HasEverything(Materials)){
            Materials = RobotRecipe.UpdateInventory(Materials);
            Robot r = new Robot(location);
            location.AddEntity(r);
            Game.AddRobot(r);
        }
    }

    /**
     * Teleportkapu építését reprezentáló függvény
     *
     * Ha nincs hely az inventoryban, az építés sikertelen
     * Ha minden nyersanyag megvan, azok létrejönnek
     * és a telepes eszköztárában létrejön egy teleportkapu-pár
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
     * Teleportkapu lehelyezését reprezentáló függvény
     *
     * Meghívja a teleportkapu Place függvényét, paraméterként
     * az aktuális Model.Location-t átadva
     *
     * @param tp a lehelyezni kívánt TeleportGate
     */
    public void PlaceTeleportGate(TeleportGate tp){
        tp.Place(location);
        RemoveTeleportGate(tp);
    }

    /**
     * Nyersanyag aszteroidába helyezését reprezentáló függvény
     *
     * Meghívja a nyersanyag Place függvényét, paraméterként
     * az aktuális Model.Location-t átadva
     *
     * @param m a lehelyezni kívánt Model.Material
     */
    public void PlaceMaterial(Material m){
        if(m.Place(location)){
            Materials.remove(m);
            m.Exposed(location);
        }
    }

    /**
     * Az eszköztár méretét ellenõrzõ függvény
     *
     * @return felvehetõ-e a nyersanyag (true), vagy sem (false)
     */
    public boolean CheckInventorySum(){
        return Materials.size() < 10;
    }

    /**
     * A telepes teleportálását reprezentáló függvény
     * Meghívja az aktuális Model.Location teleport függvényét
     */
    public void Teleport(){
        location.Teleport(this);
    }

    /**
     * Eltávolítja a paraméterként kapott m Materialt a telepes inventory-jából
     *
     * @param m eltávolítja a telepes inventory-jából
     */
    public void RemoveMaterial(Material m) {
        Materials.remove(m);
    }

    /**
     * Eltávolítja a paraméterként kapott t TeleportGate-t a telepes inventory-jából
     *
     * @param t eltávolítja a telepes inventory-jából
     */
    public void RemoveTeleportGate(TeleportGate t) {
        TeleportGateList.remove(t);
    }

    /**
     *  Felveszi a paraméterként kapott m Materialt a telepes inventory-jába
     *
     * @param m hozzáadja a telepes inventory-jához
     */
    public void AddMaterial(Material m){
        Materials.add(m);
    }

    /**
     * Felveszi a paraméterként kapott t TeleportGate-t a telepes inventory-jába
     *
     * @param t hozzáadja a telepes inventory-jához
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
