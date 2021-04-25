package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Vector;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

import org.json.JSONObject;

import simulator.control.*;
import simulator.model.*;

public class ControlPanel extends JPanel implements SimulatorObserver{
	private Controller ctrl;
	private boolean stopped;
	private JFrame window;
	
	public ControlPanel(Controller ctrl, JFrame window) {
		this.ctrl = ctrl;
		this.window = window;
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
		JButton physics = createButton("resources/icons/physics.png");
		physics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog flSelection = new JDialog(window, "Force Laws Selection");
				JPanel selectionPanel = new JPanel();
				selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.Y_AXIS));
				
				JTextArea desc = new JTextArea("Select a force law and provide values for the parameters in the Value column (default values are used for parameters with no value)");
				desc.setLineWrap(true);
				desc.setWrapStyleWord(true);
				desc.setOpaque(false);
				selectionPanel.add(desc);
				
				JTable values = new JTable(3, 3);
				selectionPanel.add(values);
				JPanel selection = new JPanel();
				selection.setLayout(new FlowLayout());
				selection.add(new JLabel("Force Laws: "));
				JComboBox<String> cbo = new JComboBox<String>(getForceLawsVector());
				selection.add(cbo);
				selectionPanel.add(selection);
				
				JPanel buttons = new JPanel();
				buttons.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
				buttons.add(new JButton("Cancel"));
				buttons.add(new JButton("OK"));
				selectionPanel.add(buttons);
				
				flSelection.add(selectionPanel);
				
				flSelection.setVisible(true);
				flSelection.setSize(600, 210);
			}
			
		});
		leftPanel.add(physics);
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
	
	private Vector<String> getForceLawsVector() {
		List<JSONObject> infoFl = ctrl.getForceLawsInfo();
		Vector<String> flVector = new Vector<String>();
		for (JSONObject jo: infoFl)
			flVector.addElement((String) jo.get("desc"));
		return flVector;
	}
	
}
