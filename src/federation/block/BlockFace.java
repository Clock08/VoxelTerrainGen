package federation.block;

public class BlockFace {
	
	public int side;
	public short type;
	public int orientation;
	public boolean transparent;
	
	public boolean equals(Object o) {
		if (o instanceof BlockFace) {
			BlockFace face = (BlockFace) o;
			return this.type == face.type && this.transparent == face.transparent;
		}
		return false;
	}
	
}
