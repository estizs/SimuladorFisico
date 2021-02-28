package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;

public class EpsilonEqualStates implements StateComparator{
	private Double eps;
	
	public EpsilonEqualStates(Double eps) {
		this.eps = eps;
	}
	
	@Override
	public boolean equal(JSONObject s1, JSONObject s2) {
		// Dos números son iguales si Math.abs(a-b) <= eps
		// Dos vectores son iguales si v1.distanceTo(v2) <= eps
		boolean eq = true;
		
		// Valor de sus claves "time" son iguales
		eq = (s1.getDouble("time") == s2.getDouble("time"));
		
		// Para todo i, el i-ésimo cuerpo tienen el mismo valor en sus claves "id" y
		// las claves "m", "p", "v" y "f" son iguales módulo epsilon
		if(eq) {
			JSONArray bodies1 = s1.getJSONArray("bodies");
			JSONArray bodies2 = s2.getJSONArray("bodies");
			int i = 0;
			while(i < s1.length() && eq) {
				JSONObject o1 = bodies1.getJSONObject(i);
				JSONObject o2 = bodies2.getJSONObject(i);
				Vector2D p1 = (Vector2D) o1.get("p");
				Vector2D p2 = (Vector2D) o2.get("p");
				Vector2D v1 = (Vector2D) o1.get("v");
				Vector2D v2 = (Vector2D) o2.get("v");
				Vector2D f1 = (Vector2D) o1.get("f");
				Vector2D f2 = (Vector2D) o2.get("f");
				eq = (o1.getString("id") == o2.getString("id")) && 
					 (Math.abs(o1.getDouble("m") - o2.getDouble("m")) <= eps) &&
					 (p1.distanceTo(p2) <= eps) &&
					 (v1.distanceTo(v2) <= eps) &&
					 (f1.distanceTo(f2) <= eps);
				++i;
			}
		}
		return eq;
	}

}
