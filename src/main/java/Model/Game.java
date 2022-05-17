package Model;

import Control.Control;
import View.ViewController;

import java.util.ArrayList;
import java.util.Random;

//A j�t�kmenetet kezel� Game oszt�ly
public class Game {

    Control control;
    ViewController viewController;

    //Tulajdons�gok
    private boolean Win;
    private boolean Active;
    private Settler currentSettler = null;

    //T�rolt objektumok
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
     * Be�ll�tja alap �rt�kre a tulajdons�gokat �s t�rolt objektumokat
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

    //--------------F�ggv�nyek----------------------

    /**
     * A j�t�kot elind�t� f�ggv�ny
     *
     * L�trehozza a Model.Space-t, az Aszterodi�kat
     * be�ll�tja a szomsz�ds�gokat
     * L�trehozza a Model.Settler-eket, illetve az Alieneket
     *
     * @param numPlayers a j�t�kosok sz�m�t adja meg
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
     * A j�t�kot lez�r� f�ggv�ny
     */
    public void EndGame(){
        SetActive(false);
    }

    /**
     * Hozz�adja a robotok list�j�hoz a param�terk�nt kapott r robotot
     *
     * @param r hozz�adja a robotok list�j�hoz
     */
    public void AddRobot(Robot r){
        r.setGame(this);
        viewController.addObject(r.getRobotView());
        RobotsAlive.add(r);
    }

    /**
     * Hozz�adja a telepesek list�j�hoz a param�terk�nt kapott s Model.Settler-t
     *
     * @param s hozz�adja a settlerek list�j�hoz
     */
    public void AddSettler(Settler s){
        s.setGame(this);
        viewController.addObject(s.getSettlerView());
        SettlersAlive.add(s);
    }

    /**
     * Hozz�adja az �rl�nyek list�j�hoz a param�terk�nt kapott a Alien-t
     *
     * @param a hozz�adja az alienek list�j�hoz
     */
    public void AddAlien(Alien a){
        a.setGame(this);
        viewController.addObject(a.getAlienView());
        AliensAlive.add(a);
    }

    /**
     * Elt�vol�tja a robotok list�j�r�l a param�terk�nt kapott r robotot
     *
     * @param r elt�vol�tja a robotok list�j�b�l
     */
    public void RemoveRobot(Robot r){
        RobotsAlive.remove(r);
        viewController.removeObject(r.getRobotView());
    }

    /**
     * Elt�vol�tja a telepesek list�j�r�l a param�terk�nt kapott s Settlert
     * ha az utols� telepest t�vol�tja el, akkor a j�t�k v�get �r
     *
     * @param s elt�vol�tja a settlerek list�j�b�l
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
     * Elt�vol�tja az alienek list�j�r�l a param�terk�nt kapott a Alient
     *
     * @param a elt�vol�tja az alienek list�j�b�l
     */
    public void RemoveAlien(Alien a){
        AliensAlive.remove(a);
        viewController.removeObject(a.getAlienView());
    }

    /**
     * A j�t�k fut�s��rt felel�s f�ggv�ny
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
     * Ellen�rzi, hogy a param�terk�nt kapott nyersanyagok
     * teljes�tik-e a nyer�s felt�tel�t
     * Ha igen, be�ll�tja a megfelel� v�ltoz�kat a megfelel� �rt�kekre
     *
     * @param materials material-ok list�ja, melyet ellen�riz, hogy el�g-e a nyer�shez
     */
    public void CheckWin(ArrayList<Material> materials){
        if (winRecipe.HasEverything(materials)){
            SetActive(false);
            SetWin(true);
        }
    }

    //-------Setterek & Getterek-----------\\

    /**
     * A param�terk�nt kapott s Model.Space-t elt�rolja
     *
     * @param s be�ll�tja a Model.Space-t
     */
    public void AddSpace(Space s){
        Space = s;
    }

    /**
     * A param�terk�nt kapott �rt�kre �ll�tja a Win v�ltoz�t (Setter)
     *
     * @param w be�ll�tja erre a Win-t
     */
    public void SetWin(boolean w){
        Win = w;
    }

    /**
     * A param�terk�nt kapott �rt�kre �ll�tja az Active v�ltoz�t (Setter)
     *
     * @param a be�ll�tja az Active �rt�k�t
     */
    public void SetActive(boolean a){
        Active = a;
    }

    /**
     * Getter
     *
     * @return visszaadja a Win �rt�k�t
     */
    public boolean isWin() {
        return Win;
    }

    /**
     * Getter
     *
     * @return visszaadja az Active �rt�k�t
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
     * @return visszaadja az �letben lev� settlerek list�j�t
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
     * @return visszaadja az �letben lev� robotok list�j�t
     */
    public ArrayList<Robot> getRobotsAlive() {
        return RobotsAlive;
    }

    /**
     * Getter
     *
     * @return visszaadja az �letben lev� alienek list�j�t
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