package federation.client;

import org.joml.Vector3f;
import org.joml.Vector3i;
import org.lwjgl.glfw.GLFW;

import federation.core.Game;
import federation.graphics.Camera;
import federation.input.InputHandler;

public class Player {
	
	public static final int VIEW_DISTANCE = 2;
	
	private Vector3f pos;
	private Vector3i chunkPos;
	private Camera camera;
	
	public Player() {
		pos = new Vector3f();
		chunkPos = new Vector3i();
		camera = new Camera();
	}
	
	public Vector3f pos() {
		return new Vector3f(pos);
	}
	
	public Vector3i chunkPos() {
		return new Vector3i(chunkPos);
	}
	
	public Camera camera() {
		return camera;
	}
	
	public void handleInput() {
		float speed = 2;		// m/s
		float rotSpeed = 0.5f;	// rad/s
		
		if (InputHandler.isKeyDown(GLFW.GLFW_KEY_W)) {
			camera.pos.z += speed / 1000 * Game.MS_PER_UPDATE * Math.cos(camera.rotation.y);
			camera.pos.x -= speed / 1000 * Game.MS_PER_UPDATE * Math.sin(camera.rotation.y);
		}
		if (InputHandler.isKeyDown(GLFW.GLFW_KEY_S)) {
			camera.pos.z -= speed / 1000 * Game.MS_PER_UPDATE * Math.cos(camera.rotation.y);
			camera.pos.x += speed / 1000 * Game.MS_PER_UPDATE * Math.sin(camera.rotation.y);
		}
		if (InputHandler.isKeyDown(GLFW.GLFW_KEY_A)) {
			camera.pos.z += speed / 1000 * Game.MS_PER_UPDATE * Math.sin(camera.rotation.y);
			camera.pos.x += speed / 1000 * Game.MS_PER_UPDATE * Math.cos(camera.rotation.y);
		}
		if (InputHandler.isKeyDown(GLFW.GLFW_KEY_D)) {
			camera.pos.z -= speed / 1000 * Game.MS_PER_UPDATE * Math.sin(camera.rotation.y);
			camera.pos.x -= speed / 1000 * Game.MS_PER_UPDATE * Math.cos(camera.rotation.y);
		}
		if (InputHandler.isKeyDown(GLFW.GLFW_KEY_SPACE)) {
			camera.pos.y -= speed / 1000 * Game.MS_PER_UPDATE;
		}
		if (InputHandler.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) {
			camera.pos.y += speed / 1000 * Game.MS_PER_UPDATE;
		}
		
		if (InputHandler.isKeyDown(GLFW.GLFW_KEY_UP)) {
			camera.rotation.x -= rotSpeed / 1000 * Game.MS_PER_UPDATE;
		}
		if (InputHandler.isKeyDown(GLFW.GLFW_KEY_DOWN)) {
			camera.rotation.x += rotSpeed / 1000 * Game.MS_PER_UPDATE;
		}
		if (InputHandler.isKeyDown(GLFW.GLFW_KEY_LEFT)) {
			camera.rotation.y -= rotSpeed / 1000 * Game.MS_PER_UPDATE;
		}
		if (InputHandler.isKeyDown(GLFW.GLFW_KEY_RIGHT)) {
			camera.rotation.y += rotSpeed / 1000 * Game.MS_PER_UPDATE;
		}
	}
	
}
