package simulator.view;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class StatusBar extends JPanel implements SimulatorObserver {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel currTime;
	private JLabel currLaws;
	private JLabel numOfBodies;
	
	public StatusBar(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	}
	
	private void initGUI() {
		setLayout(new FlowLayout(FlowLayout.LEFT, 60, 0)); 
		setBorder(BorderFactory.createBevelBorder(1));
		
		// Creamos las etiquetas
		currTime = new JLabel("Time: ");
		numOfBodies = new JLabel("Bodies: ");
		currLaws = new JLabel("Laws: ");
		
		// Añadimos las etiquetas al panel
		add(currTime);
		add(numOfBodies);
		add(currLaws);
	}

	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		currLaws.setText("Laws: " + fLawsDesc);
		currTime.setText("Time: " + time);
		numOfBodies.setText("Bodies: " + bodies.size());
	}

	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		currLaws.setText("Laws: " + fLawsDesc);
		currTime.setText("Time: " + time);
		numOfBodies.setText("Bodies: " + bodies.size());
	}

	public void onBodyAdded(List<Body> bodies, Body b) {
		numOfBodies.setText("Bodies: " + bodies.size());
	}

	public void onAdvance(List<Body> bodies, double time) {
		currTime.setText("Time: " + time);
	}

	public void onDeltaTimeChanged(double dt) {}

	public void onForceLawsChanged(String fLawsDesc) {
		currLaws.setText("Laws: " + fLawsDesc);
	}

}
