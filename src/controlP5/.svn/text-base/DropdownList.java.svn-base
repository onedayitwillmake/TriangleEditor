package controlP5;

public class DropdownList extends ListBox {

	protected DropdownList(
			ControlP5 theControlP5,
			ControllerGroup theGroup,
			String theName,
			int theX,
			int theY,
			int theW,
			int theH) {
		super(theControlP5, theGroup, theName, theX, theY, theW, theH);
		actAsPulldownMenu(true);
	}

	@Override
	public String stringValue() {
		return captionLabel().toString();
	}

	@Override
	public void setValue(float theValue) {
		for (ListBoxItem l : items) {
			if ((l.value == theValue)) {
				_myValue = l.value;
				setLabel(l.name);
				controlP5.controlbroadcaster().broadcast(new ControlEvent(this), ControlP5Constants.FLOAT);
				break;
			}
		}
	}

	@Override
	public float getValue() {
		return _myValue;
	}
}
