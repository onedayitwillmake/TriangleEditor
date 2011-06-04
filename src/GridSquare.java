/**
 * A single grid square
 * @author onedayitwillmake
 */

import javax.vecmath.Color3b;

import processing.core.PApplet;
import processing.core.PVector;

public class GridSquare {
	PVector			_position;
	PVector 		_gridPosition;
	int				_size;
	Color3b			_color;
	
	MyProcessingSketch			_parent;
	
	public GridSquare(float xpos, float ypos, int row, int column, int size, MyProcessingSketch parent) {
		_position = new PVector(xpos, ypos);
		_gridPosition = new PVector(row, column);
		_size = size;
		_parent = parent;
//		_color = new Color3b(255, 255, 255);
	}
	
	public void draw()
	{
		int halfWidth =_size/2;
		
		// Set color based on value - our values start at zero, so we say (white) - value = color
		float noise = _parent.noise( (float)(_position.x*0.001), (float)(_position.y*0.001), (float)(_parent._elapsedFrames * 0.01) );
		_parent.fill( noise * 255 );
		_parent.rect(_position.x, _position.y, _size, _size);
	}
}
