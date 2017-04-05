package federation.graphics.shader;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;

public class FragmentShader extends Shader{

	public FragmentShader(String shaderPath) {
		super(GL_FRAGMENT_SHADER, shaderPath);
	}
}
