package federation.graphics.model;

import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.util.ArrayList;
import java.util.List;

class VAO {
	
	private int vao;
	private List<VBO> vbos;
	
	public VAO() {
		vao = glGenVertexArrays();
		vbos = new ArrayList<VBO>();
	}
	
	public void addVBO(VBO vbo) {
		if (vbo != null)
			vbos.add(vbo);
	}
	
	public void bind() {
		glBindVertexArray(vao);
	}
	
	public void unbind() {
		glBindVertexArray(0);
	}
	
	public void delete() {
		for (VBO vbo : vbos) {
			vbo.delete();
		}
		glDeleteVertexArrays(vao);
		vao = 0;
	}
}
