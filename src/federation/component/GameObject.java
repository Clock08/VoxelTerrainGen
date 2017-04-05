package federation.component;

import federation.graphics.GraphicsComponent;
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
	
	public void update(World world) {
		input.update(this);
		physics.update(this, world);
		graphics.update(this);
	}
}
