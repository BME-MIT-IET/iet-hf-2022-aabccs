import Model.Settler;
import Model.TeleportGate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class asteroid {
    @Test
    void teleport(){
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
}
