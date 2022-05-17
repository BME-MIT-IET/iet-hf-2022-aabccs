package Model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

//A játékbeli Napot reprezentáló osztály, mely a napviharokért felelõs
public class Sun implements Stepable {

    //Tulajdonságok
    private int Counter = -1;

    //Tárolt objektumok
    private Space space;

//Konstruktorok

    /**
     * Konstruktor
     */
    public Sun(){
    }

    /**
     * Konstruktor
     *
     * @param s beállítja Model.Space-nek
     */
    public Sun(Space s){
        space = s;
    }
    
    //--------------Függvények----------------------

    /**
     * A Nap lépését reprezentáló függvény
     *
     * Random idõközönként Napvihar,
     * ekkor 3 körrel késõbb megindítja a Napvihart (erre van a Counter)
     */
    @Override
    public void Step() {
        if(Counter >= 0){
            if(Counter == 0){
                SunStorm();
            }
            Count();
        }
        else{
            Random rand = new Random();
            int probability = rand.nextInt(100);
            if(probability <= 20){
                Counter = 3;
            }
        }
    }

    /**
     * A napvihar érkezését reprezentáló függvény
     *
     * Minden aktív helyszín SunStorm függvényét meghívja
     */
    public void SunStorm(){
        ArrayList<Location> activeLocations = space.GetActiveLocations();
        for(int i = 0; i < activeLocations.size(); i++){
            Random rand = new Random();
            int probability = rand.nextInt(100);
            if(probability <= 70){
                activeLocations.get(i).SunStorm();
            }
        }
    }

    /**
     * Számolás, a napvihar megfelelõ körben érkezéséhez
     */
    public void Count(){
        Counter--;
    }

    /**
     * Getter
     *
     * @return Counter értéke
     */
    public int getCounter() {
        return Counter;
    }

    /**
     * Getter
     *
     * @return eltárolt Model.Space
     */
    public Space getSpace() {
        return space;
    }


    public void SetCounter(int i){
        Counter = i;
    }
}
