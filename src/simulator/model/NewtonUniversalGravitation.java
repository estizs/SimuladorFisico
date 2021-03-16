package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws{
	private double _G;
	
	public NewtonUniversalGravitation(double G) {
		this._G = G;
	}
	
	public void apply(List<Body> bs) {
		// fij = G * (mi * mj) / |pj - pi|^2
		// dij = signo(pj - pi)
		for(Body b1 : bs)
			for(Body b2 : bs)
				if(!b1.equals(b2)) // un cuerpo no ejerce fuerza sobre sí mismo
					b1.addForce(force(b1, b2));
	}
	
	private Vector2D force(Body a, Body b) {
		Vector2D delta = b.getPosition().minus(a.getPosition());
		double dist = delta.magnitude();
	    double magnitude = dist > 0 ? (_G * a.getMass() * b.getMass()) / (dist * dist) : 0.0;
	    return delta.direction().scale(magnitude);
	}
}
