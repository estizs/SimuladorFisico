package simulator.factories;

import java.util.List;

import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T> {
	private List<Builder<T>> builders;

	public BuilderBasedFactory(List<Builder<T>> builders) {
		this.builders = builders;
	}
	
	public T createInstance(JSONObject info) throws IllegalArgumentException {
		T bu;
		for(Builder b : builders) {
			
		}
		/*Builder bu;
		for(Builder b : builders) {
			bu = createInstance(info);
		}
		if(bu == null) throw new IllegalArgumentException();
		return bu.createInstance(info);
		*/
		return null;
	}

	public List<JSONObject> getInfo() {
		return null;
	}

}
