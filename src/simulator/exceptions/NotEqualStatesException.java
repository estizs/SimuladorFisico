package simulator.exceptions;

import org.json.JSONObject;

public class NotEqualStatesException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private JSONObject actual;
	private JSONObject expected;
	private int step;

	public NotEqualStatesException(JSONObject exp, JSONObject act, int i) {
		super("States are different at step " + i + System.lineSeparator() +
				" Actual: " + act + System.lineSeparator() + 
				" Expected: " + exp + System.lineSeparator());
		actual = act;
		expected = exp;
		step = i;
	}
}
