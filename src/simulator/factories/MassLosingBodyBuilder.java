package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.MassLosingBody;

public class MassLosingBodyBuilder extends Builder<Body> {
	private static final String _type = "mlb";
	private static final String _desc = "Mass losing Body";
	private static final int numArgs = 6;
	
	public MassLosingBodyBuilder() {
		super(_type, _desc);
	}

	@Override
	protected Body createTheInstance(JSONObject data) {
		// Comprobamos si los campos coinciden
		if(!data.has("id") || !data.has("m") || !data.has("p") || !data.has("v") || 
		   !data.has("freq") || !data.has("factor") || data.length() != numArgs)
			return null;
		
		String id = data.getString("id");
		double m = data.getDouble("m");
		double freq = data.getDouble("freq");
		double factor = data.getDouble("factor");
		JSONArray pos = data.getJSONArray("p");
		Vector2D p = new Vector2D(pos.getDouble(0), pos.getDouble(1));
		JSONArray vel = data.getJSONArray("v");
		Vector2D v = new Vector2D(vel.getDouble(0), vel.getDouble(1));
		return new MassLosingBody(id, v, p, m, factor, freq);
	}
	
	@Override
	protected JSONObject createData() {
		JSONObject data = new JSONObject();
		data.put("id", "the identifier");
		data.put("p", "the position");
		data.put("v", "the velocity");
		data.put("m", "the mass");
		data.put("freq", "frequency within a Mass losing Body loses mass");
		data.put("factor", "factor that indicates the quantity of mass lost by a Mass losing Body");
		return data;
	}
}
