package federation.graphics;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {

	public Vector3f pos;
	public Vector3f rotation;
	private Matrix4f viewMatrix;
	
	public Camera() {
		pos = new Vector3f();
		rotation = new Vector3f();
		viewMatrix = new Matrix4f();
	}
	
	public Matrix4f viewMatrix() {
		viewMatrix.identity();
		viewMatrix.translate(pos);
		return viewMatrix;
	}
}
