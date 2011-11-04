package triangleeditor.model;

import java.util.ArrayList;
import java.util.HashMap;

import org.jbox2d.dynamics.Body;

import triangleeditor.physics.FallingObject;
import triangleeditor.physics.ILevelObject;

public class LevelModel {	 
	private ArrayList<ILevelObject> _allObjects;
	private ArrayList<FallingObject> _fallingObjects;
	
	public LevelModel() {
		_allObjects = new ArrayList<ILevelObject>();
    	_fallingObjects = new ArrayList<FallingObject>();
    }
    
    public void addObject( ILevelObject levelObject ) {
    	getAllObjects().add( levelObject );
    	
    	if( levelObject instanceof FallingObject ) {
    		_fallingObjects.add( (FallingObject)levelObject );
    	}
    }
    
    public void removeObjet( ILevelObject levelObject ) {
    	getAllObjects().remove( levelObject );
    	
    	if( levelObject instanceof FallingObject ) { 
    		_fallingObjects.add( (FallingObject)levelObject );
    	}
    }
    
    public ArrayList<FallingObject> get_fallingObjects() { return _fallingObjects; }
	public ArrayList<ILevelObject> getAllObjects() { return _allObjects;}
}
