package triangleeditor;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import triangleeditor.model.GridModel;
import triangleeditor.model.LevelModel;

public class SaveLoadController {
	private static final SaveLoadController INSTANCE = new SaveLoadController();

    private SaveLoadController() {
    	
    }
 
    
    public void saveToRepresentation( ) {
    	//new JSon
    	GridModel model = TriangleEditor.getINSTANCE().getGridModel();
    	
    	JSONArray list = new JSONArray();
		for (GridSquare square : model.get_gridSquareList() ) {
        	list.add( square.toJson() );
    	}
		
    	
		System.out.println(list );
		
		
		FileDialog fd = new FileDialog(new Frame(), "Save", FileDialog.SAVE);
	    fd.setFile( System.currentTimeMillis()/1000 + ".json" );
	    fd.setDirectory("~/Desktop");
	    fd.setLocation(50, 50);
	    fd.show();
	    String location = fd.getDirectory() + System.getProperty("file.separator") + fd.getFile();
	    

	    File f = new File(location);
        BufferedWriter bufferedWriter = null;

	    try {
	    	//Construct the BufferedWriter object
	    	bufferedWriter = new BufferedWriter(new FileWriter(f));
            
            //Start writing to the output stream
            bufferedWriter.write(list.toString());
		    } catch (FileNotFoundException ex) {
		        ex.printStackTrace();
		    } catch (IOException ex) {
		        ex.printStackTrace();
		    } finally {
		        //Close the BufferedWriter
		        try {
		            if (bufferedWriter != null) {
		                bufferedWriter.flush();
		                bufferedWriter.close();
		            }
		        } catch (IOException ex) {
		            ex.printStackTrace();
		        }
		    }
	    
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
		  JSONParser parser = new JSONParser();
			try {
				Object obj = parser.parse(jsonString);
				  JSONArray array=(JSONArray)obj;
				  Iterator itr = array.iterator();

				 
				  TriangleEditor.getINSTANCE().getGridModel().get_gridSquareList().clear();
				 while( itr.hasNext() ) {
					 TriangleEditor.getINSTANCE().getGridModel().setSquareFromJSON(  (JSONObject) itr.next()  );
				 }				
			} catch (org.json.simple.parser.ParseException pe) {
				System.out.println("position: " + pe);
				System.out.println(pe);
			}
		
		
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

