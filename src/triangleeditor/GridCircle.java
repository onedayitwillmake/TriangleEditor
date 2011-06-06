package triangleeditor;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import processing.core.PApplet;
import triangleeditor.physics.PhysicsController;

public class GridCircle {
	private Body	_body;
	private float	_radius;
	private PApplet app;
	public GridCircle( Body aBody, float aRadius, PApplet appRef ) {
		app = appRef;
		_body = aBody;
		_radius = aRadius;
	}
	
	public void draw( PhysicsController physicsController ) {
		
		Vec2 pos = physicsController.worldToScreen( _body.getPosition() );
		app.ellipseMode( PApplet.CENTER );
		app.ellipse( pos.x, pos.y, _radius*2, _radius*2 );
	}

	public Body get_body() { return _body; }
	public void set_body(Body _body) { this._body = _body; }
}
