package federation.input;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

import federation.graphics.Window;

public class InputHandler {
	
	private static boolean[] keys = new boolean[2048];
	private static boolean firstMouse = true;
	private static double mouseX, mouseY;
	private static double mouseDX, mouseDY;
	
	public static void setWindow(Window window) {
		glfwSetKeyCallback(window.id(), (windowId, key, scancode, action, mods) -> {
			if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
				glfwSetWindowShouldClose(windowId, true);
			if (action == GLFW_PRESS) keys[key] = true;
			if (action == GLFW_RELEASE) keys[key] = false;
		});
		
		glfwSetCursorPosCallback(window.id(), (windowId, xpos, ypos) -> {
			if (firstMouse) {
				mouseX = xpos;
				mouseY = ypos;
				firstMouse = false;
			}
			mouseDX = xpos - mouseX;
			mouseDY = ypos - mouseY;
			mouseX = xpos;
			mouseY = ypos;
		});
	}
	public static boolean isKeyDown(int key) {
		return keys[key];
	}
	
	public static double mouseX() {
		return mouseX;
	}
	
	public static double mouseY() {
		return mouseY;
	}
	
	public static double mouseDeltaX() {
		double mdx = mouseDX;
		mouseDX = 0;
		return mdx;
	}
	
	public static double mouseDeltaY() {
		double mdy = mouseDY;
		mouseDY = 0;
		return mdy;
	}
}
