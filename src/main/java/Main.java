import Control.Control;
import Control.MenuController;
import Model.Game;
import View.ViewController;

import java.io.*;

//A tesztelést vezérlő főprogram osztálya
public class Main {

	private static MenuController menuController;
	private static Game game;
	private static Control control;
	private static ViewController viewController;


	public static void main(String args[]) throws IOException {

		menuController = new MenuController();
		menuController.initialize();
		
		while(menuController.isVisible()){
			try{
				Thread.sleep(1000);
			}catch (Exception e){
				System.out.println(e);
			}

		}

		if(menuController.getStarted()){
			game = new Game();
			control = new Control();
			viewController = new ViewController();

			game.setControl(control);
			game.setViewController(viewController);

			control.setGame(game);
			control.setViewController(viewController);

			viewController.setControl(control);
			viewController.setGame(game);

			control.initialize();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				game.StartGame(menuController.getNumOfPlayers());
			}catch (Exception e){
				System.out.println(e);
			}


		}



	}
	
}
