package simulator.factories;

import org.json.JSONObject;

import simulator.control.EpsilonEqualStates;
import simulator.control.StateComparator;
import simulator.model.NewtonUniversalGravitation;

public class EpsilonEqualStatesBuilder extends Builder<StateComparator> {
	private static final String _type = "epseq";
	private static final String _desc = "Epsilon Comparator";
	private static final double _defaultEpsilon = 0.0;
	
	public EpsilonEqualStatesBuilder() {
		super(_type, _desc);
	}
	
	@Override
	protected StateComparator createTheInstance(JSONObject data) {
		double eps = data.has("eps") ? data.getDouble("eps") : _defaultEpsilon;
		return new EpsilonEqualStates(eps);
	}
	
	@Override
	protected JSONObject createData() {
		JSONObject data = new JSONObject();
		data.put("eps", "epsilon");
		return data;
	}
}