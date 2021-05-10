package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class PhysicsSimulator {
	private double dt;
	private ForceLaws fl;
	private List<Body> bodies;
	private List<SimulatorObserver> observers;
	private double time;
	
	public PhysicsSimulator(double dt, ForceLaws fl) throws IllegalArgumentException {
		if (dt <= 0 || fl == null) throw new IllegalArgumentException();
		this.dt = dt;
		this.fl = fl;
		time = 0.0;
		bodies = new ArrayList<>();
		observers = new ArrayList<>();
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
		// Notificamos a todos los observadores que se ha avanzado un paso en la simulación
		for (SimulatorObserver o: observers)
			o.onAdvance(bodies, time);
	}
	
	public void addBody(Body b) throws IllegalArgumentException {
		if (bodies.contains(b)) throw new IllegalArgumentException();
		bodies.add(b);
		// Notificamos a los observadores que se ha añadido un método
		for (SimulatorObserver o: observers)
			o.onBodyAdded(bodies, b);
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
	
	public void reset() {
		// Vaciamos la lista de cuerpos
		bodies.clear(); 
		// El tiempo se pone a 0.0
		time = 0.0;
		// Notificamos a todos los observadores que se ha reseteado
		for (SimulatorObserver o: observers)
			o.onReset(bodies, time, dt, fl.toString());
	}
	
	public void setDeltaTime(double dt) throws IllegalArgumentException {
		if (dt <= 0) throw new IllegalArgumentException("El tiempo real por paso no tiene un valor válido");
		this.dt = dt;
		// Notificamos a todos los observadores de que se ha modificado el delta time
		for (SimulatorObserver o: observers)
			o.onDeltaTimeChanged(dt);
	}
	
	public void setForceLaws(ForceLaws forceLaws) throws IllegalArgumentException {
		if (forceLaws == null) throw new IllegalArgumentException("Se debe seleccionar una ley de fuerza válida");
		fl = forceLaws;
		// Notificamos a todos los observadores que se han modificado las fuerzas
		for (SimulatorObserver o: observers)
			o.onForceLawsChanged(fl.toString());
	}
	
	public void addObserver(SimulatorObserver o) {
		observers.add(o);
		// Notificamos que se ha añadido un observador
		o.onRegister(bodies, time, dt, fl.toString());
	}

	public ForceLaws getForceLaws() {
		return fl;
	}
	
}
