package federation.core;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import federation.block.Blocks;
import federation.graphics.Renderer;
import federation.graphics.Window;
import federation.graphics.model.Mesh;
import federation.graphics.model.MeshLoader;
import federation.graphics.model.Quad;
import federation.input.InputHandler;
import federation.util.Log;
import federation.world.World;

public class Game {
	
	public final static double MS_PER_UPDATE = 50;
	private boolean running;
	private boolean initialized;
	
	private Window window;
	private Renderer renderer;
	private World world;
	
	public void init() throws IllegalStateException {
		if (initialized) {
			Log.log(Log.SEVERE, "Attempted to reinitialize game");
			throw new IllegalStateException("Attempted to reinitialize game");
		}
		Log.log(Log.INFO, "Initializing");
		
		if (!GLFW.glfwInit()) {
			Log.log(Log.FATAL, "GLFW failed to initialize");
			throw new IllegalStateException("GLFW failed to initialize");
		}
	
		window = new Window();
		window.init();
		InputHandler.setWindow(window);
		renderer = new Renderer();
		renderer.init();
		MeshLoader.init();
		Blocks.init();
		world = new World();
		
		initialized = true;
		Log.log(Log.INFO, "Finished initialization");
	}
	
	public void start() throws IllegalStateException {
		if (!initialized) {
			Log.log(Log.SEVERE, "Attempted to start game without initialization");
			throw new IllegalStateException("Game has not been initialized");
		}
		if (running) {
			Log.log(Log.SEVERE, "Attempted to start game while already running");
			throw new IllegalStateException("Game already running");
		}
		
		running = true;
		loop();
	}
	
	public void stop() {
		Log.log(Log.INFO, "Stopping");
		running = false;
		
		MeshLoader.dispose();
		renderer.dispose();
		window.dispose();
	}
	
	public void loop() {
		double lastExecution = System.currentTimeMillis();
		double elapsedTime = 0.0;
		
		while (running) {
			double currentTime = System.currentTimeMillis();
			double deltaTime = currentTime - lastExecution;
			lastExecution = currentTime;
			elapsedTime += deltaTime;
			
			input();
			
			while (elapsedTime >= MS_PER_UPDATE) {
				update();
				elapsedTime -= MS_PER_UPDATE;
			}
			
			render();
			
			if (glfwWindowShouldClose(window.id())) {
				stop();
			}
		}
	}
	
	public void input() {
		glfwPollEvents();
		
		world.input();
	}
	
	public void update() {
		world.update();
	}
	
	public void render() {
		glfwSwapBuffers(window.id());
		renderer.prepare();
		
		world.render(renderer);
	}
	
}
