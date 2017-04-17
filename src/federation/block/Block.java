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
	
	private BlockFace face;
	
	public Block(String name) {
		this.name = name;
		setSolid(true);
		setTransparent(false);
		setFullBlock(true);
		setTexture(name+".png");
		
		face = new BlockFace();
		face.texture = this.texture;
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
		face.type = BlockRegistry.getBlockId(this.getClass());
		face.transparent = this.transparent;
		face.side = side;
		
		return face;
	}
}
