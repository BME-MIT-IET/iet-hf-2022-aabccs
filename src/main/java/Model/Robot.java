package Model;

import View.AlienView;
import View.RobotView;

import java.util.ArrayList;
import java.util.Random;

//A j�t�kbeli robotot, mint entit�st reprezent�l� f�ggv�ny
public class Robot extends Entity{

    private RobotView robotView;

    //Konstruktorok

    /**
     * Konstruktor
     *
     * @param l be�ll�tja Model.Location-nek
     */
    public Robot(Location l){
        super(l);
        robotView = new RobotView(this);
    }

    public Robot(Location l, Game g){
        super(l);
        Game = g;
        robotView = new RobotView(this);
    }
    
    //--------------F�ggv�nyek----------------------

    /**
     * A robot l�p�s�t reprezent�l� f�ggv�ny
     *
     * Ha hamarosan SunStorm lesz, akkor elb�jik
     * K�l�nben ha tud m�g f�rni az adott Model.Location-�n, akkor f�r
     * Ha nem tud, akkor pedig tov�bbl�p a Model.Location valamelyik szomsz�dj�ra,
     * ha a Locationnek m�r nincs szomsz�dja, akkor meghal
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
     * A robot hal�l�t reprezent�l� f�ggv�ny
     */
    @Override
    public void Die() {
        location.RemoveEntity(this);
        Game.RemoveRobot(this);
    }

    /**
     * A robotot el�r� robban�s hat�s�t reprezent�l� f�ggv�ny
     *
     * Ha nincs az aktu�lis Model.Location-nek szomsz�dja, meghal
     * Egy�bk�nt egy szomsz�dra ker�l �t
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
