package federation.block;

import federation.graphics.texture.Texture;

public class BlockFace {
	
	public int side;
	public int type;
	public int orientation;
	public boolean transparent;
	public Texture texture;
	
	public boolean equals(Object o) {
		if (o instanceof BlockFace) {
			BlockFace face = (BlockFace) o;
			return this.type == face.type && this.transparent == face.transparent;
		}
		return false;
	}
	
}
