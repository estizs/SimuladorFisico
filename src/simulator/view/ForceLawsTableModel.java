package simulator.view;

import javax.swing.table.AbstractTableModel;

public class ForceLawsTableModel extends AbstractTableModel  {
	private String[][] fl;
	private String[] columnNames;
	
	public ForceLawsTableModel(String[][] fl, String[] columnNames) {
		this.fl = fl;
	}

	public int getRowCount() {
		if(fl == null) return 0;
		else return fl.length;
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return fl[rowIndex][columnIndex];
	}
	
	public boolean isCellEditable(int row, int col) {
		return col == 1;
	}
}
