package federation.graphics.shader;

import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.*;

import java.nio.IntBuffer;

import org.lwjgl.system.MemoryStack;

import federation.util.Log;

public class ShaderProgram {
	
	private int program;
	
	public ShaderProgram() {
		program = glCreateProgram();
	}
	
	public void attachShader(Shader shader) {
		glAttachShader(program, shader.id());
	}
	
	public void link() {
		glLinkProgram(program);
		
		try (MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer success = stack.mallocInt(1);
			glGetProgramiv(program, GL_LINK_STATUS, success);
			
			if (success.get(0) != GL_TRUE) {
				Log.log(Log.FATAL, "Shader program link failed: \n" + glGetProgramInfoLog(program));
				throw new RuntimeException("Shader program failed to link");
			}
		}
	}
	
	public final int getAttribute(String var) {
		return glGetAttribLocation(program, var);
	}
	
	public final void bindAttribute(int attribute, String var) {
		glBindAttribLocation(program, attribute, var);
	}
	
	public void enableAttribute(int attribute) {
	    glEnableVertexAttribArray(attribute);  
	}
	
	public void disableAttribute(int attribute) {
		glDisableVertexAttribArray(attribute);
	}
	
	public void start() {
		glUseProgram(program);
	}
	
	public void stop() {
		glUseProgram(0);
	}
	
	public void delete() {
		stop();
		glDeleteProgram(program);
	}
}
