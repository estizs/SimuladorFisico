package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class MovingTowardsFixedPoint implements ForceLaws{
	public static final Double _g = -9.81;
	
	public void apply(List<Body> bs) {
		for(Body b : bs) {
			b.addForce(new Vector2D(b.getMass() * b.getPosition().direction().getX() * _g, 
									b.getMass() * b.getPosition().direction().getY() * _g));
		} 
	}

}
