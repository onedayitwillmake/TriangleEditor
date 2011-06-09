package triangleeditor.physics;
import org.jbox2d.dynamics.Body;



/**
 * Represents an object that can draw itself, and also has a b2Body
 * @author mariogonzalez
 *
 */
public interface ILevelObject {
	void draw( PhysicsController physicsController );
	// Set body
	void set_body(Body aBody);
	Body get_body();
	// Set properties
//	void set_sizeWidth( float aWidth );
//	void set_sizeHeight( float aWidth );
}
