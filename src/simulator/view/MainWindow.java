package simulator.view;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import simulator.control.Controller;

public class MainWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller ctrl;
	
	public MainWindow(Controller ctrl) {
		super("Physics Simulator");
		this.ctrl = ctrl;
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		// Añadimos los paneles que hemos creado
		ControlPanel controlPanel = new ControlPanel(ctrl, this);
		mainPanel.add(controlPanel, BorderLayout.PAGE_START);
		StatusBar statusBar = new StatusBar(ctrl);
		mainPanel.add(statusBar, BorderLayout.PAGE_END);
		JPanel view = new JPanel(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		Viewer viewer = new Viewer(ctrl);
		view.add(viewer);
		mainPanel.add(view, BorderLayout.CENTER);
		this.add(mainPanel);
		this.setVisible(true);
		this.pack();
	}
	
	
}
