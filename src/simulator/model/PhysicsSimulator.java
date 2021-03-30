package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class PhysicsSimulator {
	private double dt;
	private ForceLaws fl;
	private List<Body> bodies;
	private double time;
	
	public PhysicsSimulator(double dt, ForceLaws fl) throws IllegalArgumentException {
		if (dt <= 0 || fl == null) throw new IllegalArgumentException();
		this.dt = dt;
		this.fl = fl;
		time = 0.0;
		bodies = new ArrayList<>();
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
		System.out.println(b.getId());
		boolean contiene = bodies.contains(b);
		if (contiene) throw new IllegalArgumentException();
		bodies.add(b);
	}
	
	public JSONObject getState() {
		JSONObject jo = new JSONObject(); 
		JSONArray ja = new JSONArray();
		for (Body b: bodies) ja.put(b.getState());
		jo.put("bodies", ja);
		jo.put("time", time); 
		return jo; 
	}
	
	public String toString() {
		return getState().toString();
	}
}
