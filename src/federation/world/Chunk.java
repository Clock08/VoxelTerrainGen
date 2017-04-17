package federation.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Vector3f;
import org.joml.Vector3i;

import federation.block.Block;
import federation.block.BlockAir;
import federation.block.BlockDirt;
import federation.block.BlockFace;
import federation.block.BlockRegistry;
import federation.block.BlockStone;
import federation.block.BlockWater;
import federation.graphics.model.MeshLoader;
import federation.graphics.model.Model;
import federation.graphics.model.Quad;
import federation.graphics.model.TexturedQuad;
import federation.terrain.Terrain;

public class Chunk {
	
	public static final int CHUNK_SIZE = 32;
	
	private Integer[][][] blocks;
	private Chunk north, south, east, west, top, bottom;
	private int numNeighbors = 0;
	private Vector3i chunkPos;
	private List<Model> models;
	
	private boolean isLoaded;
	private boolean isDirty;
	private boolean isEmpty;
	
	public Chunk(Vector3i chunkPos) {
		this.chunkPos = chunkPos;
		models = new ArrayList<Model>();
		
		blocks = new Integer[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];
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
		
		for (Model m : models) {
			MeshLoader.deleteMesh(m.getMesh());
		}
		models.clear();
		isEmpty = false;
		isDirty = false;
		
		for (int z = 0; z < CHUNK_SIZE; z++) {
			for (int y = 0; y < CHUNK_SIZE; y++) {
				for (int x = 0; x < CHUNK_SIZE; x++) {
					int block;
					
					if (terrain.isAir(x+chunkPos.x*CHUNK_SIZE, y+chunkPos.y*CHUNK_SIZE, z+chunkPos.z*CHUNK_SIZE)) {
						if (y+chunkPos.y*CHUNK_SIZE >= 0) block = BlockRegistry.getBlockId(BlockAir.class);
						else block = BlockRegistry.getBlockId(BlockWater.class);
					} else if (Math.random() * 10 < 3) {
						block = BlockRegistry.getBlockId(BlockStone.class);
					} else {
						block = BlockRegistry.getBlockId(BlockDirt.class);
					}
					
					if (block != 0) {
						isEmpty = false;
						isDirty = true;
					}
					blocks[x][y][z] = block;
				}
			}
		}
		// TODO: Load from file + terrain
		
		isLoaded = true;
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
		
		for (Model m : models) {
			MeshLoader.deleteMesh(m.getMesh());
		}
		models.clear();
			
		// TODO: Save to file
		
		isLoaded = false;
	}
	
	public void build() {
		if (!isDirty || isEmpty) return;
		
		greedyMesh();
		
		isDirty = false;
	}
	
	public List<Model> getModels() {
		return models;
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
	
	public boolean isBlockActive(int x, int y, int z) {
		return !(getBlockAt(x, y, z) instanceof BlockAir);
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
	
	
	
	private void greedyMesh() {
		Map<Integer, List<TexturedQuad>> quadMatrix = new HashMap<Integer, List<TexturedQuad>>();
		
		BlockFace[] mask = new BlockFace[CHUNK_SIZE * CHUNK_SIZE];
		BlockFace bf1, bf2;
		int[] x = {0, 0, 0};
		int[] q = {0, 0, 0};
		int[] du = {0, 0, 0};
		int[] dv = {0, 0, 0};
		int u, v, n, i, j, w, h, k, l, side;
		boolean backFace;
		
		for (int f = 0; f < 2; f++) {
			for (int dim = 0; dim < 3; dim++) {
				backFace = f == 1;
				u = (dim+1) % 3;
				v = (dim+2) % 3;
				q[0] = 0;
				q[1] = 0;
				q[2] = 0;
				q[dim] = 1;
				x[0] = 0;
				x[1] = 0;
				x[2] = 0;
				x[dim] = -1;
				
				side = 0;
				if (dim == 0) side = backFace ? World.WEST : World.EAST;
				else if (dim == 1) side = backFace ? World.BOTTOM : World.TOP;
				else if (dim == 2) side = backFace ? World.SOUTH : World.NORTH;
				
				while (x[dim] < CHUNK_SIZE) {
					n = 0;
					for (x[v] = 0; x[v] < CHUNK_SIZE; x[v]++) {
						for (x[u] = 0; x[u] < CHUNK_SIZE; x[u]++) {
							bf1 = 0 <= x[dim] ? getBlockFace(x[0], x[1], x[2], side) : null;
							bf2 = x[dim] < CHUNK_SIZE-1 ? getBlockFace(x[0]+q[0], x[1]+q[1], x[2]+q[2], side) : null;
							if (bf1 != null && bf2 != null && (!bf1.transparent && !bf2.transparent)) mask[n] = null;
							else if (backFace) mask[n] = bf2;
							else mask[n] = bf1;
							n++;
						}
					}
					x[dim]++;
					
					n = 0;
					for (j = 0; j < CHUNK_SIZE; j++) {
						for (i = 0; i < CHUNK_SIZE; ) {
							if (mask[n] != null && mask[n].type != 0) {
								for (w = 1; i+w < CHUNK_SIZE && mask[n+w] != null && mask[n+w].equals(mask[n]); w++);
								boolean done = false;
								for (h = 1; j+h < CHUNK_SIZE; h++) {
									for (k = 0; k < w; k++) {
										if (mask[n+k+h*CHUNK_SIZE] == null || !mask[n+k+h*CHUNK_SIZE].equals(mask[n])) {
											done = true;
											break;
										}
									}
									if (done) {
										break;
									}
								}
								
								x[u] = i;
								x[v] = j;
								du[0] = 0;
								du[1] = 0;
								du[2] = 0;
								du[u] = w;
								dv[0] = 0;
								dv[1] = 0;
								dv[2] = 0;
								dv[v] = h;
								
								Vector3f translation = new Vector3f(chunkPos.x, chunkPos.y, chunkPos.z).mul(CHUNK_SIZE);
								Vector3f topLeft = new Vector3f(x[0] + du[0], x[1] + du[1], x[2] + du[2]).add(translation);
								Vector3f topRight = new Vector3f(x[0] + du[0] + dv[0], x[1] + du[1] + dv[1], x[2] + du[2] + dv[2]).add(translation);
								Vector3f bottomLeft = new Vector3f(x[0], x[1], x[2]).add(translation);
								Vector3f bottomRight = new Vector3f(x[0] + dv[0], x[1] + dv[1], x[2] + dv[2]).add(translation);
								
								if (!quadMatrix.containsKey(mask[n].type)) quadMatrix.put(mask[n].type, new ArrayList<TexturedQuad>());
								if (!backFace)
									quadMatrix.get(mask[n].type).add(Quad.createTexturedQuad(topRight, topLeft, bottomRight, bottomLeft, 1, 1, mask[n].texture));
								else
									quadMatrix.get(mask[n].type).add(Quad.createTexturedQuad(topLeft, topRight, bottomLeft, bottomRight, 1, 1, mask[n].texture));
								
								for (l = 0; l < h; l++) {
									for (k = 0; k < w; k++) {
										mask[n+k+l*CHUNK_SIZE] = null;
									}
								}
								
								i += w;
								n += w;
							} else {
								i++;
								n++;
							}
						}
					}
				}
			}
		}
		
		for (List<TexturedQuad> quadList : quadMatrix.values()) {
			TexturedQuad[] tquads = quadList.toArray(new TexturedQuad[quadList.size()]);
			Quad[] quads = new Quad[tquads.length];
			for (i = 0; i < quads.length; i++) {
				quads[i] = tquads[i].getQuad();
			}
			models.add(MeshLoader.createModel(quads, tquads[0].getTexture()));
		}
	}
	
	BlockFace getBlockFace(int x, int y, int z, int side) {
		return BlockRegistry.getBlockFromId(blocks[x][y][z]).getFace(side);
    }
}
