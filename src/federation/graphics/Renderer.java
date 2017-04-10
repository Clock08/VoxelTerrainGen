package federation.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;

import federation.core.Game;
import federation.graphics.model.Mesh;
import federation.graphics.model.MeshLoader;
import federation.graphics.model.Model;
import federation.graphics.model.Quad;
import federation.graphics.shader.DebugShader;
import federation.graphics.shader.GBuffer;
import federation.graphics.shader.GeometryShader;
import federation.graphics.shader.LightingShader;
import federation.graphics.texture.TextureLoader;
import federation.util.Log;

public class Renderer {
	
	private GBuffer gBuffer;
	private GeometryShader geometryShader;
	private LightingShader lightShader;
	private DebugShader debugShader;
	private Mesh quad, debugQuad;
	
	private Camera camera;
	private Matrix4f projection;
	
	public void init() {
		GL.createCapabilities();
		Log.log(Log.INFO, "OpenGL " + glGetString(GL_VERSION));
		
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		glFrontFace(GL_CCW);
		//glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		
		gBuffer = new GBuffer();
		geometryShader = new GeometryShader();
		lightShader = new LightingShader();
		debugShader = new DebugShader();
		
		Quad q = Quad.createQuad(new Vector3f(-1, 1, 0), new Vector3f(1, 1, 0), new Vector3f(-1, -1, 0), new Vector3f(1, -1, 0));
		quad = MeshLoader.createMesh(q);
		q = Quad.createQuad(new Vector3f(0.25f, 1, 0), new Vector3f(1, 1, 0), new Vector3f(0.25f, 0.25f, 0), new Vector3f(1, 0.25f, 0));
		debugQuad = MeshLoader.createMesh(q);
		
		camera = new Camera();
		
		projection = new Matrix4f();
		projection.setPerspective((float) Math.toRadians(30f), (float)Game.SCREEN_WIDTH/Game.SCREEN_HEIGHT, 0.1f, 1000);
	}
	
	public void prepareGeometryPass() {
		gBuffer.bind();
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		geometryShader.start();
		//TextureLoader.getTexture("/blocks/blockStone.png").bind();
	}
	
	public void draw(Model model) {
		geometryShader.loadViewMatrix(new Matrix4f().rotateXYZ(camera.rotation).translate(camera.pos));
		geometryShader.loadProjectionMatrix(projection);
		geometryShader.loadModelMatrix(new Matrix4f().identity());
		
		model.getTexture().bind();
		model.getMesh().bind();
		geometryShader.enableAttribute(0);
		geometryShader.enableAttribute(1);
		geometryShader.enableAttribute(2);
		
		glDrawElements(GL_TRIANGLES, model.getMesh().numVertices(), GL_UNSIGNED_INT, 0);
		
		geometryShader.disableAttribute(0);
		geometryShader.disableAttribute(1);
		geometryShader.disableAttribute(2);
		model.getMesh().unbind();
		model.getTexture().unbind();
	}
	
	public void endGeometryPass() {
		geometryShader.stop();
		gBuffer.unbind();
	}
	
	public void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		lightShader.start();
		
		glActiveTexture(GL_TEXTURE0);
		gBuffer.bindPositionTexture();
		glActiveTexture(GL_TEXTURE1);
		gBuffer.bindNormalTexture();
		glActiveTexture(GL_TEXTURE2);
		gBuffer.bindColorTexture();
		lightShader.loadViewPos(camera.pos);
		
		renderQuad();
		
		lightShader.stop();
	}
	
	private void renderQuad() {
		quad.bind();
		lightShader.enableAttribute(0);
		lightShader.enableAttribute(1);
		lightShader.enableAttribute(2);
		glDrawElements(GL_TRIANGLES, quad.numVertices(), GL_UNSIGNED_INT, 0);
		lightShader.disableAttribute(0);
		lightShader.disableAttribute(1);
		lightShader.disableAttribute(2);
		quad.unbind();
	}
	
	public void renderDebug() {
		glClear(GL_DEPTH_BUFFER_BIT);
		debugShader.start();
		
		glActiveTexture(GL_TEXTURE0);
		gBuffer.bindColorTexture();
		debugQuad.bind();
		debugShader.enableAttribute(0);
		debugShader.enableAttribute(1);
		debugShader.enableAttribute(2);
		glDrawElements(GL_TRIANGLES, debugQuad.numVertices(), GL_UNSIGNED_INT, 0);
		debugShader.disableAttribute(0);
		debugShader.disableAttribute(1);
		debugShader.enableAttribute(2);
		debugQuad.unbind();
		
		debugShader.stop();
	}
	
	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	
	public void dispose() {
		//gBuffer.delete();
		geometryShader.delete();
		lightShader.delete();
	}
}

