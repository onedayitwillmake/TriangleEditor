import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion.Static;

import processing.core.*;


public class MyProcessingSketch extends PApplet {
	float				_elapsedFrames;
	GridModel			_gridModel;
	
	public void setup() {
		_elapsedFrames = 0;
		
		size(1200, 600);
		frameRate(60);
		background(0);
		setupGrid();
	}
	
	public void setupGrid() {
		_gridModel = new GridModel(width, height, 25, this);
	}
	
	public void draw() {
		++_elapsedFrames;
		noStroke();
		drawGrid();
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
		
		GridSquare square = _gridModel.getSquareAtPosition( mouseX, mouseY );
		square.__color = 128;
		
		GridTriangle selectedTriangle = square.getTriangle( mouseX, mouseY );
		if(selectedTriangle != null) {
			selectedTriangle.set_isActive( true );
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
	}

	//
	public static void main(String args[]) {
		PApplet.main(new String[] { "--present", "MyProcessingSketch" });
	}
}
