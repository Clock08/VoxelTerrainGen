package federation.physics;

import federation.component.GameObject;
import federation.world.World;

public abstract class PhysicsComponent {
	
	public abstract void update(GameObject gameObject, World world);
	
}
