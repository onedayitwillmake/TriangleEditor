import java.util.ArrayList;

/**
 * Model that contains a 2 column array of GridSquares
 * @author onedayitwillmake
 *
 */
public class GridModel {
	GridSquare[][]			_gridSquares;
	ArrayList<GridSquare>	_gridSquareList;
	
	int					_squareSize;
	int                 _gridColumnCount;
	int					_gridRowCount;

	public GridModel( int width, int height, int squareSize ) {

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
	 * @return
	 */
	public void setupSquares() {
	  	int iterator = 0;
	 	for(int i = 0; i < _gridColumnCount; i ++)
		{
			for(int j = 0; j < _gridRowCount; j++)
			{  
			 	_gridSquares[i][j] = new GridSquare( i *_squareSize, j *_squareSize, i, j, (int)_squareSize, MyProcessingSketch.APPLET);;
			 	_gridSquareList.add( _gridSquares[i][j] );
		  		++iterator;
			}
		}
	}
	
	public void getSquareAtPosition( int xpos, int ypos ) {	
		 // Determin which square the click is on
	    int row = MyProcessingSketch.APPLET.floor( xpos / _squareSize) ;
	    int column = MyProcessingSketch.APPLET.floor( ypos / _squareSize);
	    // Store in pvector
//	    _gridPosition = new PVector(myRow, myColumn);
	    
	    // Retrieve the grid square at our grid position
//	    GridSquareAgent originatingSquare = 
	}
	
	public ArrayList<GridSquare> get_gridSquareList() {
		return _gridSquareList;
	}
}
