package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NoForce;

public class NoForceBuilder extends Builder<ForceLaws> {
	private static final String _type = "nf";
	private static final String _desc = "No Force";
	
	public NoForceBuilder() {
		super(_type, _desc);
	}

	@Override
	protected ForceLaws createTheInstance(JSONObject data) {
		return new NoForce();
	}
	
	@Override
	protected JSONObject createData() {
		JSONObject data = new JSONObject();
		return data;
	}
}
