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

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.control.*;
import simulator.model.*;

public class ControlPanel extends JPanel implements SimulatorObserver{
	private Controller ctrl;
	private boolean stopped;
	private JFrame window;
	// Los botones
	private JButton open;
	private JButton physics;
	private JButton run;
	private JButton stop;
	private JButton exit;
	// El spinner de los pasos
	private JSpinner spin;
	// El JTextField del delta-time
	private JTextField dtField;
	// Fichero de datos
	JFileChooser fileChooser;
	private static final String OPEN_TOOLTIP = "Cargar fichero seleccionado en el simulador.";
	private static final String PHYSICS_TOOLTIP = "Introducir una de las leyes de fuerza disponibles.";
	private static final String RUN_TOOLTIP = "Ejecutar la simulación.";
	private static final String STOP_TOOLTIP = "Parar la simulación.";
	private static final String EXIT_TOOLTIP = "Salir de la aplicación.";
	private static final String[] columnNames = {
			"Key", "Value", "Description"
	};
	
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
		// Tool bar
		JToolBar toolBar = new JToolBar();
		// Steps label
		JLabel steps = new JLabel("Steps:");
		// Steps spinner
		initSpinner();
		// Delta-Time label
		JLabel delta_time = new JLabel("Delta-Time");
		// Delta-Time text field
		dtField = new JTextField("2500.0");
		
		// BUTTONS
		// Open button
		fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		initOpenButton();
		// Physics button
		initPhysicsButton();
		// Run button
		initRunButton();
		// Stop button
		initStopButton();
		// Exit button
		initExitButton();
		
		// Añadimos los botones al toolBar
		toolBar.add(open);
		toolBar.add(physics);
		toolBar.add(run);
		toolBar.add(stop);
		toolBar.add(steps);
		toolBar.add(spin);
		toolBar.add(delta_time);
		toolBar.add(dtField);
		// Añadimos el pegamento
		toolBar.add(Box.createHorizontalGlue());
		// Añadimos el botón de exit
		toolBar.add(exit);
		
		// Añadimos la toolbar
		controlPanel.add(toolBar);
	}

	private void run_sim(int n) {
		if (n > 0 && !stopped) {
			try {
				ctrl.run(1);
			} catch(Exception ex) {
				// Show the error in a dialog box
				JOptionPane.showMessageDialog(null, "There was an error");
				// Enable all buttons
				enableButtons();
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
			// Enable all buttons
			enableButtons();
		}
	}
	
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		dtField.setText(dt + "");
	}

	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		dtField.setText(dt + "");
	}

	public void onBodyAdded(List<Body> bodies, Body b) {}

	public void onAdvance(List<Body> bodies, double time) {}

	public void onDeltaTimeChanged(double dt) {
		dtField.setText(dt + "");
	}

	public void onForceLawsChanged(String fLawsDesc) {}
	
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
	
	private String[] getColumnValue() {
		String[] values = {"Key", "Value", "Description"};
		return values;
	}
	
	private String[][] getRowData(JSONObject selectedItem) {
		String[][] data = new String[1][1];
		JSONObject info = selectedItem.getJSONObject("data");
		int i = 0;
		for(String key : info.keySet()) {
			data[i][0] = key;
			data[i][1] = "";
			data[i][2] = info.getString(key);
			++i;
		}
		return data;
	}
	
	private JSONObject getSelectedForceLaw(String selectedItem) {
		List<JSONObject> ja = ctrl.getForceLawsInfo();
		for(JSONObject jo: ja)
			if(selectedItem.equals(jo.getString("desc")))
				return jo;
		return null;
	}
	
	private void disableButtons() {
		open.setEnabled(false);
		physics.setEnabled(false);
		run.setEnabled(false);
		exit.setEnabled(false);
	}
	
	private void enableButtons() {
		open.setEnabled(true);
		physics.setEnabled(true);
		run.setEnabled(true);
		exit.setEnabled(true);
		stop.setEnabled(true);
	}
	
	private void initOpenButton() {
		open = createButton("resources/icons/open.png");
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
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
		open.setToolTipText(OPEN_TOOLTIP);
	}
	
	private void initPhysicsButton() {
		physics = createButton("resources/icons/physics.png");
		physics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Cuadro de diálogo
				JDialog flSelection = new JDialog(window, "Force Laws Selection");
				// Panel principal
				JPanel selectionPanel = new JPanel();
				selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.Y_AXIS));
				// Texto descriptivo
				JTextArea desc = new JTextArea("Select a force law and provide values for the parameters in the Value column (default values are used for parameters with no value)");
				desc.setLineWrap(true);
				desc.setWrapStyleWord(true);
				desc.setOpaque(false);
				selectionPanel.add(desc);
				// Panel de selección fl
				JPanel selection = new JPanel();
				selection.setLayout(new FlowLayout());
				// JComboBox
				JComboBox<String> cbo = new JComboBox<String>(getForceLawsVector());
				// Tabla con valores
				JTable values = new JTable(new ForceLawsTableModel(getRowData(getSelectedForceLaw((String) cbo.getSelectedItem())), columnNames));
				selectionPanel.add(values);
				// Label auxiliar
				selection.add(new JLabel("Force Laws: "));
				selection.add(cbo);
				// Panel de botones
				JPanel buttons = new JPanel();
				buttons.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
				buttons.add(new JButton("Cancel"));
				buttons.add(new JButton("OK"));
				selectionPanel.add(buttons);
				flSelection.add(selectionPanel);
				// Visibilidad y tamaño del cuádro de diálogo
				flSelection.setVisible(true);
				flSelection.setSize(600, 210);
			}
		});
		physics.setToolTipText(PHYSICS_TOOLTIP);
	}
	
	private void initRunButton() {
		run = createButton("resources/icons/run.png");
		run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Disable all buttons except for stop
				disableButtons();
				stopped = false;
				// Poner el delta-time al valor del campo de texto
				ctrl.setDeltaTime(Double.parseDouble(dtField.getText()));
				// Ejecutar la simulación
				run_sim((int) spin.getValue());
			}
		});
		run.setToolTipText(RUN_TOOLTIP);
	}
	
	private void initStopButton() {
		stop = createButton("resources/icons/stop.png");
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopped = true;
			}
		});
		stop.setToolTipText(STOP_TOOLTIP);
	}
	
	private void initExitButton() {
		exit = createButton("resources/icons/exit.png");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Pedir confirmación al usuario
				JDialog confirmacion = new JDialog(window, "Exit");
				JPanel panelC = new JPanel();
				panelC.setLayout(new BoxLayout(panelC, BoxLayout.Y_AXIS));
				panelC.add(new JLabel("You're about to exit the application"));
				panelC.add(new JLabel("Exit and close the application?"));
				JPanel panelB = new JPanel();
				panelB.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				JButton ok = new JButton("OK");
				ok.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
					}
				});
				JButton cancel = new JButton("Cancel");
				cancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						confirmacion.dispose();
					}
				});
				panelB.add(ok);
				panelB.add(cancel);
				panelC.add(panelB);
				confirmacion.add(panelC);
				confirmacion.setVisible(true);
				confirmacion.setBounds(window.getWidth() / 2 - 200, window.getHeight() / 2 - 50, 400, 100);
			}
		});
		exit.setToolTipText(EXIT_TOOLTIP);
	}
	
	private void initSpinner() {
		spin = new JSpinner();
		spin.setPreferredSize(new Dimension(60, 30));
		spin.setMaximumSize(new Dimension(60, 30));
		spin.setMinimumSize(new Dimension(60, 30));
		spin.setValue(10000);
	}
}
