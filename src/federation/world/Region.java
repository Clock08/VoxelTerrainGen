package federation.world;

import federation.terrain.Terrain;

public class Region {
	
	public static final int REGION_SIZE = 16;
	
	private Chunk[][][] chunks;
	private int regionX;
	private int regionY;
	private int regionZ;
	
	public Region(int regionX, int regionY, int regionZ) {
		this.regionX = regionX;
		this.regionY = regionY;
		this.regionZ = regionZ;
		
		chunks = new Chunk[REGION_SIZE][REGION_SIZE][REGION_SIZE];
		
		for (int z = 0; z < REGION_SIZE; z++) {
			for (int y = 0; y < REGION_SIZE; y++) {
				for (int x = 0; x < REGION_SIZE; x++) {
					//chunks[x][y][z] = new Chunk(x, y, z);
				}
			}
		}
	}
	
	public void generate(Terrain terrain, int chunkX, int chunkY, int chunkZ, int radius) {
		for (int z = chunkZ - radius; z < chunkZ + radius; z++) {
			for (int y = chunkY - radius; y < chunkZ + radius; y++) {
				for (int x = chunkX - radius; x < chunkX + radius; x++) {
					//chunks[x][y][z].generate(terrain);
				}
			}
		}
	}
	
	public Chunk getChunkAt(int x, int y, int z) {
		return chunks[x][y][z];
	}
}
