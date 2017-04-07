package federation.input;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

import org.lwjgl.glfw.GLFWKeyCallback;

import federation.graphics.Window;

public class InputHandler {
	
	private static boolean[] keys = new boolean[2048];
	
	public static void setWindow(Window window) {
		glfwSetKeyCallback(window.id(), (windowId, key, scancode, action, mods) -> {
			if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
				glfwSetWindowShouldClose(windowId, true);
			if (action == GLFW_PRESS) keys[key] = true;
			if (action == GLFW_RELEASE) keys[key] = false;
		});
	}
	
	public static boolean isKeyDown(int key) {
		return keys[key];
	}
}
