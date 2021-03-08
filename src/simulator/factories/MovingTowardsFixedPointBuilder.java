package simulator.factories;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws> {
	private static final String _type = "mtcp";
	private static final Vector2D _c = new Vector2D(0, 0);
	private static final Double _g = 9.81;
}
