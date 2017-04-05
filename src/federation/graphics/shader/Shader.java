package federation.graphics.shader;

import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderiv;
import static org.lwjgl.opengl.GL20.glShaderSource;

import java.nio.IntBuffer;

import org.lwjgl.system.MemoryStack;

import federation.util.FileIO;
import federation.util.Log;

public class Shader {

	private int shader;
	
	public Shader(int type, String shaderPath) {
		shader = glCreateShader(type);
		String source = FileIO.getText(shaderPath);
		if (source == null) {
			Log.log(Log.FATAL, "Failed to locate shader source at " + shaderPath);
			throw new RuntimeException("Shader source could not be located");
		}
		glShaderSource(shader, source);
		glCompileShader(shader);
		
		try (MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer success = stack.mallocInt(1);
			glGetShaderiv(shader, GL_COMPILE_STATUS, success);
			
			if (success.get(0) != GL_TRUE) {
				Log.log(Log.FATAL, "Shader compilation failed: \n" + glGetShaderInfoLog(shader));
				throw new RuntimeException("Shader failed to compile");
			}
		}
	}
	
	public int id() {
		return shader;
	}
	
	public void delete() {
		glDeleteShader(shader);
		shader = 0;
	}
}
