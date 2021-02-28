package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

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
		JSONArray jsonArray = jsonInput.getJSONArray("bodies");
		for(int i = 0; i < jsonArray.length(); ++i)
			simulator.addBody(bodyFactory.createInstance(jsonArray.getJSONObject(i)));
	}
	public void run(int n, OutputStream out, InputStream expOut, StateComparator cmp) {
		PrintStream p = new PrintStream(out);
		
		p.println("{");
		p.println("\"states\": [");
		
		for(int i = 0; i < n; ++i) {
			//simulator.getState()
		}
		
		p.println("]");
		p.println("}");
	}	
	
}
