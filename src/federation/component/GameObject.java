package federation.component;

import federation.graphics.GraphicsComponent;
import federation.graphics.Renderer;
import federation.input.InputComponent;
import federation.physics.PhysicsComponent;
import federation.world.World;

public class GameObject {
	
	public float x, y;
	
	private InputComponent input;
	private PhysicsComponent physics;
	private GraphicsComponent graphics;
	
	public GameObject(InputComponent input, PhysicsComponent physics, GraphicsComponent graphics) {
		this.input = input;
		this.physics = physics;
		this.graphics = graphics;
	}
	
	public void update(World world, Renderer renderer) {
		if (input != null)
			input.update(this);
		if (physics != null)
			physics.update(this, world);
		if (graphics != null)
			graphics.update(this, renderer);
	}
}
