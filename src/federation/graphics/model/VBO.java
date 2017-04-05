package federation.graphics.model;

import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

class VBO {
	
	private int vbo;
	
	public VBO() {
		vbo = glGenBuffers();
	}
	
	public void bind(int target) {
		glBindBuffer(target, vbo);
	}
	
	public void unbind(int target) {
		glBindBuffer(target, 0);
	}
	
	public void bufferData(int target, FloatBuffer data) {
		glBufferData(target, data, GL_STATIC_DRAW);
	}
	
	public void bufferData(int target, IntBuffer data) {
		glBufferData(target, data, GL_STATIC_DRAW);
	}
	
	public void delete() {
		glDeleteBuffers(vbo);
		vbo = 0;
	}
}
