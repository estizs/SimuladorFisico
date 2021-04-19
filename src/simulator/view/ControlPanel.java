package simulator.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.*;
import simulator.control.*;
import simulator.model.*;

public class ControlPanel extends JPanel implements SimulatorObserver{
	private Controller ctrl;
	private boolean stopped;
	
	public ControlPanel(Controller ctrl) {
		this.ctrl = ctrl;
		stopped = true;
		initGUI();
		ctrl.addObserver(this);
	}

	private void initGUI() {
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 200));
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		leftPanel.add(new OpenButton());
		leftPanel.add(new PhysicsButton());
		leftPanel.add(new RunButton());
		leftPanel.add(new StopButton());
		controlPanel.add(leftPanel);
		controlPanel.add(new ExitButton());
	}
	
	private void run_sim(int n) {
		if (n > 0 && !stopped) {
			try {
				ctrl.run(1);
			} catch(Exception ex) {
				// TODO show the error in a dialog box
				// TODO enable all buttons
				stopped = true;
				return;
			}
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					run_sim(n-1);
				}
			});
		}
		else {
			stopped = true;
			// TODO enable all buttons
		}
	}
	
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		// TODO Auto-generated method stub
		
	}

	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		// TODO Auto-generated method stub
		
	}

	public void onBodyAdded(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub
		
	}

	public void onAdvance(List<Body> bodies, double time) {
		// TODO Auto-generated method stub
		
	}

	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		
	}

	public void onForceLawsChanged(String fLawsDesc) {
		// TODO Auto-generated method stub
		
	}
	
}
