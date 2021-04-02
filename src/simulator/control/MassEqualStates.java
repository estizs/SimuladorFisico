package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;

public class MassEqualStates implements StateComparator{
	
	@Override
	public boolean equal(JSONObject s1, JSONObject s2) {
		// Si el valor de sus claves "time" son distintas entonces falso
		if (s1.getDouble("time") != s2.getDouble("time")) return false;
		
		JSONArray bodies1 = s1.getJSONArray("bodies");
		JSONArray bodies2 = s2.getJSONArray("bodies");
		if (bodies1.length() != bodies2.length()) return false;
		
		// Para todo i, el i-ésimo cuerpo de la lista tienen el mismo valor en las claves "id" y "mass"
		for (int i = 0; i < bodies1.length(); i++) {
			JSONObject o1 = bodies1.getJSONObject(i);
			JSONObject o2 = bodies2.getJSONObject(i);
			if ((o1.getString("id") != o2.getString("id")) || (o1.getDouble("m") != o2.getDouble("m"))) return false;
		}
		return true;
	}
}
