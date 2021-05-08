package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
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
		setContentPane(mainPanel);
		// Añadimos los paneles que hemos creado
		ControlPanel controlPanel = new ControlPanel(ctrl, this);
		mainPanel.add(controlPanel, BorderLayout.PAGE_START);
		StatusBar statusBar = new StatusBar(ctrl);
		mainPanel.add(statusBar, BorderLayout.PAGE_END);
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		BodiesTable table = new BodiesTable(ctrl);
		table.setPreferredSize(new Dimension(800, 200));
		table.setMaximumSize(new Dimension(800, 200));
		table.setMinimumSize(new Dimension(800, 200));
		centerPanel.add(table);
		Viewer viewer = new Viewer(ctrl);
		centerPanel.add(viewer);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		setVisible(true);
		pack();
		setSize(800, 800);
	}
	
	
}
