package federation.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Vector3i;

import federation.block.Block;
import federation.client.Player;
import federation.graphics.Renderer;
import federation.graphics.model.Mesh;
import federation.graphics.model.Model;
import federation.terrain.Terrain;

public class World {
	
	public static final int NORTH	= 0;
	public static final int SOUTH	= 1;
	public static final int EAST	= 2;
	public static final int WEST	= 3;
	public static final int TOP		= 4;
	public static final int BOTTOM	= 5;
	
	Player player;
	private static final int LOAD_DISTANCE = 4;
	
	Terrain terrain;
	Map<Vector3i, Chunk> chunks;
	List<Chunk> chunkUpdateList, chunkLoadList, chunkBuildList, chunkRenderList, chunkUnloadList;
	
	public World() {
		terrain = new Terrain(0);
		chunks = new HashMap<Vector3i, Chunk>();
		chunkUpdateList = new ArrayList<Chunk>();
		chunkLoadList = new ArrayList<Chunk>();
		chunkBuildList = new ArrayList<Chunk>();
		chunkRenderList = new ArrayList<Chunk>();
		chunkUnloadList = new ArrayList<Chunk>();
		
		player = new Player();
		
		chunkUpdateList.add(createChunk(player.chunkPos()));
	}
	
	public void input() {
		player.handleInput();
	}
	
	public void update() {
		chunkLoadList.clear();
		chunkBuildList.clear();
		chunkUnloadList.clear();
		
		Chunk[] chunks = chunkUpdateList.toArray(new Chunk[chunkUpdateList.size()]);
		for (Chunk c : chunks)
			updateChunk(c);
		for (Chunk c : chunkLoadList) 
			loadChunk(c);
		for (Chunk c : chunkBuildList)
			buildChunk(c);
		for (Chunk c : chunkUnloadList)
			unloadChunk(c);
	}
	
	public void render(Renderer renderer) {
		renderer.setCamera(player.camera());
		
		for (Chunk c : chunkRenderList) {
			for (Model m : c.getModels()) renderer.draw(m);
		}
	}
	
	
	
	private void updateChunk(Chunk chunk) {
		double dist = player.chunkPos().distance(chunk.pos());
		if (Math.abs(dist) > LOAD_DISTANCE) {
			chunkUnloadList.add(chunk);
			return;
		}
		
		if (!chunk.isLoaded()) {
			chunkLoadList.add(chunk);
		}
		
		if (chunk.isLoaded() && chunk.isDirty()) {
			chunkBuildList.add(chunk);
			chunkRenderList.remove(chunk);
		}
		
		if (chunk.numNeighbors() != 6) {
			Vector3i northPos = chunk.pos().add(0, 0, -1);
			Vector3i southPos = chunk.pos().add(0, 0, 1);
			Vector3i eastPos = chunk.pos().add(1, 0, 0);
			Vector3i westPos = chunk.pos().add(-1, 0, 0);
			Vector3i topPos = chunk.pos().add(0, 1, 0);
			Vector3i bottomPos = chunk.pos().add(0, -1, 0);
			
			if (Math.abs(northPos.distance(player.chunkPos())) <= LOAD_DISTANCE) {
				if (chunk.getNorthNeighbor() == null) {
					Chunk north = getChunk(northPos);
					north.setSouthNeighbor(chunk);
					chunk.setNorthNeighbor(north);
					if (!chunkUpdateList.contains(north)) chunkUpdateList.add(north);
				}
			}
			if (Math.abs(southPos.distance(player.chunkPos())) <= LOAD_DISTANCE) {
				if (chunk.getSouthNeighbor() == null) {
					Chunk south = getChunk(southPos);
					south.setNorthNeighbor(chunk);
					chunk.setSouthNeighbor(south);
					if (!chunkUpdateList.contains(south)) chunkUpdateList.add(south);
				}
			}
			if (Math.abs(eastPos.distance(player.chunkPos())) <= LOAD_DISTANCE) {
				if (chunk.getEastNeighbor() == null) {
					Chunk east = getChunk(eastPos);
					east.setWestNeighbor(chunk);
					chunk.setEastNeighbor(east);
					if (!chunkUpdateList.contains(east)) chunkUpdateList.add(east);
				}
			}
			if (Math.abs(westPos.distance(player.chunkPos())) <= LOAD_DISTANCE) {
				if (chunk.getWestNeighbor() == null) {
					Chunk west = getChunk(westPos);
					west.setEastNeighbor(chunk);
					chunk.setWestNeighbor(west);
					if (!chunkUpdateList.contains(west)) chunkUpdateList.add(west);
				}
			}
			if (Math.abs(topPos.distance(player.chunkPos())) <= LOAD_DISTANCE) {
				if (chunk.getTopNeighbor() == null) {
					Chunk top = getChunk(topPos);
					top.setBottomNeighbor(chunk);
					chunk.setTopNeighbor(top);
					if (!chunkUpdateList.contains(top)) chunkUpdateList.add(top);
				}
			}
			if (Math.abs(bottomPos.distance(player.chunkPos())) <= LOAD_DISTANCE) {
				if (chunk.getBottomNeighbor() == null) {
					Chunk bottom = getChunk(bottomPos);
					bottom.setTopNeighbor(chunk);
					chunk.setBottomNeighbor(bottom);
					if (!chunkUpdateList.contains(bottom)) chunkUpdateList.add(bottom);
				}
			}
		}
	}
	
	private void loadChunk(Chunk chunk) {
		chunk.load(terrain);
	}
	
	private void buildChunk(Chunk chunk) {
		chunk.build();
		chunkRenderList.add(chunk);
	}
	
	private void unloadChunk(Chunk chunk) {
		chunkRenderList.remove(chunk);
		chunkUpdateList.remove(chunk);
		
		chunk.unload();
	}
	
	private Chunk getChunk(Vector3i chunkPos) {
		Chunk chunk = chunks.get(chunkPos);
		
		if (chunk == null) chunk = createChunk(chunkPos);
		
		return chunk;
	}
	
	private Chunk createChunk(Vector3i chunkPos) {
		Chunk chunk = null; //Chunk.loadFromFile(chunkPos);
		
		if (chunk == null) {
			chunk = new Chunk(chunkPos);
		}
		
		chunks.put(chunkPos, chunk);
		return chunk;
	}
	
	public Block getBlockAt(int x, int y, int z) {
		return getChunk(new Vector3i(x / Chunk.CHUNK_SIZE, y / Chunk.CHUNK_SIZE, z / Chunk.CHUNK_SIZE))
		.getBlockAt(x % Chunk.CHUNK_SIZE, y % Chunk.CHUNK_SIZE, z % Chunk.CHUNK_SIZE);
	}
	
}
