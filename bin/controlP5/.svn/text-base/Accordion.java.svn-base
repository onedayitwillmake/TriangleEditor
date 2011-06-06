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

import java.util.ArrayList;
import java.util.List;

public class Accordion extends ControlGroup {

	private List<ControlGroup> items;
	
	private int spacing = 1;
	
	private int minHeight = 100;

	Accordion(ControlP5 theControlP5, Tab theTab, String theName, int theX, int theY, int theW) {
		super(theControlP5, theTab, theName, theX, theY, theW, 9);
		hideBar();
		items = new ArrayList<ControlGroup>();
	}

	/**
	 * add items of type ControlGroup to the Accordion. only ControlGroups can be added.
	 * 
	 * @param theGroup
	 */
	public void addItem(ControlGroup theGroup) {
		theGroup.close();
		theGroup.moveTo(this);
		theGroup.activateEvent(true);
		theGroup.addListener(this);
		theGroup.setMoveable(false);
		if (theGroup.getBackgroundHeight() < minHeight) {
			theGroup.setBackgroundHeight(minHeight);
		}
		items.add(theGroup);
		updateItems();
	}

	@Override
	/**
	 * removes a ControlGroup from the accordion AND from controlP5
	 * remove(ControllerInterface theGroup) overwrites it's super method.
	 * if you want to remove a ControlGroup only from the accordion, use removeItem(ControlGroup).
	 * @see #removeItem(ControlGroup)
	 */
	public void remove(ControllerInterface theGroup) {
		if (theGroup instanceof ControlGroup) {
			items.remove(theGroup);
			((ControlGroup) theGroup).removeListener(this);
			updateItems();
		}
		super.remove(theGroup);
	}

	/**
	 * removes a ControlGroup from the accordion and puts it back into the default
	 * tab of controlP5. if you dont have access to a ControlGroup via a variable,
	 * use controlP5.group("theNameOfTheGroup") which will return a
	 */
	public void removeItem(ControlGroup theGroup) {
		if (theGroup == null)
			return;
		items.remove(theGroup);
		theGroup.removeListener(this);
		theGroup.moveTo(controlP5.controlWindow);
		updateItems();
	}

	public void updateItems() {
		int n = 0;
		for (ControlGroup cg : items) {
			n += cg.getBarHeight() + spacing;
			cg.setPosition(0, n);
			if (cg.isOpen()) {
				n += cg.getBackgroundHeight();
			}
		}

	}

	/**
	 * sets the minimum height of a collapsed item. Default value is 100.
	 * 
	 * @param theHeight
	 */
	public void setMinItemHeight(int theHeight) {
		minHeight = theHeight;
		for (ControlGroup cg : items) {
			if (cg.getBackgroundHeight() < minHeight) {
				cg.setBackgroundHeight(minHeight);
			}
		}
		updateItems();
	}

	/**
	 * returns the minimum height of an item belonging to the accordion
	 * 
	 * @return int
	 */
	public int getMinItemHeight() {
		return minHeight;
	}

	/**
	 * sets the height for each item to theHeight
	 * 
	 * @param theHeight
	 */
	public void setItemHeight(int theHeight) {
		for (ControlGroup cg : items) {
			cg.setBackgroundHeight(theHeight);
		}
		updateItems();
	}

	@Override
	public ControllerGroup setWidth(int theWidth) {
		super.setWidth(theWidth);
		for (ControlGroup cg : items) {
			cg.setWidth(theWidth);
		}
		return this;
	}
	
	@Override
	public void controlEvent(ControlEvent theEvent) {
		if (theEvent.isGroup()) {
			int n = 0;
			for (ControlGroup cg : items) {
				n += cg.getBarHeight() + spacing;
				cg.setPosition(0, n);
				if (cg == theEvent.group() && cg.isOpen()) {
					n += cg.getBackgroundHeight();
				} else {
					cg.close();
				}
			}
		}
	}

}
