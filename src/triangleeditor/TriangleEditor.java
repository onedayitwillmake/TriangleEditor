package triangleeditor;

import processing.core.*;
import triangleeditor.physics.PhysicsController;


public class TriangleEditor extends PApplet {
	float				_elapsedFrames;
	GridModel			_gridModel;
	PhysicsController	_physicsController;
	
	public void setup() {
		_elapsedFrames = 0;
		
		size(1200, 600, P3D);
		frameRate(60);
		background(0);
		setupGrid();
		setupPhysicsController();
	}
	
	public void setupGrid() {
		_gridModel = new GridModel(width, height, 50, this);
	}
	
	public void setupPhysicsController() {
		_physicsController = new PhysicsController( this );
		_physicsController.createWorld(width, height, 0, 20, width*2, height*2, width, height, 10);
		
		_physicsController.m_density = 1;
		_physicsController.m_restitution = random(1);
//		_physicsController.m_
		for( int i = 0; i < 100; ++i ) {
			_physicsController.createCircle(random(width), 10, random(5, 20));
		}
	}
	
	public void draw() {
		++_elapsedFrames;
		background(0);
//		fill( 255 );
//        rect(0,0, width, height);
//        
//		noStroke();
//		drawGrid();
		
	}
	
	
	/**
	 * Calls draw on all GridSquares
	 */
	public void drawGrid() {
		for (GridSquare square : _gridModel.get_gridSquareList() ) {
			square.draw();
		}
	}
	
	
	
	/* (non-Javadoc)
	 * @see processing.core.PApplet#mouseDragged()
	 */
	@Override
	public void mouseDragged() {
		super.mouseDragged();
		handleSquares();
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
		handleSquares();
	}
	
	private void handleSquares(){
		GridSquare square = _gridModel.getSquareAtPosition( mouseX, mouseY );
		if( square == null )
			return;
		
//		square.__color = 128;
		
		GridTriangle selectedTriangle = square.getTriangle( mouseX, mouseY );
		if(selectedTriangle != null) 
		{	
			// Already was active - rotate
			if(selectedTriangle.get_isActive()) {
				selectedTriangle.rotate(90);
			}
			
			selectedTriangle.set_isActive( true );
		}
	}

	//
	public static void main(String args[]) {
		PApplet.main(new String[] { "--present", "triangleeditor.TriangleEditor" });
	}
}
