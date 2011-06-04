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
		_gridModel = new GridModel(width, height, 50, this);
	}
	
	public void draw() {
		++_elapsedFrames;
//		stroke(255);
		if(mousePressed) {
			line(mouseX,mouseY,pmouseX,pmouseY);
		}
		
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
		// TODO Auto-generated method stub
		super.mouseDragged();
	}

	/* (non-Javadoc)
	 * @see processing.core.PApplet#mouseMoved()
	 */
	@Override
	public void mouseMoved() {
		// TODO Auto-generated method stub
		super.mouseMoved();
	}

	/* (non-Javadoc)
	 * @see processing.core.PApplet#mousePressed()
	 */
	@Override
	public void mousePressed() {
		// TODO Auto-generated method stub
		super.mousePressed();
	}

	/* (non-Javadoc)
	 * @see processing.core.PApplet#mouseReleased()
	 */
	@Override
	public void mouseReleased() {
		super.mouseReleased();
		
		GridSquare square = _gridModel.getSquareAtPosition( mouseX, mouseY );
		square.getTriangle( mouseX, mouseY );
		square.__color = 128;
	}

	//
	public static void main(String args[]) {
		PApplet.main(new String[] { "--present", "MyProcessingSketch" });
	}
}
