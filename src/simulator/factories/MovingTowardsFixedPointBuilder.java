package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws> {
	private static final String _type = "mtcp";
	private static final String _desc = "Moving Towards Fixed Point Force Law";
	private static final double _gravitation = 9.81;
	
	public MovingTowardsFixedPointBuilder() {
		super(_type, _desc);
	}
	
	@Override
	protected ForceLaws createTheInstance(JSONObject data) {
		double g = data.has("g") ? data.getDouble("g") : _gravitation;
		Vector2D c = data.has("c") ? new Vector2D(data.getJSONArray("c").getDouble(0), 
												  data.getJSONArray("c").getDouble(1)) : new Vector2D(0, 0);
		return new MovingTowardsFixedPoint(c, g);
	}
	
	@Override
	protected JSONObject createData() {
		JSONObject data = new JSONObject();
		data.put("c", "origin point");
		data.put("g", "gravitation force on Earth");
		return data;
	}
}
