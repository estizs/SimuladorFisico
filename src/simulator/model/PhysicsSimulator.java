package simulator.model;

import java.util.List;

import org.json.JSONObject;

public class PhysicsSimulator {
	private Double dt;
	private ForceLaws fl;
	private List<Body> bodies;
	private Double time;
	
	public PhysicsSimulator(Double dt, ForceLaws fl) { 
		this.dt = dt;
		this.fl = fl;
		time = 0.0;
	}
	
	public void advance() {
		// llama al método resetForce() de todos los cuerpos
		
		// llama al método apply() de las leyes de fuerza
		
		// llama a move(dt) para cada cuerpo, donde dt es el tiempo real por paso
		
		// finalmente incrementa el tiempo actual en dt segundos
	}
	
	public void addBody(Body b) throws IllegalArgumentException {
		
	}
	
	public JSONObject getState() {
		return null;
	}
	
	public String toString() {
		return getState().toString();
	}
}
