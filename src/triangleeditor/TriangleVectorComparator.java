/**
 * Used to sort a set of polygonal points based on the barycenter
 * http://gamedev.stackexchange.com/questions/13229/sorting-array-of-points-in-clockwise-order
 */
package triangleeditor;

import java.util.Comparator;

import processing.core.PVector;

public class TriangleVectorComparator implements Comparator<PVector>  {
	private PVector M; 
	public TriangleVectorComparator(PVector origin) {
		M = origin;
	}
	
	public int compare(PVector o1, PVector o2) {
		double angle1 = Math.atan2(o1.y - M.y, o1.x - M.x);
		double angle2 = Math.atan2(o2.y - M.y, o2.x - M.x);
		
		if(angle1 < angle2) return 1;
		else if (angle2 > angle1) return -1;
		return 0;
	}
	
}