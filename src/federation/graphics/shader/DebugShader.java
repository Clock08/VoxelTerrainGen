package federation.graphics.shader;

public class DebugShader extends ShaderProgram {
	
	private static final String VERTEX_SHADER = "res/shaders/debug.vs";
	private static final String FRAGMENT_SHADER = "res/shaders/debug.fs";
	
	public DebugShader() {
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
	}
	
}
