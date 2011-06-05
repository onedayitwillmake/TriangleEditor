package triangleeditor;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.joints.PrismaticJoint;
import org.jbox2d.p5.Physics;

import processing.core.*;
public class JBox2DTest extends PApplet {
	float	_elapsedFrames;
	Physics	physics;
	private Body body, body2;
	
	private boolean set;
	private int count = 0;
	
	public void setup() {
		_elapsedFrames = 0;
		
		size(1200, 600);
		frameRate(30);
		
		setupPhysics();
		background(0);
	}
	
	
	public void setupPhysics() {
		physics = new Physics(this, width-5, height-5);
		physics.setDensity(1.0f);
		Body b1 = null;
		Body b2 = null;
		
		// Make a chain of bodies
		for (int i=0; i<50; ++i) {
			body = body2; //bookkeeping, for neighbor connection
			
			body2 = physics.createRect(100+25*i, 10, 120+25*i, 30);
			
			// Add a hanging thingy to each body, connect it
			// with a prismatic joint (like a piston)
			Body body3 = physics.createCircle(110+25*i,35,5.0f);
			PrismaticJoint pj = physics.createPrismaticJoint(body2, body3, 0.0f, 1.0f);
			pj.m_enableLimit = true;
			pj.setLimits(-3.0f, 1.0f);
			
			if (i==0) b1 = body2; // for pulley joint later
			if (i==9) b2 = body2;
			
			if (body == null) {
				// No previous body, so continue without adding joint
				body = body2;
				continue;
			}
			// Connect the neighbors
			physics.createRevoluteJoint(body, body2, 100+25*i, 20);
			
		}
		
		// Make a pulley joint
		float groundAnchorAx = 100;
		float groundAnchorAy = 150;
		float groundAnchorBx = 860;
		float groundAnchorBy = 150;
		float anchorAx = 100;
		float anchorAy = 20;
		float anchorBx = 345;
		float anchorBy = 20;
		float ratio = 1.0f;
		
		physics.createPulleyJoint(b1, b2, 
				groundAnchorAx, groundAnchorAy, 
				groundAnchorBx, groundAnchorBy, 
				anchorAx, anchorAy, 
				anchorBx, anchorBy, ratio);
		set = true;
	}
	
	public void draw() {
		++_elapsedFrames;
		fill( random(255) );
		background(0);
//        rect(0,0, width, height);
        
        
	}
	
	/* (non-Javadoc)
	 * @see processing.core.PApplet#mouseDragged()
	 */
	@Override
	public void mouseDragged() {
		super.mouseDragged();
	}

	/* (non-Javadoc)
	 * @see processing.core.PApplet#mouseMoved()
	 */
	@Override
	public void mouseMoved() {
		super.mouseMoved();
	}

	/* (non-Javadoc)
	 * @see processing.core.PApplet#mousePressed()
	 */
	@Override
	public void mousePressed() {
		super.mousePressed();
	}
	
	/* (non-Javadoc)
	 * @see processing.core.PApplet#mouseReleased()
	 */
	@Override
	public void mouseReleased() {
		super.mouseReleased();
	}
	
	

	//
//	public static void main(String args[]) {
//		PApplet.main(new String[] { "--present", "JBox2DTest" });
//	}
}
