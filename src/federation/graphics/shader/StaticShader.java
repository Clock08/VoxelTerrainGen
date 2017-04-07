package federation.graphics.shader;

import org.joml.Matrix4f;

public class StaticShader extends ShaderProgram {

	private static final String VERTEX_SHADER = "res/shaders/main.vs";
	private static final String FRAGMENT_SHADER = "res/shaders/main.fs";
	
	public StaticShader() {
		super();
		
		VertexShader vs = new VertexShader(VERTEX_SHADER);
		FragmentShader fs = new FragmentShader(FRAGMENT_SHADER);
		attachShader(vs);
		attachShader(fs);
		link();
		bindAttribute(0, "position");
		vs.delete();
		fs.delete();
	}
	
	public void loadModelMatrix(Matrix4f modelMatrix) {
		setUniform("model", modelMatrix);
	}
	
	public void loadViewMatrix(Matrix4f viewMatrix) {
		setUniform("view", viewMatrix);
	}
	
	public void loadProjectionMatrix(Matrix4f projMatrix) {
		setUniform("projection", projMatrix);
	}
	
}
