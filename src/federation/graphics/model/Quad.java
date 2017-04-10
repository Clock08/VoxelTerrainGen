package federation.graphics.model;

import org.joml.Vector3f;

import federation.graphics.texture.Texture;

public class Quad {
	
	// TODO: add normals and texcoords
	
	static final int[] indices = {
			0, 1, 2,
			2, 1, 3
	};
	
	float[] textureCoords = {
			0.0f, 1.0f,
			0.0f, 0.0f,
			1.0f, 1.0f,
			1.0f, 0.0f
	};
	
	Vector3f v0, v1, v2, v3;
	Vector3f n0, n1;
	
	public Quad() {
		v0 = new Vector3f();
		v1 = new Vector3f();
		v2 = new Vector3f();
		v3 = new Vector3f();
	}
	
	public static Quad createQuad(Vector3f topLeft, Vector3f topRight, Vector3f bottomLeft, Vector3f bottomRight) {
		return createQuad(topLeft, topRight, bottomLeft, bottomRight, topRight.distance(topLeft), bottomLeft.distance(topLeft));
	}
	
	public static Quad createQuad(Vector3f topLeft, Vector3f topRight, Vector3f bottomLeft, Vector3f bottomRight, float texWidth, float texHeight) {
		Quad quad = new Quad();
		
		quad.v0 = topLeft;
		quad.v1 = bottomLeft;
		quad.v2 = topRight;
		quad.v3 = bottomRight;
		
		float width = topRight.distance(topLeft);
		float height = bottomLeft.distance(topLeft);
		float wRatio = Math.abs(width/texWidth);
		float hRatio = Math.abs(height/texHeight);
		quad.textureCoords[0] *= wRatio;
		quad.textureCoords[1] *= hRatio;
		quad.textureCoords[2] *= wRatio;
		quad.textureCoords[3] *= hRatio;
		quad.textureCoords[4] *= wRatio;
		quad.textureCoords[5] *= hRatio;
		quad.textureCoords[6] *= wRatio;
		quad.textureCoords[7] *= hRatio;
		
		quad.n0 = new Vector3f().add(bottomLeft).sub(topLeft).cross(new Vector3f().add(topRight).sub(topLeft));
		quad.n1 = new Vector3f().add(topRight).sub(bottomRight).cross(new Vector3f().add(bottomLeft).sub(bottomRight));
		
		return quad;
	}
	
	public static TexturedQuad createTexturedQuad(Vector3f topLeft, Vector3f topRight, Vector3f bottomLeft, Vector3f bottomRight, Texture texture) {
		return new TexturedQuad(createQuad(topLeft, topRight, bottomLeft, bottomRight), texture);
	}
	
	public static TexturedQuad createTexturedQuad(Vector3f topLeft, Vector3f topRight, Vector3f bottomLeft, Vector3f bottomRight, float texWidth, float texHeight, Texture texture) {
		return new TexturedQuad(createQuad(topLeft, topRight, bottomLeft, bottomRight, texWidth, texHeight), texture);
	}
}
