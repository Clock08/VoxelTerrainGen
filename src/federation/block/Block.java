package federation.block;

import federation.graphics.texture.Texture;
import federation.graphics.texture.TextureLoader;
import federation.world.World;

public abstract class Block {
	
	private String name;
	private boolean solid;
	private boolean transparent;
	private boolean isFullBlock;
	private Texture texture;
	
	public Block(String name) {
		this.name = name;
		setSolid(true);
		setTransparent(false);
		setFullBlock(true);
		setTexture(name+".png");
	}
	
	protected final void setSolid(boolean solid) {
		this.solid = solid;
	}
	
	protected final void setTransparent(boolean transparent) {
		this.transparent = transparent;
	}
	
	protected final void setFullBlock(boolean isFullBlock) {
		this.isFullBlock = isFullBlock;
	}
	
	protected final void setTexture(String texture) {
		this.texture = TextureLoader.getTexture("blocks/"+texture);
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isSolid() {
		return solid;
	}
	
	public boolean isTransparent() {
		return transparent;
	}
	
	public boolean isFullBlock() {
		return isFullBlock;
	}
	
	
	
	public BlockFace getFace(int side) {
		BlockFace face = new BlockFace();
		face.type = BlockRegistry.getBlockId(this.getClass());
		face.transparent = this.transparent;
		face.texture = this.texture;
		
		switch (side) {
		case World.NORTH:
			face.side = World.NORTH;
			break;
		case World.SOUTH:
			face.side = World.SOUTH;
			break;
		case World.EAST:
			face.side = World.EAST;
			break;
		case World.WEST:
			face.side = World.WEST;
			break;
		case World.TOP:
			face.side = World.TOP;
			break;
		case World.BOTTOM:
			face.side = World.BOTTOM;
			break;
		}
		
		return face;
	}
}
