package controlP5;

import java.util.HashMap;
import processing.core.PApplet;
import processing.core.PVector;

public class Tooltip {

	private ControllerDisplay _myDisplay;

	private PVector position;

	private PVector currentPosition;

	private PVector previousPosition;

	private PVector offset;

	private Controller _myController;

	private long startTime = 0;

	private long _myDelayInMillis = 1000;

	private int _myMode = ControlP5.INACTIVE;

	private int _myWidth = 100;

	private int _myHeight = 20;

	private int _myBackgroundColor = 0xffffffb4;

	private int _myMaxAlpha = 255;

	private int _myAlpha = _myMaxAlpha;

	private HashMap<Controller, String> map;

	private Label _myLabel;

	private boolean enabled = true;

	private int _myBorder;

	Tooltip() {
		position = new PVector();
		currentPosition = new PVector();
		previousPosition = new PVector();
		offset = new PVector(0, 24, 0);
		map = new HashMap<Controller, String>();
		_myLabel = new Label("", 100, 10);
		_myLabel.setColor(0xff000000);
		_myLabel.multiline(true);
		_myLabel.update();
		setDisplay(new TooltipDisplay());
		setBorder(4);
	}

	/**
	 * sets the border of the tooltip, the default border is 4px.
	 * 
	 * @param theValue
	 */
	public void setBorder(int theValue) {
		_myBorder = theValue;
		_myLabel.style().setMargin(_myBorder, _myBorder, _myBorder, _myBorder);
	}

	/**
	 * returns the value of the border
	 * 
	 * @return
	 */
	public int getBorder() {
		return _myBorder;
	}

	/**
	 * sets the transparency of the default background, default value is 200
	 * 
	 * @param theValue
	 */
	public void setAlpha(int theValue) {
		_myMaxAlpha = theValue;
	}

	private void updateText(String theText) {
		int n = 1;
		for (char c : theText.toCharArray()) {
			if (c == '\n') {
				n++;
			}
		}
		if (_myLabel.height() != _myLabel.getLineHeight() * n) {
			_myLabel.setHeight(_myLabel.getLineHeight() * n);
		}
		_myLabel.set(theText);
		_myLabel.update();
		_myWidth = Label.bitFontRenderer.getWidth(_myLabel);
		_myHeight = _myLabel.height();

	}

	public void draw(ControlWindow theWindow) {

		if (enabled) {
			if (_myMode >= ControlP5.WAIT) {

				previousPosition.set(currentPosition);
				currentPosition.set(theWindow.mouseX, theWindow.mouseY,0);

				if (_myController != null) {
					if (_myController.controlWindow().equals(theWindow)) {
						switch (_myMode) {
						case (ControlP5.WAIT):
							if (moved()) {
								startTime = System.nanoTime();
							}

							if (System.nanoTime() > startTime + (_myDelayInMillis * 1000000)) {
								position.set(currentPosition);
								if (position.x > (_myController.controlWindow().papplet().width - _myWidth)) {
									position.sub(new PVector(_myWidth, 0, 0));
								}
								_myMode = ControlP5.FADEIN;
								startTime = System.nanoTime();
								_myAlpha = 0;
							}
							break;

						case (ControlP5.FADEIN):
							float t1 = System.nanoTime() - startTime;
							_myAlpha = (int) PApplet.map(t1, 0, 200 * 1000000, 0, _myMaxAlpha);
							if (_myAlpha >= 250) {
								_myMode = ControlP5.IDLE;
								_myAlpha = 255;
							}
							break;
						case (ControlP5.IDLE):
							break;
						case (ControlP5.FADEOUT):
							float t2 = System.nanoTime() - startTime;
							_myAlpha = (int) PApplet.map(t2, 0, 200 * 1000000, _myMaxAlpha, 0);
							if (_myAlpha <= 0) {
								_myMode = ControlP5.DONE;
							}
							break;
						case (ControlP5.DONE):
							_myController = null;
							_myMode = ControlP5.INACTIVE;
						}

						_myAlpha = PApplet.max(0, PApplet.min(_myAlpha, _myMaxAlpha));

						if (_myMode > ControlP5.WAIT) {
							theWindow.papplet().pushMatrix();
							theWindow.papplet().translate(position.x, position.y);
							theWindow.papplet().translate(offset.x, offset.y);
							_myDisplay.display(theWindow.papplet(), null);
							theWindow.papplet().popMatrix();
						}
						if (_myMode < ControlP5.FADEOUT) {
							if (moved()) {
								deactivate(0);
							}
						}
					}
				}
			}
		}
	}

	private boolean moved() {
		return PApplet.abs(PApplet.dist(previousPosition.x, previousPosition.y, currentPosition.x, currentPosition.y)) > 1;
	}

	/**
	 * A tooltip is activated when entered by the mouse, after a given delay time
	 * the Tooltip starts to fade in. Use setDelay(long) to adjust the default
	 * delay time of 1000 millis.
	 * 
	 * @param theMillis
	 */
	public void setDelay(long theMillis) {
		_myDelayInMillis = theMillis;
	}

	/**
	 * a Tooltip is activated when the mouse enters a controller.
	 * 
	 * @param theController
	 */
	protected void activate(Controller theController) {
		if (map.containsKey(theController)) {
			startTime = System.nanoTime();
			_myController = theController;
			currentPosition.set(theController.controlWindow().mouseX, theController.controlWindow().mouseY,0);
			updateText(map.get(_myController));
			_myMode = ControlP5.WAIT;
		}
	}

	protected void deactivate(int theNum) {
		if (theNum == 0) {
			if (_myMode >= ControlP5.IDLE) {
				if (_myMode < ControlP5.FADEOUT)
					startTime = System.nanoTime();
				_myMode = ControlP5.FADEOUT;
			}
		} else {
			_myMode = (_myMode >= ControlP5.IDLE) ? ControlP5.FADEOUT : ControlP5.DONE;
		}
	}

	/**
	 * A custom display can be set for a Tooltip. The default display class can be
	 * found at the bottom of the Tooltip source.
	 * 
	 * @see controlP5.ControllerDisplay
	 * @param theDisplay
	 */
	public void setDisplay(ControllerDisplay theDisplay) {
		_myDisplay = theDisplay;
	}

	/**
	 * registers a controller with the Tooltip, when activating the tooltip for a
	 * particular controller, the registered text (second parameter) will be
	 * displayed.
	 * 
	 * @param theController
	 * @param theText
	 */
	public void register(Controller theController, String theText) {
		map.put(theController, theText);
		theController.registerProperty("setTooltipEnabled", "isTooltipEnabled");
	}

	/**
	 * removes a controller from the tooltip
	 * 
	 * @param theController
	 */
	public void unregister(Controller theController) {
		map.remove(theController);
		theController.removeProperty("setTooltipEnabled", "isTooltipEnabled");
	}

	/**
	 * with the default display, the width of the tooltip is set automatically,
	 * therefore setWidth() does not have any effect without changing the default
	 * display to a custom ControllerDisplay.
	 * 
	 * @see controlP5.ControllerDisplay
	 * @see controlP5.Tooltip#setDisplay(ControllerDisplay)
	 */
	public void setWidth(int theWidth) {
		_myWidth = theWidth;
	}

	/**
	 * @see controlP5.Tooltip#setWidth(int)
	 * @param theHeight
	 */
	public void setHeight(int theHeight) {
		_myHeight = theHeight;
	}

	/**
	 * adds an offset to the position of the controller relative to the mouse
	 * cursor's position. default offset is (10,20)
	 * 
	 * @param theX
	 * @param theY
	 */
	public void setPositionOffset(float theX, float theY) {
		offset.x = theX;
		offset.y = theY;
	}

	/**
	 * disables the Tooltip on a global level, when disabled, tooltip will not
	 * respond to any registered controller. to disable a tooltip for aparticular
	 * controller, used unregister(Controller)
	 * 
	 * @see controlP5.Tooltip#unregister(Controller)
	 */
	public void disable() {
		enabled = false;
	}

	/**
	 * in case the tooltip is disabled, use enable() to turn the tooltip back on.
	 */
	public void enable() {
		enabled = true;
	}

	/**
	 * check if the tooltip is enabled or disabled
	 * 
	 * @return boolean
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * sets the Label to a custom label and replaces the default label.
	 * 
	 * @param theLabel
	 */
	public void setLabel(Label theLabel) {
		_myLabel = theLabel;
	}

	/**
	 * returns the current Label
	 * 
	 * @return Label
	 */
	public Label getLabel() {
		return _myLabel;
	}

	/**
	 * sets the background color of the tooltip, the default color is a dark grey
	 * 
	 * @param theColor
	 */
	public void setColorBackground(int theColor) {
		_myBackgroundColor = theColor;
	}

	/**
	 * sets the text color of the tooltip's label, the default color is a white
	 * 
	 * @param theColor
	 */
	public void setColorLabel(int theColor) {
		_myLabel.setColor(theColor);
	}

	class TooltipDisplay implements ControllerDisplay {

		public void display(PApplet theApplet, Controller theController) {
			theApplet.pushStyle();
			theApplet.fill(_myBackgroundColor, _myAlpha);
			theApplet.rect(0, 0, _myWidth + _myBorder * 2, _myHeight + _myBorder);
			theApplet.triangle(3,0,7,-4,11,0);
			theApplet.tint(255, PApplet.map(_myAlpha, 0, _myMaxAlpha, 0, 255));
			_myLabel.draw(theApplet);
			theApplet.tint(255);
			theApplet.popStyle();
		}
	}
}
