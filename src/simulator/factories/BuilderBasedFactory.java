package simulator.factories;

import java.util.List;

import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T> {

	public BuilderBasedFactory(List<Builder<T>> builders) {
		
	}
	
	public T createInstance(JSONObject info) throws IllegalArgumentException {
		
		return null;
	}

	public List<JSONObject> getInfo() {
		return null;
	}

}
