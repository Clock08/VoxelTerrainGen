package federation.client;

import org.joml.Vector3f;
import org.joml.Vector3i;

public class Player {
	
	public static final int VIEW_DISTANCE = 2;
	
	private Vector3f pos;
	private Vector3i chunkPos;
	
	public Player() {
		pos = new Vector3f();
		chunkPos = new Vector3i();
	}
	
	public Vector3f pos() {
		return new Vector3f(pos);
	}
	
	public Vector3i chunkPos() {
		return new Vector3i(chunkPos);
	}
	
}
