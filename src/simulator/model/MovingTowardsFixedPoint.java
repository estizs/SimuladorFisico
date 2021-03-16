package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class MovingTowardsFixedPoint implements ForceLaws{
	private Double _g;
	private Vector2D c;
	
	public MovingTowardsFixedPoint(Vector2D c, double g) {
		_g = g;
		this.c = new Vector2D(c);
	}
	
	public void apply(List<Body> bs) {
		for(Body b : bs)
			b.addForce(c.minus(b.getPosition()).direction().scale(_g * b.getMass()));
	}

}
