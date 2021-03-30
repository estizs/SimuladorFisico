package simulator.model;

import org.json.JSONObject;

import simulator.misc.Vector2D;

public class Body {
	protected String id;
	protected Vector2D v;
	protected Vector2D f;
	protected Vector2D p;
	protected double m;
	
	public Body(String id, Vector2D v, Vector2D p, double m) {
		this.id = id;
		this.v = v;
		this.f = new Vector2D(0, 0);
		this.p = p;
		this.m = m;
	}
	
	public String getId() {
		return id;
	}
	public Vector2D getVelocity() {
		return new Vector2D(v.getX(), v.getY());
	}
	public Vector2D getForce() {
		return new Vector2D(f.getX(), f.getY());
	}
	public Vector2D getPosition() {
		return new Vector2D(p.getX(), p.getY());
	}
	public double getMass() {
		return m;
	}
	
	void addForce(Vector2D f) {
		this.f =  (this.f).plus(f);
	}
	void resetForce() {
		this.f = new Vector2D(0, 0);
	}
	void move(double t) {
		Vector2D a;
		if (m == 0.0) a = new Vector2D(0, 0);
		// a = f/m
		else a = f.scale(1.0 / m);
		
		// p = p + v*t + 1/2*a*t^2
		p = p.plus(v.scale(t).plus(a.scale(0.5 * t * t)));
		// v = v + a*t
		v = v.plus(a.scale(t));
	}
	public JSONObject getState() {
		JSONObject jo = new JSONObject();
		jo.put("id", getId());
		jo.put("m", getMass());
		jo.put("p", getPosition().asJSONArray());
		jo.put("v", getVelocity().asJSONArray());
		jo.put("f", getForce().asJSONArray());
		return jo; 
	}
	public String toString() {
		return getState().toString();
	}
	public boolean equals(Body b) {
		return id == b.getId(); 
	}
}
