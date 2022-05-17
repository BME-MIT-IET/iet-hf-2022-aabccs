package Model;

import View.AlienView;
import View.RobotView;

import java.util.ArrayList;
import java.util.Random;

//A játékbeli robotot, mint entitást reprezentáló függvény
public class Robot extends Entity{

    private RobotView robotView;

    //Konstruktorok

    /**
     * Konstruktor
     *
     * @param l beállítja Model.Location-nek
     */
    public Robot(Location l){
        super(l);
        robotView = new RobotView(this);
    }
    
    //--------------Függvények----------------------

    /**
     * A robot lépését reprezentáló függvény
     *
     * Ha hamarosan SunStorm lesz, akkor elbújik
     * Különben ha tud még fúrni az adott Model.Location-ön, akkor fúr
     * Ha nem tud, akkor pedig továbbláp a Model.Location valamelyik szomszédjára,
     * ha a Locationnek már nincs szomszédja, akkor meghal
     */
    @Override
    public void Step() {
        if(Game.getSpace().GetSun().getCounter() == 1){
            Hide();
        }
        else if(location.getNumberOfLayers() > 0){
            Drill();
        }
        else{
            ArrayList<Location> neighbours = location.GetNeighbours();
            if(neighbours.size() > 0){
                Random rand = new Random();
                int randIx = rand.nextInt(neighbours.size());
                Move(neighbours.get(randIx));
            }
            else {
                Die();
            }
        }
    }

    /**
     * A robot halálát reprezentáló függvény
     */
    @Override
    public void Die() {
        location.RemoveEntity(this);
        Game.RemoveRobot(this);
    }

    /**
     * A robotot elérõ robbanás hatását reprezentáló függvény
     *
     * Ha nincs az aktuális Model.Location-nek szomszédja, meghal
     * Egyébként egy szomszédra kerül át
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

    public RobotView getRobotView(){
        return robotView;
    }

}
