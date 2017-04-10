package federation.graphics.model;

import federation.graphics.texture.Texture;

public class Model {
	
	private Mesh mesh;
	private Texture texture;
	
	public Model(Mesh mesh, Texture texture) {
		this.mesh = mesh;
		this.texture = texture;
	}
	
	public Mesh getMesh() {
		return mesh;
	}
	
	public Texture getTexture() {
		return texture;
	}
}
