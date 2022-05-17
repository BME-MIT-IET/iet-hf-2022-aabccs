package Model;

import View.ObjectView;

import java.util.ArrayList;
import java.util.Random;

//A játékteret, azaz az ûrt reprezentáló osztály
public class Space {

    //Tárolt objektumok
    private Game Game;
    private ArrayList<Location> Locations;
    private ArrayList<Location> StepperLocations;
    private Sun Sun;

    //Konstruktorok

    /**
     * Konstruktor,
     * beállítja a tárolt objektumokat
     */
    public Space(){
        Game = new Game();
        Locations = new ArrayList<Location>();
        Sun = new Sun(this);
        StepperLocations = new ArrayList<Location>();
    }

    /**
     * Konstruktor,
     * beállítja a tárolt objektumokat
     *
     * @param g beállítja Game-nek
     */
    public Space(Game g){
        Game = g;
        Locations = new ArrayList<Location>();
        Sun = new Sun(this);
        StepperLocations = new ArrayList<Location>();
    }
    
    //--------------Függvények----------------------

    /**
     * Segédfüggvény a SunStorm-hoz
     * Visszatér azon helyszínek listájával, melyeken entitás tartózkodik
     *
     * @return Model.Location-ök listája, melyeken entitás tartózkodik
     */
    public ArrayList<Location> GetActiveLocations(){
        ArrayList<Location> ActiveLocations = new ArrayList<Location>();

        for(int i = 0; i < Locations.size(); i++){
            if(Locations.get(i).getEntitiesOnSurface().size() > 0){
                ActiveLocations.add(Locations.get(i));
            }
        }

        return ActiveLocations;
    }

    /**
     * A játéktérben levõ Model.Location-ök szomszédságait állítja be
     */
    public void SetNeighbours(){
        for(Location l1 : Locations){
            for(Location l2 : Locations){
                if(l2 == l1) continue;
                if(l1.getCoordinate().distance(l2.getCoordinate()) <= 140){
                    if(!l1.GetNeighbours().contains(l2) && !l2.GetNeighbours().contains(l1)){
                        l1.AddNeighbour(l2);
                        l2.AddNeighbour(l1);
                    }
                }
            }
        }
    }

    /**
     * A paraméterként kapott l Model.Location-t, felveszi a Model.Location-ök listájára
     *
     * @param l hozzáadja a Model.Location-ök listájára
     */
    public void AddLocation(Location l){
        Locations.add(l);
        Game.viewController.addObject(l.getView());
    }

    /**
     * A paraméterként kapott l Model.Location-t, felveszi a StepperLocation-ök listájára
     *
     * @param l hozzáadja a StepperLocation-ök listájára
     */
    public void AddStepperLocation(Location l){
        StepperLocations.add(l);
    }

    /**
     * A paraméterként kapott l Model.Location-t, eltávolítja a Model.Location-ök listájáról
     *
     * @param l eltávolítja a Model.Location-ök listájáról
     */
    public void RemoveLocation(Location l){
        Locations.remove(l);
        Game.viewController.removeObject(l.getView());
    }


    //-----------Setterek & Getterek------------\\

    /**
     * Getter
     *
     * @return eltárolt Model.Sun
     */
    public Sun GetSun(){
        return Sun;
    }

    /**
     * Getter
     *
     * @return eltárolt Game
     */
    public Game getGame() {
        return Game;
    }

    /**
     * Getter
     *
     * @return eltárolt Model.Location-ök listája
     */
    public ArrayList<Location> getLocations() {
        return Locations;
    }

    /**
     * Getter
     *
     * @return eltárolt StepperLocation-ök listája
     */
    public ArrayList<Location> getStepperLocations() {
        return StepperLocations;
    }

    /**
     * Getter
     *
     * @param game az eltárolt Game
     */
    public void setGame(Game game) {
        Game = game;
    }

    /**
     * Setter
     *
     * @param locations erre állítja be a Locations listát
     */
    public void setLocations(ArrayList<Location> locations) {
        Locations = locations;
    }

    /**
     * Setter
     *
     * @param stepperLocations erre állítja be a StepperLocations listát
     */
    public void setStepperLocations(ArrayList<Location> stepperLocations) {
        StepperLocations = stepperLocations;
    }

    /**
     * Setter
     *
     * @param sun erre állítja be a Model.Sun-t
     */
    public void setSun(Sun sun) {
        Sun = sun;
    }

}
