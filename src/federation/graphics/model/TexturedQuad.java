package federation.graphics.model;

import federation.graphics.texture.Texture;

public class TexturedQuad {
	
	private Quad quad;
	private Texture texture;
	
	public TexturedQuad(Quad quad, Texture texture) {
		this.quad = quad;
		this.texture = texture;
	}
	
	public Quad getQuad() {
		return quad;
	}
	
	public Texture getTexture() {
		return texture;
	}
}
