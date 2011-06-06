package controlP5;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ControllerProperty implements Serializable {

	String setter;
	String getter;
	Class<?> type;
	Object value;
	String name;
	transient boolean active;
	transient ControllerInterface controller;
	
	ControllerProperty(ControllerInterface theController, String theSetter, String theGetter) {
		controller = theController;
		name = theController.name();
		setter = theSetter;
		getter = theGetter;
		active = true;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		 
		ControllerProperty p = (ControllerProperty) o;
		if (!name.equals(p.name) || !setter.equals(p.setter) || !getter.equals(p.getter)) {
			return false;
		}
		return true;
	}

	
	public int hashCode() {
		int result = 17;
		result = 37 * result + (name != null ? name.hashCode() : 0);
		result = 37 * result + (setter != null ? setter.hashCode() : 0);
		result = 37 * result + (getter != null ? getter.hashCode() : 0);
		return result;
	}
	
	public void disable() {
		active = false;
	} 
	
	public void enable() {
		active = true;
	}
	
	public String toString() {
		return name+" "+setter+", "+getter;
	}
	
}
