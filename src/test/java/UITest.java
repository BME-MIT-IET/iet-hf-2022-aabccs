import Control.MenuController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.InputEvent;

import static org.junit.jupiter.api.Assertions.*;

public class UITest {
    private Robot robot;

    @BeforeEach
    public void InitRobot() {
        try {
            robot = new Robot();
        }
        catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
            assertNotNull(robot);
        }
    }

    @Test
    public void TestStartScreen() {
        MenuController menuController= new MenuController();
        menuController.initialize();

        Dimension size = menuController.getSize();

        int centerX = Toolkit.getDefaultToolkit().getScreenSize().width/2;
        int centerY = Toolkit.getDefaultToolkit().getScreenSize().height/2;

        int buttonX = centerX;
        int buttonY = centerY + 140;
        while(menuController.isVisible()){
            try{
                Thread.sleep(1000);
                robot.mouseMove(buttonX, buttonY);
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            }catch (Exception e){

            }

        }
        assertFalse(menuController.isVisible());
    }

}
