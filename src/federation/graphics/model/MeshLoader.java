package federation.graphics.model;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import federation.graphics.texture.Texture;

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
	
	public static Mesh createMesh(float[] vertices, float[] texcoords, float[] normals, int[] indices) {
		VAO vao = new VAO();
		
		vao.bind();
		VBO vertexVBO, texVBO, normalsVBO, indicesVBO;
		vertexVBO = storeAttribute(0, 3, vertices);
		texVBO = storeAttribute(1, 2, texcoords);
		normalsVBO = storeAttribute(2, 3, normals);
		indicesVBO = storeIndices(indices);
		vao.addVBO(vertexVBO);
		vao.addVBO(texVBO);
		vao.addVBO(normalsVBO);
		vao.addVBO(indicesVBO);
		vao.unbind();
		
		Mesh mesh = new Mesh(vao, indices.length);
		meshes.add(mesh);
		return mesh;
	}
	
	public static Mesh createMesh(Quad quad) {
		return createMesh(new Quad[] {quad});
	}
	
	public static Mesh createMesh(Quad[] quads) {
		List<Vector3f> vertexList = new ArrayList<Vector3f>();
		List<Vector3f> normalList = new ArrayList<Vector3f>();
		List<Integer> indexList = new ArrayList<Integer>();
		float[] vertices = new float[quads.length * 12];	// Each quad has 4 vertices of 3 coordinates
		float[] texcoords = new float[quads.length * 8];	// Each quad has 4 vertices and 2 uv coords
		float[] normals = new float[quads.length * 12];		// Each quad has a Vector3f normal per vertex
		int[] indices = new int[quads.length * 6];			// Each quad has 6 indices
		
		for (Quad q : quads) {
			int v0, v1, v2, v3;
			vertexList.add(q.v0);
			v0 = vertexList.size()-1;
			vertexList.add(q.v1);
			v1 = vertexList.size()-1;
			vertexList.add(q.v2);
			v2 = vertexList.size()-1;
			vertexList.add(q.v3);
			v3 = vertexList.size()-1;
			
			normalList.add(q.n0);
			normalList.add(q.n0);
			normalList.add(q.n1);
			normalList.add(q.n1);
			
			indexList.add(v0);
			indexList.add(v1);
			indexList.add(v2);
			indexList.add(v2);
			indexList.add(v1);
			indexList.add(v3);
		}
		
		int i;
		for (i = 0; i < texcoords.length; i+=8) {
			Quad quad = quads[i/8];
			texcoords[i] = quad.textureCoords[0];
			texcoords[i+1] = quad.textureCoords[1];
			texcoords[i+2] = quad.textureCoords[2];
			texcoords[i+3] = quad.textureCoords[3];
			texcoords[i+4] = quad.textureCoords[4];
			texcoords[i+5] = quad.textureCoords[5];
			texcoords[i+6] = quad.textureCoords[6];
			texcoords[i+7] = quad.textureCoords[7];
		}
		
		for (i = 0; i < vertices.length; i+=3) {
			Vector3f v = vertexList.get(i/3);
			vertices[i] = v.x;
			vertices[i+1] = v.y;
			vertices[i+2] = v.z;
			Vector3f n = normalList.get(i/3);
			normals[i] = n.x;
			normals[i+1] = n.y;
			normals[i+2] = n.z;
		}
	 	
		for (i = 0; i < indices.length; i++) {
			indices[i] = indexList.get(i);
		}
		
		return createMesh(vertices, texcoords, normals, indices);
	}
	
	public static Model createModel(Quad quad, Texture texture) {
		return new Model(createMesh(quad), texture);
	}
	
	public static Model createModel(Quad[] quads, Texture texture) {
		return new Model(createMesh(quads), texture);
	}
	
	/*public static Mesh createMesh(Quad[] quads) {
		List<Vector3f> vertexList = new ArrayList<Vector3f>();
		List<Vector3f> normalList = new ArrayList<Vector3f>();
		List<Integer> indexList = new ArrayList<Integer>();
		
		// Create vertex and index lists
		// Remaps duplicate vertices and indices
		int v0, v1, v2, v3;
		for (Quad q : quads) {
			v0 = vertexList.indexOf(q.v0);
			if (v0 == -1) {
				vertexList.add(q.v0);
				v0 = vertexList.size() - 1;
			}
			v1 = vertexList.indexOf(q.v1);
			if (v1 == -1) {
				vertexList.add(q.v1);
				v1 = vertexList.size() - 1;
			}
			v2 = vertexList.indexOf(q.v2);
			if (v2 == -1) {
				vertexList.add(q.v2);
				v2 = vertexList.size() - 1;
			}
			v3 = vertexList.indexOf(q.v3);
			if (v3 == -1) {
				vertexList.add(q.v3);
				v3 = vertexList.size() - 1;
			}
			
			normalList.add(q.n0);
			normalList.add(q.n1);
			
			indexList.add(v0);
			indexList.add(v1);
			indexList.add(v2);
			indexList.add(v2);
			indexList.add(v1);
			indexList.add(v3);
		}
		
		float[] vertices = new float[vertexList.size() * 3];
		float[] normals = new float[normalList.size() * 3];
		int[] indices = new int[indexList.size()];
		
		for (int i = 0; i < vertices.length; i+=3) {
			Vector3f v = vertexList.get(i/3);
			vertices[i] = v.x;
			vertices[i+1] = v.y;
			vertices[i+2] = v.z;
		}
		for (int i = 0; i < normals.length; i+=3) {
			Vector3f n = normalList.get(i/3);
			normals[i] = n.x;
			normals[i+1] = n.y;
			normals[i+2] = n.z;
		}
		for (int i = 0; i < indices.length; i++) {
			indices[i] = indexList.get(i);
		}
		
		// TODO: add texcoords
		return createMesh(vertices, new float[] {}, normals, indices);
	}*/
	
	public static void deleteMesh(Mesh mesh) {
		if (meshes.remove(mesh)) {
			mesh.delete();
		}
	}
	
	public static void deleteMesh(Model model) {
		if (meshes.remove(model.getMesh())) {
			model.getMesh().delete();
		}
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
