package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws> {
	private static final String _type = "nlug";
	private static final String _desc = "Newton's best law";
	private static final double _gravitationalConstant = 6.67e10-11;
	
	public NewtonUniversalGravitationBuilder() {
		super(_type, _desc);
	}
	
	@Override
	protected ForceLaws createTheInstance(JSONObject data) {
		double g = data.has("g") ? data.getDouble("g") : _gravitationalConstant;
		return new NewtonUniversalGravitation(g);
	}
	
	@Override
	protected JSONObject createData() {
		JSONObject data = new JSONObject();
		data.put("g", "gravitational constant");
		return data;
	}
}
