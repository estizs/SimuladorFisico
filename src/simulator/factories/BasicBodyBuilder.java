package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;

public class BasicBodyBuilder extends Builder<Body> {
	public static final String _type = "basic";
	public static final String _desc = "Default Body";
	public static final int numArgs = 4;
	
	public BasicBodyBuilder() {
		super(_type, _desc);
	}

	@Override
	protected JSONObject createData() {
		JSONObject data = new JSONObject();
		data.put("id", "the identifier");
		data.put("p", "the position");
		data.put("v", "the velocity");
		data.put("m", "the mass");
		return data;
	}

	@Override
	protected Body createTheInstance(JSONObject data) {
		// Comprobamos si los campos coinciden
		if(!data.has("id") || !data.has("m") || !data.has("p") || !data.has("v") || data.length() != numArgs)
			return null;
		String id = data.getString("id");
		double m = data.getDouble("m");
		JSONArray pos = data.getJSONArray("p");
		Vector2D p = new Vector2D(pos.getDouble(0), pos.getDouble(1));
		JSONArray vel = data.getJSONArray("v");
		Vector2D v = new Vector2D(vel.getDouble(0), vel.getDouble(1));
		return new Body(id, v, p, m);
	}
}
