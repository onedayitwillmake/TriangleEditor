package controlP5;

import processing.core.PApplet;

public class Slider2D extends Controller {

	protected int cursorWidth = 10, cursorHeight = 10;

	protected float cursorX, cursorY;

	protected float _myMinX, _myMinY;

	protected float _myMaxX, _myMaxY;

	public boolean isCrosshairs;

	private String _myValueLabelSeparator = ",";

	protected Slider2D(
			ControlP5 theControlP5,
			ControllerGroup theParent,
			String theName,
			int theX,
			int theY,
			int theWidth,
			int theHeight) {
		super(theControlP5, theParent, theName, theX, theY, theWidth, theHeight);
		_myArrayValue = new float[] { 0.0f, 0.0f };
		_myMinX = 0;
		_myMinY = 0;
		_myMaxX = theWidth;
		_myMaxY = theHeight;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controlP5.Controller#updateInternalEvents(processing.core.PApplet)
	 */
	public void updateInternalEvents(PApplet theApplet) {
		if (isInside()) {
			if (!ControlP5.keyHandler.isAltDown) {
				float tX = PApplet.constrain(_myControlWindow.mouseX - (_myParent.getAbsolutePosition().x + position.x), 0, width
						- cursorWidth);
				float tY = PApplet.constrain(_myControlWindow.mouseY - (_myParent.getAbsolutePosition().y + position.y), 0, height
						- cursorHeight);
				if (isMousePressed) {
					cursorX = tX;
					cursorY = tY;
					updateValue();
				}
			}
		}
	}

	protected void updateValue() {
		setValue(0);
	}

	public void setMinX(float theMinX) {
		_myMinX = theMinX;
		updateValue();
	}

	public void setMinY(float theMinY) {
		_myMinY = theMinY;
		updateValue();
	}

	public void setMaxX(float theMaxX) {
		_myMaxX = theMaxX;
		updateValue();
	}

	public void setMaxY(float theMaxY) {
		_myMaxY = theMaxY;
		updateValue();
	}

	public float getMinX() {
		return _myMinX;
	}

	public float getMinY() {
		return _myMinY;
	}

	public float getMaxX() {
		return _myMaxX;
	}

	public float getMaxY() {
		return _myMaxY;
	}

	public float getCursorX() {
		return cursorX;
	}

	public float getCursorY() {
		return cursorY;
	}

	public float getCursorWidth() {
		return cursorWidth;
	}

	public float getCursorHeight() {
		return cursorHeight;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controlP5.Controller#setArrayValue(float[])
	 */
	public void setArrayValue(float[] theArray) {
		_myArrayValue = theArray;
		float rX = (width - cursorWidth) / (float) (_myMaxX - _myMinX);
		float rY = (height - cursorHeight) / (float) (_myMaxY - _myMinY);
		cursorX = PApplet.constrain(theArray[0] * rX, 0, width - cursorWidth);
		cursorY = PApplet.constrain(theArray[1] * rY, 0, height - cursorHeight);
		updateValue();
	}

	public float[] getArrayValue() {
		return _myArrayValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controlP5.Controller#setValue(float)
	 */
	public void setValue(float theValue) {
		_myArrayValue[0] = cursorX / ((float) (width - cursorWidth) / (float) width);
		_myArrayValue[1] = cursorY / ((float) (height - cursorHeight) / (float) height);
		_myArrayValue[0] = PApplet.map(_myArrayValue[0], 0, width, _myMinX, _myMaxX);
		_myArrayValue[1] = PApplet.map(_myArrayValue[1], 0, height, _myMinY, _myMaxY);
		_myValueLabel.set(adjustValue(_myArrayValue[0], 0) + _myValueLabelSeparator + adjustValue(_myArrayValue[1], 0));
		broadcast(FLOAT);
	}

	/**
	 * assigns a random value to the controller.
	 */
	public void shuffle() {
		float rX = (float) Math.random();
		float rY = (float) Math.random();
		_myArrayValue[0] = rX * width;
		_myArrayValue[0] = rY * height;
		setValue(0);
	}

	public void setValueLabelSeparator(String theSeparator) {
		_myValueLabelSeparator = theSeparator;
	}

	public void updateDisplayMode(int theMode) {
		_myDisplayMode = theMode;
		switch (theMode) {
		case (DEFAULT):
			_myDisplay = new Slider2DDisplay();
			break;
		case (IMAGE):
		case (SPRITE):
		case (CUSTOM):
		default:
			break;
		}
	}

	class Slider2DDisplay implements ControllerDisplay {

		public void display(PApplet theApplet, Controller theController) {

			theApplet.pushStyle();

			if (theController.isInside()) {
				theApplet.fill(theController.color().getForeground());
			} else {
				theApplet.fill(theController.color().getBackground());
			}

			theApplet.rect(0, 0, getWidth(), getHeight());

			if (isCrosshairs) {
				if (theController.isInside()) {
					theApplet.stroke(theController.color().getBackground());
				} else {
					theApplet.stroke(theController.color().getForeground());
				}
				theApplet.line(0, getCursorY(), getWidth(), getCursorY());
				theApplet.line(getCursorX(), 0, getCursorX(), getHeight());
				theApplet.noStroke();
			}

			theApplet.fill(theController.color().getActive());
			theApplet.rect(getCursorX(), getCursorY(), getCursorWidth(), getCursorHeight());

			theApplet.popStyle();

			captionLabel().draw(theApplet, 0, getHeight() + 4);
			valueLabel().draw(theApplet, captionLabel().getWidth() + 4, getHeight() + 4);
		}

	}
}
