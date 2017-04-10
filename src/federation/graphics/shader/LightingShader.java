package federation.graphics.shader;

import org.joml.Vector3f;

public class LightingShader extends ShaderProgram {
	
	private static final String VERTEX_SHADER = "res/shaders/light.vs";
	private static final String FRAGMENT_SHADER = "res/shaders/light.fs";
	
	public LightingShader() {
		super();
		
		VertexShader vs = new VertexShader(VERTEX_SHADER);
		FragmentShader fs = new FragmentShader(FRAGMENT_SHADER);
		attachShader(vs);
		attachShader(fs);
		link();
		bindAttribute(0, "position");
		bindAttribute(1, "texCoords");
		vs.delete();
		fs.delete();
		
		start();
		setUniform("gPosition", 0);
		setUniform("gNormal", 1);
		setUniform("gAlbedoSpec", 2);
		stop();
	}
	
	public void loadLight() {
		setUniform("lights[0].Position", new Vector3f(0, 0f, 0));
		setUniform("lights[0].Color", new Vector3f(1000.0f));
		setUniform("lights[0].Linear", 0.7f);
		setUniform("lights[0].Quadratic", 1.8f);
	}
	
	public void loadViewPos(Vector3f viewPos) {
		setUniform("viewPos", viewPos);
	}
}
