package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;

public class EpsilonEqualStates implements StateComparator{
	private double eps;
	
	public EpsilonEqualStates(double eps) {
		this.eps = eps;
	}
	
	@Override
	public boolean equal(JSONObject s1, JSONObject s2) {
		// Valor de sus claves "time" son distintas
		if (s1.getDouble("time") != s2.getDouble("time")) return false;
		
		// Comprobamos si ambos estados tienen el mismo número de cuerpos
		JSONArray bodies1 = s1.getJSONArray("bodies");
		JSONArray bodies2 = s2.getJSONArray("bodies");
		if(bodies1.length() != bodies2.length()) return false;
		
		// Para todo i, el i-ésimo cuerpo tienen el mismo valor en sus claves "id" y
		// las claves "m", "p", "v" y "f" son iguales módulo epsilon
		for (int i = 0; i < bodies1.length(); i++) {
			JSONObject o1 = bodies1.getJSONObject(i);
			JSONObject o2 = bodies2.getJSONObject(i);
			
			JSONArray p1aux = o1.getJSONArray("p");
			JSONArray p2aux = o2.getJSONArray("p");
			Vector2D p1 = new Vector2D(p1aux.getDouble(0), p1aux.getDouble(1));
			Vector2D p2 = new Vector2D(p2aux.getDouble(0), p2aux.getDouble(1));
			
			JSONArray v1aux = o1.getJSONArray("v");
			JSONArray v2aux = o2.getJSONArray("v");
			Vector2D v1 = new Vector2D(v1aux.getDouble(0), v1aux.getDouble(1));
			Vector2D v2 = new Vector2D(v2aux.getDouble(0), v2aux.getDouble(1));
			
			
			JSONArray f1aux = o1.getJSONArray("f");
			JSONArray f2aux = o2.getJSONArray("f");
			Vector2D f1 = new Vector2D(f1aux.getDouble(0), f1aux.getDouble(1));
			Vector2D f2 = new Vector2D(f2aux.getDouble(0), f2aux.getDouble(1));
			
			if (!o1.getString("id").equals(o2.getString("id")))
					return false;
			if((Math.abs(o1.getDouble("m") - o2.getDouble("m")) > eps))
				return false;
			if((p1.distanceTo(p2) > eps))
				return false;
			if((v1.distanceTo(v2) > eps))
				return false;
			if((f1.distanceTo(f2) > eps))
				return false;
		}
		return true;
	}

}
