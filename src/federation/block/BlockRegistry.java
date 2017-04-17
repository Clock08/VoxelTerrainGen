package federation.block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import federation.util.Log;

public class BlockRegistry {
	
	private static Map<Class<? extends Block>, Block> blockRegister = new HashMap<Class<? extends Block>, Block>();
	private static List<Class<? extends Block>> indexRegister = new ArrayList<Class<? extends Block>>();
	
	public static void registerBlock(Block block) {
		if (indexRegister.contains(block.getClass())) throw new IllegalStateException("Block can only be registered once!");
		
		blockRegister.put(block.getClass(), block);
		indexRegister.add(block.getClass());
		Log.log(Log.INFO, "Registered " + block.getName() + " at ID: " + (indexRegister.size()-1));
	}
	
	public static Block getBlock(Class<? extends Block> block) {
		return blockRegister.get(block);
	}
	
	public static Block getBlockFromId(int id) {
		return getBlock(indexRegister.get(id));
	}
	
	public static Integer getBlockId(Class<? extends Block> block) {
		return indexRegister.indexOf(block);
	}
	
}
