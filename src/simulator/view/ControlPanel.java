package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

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
		// Control panel
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new BorderLayout());
		// Left panel
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 5));
		// Buttons
		leftPanel.add(new JLabel("  "));
		JButton open = createButton("resources/icons/open.png");
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				int returnValue = fileChooser.showOpenDialog(null);
		        if (returnValue == JFileChooser.APPROVE_OPTION) {
		        	try {
		        		InputStream selectedFile = new FileInputStream(fileChooser.getSelectedFile());
		        		ctrl.reset();
			            ctrl.loadBodies(selectedFile);
		        	} catch(Exception ex) {
		        		JOptionPane.showMessageDialog(null, "El archivo seleccionado no es válido");
		        	}
		        }
			}
		});
		leftPanel.add(open);
		leftPanel.add(new JLabel("  "));
		leftPanel.add(createButton("resources/icons/physics.png"));
		leftPanel.add(new JLabel("  "));
		leftPanel.add(createButton("resources/icons/run.png"));
		leftPanel.add(createButton("resources/icons/stop.png"));
		// Steps label
		JLabel steps = new JLabel("Steps:");
		leftPanel.add(steps);
		// Steps spinner
		JSpinner spin = new JSpinner();
		spin.setPreferredSize(new Dimension(60, 30));
		spin.setMaximumSize(new Dimension(60, 30));
		spin.setMinimumSize(new Dimension(60, 30));
		spin.setValue(10000);
		leftPanel.add(spin);
		// Delta-Time label
		JLabel delta_time = new JLabel("Delta-Time");
		leftPanel.add(delta_time);
		// Delta-Time text field
		JTextField dtField = new JTextField("2500.0");
		leftPanel.add(dtField);
		controlPanel.add(leftPanel, BorderLayout.WEST);
		// Right panel
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		// Exit button
		rightPanel.add(new ExitButton());
		controlPanel.add(rightPanel, BorderLayout.EAST);
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
	
	public JButton createButton(String icon) {
		Dimension dim = new Dimension(42, 32);
		JButton b = new JButton();
		b.setIcon(new ImageIcon(icon));
		b.setOpaque(false);
		b.setContentAreaFilled(false);
		b.setPreferredSize(dim);
		b.setMaximumSize(dim);
		b.setMinimumSize(dim);
		return b;
	}
	
	
}
