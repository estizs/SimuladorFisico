package simulator.view;

import javax.swing.table.AbstractTableModel;

public class ForceLawsTableModel extends AbstractTableModel  {
	private static final long serialVersionUID = 1L;
	private String[][] fl;
	private String[] columnNames;
	
	public ForceLawsTableModel(String[][] fl, String[] columnNames) {
		this.fl = fl;
		this.columnNames = columnNames;
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
	
	public void setValueAt(Object object, int row, int col) {
		fl[row][col] = (String) object; // POSIBLE ERROR DE CONVERSIÓN
		fireTableCellUpdated(row, col);
	}

	public boolean isCellEditable(int row, int col) {
		return col == 1;
	}
	
	public String getColumnName(int col) {
		return columnNames[col];
	}
}
