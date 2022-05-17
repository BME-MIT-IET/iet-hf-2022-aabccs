package Model;

import Control.Control;
import View.ViewController;

import java.util.ArrayList;
import java.util.Random;

//A játékmenetet kezelõ Game osztály
public class Game {

    Control control;
    ViewController viewController;

    //Tulajdonságok
    private boolean Win;
    private boolean Active;
    private Settler currentSettler = null;

    //Tárolt objektumok
    private Space Space;
    private ArrayList<Settler> SettlersAlive;
    private ArrayList<Robot> RobotsAlive;
    private ArrayList<Alien> AliensAlive;
    private Recipe winRecipe;

    private int numberOfSettlers;

    //Konstruktorok

    /**
     * Konstruktor
     *
     * Beállítja alap értékre a tulajdonságokat és tárolt objektumokat
     */
    public Game(){
        Space = new Space(this);

        SetWin(false);
        SetActive(false);
        SettlersAlive = new ArrayList<Settler>();
        RobotsAlive = new ArrayList<Robot>();
        AliensAlive = new ArrayList<Alien>();

        winRecipe = new Recipe();
        winRecipe.SetToWin();

    }

    //--------------Függvények----------------------

    /**
     * A játékot elindító függvény
     *
     * Létrehozza a Model.Space-t, az Aszterodiákat
     * beállítja a szomszédságokat
     * Létrehozza a Model.Settler-eket, illetve az Alieneket
     *
     * @param numPlayers a játékosok számát adja meg
     */
    public void StartGame(int numPlayers){
        Space = new Space(this);
        SetActive(true);

        int numOfAst = new Random().nextInt(30) + 30;
        for(int i = 0; i < numOfAst; i++){
            Asteroid a = new Asteroid(Space);
            Random random = new Random();
            a.setNumberOfLayers(random.nextInt(5));
            int p = random.nextInt(100);
            if(p <= 20){
                a.SetMaterialInside(new Iron());
            }
            else if(p <= 40){
                a.SetMaterialInside(new Coal());
            }
            else if(p <= 60){
                a.SetMaterialInside(new Water());
            }
            else if(p <= 80){
                a.SetMaterialInside(new Uran());
            }
            else{
                a.SetMaterialInside(null);
            }

            p = random.nextInt(100);
            if(p <= 20){
                a.setNearSun(true);
            }else {
                a.setNearSun(false);
            }

        }
        Space.SetNeighbours();

        for(int i = 0; i < numPlayers; i++){
            Random rand = new Random();
            int randIx = rand.nextInt(numOfAst);
            Settler s = new Settler(Space.getLocations().get(randIx));
            AddSettler(s);
        }

        int numOfAli = new Random().nextInt(10) + 3;
        for(int i = 0; i < numOfAli; i++){
            Random rand = new Random();
            int randIx = rand.nextInt(numOfAst);
            Alien al = new Alien(Space.getLocations().get(randIx));
            AddAlien(al);
        }

        GameLoop();
    }

    /**
     * A játékot lezáró függvény
     */
    public void EndGame(){
        SetActive(false);
    }

    /**
     * Hozzáadja a robotok listájához a paraméterként kapott r robotot
     *
     * @param r hozzáadja a robotok listájához
     */
    public void AddRobot(Robot r){
        r.setGame(this);
        viewController.addObject(r.getRobotView());
        RobotsAlive.add(r);
    }

    /**
     * Hozzáadja a telepesek listájához a paraméterként kapott s Model.Settler-t
     *
     * @param s hozzáadja a settlerek listájához
     */
    public void AddSettler(Settler s){
        s.setGame(this);
        viewController.addObject(s.getSettlerView());
        SettlersAlive.add(s);
    }

    /**
     * Hozzáadja az ûrlények listájához a paraméterként kapott a Alien-t
     *
     * @param a hozzáadja az alienek listájához
     */
    public void AddAlien(Alien a){
        a.setGame(this);
        viewController.addObject(a.getAlienView());
        AliensAlive.add(a);
    }

    /**
     * Eltávolítja a robotok listájáról a paraméterként kapott r robotot
     *
     * @param r eltávolítja a robotok listájából
     */
    public void RemoveRobot(Robot r){
        RobotsAlive.remove(r);
        viewController.removeObject(r.getRobotView());
    }

    /**
     * Eltávolítja a telepesek listájáról a paraméterként kapott s Settlert
     * ha az utolsó telepest távolítja el, akkor a játék véget ér
     *
     * @param s eltávolítja a settlerek listájából
     */
    public void RemoveSettler(Settler s){
        SettlersAlive.remove(s);
        viewController.removeObject(s.getSettlerView());
        if(SettlersAlive.size() == 0){
            SetActive(false);
            SetWin(false);
        }
    }

    /**
     * Eltávolítja az alienek listájáról a paraméterként kapott a Alient
     *
     * @param a eltávolítja az alienek listájából
     */
    public void RemoveAlien(Alien a){
        AliensAlive.remove(a);
        viewController.removeObject(a.getAlienView());
    }

    /**
     * A játék futásáért felelõs függvény
     */
    public void GameLoop(){
        viewController.DrawAll();
        while (Active){
            for(int i = 0; i < SettlersAlive.size(); i++){
                if(!Active) continue;
                currentSettler = SettlersAlive.get(i);
                control.setLists();
                viewController.DrawAll();
                SettlersAlive.get(i).Step();
            }

            currentSettler = null;

            for(int i = 0; i < RobotsAlive.size(); i++){
                if(!Active) continue;
                RobotsAlive.get(i).Step();
            }

            for(int i = 0; i < AliensAlive.size(); i++){
                if(!Active) continue;
                AliensAlive.get(i).Step();
            }

            for(int i = 0; i < Space.getStepperLocations().size(); i++){
                if(!Active) continue;
                Space.getStepperLocations().get(i).Step();
            }
            if(Active){
                Space.GetSun().Step();
                control.setSunCounterLabel();
            }


        }
        viewController.DrawEndScreen();
    }

    /**
     * Ellenõrzi, hogy a paraméterként kapott nyersanyagok
     * teljesítik-e a nyerés feltételét
     * Ha igen, beállítja a megfelelõ változókat a megfelelõ értékekre
     *
     * @param materials material-ok listája, melyet ellenõriz, hogy elég-e a nyeréshez
     */
    public void CheckWin(ArrayList<Material> materials){
        if (winRecipe.HasEverything(materials)){
            SetActive(false);
            SetWin(true);
        }
    }

    //-------Setterek & Getterek-----------\\

    /**
     * A paraméterként kapott s Model.Space-t eltárolja
     *
     * @param s beállítja a Model.Space-t
     */
    public void AddSpace(Space s){
        Space = s;
    }

    /**
     * A paraméterként kapott értékre állítja a Win változót (Setter)
     *
     * @param w beállítja erre a Win-t
     */
    public void SetWin(boolean w){
        Win = w;
    }

    /**
     * A paraméterként kapott értékre állítja az Active változót (Setter)
     *
     * @param a beállítja az Active értékét
     */
    public void SetActive(boolean a){
        Active = a;
    }

    /**
     * Getter
     *
     * @return visszaadja a Win értékét
     */
    public boolean isWin() {
        return Win;
    }

    /**
     * Getter
     *
     * @return visszaadja az Active értékét
     */
    public boolean isActive() {
        return Active;
    }

    /**
     * Getter
     *
     * @return visszaadja a Model.Space-t
     */
    public Space getSpace() {
        return Space;
    }

    /**
     * Getter
     *
     * @return visszaadja az életben levõ settlerek listáját
     */
    public ArrayList<Settler> getSettlersAlive() {
        return SettlersAlive;
    }

    public void setCurrentSettler(Settler s){
        currentSettler = s;
    }

    /**
     * Getter
     *
     * @return visszaadja az életben levõ robotok listáját
     */
    public ArrayList<Robot> getRobotsAlive() {
        return RobotsAlive;
    }

    /**
     * Getter
     *
     * @return visszaadja az életben levõ alienek listáját
     */
    public ArrayList<Alien> getAliensAlive() {
        return AliensAlive;
    }

    public Settler getCurrentSettler(){
        return currentSettler;
    }

    public void setControl(Control c){
        control = c;
    }

    public void setViewController(ViewController vc){
        viewController = vc;
    }
}