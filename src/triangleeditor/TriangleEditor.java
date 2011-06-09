package triangleeditor;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.joints.DistanceJointDef;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

import processing.core.*;
import triangleeditor.gui.GuiController;
import triangleeditor.model.GridModel;
import triangleeditor.model.LevelModel;
import triangleeditor.physics.FallingObject;
import triangleeditor.physics.PhysicsController;


public class TriangleEditor extends PApplet {
	private static final long serialVersionUID = -3824555102005090780L;
	
	private float				_elapsedFrames;
	private GridModel			_gridModel;
	private LevelModel			_levelModel;
	
	private PhysicsController	_physicsController;
	private GuiController		_guiController;
		
	// States
	public Boolean isSmoothing = false;
	public void setup() {
		_elapsedFrames = 0;
		
		size(1200, 600);
		frameRate(60);
		background(0);
		
		
		
		setupPhysicsController();
		setupLevelModel();
		setupGrid();
		setupControls();
		
		RevoluteJointDef jd = new RevoluteJointDef();
		jd.collideConnected = false;

		Body prevBody = null;
		for( int i = 0; i < 800; ++i ) {
			FallingObject fallingCircle = addCircle( random(width), 10, random(3, 9) );
		}
	}
	
	
	private void setupControls() {
		_guiController = new GuiController( this );
		_guiController.toggle();
	}

	public void setupGrid() {
		_gridModel = new GridModel(width, height, 50 , this);
	}
	
	public void setupLevelModel() {
		_levelModel = new LevelModel();
	}
	
	public void setupPhysicsController() {
		_physicsController = new PhysicsController( this );
		_physicsController.createWorld(width, height, 0, 20, width*2, height*2, width, height, 20);
//		_physicsController.createHollowBox(width * 0.5f, height*.5f, width, height, 10.0f);
		
		_physicsController.m_density = 1;
		_physicsController.m_restitution = 0.5f;
	}
	
	@SuppressWarnings("unused")
	public void draw() {
		++_elapsedFrames;
		background( 255 );
		noStroke();
		drawGrid();
		
		_physicsController.update();
//		_physicsController.draw();
		
		for( FallingObject gridCircle : _levelModel.get_fallingObjects() ) {
			
		}
		
		// Reset circles
		for( FallingObject gridCircle : _levelModel.get_fallingObjects() ) {
			Body body = gridCircle.get_body();
			Vec2 pos = _physicsController.worldToScreen( body.getPosition() );
			if( pos.y > height ) {
				Vec2 randomPosition = _physicsController.screenToWorld( random(width), 0);
				body.setXForm( randomPosition, 0);
				body.setLinearVelocity( new Vec2(0.0f, 0.0f) );
			}
			
			gridCircle.draw( _physicsController );
		}
	}
	
	
	/**
	 * Calls draw on all GridSquares
	 */
	public void drawGrid() {
		for (GridSquare square : _gridModel.get_gridSquareList() ) {
			square.draw( _physicsController );
		}
	}
	
	
	
	/* (non-Javadoc)
	 * @see processing.core.PApplet#mouseDragged()
	 */
	@Override
	public void mouseDragged() {
		super.mouseDragged();
		
		if( _guiController != null ) {
			if( _guiController.get_isActive() ) {
				_guiController.set_activeTriangle( _gridModel.getTriangleAtPosition(mouseX, mouseY) );
			} else {
				handleSquares();
				//createTriangleAtMouse();
			}
		}
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

		if( _guiController != null ) {
			if( _guiController.get_isActive() ) {
				_guiController.set_activeTriangle( _gridModel.getTriangleAtPosition(mouseX, mouseY) );
			} else { 
				//createTriangleAtMouse();
				handleSquares();
			}
		}
	}
	
	@Override
	public void keyPressed() {
		
		if (key == CODED && keyCode == UP && _guiController != null) {
			_guiController.toggle();
		}
			
		super.keyPressed();
	}


	private void createTriangleAtMouse(){
		GridTriangle triangle = _gridModel.getTriangleAtPosition( mouseX, mouseY );
		
		if( triangle == null ) return;
		
		createTriangleBody( triangle );
		
		if(_guiController != null) {
			_guiController.set_activeTriangle( triangle );
		}
	}
	
	/**
	 * Creates a Box2D body using information from triangle
	 * @param triangle
	 */
	public void createTriangleBody(GridTriangle triangle) {
		// Destroy current triangle
		if( triangle.get_body() != null ) {
			_physicsController.get_world().destroyBody( triangle.get_body() );
		}
		
		// Create a triangle based on the CCW points of the triangle
		ArrayList<PVector> trianglePoints = triangle.getPoints( true );
		_physicsController.m_density = 0;
		_physicsController.m_friction = 0;
		_physicsController.m_restitution = 0.1f;
		
		Body triangleBody = _physicsController.createPolygon(trianglePoints.get(0).x, trianglePoints.get(0).y, 
				trianglePoints.get(1).x, trianglePoints.get(1).y,
				trianglePoints.get(2).x, trianglePoints.get(2).y);
		
		triangle.set_body( triangleBody );
	}
	
	public void destroyTriangle( GridTriangle triangle ) {
		_physicsController.get_world().destroyBody( triangle.get_body() );
		triangle.set_body( null );
	}


	/**
	 * Rotate the triangle
	 * @param triangle
	 */
	public void rotateTriangle(GridTriangle triangle) {
		triangle.rotate( 90 );
	}

	
	private void handleSquares(){
		GridSquare square = _gridModel.getSquareAtPosition( mouseX, mouseY );
		if( square == null ) return; // No square at location

		GridTriangle triangle = square.getTriangle( mouseX, mouseY );
		if(triangle == null)  return; // No triangle at location


		// Already was active - rotate
		if(triangle.get_isActive()) {

		}

		// If the triangle already has a body, destroy it
		if(triangle.get_body() != null ) {
			_physicsController.get_world().destroyBody( triangle.get_body() );
			triangle.rotate( 90 );
		}


		// Create a triangle based on the CCW points of the triangle
		ArrayList<PVector> trianglePoints = triangle.getPoints( true );
		_physicsController.m_density = 0;
		_physicsController.m_friction = 1;
		_physicsController.m_restitution = 0.8f;

		Body triangleBody = _physicsController.createPolygon(trianglePoints.get(0).x, trianglePoints.get(0).y, 
				trianglePoints.get(1).x, trianglePoints.get(1).y,
				trianglePoints.get(2).x, trianglePoints.get(2).y);

		triangle.set_body( triangleBody );

		_guiController.set_activeTriangle( triangle );
	}
	
	

	/**
	 * Creates a Box2D circle object represented by a GridCircle
	 * @param posX
	 * @param posY
	 * @param aRadius
	 */
	private FallingObject addCircle( float posX, float posY, float aRadius ) {
		Body circleBody = _physicsController.createCircle(posX, posY, aRadius);
		FallingObject gridCircle = new FallingObject( circleBody, aRadius, FallingObject.CIRCLE, this );
		_levelModel.addObject( gridCircle );
		
		return gridCircle;
	}
	
	/**
	 * Creates a Box2D circle object represented by a GridCircle
	 * @param posX
	 * @param posY
	 * @param aRadius
	 */
	private void addSquare( float posX, float posY, float width ) {
		Body circleBody = _physicsController.createRect(posX, posY, posX+width, posY+width);
		FallingObject gridSquare = new FallingObject( circleBody, width, FallingObject.RECTANGLE, this );
		_levelModel.addObject( gridSquare );
	}

	//
	public static void main(String args[]) {
		PApplet.main(new String[] { "--present", "triangleeditor.TriangleEditor" });
	}
}
