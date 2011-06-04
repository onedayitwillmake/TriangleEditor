import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion.Static;

import processing.core.*;


public class MyProcessingSketch extends PApplet {
	float				_elapsedFrames;
	GridModel			_gridModel;
	public static MyProcessingSketch	APPLET;
	public void setup() {
		MyProcessingSketch.APPLET = this;
		_elapsedFrames = 0;
		
		size(1200, 800);
		frameRate(60);
		background(0);
		setupGrid();
	}
	
	public void setupGrid() {
		_gridModel = new GridModel(width, height, 40);
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
	
	//
	public static void main(String args[]) {
		PApplet.main(new String[] { "--present", "MyProcessingSketch" });
	}
}
