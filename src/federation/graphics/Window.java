package federation.graphics;

import org.lwjgl.glfw.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import federation.util.Log;

public class Window {
	
	private long window;
	int width, height;
	
	public Window(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public void init() {
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 0);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		
		window = glfwCreateWindow(1200, 720, "Federation", NULL, NULL);
		if (window == NULL) {
			glfwTerminate();
			Log.log(Log.FATAL, "Failed to create a GLFW window");
			throw new RuntimeException("Failed to create a GLFW window");
		}
		
		glfwMakeContextCurrent(window);
		
		try (MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);
			//IntBuffer bWidth = stack.mallocInt(1);
			//IntBuffer bHeight = stack.mallocInt(1);
			
			glfwGetWindowSize(window, pWidth, pHeight);

			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
			
			//glfwGetFramebufferSize(window, bWidth, bHeight);
			
			//GL11.glViewport(0, 0, bWidth.get(0), bHeight.get(0));
		}
		
		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);	// Enable v-sync

		glfwShowWindow(window);
	}
	
	public void dispose() {
		glfwTerminate();
	}
	
	public long id() {
		return window;
	}
}
