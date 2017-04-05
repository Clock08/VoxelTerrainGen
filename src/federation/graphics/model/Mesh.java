package federation.graphics.model;

public class Mesh {
	
	private VAO vao;
	private int numVertices;
	
	public Mesh(VAO vao, int numVertices) {
		this.vao = vao;
		this.numVertices = numVertices;
	}
	
	public void bind() {
		vao.bind();
	}
	
	public void unbind() {
		vao.unbind();
	}
	
	public int numVertices() {
		return numVertices;
	}
	
	public void delete() {
		vao.delete();
	}
}
