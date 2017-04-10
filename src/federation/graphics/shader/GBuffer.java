package federation.graphics.shader;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import federation.core.Game;
import federation.util.Log;

public class GBuffer {

	private int gBuffer;
	
	private int gPosition;
	private int gNormal;
	private int gColorSpec;
	
	private int rboDepth;
	
	public GBuffer() {
		gBuffer = glGenFramebuffers();
		bind();
		
		// Position Buffer
		gPosition = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, gPosition);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB16F, Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT, 0, GL_RGB, GL_FLOAT, 0);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, gPosition, 0);
		
		// Normal Buffer
		gNormal = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, gNormal);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB16F, Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT, 0, GL_RGB, GL_FLOAT, 0);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT1, GL_TEXTURE_2D, gNormal, 0);
		
		// Diffuse + Specular Buffer
		gColorSpec = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, gColorSpec);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT, 0, GL_RGBA, GL_UNSIGNED_BYTE, 0);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT2, GL_TEXTURE_2D, gColorSpec, 0);
		
		IntBuffer attachments = BufferUtils.createIntBuffer(3);
		attachments.put(new int[] { GL_COLOR_ATTACHMENT0, GL_COLOR_ATTACHMENT1, GL_COLOR_ATTACHMENT2 });
		attachments.flip();
		glDrawBuffers(attachments);
		
		rboDepth = glGenRenderbuffers();
		glBindRenderbuffer(GL_RENDERBUFFER, rboDepth);
		glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);
		glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, rboDepth);
		
		if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
			Log.log(Log.FATAL, "Framebuffer not complete");
			throw new RuntimeException("Framebuffer not complete");
		}
		
		unbind();
	}
	
	public void bind() {
		glBindFramebuffer(GL_FRAMEBUFFER, gBuffer);
	}
	
	public void unbind() {
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
	}
	
	public void bindPositionTexture() {
        glBindTexture(GL_TEXTURE_2D, gPosition);
	}
	
	public void bindNormalTexture() {
        glBindTexture(GL_TEXTURE_2D, gNormal);
	}
	
	public void bindColorTexture() {
        glBindTexture(GL_TEXTURE_2D, gColorSpec);
	}
}
