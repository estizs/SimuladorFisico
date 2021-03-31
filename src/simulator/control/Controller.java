package simulator.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.exceptions.NotEqualStatesException;
import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.PhysicsSimulator;

public class Controller {
	private PhysicsSimulator simulator;
	private Factory<Body> bodyFactory;
	
	public Controller(PhysicsSimulator simulator, Factory<Body> bodyFactory) {
		this.simulator = simulator;
		this.bodyFactory = bodyFactory;
	}
	
	public void loadBodies(InputStream in) {
		JSONObject jsonInput = new JSONObject(new JSONTokener(in));
		
		JSONArray bodies = jsonInput.getJSONArray("bodies");
		
		for(int i = 0; i < bodies.length(); ++i)
			simulator.addBody(bodyFactory.createInstance(bodies.getJSONObject(i)));
	}
	public void run(int n, OutputStream out, InputStream expOut, StateComparator cmp) throws NotEqualStatesException{
		JSONObject expOutJo = null;
		
		if (expOut != null) expOutJo = new JSONObject(new JSONTokener(expOut));
		
		if(out == null) {
			out = new OutputStream() {
				@Override
			public void write(int b) throws IOException {}
			};
		}
		
		PrintStream p = new PrintStream(out);
		p.println("{");
		p.println("\"states\": [");
		
		JSONObject currState = null;
		JSONObject expState = null;
		
		// Comparación de los estados iniciales
		currState = simulator.getState();
		p.println(currState);
		if(expOutJo != null) {
			expState = expOutJo.getJSONArray("states").getJSONObject(0);
			if(!cmp.equal(expState, currState)) throw new NotEqualStatesException(expState, currState, 0);
		}
		for (int i = 0; i < n; ++i) {
			simulator.advance();
			currState = simulator.getState();
			if(expOutJo != null) {
				expState = expOutJo.getJSONArray("states").getJSONObject(i);
				if(!cmp.equal(expState, currState)) throw new NotEqualStatesException(expState, currState, i);
			}
			p.println("," + currState);
		}
		p.println("]");
		p.println("}");
	}	
	
}
