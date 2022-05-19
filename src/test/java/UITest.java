import Control.Control;
import Control.MenuController;
import Model.*;
import View.ViewController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

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

    /**
     * Elindítja a menüt, majd a kilépés gombot megnyomva teszteli, hogy leáll-e
     */
    @Test
    public void TestStartScreenExit() {

        MenuController menuController= new MenuController();
        menuController.initialize();

        Dimension size = menuController.getSize();

        if (robot != null) {
            int centerX = Toolkit.getDefaultToolkit().getScreenSize().width / 2;
            int centerY = Toolkit.getDefaultToolkit().getScreenSize().height / 2;

            int buttonX = centerX;
            int buttonY = centerY + 140;
            if (menuController.isVisible()) {
                try {
                    robot.delay(1000);
                    robot.mouseMove(buttonX, buttonY);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    robot.delay(1000);
                } catch (Exception e) {
                    System.out.println("Error while closing screen");
                }

            }
        }
        assertFalse(menuController.isVisible());
    }

    /**
     * Elindítja a menüt, majd az új játék gombbal, a default játékosszámot átállítva
     * ellenőrzi, hogy elindul-e az új játék.
     */
    @Test
    public void TestStartScreenNewGame() {

        MenuController menuController= new MenuController();
        menuController.initialize();

        Dimension size = menuController.getSize();

        if (robot != null) {
            int centerX = Toolkit.getDefaultToolkit().getScreenSize().width / 2;
            int centerY = Toolkit.getDefaultToolkit().getScreenSize().height / 2;

            int buttonX = centerX;
            int buttonY = centerY + 50;
            if (menuController.isVisible()) {
                try {
                    robot.delay(500);
                    robot.mouseMove(buttonX, buttonY);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    robot.delay(500);
                    assertTrue(menuController.isVisible());
                    buttonY = centerY + 20;
                    robot.mouseMove(buttonX, buttonY);
                    robot.keyPress(KeyEvent.VK_BACK_SPACE);
                    robot.keyPress(KeyEvent.VK_5);
                    robot.delay(500);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    robot.delay(500);
                } catch (Exception e) {
                    System.out.println("Error while screen interaction");
                }
            }
        }
        assertTrue(menuController.getStarted());
        assertEquals(menuController.getNumOfPlayers(), 5);
        assertFalse(menuController.isVisible());
    }

    /**
     * Elindítja a játékot, és teszteli, hogy a rejtett kilépés gomb
     * elérhető-e és működik-e.
     */
    @Test
    public void TestGameExit() {
        Game game = new Game();
        Control control = new Control();
        ViewController viewController = new ViewController();

        game.setControl(control);
        game.setViewController(viewController);

        control.setGame(game);
        control.setViewController(viewController);

        viewController.setControl(control);
        viewController.setGame(game);

        control.initialize();

        if (robot != null) {
            int centerX = Toolkit.getDefaultToolkit().getScreenSize().width / 2;
            int centerY = Toolkit.getDefaultToolkit().getScreenSize().height / 2;

            int buttonX = centerX*2;
            int buttonY = centerY*2;

            robot.mouseMove(buttonX, buttonY);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(1000);
        }
        assertFalse(control.isActive());

    }
}
