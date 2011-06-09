package triangleeditor.gui;

import java.lang.reflect.Method;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.Body;

import processing.core.PApplet;
import triangleeditor.GridTriangle;
import triangleeditor.SaveLoadController;
import triangleeditor.TriangleEditor;
import controlP5.ControlEvent;
import controlP5.ControlListener;
import controlP5.ControlP5;
import controlP5.Controller;

public class GuiController implements ControlListener {
	private ControlP5 _controls;
	
	private GridTriangle _activeTriangle;
	
	// State
	private Boolean		_isActive = true;

	private TriangleEditor app;
	public GuiController(TriangleEditor appRef ) {
		app = appRef;
		_controls = new ControlP5( appRef );
		_controls.addListener( this );
		
		Controller controller;
		_controls.addButton("triangleRotate", 1).setLabel("rotate");
		_controls.addButton("triangleDelete", 1).setLabel("delete");
		controller = _controls.addSlider("triangleModifyRestitution", 0.0f, 1.0f).linebreak().linebreak();
		controller.setLabel("Restitution");
		controller.setColorCaptionLabel(0x0);
		
		//
		
		_controls.addButton("loadFile", 0);
		_controls.addButton("saveFile", 0);
		
	}
	
	@Override
	public void controlEvent(ControlEvent theEvent) {

		String methodName = theEvent.controller().name();
		
		try {
			Class cl = Class.forName("triangleeditor.gui.GuiController");
			Method mthd = cl.getMethod(methodName, Controller.class);
			
			mthd.invoke(this, theEvent.controller() );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateProps() {
		if( _activeTriangle == null || _activeTriangle.get_body() == null ) return;
		
		_controls.getController("triangleModifyRestitution").setValue(_activeTriangle.get_body().m_shapeList.getRestitution() );
	}
	
//////////////////////////////////////////////////////////////////////
	public void triangleRotate( Controller controller ) {
		if( _activeTriangle == null ) return;
		
		app.rotateTriangle( _activeTriangle );
		app.createTriangleBody( _activeTriangle );
	}
	
	public void triangleDelete( Controller controller ) {
		if( _activeTriangle == null ) return;
		
		app.destroyTriangle( _activeTriangle );
	}
	
	public void triangleModifyRestitution( Controller controller ) {
		if( _activeTriangle == null ) return;

		// Modify restitution of all shapes
		Body body = _activeTriangle.get_body();
		Shape shape = body.m_shapeList;
		while( shape != null ) {
			shape.setRestitution( controller.value() * 2 );
			shape = shape.m_next;
		}
	}
	
	public void loadFile( Controller controller ) {
		SaveLoadController.getInstance().loadRepresentationFromFileDialog();
	}
	
	public void toggle() {
		_isActive = !_isActive;
		
		if( _isActive ) {
			_controls.show();
		} else {
			_controls.hide();
		}
	}
	
	
	public GridTriangle get_activeTriangle() { return _activeTriangle; }
	public void set_activeTriangle(GridTriangle anActiveTriangle) {
		
		if( anActiveTriangle == null ) return;
		if( anActiveTriangle.get_body() == null ) return; // Cannot be active triangle if does not contain Box2DBody
		
		if( _activeTriangle != null) {
			_activeTriangle.set_isActive( false );
		}

		_activeTriangle = anActiveTriangle;
		_activeTriangle.set_isActive( true );
		
		updateProps();
	}
	
	public Boolean get_isActive() { return _isActive; }
}
