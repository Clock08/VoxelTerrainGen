package federation.graphics.texture;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;

import federation.util.Log;

public class TextureLoader {
	
	private static Map<String, Texture> textures;
	
	public static void init() {
		textures = new HashMap<String, Texture>();
	}
	
	public static void dispose() {
		for (Texture texture : textures.values()) {
			texture.delete();
		}
	}
	
	public static Texture getTexture(String path) {
		Texture texture = textures.get(path);
		
		if (texture == null) texture = createTexture(path);
		
		return texture;
	}
	
	private static Texture createTexture(String path) {
		IntBuffer w = BufferUtils.createIntBuffer(1);
		IntBuffer h = BufferUtils.createIntBuffer(1);
		IntBuffer comp = BufferUtils.createIntBuffer(1);
		
		ByteBuffer image = STBImage.stbi_load("res/textures/" + path, w, h, comp, 4);	// Force 4 channel (RGBA)
		if (image == null) {
			Log.log(Log.SEVERE, "Failed to load image at " + path);
			return null;
		}
		STBImage.stbi_image_free(image);
		
		Texture texture = new Texture(w.get(), h.get(), image);
		textures.put(path, texture);
		
		return texture;
	}
}
