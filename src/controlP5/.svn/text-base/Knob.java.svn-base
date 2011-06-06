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

import processing.core.PApplet;
import processing.core.PVector;

/**
 * a knob. description tbd.
 * 
 * @example ControlP5knob
 */
public class Knob extends Controller {

	protected float _myDiameter;

	protected float _myRadius;

	protected float myAngle;

	protected float startAngle;

	protected float range;

	protected float resolution = 200.0f; // sensitivity.

	protected int _myTickMarksNum = 8;

	protected boolean isShowTickMarks;

	protected boolean isSnapToTickMarks;

	protected int myTickMarkLength = 2;

	protected float myTickMarkWeight = 1;

	protected boolean isShowRange = true;

	protected float currentValue;

	protected float previousValue;

	protected float modifiedValue;

	protected boolean isConstrained;

	protected int _myDragDirection = HORIZONTAL;

	protected int displayStyle = LINE;

	public static int autoWidth = 40;

	public static int autoHeight = 40;

	protected PVector autoSpacing = new PVector(10, 20, 0);

	public Knob(
			ControlP5 theControlP5,
			ControllerGroup theParent,
			String theName,
			float theMin,
			float theMax,
			float theDefaultValue,
			int theX,
			int theY,
			int theWidth) {
		super(theControlP5, theParent, theName, theX, theY, theWidth, theWidth);
		_myValue = theDefaultValue;
		setMin(theMin);
		setMax(theMax);
		_myDiameter = theWidth;
		_myRadius = _myDiameter / 2;
		_myUnit = (_myMax - _myMin) / ControlP5Constants.TWO_PI;

		startAngle = HALF_PI + PI * 0.25f;
		range = PI + HALF_PI;
		myAngle = startAngle;
		isConstrained = true;

	}

	public void setRadius(float theValue) {
		_myRadius = theValue;
		_myDiameter = _myRadius * 2;
		width = (int) _myDiameter;
	}

	public float getRadius() {
		return _myRadius;
	}

	/**
	 * The start angle is a value between 0 and TWO_PI. By default the start angle
	 * is set to HALF_PI + PI * 0.25f
	 * 
	 * @param theAngle
	 */
	public void setStartAngle(float theAngle) {
		startAngle = theAngle;
		setInternalValue(modifiedValue);
	}

	/**
	 * get the start angle, 0 is at 3 o'clock.
	 * 
	 * @return
	 */
	public float getStartAngle() {
		return startAngle;
	}

	/**
	 * set the range in between which the know operates. By default the range is
	 * PI + HALF_PI
	 * 
	 * @param theRange
	 */
	public void setRange(float theRange) {
		range = theRange;
		setInternalValue(modifiedValue);
	}

	/**
	 * get the range value.
	 * 
	 * @return
	 */
	public float getRange() {
		return range;
	}

	public float getAngle() {
		return myAngle;
	}

	public boolean isShowRange() {
		return isShowRange;
	}

	public void setShowRange(boolean theValue) {
		isShowRange = theValue;
	}

	/**
	 * set the drag direction, when controlling a knob, parameter is either
	 * Controller.HORIZONTAL or Controller.VERTICAL.
	 * 
	 * @param theValue
	 */
	public void setDragDirection(int theValue) {
		if (theValue == HORIZONTAL) {
			_myDragDirection = HORIZONTAL;
		} else {
			_myDragDirection = VERTICAL;
		}
	}

	/**
	 * get the drag direction which is either Controller.HORIZONTAL or
	 * Controller.VERTICAL.
	 * 
	 * @return
	 */
	public int getDragDirection() {
		return _myDragDirection;
	}

	/**
	 * resolution is a sensitivity value when dragging a knob. the higher the
	 * value, the more sensitive the dragging.
	 * 
	 * @param theValue
	 */
	public void setResolution(float theValue) {
		resolution = theValue;
	}

	public float getResolution() {
		return resolution;
	}

	public void setNumberOfTickMarks(int theNumber) {
		_myTickMarksNum = theNumber;
	}

	public int getNumberOfTickMarks() {
		return _myTickMarksNum;
	}

	public void showTickMarks(boolean theFlag) {
		isShowTickMarks = theFlag;
	}

	public boolean isShowTickMarks() {
		return isShowTickMarks;
	}

	public void snapToTickMarks(boolean theFlag) {
		isSnapToTickMarks = theFlag;
	}

	public void setTickMarkLength(int theLength) {
		myTickMarkLength = theLength;
	}

	public int getTickMarkLength() {
		return myTickMarkLength;
	}

	public void setTickMarkWeight(float theWeight) {
		myTickMarkWeight = theWeight;
	}

	public float getTickMarkWeight() {
		return myTickMarkWeight;
	}

	public void setConstrained(boolean theValue) {
		isConstrained = theValue;
	}

	public boolean isConstrained() {
		return isConstrained;
	}

	/*
	 * 
	 * settings for:
	 * 
	 * TODO tickmarks: distance from edge
	 * 
	 * TODO only start-end marks if isLimited and tickmarks are off.
	 * 
	 * TODO arc: add setter for distance to center + distance to edge currently
	 * percental.
	 * 
	 * TODO enable/disable drag and click control (for endless, click should be
	 * disabled).
	 * 
	 * TODO dragging: add another option to control the knob. currently only
	 * linear dragging is implemented, add circular dragging (as before) as well
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see controlP5.Controller#updateInternalEvents(processing.core.PApplet)
	 */
	public void updateInternalEvents(PApplet theApplet) {
		if (isMousePressed && !ControlP5.keyHandler.isAltDown) {
			if (isActive) {
				float c = (_myDragDirection == HORIZONTAL) ? _myControlWindow.mouseX - _myControlWindow.pmouseX
						: _myControlWindow.mouseY - _myControlWindow.pmouseY;
				currentValue += (c) / resolution;
				if (isConstrained) {
					currentValue = PApplet.constrain(currentValue, 0, 1);
				}
				setInternalValue(currentValue);
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controlP5.Controller#onEnter()
	 */
	protected void onEnter() {
		isActive = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controlP5.Controller#onLeave()
	 */
	protected void onLeave() {
		isActive = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controlP5.Controller#mousePressed()
	 */
	public void mousePressed() {
		float x = _myParent.absolutePosition().x + position.x + _myRadius;
		float y = _myParent.absolutePosition().y + position.y + _myRadius;
		if (PApplet.dist(x, y, _myControlWindow.mouseX, _myControlWindow.mouseY) < _myRadius) {
			isActive = true;
			if (PApplet.dist(x, y, _myControlWindow.mouseX, _myControlWindow.mouseY) > (_myRadius * 0.6)) {
				myAngle = (PApplet.atan2(_myControlWindow.mouseY - y, _myControlWindow.mouseX - x) - startAngle);
				if (myAngle < 0) {
					myAngle = TWO_PI + myAngle;
				}
				if (isConstrained) {
					myAngle %= TWO_PI;
				}
				currentValue = PApplet.map(myAngle, 0, range, 0, 1);
				setInternalValue(currentValue);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controlP5.Controller#mouseReleased()
	 */
	public void mouseReleased() {
		isActive = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controlP5.Controller#mouseReleasedOutside()
	 */
	public void mouseReleasedOutside() {
		mouseReleased();
	}

	/**
	 * set the minimum value of the knob.
	 * 
	 * @param theValue float
	 */
	public void setMin(float theValue) {
		_myMin = theValue;
		// TODO should update internalValue here?
	}

	/**
	 * set the maximum value of the knob.
	 * 
	 * @param theValue float
	 */
	public void setMax(float theValue) {
		_myMax = theValue;
		// TODO should update internalValue here?
	}

	protected void setInternalValue(float theValue) {
		// TODO simplify below code.
		// TODO rename modifiedValue and currentValue to make it more obvious to
		// what these variables refer to
		modifiedValue = (isSnapToTickMarks) ? PApplet.round((theValue * _myTickMarksNum)) / ((float) _myTickMarksNum)
				: theValue;
		currentValue = theValue;
		myAngle = PApplet.map(isSnapToTickMarks == true ? modifiedValue : currentValue, 0, 1, startAngle, startAngle
				+ range);

		if (isSnapToTickMarks) {
			if (previousValue != modifiedValue && isSnapToTickMarks) {
				broadcast(FLOAT);
				previousValue = modifiedValue;
				return;
			}
		}
		if (previousValue != currentValue) {
			broadcast(FLOAT);
			previousValue = modifiedValue;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controlP5.Controller#setValue(float)
	 */
	public void setValue(float theValue) {
		setInternalValue(PApplet.map(theValue, _myMin, _myMax, 0, 1));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controlP5.Controller#value()
	 */
	public float value() {
		_myValue = PApplet.map(_myTickMarksNum > 0 ? modifiedValue : currentValue, 0, 1, _myMin, _myMax);
		return _myValue;
	}

	/**
	 * assigns a random value to the controller.
	 */
	public void shuffle() {
		float r = (float) Math.random();
		setValue(PApplet.map(r, 0, 1, getMin(), getMax()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controlP5.Controller#update()
	 */
	public void update() {
		setValue(_myValue);
	}

	/**
	 * @deprecated
	 * @see controlP5.Knob#setStartAngle(float)
	 * 
	 */
	public void setOffsetAngle(float theValue) {
		setStartAngle(theValue);
	}

	/**
	 * set the display style of a know. takes parameters Knob.LINE, Knob.ELLIPSE
	 * or Knob.ARC. default style is Knob.LINE
	 * 
	 * @param theStyle
	 */
	public Knob setDisplayStyle(int theStyle) {
		displayStyle = theStyle;
		return this;
	}

	public int getDisplayStyle() {
		return displayStyle;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controlP5.Controller#updateDisplayMode(int)
	 */
	public void updateDisplayMode(int theMode) {
		_myDisplayMode = theMode;
		switch (theMode) {
		case (DEFAULT):
			_myDisplay = new KnobDisplay();
			break;
		case (SPRITE):
		case (IMAGE):
			_myDisplay = new KnobDisplay();
			break;
		case (CUSTOM):
		default:
			break;
		}
	}

	class KnobDisplay implements ControllerDisplay {
		public void display(PApplet theApplet, Controller theController) {
			theApplet.translate(getRadius(), getRadius());

			theApplet.pushMatrix();
			theApplet.pushStyle();
			theApplet.ellipseMode(PApplet.CENTER);
			theApplet.noStroke();
			theApplet.fill(color().getBackground());
			theApplet.ellipse(0, 0, getRadius() * 2, getRadius() * 2);
			theApplet.popMatrix();
			int c = isActive() ? color().getActive() : color().getForeground();
			theApplet.pushMatrix();
			if (getDisplayStyle() == Controller.LINE) {
				theApplet.rotate(getAngle());
				theApplet.stroke(c);
				theApplet.line(0, 0, getRadius(), 0);
			} else if (getDisplayStyle() == Controller.ELLIPSE) {
				theApplet.rotate(getAngle());
				theApplet.noStroke();
				theApplet.fill(c);
				theApplet.ellipse(getRadius() * 0.75f, 0, getRadius() * 0.2f, getRadius() * 0.2f);
			} else if (getDisplayStyle() == Controller.ARC) {
				theApplet.noStroke();
				theApplet.fill(c);
				theApplet.arc(0, 0, getRadius() * 1.8f, getRadius() * 1.8f, getStartAngle(),getAngle() + ((getStartAngle()==getAngle()) ? 0.06f:0f));
				theApplet.fill(color().getBackground());
				theApplet.ellipse(0, 0, getRadius() * 1.2f, getRadius() * 1.2f);
			}
			theApplet.popMatrix();

			theApplet.pushMatrix();
			theApplet.rotate(getStartAngle());

			if (isShowTickMarks()) {
				float step = getRange() / getNumberOfTickMarks();
				theApplet.stroke(color().getForeground());
				theApplet.strokeWeight(getTickMarkWeight());
				for (int i = 0; i <= getNumberOfTickMarks(); i++) {
					theApplet.line(getRadius() + 2, 0, getRadius() + getTickMarkLength() + 2, 0);
					theApplet.rotate(step);
				}
			} else {
				if (isShowRange()) {
					theApplet.stroke(color().getForeground());
					theApplet.strokeWeight(getTickMarkWeight());
					theApplet.line(getRadius() + 2, 0, getRadius() + getTickMarkLength() + 2, 0);
					theApplet.rotate(getRange());
					theApplet.line(getRadius() + 2, 0, getRadius() + getTickMarkLength() + 2, 0);
				}
			}
			theApplet.noStroke();
			theApplet.popStyle();
			theApplet.popMatrix();
			captionLabel().draw(theApplet, -captionLabel().getWidth() / 2, getHeight() / 2 + 5);
		}
	}

}
