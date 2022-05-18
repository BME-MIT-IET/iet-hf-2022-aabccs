import Model.*;
import Model.Robot;
import View.ViewController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StressTest {

    @Test
    void Test100000Settlers(){
        Asteroid a1 = new Asteroid();

        for(int i= 0 ; i<100000;i++)
        {
            new Settler(a1);
        }

        assertEquals(100000, a1.getEntitiesOnSurface().size());
    }

    @Test
    void Test100000Aliens(){
        Asteroid a1 = new Asteroid();

        for(int i= 0 ; i<100000;i++)
        {
            new Alien(a1);
        }

        assertEquals(100000, a1.getEntitiesOnSurface().size());
    }

    @Test
    void Test100000Robots(){
        Asteroid a1 = new Asteroid();

        for(int i= 0 ; i<100000;i++)
        {
            new Robot(a1);
        }

        assertEquals(100000, a1.getEntitiesOnSurface().size());
    }

    @Test
    void Test1000000Asteroids(){
        Game g = new Game();
        ViewController v = new ViewController();

        g.setViewController(v);
        v.setGame(g);
        Space s = new Space(g);

        for(int i= 0 ; i<1000000;i++)
        {
            Asteroid a1 = new Asteroid(s);
        }

        assertEquals(1000000,s.getLocations().size() );
    }

    @Test
    void Test100000SettlersCanMove(){

        Asteroid a1 = new Asteroid();
        Asteroid a2 = new Asteroid();

        a1.AddNeighbour(a2);
        a2.AddNeighbour(a1);

        for(int i= 0 ; i<100000;i++)
        {
            Settler s = new Settler(a1);
            s.Move(a2);
        }


        assertEquals(0, a1.getEntitiesOnSurface().size());
        assertEquals(100000, a2.getEntitiesOnSurface().size());
    }

    @Test
    void TestSunStormWithALotOfEntities(){
        Game g = new Game();
        ViewController v = new ViewController();

        g.setViewController(v);
        v.setGame(g);
        Space s = new Space(g);

        Asteroid a1 = new Asteroid(s);

        for(int i= 0 ; i<100000;i++)
        {
            Settler se = new Settler(a1);
            g.AddSettler(se);
            Robot r = new Robot(a1);
            g.AddRobot(r);
            a1.SunStorm();
        }

        assertEquals(0, a1.getEntitiesOnSurface().size());
    }

    @Test
    void Test100000SettlersCanMoveWithTeleportGate(){

        TeleportGate t1 = new TeleportGate();
        TeleportGate t2 = new TeleportGate();


        t1.SetPair(t2);
        t2.SetPair(t1);
        t1.SetActive();
        t2.SetActive();

        for(int i= 0 ; i<10;i++)
        {
            Settler s = new Settler(t1);
            s.Teleport();
        }

        assertEquals(0, t1.getEntitiesOnSurface().size());
        assertEquals(10, t2.getEntitiesOnSurface().size());
    }



}
