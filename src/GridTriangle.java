import processing.core.PApplet;
import processing.core.PVector;


/**
 * Represents a triangle given 3 points
 * @author onedayitwillmake
 */
public class GridTriangle {
	public PVector	A;
	public PVector	B;
	public PVector	C;
	public PVector	centroid;
	
	private Boolean	_isActive;
	
	
	private PApplet	app;
	
	public GridTriangle( PVector a, PVector b, PVector c, PApplet appRef) {
		A = a;
		B = b;
		C = c;
		app = appRef;
		_isActive = false;
		
		
		computeCentroid();
	}
	
	private void computeCentroid() {
		centroid = new PVector();
		centroid.x = (A.x + B.x + C.x) / 3.0f;
		centroid.y = (A.y + B.y + C.y) / 3.0f;
	}
	
	/**
	 * Given a point X,Y will return if it is contained within this triangle
	 * @param x
	 * @param y
	 * @return
	 */
	public Boolean containsPoint(int x, int y) {
		PVector p = new PVector(x, y);
		PVector v1 = PVector.sub(p, A);
		v1.normalize();
		PVector v2 = PVector.sub(p, B);
		v2.normalize();
		PVector v3 = PVector.sub(p, C);
		v3.normalize();
        double total_angles = Math.acos(v1.dot(v2));
        
        total_angles += Math.acos(v2.dot(v3));
        total_angles += Math.acos(v3.dot(v1));
        return (PApplet.abs((float) total_angles - PApplet.TWO_PI) <= 0.01f);
	}
	
	public void draw() {
		if(!_isActive) { 
			return;
		}
		
		app.triangle(A.x, A.y, B.x, B.y, C.x, C.y);
		
		Boolean shouldDrawCenter = true;
		if(shouldDrawCenter) {
			// Draw center
			app.fill( 255 );
			app.ellipseMode(PApplet.CENTER);
			app.ellipse(centroid.x, centroid.y, 2, 2);
		}
	}

	public Boolean get_isActive() {
		return _isActive;
	}

	public void set_isActive(Boolean _isActive) {
		this._isActive = _isActive;
	}
	
	
}
