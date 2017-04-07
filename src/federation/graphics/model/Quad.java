package federation.graphics.model;

import org.joml.Vector3f;

public class Quad {
	
	// TODO: add normals and texcoords
	
	static final int[] indices = {
			0, 1, 2,
			2, 1, 3
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
		Quad quad = new Quad();
		
		quad.v0 = topLeft;
		quad.v1 = bottomLeft;
		quad.v2 = topRight;
		quad.v3 = bottomRight;
		
		quad.n0 = new Vector3f().add(bottomLeft).sub(topLeft).cross(new Vector3f().add(topRight).sub(topLeft));
		quad.n1 = new Vector3f().add(topRight).sub(bottomRight).cross(new Vector3f().add(bottomLeft).sub(bottomRight));
		
		return quad;
	}
}
