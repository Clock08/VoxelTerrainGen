package federation.block;

import static federation.block.BlockRegistry.registerBlock;

import federation.util.Log;

public class Blocks {
	
	// Single instances of every block
	private static Block blockAir = new BlockAir();
	private static Block blockStone = new BlockStone();
	
	public static void init() {
		Log.log(Log.INFO, "Registering blocks");
		registerBlock(blockAir);
		registerBlock(blockStone);
	}
}
