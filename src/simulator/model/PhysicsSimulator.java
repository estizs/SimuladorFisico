package simulator.model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class PhysicsSimulator {
	private Double dt;
	private ForceLaws fl;
	private List<Body> bodies;
	private Double time;
	
	public PhysicsSimulator(Double dt, ForceLaws fl) throws IllegalArgumentException {
		if (dt <= 0 || fl == null) throw new IllegalArgumentException();
		this.dt = dt;
		this.fl = fl;
		time = 0.0;
	}
	
	public void advance() {
		// llama al método resetForce() de todos los cuerpos
		for (Body b: bodies) b.resetForce();
		// llama al método apply() de las leyes de fuerza
		fl.apply(bodies);
		// llama a move(dt) para cada cuerpo, donde dt es el tiempo real por paso
		for (Body b: bodies) b.move(dt);
		// finalmente incrementa el tiempo actual en dt segundos
		time += dt;
	}
	
	public void addBody(Body b) throws IllegalArgumentException {
		if (bodies.contains(b)) throw new IllegalArgumentException();
		bodies.add(b); 
	}
	
	public JSONObject getState() {
		JSONObject jo = new JSONObject(); 
		jo.put("time", time); 
		JSONArray ja = new JSONArray();
		for (Body b: bodies) ja.put(b.getState());
		jo.put("bodies", ja);
		return jo; 
	}
	
	public String toString() {
		return getState().toString();
	}
}
