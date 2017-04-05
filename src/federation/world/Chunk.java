package federation.world;

import org.joml.Vector3i;

import federation.block.Block;
import federation.block.BlockAir;
import federation.block.BlockRegistry;
import federation.block.BlockStone;
import federation.graphics.model.Mesh;
import federation.terrain.Terrain;

public class Chunk {
	
	public static final int CHUNK_SIZE = 32;
	
	private Short[][][] blocks;
	private Chunk north, south, east, west, top, bottom;
	private int numNeighbors = 0;
	private Vector3i chunkPos;
	private Mesh mesh;
	
	private boolean isLoaded;
	private boolean isDirty;
	private boolean isEmpty;
	
	public Chunk(Vector3i chunkPos) {
		this.chunkPos = chunkPos;
		
		blocks = new Short[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];
		numNeighbors = 0;
		
		north = null;
		south = null;
		east = null;
		west = null;
		top = null;
		bottom = null;
		
		isLoaded = false;
		isDirty = true;
		isEmpty = true;
	}
	
	public void load(Terrain terrain) {
		if (isLoaded) return;
		
		for (int z = 0; z < CHUNK_SIZE; z++) {
			for (int y = 0; y < CHUNK_SIZE; y++) {
				for (int x = 0; x < CHUNK_SIZE; x++) {
					Short block;
					
					if (terrain.isAir(x, y, z)) {
						block = BlockRegistry.getBlockId(BlockAir.class);
					} else {
						block = BlockRegistry.getBlockId(BlockStone.class);
					}
					
					blocks[x][y][z] = block;
				}
			}
		}
		// TODO: Load from file + terrain
		
		isLoaded = true;
		isDirty = true;
	}
	
	public void unload() {
		if (!isLoaded) return;
		
		if (north != null) {
			north.setSouthNeighbor(null);
		}
		if (south != null) {
			south.setNorthNeighbor(null);
		}
		if (east != null) {
			east.setWestNeighbor(null);
		}
		if (west != null) {
			west.setEastNeighbor(null);
		}
		if (top != null) {
			top.setBottomNeighbor(null);
		}
		if (bottom != null) {
			bottom.setTopNeighbor(null);
		}
		
		// TODO: Save to file
		
		isLoaded = false;
	}
	
	public void build() {
		if (!isDirty) return;
		
		// Create mesh
		
		isDirty = false;
	}
	
	public Mesh getMesh() {
		return mesh;
	}
	
	public boolean isLoaded() {
		return isLoaded;
	}
	
	public boolean isDirty() {
		return isDirty;
	}
	
	public boolean isEmpty() {
		return isEmpty;
	}
	
	public Vector3i pos() {
		return new Vector3i(chunkPos);
	}
	
	public Block getBlockAt(int x, int y, int z) {
		return BlockRegistry.getBlockFromId(blocks[x][y][z]);
	}
	
	public void setBlockAt(Class<? extends Block> block, int x, int y, int z) {
		blocks[x][y][z] = BlockRegistry.getBlockId(block);
		isDirty = true;
	}
	
	public int numNeighbors() {
		return numNeighbors;
	}
	
	public Chunk getNorthNeighbor() {
		return north;
	}

	public void setNorthNeighbor(Chunk north) {
		if (this.north == null) numNeighbors++;
		if (this.north != null && north == null) numNeighbors--;
		this.north = north;
	}

	public Chunk getSouthNeighbor() {
		return south;
	}

	public void setSouthNeighbor(Chunk south) {
		if (this.south == null) numNeighbors++;
		if (this.south != null && south == null) numNeighbors--;
		this.south = south;
	}

	public Chunk getEastNeighbor() {
		return east;
	}

	public void setEastNeighbor(Chunk east) {
		if (this.east == null) numNeighbors++;
		if (this.east != null && east == null) numNeighbors--;
		this.east = east;
	}

	public Chunk getWestNeighbor() {
		return west;
	}

	public void setWestNeighbor(Chunk west) {
		if (this.west == null) numNeighbors++;
		if (this.west != null && west == null) numNeighbors--;
		this.west = west;
	}

	public Chunk getTopNeighbor() {
		return top;
	}

	public void setTopNeighbor(Chunk top) {
		if (this.top == null) numNeighbors++;
		if (this.top != null && top == null) numNeighbors--;
		this.top = top;
	}

	public Chunk getBottomNeighbor() {
		return bottom;
	}

	public void setBottomNeighbor(Chunk bottom) {
		if (this.bottom == null) numNeighbors++;
		if (this.bottom != null && bottom == null) numNeighbors--;
		this.bottom = bottom;
	}
	
	

	public static Chunk loadFromFile(Vector3i chunkPos) {
		
		/*int regionX = chunkPos.x / Region.REGION_SIZE;
		int regionY = chunkPos.y / Region.REGION_SIZE;
		int regionZ = chunkPos.z / Region.REGION_SIZE;
		FileInputStream fs = FileIO.read(regionX+"_"+regionY+"_"+regionZ+".rgn");
		chunk.load();
		*/
		
		return null;
	}
	
	public static void saveToFile(Chunk chunk) {
		
	}
}
