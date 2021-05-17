package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
	private static final long serialVersionUID = 1L;
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
	// La ventana para la selección de las fuerzas de gravedad
	private FLSelection flSelection;
	
	// El JTextField del delta-time
	private JTextField dtField;
	// Fichero de datos
	JFileChooser fileChooser;
	private static final String OPEN_TOOLTIP = "Cargar fichero seleccionado en el simulador.";
	private static final String PHYSICS_TOOLTIP = "Introducir una de las leyes de fuerza disponibles.";
	private static final String RUN_TOOLTIP = "Ejecutar la simulación.";
	private static final String STOP_TOOLTIP = "Parar la simulación.";
	private static final String EXIT_TOOLTIP = "Salir de la aplicación.";
	
	
	public ControlPanel(Controller ctrl, JFrame window) {
		this.ctrl = ctrl;
		this.window = window;
		stopped = true;
		initGUI();
		ctrl.addObserver(this);
	}
	
	private void initGUI() {
		// Control panel
		setLayout(new BorderLayout());
		// Tool bar
		JToolBar toolBar = new JToolBar();
		// Steps label
		JLabel steps = new JLabel("Steps:");
		// Steps spinner
		initSpinner();
		// Delta-Time label
		JLabel delta_time = new JLabel("Delta-Time");
		// Delta-Time text field
		initDTField();
		
		// BUTTONS
		// Open button
		fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		initOpenButton();
		// Physics button
		// Cuadro de diálogo
		flSelection = new FLSelection(window, ctrl);
		// Lo mantenemos oculto
		flSelection.setVisible(false);
		initPhysicsButton();
		// Run button
		initRunButton();
		// Stop button
		initStopButton();
		// Exit button
		initExitButton();
		
		// Añadimos los botones al toolBar
		toolBar.add(open);
		toolBar.add(new JLabel("  "));
		toolBar.add(physics);
		toolBar.add(new JLabel("  "));
		toolBar.add(run);
		toolBar.add(stop);
		toolBar.add(new JLabel("  "));
		toolBar.add(steps);
		toolBar.add(spin);
		toolBar.add(new JLabel("  "));
		toolBar.add(delta_time);
		toolBar.add(dtField);
		// Añadimos el pegamento
		toolBar.add(Box.createHorizontalGlue());
		// Añadimos el botón de exit
		toolBar.add(exit);
		
		// Añadimos la toolbar
		add(toolBar);
		setPreferredSize(new Dimension(400, 50));
		setMaximumSize(new Dimension(400, 50));
		setMinimumSize(new Dimension(400, 50));
	}

	private void initDTField() {
		dtField = new JTextField("2500.0");
		dtField.setPreferredSize(new Dimension(60, 30));
		dtField.setMaximumSize(new Dimension(60, 30));
		dtField.setMinimumSize(new Dimension(60, 30));
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
	
	private void disableButtons() {
		open.setEnabled(false);
		physics.setEnabled(false);
		run.setEnabled(false);
		exit.setEnabled(false);
		spin.setEnabled(false);
		dtField.setEnabled(false);
	}
	
	private void enableButtons() {
		open.setEnabled(true);
		physics.setEnabled(true);
		run.setEnabled(true);
		exit.setEnabled(true);
		stop.setEnabled(true);
		spin.setEnabled(true);
		dtField.setEnabled(true);
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
				flSelection.showFlSelection();
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
				enableButtons();
			}
		});
		stop.setToolTipText(STOP_TOOLTIP);
	}
	
	private void initExitButton() {
		exit = createButton("resources/icons/exit.png");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ExitDialog(window);
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
