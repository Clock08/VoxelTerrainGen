package federation;

import federation.core.Game;
import federation.util.Log;

public class Main {
	
	public static void main(String[] args) {
		Log.log(Log.INFO, "Starting Federation");
		
		Game game = new Game();
		game.init();
		game.start();
		
		Log.log(Log.INFO, "Exiting");
		System.exit(0);
	}
	
}
