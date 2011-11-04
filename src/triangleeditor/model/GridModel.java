package triangleeditor.model;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import processing.core.PApplet;
import processing.core.PImage;
import triangleeditor.GridSquare;
import triangleeditor.GridTriangle;

/**
 * Model that contains a 2 column array of GridSquares
 * @author onedayitwillmake
 *
 */
public class GridModel {
	private GridSquare[][]			_gridSquares;
	private ArrayList<GridSquare>	_gridSquareList;
	
	private int					_squareSize;
	private int                 _gridColumnCount;
	private int					_gridRowCount;
	
	public PImage b;
	
	
	// Reference to Processing
	private PApplet app;


	public GridModel( int width, int height, int squareSize, PApplet app ) {

		b = app.loadImage("data/tree.png");
		
		
		this.app = app;
		_squareSize = squareSize;
		
		// Determin column / row count - based on width/height of sketch relative to "boxWidth"
		_gridColumnCount   = width / _squareSize;
		_gridRowCount      = height / _squareSize;
		
		_gridSquares = new GridSquare[ _gridColumnCount ][ _gridRowCount ];
		_gridSquareList = new ArrayList<GridSquare>();
		setupSquares();
	}
	
	/**
	 * Creates a square for each row/column
	 * Assumes _gridColumnCount/_gridRowCount properties are set
	 */
	public void setupSquares() {
	  	int iterator = 0;
	 	for(int i = 0; i < _gridColumnCount; i ++)
		{
			for(int j = 0; j < _gridRowCount; j++)
			{  
			 	_gridSquares[i][j] = new GridSquare( i *_squareSize, j *_squareSize, i, j, (int)_squareSize, app);
			 	_gridSquareList.add( _gridSquares[i][j] );
			 	
			 	_gridSquares[i][j].__color = b.get((int)(i *_squareSize + _squareSize*0.5f), (int)(j *_squareSize + _squareSize * 0.5f) );
		  		++iterator;
			}
		}
	}
	
	/**
	 * Returns the square located at xpos,ypos
	 * @param xpos
	 * @param ypos
	 * @return
	 */
	public GridSquare getSquareAtPosition( int xpos, int ypos ) {
	    int column= PApplet.floor( xpos / _squareSize) ;
	    int row  = PApplet.floor( ypos / _squareSize);
	    
	    // Out of bounds 
	    if(row < 0 || column < 0 || column >= _gridColumnCount || row >= _gridRowCount ) 
	    	return null;
	    
	    return _gridSquares[column][row];
	}
	
	//  JSONObject obj2=(JSONObject)array.get(1);

	public void clear( JSONObject obj ) {
		
	}
	
	/**
	 * Returns the Triangle at xpos, ypos
	 * @param mouseX
	 * @param mouseY
	 * @return
	 */
	public GridTriangle getTriangleAtPosition(int mouseX, int mouseY) {
		GridSquare square = getSquareAtPosition( mouseX, mouseY );
		if( square == null ) return null; // No square at location
						
		return square.getTriangle( mouseX, mouseY );
	}
	
	public ArrayList<GridSquare> get_gridSquareList() {
		return _gridSquareList;
	}

	public void setSquareFromJSON(JSONObject anObject) {
		// TODO Auto-generated method stub
		float column = new Double( (Double) anObject.get("column") ).floatValue();
		float row = new Double( (Double) anObject.get("row") ).floatValue();
		float xpos = new Double( (Double) anObject.get("xpos") ).floatValue();
		float ypos = new Double( (Double) anObject.get("ypos") ).floatValue();
		float size = new Long( (Long) anObject.get("size") ).floatValue();
		
		_gridSquares[(int)column][(int)row] = new GridSquare( xpos, ypos, (int) column, (int) row, (int)size, app);
	 	_gridSquareList.add( _gridSquares[(int)column][(int)row] );
	}

}
