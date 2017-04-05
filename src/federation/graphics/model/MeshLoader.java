package federation.graphics.model;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;

public class MeshLoader {
	
	private static List<Mesh> meshes;
	
	public static void init() {
		meshes = new ArrayList<Mesh>();
	}
	
	public static void dispose() {
		for (Mesh mesh : meshes) {
			mesh.delete();
		}
	}
	
	public static Mesh createMesh(float[] vertices, int[] indices) {
		VAO vao = new VAO();
		
		vao.bind();
		VBO vertexVBO, indexVBO;
		vertexVBO = storeAttribute(0, 3, vertices);
		indexVBO = storeIndices(indices);
		vao.addVBO(vertexVBO);
		vao.addVBO(indexVBO);
		vao.unbind();
		
		Mesh mesh = new Mesh(vao, indices.length);
		meshes.add(mesh);
		return mesh;
	}
	
	public static Mesh createMesh(float[] vertices, float[] texcoords, int[] indices) {
		VAO vao = new VAO();
		
		vao.bind();
		VBO vertexVBO, texVBO, indicesVBO;
		vertexVBO = storeAttribute(0, 3, vertices);
		texVBO = storeAttribute(1, 2, texcoords);
		indicesVBO = storeIndices(indices);
		vao.addVBO(vertexVBO);
		vao.addVBO(texVBO);
		vao.addVBO(indicesVBO);
		vao.unbind();
		
		Mesh mesh = new Mesh(vao, indices.length);
		meshes.add(mesh);
		return mesh;
	}
	
	
	
	private static VBO storeAttribute(int attributeId, int dataSize, float[] data) {
		VBO vbo = new VBO();
		vbo.bind(GL_ARRAY_BUFFER);
		vbo.bufferData(GL_ARRAY_BUFFER, convertToBuffer(data));
		glVertexAttribPointer(attributeId, dataSize, GL_FLOAT, false, 0, 0);
		vbo.unbind(GL_ARRAY_BUFFER);
		
		return vbo;
	}
	
	private static VBO storeIndices(int[] indices) {
		VBO vbo = new VBO();
		vbo.bind(GL_ELEMENT_ARRAY_BUFFER);
		vbo.bufferData(GL_ELEMENT_ARRAY_BUFFER, convertToBuffer(indices));
		
		return vbo;
	}
	
	private static FloatBuffer convertToBuffer(float[] array) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(array.length);
		buffer.put(array);
		buffer.flip();
		return buffer;
	}
	
	private static IntBuffer convertToBuffer(int[] array) {
		IntBuffer buffer = BufferUtils.createIntBuffer(array.length);
		buffer.put(array);
		buffer.flip();
		return buffer;
	}
}
