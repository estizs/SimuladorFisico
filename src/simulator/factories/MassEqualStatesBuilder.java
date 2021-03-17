package simulator.factories;

import org.json.JSONObject;

import simulator.control.MassEqualStates;
import simulator.control.StateComparator;

public class MassEqualStatesBuilder extends Builder<StateComparator>{
	private static final String _type = "masseq";
	private static final String _desc = "Mass Comparator";
	
	public MassEqualStatesBuilder() {
		super(_type, _desc);
	}

	@Override
	protected StateComparator createTheInstance(JSONObject data) {
		return new MassEqualStates();
	}
	
	@Override
	protected JSONObject createData() {
		JSONObject data = new JSONObject();
		return data;
	}
}
