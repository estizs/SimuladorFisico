package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T> {
	private List<Builder<T>> builders;
	private List<JSONObject> factoryElements;

	public BuilderBasedFactory(List<Builder<T>> builders) {
		this.builders = new ArrayList<>(builders);
		this.factoryElements = new ArrayList<>();
		for(Builder<T> b : this.builders) {
			JSONObject o = b.getBuilderInfo();
			factoryElements.add(o);
		}
	}
	
	public T createInstance(JSONObject info) throws IllegalArgumentException {
		if(info == null) throw new IllegalArgumentException("Invalid value for create instance: null");
		T t = null;
		for(Builder<T> b : builders) {
			t = b.createInstance(info);
			if(t != null)
				break;
		}
		if (t == null) throw new IllegalArgumentException("Invalid value for create instance: no such type found");
		return t;
	}

	public List<JSONObject> getInfo() {
		return factoryElements;
	}
}
