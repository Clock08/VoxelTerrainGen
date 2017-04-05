package federation.graphics;

import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_CCW;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glFrontFace;
import static org.lwjgl.opengl.GL11.glGetString;

import org.lwjgl.opengl.GL;

import federation.graphics.model.Mesh;
import federation.graphics.shader.ShaderProgram;
import federation.graphics.shader.StaticShader;
import federation.util.Log;

public class Renderer {
	
	ShaderProgram shaderProgram;
	
	public void init() {
		GL.createCapabilities();
		Log.log(Log.INFO, "OpenGL " + glGetString(GL_VERSION));
		
		glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		glFrontFace(GL_CCW);
		
		shaderProgram = new StaticShader();
	}
	
	public void dispose() {
		shaderProgram.delete();
	}
	
	public void prepare() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	public void render(Mesh mesh) {
		shaderProgram.start();
		
		//shaderProgram.loadViewMatrix(camera);
		//shaderProgram.loadProjectionMatrix(projectionMatrix);
		//Mesh mesh = entity.getMesh();
		//entity.getTexture().bind();
		//Matrix4f transformationMatrix = MathHelper.createTransformationMatrix(entity.getPos(), entity.getRotation(), entity.getScale());
		//shaderProgram.loadModelMatrix(transformationMatrix);
		//shaderProgram.loadColor(entity.getColor());
		
		mesh.bind();
		shaderProgram.enableAttribute(0);
		//shaderProgram.enableAttribute(1);
		
		glDrawElements(GL_TRIANGLES, mesh.numVertices(), GL_UNSIGNED_INT, 0);
		
		shaderProgram.disableAttribute(0);
		//shaderProgram.disableAttribute(1);
		//entity.getTexture().unbind();
		mesh.unbind();
		
		shaderProgram.stop();
	}
}
