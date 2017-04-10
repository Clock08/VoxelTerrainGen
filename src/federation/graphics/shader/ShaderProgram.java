package federation.graphics.shader;

import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetAttribLocation;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgramiv;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.logging.Level;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
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
	
	public int getUniform(String var) {
		return glGetUniformLocation(program, var);
	}
	
	public void setUniform(String var, Matrix4f matrix) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		matrix.get(buffer);
		glUniformMatrix4fv(getUniform(var), false, buffer);
	}
	
	public void setUniform(String var, float value) {
		glUniform1f(getUniform(var), value);
	}
	
	public void setUniform(String var, Vector3f value) {
		glUniform3f(getUniform(var), value.x, value.y, value.z);
	}
	
	public void setUniform(String var, Vector4f value) {
		glUniform4f(getUniform(var), value.x, value.y, value.z, value.w);
	}
	
	public void setUniform(String var, int value) {
		glUniform1i(getUniform(var), value);
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
