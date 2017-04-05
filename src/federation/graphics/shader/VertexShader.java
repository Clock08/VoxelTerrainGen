package federation.graphics.shader;

import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;;

public class VertexShader extends Shader {
	
	public VertexShader(String shaderPath) {
		super(GL_VERTEX_SHADER, shaderPath);
	}
}
