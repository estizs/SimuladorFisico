package simulator.exceptions;

import org.json.JSONObject;

public class NotEqualStatesException extends Exception {
	
	public NotEqualStatesException(JSONObject current, JSONObject expected, int i) {
		super("Failed in step " + i + ": the current state (" + current + ") was not the expected state (" + expected + ").");
	}
}
