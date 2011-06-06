package controlP5;

/**
 * controlP5 is a processing gui library.
 *
 *  2007-2010 by Andreas Schlegel
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307 USA
 *
 * @author 		Andreas Schlegel (http://www.sojamo.de)
 * @modified	##date##
 * @version		##version##
 *
 */

import java.awt.event.KeyEvent;

import processing.core.PApplet;
import processing.core.PVector;

public interface ControllerInterface {

	public void init();

	public int getWidth();

	public int getHeight();

	public float value();
	
	public void setValue(float theValue);
	
	public float getValue();
	
	public PVector position();
	
	public PVector getPosition();

	public void setPosition(float theX, float theY);
	
	public void setPosition(PVector thePVector); 

	public PVector absolutePosition();
	
	public PVector getAbsolutePosition();
	
	public void setAbsolutePosition(PVector thePVector);

	public void updateAbsolutePosition();

	public void update();

	public void setUpdate(boolean theFlag);

	public boolean isUpdate();

	public void updateEvents();

	public void continuousUpdateEvents();

	/**
	 * a method for putting input events like e.g. mouse or keyboard events and
	 * queries. this has been taken out of the draw method for better overwriting
	 * capability.
	 * 
	 * 
	 */
	public void updateInternalEvents(PApplet theApplet);

	public void draw(PApplet theApplet);

	public void add(ControllerInterface theElement);

	public void remove(ControllerInterface theElement);

	public void remove();

	public String name();

	public ControlWindow getWindow();

	public Tab getTab();

	public boolean setMousePressed(boolean theStatus);

	public void keyEvent(KeyEvent theEvent);

	public void setId(int theValue);

	public int id();

	public void setLabel(String theString);

	public void setColorActive(int theColor);

	public void setColorForeground(int theColor);

	public void setColorBackground(int theColor);

	public void setColorLabel(int theColor);

	public void setColorValue(int theColor);

	public CColor color();

	public void show();

	public void hide();

	public boolean isVisible();

	public void moveTo(ControlGroup theGroup, Tab theTab, ControlWindow theWindow);

	public String stringValue();

	public int getPickingColor();

	public ControllerInterface parent();
	
	public ControllerProperty getProperty(String thePropertyName);
	
	public ControllerProperty getProperty(String theSetter, String theGetter);
	
	public ControllerInterface registerProperty(String thePropertyName);
	
	public ControllerInterface registerProperty(String theSetter, String theGetter);
	
	public void removeProperty(String thePropertyName);
	
	public void removeProperty(String theSetter, String theGetter);
	
	public void setColor(CColor theColor);

}
