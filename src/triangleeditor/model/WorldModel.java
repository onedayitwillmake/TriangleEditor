package triangleeditor.model;

public class WorldModel {
	private static final WorldModel INSTANCE = new WorldModel();
	 
    private WorldModel() {
    }
 
    public void saveToRepresentation( GridModel aGridModel ) {
    	
    }
    
    public void createFromRepresentation( String jsonString ) {
    	
    }
    
    public static WorldModel getInstance() {
        return INSTANCE;
    }
    
}
