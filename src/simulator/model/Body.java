package simulator.model;

import org.json.JSONObject;

import simulator.misc.Vector2D;

public class Body {
	protected String id;
	protected Vector2D v;
	protected Vector2D f;
	protected Vector2D p;
	protected Double m;
	
	public Body(String id, Vector2D v, Vector2D p, Double m) {
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
		return v;
	}
	public Vector2D getForce() {
		return f;
	}
	public Vector2D getPosition() {
		return p;
	}
	public Double getMass() {
		return m;
	}
	
	void addForce(Vector2D f) {
		this.f =  this.f.plus(f);
	}
	void resetForce() {
		this.f = new Vector2D(0, 0);
	}
	void move(Double t) {
		Vector2D a;
		if (m == 0.0) a = new Vector2D(0, 0);
		// a = f/m
		else a = new Vector2D(f.getX() / m, f.getY() / m);
		
		// p = p + v*t + 1/2*a*t^2
		p = (p.plus(new Vector2D(v.getX() * t, v.getY() * t))).plus(new Vector2D(0.5 * a.getX() * t * t, 0.5 * a.getY() * t * t));
		// v = v + a*t
		v = v.plus(new Vector2D(a.getX() * t, a.getY() * t));
	}
	public JSONObject getState() {
		JSONObject jo = new JSONObject();
		jo.put("id", id);
		jo.put("m", m);
		jo.put("p", p);
		jo.put("v", v);
		jo.put("f", f);
		return jo; 
	}
	public String toString() {
		return getState().toString();
	}
	public boolean equals(Body b) {
		return id == b.id; 
	}
}
