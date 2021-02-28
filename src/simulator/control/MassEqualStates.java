package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;

public class MassEqualStates implements StateComparator{
	
	@Override
	public boolean equal(JSONObject s1, JSONObject s2) {
		boolean eq = true;
		
		// Valor de sus claves "time" son iguales
		eq = (s1.getDouble("time") == s2.getDouble("time"));
		
		// Para todo i, el i-ésimo cuerpo de la lista tienen el mismo valor en las claves "id" y "mass"
		if(eq) {
			JSONArray bodies1 = s1.getJSONArray("bodies");
			JSONArray bodies2 = s2.getJSONArray("bodies");
			int i = 0;
			while(i < s1.length() && eq) {
				JSONObject o1 = bodies1.getJSONObject(i);
				JSONObject o2 = bodies2.getJSONObject(i);
				eq = (o1.getString("id") == o2.getString("id")) && (o1.getDouble("m") == o2.getDouble("m"));
				++i;
			}
		}
		return eq;
	}
}
