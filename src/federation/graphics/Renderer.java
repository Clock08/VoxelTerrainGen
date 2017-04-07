package federation.graphics;

import static org.lwjgl.opengl.GL11.*;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL;

import federation.graphics.model.Mesh;
import federation.graphics.shader.StaticShader;
import federation.util.Log;

public class Renderer {
	
	private StaticShader shaderProgram;
	private Camera camera;
	private Matrix4f model, view, projection;
	
	public void init() {
		GL.createCapabilities();
		Log.log(Log.INFO, "OpenGL " + glGetString(GL_VERSION));
		
		glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
		
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		glFrontFace(GL_CCW);
		//glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		
		shaderProgram = new StaticShader();
		camera = new Camera();
		
		projection = new Matrix4f();
		projection.setPerspective((float) Math.toRadians(30f), 640f/480f, 0.1f, 100);
	}
	
	public void prepare() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	public void draw(Mesh mesh) {
		shaderProgram.start();
		
		shaderProgram.loadViewMatrix(new Matrix4f().rotateXYZ(camera.rotation).translate(camera.pos));
		shaderProgram.loadProjectionMatrix(projection);
		shaderProgram.loadModelMatrix(new Matrix4f().identity());
		//Mesh mesh = entity.getMesh();
		//entity.getTexture().bind();
		//Matrix4f transformationMatrix = MathHelper.createTransformationMatrix(entity.getPos(), entity.getRotation(), entity.getScale());
		//shaderProgram.loadModelMatrix(transformationMatrix);
		//shaderProgram.loadColor(entity.getColor());
		
		mesh.bind();
		shaderProgram.enableAttribute(0);
		//shaderProgram.enableAttribute(1);
		//shaderProgram.enableAttribute(2);
		
		glDrawElements(GL_TRIANGLES, mesh.numVertices(), GL_UNSIGNED_INT, 0);
		
		shaderProgram.disableAttribute(0);
		//shaderProgram.disableAttribute(1);
		//shaderProgram.disableAttribute(2);
		//entity.getTexture().unbind();
		mesh.unbind();
		
		shaderProgram.stop();
	}
	
	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	
	public void dispose() {
		shaderProgram.delete();
	}
}

