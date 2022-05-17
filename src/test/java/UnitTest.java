import Model.*;
import View.ViewController;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class UnitTest {


    @Test
    void TestTeleportSuccess(){
        TeleportGate tg1 = new TeleportGate();
        TeleportGate tg2 = new TeleportGate();

        tg1.SetPair(tg2);
        tg1.SetActive();
        tg2.SetPair(tg1);
        tg2.SetActive();

        Settler s = new Settler(tg1);
        s.Teleport();

        assertEquals(tg2,s.getLocation());
        assertNotEquals(tg1,s.getLocation());
        assertNotEquals(tg1, tg2);
    }

    @Test
    void TestSettlerCanMove(){

        Asteroid a1 = new Asteroid();
        Asteroid a2 = new Asteroid();

        a1.AddNeighbour(a2);
        a2.AddNeighbour(a1);

        Settler s = new Settler(a1);

        s.Move(a2);

        assertEquals(a2,s.getLocation());
        assertNotEquals(a1, s.getLocation());
        assertNotEquals(a1,a2);
        assertTrue(a2.GetNeighbours().contains(a1));
        assertTrue(a1.GetNeighbours().contains(a2));


    }


    @Test
    void TestWin(){

        Game g = new Game();
        ViewController v = new ViewController();
        g.setViewController(v);
        v.setGame(g);

        Space s = new Space(g);
        g.AddSpace(s);

        Asteroid a1 = new Asteroid(s);
        Settler s1 = new Settler(a1, g);

        g.AddSettler(s1);

        Coal c = new Coal();
        Iron i = new Iron();
        Uran u = new Uran();
        Water w = new Water();

        s1.AddMaterial(c);
        s1.AddMaterial(i);
        s1.AddMaterial(u);

        Asteroid a2 = new Asteroid(s);

        s.AddLocation(a2);
        s.SetNeighbours();

        Settler s2 = new Settler(a2);
        s2.AddMaterial(w);
        s2.Move(a1);

        ArrayList<Material> items01 = s1.getInventory();
        ArrayList<Material> items02 = s2.getInventory();

        items01.addAll(items02);

        g.CheckWin(items01);

        assertTrue(g.isWin());


    }

    @Test
    void TestNoSpaceInInventory(){

        Asteroid a = new Asteroid();
        Coal c = new Coal();
        a.SetMaterialInside(c);

        Settler s = new Settler(a);
        for(int i = 0; i < 10; i++){
            Iron ir = new Iron();
            s.AddMaterial(ir);
        }

        s.Mine();

        assertEquals(c, a.GetMaterial());
        assertFalse(s.getInventory().contains(c));


    }

    @Test
    void TestMineOnTeleportGate(){

        TeleportGate t = new TeleportGate();
        Settler s = new Settler(t);

        s.Mine();

        assertTrue(s.getInventory().isEmpty());

    }

    @Test
    void TestSettlerCanBuildTeleportGate(){
        Game g = new Game();
        ViewController v = new ViewController();
        g.setViewController(v);
        v.setGame(g);

        Space sp =new Space(g);
        g.AddSpace(sp);

        Iron i1 = new Iron();
        Iron i2 = new Iron();
        Water w = new Water();
        Uran u = new Uran();

        Asteroid a = new Asteroid(sp);
        sp.AddLocation(a);


        Settler s = new Settler(a,g);
        g.AddSettler(s);

        s.AddMaterial(i1);
        s.AddMaterial(i2);
        s.AddMaterial(w);
        s.AddMaterial(u);

        s.BuildTeleportGate();

        assertEquals(2, s.GetTeleportGate().size());




    }

    @Test
    void TestSettlerCanBuildRobot(){

        Iron i = new Iron();
        Coal c = new Coal();
        Uran u = new Uran();

        Game g = new Game();
        ViewController v = new ViewController();
        g.setViewController(v);
        v.setGame(g);
        Space sp = new Space(g);
        g.AddSpace(sp);
        Asteroid a = new Asteroid(sp);

        Settler s = new Settler(a, g);
        g.AddSettler(s);

        s.AddMaterial(i);
        s.AddMaterial(c);
        s.AddMaterial(u);

        s.BuildRobot();

        assertEquals(1, g.getRobotsAlive().size());
        assertEquals( 2, a.getEntitiesOnSurface().size());


    }

    @Test
    void TestHideSuccess(){

        Asteroid a = new Asteroid();
        a.setNumberOfLayers(0);

        Settler s = new Settler(a);

        s.Hide();

        assertEquals(s, a.GetEntityInside());
        assertFalse(a.getEntitiesOnSurface().contains(s));



    }

    @Test
    void TestHideFailNotEmpty(){

        Iron i = new Iron();
        Asteroid a = new Asteroid();
        a.setNumberOfLayers(0);
        a.SetMaterialInside(i);
        Settler s = new Settler(a);

        s.Hide();

        assertNotEquals(s, a.GetEntityInside());
        assertNull(a.GetEntityInside());
        assertTrue(a.getEntitiesOnSurface().contains(s));



    }

    @Test
    void TestPlaceMaterialSuccess(){

        Asteroid a = new Asteroid();
        a.setNumberOfLayers(0);
        Iron i = new Iron();
        Settler s = new Settler(a);
        s.AddMaterial(i);
        s.PlaceMaterial(i);

        assertEquals(i, a.GetMaterial());
        assertFalse(s.getInventory().contains(i));



    }

    @Test
    void TestPlaceMaterialFailNotEmpty(){

        Iron i1 = new Iron();
        Iron i2 = new Iron();
        Asteroid a = new Asteroid();
        a.SetMaterialInside(i1);
        Settler s = new Settler(a);
        s.AddMaterial(i2);
        s.PlaceMaterial(i2);

        assertNotEquals(i2, a.GetMaterial());
        assertEquals(i1, a.GetMaterial());
        assertTrue(s.getInventory().contains(i2));
        assertFalse(s.getInventory().contains(i1));


    }

    @Test
    void TestPlaceTeleportGate(){
        Game g = new Game();
        ViewController v = new ViewController();
        g.setViewController(v);
        v.setGame(g);
        Space sp = new Space(g);
        g.AddSpace(sp);
        Asteroid a = new Asteroid(sp);
        Settler s = new Settler(a, g);
        g.AddSettler(s);
        TeleportGate tg = new TeleportGate(sp);

        s.AddTeleportGate(tg);
        sp.AddLocation(a);

        s.PlaceTeleportGate(tg);

        assertTrue(sp.getLocations().contains(tg));
        assertFalse(s.GetTeleportGate().contains(tg));

    }
    @Test
    void TestDrillButNumberOfLayersEqualsZero(){

        Asteroid a = new Asteroid();
        Settler s = new Settler(a);

        a.setNumberOfLayers(0);

        s.Drill();

        assertEquals(0, a.getNumberOfLayers());


    }

    @Test
    void TestDrillOnTeleportGate(){

        TeleportGate tg = new TeleportGate();
        Settler s = new Settler(tg);

        int layerCount = tg.getNumberOfLayers();

        s.Drill();

        assertEquals(layerCount, tg.getNumberOfLayers());


    }

    @Test
    void TestAsteroidExplodesWithSettler(){

        Game g = new Game();
        ViewController v = new ViewController();
        g.setViewController(v);
        v.setGame(g);

        Space s = new Space(g);
        g.AddSpace(s);

        Asteroid a1 = new Asteroid(s);
        Asteroid a2 =new Asteroid(s);


        Settler s1 = new Settler(a1, g);
        Settler s2 = new Settler(a2, g);

        g.AddSettler(s1);
        g.AddSettler(s2);

        a1.Explode();

        assertTrue(s.GetActiveLocations().contains(a2));
        assertFalse(s.GetActiveLocations().contains(a1));
        assertTrue(g.getSettlersAlive().contains(s2));
        assertFalse(g.getSettlersAlive().contains(s1));


    }

    @Test
    void TestRobotExplodesNoNeighbour(){

        Game g = new Game();
        ViewController v = new ViewController();
        g.setViewController(v);
        v.setGame(g);

        Space s = new Space(g);
        g.AddSpace(s);

        Asteroid a = new Asteroid(s);
        Robot r = new Robot(a, g);

        g.AddRobot(r);
        r.Explode();

        assertFalse(g.getRobotsAlive().contains(r));



    }

    @Test
    void TestSunStorm(){

        Game g = new Game();
        ViewController v = new ViewController();
        g.setViewController(v);
        v.setGame(g);

        Space sp = new Space(g);
        g.AddSpace(sp);

        Asteroid a = new Asteroid(sp);
        a.setNumberOfLayers(0);
        a.SetMaterialInside(null);

        Settler s = new Settler(a, g);
        Robot r = new Robot(a, g);

        g.AddSettler(s);
        g.AddRobot(r);

        s.Hide();
        a.SunStorm();


        assertFalse(g.getRobotsAlive().contains(r));
        assertTrue(g.getSettlersAlive().contains(s));
        assertEquals(s,a.GetEntityInside());
        assertFalse(a.getEntitiesOnSurface().contains(r));


    }

    @Test
    void TestLose(){

        Game g = new Game();
        ViewController v = new ViewController();
        g.setViewController(v);
        v.setGame(g);

        Space sp = new Space(g);
        g.AddSpace(sp);

        Asteroid a = new Asteroid(sp);
        Settler s = new Settler(a, g);

        g.AddSettler(s);
        s.Die();

        assertFalse(g.isWin());

    }

    @Test
    void TestRobotDies(){

        Game g = new Game();
        ViewController v = new ViewController();
        g.setViewController(v);
        v.setGame(g);

        Space s = new Space(g);
        g.AddSpace(s);

        Asteroid a = new Asteroid(s);
        Robot r = new Robot(a,g);
        
        r.Die();

        assertFalse(g.getRobotsAlive().contains(r));
        assertFalse(a.getEntitiesOnSurface().contains(r));

    }


}
