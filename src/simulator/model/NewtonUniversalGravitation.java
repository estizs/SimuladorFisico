package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws{
	private Double _G;
	
	public NewtonUniversalGravitation(Double G) {
		this._G = G;
	}
	
	public void apply(List<Body> bs) {
		// fij = G * (mi * mj) / |pj - pi|^2
		// dij = signo(pj - pi)
		for(Body b1 : bs) {
			Vector2D Fi = new Vector2D(0, 0);
			for(Body b2 : bs) {
				Double fij;
				Vector2D dij;
				if(!b1.equals(b2)) { // un cuerpo no ejerce fuerza sobre sí mismo
					fij = _G * (b1.getMass() * b2.getMass()) 
							/ Math.pow(b2.getPosition().distanceTo(b1.getPosition()), 2);
					dij = (b2.getPosition().minus(b1.getPosition()).direction());
					Fi.plus(new Vector2D(fij * dij.getX(), fij * dij.getY()));
				}
			}
			b1.addForce(Fi);
		}
	}
	
}
