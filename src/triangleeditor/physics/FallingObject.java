package triangleeditor.physics;

import processing.core.*;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import processing.core.PApplet;

public class FallingObject implements ILevelObject {
	private Body	_body;
	private float	_radius;
	private int		_type;		// Can be rectangle or circle
	private int _color;
	
	public static int RECTANGLE = 0;
	public static int CIRCLE = 1;
	
	private PApplet app;
	public FallingObject( Body aBody, float aRadius, int type, int i, PApplet appRef ) {
		app = appRef;
		_type = type;
		_body = aBody;
		_radius = aRadius;
		
		_color = i;
	}
	
	/**
	 * Draws this object. Uses PhysicsController to convert to screen coordinates
	 * @param physicsController
	 */
	public void draw( PhysicsController physicsController ) {
		Vec2 pos = physicsController.worldToScreen( _body.getPosition() );
		
		// Draw circle or square
		if(_type == FallingObject.CIRCLE) {
			app.fill( _color );
			app.ellipseMode( PApplet.CENTER );
			app.ellipse( pos.x, pos.y, _radius*2, _radius*2 );
		} else if( _type == FallingObject.RECTANGLE ) {
			app.rectMode( PApplet.CENTER );
			app.rect(pos.x, pos.y, _radius, _radius);
		}
		
	}


	public Body get_body() { return _body; }
	public void set_body(Body _body) { this._body = _body; }
}
