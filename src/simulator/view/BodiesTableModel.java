package simulator.view;

import java.util.List;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Body> bodies;
	private String[] columnNames = {
			"Id", "Mass", "Position", "Velocity", "Force"	
		};
	
	BodiesTableModel(Controller ctrl) {
		bodies = new ArrayList<>();
		ctrl.addObserver(this);
	}

	public int getRowCount() {
		if(bodies == null) return 0;
		else return bodies.size();
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex) {
		case 0:
			return bodies.get(rowIndex).getId();
		case 1:
			return bodies.get(rowIndex).getMass(); 
		case 2:
			return bodies.get(rowIndex).getPosition();
		case 3:
			return bodies.get(rowIndex).getVelocity();
		case 4:
			return bodies.get(rowIndex).getForce();
		default:
			return null;
		}
	}
	
	public String getColumnName(int col) {
		if(columnNames == null) return "";
		else return columnNames[col];
	}

	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		this.bodies = bodies;
		fireTableDataChanged();
	}

	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		this.bodies = bodies;
		fireTableDataChanged();
	}

	public void onBodyAdded(List<Body> bodies, Body b) {
		this.bodies = bodies;
		fireTableDataChanged();
	}

	public void onAdvance(List<Body> bodies, double time) {
		this.bodies = bodies;
		fireTableDataChanged();
	}

	public void onDeltaTimeChanged(double dt) {}

	public void onForceLawsChanged(String fLawsDesc) {}

}
