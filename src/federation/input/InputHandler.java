package federation.input;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

import org.lwjgl.glfw.GLFWKeyCallback;

import federation.graphics.Window;

public class InputHandler {
	
	GLFWKeyCallback keyCallback;
	
	public InputHandler(Window window) {
		glfwSetKeyCallback(window.id(), (windowId, key, scancode, action, mods) -> {
			if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
				glfwSetWindowShouldClose(windowId, true); // We will detect this in the rendering loop
		});
	}
}
