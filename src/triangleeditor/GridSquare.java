package triangleeditor;
/**
 * A single grid square, each square contains two triangles
 * @author onedayitwillmake
 */

import javax.vecmath.Color3b;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import processing.core.PApplet;
import processing.core.PVector;
import triangleeditor.physics.PhysicsController;

public class GridSquare {
	
	// Square Info
	public PVector			_position;
	public PVector 			_gridPosition;
	public int				_size;
	public PVector			_center;
	
	// Square is made up of two triangles
	GridTriangle			_triangleA;
	GridTriangle			_triangleB;
	
	// Color
	public Color3b			_color;
	public int				 __color;
	
	// Reference to Processing
	private PApplet			app;
	
	public GridSquare(float xpos, float ypos, int row, int column, int size, PApplet appRef) {
		app = appRef;
		_position = new PVector(xpos, ypos);
		_gridPosition = new PVector(row, column);
		_size = size;
		_center = new PVector(_position.x + _size/2, _position.y + _size/2);
		__color = (int)appRef.random(255);
		
		setupTriangles();
	}
	
	private void setupTriangles() {
		// Triangle A
		//
		PVector A = new PVector();
		A.x = _position.x;
		A.y = _position.y + _size;
		
		PVector B = new PVector();
		B.x = _position.x + _size;
		B.y = _position.y + _size;
		
		PVector C = new PVector();
		C.x = _position.x;
		C.y = _position.y;
			
		_triangleA = new GridTriangle(A, B, C, _center, app);
		
		// Triangle B
		// 
		PVector A1 = new PVector();
		A1.x = _position.x + _size;
		A1.y = _position.y;
		
		PVector B1 = new PVector();
		B1.x = _position.x + _size;
		B1.y = _position.y + _size;
		
		PVector C1 = new PVector();
		C1.x = _position.x;
		C1.y = _position.y;
			
		_triangleB = new GridTriangle(A1, B1, C1, _center, app);
	}
	
	public void draw( PhysicsController physicsController )
	{

        
		// Set color based on value - our values start at zero, so we say (white) - value = color
		app.fill( __color );
		_triangleA.draw( physicsController );
		
		app.fill( __color );
		_triangleB.draw( physicsController );
	}

	/**
	 * Given a point, will return one of the two triangles contained within this square
	 * @param mouseX
	 * @param mouseY
	 * @return
	 */
	public GridTriangle getTriangle(int mouseX, int mouseY) {
		Boolean isInTriangleA = _triangleA.containsPoint(mouseX, mouseY);
		Boolean isInTriangleB = _triangleB.containsPoint(mouseX, mouseY);
		
//		PApplet.print("ContainsPoint: A:" + isInTriangleA + " B: " + isInTriangleB + "\n" );
//		PApplet.print( __color );

		if(isInTriangleA) return _triangleA;
		else if(isInTriangleB) return _triangleB;
		else return null;
	}
	
	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		json.put("xpos", _position.x);
		json.put("ypos", _position.y);
		json.put("row", _gridPosition.x);
		json.put("column", _gridPosition.y );
		json.put("size", _size);;
		json.put("triangleA", _triangleA.get_isActive() );
		json.put("triangleB",  _triangleB.get_isActive() );;
		
		return json;
	}
}
