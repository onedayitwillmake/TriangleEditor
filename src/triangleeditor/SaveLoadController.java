package triangleeditor;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import triangleeditor.model.LevelModel;

public class SaveLoadController {
	private static final SaveLoadController INSTANCE = new SaveLoadController();

    private SaveLoadController() {
    	
    }
 
    public void saveToRepresentation( LevelModel aLevelModel ) {
    	
    }
    
    /**
     * Loads a representation from the FileDialog.
     * Calls createFromRepresentation after attempting to load the chosen file
     */
    public void loadRepresentationFromFileDialog() {
    	String fileLocation = openFileDialog();
    	
		File file = new File(fileLocation);
		StringBuffer contents = new StringBuffer();
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(file));
			String text = null;

			// repeat until all lines is read
			while ((text = reader.readLine()) != null) {
				contents.append(text).append(
						System.getProperty("line.separator"));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		createFromRepresentation( contents.toString() );
    }
    
    public void createFromRepresentation( String jsonString ) {
    	System.out.println( jsonString.toString() );
    }
    
    private String openFileDialog() {
    	FileDialog fd = new FileDialog(new Frame(), "Open", FileDialog.LOAD);
	    fd.setFile("*.java");
	    fd.setDirectory("~/");
	    fd.setLocation(50, 50);
	    fd.show();
	    return fd.getDirectory() + System.getProperty("file.separator") + fd.getFile();
    }
    
    public static SaveLoadController getInstance() {
        return INSTANCE;
    }
    
}

