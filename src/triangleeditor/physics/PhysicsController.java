package triangleeditor.physics;

import org.jbox2d.collision.AABB;
import org.jbox2d.collision.shapes.CircleDef;
import org.jbox2d.collision.shapes.PolygonDef;
import org.jbox2d.collision.shapes.ShapeDef;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.DebugDraw;
import org.jbox2d.dynamics.World;
import org.jbox2d.testbed.ProcessingDebugDraw;
import org.jbox2d.testbed.TestSettings;

import processing.core.PApplet;

public class PhysicsController {
	
	
	private World					_world;
	private AABB					_worldAABB;
	private Vec2					_dimensions;
	private Vec2					_gravity;
	
	private TestSettings			_settings;
	private ProcessingDebugDraw		_debugDraw;
	
	
	// Next shape properties
	public float m_density;
	public float m_restitution;
	public float m_friction;
	public boolean m_bullet;
	public boolean m_sensor;
	
	private PApplet app;
	public PhysicsController(PApplet appRef) {
		app = appRef;
	}
	
	public void createWorld(float screenW, float screenH,
			   float gravX, float gravY,
			   float screenAABBWidth, float screenAABBHeight,
			   float borderBoxWidth, float borderBoxHeight,
			   float pixelsPerMeter) {
		
		_dimensions = new Vec2(screenW, screenH);
		_debugDraw = new ProcessingDebugDraw( app );
		
		_gravity = new Vec2(gravX, -gravY);
		_debugDraw.setCamera(0, 0, pixelsPerMeter);
		
		Vec2 minWorldAABB = new Vec2(-screenAABBWidth*.5f/pixelsPerMeter, -screenAABBHeight*.5f/pixelsPerMeter);
		Vec2 maxWorldAABB = minWorldAABB.mul(-1.0f);
		boolean doSleep = true;
		
		
		setupTestSettings();
		
		_worldAABB = new AABB(minWorldAABB,maxWorldAABB);
		_world = new World(_worldAABB, _gravity,doSleep);
		_world.setDebugDraw( _debugDraw );
		_world.setDrawDebugData(false);
		
//		app.registerDraw(this);
	}
	
	private void setupTestSettings() {
		_settings = new TestSettings();
		_settings.drawAABBs = true;
		
	}
	
	private void setupWorldBoundary() {
		createHollowBox(_dimensions.x*.5f, _dimensions.y*.5f, _dimensions.x, _dimensions.y, 10.0f);
	}
	
	public Body[] createHollowBox(float centerX, float centerY, float width, float height, float thickness) {
		Body[] result = new Body[4];
		result[0] = createRect(centerX - width*.5f - thickness*.5f, centerY - height*.5f - thickness*.5f,
				   			   centerX - width*.5f + thickness*.5f, centerY + height*.5f + thickness*.5f);
		result[1] = createRect(centerX + width*.5f - thickness*.5f, centerY - height*.5f - thickness*.5f,
				   			   centerX + width*.5f + thickness*.5f, centerY + height*.5f + thickness*.5f);
		result[2] = createRect(centerX - width*.5f - thickness*.5f, centerY + height*.5f - thickness*.5f,
				   			   centerX + width*.5f + thickness*.5f, centerY + height*.5f + thickness*.5f);
		result[3] = createRect(centerX - width*.5f - thickness*.5f, centerY - height*.5f - thickness*.5f,
				   			   centerX + width*.5f + thickness*.5f, centerY - height*.5f + thickness*.5f);
		return result;
	}
	
	/**
	 * Create a circle in screen coordinates
	 * @param x
	 * @param y
	 * @param r
	 * @return
	 */
	public Body createCircle(float x, float y, float r) {
		Vec2 center = screenToWorld(x,y);
		float rad = screenToWorld(r, new Vec2(1,0));
		
		CircleDef cd = new CircleDef();
		cd.radius = rad;
		setShapeDefProperties(cd);
		
		BodyDef bd = new BodyDef();
		setBodyDefProperties(bd);
		
		Body b = _world.createBody(bd);
		b.createShape(cd);
		if (m_density > 0.0f) b.setMassFromShapes();
		
		b.setXForm(center, 0.0f);
		
		return b;
	}
	
	/**
	 * Create a rectangle given by screen coordinates of corners.
	 * @param x0
	 * @param y0
	 * @param x1
	 * @param y1
	 * @return
	 */
	public Body createRect(float x0, float y0, float x1, float y1) {
		float cxs = (x0 + x1) * .5f;
		float cys = (y0 + y1) * .5f;
		float wxs = MathUtils.abs(x1-x0);
		float wys = MathUtils.abs(y1-y0);
		//System.out.println("Screen: ("+cxs + ","+cys+")");
		Vec2 center = screenToWorld(cxs, cys);
		//System.out.println("World: "+center);
		float halfWidthWorld = .5f* screenToWorld(wxs, new Vec2(1,0));
		float halfHeightWorld = .5f* screenToWorld(wys, new Vec2(1,0));
		//System.out.println("Half Width world: "+halfWidthWorld);
		PolygonDef pd = new PolygonDef();
		pd.setAsBox(halfWidthWorld, halfHeightWorld);
		setShapeDefProperties(pd);
		
		BodyDef bd = new BodyDef();
		setBodyDefProperties(bd);
		
		Body b = _world.createBody(bd);
		b.createShape(pd);
		if (m_density > 0.0f) b.setMassFromShapes();
		
		b.setXForm(center, 0.0f);
		
		return b;
	}
	
	/**
	 * Create a polygon based on vertices.
	 * <BR><BR>
	 * Polygons must be:
	 * <ul>
	 * <li>Ordered clockwise in screen coordinates (which
	 * becomes counterclockwise in world coordinates).
	 * <li>Non self-intersecting.
	 * <li>Convex
	 * </ul>
	 * Failure to adhere to any of these restrictions may cause
	 * simulation crashes or problems.  In particular, if your
	 * objects are showing up as static objects instead of dynamic
	 * ones, and are not colliding correctly, you have probably 
	 * not met the clockwise ordering requirement.
	 * <BR><BR>
	 * This can be called with any number of vertices passed as
	 * pairs of interleaved floats, for instance:
	 * <pre>
	 * createPolygon(x0,y0,x1,y1,x2,y2,x3,y3);</pre>
	 * or
	 * <pre>
	 * createPolygon(x0,y0,x1,y1,x2,y2,x3,y3,x4,y4,x5,y5);</pre>
	 * or
	 * <pre>
	 * float[] xyInterleaved = {x0,y0,x1,y1,x2,y2,x3,y3,x4,y4};
	 * createPolygon(xyInterleaved);</pre>
	 * are all fine.
	 * @param vertices Any number of pairs of x,y floats, or an array of the same (screen coordinates)
	 * @return
	 */
	public Body createPolygon(float... vertices) {
		if (vertices.length % 2 != 0) 
			throw new IllegalArgumentException("Vertices must be given as pairs of x,y coordinates, " +
											   "but number of passed parameters was odd.");
		int nVertices = vertices.length / 2;
		PolygonDef pd = new PolygonDef();
		for (int i=0; i<nVertices; ++i) {
			Vec2 v = screenToWorld(vertices[2*i],vertices[2*i+1]);
			pd.addVertex(v);
		}
		setShapeDefProperties(pd);
		
		BodyDef bd = new BodyDef();
		setBodyDefProperties(bd);
		
		Body b = _world.createBody(bd);
		b.createShape(pd);
		if (m_density > 0.0f)
			b.setMassFromShapes();
		
		return b;
	}
	
	/**
	 * Update the Box2D simualtion
	 */
	public void update() {
		_world.setWarmStarting(_settings.enableWarmStarting);
		_world.setPositionCorrection(_settings.enablePositionCorrection);
		_world.setContinuousPhysics(_settings.enableTOI);
		_world.step(1.0f/_settings.hz, _settings.iterationCount);
	}

	/**
	 * Draw
	 */
	public void draw() {		
		debugDraw();	
	}
	
	/**
	 * Debug drawing Box2d
	 */
	private void debugDraw() {
		_debugDraw.setFlags(0);
		if (_settings.drawShapes) _debugDraw.appendFlags(DebugDraw.e_shapeBit);
		if (_settings.drawJoints) _debugDraw.appendFlags(DebugDraw.e_jointBit);
		if (_settings.drawCoreShapes) _debugDraw.appendFlags(DebugDraw.e_coreShapeBit);
		if (_settings.drawAABBs) _debugDraw.appendFlags(DebugDraw.e_aabbBit);
		if (_settings.drawOBBs) _debugDraw.appendFlags(DebugDraw.e_obbBit);
		if (_settings.drawPairs) _debugDraw.appendFlags(DebugDraw.e_pairBit);
		if (_settings.drawCOMs) _debugDraw.appendFlags(DebugDraw.e_centerOfMassBit);

		_world.setDrawDebugData(true);
		_world.drawDebugData();
		_world.setDrawDebugData(false);		
	}
	
	
	/**
	 * Sets the body def properties based on the current state
	 * of the physics handler.
	 * 
	 * @param bd
	 */
	private void setBodyDefProperties(BodyDef bd) {
		bd.isBullet = m_bullet;
	}
	
	/**
	 * Sets the shape def properties based on the current state
	 * of the physics handler.
	 * 
	 * @param sd Shape def to set
	 */
	private void setShapeDefProperties(ShapeDef sd) {
		sd.density = m_density;
		sd.friction = m_friction;
		sd.restitution = m_restitution;
		sd.isSensor = m_sensor;
	}
	
// Screen space to world space conversions
	
	/** Screen space to world space conversion for position. */
	public float screenToWorldX(float x, float y) {
		return _debugDraw.getScreenToWorld(x,y).x;
	}
	
	/** Screen space to world space conversion for position. */
	public float screenToWorldY(float x, float y) {
		return _debugDraw.getScreenToWorld(x,y).y;
	}
	
	/** Screen space to world space conversion for position. */
	public Vec2 screenToWorld(float x, float y) {
		return _debugDraw.getScreenToWorld(x,y);
	}
	
	/** Screen space to world space conversion for position. */
	public Vec2 screenToWorld(Vec2 v) {
		return _debugDraw.getScreenToWorld(v);
	}
	
	/** Screen length to world length, on the given vector direction*/
	public float screenToWorld(float length, Vec2 vector){
		Vec2 ret = new Vec2(vector);
		ret.normalize();
		ret.mulLocal( length);
		_debugDraw.getViewportTranform().vectorInverseTransform(ret, ret);
		return ret.length();
	}
	
	/** World space to screen space conversion for position. */
	public float worldToScreenX(float x, float y) {
		return _debugDraw.getWorldToScreen(x, y).x;
	}
	
	/** World space to screen space conversion for position. */
	public float worldToScreenY(float x, float y) {
		return _debugDraw.getWorldToScreen(x, y).y;
	}
	
	/** World space to screen space conversion for position. */
	public Vec2 worldToScreen(float x, float y) {
		return _debugDraw.getWorldToScreen(x, y);
	}
	
	/** World space to screen space conversion for position. */
	public Vec2 worldToScreen(Vec2 v) {
		return _debugDraw.getWorldToScreen(v);
	}
	
	/** World length to screen length, on the given vector direction*/
	public float worldToScreen(float length, Vec2 vector){
		Vec2 ret = new Vec2(vector);
		ret.normalize();
		ret.mulLocal( length);
		_debugDraw.getViewportTranform().vectorTransform(ret, ret);
		return ret.length();
	}
	
	public Vec2 screenToWorldVector(Vec2 screenV) {
		Vec2 ret = new Vec2();
		_debugDraw.getViewportTranform().vectorInverseTransform(screenV, ret);
		if(_debugDraw.getViewportTranform().isYFlip()){
			ret.y *= -1;
		}
		return ret;
	}
	
	public Vec2 screenToWorldVector(float sx, float sy) {
		Vec2 ret = new Vec2(sx, sy);
		_debugDraw.getViewportTranform().vectorInverseTransform(ret, ret);
		if(_debugDraw.getViewportTranform().isYFlip()){
			ret.y *= -1;
		}
		return ret;
	}
	
	public Vec2 worldToScreenVector(Vec2 worldV) {
		Vec2 ret = new Vec2();
		_debugDraw.getViewportTranform().vectorTransform(worldV, ret);
		if(_debugDraw.getViewportTranform().isYFlip()){
			ret.y *= -1;
		}
		return ret;
	}
	
	public Vec2 worldToScreenVector(float wx, float wy) {
		Vec2 ret = new Vec2(wx, wy);
		_debugDraw.getViewportTranform().vectorTransform(ret, ret);
		if(_debugDraw.getViewportTranform().isYFlip()){
			ret.y *= -1;
		}
		return ret;
	}

	public World get_world() { return _world; }
}
