package triangleeditor.model;

import java.util.ArrayList;
import java.util.HashMap;

import org.jbox2d.dynamics.Body;

import triangleeditor.FallingObject;
import triangleeditor.ILevelObject;

public class LevelModel {	 
	private ArrayList<ILevelObject> _allObjects;
	private ArrayList<FallingObject> _fallingObjects;
	
	public LevelModel() {
    	_allObjects = new ArrayList<ILevelObject>();
    	_fallingObjects = new ArrayList<FallingObject>();
    }
    
    public void addObject( ILevelObject levelObject ) {
    	_allObjects.add( levelObject );
    	
    	if( levelObject instanceof FallingObject ) {
    		_fallingObjects.add( (FallingObject)levelObject );
    	}
    }
    
    public void removeObjet( ILevelObject levelObject ) {
    	_allObjects.remove( levelObject );
    	
    	if( levelObject instanceof FallingObject ) { 
    		_fallingObjects.add( (FallingObject)levelObject );
    	}
    }
    
    public ArrayList<FallingObject> get_fallingObjects() { return _fallingObjects; }
}
